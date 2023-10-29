package com.github.pwczar.cblgame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import javax.swing.*;

/**
 * A falling Pumpkin.
 */
public class Pumpkin extends Rectangle2D.Double implements Entity {

    static Random rand = new Random(System.currentTimeMillis());

    final Menu menu;

    double vy;

    Image sprite;

    /**
     * Initialize a Pumpkin.
     * @param menu the Pumpkin's scene
     */
    Pumpkin(Menu menu) {
        this.menu = menu;
        vy = rand.nextInt(30) + 50;;
        x = rand.nextInt(menu.app.getWidth());
        y = rand.nextInt(menu.app.getHeight());
        height = 12;
        width = 12;
        sprite = new ImageIcon(
            getClass().getClassLoader().getResource("pumpkin.png"))
            .getImage().getScaledInstance(36, 36, Image.SCALE_SMOOTH);
    }

    public void draw(Graphics g) {
        g.drawImage(sprite, (int) x, (int) y, null);
    }

    /**
     * Update the pumpkin's position.
     */
    public void update(double delta) {
        y += vy * delta;

        if (this.y >= menu.app.getHeight()) {
            y = -sprite.getHeight(null);
            x = rand.nextInt(menu.app.getWidth());
            vy = rand.nextInt(30) + 50;
        }
    }
}