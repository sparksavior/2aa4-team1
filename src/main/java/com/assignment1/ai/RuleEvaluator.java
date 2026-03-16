package com.assignment1.ai;

import com.assignment1.board.Board;
import com.assignment1.player.Player;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Evaluates the rules and returns the best rule to apply.
 * Integrate with ComputerPlayer.makeMove()
 * 
 */
public class RuleEvaluator {

    private List<ValueRule> valueRules;
    private List<ConstraintRule> constraintRules;

    public RuleEvaluator() {
        // TODO: Initialize the lists
    }

    public Optional<Rule> evaluate(Player player, Board board) {
        // TODO: Evaluate the rules and return the best rule
        // Step 1: check constraints first (chain of responsibility pattern)
        // Step 2: evaluate value rules (strategy pattern)
        return Optional.empty();
    }

    public List<Rule> getAll() {
        // TODO: Return all the rules
        return new ArrayList<>();
    }
    
}
