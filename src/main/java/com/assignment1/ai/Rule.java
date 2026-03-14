package com.assignment1.ai;

import com.assignment1.board.Board;
import com.assignment1.player.Player;

/**
 * A rule is a condition that must be met for a move to be valid.
 */
public interface Rule {

    /**
     * Evaluates the rule and returns true if the rule is met, false otherwise.
     * @param player The player to evaluate the rule for.
     * @param board The board to evaluate the rule for.
     * @return The value/priority of applying the rule (higher is better).
     */
    double evaluate(Player player, Board board);

    /**
     * Checks if the rule can be applied to the given player and board.
     * @param player The player to check if the rule can be applied to.
     * @param board The board to check if the rule can be applied to.
     * @return True if the rule can be applied, false otherwise.
     */
    boolean canApply(Player player, Board board);

    /**
     * Applies the rule to the given player and board.
     * @param player The player to apply the rule to.
     * @param board The board to apply the rule to.
     * @return A description of the action taken.
     */
    String apply(Player player, Board board);
    
}
