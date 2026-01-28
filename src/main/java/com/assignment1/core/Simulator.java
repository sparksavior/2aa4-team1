package com.assignment1.core;

import com.assignment1.board.Board;
import com.assignment1.config.GameConfig;
import com.assignment1.player.Player;

import java.util.List;

public class Simulator {

    private Board board;
    private List<Player> players;
    private GameConfig config;
    private int currentRound;

    public Simulator(GameConfig config) {
        this.config = config;
        this.board = null;
        this.players = null;
        this.currentRound = 0;
    }

    public void run() {
        System.out.println("simulator started");
    }

    public void playRound() {
    }

    public void takeTurn(Player player) {
    }

    public boolean isFinished() {
        return false;
    }

    public int rollDice() {
        return 0;
    }

    public void distributeResources(int diceRoll) {
    }
}
