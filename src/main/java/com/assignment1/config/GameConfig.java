package com.assignment1.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

        int maxRounds = 100;

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {

            String line;
            while ((line = reader.readLine()) != null) {

                String trimmed = line.trim();
                String[] tokens = trimmed.split(":");
                if (tokens.length != 2) {
                    continue;
                }
                
                String key = tokens[0].trim();
                String value = tokens[1].trim();
                if (key.equals("turns")) {
                    maxRounds = Integer.parseInt(value);
                    if (maxRounds < 1) {
                        maxRounds = 1;
                    } else if (maxRounds > 8192) {
                        maxRounds = 8192;
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            // exception ignored
        }
        return new GameConfig(maxRounds, 10);
    }
}
