package com.assignment1.board;

import com.assignment1.enums.TerrainType;

public class Tile {

    private String id;
    private TerrainType terrain;
    private int productionNumber;

    public Tile(String id, TerrainType terrain, int productionNumber) {
        this.id = id;
        this.terrain = terrain;
        this.productionNumber = productionNumber;
    }

    public String getId() {
        return id;
    }

    public TerrainType getTerrain() {
        return terrain;
    }

    public int getProductionNumber() {
        return productionNumber;
    }
    
}
