package com.github.pwczar.cblgame;

import java.awt.Graphics;
import java.awt.event.*;
import javax.swing.*;

public class Controls extends Scene {
    JButton back = new JButton("BACK");
    JLabel rules = new JLabel("RULES :)");

    Controls(App app) {
        super(app);
    }

    public void draw(Graphics g) {
        g.clearRect(0, 0, app.getWidth(), app.getHeight());
    }

    public void run() {
        add(rules);
        add(back);

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.setScene(new Menu(app));
            }
        });
    }
}