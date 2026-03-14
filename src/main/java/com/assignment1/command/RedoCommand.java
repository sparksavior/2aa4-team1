package com.assignment1.command;

import com.assignment1.board.Board;
import com.assignment1.player.Player;

public class RedoCommand implements Command {

    @Override
    public String execute(Player player, Board board) {
        return "redo";
    }
}