package com.github.pwczar.cblgame;

public abstract class Upgrade {
    final Player player;
    double time = 0;

    Upgrade(Player player) {
        this.player = player;
    }

    void addUpgrade() {
        player.upgrades.add(this);
    }

    void removeUpgrade() {
        player.upgrades.remove(this);
    }

    void update(double delta) {
        time -= delta;
        if (time <= 0) {
            removeUpgrade();
        }
    }
}
