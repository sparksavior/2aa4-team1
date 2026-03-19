package com.assignment1.ai.rules;

import com.assignment1.ai.ValueRule;
import com.assignment1.board.Board;
import com.assignment1.board.Path;
import com.assignment1.player.Player;


/**
 * Concrete ValueRule that evaluates the benefit of building a settlement or city.
 */
public class BuildRule extends ValueRule {

    private static final double VALUE = 0.8;

    @Override
    public double evaluate(Player player, Board board) {
        return canApply(player, board) ? VALUE : 0.0;
    }

    @Override
    public boolean canApply(Player player, Board board) {
        for (Path p : board.getPaths()) {
            if (p.getOccupant() == null) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String apply(Player player, Board board) {
        for (Path path : board.getPaths()) {
            if (path.getOccupant() == null) {
                if (player.buildRoad(board, path)) {
                    return "build road";
                }
            }
        }
        return "no-op";
    }
}
