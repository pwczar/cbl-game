package com.github.pwczar.cblgame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameOver extends Scene {
    JButton playAgain = new JButton("PLAY AGAIN!");
    double gameTime;
    JLabel stats;

    //TODO: ADD POST-GAME STATS

    GameOver(App app, double gameTime) {
        super(app);
        this.gameTime = gameTime;
    }

    public void draw(Graphics g) {
        g.clearRect(0, 0, app.getWidth(), app.getHeight());
    }

    public void update(double delta) {
    }

    public void run() {
        add(playAgain);
        stats = new JLabel();
        stats.setText("You survived " + gameTime + " s");
        add(stats);

        playAgain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.setScene(new Game(app));
            }
        });
    }


}