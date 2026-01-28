package com.assignment1;

import com.assignment1.config.GameConfig;
import com.assignment1.core.Simulator;

public class Demonstrator {
    public static void main(String[] args) {
        GameConfig config = GameConfig.fromFile("config/config.txt");
        Simulator simulator = new Simulator(config);
        simulator.run();
    }
}
