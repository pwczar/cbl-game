package com.github.pwczar.cblgame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;

/**
 * The class representing the player character.
 */
public class Player extends Rectangle2D.Double implements Entity, KeyListener {
    final Game game;

    double vx = 0;
    double vy = 0;

    double mass = 60;
    double moveSpeed = 160;
    double jumpForce = 200;

    // controls state
    private boolean moveLeft = false;
    private boolean moveRight = false;

    // is the player currently standing on something?
    private boolean onFloor = false;

    boolean facingRight = true;

    Block heldBlock = null;

    /**
     * Initialize a Player object.
     */
    Player(Game game) {
        this.game = game;
        x = 0;
        y = 0;
        width = 24;
        height = 32;
    }

    /**
     * Create a Player object at the given point.
     * @param x the x coordinate (player center)
     * @param y the y coordinate (bottom of player)
     */
    Player(Game game, double x, double y) {
        // call the default constructor
        this(game);
        this.x = x - getWidth() / 2;
        this.y = y - getHeight();
    }

    /**
     * Handle collision with another object.
     * @param rect the rectangle we collide with
     */
    void collideWith(Rectangle2D rect) {
        if (!this.intersects(rect)) {
            // no overlap - no collision, we don't need to move
            return;
        }

        // get the overlapping part
        Rectangle2D intersection = this.createIntersection(rect);

        // move away from rect on the axis with a smaller difference
        if (intersection.getWidth() < intersection.getHeight()) {
            if (this.x < rect.getX()) {
                x -= intersection.getWidth();
            } else {
                x += intersection.getWidth();
            }
        } else {
            if (this.y < rect.getY()) {
                y -= intersection.getHeight();
                this.onFloor = true;
                vy = 0;
            } else {
                y += intersection.getHeight();
            }
        }
    }

    /**
     * Handle collision with a block.
     * @param block the block
     */
    void collideWith(Block block) {
        if (!(block.state instanceof BlockStateFalling)
            && block.intersects(this)
            && block.getY() + block.getHeight() < this.y) {
            game.app.setScene(new GameOver(game.app, game.gameTime));
        } else {
            this.collideWith((Rectangle2D) block);
        }
    }

    /**
     * Get the column of the currently selected place.
     * @return the column
     */
    int getInteractCol() {
        if (facingRight) {
            return (int) ((x + width - 1) / Block.SIZE) + 1;
        } else {
            return (int) ((x + 1) / Block.SIZE) - 1;
        }
    }

    /**
     * Get the row of the currently selected place.
     * @return the row
     */
    int getInteractRow() {
        return (int) (y / Block.SIZE);
    }

    /**
     * Handle key presses for player controls.
     */
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == (int) 'A') {
            moveLeft = true;
        } else if (e.getKeyCode() == (int) 'D') {
            moveRight = true;
        } else if (e.getKeyCode() == (int) ' ') {
            if (onFloor) {
                vy = -jumpForce;
            }
        } else if (e.getKeyCode() == (int) 'E') {
            int col = getInteractCol();
            int row = getInteractRow();
            Block block = game.grid.getBlockAt(col, row);
            if (heldBlock == null
                && block != null
                && !(block.state instanceof BlockStateRemoved)) {
                // pick up a Block
                heldBlock = block;
                game.grid.putBlockAt(null, col, row);
            } else if (heldBlock != null && block == null
                       && col >= 0 && col < game.grid.getWidth()
                       && row >= 0 && row < game.grid.getHeight()) {
                // place a Block
                game.grid.putBlockAt(heldBlock, col, row);
                game.grid.unstackBlockAt(col, row);
                heldBlock = null;
            }
        } else if (e.getKeyCode() == (int) 'F') {
            // throw a Block if one is held
            if (heldBlock != null) {
                heldBlock.state = new BlockStateThrown(heldBlock);
                game.addEntity(heldBlock);
                heldBlock = null;
            }
        }
    }

    /**
     * Handle key releases for player controls.
     */
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == (int) 'A') {
            moveLeft = false;
        } else if (e.getKeyCode() == (int) 'D') {
            moveRight = false;
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    /**
     * Draw the player at their current position.
     */
    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0));
        g.fillRect((int) x, (int) y, (int) width, (int) height);
    }

    /**
     * Draw player-related UI.
     * @param g graphics context
     */
    public void drawUI(Graphics g) {
        int col = getInteractCol();
        int row = getInteractRow();
        if (heldBlock == null) {
            if (game.grid.getBlockAt(col, row) != null) {
                g.setColor(new Color(255, 255, 255, 240));
                g.drawRect(col * Block.SIZE, row * Block.SIZE, Block.SIZE - 1, Block.SIZE - 1);
            }
        } else {
            heldBlock.draw(g);
            if (game.grid.getBlockAt(col, row) == null
                && col >= 0 && col < game.grid.getWidth()
                && row >= 0 && row < game.grid.getHeight()) {
                g.setColor(new Color(
                    heldBlock.color.getRed(),
                    heldBlock.color.getGreen(),
                    heldBlock.color.getBlue(),
                    190
                ));
                g.fillRect(col * Block.SIZE, row * Block.SIZE, Block.SIZE - 1, Block.SIZE - 1);
            }
        }
    }

    /**
     * Update the player character.
     */
    public void update(double delta) {
        // gravitational acceleration
        vy += game.gravity * mass * delta;

        short moveDir = 0;
        if (moveLeft) {
            moveDir--;
        }
        if (moveRight) {
            moveDir++;
        }

        if (moveDir > 0) {
            facingRight = true;
        } else if (moveDir < 0) {
            facingRight = false;
        }

        vx = moveDir * moveSpeed;

        x += delta * vx;
        y += delta * vy;

        onFloor = false;
        for (Rectangle2D boundary : game.boundaries) {
            this.collideWith(boundary);
        }
        for (Block block : game.grid.getBlocks()) {
            this.collideWith(block);
        }

        if (heldBlock != null) {
            heldBlock.x = x - (Block.SIZE - width) / 2;
            heldBlock.y = y - Block.SIZE;
        }
    }
}
