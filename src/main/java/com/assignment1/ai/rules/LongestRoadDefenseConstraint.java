package com.assignment1.ai.rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.assignment1.ai.ConstraintRule;
import com.assignment1.board.Board;
import com.assignment1.board.Intersection;
import com.assignment1.board.Path;
import com.assignment1.enums.ResourceType;
import com.assignment1.pieces.Road;
import com.assignment1.player.Player;

/**
 * Concrete ConstraintRule that represent the constraint:
 * - if other player has longest road that is at most one road shorter, 
 * - the agent should buy a connected road segment
 */
public class LongestRoadDefenseConstraint extends ConstraintRule {
    
    private static final double PRIORITY = 10.0;
    
    // Cost of a road
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

        int myLongestRoad = calculateLongestRoad(player, board);

        int maxOpponentRoad = 0;
        List<Player> opponents = getOpponents(board, player);
        for (Player opponent : opponents) {
            int oppRoad = calculateLongestRoad(opponent, board);
            if (oppRoad > maxOpponentRoad) {
                maxOpponentRoad = oppRoad;
            }
        }

        if (myLongestRoad > 0 && maxOpponentRoad >= myLongestRoad - 1) {
            bestExtensionPath = findExtensionPath(player, board);
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

    private int calculateLongestRoad(Player player, Board board) {
        List<Path> myRoads = new ArrayList<>();
        for (Path p : board.getPaths()) {
            if (p.getOccupant() instanceof Road && p.getOccupant().getOwner() == player) {
                myRoads.add(p);
            }
        }

        if (myRoads.isEmpty()) return 0;

        int maxLen = 0;
        
        Set<Intersection> startNodes = new HashSet<>();
        for (Path p : myRoads) {
            startNodes.addAll(p.getEndpoints());
        }

        for (Intersection start : startNodes) {
            Set<Path> visited = new HashSet<>();
            int len = dfsLongestRoad(start, myRoads, visited);
            if (len > maxLen) {
                maxLen = len;
            }
        }

        return maxLen;
    }

    private int dfsLongestRoad(Intersection current, List<Path> allowedRoads, Set<Path> visited) {
        int maxDepth = 0;
        
        for (Path p : allowedRoads) {
            if (!visited.contains(p) && p.getEndpoints().contains(current)) {
                
                if (current.getOccupant() != null && current.getOccupant().getOwner() != allowedRoads.get(0).getOccupant().getOwner()) {
                    continue; 
                }

                visited.add(p);
                Intersection next = p.getEndpoints().get(0) == current ? p.getEndpoints().get(1) : p.getEndpoints().get(0);
                
                int depth = 1 + dfsLongestRoad(next, allowedRoads, visited);
                if (depth > maxDepth) {
                    maxDepth = depth;
                }
                
                visited.remove(p); 
            }
        }
        return maxDepth;
    }

    private Path findExtensionPath(Player player, Board board) {
        for (Path p : board.getPaths()) {
            if (p.getOccupant() == null && board.canPlaceRoad(p, player)) {
                return p;
            }
        }
        return null;
    }

    private List<Player> getOpponents(Board board, Player me) {
        Set<Player> opponents = new HashSet<>();
        for (Intersection i : board.getIntersections()) {
            if (i.getOccupant() != null && i.getOccupant().getOwner() != me) {
                opponents.add(i.getOccupant().getOwner());
            }
        }
        for (Path p : board.getPaths()) {
            if (p.getOccupant() != null && p.getOccupant().getOwner() != me) {
                opponents.add(p.getOccupant().getOwner());
            }
        }
        return new ArrayList<>(opponents);
    }
    
}
