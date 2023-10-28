package com.github.pwczar.cblgame;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * The class representing the player character.
 */
public class Player extends Rectangle2D.Double implements Entity, KeyListener {
    final Game game;

    double vx = 0;
    double vy = 0;

    double gravity = 360;
    double moveSpeed = 60;
    double jumpForce = 100;

    double hp = 5;

    List<Upgrade> upgrades = new ArrayList<>();

    // controls state
    private boolean moveLeft = false;
    private boolean moveRight = false;

    // is the player currently standing on something?
    private boolean onFloor = false;

    boolean facingRight = true;

    Block heldBlock = null;

    Animation idleAnimationTorso;
    Animation idleAnimationLegs;

    Animation holdingAnimationTorso;

    Animation runAnimationTorso;
    Animation runAnimationLegs;

    Animation torsoAnimation;
    Animation legsAnimation;

    Image blockHighlightImage;
    Image heartImage;

    /**
     * Initialize a Player object.
     */
    Player(Game game) {
        this.game = game;
        x = 0;
        y = 0;
        width = 6;
        height = 11;

        idleAnimationTorso = new Animation(game, new String[] {
            "player_idle_torso.png"
        }, 1.0, false);
        idleAnimationLegs = new Animation(game, new String[] {
            "player_idle_legs.png"
        }, 1.0, false);

        holdingAnimationTorso = new Animation(game, new String[] {
            "player_holding_torso.png"
        }, 1, false);

        runAnimationTorso = new Animation(game, new String[] {
            "player_running_torso_0.png",
            "player_running_torso_1.png"
        }, 7, true);
        runAnimationLegs = new Animation(game, new String[] {
            "player_running_legs_0.png",
            "player_running_legs_1.png"
        }, 7, true);

        torsoAnimation = idleAnimationTorso;
        legsAnimation = idleAnimationLegs;

        blockHighlightImage = game.loadSprite("block_highlight.png");
        heartImage = game.loadSprite("heart.png");
    }

    /**
     * Create a Player object at the given point.
     * @param x the x coordinate (player center)
     * @param y the y coordinate (bottom of player)
     */
    Player(Game game, double x, double y) {
        // call the default constructor
        this(game);
        this.x = x - getWidth() / 2;
        this.y = y - getHeight();
    }

    /**
     * Handle collision with another object.
     * @param rect the rectangle we collide with
     */
    void collideWith(Rectangle2D rect) {
        if (!this.intersects(rect)) {
            // no overlap - no collision, we don't need to move
            return;
        }

        // get the overlapping part
        Rectangle2D intersection = this.createIntersection(rect);

        // move away from rect on the axis with a smaller difference
        if (intersection.getWidth() < intersection.getHeight()) {
            if (this.x < rect.getX()) {
                x -= intersection.getWidth();
            } else {
                x += intersection.getWidth();
            }
        } else {
            if (this.y < rect.getY()) {
                y -= intersection.getHeight();
                this.onFloor = true;
                vy = 0;
            } else {
                y += intersection.getHeight();
            }
        }
    }

    /**
     * Handle collision with a block.
     * @param block the block
     */
    void collideWith(Block block) {
        if (block.state instanceof BlockStateFalling
            && block.intersects(this)
            && block.getY() + block.getHeight() < this.y + this.height / 2) {
            hp--;
            game.grid.removeBlock(block);
        } else {
            this.collideWith((Rectangle2D) block);
        }
    }

    /**
     * Get the column of the currently selected place.
     * @return the column
     */
    int getInteractCol() {
        if (facingRight) {
            return (int) ((x + width - 1) / Block.SIZE) + 1;
        } else {
            return (int) ((x + 1) / Block.SIZE) - 1;
        }
    }

    /**
     * Get the row of the currently selected place.
     * @return the row
     */
    int getInteractRow() {
        return (int) (y / Block.SIZE);
    }

