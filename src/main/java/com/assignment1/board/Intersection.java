package com.assignment1.board;

import com.assignment1.pieces.Building;

import java.util.ArrayList;
import java.util.List;

/** Represents a vertex where buildings can be placed. */
public class Intersection {

    private int id;
    private Building occupant;
    private List<Intersection> neighbors;
    private List<Tile> tiles;

    public Intersection(int id) {
        this.id = id;
        this.occupant = null;
        this.neighbors = new ArrayList<>();
        this.tiles = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public Building getOccupant() {
        return occupant;
    }

    public void setOccupant(Building building) {
        this.occupant = building;
    }

    public List<Intersection> getNeighbors() {
        return neighbors;
    }

    public List<Tile> getTiles() {
        return tiles;
    }
}
