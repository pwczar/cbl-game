package com.github.pwczar.cblgame;

import java.awt.*;
import java.awt.Graphics;
import java.awt.event.*;
import javax.swing.*;

public class Menu implements Scene {
    App app;

    ImageIcon background = new ImageIcon("AABackground");
    JLabel image = new JLabel("", background, JLabel.CENTER);
    //TODO: Add background behind button

    JButton startButton = new JButton("START!");
    
    Menu(App app) {
        this.app = app;
    }

    public void draw(Graphics g) {
        g.clearRect(0, 0, app.frame.getWidth(), app.frame.getHeight());
        app.frame.add(image);
    }

    public void run() {
        app.frame.add(startButton, BorderLayout.NORTH);
        app.updateUI();

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread.currentThread().interrupt();
            }
        });
}

}
