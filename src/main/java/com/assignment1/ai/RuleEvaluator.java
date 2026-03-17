package com.assignment1.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.assignment1.board.Board;
import com.assignment1.player.Player;

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

        // Add value rules
        valueRules.add(new com.assignment1.ai.rules.VictoryPointRule());
        valueRules.add(new com.assignment1.ai.rules.BuildRule());
        valueRules.add(new com.assignment1.ai.rules.SpendRule());

        // Constraints 
        constraintRules.add(new com.assignment1.ai.rules.ExcessCardsConstraint());
        constraintRules.add(new com.assignment1.ai.rules.RoadConnectionConstraint());
        constraintRules.add(new com.assignment1.ai.rules.LongestRoadDefenseConstraint());
    }

    public Optional<Rule> evaluate(Player player, Board board) {
        // Check constraints first
        Rule bestConstraint = null;
        double highestPriority = -1;
        for (ConstraintRule constraint : constraintRules) {
            if (constraint.canApply(player, board)) {
                double priority = constraint.evaluate(player, board);

                if (priority > highestPriority) {
                    highestPriority = priority;
                    bestConstraint = constraint;
                }
            }
        }
        if (bestConstraint != null && highestPriority > 0) {
            return Optional.of(bestConstraint);
        }

        // Evaluate value rules
        Rule bestRule = null;
        double bestValue = -1;
        for (ValueRule rule : valueRules) {
            if (rule.canApply(player, board)) {
                double value = rule.evaluate(player, board);
                if (value > bestValue) {
                    bestValue = value;
                    bestRule = rule;
                }
            }
        }
        return Optional.ofNullable(bestRule);
    }

    public List<Rule> getAll() {
        List<Rule> all = new ArrayList<>();
        all.addAll(constraintRules);
        all.addAll(valueRules);
        return all;
    }
}
