package com.assignment1.ai.rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import com.assignment1.ai.ConstraintRule;
import com.assignment1.board.Board;
import com.assignment1.board.Intersection;
import com.assignment1.board.Path;
import com.assignment1.enums.ResourceType;
import com.assignment1.player.Player;

/**
 * Concrete ConstraintRule that represent the constraint:
 * - if there exist two road segments that are maximum 2 units away,
 * - the agent should try to connect them
 */
public class RoadConnectionConstraint extends ConstraintRule {

    private static final double PRIORITY = 10.0;
    
    private static final Map<ResourceType, Integer> ROAD_COST =
        Map.of(
            ResourceType.BRICK, 1,
            ResourceType.WOOD, 1
        );

    private Path bestPathToBuild = null;

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

        List<Path> myRoads = board.getRoadsOwnedBy(player);

        if (myRoads.isEmpty()) {
            return false;
        }

        // Group roads into connected networks using BFS
        List<Set<Intersection>> networks = new ArrayList<>();
        Set<Path> visitedRoads = new HashSet<>();

        for (Path startRoad : myRoads) {
            if (visitedRoads.contains(startRoad)) continue;

            Set<Intersection> networkNodes = new HashSet<>();
            Queue<Path> queue = new LinkedList<>();
            queue.add(startRoad);
            visitedRoads.add(startRoad);

            while (!queue.isEmpty()) {
                Path curr = queue.poll();
                networkNodes.addAll(curr.getEndpoints());

                for (Intersection end : curr.getEndpoints()) {
                    for (Path adj : board.getAdjacentPaths(end)) {
                        if (myRoads.contains(adj) && !visitedRoads.contains(adj)) {
                            visitedRoads.add(adj);
                            queue.add(adj);
                        }
                    }
                }
            }
            networks.add(networkNodes);
        }

        if (networks.size() <= 1) {
            return false;
        }

        // Find shortest bridging path between any two networks
        bestPathToBuild = null;
        for (int i = 0; i < networks.size(); i++) {
            for (int j = i + 1; j < networks.size(); j++) {
                for (Intersection startNode : networks.get(i)) {
                    Path firstStep = bfsShortestPath(board, startNode, networks.get(j));
                    if (firstStep != null) {
                        bestPathToBuild = firstStep;
                        return true;
                    }
                }
            }
        }

        return false;
    }
    
    private Path bfsShortestPath(Board board, Intersection start, Set<Intersection> targetNet) {
        Queue<Intersection> queue = new LinkedList<>();
        Map<Intersection, Integer> distances = new HashMap<>();
        Map<Intersection, Path> firstMoveMade = new HashMap<>();
        
        queue.add(start);
        distances.put(start, 0);

        while (!queue.isEmpty()) {
            Intersection curr = queue.poll();
            int dist = distances.get(curr);

            if (dist > 2) continue;
            
            if (targetNet.contains(curr) && dist > 0) {
                return firstMoveMade.get(curr);
            }

            for (Path path : board.getAdjacentPaths(curr)) {
                if (path.getOccupant() != null) continue;
                    
                Intersection next = path.getEndpoints().get(0) == curr
                    ? path.getEndpoints().get(1) : path.getEndpoints().get(0);
                    
                if (!distances.containsKey(next)) {
                    distances.put(next, dist + 1);
                    queue.add(next);
                    firstMoveMade.put(next, dist == 0 ? path : firstMoveMade.get(curr));
                }
            }
        }
        return null;
    }

    @Override
    public String apply(Player player, Board board) {
        if (bestPathToBuild != null) {
            boolean success = player.buildRoad(board, bestPathToBuild);
            if (success) {
                bestPathToBuild = null;
                return "Built road to connect isolated road networks.";
            }
        }
        return "Attempted to connect road networks but failed.";
    }
    
}
