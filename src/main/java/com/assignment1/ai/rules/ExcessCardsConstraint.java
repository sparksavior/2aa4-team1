package com.assignment1.ai.rules;

import com.assignment1.ai.ConstraintRule;
import com.assignment1.board.Board;
import com.assignment1.player.Player;

import java.lang.String;
import java.lang.Integer;

/**
 * Concrete ConstraintRule that represent the constraint:
 * - if there are more than 7 cards in hand, the player must spend them
 */
public class ExcessCardsConstraint extends ConstraintRule {

    private static final double PRIORITY = 10.0;

    @Override
    public double evaluate(Player player, Board board) {
        if (canApply(player, board)) {
            return PRIORITY;
        }
        return 0.0;
    }

    @Override
    public boolean canApply(Player player, Board board) {
        // Player hand is protected, so getting the count requires parsing the summary.
        int count = getCardCount(player);
        return count > 7;
    }

    @Override
    public String apply(Player player, Board board) {
        player.handleDiceRoll7();
        return "Discards half of hand due to excess cards limit (>7).";
    }

    private int getCardCount(Player player) {
        String summary = player.getResourceHandSummary();
        if (summary.equals("empty")) return 0;
        
        int total = 0;
        String[] parts = summary.split(", ");
        for (String part : parts) {
            String[] kv = part.split(": ");
            if (kv.length == 2) {
                total += Integer.parseInt(kv[1]);
            }
        }
        return total;
    }

}
