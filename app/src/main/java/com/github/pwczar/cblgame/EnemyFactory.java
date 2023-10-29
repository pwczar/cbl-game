package com.github.pwczar.cblgame;

/**
 * An EnemyFactory.
 * Creates randomized Enemies and adds them as Entities.
 */
public class EnemyFactory extends EntityFactory {
    /**
     * Initialize an EnemyFactory.
     * @param game the game to work under
     */
    EnemyFactory(Game game) {
        super(game);
        spawnInterval = 20;
    }

    /**
     * Create a new Enemy.
     */
    Enemy create() {
        Enemy en = new Enemy(
            game,
            rand.nextInt(game.getGameWidth()),
            -24,
            rand.nextInt(0, 20)
        );
        return en;
    }

    /**
     * Spawn an Enemy.
     */
    void spawn() {
        game.addEntity(create());
    }
}
