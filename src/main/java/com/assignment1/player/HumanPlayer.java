package com.assignment1.player;

import com.assignment1.board.Board;
import com.assignment1.board.Intersection;
import com.assignment1.command.Command;
import com.assignment1.command.GoCommand;
import com.assignment1.core.CommandParser;
import com.assignment1.enums.PlayerColor;

import java.util.Scanner;

// This class represents the human player in the game that receives input from the command line.
public class HumanPlayer extends Player {

    private CommandParser parser;
    private Scanner scanner;

    // Creates a human player with the given ID and color.
    public HumanPlayer(int id, PlayerColor color) {
        super(id, color);
        this.parser = new CommandParser();
        this.scanner = new Scanner(System.in);
    }

    // Creates a human player with an initial settlement at the given intersection.
    public HumanPlayer(int id, PlayerColor color, Intersection initialSettlement) {
        super(id, color, initialSettlement);
        this.parser = new CommandParser();
        this.scanner = new Scanner(System.in);
    }

    // Executes a human player's turn by parsing and executing commands from input.
    @Override
    public String makeMove(Board board) {
        // TODO: implement command execution logic by role 4
        while (true) {
            String input = scanner.nextLine();
            Command cmd = parser.parse(input);
            if (cmd == null) continue; // invalid command, try again

            String result = cmd.execute(this, board);
            if (cmd instanceof GoCommand) return result; // turn ends

            // TODO: other commands goes here...
        }
    }
}
