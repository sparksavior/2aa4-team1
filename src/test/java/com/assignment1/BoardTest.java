package com.assignment1;

import com.assignment1.board.Board;
import com.assignment1.board.Intersection;
import com.assignment1.board.Path;
import com.assignment1.config.GameConfig;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    @Test
    void setup_createsExpectedCoreStructure(){
        Board board = new Board(new GameConfig(100, 10));
        board.setup();

        for(int i = 0; i <= 18; i++){
            assertNotNull(board.getTileById(i), "Tile " + i + " should exist after setup()");
        }
        assertNull(board.getTileById(19), "Tile 19 should not exist (board has 19 tiles: 0..18)");

        // Intersections: 54 total (0...53)
        assertEquals(54, board.getIntersections().size(), "Board should have 54 intersections");
        assertNotNull(board.getIntersectionById(0), "Intersection 0 should exist");
        assertNotNull(board.getIntersectionById(53), "Intersection 53 should exist");
        assertNull(board.getIntersectionById(54), "Intersection 54 should not exist");


        // Paths: should exist and IDs should start from 0
        assertTrue(board.getPaths().size() > 0, "Board should have paths after setup()");
        assertNotNull(board.getPathById(0), "Path 0 should exist after setup()");
    }

    @Test
    void allPathsConnectValidIntersections(){
        Board board = new Board(new GameConfig(100, 10));
        board.setup();

        for(Path path : board.getPaths()){
            assertNotNull(path, "Path should not be null");

            var endpoints = path.getEndpoints();
            assertEquals(2, endpoints.size(), "Each path must connect exactly 2 intersections");


            Intersection a = endpoints.get(0);
            Intersection b = endpoints.get(1);

            assertNotNull(a, "Endpoint A should not be null");
            assertNotNull(b, "Endpoint B should not be null");

            assertTrue(board.getIntersections().contains(a),
                    "Endpoint A must belong to board intersections");

            assertTrue(board.getIntersections().contains(b),
                    "Endpoint B must belong to board intersections");
        }
    }
}
