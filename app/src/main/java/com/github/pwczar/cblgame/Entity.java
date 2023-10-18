package com.github.pwczar.cblgame;

import java.awt.Graphics;

/**
 * A common class for all game objects.
 */
public interface Entity {
    /**
     * Draw entity.
     */
    default void draw(Graphics g) {
        // Draw nothing by default
    }

    /**
     * Update entity's game logic.
     * @param delta time since the last frame (in seconds)
     */
    default void update(double delta) {
        // Do nothing by default
    }
}
