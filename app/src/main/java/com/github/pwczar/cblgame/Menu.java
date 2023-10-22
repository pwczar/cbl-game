package com.github.pwczar.cblgame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.google.errorprone.annotations.OverridingMethodsMustInvokeSuper;

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

    public void run() {
        /* 
        background.setImage(image);
        imageLabel = new JLabel("", background, JLabel.CENTER);
        app.frame.add(imageLabel, BorderLayout.CENTER);
        */
        app.frame.add(startButton, BorderLayout.NORTH);
        app.frame.add(controlsButton, BorderLayout.SOUTH);
        app.updateUI();

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.frame.remove(startButton);
                Thread.currentThread().interrupt();
                app.setScene(new Game(app));
                return;
            }
        });

        controlsButton.addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                app.frame.remove(startButton);
                app.frame.remove(controlsButton);
                Thread.currentThread().interrupt();
                app.setScene(new Controls(app));
                return;
            }
        });
}

}