    /**
     * Handle key presses for player controls.
     */
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == (int) 'A') {
            moveLeft = true;
        } else if (e.getKeyCode() == (int) 'D') {
            moveRight = true;
        } else if (e.getKeyCode() == (int) ' ') {
            // jump on space
            if (onFloor) {
                vy = -jumpForce;
            }
        } else if (e.getKeyCode() == (int) 'E') {
            int col = getInteractCol();
            int row = getInteractRow();
            Block block = game.grid.getBlockAt(col, row);

            if (heldBlock == null
                && block != null
                && !(block.state instanceof BlockStateRemoved)) {
                // pick up a Block
                heldBlock = block;
                game.grid.removeBlock(block);
            } else if (heldBlock != null && block == null
                       && col >= 0 && col < game.grid.getWidth()
                       && row >= 0 && row < game.grid.getHeight()) {
                // place a Block
                game.grid.putBlockAt(heldBlock, col, row);
                game.grid.unstackBlockAt(col, row);
                heldBlock = null;
            }
        } else if (e.getKeyCode() == (int) 'F') {
            // throw a Block if one is held
            if (heldBlock != null) {
                heldBlock.state = new BlockStateThrown(heldBlock);
                game.addEntity(heldBlock);
                heldBlock = null;
            }
        }
    }

    /**
     * Handle key releases for player controls.
     */
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == (int) 'A') {
            moveLeft = false;
        } else if (e.getKeyCode() == (int) 'D') {
            moveRight = false;
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    /**
     * Draw the player at their current position.
     */
    public void draw(Graphics g) {
        // draw the sprite with an offset
        int ox = (int) (x + 0.5 - 1);
        int oy = (int) (y + 0.5 - 1);
        if (facingRight) {
            g.drawImage(legsAnimation.getFrame(), ox, oy, null);
            g.drawImage(torsoAnimation.getFrame(), ox, oy, null);
        } else {
            // draw the sprite flipped
            g.drawImage(
                legsAnimation.getFrame(),
                ox + legsAnimation.getFrame().getWidth(null),
                oy,
                legsAnimation.getFrame().getWidth(null) * -1,
                legsAnimation.getFrame().getHeight(null),
                null
            );
            g.drawImage(
                torsoAnimation.getFrame(),
                ox + torsoAnimation.getFrame().getWidth(null),
                oy,
                torsoAnimation.getFrame().getWidth(null) * -1,
                torsoAnimation.getFrame().getHeight(null),
                null
            );
        }
    }

    /**
     * Draw player-related UI.
     * @param g graphics context
     */
    public void drawUI(Graphics g) {
        int col = getInteractCol();
        int row = getInteractRow();
        if (heldBlock == null) {
            if (game.grid.getBlockAt(col, row) != null
                && !(game.grid.getBlockAt(col, row).state instanceof BlockStateRemoved)) {
                g.drawImage(blockHighlightImage, col * Block.SIZE, row * Block.SIZE, null);
            }
        } else {
            heldBlock.draw(g);
            if (game.grid.getBlockAt(col, row) == null
                && col >= 0 && col < game.grid.getWidth()
                && row >= 0 && row < game.grid.getHeight()) {
                Graphics2D g2d = (Graphics2D) g;
                // apply opacity
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
                g.drawImage(heldBlock.sprite, col * Block.SIZE, row * Block.SIZE, null);
                // reset AlphaComposite
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            }
        }

        // draw hearts
        int x = 0;
        for (int i = 0; i < hp; i++, x += heartImage.getWidth(null)) {
            g.drawImage(heartImage, x, 0, null);
        }

        // draw modifiers
        x = 0;
        for (Upgrade up : upgrades) {
            if (up.icon == null) {
                continue;
            }
            g.drawImage(up.icon, x, heartImage.getWidth(null), null);
            x += up.icon.getWidth(null);
        }
    }

    /**
     * Give the player an upgrade corresponding to the block type.
     * @param blockType the block type
     */
    public void giveUpgrade(int blockType) {
        switch (blockType) {
            case Block.TYPE_BLUE:
                new MovespeedUpgrade(this).addUpgrade();
                break;
            case Block.TYPE_RED:
                new HealthUpgrade(this).addUpgrade();
                break;
            case Block.TYPE_GREEN:
                new JumpUpgrade(this).addUpgrade();
                break;
            default:
                // other blockType, no upgrade
                break;
        }
    }

    /**
     * Update the player's animation.
     */
    private void updateAnimation(double delta, int moveDir) {
        if (moveDir == 0) {
            // the player is standing
            legsAnimation = idleAnimationLegs;
            torsoAnimation = idleAnimationTorso;
        } else {
            if (legsAnimation != runAnimationLegs) {
                // start with first frame
                runAnimationLegs.restart();
            }
            legsAnimation = runAnimationLegs;
            if (torsoAnimation != runAnimationTorso) {
                runAnimationTorso.restart();
            }
            torsoAnimation = runAnimationTorso;
        }
        if (heldBlock != null) {
            // the player is not holding anything
            torsoAnimation = holdingAnimationTorso;
        }
        if (!onFloor) {
            // the player is in the air
            legsAnimation = idleAnimationLegs;
        }

        torsoAnimation.update(delta);
        legsAnimation.update(delta);
    }

    /**
     * Update the player character.
     */
    public void update(double delta) {
        // gravitational acceleration
        vy += gravity * delta;

        // where should the player move?
        short moveDir = 0;
        if (moveLeft) {
            moveDir--;
        }
        if (moveRight) {
            moveDir++;
        }

        if (moveDir > 0) {
            facingRight = true;
        } else if (moveDir < 0) {
            facingRight = false;
        }

        vx = moveDir * moveSpeed;

        // update position
        x += delta * vx;
        y += delta * vy;

        onFloor = false;
        // collide with game boundaries
        for (Rectangle2D boundary : game.boundaries) {
            this.collideWith(boundary);
        }

        // collide with blocks
        for (Block block : game.grid.getBlocks()) {
            this.collideWith(block);
        }

        if (heldBlock != null) {
            // move the held block above the player's head
            heldBlock.x = x - (Block.SIZE - width) / 2;
            heldBlock.y = y - Block.SIZE;
        }

        if (hp <= 0) {
            // die
            game.app.setScene(new GameOver(game.app, game.gameTime));
        }

        // update all active upgrades
        for (Upgrade up : new ArrayList<>(upgrades)) {
            up.update(delta);
        }

        updateAnimation(delta, moveDir);
    }
}
