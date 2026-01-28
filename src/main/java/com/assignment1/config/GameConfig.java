package com.assignment1.config;

public class GameConfig {

    private int maxRounds;
    private int targetVictoryPoints;
    private String configFilePath;

    public int getMaxRounds() {
        return maxRounds;
    }

    public int getTargetVictoryPoints() {
        return targetVictoryPoints;
    }

    public GameConfig(int maxRounds, int targetVP) {
        this.maxRounds = maxRounds;
        this.targetVictoryPoints = targetVP;
        this.configFilePath = null;
    }

    public static GameConfig fromFile(String path) {
        return null;
    }
}
