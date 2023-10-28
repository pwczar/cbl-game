package com.github.pwczar.cblgame;

/**
 * A falling Block's state.
 */
public class BlockStateFalling extends BlockState {
    double vy = 50;

    BlockStateFalling(Block block) {
        super(block);
    }

    /**
     * Update a falling Block by moving down and checking for collisions.
     */
    public void update(double delta) {
        block.y += vy * delta;
        if (block.intersects(block.game.floor)) {
            block.stackOnGrid();
        }

        for (Block b : block.grid.getBlocks()) {
            if (b == block) {
                continue;
            }
            if (block.intersects(b)) {
                block.y = b.y - block.height;
                if (b.state instanceof BlockStateStacked) {
                    block.stackOnGrid();
                }
            }
        }
    }
}
