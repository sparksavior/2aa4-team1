package com.assignment1.board;

import com.assignment1.config.GameConfig;
import com.assignment1.enums.ResourceType;
import com.assignment1.enums.TerrainType;
import com.assignment1.pieces.Building;
import com.assignment1.pieces.Road;
import com.assignment1.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/** Manages the game board: tiles, intersections, paths, and placement validation. */
public class Board {

    private List<Tile> tiles;
    private List<Intersection> intersections;
    private List<Path> paths;
    private Map<Integer, List<Tile>> tilesByProductionNumber;
    private Map<Intersection, List<Path>> pathsByIntersection;

    /** Creates a new board with the given configuration. */
    public Board(GameConfig config) {
        this.tiles = new ArrayList<>();
        this.intersections = new ArrayList<>();
        this.paths = new ArrayList<>();
        this.tilesByProductionNumber = new HashMap<>();
        this.pathsByIntersection = new HashMap<>();
    }

    /** Sets up the fixed Catan board layout with tiles, intersections, and paths. */
    public void setup() {
        setupTiles();
        indexTilesByProductionNumber();
        setupIntersections();
        setupPaths();
        connectTilesToIntersections();
    }

    /** Creates the 19 tiles with terrain types and production numbers. */
    private void setupTiles() {
        // central tile
        tiles.add(new Tile(0, TerrainType.FOREST, 10)); // wood
        
        // inner ring (1-6)
        tiles.add(new Tile(1, TerrainType.FIELDS, 11)); // wheat
        tiles.add(new Tile(2, TerrainType.HILLS, 8)); // brick
        tiles.add(new Tile(3, TerrainType.MOUNTAINS, 3)); // ore
        tiles.add(new Tile(4, TerrainType.PASTURES, 11)); // sheep
        tiles.add(new Tile(5, TerrainType.PASTURES, 5)); // sheep
        tiles.add(new Tile(6, TerrainType.PASTURES, 12)); // sheep
        
        // outer ring (7-18)
        tiles.add(new Tile(7, TerrainType.FIELDS, 3)); // wheat   
        tiles.add(new Tile(8, TerrainType.MOUNTAINS, 6)); // ore
        tiles.add(new Tile(9, TerrainType.FOREST, 4)); // wood
        tiles.add(new Tile(10, TerrainType.MOUNTAINS, 6)); // ore
        tiles.add(new Tile(11, TerrainType.FIELDS, 9)); // wheat
        tiles.add(new Tile(12, TerrainType.FOREST, 5)); // wood
        tiles.add(new Tile(13, TerrainType.HILLS, 9)); // brick
        tiles.add(new Tile(14, TerrainType.HILLS, 8)); // brick
        tiles.add(new Tile(15, TerrainType.FIELDS, 4)); // wheat
        tiles.add(new Tile(16, TerrainType.DESERT, 0)); // no production
        tiles.add(new Tile(17, TerrainType.FOREST, 2)); // wood
        tiles.add(new Tile(18, TerrainType.PASTURES, 10)); // sheep
    }

    private void indexTilesByProductionNumber() {
        tilesByProductionNumber.clear();

        for (Tile tile : tiles) {
            int production = tile.getProductionNumber();
            if (production <= 0) {
                continue;
            }
            tilesByProductionNumber
                .computeIfAbsent(production, key -> new ArrayList<>())
                .add(tile);
        }
    }

    private void setupIntersections() {
        for (int i = 0; i < 54; i++) {
            intersections.add(new Intersection(i));
        }
    }

