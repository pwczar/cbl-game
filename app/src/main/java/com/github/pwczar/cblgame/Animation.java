package com.github.pwczar.cblgame;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

/**
 * An Animation consisting of several frames.
 */
public class Animation {
    List<Image> frames = new ArrayList<>();
    double fps;
    boolean loop;

    double time = 0;

    /**
     * Initialize an Animation.
     * @param game the game to depend on
     * @param imagePaths paths to images containing animation frames
     * @param fps frames per second
     * @param loop whether the animation should loop
     */
    Animation(Game game, String[] imagePaths, double fps, boolean loop) {
        for (String path : imagePaths) {
            frames.add(game.loadSprite(path));
        }
        this.fps = fps;
        this.loop = loop;
    }

    /**
     * Get the current frame.
     * @return the frame Image
     */
    Image getFrame() {
        if (time == getDuration()) {
            return frames.get((int) (time * fps) - 1);
        }
        return frames.get((int) (time * fps));
    }

    /**
     * Get Animation's duration.
     * @return the duration in seconds
     */
    double getDuration() {
        return frames.size() / fps;
    }

    /**
     * Restart the animation (go back to the first frame).
     */
    void restart() {
        time = 0;
    }

    /**
     * Update the Animation.
     * @param delta time since the last update
     */
    void update(double delta) {
        time += delta;
        if (time > getDuration()) {
            if (loop) {
                time -= getDuration();
            } else {
                time = getDuration();
            }
        }
    }
}
