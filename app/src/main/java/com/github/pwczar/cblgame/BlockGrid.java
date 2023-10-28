package com.github.pwczar.cblgame;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
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

    Image backgroundTile;

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
        backgroundTile = game.loadSprite("bricks.png");
    }

    public int getWidth() {
        return grid.length;
    }

    public int getHeight() {
        return grid[0].length;
    }

    public List<Block> getBlocks() {
        return Collections.unmodifiableList(new ArrayList<>(blocks));
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

        int col = (int) (block.x / Block.SIZE);
        int row = (int) (block.y / Block.SIZE);
        grid[col][row] = null;
        unstackBlockAt(col, row - 1);
        blocks.remove(block);
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
            blocks.remove(getBlockAt(col, row));
        }

        grid[col][row] = block;

        block.x = col * Block.SIZE;
        block.y = row * Block.SIZE;
        block.state = new BlockStateStacked(block);
        checkedBlocks.clear();
        matchBlocksAt(col, row);
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
     * Remove blocks a, b, c if they have the same type and give the player an upgrade.
     * This method already assumes a, b and c form a line.
     * @param a block
     * @param b block
     * @param c block
     * @return true if blocks were matched
     */
    private boolean matchBlocks(Block a, Block b, Block c) {
        if (a == null || b == null || c == null) {
            return false;
        }
        if (a.state instanceof BlockStateRemoved
            || b.state instanceof BlockStateRemoved
            || c.state instanceof BlockStateRemoved) {
            return false;
        }
        if (a.type == b.type && b.type == c.type) {
            a.state = new BlockStateRemoved(a);
            b.state = new BlockStateRemoved(b);
            c.state = new BlockStateRemoved(c);
            game.player.giveUpgrade(a.type);
            return true;
        }
        return false;
    }

    /**
     * Try to match blocks of the same type around col, row.
     * @param col column
     * @param row row
     * @return true if blocks were matched
     */
    private boolean matchBlocksAt(int col, int row) {
        Block block = getBlockAt(col, row);
        if (block == null
            || checkedBlocks.get(block) != null
            || block.state instanceof BlockStateRemoved) {
            return false;
        }
        checkedBlocks.put(block, true);

        // horizontal
        Block ln = getBlockAt(col - 1, row);
        Block rn = getBlockAt(col + 1, row);

        if (matchBlocks(ln, block, rn)
            || matchBlocksAt(col - 1, row)
            || matchBlocksAt(col + 1, row)) {
            return true;
        }

        // vertical
        Block tn = getBlockAt(col, row - 1);
        Block bn = getBlockAt(col, row + 1);

        if (matchBlocks(tn, block, bn)
            || matchBlocksAt(col, row - 1)
            || matchBlocksAt(col, row + 1)) {
            return true;
        }

        return false;
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
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        for (int col = 0; col < getWidth(); col++) {
            for (int row = 0; row < getHeight(); row++) {
                g.drawImage(
                    backgroundTile,
                    (int) (col * Block.SIZE),
                    (int) (row * Block.SIZE),
                    null
                );
            }
        }
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

        for (Block block : getBlocks()) {
            block.draw(g);
        }
    }

    /**
     * Update all blocks.
     * @param delta time since the last frame/update.
     */
    public void update(double delta) {
        for (Block block : getBlocks()) {
            block.update(delta);
        }
    }
}
