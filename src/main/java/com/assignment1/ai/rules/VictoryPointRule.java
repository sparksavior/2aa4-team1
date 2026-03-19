package com.assignment1.ai.rules;

import java.util.HashMap;
import java.util.Map;

import com.assignment1.ai.ValueRule;
import com.assignment1.board.Board;
import com.assignment1.board.Intersection;
import com.assignment1.enums.ResourceType;
import com.assignment1.pieces.Building;
import com.assignment1.pieces.Settlement;
import com.assignment1.player.Player;

/**
 * Concrete ValueRule that evaluates the benefit of earning victory points.
 */
public class VictoryPointRule extends ValueRule {

    private static final double VALUE = 1.0;

    private Map<ResourceType, Integer> settlementCost() {
        Map<ResourceType, Integer> cost = new HashMap<>();
        cost.put(ResourceType.BRICK, 1);
        cost.put(ResourceType.WOOD, 1);
        cost.put(ResourceType.WHEAT, 1);
        cost.put(ResourceType.SHEEP, 1);
        return cost;
    }

    private Map<ResourceType, Integer> cityCost() {
        Map<ResourceType, Integer> cost = new HashMap<>();
        cost.put(ResourceType.WHEAT, 2);
        cost.put(ResourceType.ORE, 3);
        return cost;
    }

    @Override
    public double evaluate(Player player, Board board) {
        return canApply(player, board) ? VALUE : 0.0;
    }

    @Override
    public boolean canApply(Player player, Board board) {
        // Check valid city upgrade
        for (Intersection intersection : board.getIntersections()) {
            Building building = intersection.getOccupant();
            if (building instanceof Settlement && building.getOwner() == player) {
                if (player.canAfford(cityCost())) {
                    return true;
                }
            }
        }
        // Check valid settlement build
        for (Intersection intersection : board.getIntersections()) {
            if (intersection.getOccupant() == null && board.canPlaceSettlement(intersection, player) && player.canAfford(settlementCost())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String apply(Player player, Board board) {
        // Try city first
        for (Intersection intersection : board.getIntersections()) {
            Building building = intersection.getOccupant();
            if (building instanceof Settlement && building.getOwner() == player) {
                if (player.upgradeCity(intersection)) {
                    return "upgrade city";
                }
            }
        }
        // Then try settlement
        for (Intersection intersection : board.getIntersections()) {
            if (intersection.getOccupant() == null && board.canPlaceSettlement(intersection, player)) {
                if (player.buildSettlement(board, intersection)) {
                    return "build settlement";
                }
            }
        }
        return "no-op";
    }
        
}
