package com.assignment1.core;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandParser {

    private static final Pattern ROLL_PATTERN = Pattern.compile("(?i)^Roll$");
    private static final Pattern GO_PATTERN = Pattern.compile("(?i)^Go$");
    private static final Pattern LIST_PATTERN = Pattern.compile("(?i)^List$");

    // Example: Build settlement 5
    private static final Pattern BUILD_SETTLEMENT_PATTERN = Pattern.compile("(?i)^Build\\s+settlement\\s+(\\d+)$");

    // Example: Build city 5
    private static final Pattern BUILD_CITY_PATTERN = Pattern.compile("(?i)^Build\\s+city\\s+(\\d+)$");

    // Example: Build road 5, 8 or Build road 5 8
    private static final Pattern BUILD_ROAD_PATTERN = Pattern.compile("(?i)^Build\\s+road\\s+(\\d+)[,\\s]+(\\d+)$");

    public ParsedCommand parse(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Input cannot be empty");
        }

        input = input.trim();

        if (ROLL_PATTERN.matcher(input).matches()) {
            return new ParsedCommand(ParsedCommand.ActionType.ROLL);
        }
        if (GO_PATTERN.matcher(input).matches()) {
            return new ParsedCommand(ParsedCommand.ActionType.GO);
        }
        if (LIST_PATTERN.matcher(input).matches()) {
            return new ParsedCommand(ParsedCommand.ActionType.LIST);
        }

        Matcher settlementMatcher = BUILD_SETTLEMENT_PATTERN.matcher(input);
        if (settlementMatcher.matches()) {
            String nodeId = settlementMatcher.group(1);
            return new ParsedCommand(ParsedCommand.ActionType.BUILD_SETTLEMENT, Arrays.asList(nodeId));
        }

        Matcher cityMatcher = BUILD_CITY_PATTERN.matcher(input);
        if (cityMatcher.matches()) {
            String nodeId = cityMatcher.group(1);
            return new ParsedCommand(ParsedCommand.ActionType.BUILD_CITY, Arrays.asList(nodeId));
        }

        Matcher roadMatcher = BUILD_ROAD_PATTERN.matcher(input);
        if (roadMatcher.matches()) {
            String fromNode = roadMatcher.group(1);
            String toNode = roadMatcher.group(2);
            return new ParsedCommand(ParsedCommand.ActionType.BUILD_ROAD, Arrays.asList(fromNode, toNode));
        }

        throw new IllegalArgumentException("Invalid command: " + input);
    }
}
