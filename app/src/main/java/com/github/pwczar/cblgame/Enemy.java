package com.github.pwczar.cblgame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.util.Random;

/**
 * An Enemy which moves across the screen and attacks the player.
 */
public class Enemy extends Rectangle2D.Double implements Entity {
    static Random rand = new Random(System.currentTimeMillis());

    final Game game;

    double vy;
    double offset;

    Image sprite;
    boolean flip = false;

    /**
     * Initialize an Enemy at x, y.
     * @param game the game to work under
     * @param x x coordinate
     * @param y y coordinate
     */
    Enemy(Game game, double x, double y, double offset) {
        this.game = game;
        vy = 20;
        this.x = x;
        this.y = y;
        height = 12;
        width = 12;
        this.offset = offset;
        sprite = game.loadSprite("witch.png");
    }

    /**
     * Draw an Enemy.
     */
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

    /**
     * Update an Enemy.
     */
    public void update(double delta) {
        // die from thrown Blocks
        for (Entity ent : game.getEntities()) {
            if (ent instanceof Block
                && ((Block) ent).intersects(this)) {
                game.removeEntity((Block) ent);
                game.removeEntity(this);
                return;
            }
        }

        // destroy stacked Blocks
        for (Block block : game.grid.getBlocks()) {
            if (block.state instanceof BlockStateStacked
                && block.intersects(this)) {
                block.state = new BlockStateDestroyed(block);
                game.removeEntity(this);
                return;
            }
        }

        if (intersects(game.floor) || intersects(game.player)) {
            game.removeEntity(this);
            game.player.hp -= 1;
            return;
        }

        y += delta * vy;
        double yy = y / 20;
        x = (game.getGameWidth() - width + 1) * (Math.sin(yy + offset) + 1) * 0.5;
        flip = Math.cos(yy + offset) < 0;
    }
}