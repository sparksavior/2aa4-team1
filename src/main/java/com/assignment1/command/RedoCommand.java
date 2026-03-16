package com.assignment1.command;

import com.assignment1.board.Board;
import com.assignment1.core.CommandHistory;
import com.assignment1.player.Player;

/**
 * Command to redo the last undone command.
 * Encapsulates the redo operation by delegating to CommandHistory.
 */
public class RedoCommand implements Command {

    private final CommandHistory commandHistory;

    public RedoCommand(CommandHistory commandHistory) {
        this.commandHistory = commandHistory;
    }

    @Override
    public CommandResult execute(Player player, Board board) {
        String message = commandHistory.redo(player, board);
        return CommandResult.continueTurn(message);
    }
}