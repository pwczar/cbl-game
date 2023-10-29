/*
 * This Java source file was generated by the Gradle 'init' task.
 */

package com.github.pwczar.cblgame;

import javax.swing.JFrame;

/**
 * Main application class.
 */
public class App extends JFrame {
    private Scene scene;

    // time between frames in miliseconds
    private long interval = (long) (1.0 / 60 * 1000);

    /**
     * Initialize App and create a window.
     */
    App(String title) {
        super(title);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 800);
        // make the window appear at the center of the screen
        setLocationRelativeTo(null);

        setVisible(true);
    }

    /**
     * Change current scene.
     * @param scene scene
     */
    synchronized void setScene(Scene scene) {
        synchronized (scene) {
            if (this.scene != null) {
                synchronized (this.scene) {
                    this.scene.exit();
                    remove(this.scene);
                }
            }

            scene.run();
            this.add(scene);
            this.scene = scene;
            requestFocus();
        }
    }

    public static void main(String[] args) {
        App app = new App("CBL Game");
        app.setScene(new Menu(app));


        // periodically update the scene in another thread
        new Thread() {
            public void run() {
                long time = System.currentTimeMillis();
                while (true) {
                    long now = System.currentTimeMillis();
                    // time between now and the last frame
                    double delta = (now - time) / 1000.0;
                    time = now;

                    if (app.scene != null) {
                        synchronized (app.scene) {
                            app.scene.update(delta);
                            app.scene.updateUI();
                        }
                    }

                    try {
                        Thread.sleep(app.interval);
                    } catch (InterruptedException e) {
                        app.scene.exit();
                    }
                }
            }
        }.start();
    }
}
