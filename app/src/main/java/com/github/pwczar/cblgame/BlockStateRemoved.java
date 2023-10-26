package com.github.pwczar.cblgame;

import java.awt.Color;
import java.awt.Graphics;

/**
 * The state of a Block that should disappear.
 */
public class BlockStateRemoved extends BlockStateStacked {
    static final double LIFESPAN = 1;
    double timeLeft = LIFESPAN;

    BlockStateRemoved(Block block) {
        super(block);
    }

    /**
     * Draw Block with fade-out.
     */
    public void draw(Graphics g) {
        g.setColor(new Color(
            block.color.getRed(),
            block.color.getGreen(),
            block.color.getBlue(),
            (int) (255.0 * (timeLeft / LIFESPAN))
        ));
        drawRect(g);
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
