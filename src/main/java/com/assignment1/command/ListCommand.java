package com.assignment1.command;

import com.assignment1.board.Board;
import com.assignment1.player.Player;

// Command to list cards currently in the player's hand
public class ListCommand extends Command {
    
    @Override
    public String execute(Player player, Board board) {
        // list resources in player's hand
        return "player " + player.getId() + " hand: " + player.getResourceHandSummary();
    }
}
