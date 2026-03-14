package com.assignment1.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.assignment1.command.BuildCommand;
import com.assignment1.command.Command;
import com.assignment1.command.GoCommand;
import com.assignment1.command.ListCommand;
import com.assignment1.command.RollCommand;
import com.assignment1.enums.BuildType;
import com.assignment1.command.RedoCommand;
import com.assignment1.command.UndoCommand;

public class CommandParser {

    private static final Pattern ROLL_PATTERN = Pattern.compile("(?i)^Roll$");
    private static final Pattern GO_PATTERN = Pattern.compile("(?i)^Go$");
    private static final Pattern LIST_PATTERN = Pattern.compile("(?i)^List$");
    private static final Pattern UNDO_PATTERN = Pattern.compile("(?i)^Undo$");
    private static final Pattern REDO_PATTERN = Pattern.compile("(?i)^Redo$");

    // Example: Build settlement 5
    private static final Pattern BUILD_SETTLEMENT_PATTERN = Pattern.compile("(?i)^Build\\s+settlement\\s+(\\d+)$");

    // Example: Build city 5
    private static final Pattern BUILD_CITY_PATTERN = Pattern.compile("(?i)^Build\\s+city\\s+(\\d+)$");

    // Example: Build road 5, 8 or Build road 5 8
    private static final Pattern BUILD_ROAD_PATTERN = Pattern.compile("(?i)^Build\\s+road\\s+(\\d+)[,\\s]+(\\d+)$");

    public Command parse(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Input cannot be empty");
        }

        input = input.trim();

        if (ROLL_PATTERN.matcher(input).matches()) {
            return new RollCommand();
        }
        if (GO_PATTERN.matcher(input).matches()) {
            return new GoCommand();
        }
        if (LIST_PATTERN.matcher(input).matches()) {
            return new ListCommand();
        }
        if (UNDO_PATTERN.matcher(input).matches()) {
            return new UndoCommand();
        }
        if (REDO_PATTERN.matcher(input).matches()) {
            return new RedoCommand();
        }

        Matcher settlementMatcher = BUILD_SETTLEMENT_PATTERN.matcher(input);
        if (settlementMatcher.matches()) {
            int nodeId = Integer.parseInt(settlementMatcher.group(1));
            return new BuildCommand(BuildType.SETTLEMENT, nodeId);
        }

        Matcher cityMatcher = BUILD_CITY_PATTERN.matcher(input);
        if (cityMatcher.matches()) {
            int nodeId = Integer.parseInt(cityMatcher.group(1));
            return new BuildCommand(BuildType.CITY, nodeId);
        }

        Matcher roadMatcher = BUILD_ROAD_PATTERN.matcher(input);
        if (roadMatcher.matches()) {
            int fromNode = Integer.parseInt(roadMatcher.group(1));
            int toNode = Integer.parseInt(roadMatcher.group(2));
            return new BuildCommand(fromNode, toNode);
        }

        throw new IllegalArgumentException("Invalid command: " + input);
    }
}
