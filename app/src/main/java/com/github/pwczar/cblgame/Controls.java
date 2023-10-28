package com.github.pwczar.cblgame;

import java.awt.*;
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
        back.setBackground(new Color(76, 9, 110));
        back.setForeground(new Color(243, 121, 7));
        back.setFont(new Font("", Font.BOLD, 25));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        back.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createRigidArea(new Dimension(10, app.getHeight() / 4)));
        add(back);
        add(Box.createRigidArea(new Dimension(10, 10)));
        add(rules);

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.setScene(new Menu(app));
            }
        });
    }
}