package com.assignment1.pieces;

import com.assignment1.player.Player;

public abstract class Building extends Piece {
    
    private int victoryPoints;

    public Building(Player owner, int victoryPoints) {
        super(owner);
        this.victoryPoints = victoryPoints;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }
}
