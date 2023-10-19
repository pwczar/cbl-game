package com.github.pwczar.cblgame;

import java.awt.Color;
import java.util.Random;

public class BlockFactory {
    Game game;
    Random rand;
    Color colors[];

    BlockFactory(Game game) {
        this.game = game;
        this.rand = new Random(System.currentTimeMillis());
        this.colors = new Color[] {
            new Color(100, 50, 200)
        };
    }

    Block createBlock() {
        Block block = new Block(
            this.game,
            rand.nextInt(game.getGridWidth()) * Block.SIZE,
            0,
            this.colors[rand.nextInt(colors.length)]
        );

        return block;
    }
}
