package com.github.pwczar.cblgame;

import java.awt.Graphics;

/**
 * Main game logic thread.
 */
public class Game implements Runnable {
    // time between frames in miliseconds
    private long interval = (long) (1.0 / 60 * 1000);

    App app;
    Player player;

    /**
     * Initialize.
     * @param app the app to run under
     */
    Game(App app) {
        this.app = app;

        player = new Player();
    }

    /**
     * Draw the game.
     * @param g graphics context
     */
    public void draw(Graphics g) {
        g.clearRect(0, 0, app.getWidth(), app.getHeight());
        player.draw(g);
    }

    public void run() {
        long time = System.currentTimeMillis();
        while (true) {
            long now = System.currentTimeMillis();
            // time between now and the last frame
            double delta = (now - time) / 1000.0;
            time = now;

            player.update(delta);
            app.updateUI();

            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                // TODO: handle exit?
            }
        }
    }
}
