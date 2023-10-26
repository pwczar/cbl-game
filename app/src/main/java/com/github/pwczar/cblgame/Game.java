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
    private List<Entity> toBeAddedEntities = new ArrayList<>();
    private List<Entity> toBeRemovedEntities = new ArrayList<>();

    Player player;

    // TODO: rewrite as a class implementing Entity
    Rectangle2D[] boundaries;
    Rectangle2D floor;

    BlockGrid grid;

    /**
     * Initialize.
     * @param app the app to run under
     */
    Game(App app) {
        super(app);

        player = new Player(this, app.getWidth() / 2, app.getHeight());

        boundaries = new Rectangle2D[] {
            // floor
            new Rectangle2D.Double(0, app.getHeight(), app.getWidth(), 128),
            // left boundary
            new Rectangle2D.Double(0 - 128, 0, 128, app.getHeight()),
            // right boundary
            new Rectangle2D.Double(app.getWidth(), 0, 128, app.getHeight()),
        };
        floor = boundaries[0];

        grid = new BlockGrid(
            this,
            app.getWidth() / Block.SIZE,
            app.getHeight() / Block.SIZE
        );

        addEntity(player);
        addEntity(grid);
    }

    /**
     * Add an Entity to the game.
     * @param ent the Entity to add
     */
    public void addEntity(Entity ent) {
        if (entities.indexOf(ent) != -1 || toBeAddedEntities.indexOf(ent) != -1) {
            return;
        }
        toBeAddedEntities.add(ent);
    }

    /**
     * Remvoe an Entity from the game.
     * @param ent the Entity to remove
     */
    public void removeEntity(Entity ent) {
        if (ent == player || ent == grid) {
            throw new IllegalArgumentException("This entity cannot be removed.");
        }
        toBeRemovedEntities.add(ent);
    }

    public List<Entity> getEntities() {
        return Collections.unmodifiableList(entities);
    }

    /**
     * Draw the game.
     * @param g graphics context
     */
    public void draw(Graphics g) {
        g.clearRect(0, 0, app.getWidth(), app.getHeight());

        for (Entity ent : entities) {
            ent.draw(g);
        }

        player.drawUI(g);
    }

    /**
     * Update the game.
     * @param delta time since the last update (frame)
     */
    public void update(double delta) {
        for (Entity ent : entities) {
            ent.update(delta);
        }

        for (Entity ent : toBeAddedEntities) {
            entities.add(ent);
        }
        toBeAddedEntities.clear();
        for (Entity ent : toBeRemovedEntities) {
            entities.remove(ent);
        }
        toBeRemovedEntities.clear();
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
