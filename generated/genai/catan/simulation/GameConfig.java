package catan.simulation;

public class GameConfig {

    private int maxRounds;
    private int targetVictoryPoints;
    private String mapLayoutFile;

    public GameConfig(int maxRounds, int targetVictoryPoints, String mapLayoutFile) {
        this.maxRounds = maxRounds;
        this.targetVictoryPoints = targetVictoryPoints;
        this.mapLayoutFile = mapLayoutFile;
    }

    public String loadFromFile(String path) {
        return path;
    }
}