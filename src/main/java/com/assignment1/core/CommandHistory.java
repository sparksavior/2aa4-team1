package com.assignment1.core;

import com.assignment1.board.Board;
import com.assignment1.command.UndoableCommand;
import com.assignment1.player.Player;

import java.util.Stack;

public class CommandHistory {

    private Stack<UndoableCommand> undoStack = new Stack<>();
    private Stack<UndoableCommand> redoStack = new Stack<>();

    public void record(UndoableCommand cmd) {
        undoStack.push(cmd);
        redoStack.clear();
    }

    public String undo(Player player, Board board) {
        if (undoStack.isEmpty()) {
            return "nothing to undo";
        }

        UndoableCommand cmd = undoStack.pop();
        cmd.undo(player, board);
        redoStack.push(cmd);

        return "undo successful";
    }

    public String redo(Player player, Board board) {
        if (redoStack.isEmpty()) {
            return "nothing to redo";
        }

        UndoableCommand cmd = redoStack.pop();
        cmd.redo(player, board);
        undoStack.push(cmd);

        return "redo successful";
    }
}