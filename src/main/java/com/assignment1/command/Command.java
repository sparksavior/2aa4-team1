package com.assignment1.command;

import com.assignment1.board.Board;
import com.assignment1.player.Player;

/**
 * Base interface for all player commands.
 */
public interface Command {
    
    /**
     * Executes the command and returns a result with metadata about the execution.
     * 
     * @param player The player executing the command
     * @param board The game board
     * @return CommandResult containing message and behavior metadata
     */
    CommandResult execute(Player player, Board board);
    
}
