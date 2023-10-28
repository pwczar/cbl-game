package com.github.pwczar.cblgame;

import java.awt.Graphics;

/**
 * A Block's state, defines what to do on draw and update.
 */
public abstract class BlockState implements Entity {
    final Block block;

    BlockState(Block block) {
        this.block = block;
    }

    /**
     * Draw the Block.
     */
    public void draw(Graphics g) {
        g.drawImage(block.sprite, (int) block.x, (int) block.y, null);
    }
}
