package com.github.pwczar.cblgame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;

/**
 * Main game logic thread.
 */
public class Game extends Scene {
    static final double SCALE = 4.0;

    double gravity = 6;

    private List<Entity> entities = new ArrayList<>();

    Player player;
    double gameTime = 0;

    // TODO: rewrite as a class implementing Entity
    Rectangle2D[] boundaries;
    Rectangle2D floor;
    final int floorOffset;

    BlockGrid grid;

    EnemyFactory enemyFactory = new EnemyFactory(this);

    /**
     * Initialize.
     * @param app the app to run under
     */
    Game(App app) {
        super(app);

        floorOffset = getGameHeight()
            - (getGameHeight() / Block.SIZE) * Block.SIZE + Block.SIZE * 3;

        boundaries = new Rectangle2D[] {
            // floor
            new Rectangle2D.Double(
                0,
                getGameHeight() - floorOffset,
                getGameWidth(),
                128
            ),
            // left boundary
            new Rectangle2D.Double(0 - 128, 0, 128, getGameHeight()),
            // right boundary
            new Rectangle2D.Double(getGameWidth(), 0, 128, getGameHeight()),
        };
        floor = boundaries[0];

        player = new Player(this, getGameWidth() / 2, floor.getY());

        grid = new BlockGrid(
            this,
            getGameWidth() / Block.SIZE,
            (getGameHeight() - floorOffset) / Block.SIZE
        );

        addEntity(player);
        addEntity(grid);

        for (int i = 0; i < 5; i++) {
            // addEntity(enemyFactory.createEnemy());
        }
    }

    int getGameWidth() {
        return (int) (app.getWidth() / SCALE);
    }

    int getGameHeight() {
        return (int) (app.getHeight() / SCALE);
    }

    Image loadSprite(String path) {
        return new ImageIcon(getClass().getClassLoader().getResource(path)).getImage();
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
        Image buffer = new BufferedImage(
            getGameWidth(),
            getGameHeight(),
            BufferedImage.TYPE_INT_ARGB
        );
        Graphics bg = buffer.getGraphics();

        bg.setColor(new Color(255, 255, 255));
        bg.fillRect(
            0,
            0,
            getGameWidth(),
            getGameHeight()
        );

        for (Entity ent : getEntities()) {
            ent.draw(bg);
        }

        player.drawUI(bg);

        g.drawImage(
            buffer.getScaledInstance(app.getWidth(), app.getHeight(), Image.SCALE_SMOOTH),
            0, 0, null
        );
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
