package com.github.pwczar.cblgame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.ImageIcon;

/**
 * Main game logic thread.
 */
public class Game extends Scene {
    static final double SCALE = 4.0;

    private List<Entity> entities = new ArrayList<>();

    Player player;
    double gameTime = 0;
    int score = 0;

    // TODO: rewrite as a class implementing Entity
    Rectangle2D[] boundaries;
    Rectangle2D floor;
    final int floorOffset;
    Image lastFrame;

    BlockGrid grid;

    EnemyFactory enemyFactory = new EnemyFactory(this);

    /**
     * Initialize.
     * @param app the app to run under
     */
    Game(App app) {
        super(app);

        floorOffset = getGameHeight()
            - (getGameHeight() / Block.SIZE) * Block.SIZE + Block.SIZE * 2;

        boundaries = new Rectangle2D[] {
            // floor
            new Rectangle2D.Double(
                0,
                getGameHeight() - floorOffset,
                getGameWidth(),
                128
            ),
            // left boundary
            new Rectangle2D.Double(0 - 128, 0, 128, getGameHeight()),
            // right boundary
            new Rectangle2D.Double(getGameWidth(), 0, 128, getGameHeight()),
        };
        floor = boundaries[0];

        player = new Player(this, getGameWidth() / 2, floor.getY());

        grid = new BlockGrid(
            this,
            getGameWidth() / Block.SIZE,
            (getGameHeight() - floorOffset) / Block.SIZE
        );

        addEntity(player);
        addEntity(grid);
    }

    int getGameWidth() {
        // constant, not dependent on window width
        // so that game-play is not affected
        return Block.SIZE * 10;
    }

    int getGameHeight() {
        // constant, not dependent on window height,
        // same reason as width
        return Block.SIZE * 16;
    }

    Image loadSprite(String path) {
        return new ImageIcon(getClass().getClassLoader().getResource(path)).getImage();
    }

    /**
     * Add an Entity to the game.
     * @param ent the Entity to add
     */
    public void addEntity(Entity ent) {
        if (entities.indexOf(ent) != -1) {
            return;
        }
        entities.add(ent);
    }

    /**
     * Remvoe an Entity from the game.
     * @param ent the Entity to remove
     */
    public void removeEntity(Entity ent) {
        if (ent == player || ent == grid) {
            throw new IllegalArgumentException("This entity cannot be removed.");
        }
        entities.remove(ent);
    }

    public List<Entity> getEntities() {
        return Collections.unmodifiableList(new ArrayList<>(entities));
    }

    /**
     * Draw the game.
     * @param g graphics context
     */
    public void draw(Graphics g) {
        Image buffer = new BufferedImage(
            getGameWidth(),
            getGameHeight(),
            BufferedImage.TYPE_INT_ARGB
        );
        Graphics bg = buffer.getGraphics();

        bg.setColor(new Color(0, 0, 0));
        bg.fillRect(
            0,
            0,
            getGameWidth(),
            getGameHeight()
        );

        // draw the grid first
        grid.draw(bg);

        // draw all other entities
        for (Entity ent : getEntities()) {
            if (ent == grid) {
                continue;
            }
            ent.draw(bg);
        }

        bg.setColor(new Color(107, 107, 107));
        bg.fillRect(0, getGameHeight() - floorOffset, getGameWidth(), floorOffset);

        player.drawUI(bg);

        // set font for UI
        bg.setFont(new Font("", Font.PLAIN, 10));

        // draw game time
        bg.setColor(new Color(255, 255, 255));
        String str = String.format("%.2fs", gameTime);
        Rectangle2D bounds = bg.getFont().getStringBounds(
            str, bg.getFontMetrics().getFontRenderContext()
        );
        bg.drawString(
            str,
            (int) (getGameWidth() - bounds.getWidth() - 6),
            (int) bounds.getHeight()
        );

        str = Integer.toString(score);
        bounds = bg.getFont().getStringBounds(str, bg.getFontMetrics().getFontRenderContext());
        bg.drawString(str,
             (int) (getGameWidth() / 2 - bounds.getWidth()),
             (int) bounds.getHeight()
        );

        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, app.getWidth(), app.getHeight());
        int width = (int) (getGameWidth() * SCALE);
        int height = (int) (getGameHeight() * SCALE);
        g.drawImage(
            buffer.getScaledInstance(
                width,
                height,
                Image.SCALE_SMOOTH
            ),
            (app.getWidth() - width) / 2, (app.getHeight() - height) / 2, null
        );

        lastFrame = buffer;
    }

    /**
     * Update the game.
     * @param delta time since the last update (frame)
     */
    public void update(double delta) {
        for (Entity ent : getEntities()) {
            ent.update(delta);
        }

        gameTime += delta;
    }

    public void run() {
        app.addKeyListener(player);
        grid.startSpawning();
        enemyFactory.start();
    }

    /**
     * Clean up after game.
     */
    public void exit() {
        app.removeKeyListener(player);
        grid.stopSpawning();
        enemyFactory.stop();
    }
}
