package com.assignment1.board;

import com.assignment1.enums.TerrainType;

import java.util.ArrayList;
import java.util.List;

/** Represents a hexagonal tile on the board. */
public class Tile {

    private int id;
    private TerrainType terrain;
    private int productionNumber;
    private List<Intersection> intersections;

    public Tile(int id, TerrainType terrain, int productionNumber) {
        this.id = id;
        this.terrain = terrain;
        this.productionNumber = productionNumber;
        this.intersections = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public TerrainType getTerrain() {
        return terrain;
    }

    public int getProductionNumber() {
        return productionNumber;
    }

    public List<Intersection> getIntersections() {
        return intersections;
    }
    
}
