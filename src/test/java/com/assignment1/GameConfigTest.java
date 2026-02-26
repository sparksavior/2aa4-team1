package com.assignment1;


import com.assignment1.config.GameConfig;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class GameConfigTest{

    @Test
    void turnsBoundary_minAndMaxAreAccepted(){
        GameConfig min = new GameConfig(1, 10);
        GameConfig max = new GameConfig(8192, 10);

        assertEquals(1, min.getMaxRounds(), "turns should accept minimum boundary 1");
        assertEquals(8192, max.getMaxRounds(), "turns should accept maximum boundary 8192");

    }

    @Test
    void turnsBoundary_fromFileClampsOutOfRangeValues() throws IOException {
        // Below minimum -> to 1
        Path f1 = Files.createTempFile("config", ".txt");
        Files.writeString(f1, "turns: 0\n");
        GameConfig c1 = GameConfig.fromFile(f1.toString());
        assertEquals(1, c1.getMaxRounds(), "turns=0 should condense to 1");

        // Above maximum -> clamp to 8192
        Path f2 = Files.createTempFile("config", ".txt");
        Files.writeString(f2, "turns: 8193\n");
        GameConfig c2 = GameConfig.fromFile(f2.toString());
        assertEquals(8192, c2.getMaxRounds(), "turns=8193 should condense to 8192");
    }

    @Test
    void fromFile_partitionTesting_variousInput() throws IOException{
        // Partition 1: missing turns: -> default 100
        Path missingTurns = Files.createTempFile("config_missing", ".txt");
        Files.writeString(missingTurns, "somethingElse: 5\n");
        GameConfig c1 = GameConfig.fromFile(missingTurns.toString());
        assertEquals(100, c1.getMaxRounds(), "missing turns should default to 100");

        // Partition 2: invalid number -> default 100
        Path invalidTurns = Files.createTempFile("config_invalid", ".txt");
        Files.writeString(invalidTurns, "turns: abc\n");
        GameConfig c2 = GameConfig.fromFile(invalidTurns.toString());
        assertEquals(100, c2.getMaxRounds(), "non-integer turns should default to 100");

        // Partition 3: valid number -> use that number
        Path validTurns = Files.createTempFile("config_valid", ".txt");
        Files.writeString(validTurns, "turns: 250\n");
        GameConfig c3 = GameConfig.fromFile(validTurns.toString());
        assertEquals(250, c3.getMaxRounds(), "valid turns should parse correctly");

    }

    @Test
    void fromFile_missingFile_returnsDefaultConfig(){
        GameConfig cfg = GameConfig.fromFile("this_file_should_not_exist_12345.txt");
        assertEquals(100, cfg.getMaxRounds(), "Missing file should default turns to 100");
        assertEquals(10,cfg.getTargetVictoryPoints(), "Missing file should default to 10 VP");
    }
}