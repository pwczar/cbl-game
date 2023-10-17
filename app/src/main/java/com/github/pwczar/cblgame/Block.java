package com.github.pwczar.cblgame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.Random;

/**
 * Block class representing blacks falling from the top of the screen
 * and stacking at the bottom.
 */
public class Block extends Rectangle2D.Double implements Entity {
    boolean stopped;
    double vy;
    static Random rand = new Random(System.currentTimeMillis());


    /**
     * Initialize a Block object.
     */
    Block() {
        x = rand.nextInt(7) * 64;
        y = 25;
        vy = rand.nextInt(50) + 100;
        width = 64;
        height = 64;
        stopped = false;
    }

    /**
     * Draw the block at its current position.
     */
    public void draw(Game game, Graphics g) {
        g.setColor(new Color(100, 50, 200));
        g.fillRect((int) x, (int) y, (int) width, (int) height);
    }

    /**
     * Update the block.
     */
    public void update(Game game, double delta) {
        if (!stopped) {
            y += delta * vy;
            if (this.intersects(game.floor)) {
                stopped = true;
            }
        }

        for (Block other : game.blocks) {
            if (this == other) {
                continue;
            }
            if (this.intersects(other) && other.stopped) {
                this.y = other.y - this.height;
            }
        }

    }

}
