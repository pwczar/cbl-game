package com.github.pwczar.cblgame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Enemy extends Rectangle2D.Double implements Entity {
    static Random rand = new Random(System.currentTimeMillis());
    static final int SIZE = 32;

    final Game game;

    double vy;
    
    Enemy(Game game, double x, double y) {
        this.game = game;
        vy = 30;
        this.x = x;
        this.y = y;
        height = SIZE;
        width = SIZE;
    }

    public void draw(Graphics g) {
        g.setColor(new Color(100, 50, 200));
        g.fillRect((int) x, (int) y, (int) width, (int) height);
    }

    public void update(double delta) {
        if (intersects(game.floor)) {
            // player loses hp
            return;
        } else {
            y += delta * vy;
            double yy = y/10;
            x += 3*Math.sin(yy);
        }
    }
}