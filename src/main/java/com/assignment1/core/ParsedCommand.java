package com.assignment1.core;

import java.util.ArrayList;
import java.util.List;

public class ParsedCommand {
    public enum ActionType {
        ROLL, GO, LIST, BUILD_SETTLEMENT, BUILD_CITY, BUILD_ROAD
    }

    private final ActionType actionType;
    private final List<String> args;

    public ParsedCommand(ActionType actionType) {
        this.actionType = actionType;
        this.args = new ArrayList<>();
    }

    public ParsedCommand(ActionType actionType, List<String> args) {
        this.actionType = actionType;
        this.args = args;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public List<String> getArgs() {
        return args;
    }
}
