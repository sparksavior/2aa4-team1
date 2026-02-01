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
import java.util.Optional;

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

    public String makeMove(Board board) {

        if (getTotalCards() <= 7) return "no-op";

        return tryUpgradeCity(board)
            .or(() -> tryBuildSettlement(board))
            .or(() -> tryBuildRoad(board))
            .orElse("no-op");
    }

    private int getTotalCards() {
        int total = 0;
        for (ResourceType type : ResourceType.values()) {
            total += resourceHand.getOrDefault(type, 0);
        }
        return total;
    }

    private Optional<String> tryUpgradeCity(Board board) {
        for (Intersection intersection : board.getIntersections()) {
            Building occupant = intersection.getOccupant();
            // currently holding a Settlement
            if (occupant instanceof Settlement && occupant.getOwner() == this) {
                if (upgradeCity(intersection)) {
                    return Optional.of("upgrade city");
                }
            }
        }
        return Optional.empty();
    }

    private Optional<String> tryBuildSettlement(Board board) {
        for (Intersection intersection : board.getIntersections()) {
            if (intersection.getOccupant() == null && buildSettlement(board, intersection)) {
                return Optional.of("build settlement");
            }
        }
        return Optional.empty(); // no settlement to build
    }

    private Optional<String> tryBuildRoad(Board board) {
        for (Path path : board.getPaths()) {
            if (path.getOccupant() == null && buildRoad(board, path)) {
                return Optional.of("build road");
            }
        }
        return Optional.empty(); // no road to build
    }

    public boolean buildRoad(Board board, Path path) {
        if (path.getOccupant() != null) return false;
        if (!board.canPlaceRoad(path, this)) return false;

        Map<ResourceType, Integer> cost = new HashMap<>();
        cost.put(ResourceType.BRICK, 1);
        cost.put(ResourceType.WOOD, 1);

        if (!canAfford(cost)) return false;

        for (Map.Entry<ResourceType, Integer> entry : cost.entrySet()) {
            removeResources(entry.getKey(), entry.getValue());
        }

        path.setOccupant(new Road(this));
        roadsBuilt++;
        recalculateVictoryPoints();
        return true;
    }

    public boolean buildSettlement(Board board, Intersection intersection) {
        if (intersection.getOccupant() != null) return false;
        if (!board.canPlaceSettlement(intersection, this)) return false;

        Map<ResourceType, Integer> cost = new HashMap<>();
        cost.put(ResourceType.BRICK, 1);
        cost.put(ResourceType.WOOD, 1);
        cost.put(ResourceType.WHEAT, 1);
        cost.put(ResourceType.SHEEP, 1);

        if (!canAfford(cost)) return false;

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

    public void handleDiceRoll7() {
        if (getTotalCards() > 7) {
            discardHalfHand();
        }
    }

    private void discardHalfHand() {
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

    // initial setup: place settlement without cost
    public void placeInitialSettlement(Intersection intersection) {
        intersection.setOccupant(new Settlement(this));
        settlementsBuilt++;
        recalculateVictoryPoints();
    }
    
}
