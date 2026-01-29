package catan.simulation;

import java.util.List;

public class Simulator {

    private List<Player> players;
    private Board board;
    private GameConfig config;
    private int currentRound;

    public Simulator(GameConfig config) {
        this.config = config;
    }

    public void run() {}

    public void playRound() {}

    public void takeTurn(Player player) {}

    public boolean isFinished() {
        return false;
    }

    public int rollDice() {
        return 0;
    }

    public void distributeResources(int diceRoll) {}
}