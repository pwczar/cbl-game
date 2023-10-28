package com.github.pwczar.cblgame;

public class InvincibilityUpgrade implements Upgrade {

    Player player;
    double hp;
    double time = 10;

    InvincibilityUpgrade(Player player, double hp) {
        this.player = player;
        this.hp = hp;
    }

    public void addUpgrade() {
        player.hp = Double.POSITIVE_INFINITY;
    }

    public void removeUpgrade() {
        player.hp = this.hp;
    }

    public void update(double delta) {
        time -= delta;
        if (time <= 0) {
            removeUpgrade();
        }
    }
}