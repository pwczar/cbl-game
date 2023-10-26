package com.github.pwczar.cblgame;

import java.awt.Color;
import java.awt.Graphics;

/**
 * A falling Block's state.
 */
public class BlockStateFalling extends BlockState {
    double vy = 200;

    BlockStateFalling(Block block) {
        super(block);
    }

    /**
     * Draw a falling Block.
     */
    public void draw(Graphics g) {
        Color c = new Color(
            block.color.getRed(),
            block.color.getGreen(),
            block.color.getBlue(),
            160
        );
        g.setColor(c);
        drawRect(g);
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
