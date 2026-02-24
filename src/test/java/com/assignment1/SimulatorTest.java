package com.assignment1;

import com.assignment1.config.GameConfig;
import com.assignment1.core.Simulator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SimulatorTest {
    @Test
    void rollDice_validNumber() {
        Simulator simulator = new Simulator(new GameConfig(100, 10));
        for (int i = 0; i < 500; i++){
            int roll = simulator.rollDice();
            assertTrue(roll >= 2 && roll <= 12, "Dice roll must be between 2 and 12");
        }
    }
}
