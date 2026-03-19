package com.assignment1.observer;

import com.assignment1.board.Board;

/**
 * Observer role in the Observer pattern.
 * Concrete observers react to changes in the board state.
 */
public interface Observer {

    /**
     * Called when the subject notifies observers.
     * @param board current board state
     */
    void update(Board board);

}

