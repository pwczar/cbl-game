package com.github.pwczar.cblgame;

import java.awt.*;
import java.awt.Graphics;
import java.awt.event.*;
import javax.swing.*;

public class Controls implements Scene {
    App app;
    JButton back = new JButton("BACK");
    JTextField howtoplay = new JTextField("HERE SHOULD BE CONTROLS EXPLAINED");

    Controls(App app) {
        this.app = app;
    }

    public void draw(Graphics g) {
        g.clearRect(0, 0, app.frame.getWidth(), app.frame.getHeight());
    }

    public void run() {
        app.frame.add(howtoplay, BorderLayout.CENTER);
        app.frame.add(back, BorderLayout.SOUTH);
        app.updateUI();

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.setScene(new Menu(app));
                return;
            }
        });


    }

    public void update(double delta) {
    }
}