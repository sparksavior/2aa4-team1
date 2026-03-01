package com.assignment1.core;

import com.assignment1.board.Board;
import com.assignment1.board.Intersection;
import com.assignment1.board.Tile;
import com.assignment1.config.GameConfig;
import com.assignment1.enums.PlayerColor;
import com.assignment1.enums.ResourceType;
import com.assignment1.enums.TerrainType;
import com.assignment1.pieces.Building;
import com.assignment1.player.Player;
import com.assignment1.player.ComputerPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;

/** Orchestrates the game simulation loop and player turns. */
public class Simulator {

    private Board board;
    private List<Player> players;
    private GameConfig config;
    private int currentRound;

    private final Random random;

    /** Creates a new simulator with the given configuration. */
    public Simulator(GameConfig config) {
        this.config = config;
        this.board = new Board(config);
        this.players = new ArrayList<>();
        this.currentRound = 0;
        this.random = new Random();
    }

    /** Runs the simulation until termination conditions are met. */
    public void run() {

        board.setup();
        createPlayers();

        currentRound = 1;
        while (!isFinished()) {
            playRound();
            currentRound = currentRound + 1;
        }

        for (Player player : players) {
            System.out.println("final vp / player " + player.getId() + ": " + player.getVictoryPoints());
        }
    }

    /** Executes one round: dice roll, resource distribution or discard, and player turns. */
    public void playRound() {
        int diceRoll = rollDice();
        if (diceRoll != 7) {
            distributeResources(diceRoll);
        } else {
            handleDiceRoll7(diceRoll);
        }

        for (Player player : players) {
            takeTurn(player);
        }

        // show round summary
        StringBuilder sb = new StringBuilder();
        sb.append("-- round " + currentRound + " : ");
        for (int i = 0; i < players.size(); i++) {
            sb.append("player " + players.get(i).getId() + ": vp " + players.get(i).getVictoryPoints() + (i == players.size() - 1 ? "" : ", "));
        }
        sb.append("\n");
        System.out.println(sb.toString());
    }

    /** Executes a single player's turn and prints the action. */
    public void takeTurn(Player player) {
        String action = player.makeMove(board);
        System.out.println(currentRound + " / " + player.getId() + ": " + action);
    }

    /** Checks if the simulation should terminate. */
    public boolean isFinished() {
        if (currentRound >= config.getMaxRounds()) {
            return true;
        }

        for (Player player : players) {
            if (player.getVictoryPoints() >= config.getTargetVictoryPoints()) {
                return true;
            }
        }
        return false;
    }

    /** Rolls two six-sided dice and returns the sum. */
    public int rollDice() {
        int first = random.nextInt(6) + 1;
        int second = random.nextInt(6) + 1;
        return first + second;
    }

    /** Delegates resource production to the board based on dice roll. */
    public void distributeResources(int diceRoll) {
        board.produceResources(diceRoll, players);
    }

    /** Handles dice roll 7: discards half hand, places robber, and steals from qualifying player. */
    private void handleDiceRoll7(int diceRoll) {
        // 1. all players discard half hand if holding >7 cards
        for (Player player : players) {
            player.handleDiceRoll7();
        }

        // 2. place robber on random tile (excluding desert)
        Tile robberTile = getRandomTile();
        if (robberTile != null) {
            board.getRobber().moveTo(robberTile);
        }

        // 3. find qualifying players (with buildings adjacent to robber tile)
        List<Player> qualifyingPlayers = getQualifyingPlayers(robberTile);

        // 4. randomly select a qualifying player and steal from them
        if (!qualifyingPlayers.isEmpty()) {
            Player fromPlayer = qualifyingPlayers.get(random.nextInt(qualifyingPlayers.size()));
            Player toPlayer = players.get(0); // TODO: implement full logic for turn tracking
            board.getRobber().steal(fromPlayer, toPlayer);
        }
    }

    /** Selects a random tile for the robber, excluding desert tiles. */
    private Tile getRandomTile() {
        List<Tile> validTiles = new ArrayList<>();
        for (Tile tile : board.getTiles()) {
            if (tile.getTerrain() != TerrainType.DESERT) {
                validTiles.add(tile);
            }
        }
        if (validTiles.isEmpty()) {
            return null;
        }
        return validTiles.get(random.nextInt(validTiles.size()));
    }

    /** Finds players with buildings on intersections adjacent to the robber tile. */
    private List<Player> getQualifyingPlayers(Tile robberTile) {
        Set<Player> seen = new HashSet<>();
        if (robberTile == null) {
            return new ArrayList<>(seen);
        }

        // get all intersections on this tile
        for (Intersection intersection : robberTile.getIntersections()) {
            Building building = intersection.getOccupant();
            if (building != null) {
                Player owner = building.getOwner();
                if (!seen.contains(owner)) {
                    int totalResources =
                        owner.getResourceCount(ResourceType.BRICK) + 
                        owner.getResourceCount(ResourceType.WOOD) + 
                        owner.getResourceCount(ResourceType.WHEAT) +
                        owner.getResourceCount(ResourceType.SHEEP) + 
                        owner.getResourceCount(ResourceType.ORE);
                    if (totalResources > 0) {
                        seen.add(owner);
                    }
                }
            }
        }
        return new ArrayList<>(seen);
    }

    private void createPlayers() {
        // place one settlement per player at valid intersections
        int[] initialIntersectionIds = {0, 24, 32, 40};
        PlayerColor[] colors = {PlayerColor.RED, PlayerColor.BLUE, PlayerColor.WHITE, PlayerColor.ORANGE};

        for (int i = 0; i < initialIntersectionIds.length && i < colors.length; i++) {
            Intersection intersection = board.getIntersectionById(initialIntersectionIds[i]);
            Player player = new ComputerPlayer(i + 1, colors[i], intersection);

            // give initial resources
            player.addResources(ResourceType.BRICK, 1);
            player.addResources(ResourceType.WOOD, 1);
            player.addResources(ResourceType.WHEAT, 1);
            player.addResources(ResourceType.SHEEP, 1);
            player.addResources(ResourceType.ORE, 1);

            players.add(player);
        }
    }
}
