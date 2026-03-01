package com.assignment1.player;

import com.assignment1.board.Board;
import com.assignment1.board.Intersection;
import com.assignment1.enums.PlayerColor;

// This class represents the human player in the game that receives input from the command line.
public class HumanPlayer extends Player {

    // private CommandParser commandParser;

    // Creates a human player with the given ID and color.
    public HumanPlayer(int id, PlayerColor color) {
        super(id, color);
    }

    // Creates a human player with an initial settlement at the given intersection.
    public HumanPlayer(int id, PlayerColor color, Intersection initialSettlement) {
        super(id, color, initialSettlement);
    }

    // Executes a human player's turn by parsing and executing commands from input.
    @Override
    public String makeMove(Board board) {
        // TODO: Integrate with CommandParser when available
        return "human turn (not yet implemented)";
    }
}
