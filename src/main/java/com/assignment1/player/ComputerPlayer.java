package com.assignment1.player;

import com.assignment1.board.Board;
import com.assignment1.board.Intersection;
import com.assignment1.board.Path;
import com.assignment1.enums.PlayerColor;
import com.assignment1.pieces.Building;
import com.assignment1.pieces.Settlement;

import java.util.Optional;

// This class represents the computer player in the game with automated agent behavior.
public class ComputerPlayer extends Player {

    // Creates a computer player with the given ID and color.
    public ComputerPlayer(int id, PlayerColor color) {
        super(id, color);
    }

    // Creates a computer player with an initial settlement at the given intersection.
    public ComputerPlayer(int id, PlayerColor color, Intersection initialSettlement) {
        super(id, color, initialSettlement);
    }

    // Agent behavior: attempts to build when holding more than 7 cards.
    @Override
    public String makeMove(Board board) {
        if (getTotalCards() <= 7) return "no-op";

        return tryUpgradeCity(board)
            .or(() -> tryBuildSettlement(board))
            .or(() -> tryBuildRoad(board))
            .orElse("no-op");
    }

    private Optional<String> tryUpgradeCity(Board board) {
        for (Intersection intersection : board.getIntersections()) {
            Building occupant = intersection.getOccupant();
            // currently holding a Settlement
            if (occupant instanceof Settlement && occupant.getOwner() == this) {
                if (upgradeCity(intersection)) {
                    return Optional.of("upgrade city");
                }
            }
        }
        return Optional.empty();
    }

    private Optional<String> tryBuildSettlement(Board board) {
        for (Intersection intersection : board.getIntersections()) {
            if (intersection.getOccupant() == null && buildSettlement(board, intersection)) {
                return Optional.of("build settlement");
            }
        }
        return Optional.empty(); // no settlement to build
    }

    private Optional<String> tryBuildRoad(Board board) {
        for (Path path : board.getPaths()) {
            if (path.getOccupant() == null && buildRoad(board, path)) {
                return Optional.of("build road");
            }
        }
        return Optional.empty(); // no road to build
    }
}
