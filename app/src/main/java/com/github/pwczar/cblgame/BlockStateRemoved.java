package com.github.pwczar.cblgame;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;

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
        Graphics2D g2d = (Graphics2D) g;
        // apply opacity
        g2d.setComposite(
            AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) (timeLeft / LIFESPAN))
        );
        g.drawImage(block.sprite, (int) block.x, (int) block.y, null);
        // reset AlphaComposite
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
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
