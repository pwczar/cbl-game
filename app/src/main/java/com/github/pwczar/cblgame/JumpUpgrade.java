package com.github.pwczar.cblgame;

public class JumpUpgrade extends Upgrade {
    JumpUpgrade(Player player) {
        super(player);
        time = 3;
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
