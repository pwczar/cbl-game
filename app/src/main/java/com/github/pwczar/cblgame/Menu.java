package com.github.pwczar.cblgame;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class Menu extends Scene {

    // TODO: Add background behind button

    JButton startButton = new JButton("Start game");
    JButton controlsButton = new JButton("Help");

    ArrayList<Pumpkin> pumpkins = new ArrayList<>();

    Menu(App app) {
        super(app);
    }

    public void draw(Graphics g) {
        g.setColor(new Color(69, 36, 107));
        g.fillRect(0, 0, app.getWidth(), app.getHeight());

        for (int i = 0; i < 50; i++) {
            pumpkins.get(i).draw(g);
        }
    }

    public void update(double delta) {
        for (int i = 0; i < 50; i++) {
            pumpkins.get(i).update(delta);
        }
    }

    public void run() {

        for (int i = 0; i < 50; i++) {
            Pumpkin n = new Pumpkin(this);
            pumpkins.add(n);
        }

        startButton.setBackground(Color.BLACK);
        startButton.setForeground(new Color(243, 121, 7));
        // startButton.setPreferredSize(new Dimension(160, 512));
        startButton.setFont(new Font("", Font.BOLD, 25));
        controlsButton.setFont(new Font("", Font.BOLD, 25));

        controlsButton.setBackground(Color.BLACK);
        controlsButton.setForeground(new Color(243, 121, 7));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        controlsButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createRigidArea(new Dimension(10, app.getHeight() / 4)));
        add(startButton);
        add(Box.createRigidArea(new Dimension(10, 10)));
        add(controlsButton);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.setScene(new Game(app));
            }
        });

        controlsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.setScene(new Controls(app));
            }
        });
    }
}
