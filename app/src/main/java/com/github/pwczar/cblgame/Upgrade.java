package com.github.pwczar.cblgame;

import java.awt.Image;

/**
 * Main class of Upgrades.
 */
public abstract class Upgrade {
    final Player player;
    double time = 0;
    Image icon;

    Upgrade(Player player) {
        this.player = player;
    }

    void addUpgrade() {
        player.upgrades.add(this);
    }

    void removeUpgrade() {
        player.upgrades.remove(this);
    }

    /**
     * Updating upgrade uptime.
     * @param delta time in seconds
     */
    void update(double delta) {
        time -= delta;
        if (time <= 0) {
            removeUpgrade();
        }
    }
}
