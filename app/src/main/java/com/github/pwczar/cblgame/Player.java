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
    }
}
