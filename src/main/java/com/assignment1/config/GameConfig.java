package com.assignment1.config;

public class GameConfig {

    private int maxRounds;
    private int targetVictoryPoints;
    private String configFilePath;

    public GameConfig(int maxRounds, int targetVP) {
    }

    public static GameConfig fromFile(String path) {
        return null;
    }
}
