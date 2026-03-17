package com.assignment1.ai;

import com.assignment1.board.Board;
import com.assignment1.player.Player;

import com.assignment1.ai.rules.ExcessCardsConstraint;
import com.assignment1.ai.rules.LongestRoadDefenseConstraint;
import com.assignment1.ai.rules.RoadConnectionConstraint;

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
        this.valueRules = new ArrayList<>();
        this.constraintRules = new ArrayList<>();
        
        this.constraintRules.add(new ExcessCardsConstraint());
        this.constraintRules.add(new RoadConnectionConstraint());
        this.constraintRules.add(new LongestRoadDefenseConstraint());
    }

    public Optional<Rule> evaluate(Player player, Board board) {
        for (ConstraintRule constraint : constraintRules) {
            if (constraint.evaluate(player, board) > 0.0) {
                return Optional.of(constraint);
            }
        }
        
        return Optional.empty();
    }

    public List<Rule> getAll() {
        List<Rule> all = new ArrayList<>();
        all.addAll(constraintRules);
        all.addAll(valueRules);
        return all;
    }
    
}
