# SFWRENG 2AA4 - Catan Simulator

**Course:** SFWRENG 2AA4 Software Design I  
**Team:** 2AA4 Team 1

## Overview

This repository contains the implementation of a Settlers of Catan simulator, developed across Assignment 1 and Assignment 2. The simulator supports both automated agent gameplay and human player interaction with real-time visualization.

## Project Structure

```
2AA4-Team1
├── model/                   # Papyrus UML model files
│   └── (Papyrus .uml, .di, .notation files)
├── generated/
│   ├── papyrus/             # Code generated from Papyrus model (A1 Task 2)
│   └── genai/               # Code generated from GenAI (A1 Task 3)
├── src/                     # Manual implementation
│   ├── main/java/           # Java source code
│   └── test/java/           # Unit tests
├── docs/
│   ├── figures/             # Exported UML diagrams
│   ├── genai/               # GenAI prompts and observations
│   ├── papyrus/             # Papyrus translation notes
│   └── report/              # Report source files
├── config/                  # Configuration files
│   └── config.txt           # Game configuration (turns: int [1-8192])
├── visualize/               # Visualization support (A2)
│   ├── light_visualizer.py  # Python visualizer script
│   ├── state.json           # Game state export (auto-generated)
│   └── base_map.json        # Base board layout
└── README.md                # This file
```

## Requirements Implemented

### Assignment 1 Requirements
- **R1.1:** Valid map setup with specified tile/node identification
- **R1.2:** 4 randomly acting agents
- **R1.3:** Game rules compliance (excluding harbors, trading, development cards, robber)
- **R1.4:** Configurable simulation rounds (max 8192) or until 10 VPs
- **R1.5:** Proper termination handling
- **R1.6:** Key invariants enforcement (road connectivity, settlement distance, city replacement, etc.)
- **R1.7:** Console output format: `[RoundNumber] / [PlayerID]: [Action]` + VPs per round
- **R1.8:** Agent behavior: attempt to build when holding >7 cards
- **R1.9:** Demonstrator class with annotated main method

### Assignment 2 Requirements
- **R2.1:** Human player support with command-line input (Roll, Go, List, Build commands)
- **R2.2:** Integration with Python visualizer for real-time board visualization
- **R2.3:** Game state export to JSON file (visualize/state.json) updated after each action
- **R2.4:** Step-forward functionality to observe computer player actions
- **R2.5:** Robber mechanism (discard, placement, resource stealing on dice roll 7)
- **R2.6:** Enhanced Demonstrator class with detailed documentation

## Code Quality

[![Quality gate](https://sonarcloud.io/api/project_badges/quality_gate?project=sparksavior_2aa4-team1)](https://sonarcloud.io/summary/new_code?id=sparksavior_2aa4-team1)

## Building and Running

### Prerequisites
- Java 11 or higher
- Maven 3.6+
- Python 3.7+ (for visualization, optional)

### Build
```bash
mvn clean compile
mvn package
```

### Run
```bash
# Run the simulator
java -jar target/catan-simulator-1.0-SNAPSHOT.jar
```

### Configuration

Edit `config/config.txt` to set simulation parameters:
```
turns: 100
```

### Visualization

The game state is automatically exported to `visualize/state.json` after each action. To view the board:

1. Run the Java simulator (it will update `visualize/state.json`)
2. In a separate terminal, run:
   ```bash
   cd visualize
   python light_visualizer.py
   ```

The visualizer will display the current board state with roads and buildings.

### Step-Forward Mode

To enable step-forward mode (pause after computer player turns), modify `Demonstrator.java`:
```java
Simulator simulator = new Simulator(config, true); // true = step mode enabled
```

When enabled, the game pauses after each computer player's turn. Enter "go" to proceed.

## Assignment Tasks

### Assignment 1
- **Task 1:** Domain modeling in Papyrus UML
- **Task 2:** Code generation via Papyrus + translation observations
- **Task 3:** Code generation via GenAI + prompt disclosure + observations
- **Task 4:** Manual implementation (R1.1–R1.9)
- **Task 5:** Engineering process reflection

### Assignment 2
- **Task 1:** Unit test suite (10-20 JUnit tests)
- **Task 2:** Design evolution (UML updates, automata model)
- **Task 3:** Code evolution (R2.1–R2.6 implementation)

## Team Workflow

- **Kanban Board:** [GitHub Projects](https://github.com/users/sparksavior/projects/3)
- **Issues:** All work tracked via GitHub Issues
- **PRs:** All changes via Pull Requests with "Closes #X" linking

## Key Design Decisions

See the pinned issue: [A1 Decisions & Notes](https://github.com/sparksavior/2aa4-team1/issues/7)
