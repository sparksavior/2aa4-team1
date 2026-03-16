package com.assignment1.command;

import com.assignment1.board.Board;
import com.assignment1.player.Player;

/** Command to proceed to the next turn (no more actions). */
public class GoCommand implements Command {
    
    @Override
    public CommandResult execute(Player player, Board board) {
        return CommandResult.endTurn("go");
    }
}
