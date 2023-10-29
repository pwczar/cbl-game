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
            + "The ultimate goal of the game is to keep the witches"
            + " from reaching the construction site for as long as possible"
            + " and earning as many points as you can."
            + " In order to do that, the <b>player</b> has"
            + " to use the blocks to his advantage.<br><br>"
            + "Press <b>E</b> to pick up or place a block."
            + " While holding a block, press <b>F</b> to throw it"
            + " towards approaching witches.<br><br>"
            + "Place 3 blocks the same color to earn points and,"
            + " if the color is different than gray, a power up.<br><br>"
            + "Finally, hold <b>A</b> and <b>D</b> to move left and right,"
            + " and press <b>SPACE</b> to jump."
            + "</center>"
        );
        rules.setEditable(false);
        rules.setBackground(new Color(0, 0, 0, 0));
        rules.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        rules.setMaximumSize(new Dimension(500, 400));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        back.setAlignmentX(Component.CENTER_ALIGNMENT);
        rules.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createRigidArea(new Dimension(10, app.getHeight() / 4)));
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