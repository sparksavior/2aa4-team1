package com.assignment1.command;

import com.assignment1.board.Board;
import com.assignment1.player.Player;

/** Command to proceed to the next turn (no more actions). */
public class GoCommand extends Command {
    
    @Override
    public String execute(Player player, Board board) {
        // signal to proceed to next turn
        return "go";
    }
}
