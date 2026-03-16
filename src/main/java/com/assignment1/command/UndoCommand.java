package com.assignment1.command;

import com.assignment1.board.Board;
import com.assignment1.core.CommandHistory;
import com.assignment1.player.Player;

/**
 * Command to undo the last executed reversible command.
 * Encapsulates the undo operation by delegating to CommandHistory.
 */
public class UndoCommand implements Command {

    private final CommandHistory commandHistory;

    public UndoCommand(CommandHistory commandHistory) {
        this.commandHistory = commandHistory;
    }

    @Override
    public CommandResult execute(Player player, Board board) {
        String message = commandHistory.undo(player, board);
        return CommandResult.continueTurn(message);
    }
}