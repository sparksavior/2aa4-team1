package com.assignment1.ai.rules;

import com.assignment1.ai.ConstraintRule;
import com.assignment1.board.Board;
import com.assignment1.board.Intersection;
import com.assignment1.board.Path;
import com.assignment1.player.Player;

/**
 * Concrete ConstraintRule that represent the constraint:
 * - if there are more than 7 cards in hand, the player must spend them
 */
public class ExcessCardsConstraint extends ConstraintRule {

    private static final double PRIORITY = 10.0;

    @Override
    public double evaluate(Player player, Board board) {
        if (canApply(player, board)) {
            return PRIORITY;
        }
        return 0.0;
    }

    @Override
    public boolean canApply(Player player, Board board) {
        return player.getTotalCards() > 7;
    }

    @Override
    public String apply(Player player, Board board) {
        StringBuilder actions = new StringBuilder();

        // Try to spend cards by building things until hand size <= 7
        while (player.getTotalCards() > 7) {
            boolean spent = false;

            // Try upgrading a settlement to a city (costs 2 Wheat + 3 Ore = 5 cards)
            for (Intersection i : board.getIntersections()) {
                if (i.getOccupant() != null && i.getOccupant().getOwner() == player) {
                    if (player.upgradeCity(i)) {
                        actions.append("Upgraded settlement to city. ");
                        spent = true;
                        break;
                    }
                }
            }
            if (spent && player.getTotalCards() <= 7) break;

            // Try building a settlement (costs 1 Brick + 1 Wood + 1 Wheat + 1 Sheep = 4 cards)
            if (!spent) {
                for (Intersection i : board.getIntersections()) {
                    if (i.getOccupant() == null && board.canPlaceSettlement(i, player)) {
                        if (player.buildSettlement(board, i)) {
                            actions.append("Built settlement. ");
                            spent = true;
                            break;
                        }
                    }
                }
            }
            if (spent && player.getTotalCards() <= 7) break;

            // Try building a road (costs 1 Brick + 1 Wood = 2 cards)
            if (!spent) {
                Path extensionPath = board.findExtensionPath(player);
                if (extensionPath != null && player.buildRoad(board, extensionPath)) {
                    actions.append("Built road. ");
                    spent = true;
                }
            }
            if (spent && player.getTotalCards() <= 7) break;

            // Could not spend any more cards
            if (!spent) break;
        }

        if (actions.length() == 0) {
            return "Has >7 cards but could not spend any resources.";
        }
        return "Spent resources to reduce hand size: " + actions.toString().trim();
    }

}
