package com.assignment1.command;

import com.assignment1.board.Board;
import com.assignment1.board.Intersection;
import com.assignment1.board.Path;
import com.assignment1.enums.*;
import com.assignment1.player.Player;
import com.assignment1.pieces.Settlement;

import java.util.List;
import java.util.Map;

// Command to build a settlement, city, or road
public class BuildCommand implements UndoableCommand {
    
    private BuildType buildType;
    private int nodeId;
    private int fromNodeId;
    private int toNodeId;
    private Path builtPath;
    private Intersection builtIntersection;
    private boolean executedSuccessfully = false;
    
    // Builds a settlement/city at the given intersection
    public BuildCommand(BuildType buildType, int nodeId) {
        this.buildType = buildType;
        this.nodeId = nodeId;
        this.fromNodeId = -1;
        this.toNodeId = -1;
    }
    
    // Builds a road between the given intersections
    public BuildCommand(int fromNodeId, int toNodeId) {
        this.buildType = BuildType.ROAD;
        this.fromNodeId = fromNodeId;
        this.toNodeId = toNodeId;
        this.nodeId = -1;
    }

    @Override
    public String undo(Player player, Board board) {
        if (!executedSuccessfully) {
            return "undo failed";
        }

        switch (buildType) {
            case SETTLEMENT:
                if (builtIntersection != null) {
                    builtIntersection.setOccupant(null);
                    player.addResources(ResourceType.BRICK, 1);
                    player.addResources(ResourceType.WOOD, 1);
                    player.addResources(ResourceType.WHEAT, 1);
                    player.addResources(ResourceType.SHEEP, 1);
                    return "undo build settlement " + nodeId;
                }
                break;

            case CITY:
                if (builtIntersection != null) {
                    builtIntersection.setOccupant(new Settlement(player));
                    player.addResources(ResourceType.WHEAT, 2);
                    player.addResources(ResourceType.ORE, 3);
                    return "undo build city " + nodeId;
                }
                break;

            case ROAD:
                if (builtPath != null) {
                    builtPath.setOccupant(null);
                    player.addResources(ResourceType.BRICK, 1);
                    player.addResources(ResourceType.WOOD, 1);
                    return "undo build road " + fromNodeId + "," + toNodeId;
                }
                break;
        }

        return "undo failed";
    }

    @Override
    public String redo(Player player, Board board) {
        return execute(player, board);
    }
    
    @Override
    public String execute(Player player, Board board) {
        switch (buildType) {
            case SETTLEMENT:
                Intersection intersection = board.getIntersectionById(nodeId);
                if (intersection == null) {
                    return "build settlement failed: invalid intersection id " + nodeId;
                }
                if (intersection.getOccupant() != null) {
                    return "build settlement failed: intersection " + nodeId + " is already occupied";
                }
                if (!player.canAfford(Map.of(
                    ResourceType.BRICK, 1,
                    ResourceType.WOOD, 1,
                    ResourceType.WHEAT, 1,
                    ResourceType.SHEEP, 1
                ))) {
                    return "build settlement failed: insufficient resources (need 1 BRICK, 1 WOOD, 1 WHEAT, 1 SHEEP)";
                }
                if (player.buildSettlement(board, intersection)) {
                    builtIntersection = intersection;
                    executedSuccessfully = true;
                    return "build settlement " + nodeId;
                }
                return "build settlement failed: unknown error";
                
            case CITY:
                Intersection cityIntersection = board.getIntersectionById(nodeId);
                if (cityIntersection == null) {
                    return "build city failed: invalid intersection id " + nodeId;
                }
                if (cityIntersection.getOccupant() == null) {
                    return "build city failed: no settlement at intersection " + nodeId;
                }
                if (!(cityIntersection.getOccupant() instanceof Settlement)) {
                    return "build city failed: intersection " + nodeId + " already has a city";
                }
                if (cityIntersection.getOccupant().getOwner() != player) {
                    return "build city failed: you don't own the settlement at intersection " + nodeId;
                }
                if (!player.canAfford(Map.of(
                    ResourceType.WHEAT, 2,
                    ResourceType.ORE, 3
                ))) {
                    return "build city failed: insufficient resources (need 2 WHEAT, 3 ORE)";
                }
                if (player.upgradeCity(cityIntersection)) {
                    builtIntersection = cityIntersection;
                    executedSuccessfully = true;
                    return "build city " + nodeId;
                }
                return "build city failed: unknown error";
                
            case ROAD:
                // find a path between fromNodeId and toNodeId
                Intersection fromIntersection = board.getIntersectionById(fromNodeId);
                Intersection toIntersection = board.getIntersectionById(toNodeId);
                if (fromIntersection == null) {
                    return "build road failed: invalid intersection id " + fromNodeId;
                }
                if (toIntersection == null) {
                    return "build road failed: invalid intersection id " + toNodeId;
                }
                Path path = findPath(board, fromNodeId, toNodeId);
                if (path == null) {
                    return "build road failed: no path exists between " + fromNodeId + " and " + toNodeId;
                }
                if (path.getOccupant() != null) {
                    return "build road failed: path between " + fromNodeId + " and " + toNodeId + " is already occupied";
                }
                if (!board.canPlaceRoad(path, player)) {
                    return "build road failed: road must connect to your existing road or building";
                }
                if (!player.canAfford(Map.of(
                    ResourceType.BRICK, 1,
                    ResourceType.WOOD, 1
                ))) {
                    return "build road failed: insufficient resources (need 1 BRICK, 1 WOOD)";
                }
                if (player.buildRoad(board, path)) {
                    builtPath = path;
                    executedSuccessfully = true;
                    return "build road " + fromNodeId + "," + toNodeId;
                }
                return "build road failed: unknown error";
                
            default:
                return "build failed: unknown build type";
        }
    }
    
    // A helper method to find a path between two intersections
    private Path findPath(Board board, int fromId, int toId) {
        Intersection from = board.getIntersectionById(fromId);
        Intersection to = board.getIntersectionById(toId);
        if (from == null || to == null) {
            return null;
        }
        
        // search for path connecting these two intersections
        for (Path path : board.getPaths()) {
            List<Intersection> endpoints = path.getEndpoints();
            if (endpoints.size() == 2) {
                Intersection a = endpoints.get(0);
                Intersection b = endpoints.get(1);
                if ( // found a path between fromId and toId
                    (a.getId() == fromId && b.getId() == toId) ||
                    (a.getId() == toId && b.getId() == fromId)
                ) {
                    return path;
                }
            }
        }
        return null;
    }
}
