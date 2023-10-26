package com.github.pwczar.cblgame;

import java.awt.Graphics;

/**
 * A Block's state, defines what to do on draw and update.
 */
public abstract class BlockState implements Entity, Cloneable {
    final Block block;

    BlockState(Block block) {
        this.block = block;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Draw a rectangle matching the Block.
     * @param g graphics context
     */
    public void drawRect(Graphics g) {
        g.fillRect(
            (int) block.getX(),
            (int) block.getY(),
            (int) block.getWidth(),
            (int) block.getHeight()
        );
    }

    /**
     * Draw the Block.
     */
    public void draw(Graphics g) {
        g.setColor(block.color);
        drawRect(g);
    }
}
