package com.github.pwczar.cblgame;

import java.awt.Image;

/**
 * A Block factory.
 */
public class BlockFactory extends EntityFactory {
    final BlockGrid grid;

    Image[] sprites;

    /**
     * Initialize BlockFactory.
     * @param game game
     * @param grid block grid
     */
    BlockFactory(Game game, BlockGrid grid) {
        super(game);
        this.grid = grid;
        spawnInterval = 6;
        sprites = new Image[] {
            game.loadSprite("bricks_green.png"),
            game.loadSprite("bricks_red.png"),
            game.loadSprite("bricks_blue.png"),
            game.loadSprite("bricks.png")
        };
    }

    /**
     * Create a new Block.
     * @return the new block
     */
    Block create() {
        int type = rand.nextInt(4);
        Block block = new Block(
            game,
            grid,
            rand.nextInt(grid.getWidth()) * Block.SIZE,
            0,
            type,
            sprites[type]
        );

        return block;
    }

    /**
     * Spawn a new Block.
     */
    void spawn() {
        grid.addBlock(create());
    }
}
