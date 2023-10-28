package com.github.pwczar.cblgame;

import java.awt.Color;
import java.awt.Image;
import java.util.ConcurrentModificationException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A Block factory.
 */
public class BlockFactory {
    final Game game;
    final BlockGrid grid;
    Random rand;

    Image[] sprites;

    Timer spawnTimer;
    // time between block-spanws (in seconds)
    long spawnInterval = 2;

    /**
     * Initialize BlockFactory.
     * @param game game
     * @param grid block grid
     */
    BlockFactory(Game game, BlockGrid grid) {
        this.game = game;
        this.grid = grid;
        this.rand = new Random(System.currentTimeMillis());
        this.sprites = new Image[] {
            game.loadSprite("bricks_green.png"),
            game.loadSprite("bricks_red.png"),
            game.loadSprite("bricks_blue.png")
        };
    }

    /**
     * Create a new Block.
     * @return the new block
     */
    Block createBlock() {
        int type = rand.nextInt(3);
        Block block = new Block(
            game,
            grid,
            rand.nextInt(grid.getWidth()) * Block.SIZE,
            0,
            type,
            sprites[type]
        );

        return block;
    }

    /**
     * Spawn a new Block.
     */
    void spawnBlock() {
        grid.addBlock();
    }

    /**
     * Start spawning blocks periodically.
     */
    void start() {
        spawnTimer = new Timer(true);
        spawnTimer.schedule(new TimerTask() {
            public void run() {
                while (true) {
                    double delay = spawnInterval;
                    try {
                        spawnBlock();
                    } catch (ConcurrentModificationException e) {
                        // try again in a moment
                        delay = 0.01;
                    }

                    try {
                        Thread.sleep((long) (delay * 1000));
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
        }, spawnInterval);
    }

    /**
     * Stop periodically spawning blocks.
     */
    void stop() {
        if (spawnTimer != null) {
            spawnTimer.cancel();
        }
    }
}
