package com.assignment1.ai.rules;

import com.assignment1.ai.ConstraintRule;
import com.assignment1.board.Board;
import com.assignment1.player.Player;

/**
 * Concrete ConstraintRule that represent the constraint:
 * - if there exist two road segments that are maximum 2 units away,
 * - the agent should try to connect them
 */
public class RoadConnectionConstraint extends ConstraintRule {

    private static final double PRIORITY = 10.0;

    @Override
    public double evaluate(Player player, Board board) {
        // TODO: Implement evaluation logic
        return PRIORITY;
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
