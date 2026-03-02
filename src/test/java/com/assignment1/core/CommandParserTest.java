package com.assignment1.core;

import com.assignment1.command.*;
import com.assignment1.enums.BuildType;
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
        Command cmd = parser.parse("Roll");
        assertTrue(cmd instanceof RollCommand);

        // Test case insensitivity
        assertTrue(parser.parse("rOlL") instanceof RollCommand);
    }

    @Test
    public void testParseGoCommand() {
        Command cmd = parser.parse("Go");
        assertTrue(cmd instanceof GoCommand);
    }

    @Test
    public void testParseListCommand() {
        Command cmd = parser.parse("List");
        assertTrue(cmd instanceof ListCommand);
    }

    @Test
    public void testParseBuildSettlementCommand() {
        Command cmd = parser.parse("Build settlement 5");
        assertTrue(cmd instanceof BuildCommand);
        assertEquals(BuildType.SETTLEMENT, getPrivateField(cmd, "buildType"));
        assertEquals(5, getPrivateField(cmd, "nodeId"));
    }

    @Test
    public void testParseBuildCityCommand() {
        Command cmd = parser.parse("build city 12");
        assertTrue(cmd instanceof BuildCommand);
        assertEquals(BuildType.CITY, getPrivateField(cmd, "buildType"));
        assertEquals(12, getPrivateField(cmd, "nodeId"));
    }

    @Test
    public void testParseBuildRoadCommand() {
        // Test with comma
        Command cmd = parser.parse("Build road 4, 5");
        assertTrue(cmd instanceof BuildCommand);
        assertEquals(BuildType.ROAD, getPrivateField(cmd, "buildType"));
        assertEquals(4, getPrivateField(cmd, "fromNodeId"));
        assertEquals(5, getPrivateField(cmd, "toNodeId"));

        // Test with space only
        Command cmd2 = parser.parse("Build road 4 5");
        assertTrue(cmd2 instanceof BuildCommand);
        assertEquals(BuildType.ROAD, getPrivateField(cmd2, "buildType"));
        assertEquals(4, getPrivateField(cmd2, "fromNodeId"));
        assertEquals(5, getPrivateField(cmd2, "toNodeId"));
    }

    private Object getPrivateField(Object obj, String fieldName) {
        try {
            java.lang.reflect.Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            throw new RuntimeException("Failed to access field " + fieldName, e);
        }
    }

    @Test
    public void testEmptyOrNullCommand() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse(""));
        assertThrows(IllegalArgumentException.class, () -> parser.parse("   "));
        assertThrows(IllegalArgumentException.class, () -> parser.parse(null));
    }

    @Test
    public void testInvalidCommand() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse("Jump"));
        assertThrows(IllegalArgumentException.class, () -> parser.parse("Build settlement"));
        assertThrows(IllegalArgumentException.class, () -> parser.parse("Build road 5"));
    }
}
