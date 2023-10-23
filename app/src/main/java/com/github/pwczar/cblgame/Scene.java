package com.github.pwczar.cblgame;

import java.awt.Graphics;

/**
 * A class representing a drawable scene.
 */
public interface Scene extends Runnable {
    void draw(Graphics g);

    void update(double delta);

    /**
     * Do the scene's cleanup.
     */
    default void stop() {

    }
}
