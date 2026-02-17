package com.assignment1;

import com.assignment1.board.Board;
import com.assignment1.board.Intersection;
import com.assignment1.config.GameConfig;
import com.assignment1.enums.PlayerColor;
import com.assignment1.enums.ResourceType;
import com.assignment1.player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

public class PlayerTest {
    @Test
    void addAndRemoveResources_updatesHand() {
        Player player = new Player(0, PlayerColor.BLUE);

        player.addResources(ResourceType.WOOD, 3);
        player.addResources(ResourceType.WHEAT, 2);

        assertTrue(player.canAfford(Map.of(ResourceType.WOOD, 3, ResourceType.WHEAT, 2)), "Player should afford exact resources");
        player.removeResources(ResourceType.WOOD, 3);

        assertFalse(player.canAfford(java.util.Map.of(ResourceType.WOOD, 1)), "Player should not have wood after removing all");

        assertTrue(player.canAfford(java.util.Map.of(ResourceType.WHEAT, 2)), "Player should still have wheat");
    }

    @Test
    void canAffordPartitionTesting(){
        Player player = new Player(1, PlayerColor.RED);
        player.addResources(ResourceType.WOOD, 3);

        // Partition 1: exactly enough
        assertTrue(player.canAfford(Map.of(ResourceType.WOOD, 3)), "Exact amount should be affordable");

        // Partition 2: more than enough
        assertTrue(player.canAfford(java.util.Map.of(ResourceType.WOOD, 2)), "Less than available should be affordable");

        // Partition 3: less than required
        assertFalse(player.canAfford(java.util.Map.of(ResourceType.WOOD, 4)), "More than available should not be affordable");

        // Partition 4: resource not owned
        assertFalse(player.canAfford(java.util.Map.of(ResourceType.BRICK, 1)), "Missing resource type should not be affordable");
    }

    @Test
    void buildSettlement_succeedsWhenValid(){
        Board board = new Board(new GameConfig(100, 10));
        board.setup();

        Player player = new Player(2, PlayerColor.BLUE);

        player.addResources(ResourceType.BRICK, 1);
        player.addResources(ResourceType.WOOD, 1);
        player.addResources(ResourceType.WHEAT, 1);
        player.addResources(ResourceType.SHEEP, 1);

        Intersection intersection = board.getIntersectionById(0);

        boolean result = player.buildSettlement(board, intersection);

        assertTrue(result, "Settlement should be built when affordable and valid");
        assertNotNull(intersection.getOccupant(), "Intersection should now have a building");
        assertEquals(player, intersection.getOccupant().getOwner(), "Building owner should be the player");
    }

    @Test
    void buildSettlement_failsWithoutRequiredResources(){
        Board board = new Board(new GameConfig(100, 10));
        board.setup();

        Player player = new Player(3, PlayerColor.RED);


        Intersection intersection = board.getIntersectionById(1);

        boolean result = player.buildSettlement(board, intersection);

        assertFalse(result, "Settlement should fail when player cannot afford it");
        assertNull(intersection.getOccupant(), "Intersection should remain empty if build fails");
    }

    @Test
    void buildSettlements_shouldIncreaseVP(){
        Board board = new Board(new GameConfig(100, 10));
        board.setup();

        Player player = new Player(4, PlayerColor.BLUE);

        player.addResources(ResourceType.BRICK, 1);
        player.addResources(ResourceType.WOOD, 1);
        player.addResources(ResourceType.WHEAT, 1);
        player.addResources(ResourceType.SHEEP, 1);

        Intersection intersection = board.getIntersectionById(2);

        int before = player.getVictoryPoints();

        boolean built = player.buildSettlement(board, intersection);

        assertTrue(built, "Settlement should build successfully");
        assertEquals(before + 1, player.getVictoryPoints(), "Victory points should increase by 1 after building settlement");
    }
}
