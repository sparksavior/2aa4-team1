package catan.simulation;

import java.util.List;

public class Intersection {

    private String id;
    private Building occupant;
    private List<Intersection> neighbours;

    public Intersection(String id) {
        this.id = id;
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

    public List<Intersection> getNeighbours() {
        return neighbours;
    }
}