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
    static Random rand = new Random(System.currentTimeMillis());
    static final int SIZE = 32;

    boolean stopped;
    double vy;

    /**
     * Initialize a Block object.
     */
    Block() {
        x = 0;
        y = 0;
        vy = 200;
        width = SIZE;
        height = SIZE;
        stopped = false;
    }

    /**
     * Initialize a Block object with a randomized
     * position dependent on game's grid size.
     * @param game game
     */
    Block(Game game) {
        this();
        x = rand.nextInt(game.getGridWidth()) * SIZE;
        y = 0;
    }

    /**
     * Stop the block and put it on game's grid.
     * @param game game
     */
    public void putOnGrid(Game game) {
        int col = (int) (x / SIZE);
        int row = game.getGridHeight() - 1;

        while (game.grid[col][row] != null) {
            row--;
            if (row < 0) {
                // TODO: game over?
                return;
            }
        }
        game.grid[col][row] = this;
        stopped = true;
        x = col * SIZE;
        y = row * SIZE;
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
                putOnGrid(game);
            }
        }

        for (Block other : game.blocks) {
            if (this == other) {
                continue;
            }
            if (this.intersects(other) && other.stopped) {
                this.y = other.y - this.height;
                putOnGrid(game);
            }
        }

    }

}
