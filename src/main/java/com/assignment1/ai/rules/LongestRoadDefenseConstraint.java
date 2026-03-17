package com.assignment1.ai.rules;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.assignment1.ai.ConstraintRule;
import com.assignment1.board.Board;
import com.assignment1.board.Path;
import com.assignment1.enums.ResourceType;
import com.assignment1.player.Player;

/**
 * Concrete ConstraintRule that represent the constraint:
 * - if other player has longest road that is at most one road shorter, 
 * - the agent should buy a connected road segment
 */
public class LongestRoadDefenseConstraint extends ConstraintRule {
    
    private static final double PRIORITY = 10.0;
    
    private static final Map<ResourceType, Integer> ROAD_COST = new HashMap<>();
    static {
        ROAD_COST.put(ResourceType.BRICK, 1);
        ROAD_COST.put(ResourceType.WOOD, 1);
    }

    private Path bestExtensionPath = null;
    
    @Override
    public double evaluate(Player player, Board board) {
        if (canApply(player, board)) {
            return PRIORITY;
        }
        return 0.0;
    }

    @Override
    public boolean canApply(Player player, Board board) {
        if (!player.canAfford(ROAD_COST)) {
            return false;
        }

        int myLongestRoad = board.getLongestRoadLength(player);

        int maxOpponentRoad = 0;
        List<Player> opponents = board.getOpponentPlayers(player);
        for (Player opponent : opponents) {
            int oppRoad = board.getLongestRoadLength(opponent);
            if (oppRoad > maxOpponentRoad) {
                maxOpponentRoad = oppRoad;
            }
        }

        // Defend if an opponent is within 1 road of our longest road
        if (myLongestRoad > 0 && maxOpponentRoad >= myLongestRoad - 1) {
            bestExtensionPath = board.findExtensionPath(player);
            return bestExtensionPath != null;
        }

        return false;
    }
    
    @Override
    public String apply(Player player, Board board) {
        if (bestExtensionPath != null) {
            boolean success = player.buildRoad(board, bestExtensionPath);
            if (success) {
                bestExtensionPath = null;
                return "Built road to defend Longest Road title.";
            }
        }
        return "Attempted to defend Longest Road but failed.";
    }
    
}
