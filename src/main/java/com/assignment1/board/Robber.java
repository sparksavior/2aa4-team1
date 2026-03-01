package com.assignment1.board;

import com.assignment1.enums.ResourceType;
import com.assignment1.player.Player;

import java.util.Random;
import java.util.TreeMap;

// This class represents the robber that blocks resource production and allows card stealing
public class Robber {

    private Tile currentTile;
    private final Random random;

    // Creates a new robber
    public Robber() {
        this.currentTile = null;
        this.random = new Random();
    }

    // Returns the tile where the robber is currently placed, or null if not placed
    public Tile getCurrentTile() {
        return currentTile;
    }

    // Moves the robber to the specified tile
    public void moveTo(Tile tile) {
        this.currentTile = tile;
    }

    // Steals a random resource from fromPlayer and gives it to toPlayer
    public void steal(Player fromPlayer, Player toPlayer) {
        if (fromPlayer == null || toPlayer == null) {
            return;
        }

        TreeMap<Integer, ResourceType> intervalMap = new TreeMap<>();
        
        int totalCount = 0;
        for (ResourceType type : ResourceType.values()) {
            int count = fromPlayer.getResourceCount(type);
            if (count > 0) {
                totalCount += count;
                intervalMap.put(totalCount, type);
            }
        }

        // no recourses to steal from fromPlayer
        if (totalCount == 0) {
            return;
        }

        // randomly select a card index in [1, totalCount]
        int selectedIndex = random.nextInt(totalCount) + 1;

        // find the resource type corresponding to the selected index
        ResourceType stolenType = intervalMap.ceilingEntry(selectedIndex).getValue();

        // transfer the resource from fromPlayer to toPlayer
        fromPlayer.removeResources(stolenType, 1);
        toPlayer.addResources(stolenType, 1);
    }
}
