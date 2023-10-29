package com.github.pwczar.cblgame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Controls extends Scene {
    JButton back = new JButton("BACK");
    JTextArea rules = new JTextArea("Ultimate goal of the game is to keep the witches"
            + " from reaching the construction site. \n"
            + "In order to do that, Player has to use blocks to his advantage. Use 'WASD' to move,"
            + " 'SPACE' to jump and 'E'"
            + " to pick up/put down the block.\n"
            + "While picked up, use 'F' to throw held block towards"
            + " approaching witches.\nStack of 3 same colored blocks give points,"
            + " and if a block is of color other than grey, different power ups.");

    Controls(App app) {
        super(app);
    }

    public void draw(Graphics g) {
        g.setColor(new Color(69, 36, 107));
        g.fillRect(0, 0, app.getWidth(), app.getHeight());
    }

    public void run() {
        back.setBackground(Color.BLACK);
        back.setForeground(new Color(243, 121, 7));
        back.setFont(new Font("", Font.BOLD, 25));

        rules.setBackground(new Color(69, 36, 107));
        rules.setForeground(new Color(243, 121, 7));
        rules.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        rules.setLineWrap(true);
        rules.setWrapStyleWord(true);
        rules.setFont(new Font("", Font.BOLD, 15));
        rules.setMaximumSize(new Dimension(500, 400));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        back.setAlignmentX(Component.CENTER_ALIGNMENT);
        rules.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createRigidArea(new Dimension(10, app.getHeight() / 4)));
        add(back);
        add(Box.createRigidArea(new Dimension(10, 10)));
        rules.setEditable(false);
        add(rules);

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.setScene(new Menu(app));
            }
        });
    }
}