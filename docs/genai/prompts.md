# GenAI Code Generation 

This document records the prompts and settings used for generating Java source code from the UML diagram.

---

## GenAI Tool Used

- Tool: ChatGPT (OpenAI)
- Model: GPT-5.2
- Access method: Web-based conversational interface
- Date(s) of use: 01/28/2026

> Note: No fine-tuning or external plugins were used.

---

## Input Format

The following resources are provided as input to the GenAI tool:

- UML Class Diagram exported from Papyrus as a PNG image  

These resources describe the structure of a Settlers of Catan simulator,
including entities such as players, tiles, nodes, roads, and the simulator controller.

---

## Prompt — Image Based UML Input

**Purpose:**  
Generate Java class skeletons that reflect the UML structure as directly as possible,
without attempting to fully implement game rules or logic.

**Prompt text:**

> You are given a UML class diagram for a software system that simulates the board game Settlers of Catan.
>
> The diagram defines classes, attributes, methods, and relationships such as inheritance and associations.
>
> Generate Java source code that reflects the structure of the UML model.
> Focus on:
> - Creating Java classes corresponding to UML classes
> - Translating attributes and method signatures
> - Preserving inheritance relationships and associations where possible
> - Using method stubs or placeholders where behavior is unspecified
>
> Do NOT attempt to fully implement gameplay rules or detailed logic.
> Do NOT invent additional classes or functionality not present in the model.
>
> The output should reflect clean object-oriented design and be organized as if it were
> part of a Java project.

---

## Settings / Parameters

- No additional sampling or randomness controls were specified

---

## Notes on Experimental Intent

Multiple prompt versions are used to observe:
- How prompt specificity affects structural correctness
- Whether GenAI respects UML constraints or hallucinates behavior
- The suitability of GenAI for model-driven software engineering workflows

The generated code will be evaluated in the reflection.md document.