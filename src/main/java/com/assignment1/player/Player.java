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

/** Represents a player with resources, buildings, and agent behavior. */
public class Player {

    private int id;
    private PlayerColor color;
    private Map<ResourceType, Integer> resourceHand;
    private int victoryPoints;
    private int roadsBuilt;
    private int settlementsBuilt;
    private int citiesBuilt;

    /** Creates a player with the given ID and color. */
    public Player(int id, PlayerColor color) {
        this.id = id;
        this.color = color;
        this.resourceHand = new HashMap<>();
        this.victoryPoints = 0;
        this.roadsBuilt = 0;
        this.settlementsBuilt = 0;
        this.citiesBuilt = 0;
    }

    /** Creates a player with an initial settlement at the given intersection. */
    public Player(int id, PlayerColor color, Intersection initialSettlement) {
        this.id = id;
        this.color = color;
        this.resourceHand = new HashMap<>();
        this.victoryPoints = 0;
        this.roadsBuilt = 0;
        this.settlementsBuilt = 0;
        this.citiesBuilt = 0;
        
        if (initialSettlement != null) {
            initialSettlement.setOccupant(new Settlement(this));
            settlementsBuilt++;
            recalculateVictoryPoints();
        }
    }

    /** Returns the player's ID. */
    public int getId() {
        return id;
    }

    /** Returns the player's color. */
    public PlayerColor getColor() {
        return color;
    }

    /** Returns the player's current victory points. */
    public int getVictoryPoints() {
        return victoryPoints;
    }

    /** Adds the specified amount of the given resource type to the player's hand. */
    public void addResources(ResourceType type, int amount) {
        resourceHand.put(type, resourceHand.getOrDefault(type, 0) + amount);
    }

    /** Removes the specified amount of the given resource type from the player's hand. */
    public void removeResources(ResourceType type, int amount) {
        int current = resourceHand.getOrDefault(type, 0);
        int updated = current - amount;
        if (updated <= 0) {
            resourceHand.remove(type);
        } else {
            resourceHand.put(type, updated);
        }
    }

    /** Checks if the player has enough resources to afford the given cost. */
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

    /** Agent behavior: attempts to build when holding more than 7 cards. */
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

    /** Attempts to build a settlement at the given intersection if affordable and valid. */
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

    /** Upgrades a settlement to a city if the player owns it and can afford the cost. */
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

    /** Handles dice roll 7: discards half hand if holding more than 7 cards. */
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

    /** Recalculates victory points based on built settlements and cities. */
    public void recalculateVictoryPoints() {
        this.victoryPoints = settlementsBuilt * 1 + citiesBuilt * 2;
    }

    
}
