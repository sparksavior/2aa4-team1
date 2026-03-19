package com.assignment1.ai.rules;

import java.util.HashMap;
import java.util.Map;

import com.assignment1.ai.ValueRule;
import com.assignment1.board.Board;
import com.assignment1.board.Path;
import com.assignment1.enums.ResourceType;
import com.assignment1.player.Player;

/**
 * Concrete ValueRule that evaluates the benefit of spending cards that results in less than 5 cards in hand.
 */
public class SpendRule extends ValueRule {
    private static final double VALUE = 0.5;

    private Map<ResourceType, Integer> roadCost() {
        Map<ResourceType, Integer> cost = new HashMap<>();
        cost.put(ResourceType.BRICK, 1);
        cost.put(ResourceType.WOOD, 1);
        return cost;
    }

    @Override
    public double evaluate(Player player, Board board) {
        return canApply(player, board) ? VALUE : 0.0;
    }

    @Override
    public boolean canApply(Player player, Board board) {
        int current = player.getTotalCards();
        if (current <= 5) return false;
        for (Path path : board.getPaths()) {
            if (path.getOccupant() == null &&
                board.canPlaceRoad(path, player) &&
                player.canAfford(roadCost())) {
                if (current - 2 < 5) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String apply(Player player, Board board) {
        for (Path path : board.getPaths()) {
            if (path.getOccupant() == null &&
                board.canPlaceRoad(path, player) &&
                player.canAfford(roadCost())) {
                if (player.buildRoad(board, path)) {
                    int after = player.getTotalCards();
                    if (after < 5) {
                        return "spend resources (road)";
                    }
                }
            }
        }
        return "no-op";
    }
}
