package com.github.pwczar.cblgame;

import java.awt.Graphics;
import java.awt.Image;

/**
 * The state of a Block that has been thrown by the player.
 */
public class BlockStateThrown extends BlockState {
    double vy = -160;
    Image trailImage;

    /**
     * Initialize BlockStateThrown and load trail image.
     * @param block the block to depend on
     */
    BlockStateThrown(Block block) {
        super(block);
        trailImage = block.game.loadSprite("trail.png");
    }

    /**
     * Draw a trail after the block.
     */
    public void draw(Graphics g) {
        super.draw(g);
        g.drawImage(
            trailImage,
            (int) block.x,
            (int) block.y + Block.SIZE,
            null
        );
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
