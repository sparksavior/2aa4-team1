package com.assignment1.player;

import com.assignment1.board.Board;
import com.assignment1.board.Intersection;
import com.assignment1.command.Command;
import com.assignment1.command.GoCommand;
import com.assignment1.command.RollCommand;
import com.assignment1.core.CommandParser;
import com.assignment1.enums.PlayerColor;
import com.assignment1.command.RedoCommand;
import com.assignment1.command.UndoCommand;
import com.assignment1.command.UndoableCommand;
import com.assignment1.core.CommandHistory;

import java.util.Scanner;

// This class represents the human player in the game that receives input from the command line.
public class HumanPlayer extends Player {

    private CommandHistory commandHistory;
    private CommandParser parser;
    private Scanner scanner;

    // Creates a human player with the given ID and color.
    public HumanPlayer(int id, PlayerColor color, CommandHistory commandHistory) {
        super(id, color);
        this.parser = new CommandParser();
        this.scanner = new Scanner(System.in);
        this.commandHistory = commandHistory;
    }

    // Creates a human player with an initial settlement at the given intersection.

    public HumanPlayer(int id, PlayerColor color, Intersection initialSettlement, CommandHistory commandHistory) {
        super(id, color, initialSettlement);
        this.parser = new CommandParser();
        this.scanner = new Scanner(System.in);
        this.commandHistory = commandHistory;
    }
    // Executes a human player's turn by parsing and executing commands from input.
    @Override
    public String makeMove(Board board) {
        while (true) {
            String input = scanner.nextLine();
            Command cmd;
            try {
                cmd = parser.parse(input);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                continue;
            }

            if (cmd == null) {
                continue;
            }

            String result;

            if (cmd instanceof UndoCommand) {
                result = commandHistory.undo(this, board);
                System.out.println(result);
                continue;
            }

            if (cmd instanceof RedoCommand) {
                result = commandHistory.redo(this, board);
                System.out.println(result);
                continue;
            }

            result = cmd.execute(this, board);

            if (cmd instanceof UndoableCommand && result != null && !result.contains("failed")) {
                commandHistory.record((UndoableCommand) cmd);
            }

            if (cmd instanceof GoCommand) {
                return result;
            }

            if (cmd instanceof RollCommand) {
                return result;
            }

            if (result != null) {
                System.out.println(result);
            }
        }
    }
}
