package catan.simulation;

public abstract class Building extends Piece {

    protected int victoryPoints;

    public Building(Player owner, int victoryPoints) {
        super(owner);
        this.victoryPoints = victoryPoints;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }
}