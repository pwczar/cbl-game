package com.github.pwczar.cblgame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import javax.swing.*;

public class Pumpkin extends Rectangle2D.Double implements Entity {

    static Random rand = new Random(System.currentTimeMillis());
    static final int SIZE = 12;

    final Menu menu;

    double vy;

    Image sprite;

    Pumpkin(Menu menu, double x, double y) {
        this.menu = menu;
        vy = 20;
        this.x = x;
        this.y = y;
        height = SIZE;
        width = SIZE;
        sprite = new ImageIcon("pumpkin.png").getImage();
    }

    public void draw(Graphics g) {
        g.drawImage(sprite, (int) x, (int) y, null);
    }

    public void update(double delta) {
        y += vy * delta;

        if (this.y >= menu.getHeight()) {
            y = -sprite.getHeight(null);
        }
    }
}