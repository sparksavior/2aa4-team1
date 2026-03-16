package com.assignment1.ai;

import com.assignment1.board.Board;
import com.assignment1.player.Player;

/**
 * Represents a constraint that must be met before applying a ValueRule (R3.2)
 */
public abstract class ConstraintRule implements Rule {
    
    @Override
    public abstract double evaluate(Player player, Board board); // TODO: Implement evaluation logic

    @Override
    public abstract boolean canApply(Player player, Board board); // TODO: Implement canApply logic
    
    @Override
    public abstract String apply(Player player, Board board); // TODO: Implement apply logic

}
