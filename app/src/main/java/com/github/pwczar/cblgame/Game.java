package com.github.pwczar.cblgame;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Main game logic thread.
 */
public class Game extends Scene {
    double gravity = 10;
    Player player;
    double gameTime = 0;

    // TODO: rewrite as a class implementing Entity
    Rectangle2D[] boundaries;
    Rectangle2D floor;

    BlockGrid grid;

    EnemyFactory enemyFactory = new EnemyFactory(this);
    List<Enemy> enemies = new ArrayList<>();

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

        for (int i = 0; i < 5; i++) {
            enemies.add(enemyFactory.createEnemy());
        }
    }

    /**
     * Draw the game.
     * @param g graphics context
     */
    public void draw(Graphics g) {
        g.clearRect(0, 0, app.getWidth(), app.getHeight());
        player.draw(g);
        grid.draw(g);

        for (Enemy e : enemies) {
            e.draw(g);
        }
    }

    /**
     * Update the game.
     * @param delta time since the last update (frame)
     */
    public void update(double delta) {
        player.update(delta);
        grid.update(delta);

        for (Enemy e : enemies) {
            e.update(delta);
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
