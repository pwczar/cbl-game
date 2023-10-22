package com.github.pwczar.cblgame;

import java.awt.Color;
import java.util.ConcurrentModificationException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A Block factory.
 */
public class BlockFactory {
    Game game;
    BlockGrid grid;
    Random rand;

    Color[] colors;

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
        this.colors = new Color[] {
            new Color(100, 50, 200)
        };
    }

    /**
     * Create a new Block.
     * @return the new block
     */
    Block createBlock() {
        Block block = new Block(
            this.game,
            rand.nextInt(grid.getWidth()) * Block.SIZE,
            0,
            this.colors[rand.nextInt(colors.length)]
        );

        return block;
    }

    /**
     * Create a new Block, add it to the game, and return it.
     * @return the new block
     */
    Block spawnBlock() {
        Block block = createBlock();
        grid.blocks.add(block);
        return block;
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
