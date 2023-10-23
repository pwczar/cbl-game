package com.github.pwczar.cblgame;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

/**
 * Main game logic thread.
 */
public class Game implements Scene {
    final App app;

    double gravity = 10;
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
        this.app = app;

        player = new Player(this, app.frame.getWidth() / 2, app.frame.getHeight());

        boundaries = new Rectangle2D[] {
            // floor
            new Rectangle2D.Double(0, app.frame.getHeight(), app.frame.getWidth(), 128),
            // left boundary
            new Rectangle2D.Double(0 - 128, 0, 128, app.frame.getHeight()),
            // right boundary
            new Rectangle2D.Double(app.frame.getWidth(), 0, 128, app.frame.getHeight()),
        };
        floor = boundaries[0];

        grid = new BlockGrid(
            this,
            app.frame.getWidth() / Block.SIZE,
            app.frame.getHeight() / Block.SIZE
        );
    }

    /**
     * Draw the game.
     * @param g graphics context
     */
    public void draw(Graphics g) {
        g.clearRect(0, 0, app.frame.getWidth(), app.frame.getHeight());
        player.draw(g);

        grid.draw(g);
    }

    /**
     * Update the game.
     * @param delta time since the last update (frame)
     */
    public void update(double delta) {
        player.update(delta);
        grid.update(delta);
    }

    public void run() {
        app.frame.addKeyListener(player);
        grid.startSpawning();
    }

    /**
     * Clean up after game.
     */
    public void stop() {
        app.frame.removeKeyListener(player);
        grid.stopSpawning();
    }
}
