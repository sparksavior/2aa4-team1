package com.assignment1.ai.rules;

import com.assignment1.ai.ValueRule;
import com.assignment1.board.Board;
import com.assignment1.board.Path;
import com.assignment1.player.Player;

/**
 * Concrete ValueRule that evaluates the benefit of spending cards that results in less than 5 cards in hand.
 */
public class SpendRule extends ValueRule {
    private static final double VALUE = 0.5;

    @Override
    public double evaluate(Player player, Board board) {
        return canApply(player, board) ? VALUE : 0.0;
    }

    @Override
    public boolean canApply(Player player, Board board) {
        return player.getTotalCardsPublic() > 5;
    }

    @Override
    public String apply(Player player, Board board) {
        for (Path p : board.getPaths()) {
            if (p.getOccupant() == null) {
                if (player.buildRoad(board, p)) {
                    if (player.getTotalCardsPublic() < 5) {
                        return "spend to reduce cards";
                    }
                }
            }
        }
        return "no-op";
    }
}
