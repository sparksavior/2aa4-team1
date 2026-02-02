package catan.simulation;

public abstract class Piece {

    protected Player owner;

    public Piece(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }
}