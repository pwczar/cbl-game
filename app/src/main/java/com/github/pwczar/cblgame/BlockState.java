package com.github.pwczar.cblgame;

import java.awt.Graphics;

public abstract class BlockState implements Entity {
    final Block block;

    BlockState(Block block) {
        this.block = block;
    }

    public void drawRect(Graphics g) {
        g.fillRect(
            (int) block.getX(),
            (int) block.getY(),
            (int) block.getWidth(),
            (int) block.getHeight()
        );
    }

    public void draw(Graphics g) {
        g.setColor(block.color);
        drawRect(g);
    }
}