    private void setupPaths() {
        int[] pathId = {0};
                
        // helper to add path if it doesn't already exist
        BiConsumer<Integer, Integer> addPath = (a, b) -> {
            if (a < intersections.size() && b < intersections.size()) {
                Intersection ia = intersections.get(a);
                Intersection ib = intersections.get(b);
                Path p = new Path(pathId[0], ia, ib);
                paths.add(p);
                pathsByIntersection.computeIfAbsent(ia, k -> new ArrayList<>()).add(p);
                pathsByIntersection.computeIfAbsent(ib, k -> new ArrayList<>()).add(p);
                connectNeighbors(ia, ib);
                pathId[0]++;
            }
        };
        
        // central hexagon (tile id 0-5)
        int[] centralHex = {0, 1, 2, 3, 4, 5};
        for (int i = 0; i < centralHex.length; i++) {
            int a = centralHex[i];
            int b = centralHex[(i + 1) % centralHex.length];
            addPath.accept(a, b);
        }
        
        int[][] pathConnections = {
            // central hex to inner ring
            {0, 20}, {1, 6}, {2, 9}, {3, 12}, {4, 15}, {5, 18},
            // inner ring edges (shared with outer ring's inner edge)
            {6, 7}, {7, 8}, {8, 9}, {9, 10}, {10, 11}, {11, 12},
            {12, 13}, {13, 14}, {14, 15}, {15, 17}, {17, 18}, {18, 16},
            {16, 21}, {21, 19}, {19, 20}, {20, 22}, {22, 23}, {23, 6},
            // inner ring to outer ring
            {8, 27}, {10, 29}, {11, 32}, {13, 34}, {14, 37}, {17, 39},
            {18, 40}, {21, 43}, {19, 46}, {22, 49}, {23, 52}, {7, 24},
            // outer ring edges
            {24, 25}, {25, 26}, {26, 27}, {27, 28}, {28, 29}, {29, 30},
            {30, 31}, {31, 32}, {32, 33}, {33, 34}, {34, 35}, {35, 36},
            {36, 37}, {37, 38}, {38, 39}, {39, 41}, {41, 42}, {42, 40},
            {40, 44}, {44, 43}, {43, 45}, {45, 47}, {47, 46}, {46, 48},
            {48, 49}, {49, 50}, {50, 51}, {51, 52}, {52, 53}, {53, 24}
        };
        
        for (int[] conn : pathConnections) {
            addPath.accept(conn[0], conn[1]);
        }
    }

    private void connectNeighbors(Intersection a, Intersection b) {
        if (!a.getNeighbors().contains(b)) {
            a.getNeighbors().add(b);
        }
        if (!b.getNeighbors().contains(a)) {
            b.getNeighbors().add(a);
        }
    }

    private void connectTilesToIntersections() {
        int[][] tileIntersections = {
            {0, 1, 2, 3, 4, 5}, // tile 0
            {1, 6, 7, 8, 9, 2}, // tile 1
            {2, 9, 10, 11, 12, 3}, // tile 2
            {4, 3, 12, 13, 14, 15}, // tile 3
            {18, 16, 5, 4, 15, 17}, // tile 4
            {21, 19, 20, 0, 5, 16}, // tile 5
            {20, 22, 23, 6, 1, 0}, // tile 6
            {7, 24, 25, 26, 27, 8}, // tile 7
            {9, 8, 27, 28, 29, 10}, // tile 8
            {11, 10, 29, 30, 31, 32}, // tile 9
            {13, 12, 11, 32, 33, 34}, // tile 10
            {37, 14, 13, 34, 35, 36}, // tile 11
            {39, 17, 15, 14, 37, 38}, // tile 12
            {42, 40, 18, 17, 39, 41}, // tile 13
            {44, 43, 21, 16, 18, 40}, // tile 14
            {45, 47, 46, 19, 21, 43}, // tile 15
            {46, 48, 49, 22, 20, 19}, // tile 16
            {49, 50, 51, 52, 23, 22}, // tile 17
            {23, 52, 53, 24, 7, 6}  // tile 18
        };

        for (int tileIdx = 0; tileIdx < tiles.size() && tileIdx < tileIntersections.length; tileIdx++) {
            Tile tile = tiles.get(tileIdx);
            for (int intersectionIdx : tileIntersections[tileIdx]) {
                Intersection intersection = intersections.get(intersectionIdx);
                // Tile -> Intersection
                tile.getIntersections().add(intersection);
                // Intersection -> Tile
                if (!intersection.getTiles().contains(tile)) {
                    intersection.getTiles().add(tile);
                }
            }
        }
    }

