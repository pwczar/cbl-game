package com.github.pwczar.cblgame;

public class JumpUpgrade extends Upgrade {
    JumpUpgrade(Player player) {
        super(player);
        time = 8;
        icon = player.game.loadSprite("jump_boost.png");
    }

    public void addUpgrade() {
        super.addUpgrade();
        player.jumpForce *= 1.3;
    }

    public void removeUpgrade() {
        player.jumpForce /= 1.3;
        super.removeUpgrade();
    }
}
