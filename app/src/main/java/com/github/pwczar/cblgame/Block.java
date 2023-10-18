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

    Game game;

    boolean stopped;
    double vy;

    /**
     * Initialize a Block object.
     */
    Block(Game game) {
        this.game = game;
        x = rand.nextInt(game.getGridWidth()) * SIZE;
        y = 0;
        vy = 200;
        width = SIZE;
        height = SIZE;
        stopped = false;
    }

    /**
     * Stop the block and put it on game's grid.
     */
    public void putOnGrid() {
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
    public void draw(Graphics g) {
        g.setColor(new Color(100, 50, 200));
        g.fillRect((int) x, (int) y, (int) width, (int) height);
    }

    /**
     * Update the block.
     */
    public void update(double delta) {
        if (!stopped) {
            y += delta * vy;
            if (this.intersects(game.floor)) {
                putOnGrid();
            }
        }

        for (Block other : game.blocks) {
            if (this == other) {
                continue;
            }
            if (this.intersects(other)) {
                this.y = other.y - this.height;
                if (other.stopped) {
                    putOnGrid();
                }
            }
        }

    }

}
