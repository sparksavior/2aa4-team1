package com.assignment1.board;

import com.assignment1.config.GameConfig;
import com.assignment1.player.Player;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private List<Tile> tiles;
    private List<Intersection> intersections;
    private List<Path> paths;

    public Board(GameConfig config) {
        this.tiles = new ArrayList<>();
        this.intersections = new ArrayList<>();
        this.paths = new ArrayList<>();
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
        return intersections;
    }

    public List<Path> getPaths() {
        return paths;
    }

    public void produceResources(int diceRoll, List<Player> players) {
    }
}
