package com.assignment1.ai.rules;

import com.assignment1.ai.ValueRule;
import com.assignment1.board.Board;
import com.assignment1.player.Player;

/**
 * Concrete ValueRule that evaluates the benefit of earning victory points.
 */
public class VictoryPointRule extends ValueRule {

    private static final double VALUE = 1.0;

    @Override
    public double evaluate(Player player, Board board) {
        // TODO: Implement evaluation logic
        return VALUE;
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
