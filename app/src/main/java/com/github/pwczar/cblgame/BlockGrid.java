package com.github.pwczar.cblgame;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * Delete a Block.
     * @param block the block to be removed
     */
    public void removeBlock(Block block) {
        if (blocks.indexOf(block) == -1) {
            return;
        }

        toBeRemoved.add(block);
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
     * Align the given block to the grid at given position and possible add it.
     * @param block the block
     * @param col column of grid
     * @param row row of grid
     */
    public void putBlockAt(Block block, int col, int row) throws NullPointerException {
        if (block == null) {
            throw new NullPointerException("Block cannot be null.");
        }

        if (blocks.indexOf(block) == -1) {
            blocks.add(block);
        }

        if (getBlockAt(col, row) != null) {
            toBeRemoved.add(getBlockAt(col, row));
        }

        grid[col][row] = block;

        block.x = col * Block.SIZE;
        block.y = row * Block.SIZE;
        block.state = new BlockStateStacked(block);
        checkedBlocks.clear();
        evalPatternsAt(col, row);
    }

    /**
     * Unalign a Block at the given position from the grid, i. e. make it fall.
     * @param col column
     * @param row row
     */
    public void unstackBlockAt(int col, int row) {
        Block block = getBlockAt(col, row);
        if (block == null || block.state instanceof BlockStateRemoved) {
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
            getBlockAt(col - 1, row).state = new BlockStateRemoved(getBlockAt(col - 1, row));
            getBlockAt(col, row).state     = new BlockStateRemoved(getBlockAt(col, row));
            getBlockAt(col + 1, row).state = new BlockStateRemoved(getBlockAt(col + 1, row));
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
            getBlockAt(col, row - 1).state = new BlockStateRemoved(getBlockAt(col, row - 1));
            getBlockAt(col, row).state     = new BlockStateRemoved(getBlockAt(col, row));
            getBlockAt(col, row + 1).state = new BlockStateRemoved(getBlockAt(col, row + 1));
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
            grid[(int) (block.x / Block.SIZE)][(int) (block.y / Block.SIZE)] = null;
            blocks.remove(block);
            unstackBlockAt(
                (int) (block.x / Block.SIZE),
                (int) (block.y / Block.SIZE) - 1
            );
        }
        toBeRemoved.clear();
    }
}
