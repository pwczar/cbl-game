package com.github.pwczar.cblgame;

public class MovespeedUpgrade implements Upgrade {

    double time = 10;
    Player player;

    MovespeedUpgrade(Player player) {
        this.player = player;
        addUpgrade();
    }

    public void addUpgrade() {
        player.moveSpeed *= 1.1;
    }

    public void removeUpgrade() {
        player.moveSpeed /= 1.1;
    }

    public void update(double delta) {
        time -= delta;
        if (time <= 0) {
            removeUpgrade();
        }

    }
}