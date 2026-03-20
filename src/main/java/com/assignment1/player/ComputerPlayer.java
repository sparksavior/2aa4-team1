package com.assignment1.player;

import java.util.Optional;

import com.assignment1.ai.Rule;
import com.assignment1.ai.RuleEvaluator;
import com.assignment1.board.Board;
import com.assignment1.board.Intersection;
import com.assignment1.enums.PlayerColor;

// This class represents the computer player in the game with automated agent behavior.
public class ComputerPlayer extends Player {

    private RuleEvaluator evaluator;

    // Creates a computer player with the given ID and color.
    public ComputerPlayer(int id, PlayerColor color, RuleEvaluator evaluator) {
        super(id, color);
        this.evaluator = evaluator;
    }

    /**
     * Creates a computer player with a dedicated RuleEvaluator (for unit testing)
     * @param id
     * @param color
     */
    public ComputerPlayer(int id, PlayerColor color) {
        this(id, color, new RuleEvaluator());
    }

    // Creates a computer player with an initial settlement at the given intersection.
    public ComputerPlayer(int id, PlayerColor color, Intersection initialSettlement, RuleEvaluator evaluator) {
        super(id, color, initialSettlement);
        this.evaluator = evaluator;
    }

    // Agent behavior: attempts to build when holding more than 7 cards.
    @Override
    public String makeMove(Board board) {
        if (this.evaluator == null) {
            this.evaluator = new RuleEvaluator();
        }

        Optional<Rule> rule = this.evaluator.evaluate(this, board);
        if (rule.isPresent()) {
            return rule.get().apply(this, board);
        }
        return "no-op";
    }
}
