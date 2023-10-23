package com.github.pwczar.cblgame;

import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * A class representing a drawable scene.
 */
public abstract class Scene extends JPanel implements Runnable {
    final App app;

    Scene(App app) {
        this.app = app;
    }

    public void draw(Graphics g) {
    }

    final public void paintComponent(Graphics g) {
        draw(g);
    }

    public void update(double delta) {
    }

    /**
     * Do the scene's cleanup.
     */
    void exit() {

    }
}
