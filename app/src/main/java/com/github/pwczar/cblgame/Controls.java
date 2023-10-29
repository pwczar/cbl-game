package com.github.pwczar.cblgame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * A scene explaining the game controls and mechanics.
 */
public class Controls extends Scene {
    JButton back = new JButton("BACK");
    JTextPane rules = new JTextPane();


    /**
     * Initialize a Controls scene.
     * @param app the app to work under
     */
    Controls(App app) {
        super(app);

        back.setBackground(Color.BLACK);
        back.setForeground(new Color(243, 121, 7));
        back.setFont(new Font("", Font.BOLD, 25));

        rules.setContentType("text/html");
        rules.setText(
            "<center style='color: white; font-size: 14px;'>"
            + "The ultimate goal of the game is to keep the <b>witches</b>"
            + " from reaching the construction site for as long as possible"
            + " and earning as many points as you can. Here's the basics."
            + "You can move left and right by holding <b>A</b> and <b>D</b>,"
            + " and you can jump by pressing <b>SPACE</b>.<br><br>"
            + "In order to survive the longest, you will have"
            + " to use <b>blocks</b> to your advantage.<br><br>"
            + "You can pick up or place a block by pressing <b>E</b>."
            + " While holding a block, you can also press <b>F</b> to throw it"
            + " towards approaching witches.<br><br>"
            + "Place <b>3</b> blocks of the <b>same color</b> to earn points and,"
            + " if the color is different than gray, gain a power up.<br><br>"
            + "Finally, don't let the blocks reach the top of the screen.<br><br>"
            + "<b>Good luck!</b>"
            + "</center>"
        );
        rules.setEditable(false);
        rules.setBackground(new Color(0, 0, 0, 0));
        rules.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        rules.setMaximumSize(new Dimension(500, app.getHeight() - 100));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        back.setAlignmentX(Component.CENTER_ALIGNMENT);
        rules.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createRigidArea(new Dimension(10, 100)));
        add(back);
        add(Box.createRigidArea(new Dimension(10, 10)));
        add(rules);
    }

    /**
     * Draw a solid background.
     */
    public void draw(Graphics g) {
        g.setColor(new Color(69, 36, 107));
        g.fillRect(0, 0, app.getWidth(), app.getHeight());
    }

    public void run() {
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.setScene(new Menu(app));
            }
        });
    }
}