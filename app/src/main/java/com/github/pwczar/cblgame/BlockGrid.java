package com.github.pwczar.cblgame;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * A grid of blocks, some of which may be unaligned.
 */
public class BlockGrid implements Entity {
    final Game game;
    private List<Block> blocks;
    private Block[][] grid;
    private BlockFactory factory;

    /**
     * Initialize a BlockGrid.
     * @param game the game to work
     * @param width the grid's number of columns
     * @param height the grid's number of rows
     */
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
        blocks.add(factory.createBlock());
    }

    public Block getBlockAt(int x, int y) {
        return grid[x][y];
    }

    /**
     * Align the given block to the grid at given position.
     * @param block the block to align, must be part of this BlockGrid
     * @param col column of grid
     * @param row row of grid
     * @throws NoSuchElementException the given block is not part of this grid
     */
    public void putBlockAt(Block block, int col, int row) throws NoSuchElementException {
        if (blocks.indexOf(block) == -1) {
            throw new NoSuchElementException("Block is not part of BlockGrid.");
        }

        grid[col][row] = block;
        block.x = col * Block.SIZE;
        block.y = row * Block.SIZE;
        block.stopped = true;

        // TODO: check for the presence a pattern around (col, row)
    }

    public void startSpawning() {
        factory.start();
    }

    public void stopSpawning() {
        factory.stop();
    }

    /**
     * Draw all blocks.
     * @param g the graphics context
     */
    public void draw(Graphics g) {
        for (Block block : blocks) {
            block.draw(g);
        }
    }

    /**
     * Update all blocks.
     * @param delta time since the last frame/update.
     */
    public void update(double delta) {
        for (Block block : blocks) {
            block.update(delta);
        }
    }
}
