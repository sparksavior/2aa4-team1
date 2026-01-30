package com.assignment1.core;

import com.assignment1.board.Board;
import com.assignment1.config.GameConfig;
import com.assignment1.player.Player;

import com.assignment1.enums.PlayerColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulator {

    private Board board;
    private List<Player> players;
    private GameConfig config;
    private int currentRound;

    private final Random random;

    public Simulator(GameConfig config) {
        this.config = config;
        this.board = new Board(config);
        this.players = new ArrayList<>();
        this.players.add(new Player(1, PlayerColor.RED));
        this.players.add(new Player(2, PlayerColor.BLUE));
        this.players.add(new Player(3, PlayerColor.WHITE));
        this.players.add(new Player(4, PlayerColor.ORANGE));
        this.currentRound = 0;
        this.random = new Random();
    }

    public void run() {

        board.setup();

        currentRound = 1;
        while (!isFinished()) {
            playRound();
            currentRound = currentRound + 1;
        }

        for (Player player : players) {
            System.out.println("final vp / player " + player.getId() + ": " + player.getVictoryPoints());
        }
    }

    public void playRound() {
        int diceRoll = rollDice();
        if (diceRoll != 7) {
            distributeResources(diceRoll);
        } else {
            for (Player player : players) {
                player.handleDiceRoll7();
            }
        }

        for (Player player : players) {
            takeTurn(player);
        }
        
        for (Player player : players) {
            System.out.println("round " + currentRound + " / player " + player.getId() + ": vp " + player.getVictoryPoints());
        }
    }

    public void takeTurn(Player player) {
        String action = player.makeMove(board);
        System.out.println(currentRound + " / " + player.getId() + ": " + action);
    }

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

    public int rollDice() {
        int first = random.nextInt(6) + 1;
        int second = random.nextInt(6) + 1;
        return first + second;
    }

    public void distributeResources(int diceRoll) {
        board.produceResources(diceRoll, players);
    }
}
