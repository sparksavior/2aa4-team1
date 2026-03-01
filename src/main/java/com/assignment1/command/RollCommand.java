package com.assignment1.command;

import com.assignment1.board.Board;
import com.assignment1.player.Player;

// Command to roll the dice and collect resources
public class RollCommand extends Command {
    
    @Override
    public String execute(Player player, Board board) {
        // roll dice and distribute resources will be handled by Simulator
        return "roll";
    }
}
