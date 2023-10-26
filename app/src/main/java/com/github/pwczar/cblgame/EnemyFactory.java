package com.github.pwczar.cblgame;

import java.util.ConcurrentModificationException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class EnemyFactory {
    final Game game;
    Random rand;

    EnemyFactory(Game game) {
        this.game = game;
        this.rand = new Random(System.currentTimeMillis());
    }

    Enemy createEnemy() {
        Enemy en = new Enemy(game, rand.nextInt(500), 0);
        return en;
    }

    void spawnEnemy() {
        game.addEntity(createEnemy());
    }

}