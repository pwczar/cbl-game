package com.github.pwczar.cblgame;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public class Animation {
    List<Image> frames = new ArrayList<>();
    double fps;
    boolean loop;

    double time = 0;

    Animation(Game game, String[] imagePaths, double fps, boolean loop) {
        for (String path : imagePaths) {
            frames.add(game.loadSprite(path));
        }
        this.fps = fps;
        this.loop = loop;
    }

    Image getFrame() {
        if (time == getDuration()) {
            return frames.get((int) (time * fps) - 1);
        }
        return frames.get((int) (time * fps));
    }

    double getDuration() {
        return frames.size() / fps;
    }

    void restart() {
        time = 0;
    }

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
