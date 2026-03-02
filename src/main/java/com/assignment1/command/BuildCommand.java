package com.assignment1.command;

import com.assignment1.board.Board;
import com.assignment1.board.Intersection;
import com.assignment1.board.Path;
import com.assignment1.enums.BuildType;
import com.assignment1.player.Player;
import java.util.List;

// Command to build a settlement, city, or road
public class BuildCommand extends Command {
    
    private BuildType buildType;
    private int nodeId;
    private int fromNodeId;
    private int toNodeId;
    
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
    public String execute(Player player, Board board) {
        switch (buildType) {
            case SETTLEMENT:
                Intersection intersection = board.getIntersectionById(nodeId);
                if (intersection == null) {
                    return "build settlement failed: invalid intersection id " + nodeId;
                }
                if (player.buildSettlement(board, intersection)) {
                    return "build settlement " + nodeId;
                }
                return "build settlement failed";
                
            case CITY:
                Intersection cityIntersection = board.getIntersectionById(nodeId);
                if (cityIntersection == null) {
                    return "build city failed: invalid intersection id " + nodeId;
                }
                if (player.upgradeCity(cityIntersection)) {
                    return "build city " + nodeId;
                }
                return "build city failed";
                
            case ROAD:
                // find a path between fromNodeId and toNodeId
                Path path = findPath(board, fromNodeId, toNodeId);
                if (path == null) {
                    return "build road failed: no path between " + fromNodeId + " and " + toNodeId;
                }
                if (player.buildRoad(board, path)) {
                    return "build road " + fromNodeId + "," + toNodeId;
                }
                return "build road failed";
                
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
