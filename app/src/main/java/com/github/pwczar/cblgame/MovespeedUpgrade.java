package com.github.pwczar.cblgame;

public class MovespeedUpgrade extends Upgrade {
    MovespeedUpgrade(Player player) {
        super(player);
        time = 8;
        icon = player.game.loadSprite("hermes.png");
    }

    public void addUpgrade() {
        super.addUpgrade();
        player.moveSpeed *= 1.1;
    }

    public void removeUpgrade() {
        player.moveSpeed /= 1.1;
        super.removeUpgrade();
    }
}
