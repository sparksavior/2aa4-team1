package com.assignment1;

import com.assignment1.board.Board;
import com.assignment1.board.Robber;
import com.assignment1.config.GameConfig;
import com.assignment1.enums.PlayerColor;
import com.assignment1.enums.ResourceType;
import com.assignment1.player.ComputerPlayer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RobberTest {
    @Test
    void steal_doesNotWorkForPlayerWithNoResources(){
        Robber robber = new Robber();
        ComputerPlayer from = new ComputerPlayer(1, PlayerColor.RED);
        ComputerPlayer to = new ComputerPlayer(2, PlayerColor.BLUE);

        int beforeTo = to.getResourceCount(ResourceType.WOOD);
        robber.steal(from,to);

        assertEquals(beforeTo, to.getResourceCount(ResourceType.WOOD),"Steal should not change anything if fromPlayer has no resources");

    }

    @Test
    void steal_transfersExactlyOneCard_whenFromPlayerHasResources() {
        Robber robber = new Robber();

        ComputerPlayer from = new ComputerPlayer(1, PlayerColor.RED);
        ComputerPlayer to = new ComputerPlayer(2, PlayerColor.BLUE);

        from.addResources(ResourceType.WOOD, 2);
        from.addResources(ResourceType.BRICK, 1);

        int fromTotalBefore = from.getResourceCount(ResourceType.WOOD) + from.getResourceCount(ResourceType.BRICK);
        int toTotalBefore = to.getResourceCount(ResourceType.WOOD) + to.getResourceCount(ResourceType.BRICK);

        robber.steal(from, to);

        int fromTotalAfter = from.getResourceCount(ResourceType.WOOD) + from.getResourceCount(ResourceType.BRICK);
        int toTotalAfter = to.getResourceCount(ResourceType.WOOD) + to.getResourceCount(ResourceType.BRICK);

        assertEquals(fromTotalBefore - 1, fromTotalAfter, "fromPlayer should lose exactly 1 card");
        assertEquals(toTotalBefore + 1, toTotalAfter, "toPlayer should gain exactly 1 card");
    }

    @Test
    void steal_handlesNullPlayersGracefully() {
        Robber robber = new Robber();
        assertDoesNotThrow(() -> robber.steal(null, null), "Robber.steal should safely return if players are null");
    }

    @Test
    void moveTo_updatesCurrentTile() {
        Board board = new Board(new GameConfig(100, 10));
        board.setup();

        Robber robber = new Robber();
        var tile = board.getTileById(0);

        robber.moveTo(tile);

        assertEquals(tile, robber.getCurrentTile(), "Robber should track the tile it is moved to");
    }
}
