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
        for (Entity ent : game.getEntities()) {
            if (ent instanceof Block) {
                if (((Block) ent).intersects(this)) {
                    Block block = (Block) ent;
                    game.removeEntity(block);
                    game.removeEntity(this);
                }
            }
        }

        for (Block block : game.grid.getBlocks()) {
            if (block.state instanceof BlockStateStacked
                    && block.intersects(this)) {
                block.state = new BlockStateDestroyed(block);
                game.removeEntity(this);
            }
        }
        if (intersects(game.floor)) {
            game.removeEntity(this);
            game.player.hp -= 1;
            return;
        } else {
            y += delta * vy;
            double yy = y / 10;
            x += 3 * Math.sin(yy);

            for (Rectangle2D b : game.boundaries) {
                if (!this.intersects(b)) {
                    continue;
                } else {
                    x -= 3 * Math.sin(yy);
                }
            }

        }
    }
}