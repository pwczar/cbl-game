package com.github.pwczar.cblgame;

public class HealthUpgrade implements Upgrade {

    Player player;

    HealthUpgrade(Player player) {
        this.player = player;
        addUpgrade();
    }
    public void addUpgrade() {
        if (player.hp < 5) {
            player.hp++;
        }
    }

    public void removeUpgrade() {
    }

    public void update(double delta) {
        
    }
}