package com.github.pwczar.cblgame;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Main game logic thread.
 */
public class Game extends Scene {
    double gravity = 10;

    private List<Entity> entities = new ArrayList<>();

    Player player;
    double gameTime = 0;

    // TODO: rewrite as a class implementing Entity
    Rectangle2D[] boundaries;
    Rectangle2D floor;
    static final int FLOOR_OFFSET = Block.SIZE * 3;

    BlockGrid grid;

    EnemyFactory enemyFactory = new EnemyFactory(this);

    /**
     * Initialize.
     * @param app the app to run under
     */
    Game(App app) {
        super(app);

        boundaries = new Rectangle2D[] {
            // floor
            new Rectangle2D.Double(0, app.getHeight() - FLOOR_OFFSET, app.getWidth(), 128),
            // left boundary
            new Rectangle2D.Double(0 - 128, 0, 128, app.getHeight()),
            // right boundary
            new Rectangle2D.Double(app.getWidth(), 0, 128, app.getHeight()),
        };
        floor = boundaries[0];

        player = new Player(this, app.getWidth() / 2, floor.getY());

        grid = new BlockGrid(
            this,
            app.getWidth() / Block.SIZE,
            (app.getHeight() - FLOOR_OFFSET) / Block.SIZE
        );

        addEntity(player);
        addEntity(grid);

        for (int i = 0; i < 5; i++) {
            addEntity(enemyFactory.createEnemy());
        }
    }

    /**
     * Add an Entity to the game.
     * @param ent the Entity to add
     */
    public void addEntity(Entity ent) {
        if (entities.indexOf(ent) != -1) {
            return;
        }
        entities.add(ent);
    }

    /**
     * Remvoe an Entity from the game.
     * @param ent the Entity to remove
     */
    public void removeEntity(Entity ent) {
        if (ent == player || ent == grid) {
            throw new IllegalArgumentException("This entity cannot be removed.");
        }
        entities.remove(ent);
    }

    public List<Entity> getEntities() {
        return Collections.unmodifiableList(new ArrayList<>(entities));
    }

    /**
     * Draw the game.
     * @param g graphics context
     */
    public void draw(Graphics g) {
        g.clearRect(0, 0, app.getWidth(), app.getHeight());

        for (Entity ent : getEntities()) {
            ent.draw(g);
        }

        player.drawUI(g);
    }

    /**
     * Update the game.
     * @param delta time since the last update (frame)
     */
    public void update(double delta) {
        for (Entity ent : getEntities()) {
            ent.update(delta);
        }

        gameTime += delta;
    }

    public void run() {
        app.addKeyListener(player);
        grid.startSpawning();
    }

    /**
     * Clean up after game.
     */
    public void exit() {
        app.removeKeyListener(player);
        grid.stopSpawning();
    }
}
