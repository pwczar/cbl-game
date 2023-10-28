package com.github.pwczar.cblgame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Enemy extends Rectangle2D.Double implements Entity {
    static Random rand = new Random(System.currentTimeMillis());
    static final int SIZE = 12;

    final Game game;

    double vy;

    Image sprite;
    boolean flip = false;

    Enemy(Game game, double x, double y) {
        this.game = game;
        vy = 20;
        this.x = x;
        this.y = y;
        height = SIZE;
        width = SIZE;
        sprite = game.loadSprite("witch.png");
    }

    public void draw(Graphics g) {
        if (!flip) {
            g.drawImage(sprite, (int) x, (int) y, null);
        } else {
            // flip the sprite
            g.drawImage(
                sprite,
                (int) x + sprite.getWidth(null),
                (int) y,
                -sprite.getWidth(null),
                sprite.getHeight(null),
                null
            );
        }
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
            double yy = y / 20;
            x = (game.getGameWidth() - SIZE + 1) * (Math.sin(yy) + 1) * 0.5;
            flip = Math.cos(yy) < 0;
        }
    }
}