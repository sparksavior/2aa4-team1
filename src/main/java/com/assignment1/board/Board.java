package com.assignment1.board;

import com.assignment1.config.GameConfig;
import com.assignment1.player.Player;

import java.util.List;

public class Board {

    private List<Tile> tiles;
    private List<Intersection> intersections;
    private List<Path> paths;

    public Board(GameConfig config) {
    }

    public void setup() {
    }

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
        return null;
    }

    public List<Path> getPaths() {
        return null;
    }

    public void produceResources(int diceRoll, List<Player> players) {
    }
}
