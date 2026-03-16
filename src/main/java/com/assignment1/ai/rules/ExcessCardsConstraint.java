package com.assignment1.ai.rules;

import com.assignment1.ai.ConstraintRule;
import com.assignment1.board.Board;
import com.assignment1.player.Player;

/**
 * Concrete ConstraintRule that represent the constraint:
 * - if there are more than 7 cards in hand, the player must spend them
 */
public class ExcessCardsConstraint extends ConstraintRule {

    private static final double PRIORITY = 10.0;

    @Override
    public double evaluate(Player player, Board board) {
        // TODO: Implement evaluation logic
        // Returns PRIORITY if constraint is violated, 0.0 otherwise
        return 0.0;
    }

    @Override
    public boolean canApply(Player player, Board board) {
        // TODO: Implement canApply logic
        return true;
    }

    @Override
    public String apply(Player player, Board board) {
        // TODO: Implement apply logic
        return "";
    }

}
