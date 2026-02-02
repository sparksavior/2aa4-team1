package catan.simulation;

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

    public void addResources(ResourceType type, int amount) {}

    public void removeResources(ResourceType type, int amount) {}

    public boolean canAfford(Map<ResourceType, Integer> cost) {
        return false;
    }

    public void makeMove(Board board) {}

    public boolean buildRoad(Path path) {
        return false;
    }

    public boolean buildSettlement(Intersection intersection) {
        return false;
    }

    public boolean upgradeCity(Intersection intersection) {
        return false;
    }

    public void discardHalfHand() {}

    public void recalculateVictoryPoints() {}
}