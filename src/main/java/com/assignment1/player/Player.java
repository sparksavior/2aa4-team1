package com.assignment1.player;

import com.assignment1.board.Board;
import com.assignment1.board.Intersection;
import com.assignment1.board.Path;
import com.assignment1.enums.PlayerColor;
import com.assignment1.enums.ResourceType;

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
    }

    public int getId() {
        return 0;
    }

    public PlayerColor getColor() {
        return null;
    }

    public int getVictoryPoints() {
        return 0;
    }

    public void addResources(ResourceType type, int amount) {
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
