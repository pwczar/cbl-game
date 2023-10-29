package com.github.pwczar.cblgame;

/**
 * Health upgrade class.
 */
public class HealthUpgrade extends Upgrade {
    HealthUpgrade(Player player) {
        super(player);
    }

    /**
     * Add upgrade to player's hp.
     */
    public void addUpgrade() {
        if (player.hp < 5) {
            player.hp++;
        }
    }

    public void removeUpgrade() {
        // we don't have to do anything
    }
}