    /** Returns the tile with the given ID, or null if not found. */
    public Tile getTileById(int id) {
        if (id >= 0 && id < tiles.size()) {
            return tiles.get(id);
        }
        return null;
    }

    /** Returns the intersection with the given ID, or null if not found. */
    public Intersection getIntersectionById(int id) {
        if (id >= 0 && id < intersections.size()) {
            return intersections.get(id);
        }
        return null;
    }

    /** Returns the path with the given ID, or null if not found. */
    public Path getPathById(int id) {
        if (id >= 0 && id < paths.size()) {
            return paths.get(id);
        }
        return null;
    }

    /** Returns all intersections on the board. */
    public List<Intersection> getIntersections() {
        return intersections;
    }

    /** Returns all paths on the board. */
    public List<Path> getPaths() {
        return paths;
    }

    /** Validates road placement: path must connect to player's existing road or building. */
    public boolean canPlaceRoad(Path path, Player player) {

        List<Intersection> endpoints = path.getEndpoints();
        if (endpoints.size() < 2) return false; // defensive checking

        Intersection a = endpoints.get(0);
        Intersection b = endpoints.get(1);

        // early exit: one of the endpoints has a building owned by the player
        if (hasPlayerBuilding(a, player) || hasPlayerBuilding(b, player)) {
            return true;
        }

        // check all paths that incident to the candidate path's 2 endpoints
        for (Path p : pathsAt(a)) {
            if (p == path) continue;
            Road road = p.getOccupant();
            if (road != null && road.getOwner() == player) return true;
        }
        for (Path p : pathsAt(b)) {
            if (p == path) continue;
            Road road = p.getOccupant();
            if (road != null && road.getOwner() == player) return true;
        }
        return false;
    }

    /** Validates settlement placement: no building on intersection or its neighbors. */
    public boolean canPlaceSettlement(Intersection intersection, Player player) {

        if (intersection.getOccupant() != null) return false;

        for (Intersection neighbor : intersection.getNeighbors()) {
            if (neighbor.getOccupant() != null) return false;
        }
        return true;
    }

    private boolean hasPlayerBuilding(Intersection intersection, Player player) {
        Building b = intersection.getOccupant();
        return b != null && b.getOwner() == player;
    }

    private List<Path> pathsAt(Intersection intersection) {
        List<Path> at = pathsByIntersection.get(intersection);
        return at != null ? at : List.of();
    }

    /** Distributes resources to players based on dice roll and their buildings. */
    public void produceResources(int diceRoll, List<Player> players) {

        List<Tile> matchingTiles = tilesByProductionNumber.get(diceRoll);
        if (matchingTiles == null) return;

        for (Tile tile : matchingTiles) {
            ResourceType resourceType = terrainToResource(tile.getTerrain());
            if (resourceType == null) {
                continue; // DESERT or unknown terrain
            }

            // distribute resources to players with buildings on this tile's intersections
            for (Intersection intersection : tile.getIntersections()) {
                Building building = intersection.getOccupant();
                if (building != null) {
                    Player owner = building.getOwner();
                    int amount = building.getVictoryPoints(); // Settlement = 1, City = 2
                    owner.addResources(resourceType, amount);
                }
            }
        }
    }
    
    private ResourceType terrainToResource(TerrainType terrain) {
        switch (terrain) {
            case HILLS:
                return ResourceType.BRICK;
            case FOREST:
                return ResourceType.WOOD;
            case MOUNTAINS:
                return ResourceType.ORE;
            case FIELDS:
                return ResourceType.WHEAT;
            case PASTURES:
                return ResourceType.SHEEP;
            case DESERT:
                return null; // no resource production
            default:
                return null;
        }
    }
}
