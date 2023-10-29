package com.github.pwczar.cblgame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * A game over scene.
 */
public class GameOver extends Scene {
    JButton playAgainButton = new JButton("PLAY AGAIN!");
    JTextArea statsArea = new JTextArea();
    JButton menuButton = new JButton("Back to menu");

    Game game;

    /**
     * Initialize a game over screen.
     *
     * @param app  the app to work under
     * @param game the game to show data from
     */
    GameOver(App app, Game game) {
        super(app);
        this.game = game;

        playAgainButton.setBackground(Color.BLACK);
        playAgainButton.setForeground(new Color(243, 121, 7));
        playAgainButton.setFont(new Font("", Font.BOLD, 25));

        menuButton.setBackground(Color.BLACK);
        menuButton.setForeground(new Color(243, 121, 7));
        menuButton.setFont(new Font("", Font.BOLD, 25));

        statsArea.setText(String.format(
                "You have survived %.2fs and scored %d points",
                game.gameTime, game.score));
        statsArea.setEditable(false);
        statsArea.setFont(new Font("", Font.BOLD, 20));
        statsArea.setForeground(Color.WHITE);
        statsArea.setBackground(new Color(0, 0, 0, 0));
        statsArea.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,
                (app.getWidth() - (int) (game.getGameWidth() * Game.SCALE)) / 2,
                0,
                (app.getWidth() - (int) (game.getGameWidth() * Game.SCALE)) / 2));
        statsArea.setLineWrap(true);
        statsArea.setWrapStyleWord(true);
        statsArea.setMaximumSize(getMaximumSize());

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        playAgainButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        statsArea.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createRigidArea(new Dimension(10, app.getHeight() / 4)));
        add(playAgainButton);
        add(Box.createRigidArea(new Dimension(10, 10)));
        add(menuButton);
        add(Box.createRigidArea(new Dimension(10, 10)));
        add(statsArea);
    }

    /**
     * Draw the last game frame in the background.
     */
    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, app.getWidth(), app.getHeight());
        int width = (int) (game.getGameWidth() * Game.SCALE);
        int height = (int) (game.getGameHeight() * Game.SCALE);
        g.drawImage(
            game.lastFrame.getScaledInstance(
                width,
                height,
                Image.SCALE_SMOOTH
            ),
            (app.getWidth() - width) / 2, 0, null
        );
    }

    /**
     * Update the GameOver scene.
     */
    public void update(double delta) {
        statsArea.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,
                (app.getWidth() - (int) (game.getGameWidth() * Game.SCALE)) / 2,
                0,
                (app.getWidth() - (int) (game.getGameWidth() * Game.SCALE)) / 2));
    }

    public void run() {
        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.setScene(new Game(app));
            }
        });

        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.setScene(new Menu(app));
            }
        });
    }
}
