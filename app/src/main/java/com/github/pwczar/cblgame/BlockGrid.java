package com.github.pwczar.cblgame;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * A grid of blocks, some of which may be unaligned.
 */
public class BlockGrid implements Entity {
    final Game game;
    private List<Block> blocks = new ArrayList<>();
    private Block[][] grid;
    private BlockFactory factory;
    private Map<Block, Boolean> checkedBlocks = new HashMap<Block, Boolean>();
    private List<Block> toBeRemoved = new ArrayList<>();

    /**
     * Initialize a BlockGrid.
     * @param game the game to work
     * @param width the grid's number of columns
     * @param height the grid's number of rows
     */
    BlockGrid(Game game, int width, int height) {
        this.game = game;
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

    /**
     * Get the block present at this position on the grid.
     * @param col column
     * @param row row
     * @return Block or null
     */
    public Block getBlockAt(int col, int row) {
        if (col < 0 || col >= getWidth() || row < 0 || row >= getHeight()) {
            return null;
        }
        return grid[col][row];
    }

    /**
     * Align the given block to the grid at given position.
     * @param block the block to align, must be part of this BlockGrid or null to remove a block
     * @param col column of grid
     * @param row row of grid
     * @throws NoSuchElementException the given block is not part of this grid
     */
    public void putBlockAt(Block block, int col, int row) throws NoSuchElementException {
        if (block != null && blocks.indexOf(block) == -1) {
            throw new NoSuchElementException("Block is not part of BlockGrid.");
        }

        if (getBlockAt(col, row) != null) {
            toBeRemoved.add(getBlockAt(col, row));
        }

        grid[col][row] = block;
        if (block == null) {
            unstackBlockAt(col, row - 1);
            return;
        }

        block.x = col * Block.SIZE;
        block.y = row * Block.SIZE;
        block.state = new BlockStateStacked(block);
        checkedBlocks.clear();
        evalPatternsAt(col, row);
    }

    public void unstackBlockAt(int col, int row) {
        Block block = getBlockAt(col, row);
        if (block == null) {
            return;
        }

        grid[col][row] = null;
        block.fall();

        unstackBlockAt(col, row - 1);
    }

    /**
     * Remove Block patterns.
     */
    private void evalPatternsAt(int col, int row) {
        Block block = getBlockAt(col, row);
        if (block == null) {
            return;
        }
        if (checkedBlocks.get(block) != null) {
            return;
        }
        checkedBlocks.put(block, true);

        // horizontal
        Block ln = getBlockAt(col - 1, row);
        Block rn = getBlockAt(col + 1, row);

        if (ln != null && rn != null
            && ln.color == block.color && rn.color == block.color) {
            putBlockAt(null, col, row);
            putBlockAt(null, col - 1, row);
            putBlockAt(null, col + 1, row);
            // TODO: add an animation/effect + award points?
        } else if (ln != null && ln.color == block.color) {
            evalPatternsAt(col - 1, row);
        } else if (rn != null && rn.color == block.color) {
            evalPatternsAt(col + 1, row);
        }

        // vertical
        Block tn = getBlockAt(col, row - 1);
        Block bn = getBlockAt(col, row + 1);

        if (tn != null && bn != null
            && tn.color == block.color && bn.color == block.color) {
            putBlockAt(null, col, row - 1);
            putBlockAt(null, col, row);
            putBlockAt(null, col, row + 1);
            // TODO: same as horizontal
        } else if (tn != null && tn.color == block.color) {
            evalPatternsAt(col, row - 1);
        } else if (bn != null && bn.color == block.color) {
            evalPatternsAt(col, row + 1);
        }
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

        for (Block block : toBeRemoved) {
            unstackBlockAt(
                (int) (block.x / Block.SIZE),
                (int) (block.y / Block.SIZE)
            );
            blocks.remove(block);
        }
        toBeRemoved.clear();
    }
}
