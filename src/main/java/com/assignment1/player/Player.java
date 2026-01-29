package com.assignment1.player;

import com.assignment1.board.Board;
import com.assignment1.board.Intersection;
import com.assignment1.board.Path;
import com.assignment1.enums.PlayerColor;
import com.assignment1.enums.ResourceType;

import java.util.HashMap;
import java.util.Map;

public class Player {

    private int id;
    private PlayerColor color;
    private Map<ResourceType, Integer> resourceHand;
    private int victoryPoints;
    private int roadsBuilt;
    private int settlementsBuilt;
    private int citiesBuilt;

    public Player(int id, PlayerColor color) {
        this.id = id;
        this.color = color;
        this.resourceHand = new HashMap<>();
        this.victoryPoints = 0;
        this.roadsBuilt = 0;
        this.settlementsBuilt = 0;
        this.citiesBuilt = 0;
    }

    public int getId() {
        return id;
    }

    public PlayerColor getColor() {
        return color;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public void addResources(ResourceType type, int amount) {
        resourceHand.put(type, resourceHand.getOrDefault(type, 0) + amount);
    }

    public void removeResources(ResourceType type, int amount) {
    }

    public boolean canAfford(Map<ResourceType, Integer> cost) {
        return false;
    }

    public void makeMove(Board board) {
    }

    public boolean buildRoad(Path path) {
        return false;
    }

    public boolean buildSettlement(Intersection intersection) {
        return false;
    }

    public boolean upgradeCity(Intersection intersection) {
        return false;
    }

    public void discardHalfHand() {
    }

    public void recalculateVictoryPoints() {
    }
    
}
