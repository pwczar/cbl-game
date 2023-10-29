package com.github.pwczar.cblgame;

/**
 * Jump upgrade class.
 */
public class JumpUpgrade extends Upgrade {

    /**
     * Initialize.
     * @param player player which receives upgrade
     */
    JumpUpgrade(Player player) {
        super(player);
        time = 8;
        icon = player.game.loadSprite("jump_boost.png");
    }

    /**
     * Add upgrade to player.
     */
    public void addUpgrade() {
        super.addUpgrade();
        player.jumpForce *= 1.7;
    }

    /**
     * Remove upgrade from player.
     */
    public void removeUpgrade() {
        player.jumpForce /= 1.7;
        super.removeUpgrade();
    }
}
