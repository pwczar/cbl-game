package com.github.pwczar.cblgame;

import java.awt.Color;
import java.awt.Graphics;

public class BlockStateFalling extends BlockState {
    double vy = 200;

    BlockStateFalling(Block block) {
        super(block);
    }

    public void draw(Graphics g) {
        Color c = new Color(
            block.color.getRed(),
            block.color.getGreen(),
            block.color.getBlue()
        );
        g.setColor(c);
        drawRect(g);
    }

    public void update(double delta) {
        block.y += vy * delta;
        if (block.intersects(block.game.floor)) {
            block.putOnGrid();
        }

        for (Block b : block.grid.getBlocks()) {
            if (b == block) {
                continue;
            }
            if (block.intersects(b)) {
                block.y = b.y - block.height;
                if (b.state instanceof BlockStateStacked) {
                    block.putOnGrid();
                }
            }
        }
    }
}
