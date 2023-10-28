package com.github.pwczar.cblgame;

import java.util.ConcurrentModificationException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class EnemyFactory {
    final Game game;
    Random rand;

    Timer spawnTimer;
    // time between enemy-spawns
    long spawnInterval = 6;

    EnemyFactory(Game game) {
        this.game = game;
        this.rand = new Random(System.currentTimeMillis());
    }

    Enemy createEnemy() {
        Enemy en = new Enemy(game, rand.nextInt(game.getGameWidth()), 0);
        return en;
    }

    void spawnEnemy() {
        game.addEntity(createEnemy());
    }

    /**
     * Start spawning enemies periodically.
     */
    void start() {
        spawnTimer = new Timer(true);
        spawnTimer.schedule(new TimerTask() {
            public void run() {
                while (true) {
                    double delay = spawnInterval;
                    try {
                        spawnEnemy();
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
