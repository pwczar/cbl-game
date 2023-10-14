package com.github.pwczar.cblgame;

import java.awt.Graphics;

/**
 * A common class for all game objects.
 */
public interface Entity {
    /**
     * Draw entity.
     */
    public void draw(Graphics g);

    /**
     * Update entity's game logic.
     * @param delta time since the last frame (in seconds)
     */
    public void update(double delta);
}
