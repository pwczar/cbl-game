package com.github.pwczar.cblgame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

/**
 * The class representing the player character.
 */
public class Player extends Rectangle2D.Double implements Entity {

    double vx = 0;
    double vy = 0;

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
            } else {
                y += intersection.getHeight();
            }
        }
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
        vy += 60 * delta;

        x += delta * vx;
        y += delta * vy;
    }
}
