package com.assignment1.command;

import com.assignment1.board.Board;
import com.assignment1.player.Player;

// Command that can be undone and redone
public interface UndoableCommand extends Command {

    void undo(Player player, Board board);

    void redo(Player player, Board board);

}
