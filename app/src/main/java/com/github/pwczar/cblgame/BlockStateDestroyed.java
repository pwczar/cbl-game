package com.github.pwczar.cblgame;

/**
 * The state of a Block that has been destroyed by an enemy.
 */
public class BlockStateDestroyed extends BlockStateRemoved {
    BlockStateDestroyed(Block block) {
        super(block);
    }
}
