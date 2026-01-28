package com.assignment1.board;

import com.assignment1.pieces.Road;

import java.util.ArrayList;
import java.util.List;

public class Path {

    private String id;
    private Intersection endpointA;
    private Intersection endpointB;
    private Road occupant;

    public Path(String id, Intersection a, Intersection b) {
        this.id = id;
        this.endpointA = a;
        this.endpointB = b;
        this.occupant = null;
    }

    public String getId() {
        return id;
    }

    public List<Intersection> getEndpoints() {
        List<Intersection> endpoints = new ArrayList<>();
        endpoints.add(endpointA);
        endpoints.add(endpointB);
        return endpoints;
    }

    public Road getOccupant() {
        return occupant;
    }

    public void setOccupant(Road road) {
        this.occupant = road;
    }
    
}
