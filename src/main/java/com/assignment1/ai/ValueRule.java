package com.assignment1.ai;

import com.assignment1.board.Board;
import com.assignment1.player.Player;

/**
 * Represents the evaluation of benefit of applying a rule. (R3.2)
 * - earning a VP: 1.0 
 * - building someting without a VP: 0.8
 * - spending cards that results in less than 5 cards in hand: 0.5
 */
public abstract class ValueRule implements Rule {
    
    @Override
    public abstract double evaluate(Player player, Board board); // TODO: Implement evaluation logic

    @Override
    public abstract boolean canApply(Player player, Board board); // TODO: Implement canApply logic

    @Override
    public abstract String apply(Player player, Board board); // TODO: Implement apply logic

}
