package com.assignment1.command;

import com.assignment1.board.Board;
import com.assignment1.player.Player;

// Base class for all player commands
public abstract class Command {
    
    // Executes the command and returns a description of the action
    public abstract String execute(Player player, Board board);
    
}
