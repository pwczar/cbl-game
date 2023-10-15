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
    Player() {
        x = 25;
        y = 25;
        width = 64;
        height = 64;
    }

    /**
     * Move to the closest location where we won't overlap with rect.
     * @param rect the rectangle we want to 'escape'
     */
    void moveFromRect(Rectangle2D rect) {
        if (!this.intersects(rect)) {
            // no overlap, we don't need to move
            return;
        }

        Rectangle2D intersection = this.createIntersection(rect);

        // move on the axis with a smaller difference
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
            } else {
                y += intersection.getHeight();
            }
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
    public void draw(Game game, Graphics g) {
        g.setColor(new Color(0, 0, 0));
        g.fillRect((int) x, (int) y, (int) width, (int) height);
    }

    /**
     * Update the player character.
     */
    public void update(Game game, double delta) {
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
        // TODO: collide with the environment (blocks) from `game`
    }
}
