package com.github.pwczar.cblgame;

/**
 * The state of a Block that should disappear.
 */
public class BlockStateRemoved extends BlockStateStacked {
    double timeLeft = 1;

    BlockStateRemoved(Block block) {
        super(block);
    }

    /**
     * Update a disappearing Block.
     */
    public void update(double delta) {
        timeLeft -= delta;
        if (timeLeft <= 0) {
            block.grid.removeBlock(block);
        }
    }
}
