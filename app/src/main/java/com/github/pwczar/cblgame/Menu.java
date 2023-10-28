package com.github.pwczar.cblgame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Menu extends Scene {
    ImageIcon background = new ImageIcon(getClass().getClassLoader().getResource("AABackground.jpg"));
    Image image = background.getImage().getScaledInstance(500, 800, Image.SCALE_SMOOTH);
    JLabel imageLabel;

    // TODO: Add background behind button

    JButton startButton = new JButton("Start game");
    JButton controlsButton = new JButton("Help");

    Menu(App app) {
        super(app);
    }

    public void draw(Graphics g) {
        g.clearRect(0, 0, app.getWidth(), app.getHeight());
        g.drawImage(image, 0, 0, null);
    }

    public void update(double delta) {
    }

    public void run() {

        startButton.setBackground(new Color(76, 9, 110));
        startButton.setForeground(new Color(243, 121, 7));
        //startButton.setPreferredSize(new Dimension(160, 512));
        startButton.setFont(new Font("", Font.BOLD, 25));
        controlsButton.setFont(new Font("", Font.BOLD, 25));
        

        controlsButton.setBackground(new Color(76, 9, 110));
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
