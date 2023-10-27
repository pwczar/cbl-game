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

    final Game game;
    final BlockGrid grid;

    BlockState state;
    Color color;

    /**
     * Initialize a Block object.
     */
    Block(Game game, BlockGrid grid, double x, double y, Color color) {
        this.game = game;
        this.grid = grid;
        this.x = x;
        this.y = y;
        this.color = color;
        width = SIZE;
        height = SIZE;
        state = new BlockStateFalling(this);
    }

    /**
     * Stack the block on the grid.
     */
    public void stackOnGrid() {
        int col = (int) (x / SIZE);
        int row = grid.getHeight() - 1;

        while (grid.getBlockAt(col, row) != null) {
            row--;
            if (row < 0) {
                // TODO: game over?
                return;
            }
        }
        grid.putBlockAt(this, col, row);
    }

    /**
     * Set state to BlockStateFalling and possibly remove from grid.
     */
    public void fall() {
        state = new BlockStateFalling(this);
    }

    /**
     * Draw the block at its current position.
     */
    public void draw(Graphics g) {
        state.draw(g);
    }

    /**
     * Update the block.
     */
    public void update(double delta) {
        state.update(delta);
    }
}
