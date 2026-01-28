package com.assignment1.board;

import com.assignment1.pieces.Building;

import java.util.ArrayList;
import java.util.List;

public class Intersection {

    private String id;
    private Building occupant;
    private List<Intersection> neighbors;

    public Intersection(String id) {
        this.id = id;
        this.occupant = null;
        this.neighbors = new ArrayList<>();
    }

    public String getId() {
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
}
