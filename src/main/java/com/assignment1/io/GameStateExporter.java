package com.assignment1.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.assignment1.board.Board;
import com.assignment1.board.Intersection;
import com.assignment1.board.Path;
import com.assignment1.pieces.City;

/**
 * GameStateExporter is responsible for exporting the current
 * state of the Catan board to a JSON file so the Python 
 * visualizer can read and render it.
 *
 * The exported file contains:
 *  - roads (edge connections between intersections)
 *  - buildings (settlements or cities placed on intersections)
 *
 * This file is updated after each game action so that the
 * visualizer can render the board state in real time.
 */
public class GameStateExporter {

    /** Path to the JSON file that the visualizer reads */
    private final String outputPath;

    public GameStateExporter(String outputPath) {
        this.outputPath = outputPath;
    }

    // Exports the current board state into JSON format
    public void export(Board board) {
        try {
            java.nio.file.Path filePath = Paths.get(outputPath);
            
            // Create parent directories if they don't exist
            java.nio.file.Path parentDir = filePath.getParent();
            if (parentDir != null) {
                Files.createDirectories(parentDir);
            }
            
            // Get the absolute path for writing
            File outputFile = filePath.toFile();
            
            try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {

            writer.println("{");

            // Export roads
            writer.println("  \"roads\": [");
            boolean firstRoad = true;
            
            // Iterate through all paths and export those that have an occupant (a road)
            for (Path path : board.getPaths()) {
                if (path.getOccupant() != null) {

                    if (!firstRoad) {
                        writer.println(",");
                    }

                    int a = path.getEndpoints().get(0).getId();
                    int b = path.getEndpoints().get(1).getId();
                    String owner = path.getOccupant().getOwner().getColor().name();

                    writer.print("    { \"a\": " + a + ", \"b\": " + b + ", \"owner\": \"" + owner + "\" }");

                    firstRoad = false;
                }
            }

            writer.println();
            writer.println("  ],");

            // Export buildings
            writer.println("  \"buildings\": [");
            boolean firstBuilding = true;

            // Iterate through all intersections and export those that have an occupant (a settlement or city)
            for (Intersection intersection : board.getIntersections()) {
                if (intersection.getOccupant() != null) {

                    if (!firstBuilding) {
                        writer.println(",");
                    }

                    int node = intersection.getId();
                    String owner = intersection.getOccupant().getOwner().getColor().name();
                    String type = intersection.getOccupant() instanceof City
                            ? "CITY"
                            : "SETTLEMENT";

                    writer.print("    { \"node\": " + node + ", \"owner\": \"" + owner + "\", \"type\": \"" + type + "\" }");

                    firstBuilding = false;
                }
            }

            writer.println();
            writer.println("  ]");

                writer.println("}");
            }
        } catch (IOException e) {
            System.err.println("Error exporting game state to " + outputPath + ": " + e.getMessage());
        }
    }
}