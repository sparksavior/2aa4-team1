# SFWRENG 2AA4 - Assignment 1: Catan Simulator

**Course:** SFWRENG 2AA4 Software Design I  

## Overview

This repository contains the implementation of a Settlers of Catan simulator developed as part of Assignment 1.

## Project Structure

```
2AA4-Team1
├── model/                   # Papyrus UML model files
│   └── (Papyrus .uml, .di, .notation files)
├── generated/
│   ├── papyrus/             # Code generated from Papyrus model (Task 2)
│   └── genai/               # Code generated from GenAI (Task 3)
├── src/                     # Manual implementation (Task 4)
│   └── (Java source code)
├── docs/
│   ├── figures/             # Exported UML diagrams
│   ├── genai/               # GenAI prompts and observations
│   ├── papyrus/             # Papyrus translation notes
│   └── report/              # Report source files
├── config/                  # Configuration files
│   └── config.txt           # Game configuration (turns: int [1-8192])
└── README.md                # This file

```

## Requirements Implemented

- **R1.1:** Valid map setup with specified tile/node identification
- **R1.2:** 4 randomly acting agents
- **R1.3:** Game rules compliance (excluding harbors, trading, development cards, robber)
- **R1.4:** Configurable simulation rounds (max 8192) or until 10 VPs
- **R1.5:** Proper termination handling
- **R1.6:** Key invariants enforcement (road connectivity, settlement distance, city replacement, etc.)
- **R1.7:** Console output format: `[RoundNumber] / [PlayerID]: [Action]` + VPs per round
- **R1.8:** Agent behavior: attempt to build when holding >7 cards
- **R1.9:** Demonstrator class with annotated main method

## Code Quality

*Will be added later*

## Building and Running

*Will be added later*

### Configuration

Edit `config/config.txt` to set simulation parameters:
```
turns: 100
```

## Assignment Tasks

- **Task 1:** Domain modeling in Papyrus UML
- **Task 2:** Code generation via Papyrus + translation observations
- **Task 3:** Code generation via GenAI + prompt disclosure + observations
- **Task 4:** Manual implementation (R1.1–R1.9)
- **Task 5:** Engineering process reflection

## Team Workflow

- **Kanban Board:** [GitHub Projects](https://github.com/users/sparksavior/projects/3)
- **Issues:** All work tracked via GitHub Issues
- **PRs:** All changes via Pull Requests with "Closes #X" linking

## Key Design Decisions

See the pinned issue: [A1 Decisions & Notes](https://github.com/sparksavior/2aa4-team1/issues/7)