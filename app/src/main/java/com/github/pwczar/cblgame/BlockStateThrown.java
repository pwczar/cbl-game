package com.github.pwczar.cblgame;

/**
 * The state of a Block that has been thrown by the player.
 */
public class BlockStateThrown extends BlockState {
    double vy = -100;

    BlockStateThrown(Block block) {
        super(block);
    }

    /**
     * Update a falling Block.
     */
    public void update(double delta) {
        block.y += vy * delta;
        if (block.y + block.height < 0) {
            block.grid.removeBlock(block);
        }
    }
}
