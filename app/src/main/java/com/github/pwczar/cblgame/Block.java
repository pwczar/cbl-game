package com.github.pwczar.cblgame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Block extends Rectangle2D.Double implements Entity {
    boolean stopped;
    double vy;
    static Random rand = new Random(System.currentTimeMillis());

    //Always the same number generated?
     
    public Block() {
        x = rand.nextInt(7) * 64;
        y = 25;
        vy = rand.nextInt(50) + 100;
        width = 64;
        height = 64;
        stopped = false;
    }
    
    public void draw(Game game, Graphics g) {
        g.setColor(new Color(100, 50, 200));
        g.fillRect((int) x, (int) y, (int) width, (int) height);
    }

    public void update(Game game, double delta) {
        if (y < 700 && !stopped) {
            y += delta * vy;
        } else {
            stopped = true;
        }
        for (int i = 0; i < game.blocks.size(); i++) {
            Block other = game.blocks.get(i);
            if (this == other) {
                continue;
            } 
            if (this.intersects(other) && other.stopped) {
                this.y = other.y - this.height;
            }
        }
    
    }
    
}
