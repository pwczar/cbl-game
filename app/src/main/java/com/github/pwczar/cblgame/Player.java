package com.github.pwczar.cblgame;

import java.awt.Color;
import java.awt.Graphics;

/**
 * The class representing the player character.
 */
public class Player implements Entity {
    double x = 25;
    double y = 25;

    double vx = 20;
    double vy = 0;

    double w = 64;
    double h = 64;

    /**
     * Draw the player at its current position.
     */
    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0));
        g.fillRect((int) x, (int) y, (int) w, (int) h);
    }

    /**
     * Update the player character.
     */
    public void update(double delta) {
        x += delta * vx;
    }
}
