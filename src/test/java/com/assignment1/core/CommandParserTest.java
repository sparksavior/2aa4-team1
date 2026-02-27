package com.assignment1.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommandParserTest {

    private CommandParser parser;

    @BeforeEach
    public void setUp() {
        parser = new CommandParser();
    }

    @Test
    public void testParseRollCommand() {
        ParsedCommand cmd = parser.parse("Roll");
        assertEquals(ParsedCommand.ActionType.ROLL, cmd.getActionType());

        // Test case insensitivity
        assertEquals(ParsedCommand.ActionType.ROLL, parser.parse("rOlL").getActionType());
    }

    @Test
    public void testParseGoCommand() {
        ParsedCommand cmd = parser.parse("Go");
        assertEquals(ParsedCommand.ActionType.GO, cmd.getActionType());
    }

    @Test
    public void testParseListCommand() {
        ParsedCommand cmd = parser.parse("List");
        assertEquals(ParsedCommand.ActionType.LIST, cmd.getActionType());
    }

    @Test
    public void testParseBuildSettlementCommand() {
        ParsedCommand cmd = parser.parse("Build settlement 5");
        assertEquals(ParsedCommand.ActionType.BUILD_SETTLEMENT, cmd.getActionType());
        assertEquals(1, cmd.getArgs().size());
        assertEquals("5", cmd.getArgs().get(0));
    }

    @Test
    public void testParseBuildCityCommand() {
        ParsedCommand cmd = parser.parse("build city 12");
        assertEquals(ParsedCommand.ActionType.BUILD_CITY, cmd.getActionType());
        assertEquals(1, cmd.getArgs().size());
        assertEquals("12", cmd.getArgs().get(0));
    }

    @Test
    public void testParseBuildRoadCommand() {
        // Test with comma
        ParsedCommand cmd = parser.parse("Build road 4, 5");
        assertEquals(ParsedCommand.ActionType.BUILD_ROAD, cmd.getActionType());
        assertEquals(2, cmd.getArgs().size());
        assertEquals("4", cmd.getArgs().get(0));
        assertEquals("5", cmd.getArgs().get(1));

        // Test with space only
        ParsedCommand cmd2 = parser.parse("Build road 4 5");
        assertEquals(ParsedCommand.ActionType.BUILD_ROAD, cmd2.getActionType());
        assertEquals("4", cmd2.getArgs().get(0));
        assertEquals("5", cmd2.getArgs().get(1));
    }

    @Test
    public void testEmptyOrNullCommand() {
        try {
            parser.parse("");
            fail("Expected IllegalArgumentException for empty string");
        } catch (IllegalArgumentException expected) {
            // Test passes
        }

        try {
            parser.parse("   ");
            fail("Expected IllegalArgumentException for blank string");
        } catch (IllegalArgumentException expected) {
            // Test passes
        }

        try {
            parser.parse(null);
            fail("Expected IllegalArgumentException for null");
        } catch (IllegalArgumentException expected) {
            // Test passes
        }
    }

    @Test
    public void testInvalidCommand() {
        try {
            parser.parse("Jump");
            fail("Expected IllegalArgumentException for invalid action");
        } catch (IllegalArgumentException expected) {
            // Test passes
        }

        try {
            parser.parse("Build settlement"); // Missing arguments
            fail("Expected IllegalArgumentException for missing settlement param");
        } catch (IllegalArgumentException expected) {
            // Test passes
        }

        try {
            parser.parse("Build road 5"); // Missing argument
            fail("Expected IllegalArgumentException for missing 2nd road param");
        } catch (IllegalArgumentException expected) {
            // Test passes
        }
    }
}
