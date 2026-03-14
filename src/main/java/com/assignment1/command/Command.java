package com.assignment1.command;

import com.assignment1.board.Board;
import com.assignment1.player.Player;

// Base class for all player commands
public interface Command {
    
    // Executes the command and returns a description of the action
    String execute(Player player, Board board);
    
}
