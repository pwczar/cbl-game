package com.github.pwczar.cblgame;

import java.util.ConcurrentModificationException;
import java.util.Random;

/**
 * An EntityFactory.
 * Can be used to create randomized Entities and spawn them periodically.
 */
public abstract class EntityFactory {
    final Game game;
    Random rand;

    Thread spawnThread;
    // initial time between entity-spanws (in seconds)
    double initialSpawnInterval = 2;
    double spawnInterval;

    /**
     * Initialize an EntityFactory.
     * @param game game
     */
    EntityFactory(Game game) {
        this.game = game;
        this.rand = new Random(System.currentTimeMillis());
    }

    /**
     * Create a new Entity.
     * @return the new Entity
     */
    Entity create() {
        return null;
    }

    /**
     * Spawn a new Entity.
     */
    void spawn() {
    }

    /**
     * Start spawning Entities periodically.
     */
    void start() {
        if (spawnThread != null) {
            return;
        }

        spawnInterval = initialSpawnInterval;
        spawnThread = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    double delay = spawnInterval;
                    try {
                        spawn();
                        // make the interval 10% smaller every 10 seconds
                        spawnInterval =
                            initialSpawnInterval * Math.pow(0.9, (int) (game.gameTime / 10.0));
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
        });
        spawnThread.start();
    }

    /**
     * Stop periodically spawning entities.
     */
    void stop() {
        if (spawnThread != null) {
            spawnThread.interrupt();
        }
    }
}
