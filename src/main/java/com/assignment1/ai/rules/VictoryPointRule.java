package com.assignment1.ai.rules;

import com.assignment1.ai.ValueRule;
import com.assignment1.board.Board;
import com.assignment1.board.Intersection;
import com.assignment1.pieces.Building;
import com.assignment1.pieces.Settlement;
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
        return true; // keep simple
    }

    @Override
    public String apply(Player player, Board board) {
        // Try upgrading city
        for (Intersection intersection : board.getIntersections()) {
            Building occupant = intersection.getOccupant();
            if (occupant instanceof Settlement && occupant.getOwner() == player) {
                if (player.upgradeCity(intersection)) {
                    return "upgrade city";
                }
            }
        }
        // Try building settlement
        for (Intersection intersection : board.getIntersections()) {
            if (intersection.getOccupant() == null) {
                if (player.buildSettlement(board, intersection)) {
                    return "build settlement";
                }
            }
        }
        return "no-op";
    }
        
}
