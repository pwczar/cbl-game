package com.github.pwczar.cblgame;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlockGrid {
    Game game;
    // TODO: make these private
    public List<Block> blocks;
    public Block[][] grid;
    private BlockFactory factory;

    BlockGrid(Game game, int width, int height) {
        this.game = game;
        blocks = new ArrayList<>();
        grid
            = new Block[width][height];
        factory = new BlockFactory(game, this);
    }

    public int getWidth() {
        return grid.length;
    }

    public int getHeight() {
        return grid[0].length;
    }

    public List<Block> getBlocks() {
        return Collections.unmodifiableList(blocks);
    }

    public void addBlock() {

    }

    public void startSpawning() {
        factory.start();
    }

    public void stopSpawning() {
        factory.stop();
    }

    public void draw(Graphics g) {
        for (Block block : blocks) {
            block.draw(g);
        }
    }
}
