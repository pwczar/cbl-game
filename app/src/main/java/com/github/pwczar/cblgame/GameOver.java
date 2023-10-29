package com.github.pwczar.cblgame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameOver extends Scene {
    JButton playAgain = new JButton("PLAY AGAIN!");
    double gameTime;
    JLabel stats = new JLabel();
    Game game;
    Image lastFrame;


    GameOver(App app, Game game, double gameTime) {
        super(app);
        this.game = game;
        this.gameTime = Math.round(gameTime);
    }

    public void draw(Graphics g) {
        g.clearRect(0, 0, app.getWidth(), app.getHeight());
        lastFrame = game.lastFrame;

        g.drawImage(
            lastFrame.getScaledInstance(app.getWidth(), app.getHeight(), Image.SCALE_SMOOTH),
            0, 0, null
        );
    }

    public void update(double delta) {
    }

    public void run() {
        playAgain.setBackground(Color.BLACK);
        playAgain.setForeground(new Color(243, 121, 7));
        playAgain.setFont(new Font("", Font.BOLD, 25));
        stats.setText("You survived " + gameTime + " s" + " and scored " + game.score + " points");
        stats.setFont(new Font("", Font.BOLD, 20));
        stats.setForeground(Color.WHITE);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        playAgain.setAlignmentX(Component.CENTER_ALIGNMENT);
        stats.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createRigidArea(new Dimension(10, app.getHeight() / 4)));
        add(playAgain);
        add(Box.createRigidArea(new Dimension(10, 10)));
        add(stats);

        playAgain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.setScene(new Game(app));
            }
        });
    }


}