package com.github.pwczar.cblgame;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * Main game logic thread.
 */
public class Game implements Scene {
    // time between frames in miliseconds
    private long interval = (long) (1.0 / 60 * 1000);

    App app;

    double gravity = 10;
    Player player;

    // TODO: rewrite as a class implementing Entity
    Rectangle2D[] boundaries;
    Rectangle2D floor;

    BlockFactory blockFactory;
    ArrayList<Block> blocks = new ArrayList<Block>();
    Block[][] grid;

    /**
     * Initialize.
     * @param app the app to run under
     */
    Game(App app) {
        this.app = app;

        player = new Player(this, app.frame.getWidth() / 2, app.frame.getHeight());
        app.frame.addKeyListener(player);

        boundaries = new Rectangle2D[] {
            // floor
            new Rectangle2D.Double(0, app.frame.getHeight(), app.frame.getWidth(), 128),
            // left boundary
            new Rectangle2D.Double(0 - 128, 0, 128, app.frame.getHeight()),
            // right boundary
            new Rectangle2D.Double(app.frame.getWidth(), 0, 128, app.frame.getHeight()),
        };
        floor = boundaries[0];

        blockFactory = new BlockFactory(this);
        grid
            = new Block[app.frame.getWidth() / Block.SIZE][app.frame.getHeight() / Block.SIZE];
        blockFactory.start();
    }

    int getGridWidth() {
        return grid.length;
    }

    int getGridHeight() {
        return grid[0].length;
    }

    /**
     * Draw the game.
     * @param g graphics context
     */
    public void draw(Graphics g) {
        g.clearRect(0, 0, app.frame.getWidth(), app.frame.getHeight());
        player.draw(g);

        for (Block block : blocks) {
            block.draw(g);
        }
    }

    /**
     * Update the game.
     * @param delta time since the last update (frame)
     */
    public void update(double delta) {
        player.update(delta);

        for (Block block : blocks) {
            block.update(delta);
        }
    }

    public void run() {
        long time = System.currentTimeMillis();
        while (true) {
            long now = System.currentTimeMillis();
            // time between now and the last frame
            double delta = (now - time) / 1000.0;
            time = now;

            update(delta);
            app.updateUI();

            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                // TODO: handle exit?
                blockFactory.stop();
            }
        }
    }
}
