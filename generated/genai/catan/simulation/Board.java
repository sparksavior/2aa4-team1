package catan.simulation;

import java.util.List;

public class Board {

    private List<Tile> tiles;
    private List<Intersection> intersections;
    private List<Path> paths;

    public Board(GameConfig config) {}

    public void setup() {}

    public Tile getTileById(String id) {
        return null;
    }

    public Intersection getIntersectionById(String id) {
        return null;
    }

    public Path getPathById(String id) {
        return null;
    }

    public List<Intersection> getIntersections() {
        return intersections;
    }

    public List<Path> getPaths() {
        return paths;
    }

    public void produceResources(int diceRoll, Player player) {}
}