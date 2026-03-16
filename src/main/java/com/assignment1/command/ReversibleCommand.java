package com.assignment1.command;

import com.assignment1.board.Board;
import com.assignment1.player.Player;

/**
 * Interface for commands that can be reversed (undone and redone).
 * This represents a capability - commands implementing this interface
 * can reverse their own execution.
 */
public interface ReversibleCommand extends Command {

    /**
     * Undo the command and returns a message.
     * @param player The player executing the command
     * @param board The game board
     * @return A message describing the undo operation
     */
    String undo(Player player, Board board);

    /**
     * Redo the command and returns a message.
     * @param player The player executing the command
     * @param board The game board
     * @return A message describing the redo operation
     */
    String redo(Player player, Board board);

}
