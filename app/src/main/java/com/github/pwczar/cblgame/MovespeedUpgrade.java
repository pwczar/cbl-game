package com.github.pwczar.cblgame;

/**
 * Movement speed upgrade class.
 */
public class MovespeedUpgrade extends Upgrade {

    /**
     * Initialize.
     * @param player player which receives upgrade.
     */
    MovespeedUpgrade(Player player) {
        super(player);
        time = 8;
        icon = player.game.loadSprite("hermes.png");
    }

    /**
     * Add upgrade to player.
     */
    public void addUpgrade() {
        super.addUpgrade();
        player.moveSpeed *= 1.1;
    }

    /**
     * Remove upgrade from player.
     */
    public void removeUpgrade() {
        player.moveSpeed /= 1.1;
        super.removeUpgrade();
    }
}
