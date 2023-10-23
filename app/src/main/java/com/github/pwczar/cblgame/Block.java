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
    BlockGrid grid;

    boolean stopped;
    double vy;
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
        vy = 200;
        width = SIZE;
        height = SIZE;
        stopped = false;
    }

    /**
     * Stop the block and put (align) it on the grid.
     */
    public void putOnGrid() {
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

        grid.getBlocks().stream()
            .filter((Block block) -> (block != this))
            .forEach((Block block) -> {
                if (this.intersects(block)) {
                    this.y = block.y - this.height;
                    if (block.stopped) {
                        putOnGrid();
                    }
                }
            });
    }
}
