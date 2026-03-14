package com.assignment1.core;

import com.assignment1.command.UndoableCommand;
import com.assignment1.player.Player;
import com.assignment1.board.Board;

import java.util.Deque;
import java.util.ArrayDeque;

public class CommandHistory {

    private Deque<UndoableCommand> undoStack;

    private Deque<UndoableCommand> redoStack;

    public CommandHistory() {
        this.undoStack = new ArrayDeque<>();
        this.redoStack = new ArrayDeque<>();
    }

    // TODO: Implement command history
    
}
