package com.assignment1.command;

/**
 * Result of command execution with metadata about command behavior.
 * Encapsulates all information the client needs without knowing concrete command types.
 */
public class CommandResult {

    private final String message;
    private final boolean shouldEndTurn; // whether the turn should end after the command is executed
    private final boolean shouldDisplay; // whether the message should be displayed to the user

    private CommandResult(String message, boolean shouldEndTurn, boolean shouldDisplay) {
        this.message = message;
        this.shouldEndTurn = shouldEndTurn;
        this.shouldDisplay = shouldDisplay;
    }

    /**
     * Creates a result for commands that continue the turn.
     * The message will be displayed to the user.
     */
    public static CommandResult continueTurn(String message) {
        return new CommandResult(message, false, true);
    }

    /**
     * Creates a result for commands that end the turn (GoCommand, RollCommand).
     * The message will be displayed and the turn will end.
     */
    public static CommandResult endTurn(String message) {
        return new CommandResult(message, true, true);
    }

    /**
     * Creates a result for reversible commands that continue the turn (BuildCommand).
     * The command should record itself to history if successful.
     */
    public static CommandResult reversible(String message) {
        return new CommandResult(message, false, true);
    }

    /**
     * Creates a result for commands that should not display anything.
     */
    public static CommandResult silent(String message) {
        return new CommandResult(message, false, false);
    }

    public String getMessage() {
        return message;
    }

    public boolean shouldEndTurn() {
        return shouldEndTurn;
    }

    public boolean shouldDisplay() {
        return shouldDisplay;
    }

    /**
     * Checks if the command execution was successful.
     * A command is considered failed if the message contains "failed".
     */
    public boolean isSuccess() {
        return message != null && !message.contains("failed");
    }
}
