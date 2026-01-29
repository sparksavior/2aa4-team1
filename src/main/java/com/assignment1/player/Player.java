package com.assignment1.player;

import com.assignment1.board.Board;
import com.assignment1.board.Intersection;
import com.assignment1.board.Path;
import com.assignment1.enums.PlayerColor;
import com.assignment1.enums.ResourceType;
import com.assignment1.pieces.City;
import com.assignment1.pieces.Road;
import com.assignment1.pieces.Settlement;
import com.assignment1.pieces.Building;

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
        int current = resourceHand.getOrDefault(type, 0);
        int updated = current - amount;
        if (updated <= 0) {
            resourceHand.remove(type);
        } else {
            resourceHand.put(type, updated);
        }
    }

    public boolean canAfford(Map<ResourceType, Integer> cost) {
        for (Map.Entry<ResourceType, Integer> entry : cost.entrySet()) {
            ResourceType type = entry.getKey();
            int required = entry.getValue();
            int available = resourceHand.getOrDefault(type, 0);
            if (available < required) {
                return false;
            }
        }
        return true;
    }

    public void makeMove(Board board) {

        int totalCards = 0;
        for (ResourceType type : ResourceType.values()) {
            totalCards += resourceHand.getOrDefault(type, 0);
        }
        if (totalCards <= 7) {
            return; // only build when holding > 7 cards
        }

        // try to upgrade a settlement to a city
        for (Intersection intersection : board.getIntersections()) {
            Building occupant = intersection.getOccupant();
            // currently holding a Settlement
            if (occupant instanceof Settlement && occupant.getOwner() == this) {
                if (upgradeCity(intersection)) return;
            }
        }

        // try to build a new settlement
        for (Intersection intersection : board.getIntersections()) {
            // no building on this intersection
            if (intersection.getOccupant() == null) {
                if (buildSettlement(intersection)) return;
            }
        }

        // try to build a road
        for (Path path : board.getPaths()) {
            // no building on this path
            if (path.getOccupant() == null) {
                if (buildRoad(path)) return;
            }
        }
    }

    public boolean buildRoad(Path path) {

        // already has a road
        if (path.getOccupant() != null) {
            return false;
        }

        Map<ResourceType, Integer> cost = new HashMap<>();
        cost.put(ResourceType.BRICK, 1);
        cost.put(ResourceType.WOOD, 1);

        if (!canAfford(cost)) {
            return false;
        }

        for (Map.Entry<ResourceType, Integer> entry : cost.entrySet()) {
            removeResources(entry.getKey(), entry.getValue());
        }

        path.setOccupant(new Road(this));
        roadsBuilt++;
        recalculateVictoryPoints();
        return true;
    }

    public boolean buildSettlement(Intersection intersection) {

        // already has a building
        if (intersection.getOccupant() != null) {
            return false;
        }

        Map<ResourceType, Integer> cost = new HashMap<>();
        cost.put(ResourceType.BRICK, 1);
        cost.put(ResourceType.WOOD, 1);
        cost.put(ResourceType.WHEAT, 1);
        cost.put(ResourceType.SHEEP, 1);

        if (!canAfford(cost)) {
            return false;
        }

        for (Map.Entry<ResourceType, Integer> entry : cost.entrySet()) {
            removeResources(entry.getKey(), entry.getValue());
        }

        intersection.setOccupant(new Settlement(this));
        settlementsBuilt++;
        recalculateVictoryPoints();
        return true;
    }

    public boolean upgradeCity(Intersection intersection) {
        Building building = intersection.getOccupant();
        if (!(building instanceof Settlement) || building.getOwner() != this) {
            return false;
        }

        Map<ResourceType, Integer> cost = new HashMap<>();
        cost.put(ResourceType.WHEAT, 2);
        cost.put(ResourceType.ORE, 3);

        if (!canAfford(cost)) {
            return false;
        }

        for (Map.Entry<ResourceType, Integer> entry : cost.entrySet()) {
            removeResources(entry.getKey(), entry.getValue());
        }

        intersection.setOccupant(new City(this));
        settlementsBuilt--;
        citiesBuilt++;
        recalculateVictoryPoints();
        return true;
    }

    public void discardHalfHand() {
        int total = 0;
        for (ResourceType type : ResourceType.values()) {
            total += resourceHand.getOrDefault(type, 0);
        }
        int totalDiscard = total / 2;
        if (totalDiscard <= 0) return;

        for (ResourceType type : ResourceType.values()) {
            if (totalDiscard == 0) break;

            int have = resourceHand.getOrDefault(type, 0);
            if (have == 0) continue;

            int discardAmount = Math.min(have, totalDiscard);
            removeResources(type, discardAmount);
            totalDiscard -= discardAmount;
        }
    }

    public void recalculateVictoryPoints() {
        this.victoryPoints = settlementsBuilt * 1 + citiesBuilt * 2;
    }
    
}
