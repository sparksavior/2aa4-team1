package com.assignment1;

import com.assignment1.board.Board;
import com.assignment1.board.Intersection;
import com.assignment1.command.BuildCommand;
import com.assignment1.command.GoCommand;
import com.assignment1.command.ListCommand;
import com.assignment1.command.RollCommand;
import com.assignment1.config.GameConfig;
import com.assignment1.enums.BuildType;
import com.assignment1.enums.PlayerColor;
import com.assignment1.enums.ResourceType;
import com.assignment1.player.ComputerPlayer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandTest {

    @Test
    void goCommand_returnsGo() {
        GoCommand cmd = new GoCommand();
        Board board = new Board(new GameConfig(100, 10));
        board.setup();
        ComputerPlayer p = new ComputerPlayer(0, PlayerColor.RED);

        assertEquals("go", cmd.execute(p, board));
    }

    @Test
    void rollCommand_returnsRoll() {
        RollCommand cmd = new RollCommand();
        Board board = new Board(new GameConfig(100, 10));
        board.setup();
        ComputerPlayer p = new ComputerPlayer(0, PlayerColor.RED);

        assertEquals("roll", cmd.execute(p, board));
    }

    @Test
    void listCommand_includesPlayerIdAndHandSummary() {
        ListCommand cmd = new ListCommand();
        Board board = new Board(new GameConfig(100, 10));
        board.setup();

        ComputerPlayer p = new ComputerPlayer(7, PlayerColor.BLUE);
        p.addResources(ResourceType.WOOD, 2);

        String out = cmd.execute(p, board);
        assertTrue(out.contains("player 7 hand:"), "Output should include player id");
        assertTrue(out.contains("WOOD"), "Output should include resource summary");
    }

    @Test
    void buildCommand_settlement_invalidIntersectionIdFails() {
        Board board = new Board(new GameConfig(100, 10));
        board.setup();
        ComputerPlayer p = new ComputerPlayer(0, PlayerColor.RED);

        BuildCommand cmd = new BuildCommand(BuildType.SETTLEMENT, 9999);
        String out = cmd.execute(p, board);

        assertTrue(out.startsWith("build settlement failed: invalid intersection id"),
                "Should return invalid intersection message");
    }

    @Test
    void buildCommand_settlement_successBuildsAndReturnsExpectedString() {
        Board board = new Board(new GameConfig(100, 10));
        board.setup();

        ComputerPlayer p = new ComputerPlayer(0, PlayerColor.RED);
        p.addResources(ResourceType.BRICK, 1);
        p.addResources(ResourceType.WOOD, 1);
        p.addResources(ResourceType.WHEAT, 1);
        p.addResources(ResourceType.SHEEP, 1);

        int nodeId = 10;
        Intersection i = board.getIntersectionById(nodeId);
        assertNotNull(i);

        BuildCommand cmd = new BuildCommand(BuildType.SETTLEMENT, nodeId);
        String out = cmd.execute(p, board);

        assertEquals("build settlement " + nodeId, out);
        assertNotNull(i.getOccupant(), "Intersection should be occupied after successful build");
    }

    @Test
    void buildCommand_road_invalidPathIdFails() {
        Board board = new Board(new GameConfig(100, 10));
        board.setup();
        ComputerPlayer p = new ComputerPlayer(0, PlayerColor.RED);

        BuildCommand cmd = new BuildCommand(BuildType.ROAD, 9999);
        String out = cmd.execute(p, board);

        assertTrue(out.startsWith("build road failed"),
                "Should indicate build road failed");
        assertTrue(out.toLowerCase().contains("path") || out.toLowerCase().contains("id"),
                "Should mention path/id in the error message");
    }


}