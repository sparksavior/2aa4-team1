package com.assignment1;

import com.assignment1.config.GameConfig;
import com.assignment1.core.Simulator;

/**
 * Entry point for the Catan simulator demonstrating Assignment 2 features:
 * 
 * - R2.1 - Human Player Support: Players can enter commands (Roll, Go, List, 
 *   Build settlement/city/road) during their turn. Commands are parsed using regex.
 * - R2.2 & R2.3 - Visualization: Game state is exported to visualize/state.json
 *   after each action. Use the Python visualizer to view the board in real-time.
 * - R2.4 - Step-Forward Mode: Enable by passing true to Simulator constructor.
 *   Pauses after computer player turns, allowing observation of AI actions.
 * - R2.5 - Robber Mechanism: On dice roll 7, players discard half hand if >7 cards,
 *   robber is placed on a random tile, and a resource is stolen from a qualifying player.
 * 
 * @author 2AA4 Team 1
 * @version v2.0.0
 */
public class Demonstrator {
    
    /**
     * Main entry point. Loads configuration and runs the simulation.
     * 
     * To enable step-forward mode, change to:
     * Simulator simulator = new Simulator(config, true);
     * 
     * @param args Command-line arguments (unused)
     */
    public static void main(String[] args) {
        GameConfig config = GameConfig.fromFile("config/config.txt");
        Simulator simulator = new Simulator(config);
        simulator.run();
    }
}
