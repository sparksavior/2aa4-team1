package com.assignment1.core;

import com.assignment1.board.Board;
import com.assignment1.command.ReversibleCommand;
import com.assignment1.player.Player;

import java.util.ArrayDeque;
import java.util.Deque;

public class CommandHistory {

    private Deque<ReversibleCommand> undoStack;
    private Deque<ReversibleCommand> redoStack;

    public CommandHistory() {
        this.undoStack = new ArrayDeque<>();
        this.redoStack = new ArrayDeque<>();
    }

    public void record(ReversibleCommand cmd) {
        undoStack.push(cmd);
        redoStack.clear();
    }

    public String undo(Player player, Board board) {
        if (undoStack.isEmpty()) {
            return "nothing to undo";
        }

        ReversibleCommand cmd = undoStack.pop();
        String message = cmd.undo(player, board);
        redoStack.push(cmd);

        return message;
    }

    public String redo(Player player, Board board) {
        if (redoStack.isEmpty()) {
            return "nothing to redo";
        }

        ReversibleCommand cmd = redoStack.pop();
        String message = cmd.redo(player, board);
        undoStack.push(cmd);

        return message;
    }
}