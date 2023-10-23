package com.github.pwczar.cblgame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Menu implements Scene {
    App app;

    ImageIcon background = new ImageIcon("AABackground.jpg");
    Image image = background.getImage().getScaledInstance(500, 800, Image.SCALE_SMOOTH);
    JLabel imageLabel;

    //TODO: Add background behind button

    JButton startButton = new JButton("START!");
    JButton controlsButton = new JButton("Controls");

    Menu(App app) {
        this.app = app;
    }

    public void draw(Graphics g) {
        g.clearRect(0, 0, app.frame.getWidth(), app.frame.getHeight());
    }

    public void update(double delta) {
    }

    public void run() {
        /*
        background.setImage(image);
        imageLabel = new JLabel("", background, JLabel.CENTER);
        app.frame.add(imageLabel, BorderLayout.CENTER);
        */
        app.add(startButton, BorderLayout.NORTH);
        app.add(controlsButton, BorderLayout.SOUTH);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.setScene(new Game(app));
                return;
            }
        });

        controlsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.setScene(new Controls(app));
                return;
            }
        });
    }
}
