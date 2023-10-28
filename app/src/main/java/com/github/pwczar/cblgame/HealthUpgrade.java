package com.github.pwczar.cblgame;

public class HealthUpgrade extends Upgrade {
    HealthUpgrade(Player player) {
        super(player);
    }

    public void addUpgrade() {
        if (player.hp < 5) {
            player.hp++;
        }
    }

    public void removeUpgrade() {
        // we don't have to do anything
    }
}