package com.assignment1.pieces;

import com.assignment1.player.Player;

public abstract class Piece {
    
    private Player owner;

    public Piece(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }
}
