# Task 3 — Generative AI Code Generation: Observations and Reflection

---

## 1. What did the GenAI tool do well? What are its strengths?

**Answer:**

>The GenAI tool produced well-structured Java class skeletons that accurately captured the core elements of the UML diagram it was provided. Major components such as Board, Simulator, Player, Tile, Intersection, Path, and GameConfig were translated into logically organized Java classes with appropriate attributes, constructors, and accessor methods.
>
>The GenAI tool correctly identified inheritance hierarchies by correctly modeling abstract base classes and subclass relationships. For example, Piece was correctly generated as an abstract superclass with an owner field, and extended properly by Building and Road. Building was also correctly modeled as an abstract subclass of Piece, with Settlement inheriting from it and assigning fixed victory point values with its constructor. These relationships match both the UML and the Papyrus-generated structures and indicate the GenAI's strong recognition of object-oriented inheritance patterns.
>
>GenAI also generated clean Java code, especially when compared to the Papyrus output. Papyrus frequently produced redundant nested class declarations (e.g., repeated inner Board, Intersection, and GameConfig classes), where the GenAI output avoided these artifacts and produced concise class definitions. This resulted in highly readable and maintainable scaffolding code, particularly visible in classes such as Board, Intersection, Path, and Simulator.
>
>Overall, GenAI is good at quickly generating clear and organized code, making it useful for early prototypes, exploring system design, and creating basic project structure.

---

## 2. What mistakes did the GenAI tool make? What are its weaknesses?

**Answer:**

>The GenAI tool showed limited accuracy in translating the UML model into code, which led to several structural mistakes and oversimplifications.
>
>A key weakness was the bidirectional relationships defined in the UML were simplified or removed. For instance, the UML diagram explicitly shows a bidirectional association between Tile and Intersection. However, in the GenAI-generated Tile class, this relationship is entirely omitted, as the class contains only an identifier, terrain type, and production number. As a result, the Board is structurally incomplete, limiting the GenAI's ability to correctly model relationships.
>
>GenAI also misinterpreted method intent and collection usage. For example, in the Board class, the produceResources method was implemented to operate on a single Player, even though the UML specifies that it should work with multiple players. Likewise, several methods in the Simulator class were simplified, failing to preserve the intended game flow and control structure.
>
>In GameConfig, GenAI oversimplified configuration handling by reducing file loading logic to a placeholder method returning a path string. While full logic was not required, this change fails to reflect the UML’s intended structure for configuration management.
>
>Overall, GenAI focused more on producing clean and readable code than strictly following the UML model. It lacked complete accuracy and required careful human review and correction before being suitable for final implementation.

---

## 3. How would you use GenAI in a large-scale software project? How would you balance its strengths and weaknesses?

**Answer:**

<!--
abc
-->

---

## 4. Strategy Selection for Revenue Maximization

**Answer:**

---

## 5. Why did the Instructor ask you the questions in the previous point?

**Answer:**

---