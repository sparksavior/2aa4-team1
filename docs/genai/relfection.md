# Task 3 — Generative AI Code Generation: Observations and Reflection

---

## 1. What did the GenAI tool do well? What are its strengths?

**Answer:**

>The GenAI tool produced well structured Java class skeletons that accurately captured the core elements of the UML diagram it was provided. Major components such as Board, Simulator, Player, Tile, Intersection, Path, and GameConfig were translated into logically organized Java classes with appropriate attributes, constructors, and accessor methods.
>
>The GenAI tool correctly identified inheritance hierarchies by correctly modeling abstract base classes and subclass relationships. For example, Piece was correctly generated as an abstract superclass with an owner field, and extended properly by Building and Road. Building was also correctly modeled as an abstract subclass of Piece, with Settlement inheriting from it and assigning fixed victory point values with its constructor. These relationships match both the UML and the Papyrus generated structures and indicate the GenAI's strong recognition of object-oriented inheritance patterns.
>
>GenAI also generated clean Java code, especially when compared to the Papyrus output. Papyrus frequently produced redundant nested class declarations (e.g., repeated inner Board, Intersection, and GameConfig classes), where the GenAI output avoided these artifacts and produced concise class definitions. This resulted in highly readable and maintainable scaffolding code, particularly visible in classes such as Board, Intersection, Path, and Simulator.
>
>Overall, GenAI is good at quickly generating clear and organized code, making it useful for early prototypes, exploring system design, and creating basic project structure.

---

## 2. What mistakes did the GenAI tool make? What are its weaknesses?

**Answer:**

>The GenAI tool showed limited accuracy in translating the UML model into code, which led to several structural mistakes and oversimplifications.
>
>A key weakness was the bidirectional relationships defined in the UML were simplified or removed. For instance, the UML diagram explicitly shows a bidirectional association between Tile and Intersection. However, in the GenAI generated Tile class, this relationship is entirely omitted, as the class contains only an identifier, terrain type, and production number. As a result, the Board is structurally incomplete, limiting the GenAI's ability to correctly model relationships.
>
>GenAI also misinterpreted method intent and collection usage. For example, in the Board class, the produceResources method was implemented to operate on a single Player, even though the UML specifies that it should work with multiple players. Likewise, several methods in the Simulator class were simplified, failing to preserve the intended game flow and control structure.
>
>In GameConfig, GenAI oversimplified configuration handling by reducing file loading logic to a placeholder method returning a path string. While full logic was not required, this change fails to reflect the UML’s intended structure for configuration management.
>
>Overall, GenAI focused more on producing clean and readable code than strictly following the UML model. It lacked complete accuracy and required careful human review and correction before being suitable for final implementation.

---

## 3. How would you use GenAI in a large-scale software project? How would you balance its strengths and weaknesses?

**Answer:**

>In a large scale software project, GenAI should be used as a productivity and support tool rather than as a fully autonomous development system. Its main strengths are rapid code generation, scaffolding, boilerplate creation which can significantly reduce development time and developer workload. For example, GenAI can quickly generate class skeletons from UML diagrams allowing developers to focus on architecture, correctness and system integration.
>
>However, GenAI has notable weaknesses including inconsistent adherence to design models, semantic inaccuracies, oversimplification of complex logic and occasional structural errors. These limitations mean that GenAI-generated code cannot be blindly trusted in systems that require critical saftey or production grade systems.
>
>To balance these strengths and weaknesses, GenAI should be integrated into a workflow with human review, where developers define the design and models while GenAI helps with fast code generation and prototyping. Human developers carefully review, test and refine all generated code. This combined approach increases development speed while still ensuring correctness, reliability and long term maintainability, which are essential for large software systems.

---

## 4. Strategy Selection for Revenue Maximization

**Answer:**

>To maximize revenue, the best strategy would be Model-driven Software Engineering (MDSE). Although it has the longest design phase at 25 days, it has the shortest development time for version 1 at only 5 days and the lowest churn rate of 2.5%. Since the company loses 10 potential customers per day, releasing too late reduces total possible revenue, but releasing a poor quality product leads to refunds and lost trust. MDSE provides the best balance between development speed and product quality, resulting in faster delivery than conventional software engineering and much higher quality than cowboy or vibe coding. The low churn rate means fewer refunds and higher long term customer retention which is critical when each customer pays $1000. Therefore, despite the higher upfront design cost, MDSE maximizes revenue by delivering a high quality product within a reasonable timeframe while being reliable and minimizing customer loss before and after release.

---

## 5. Why did the Instructor ask you the questions in the previous point?

**Answer:**

>The instructor asked these questions to highlight the key difference between programming and software engineering. Programming mainly focuses on writing code quickly to make something work, while software engineering emphasizes designing systems that are correct, reliable, scalable, and maintainable under real world constraints like time, cost, quality, risk, and user satisfaction. These questions show that fast coding approaches such as vibe coding or cowboy coding, may increase development speed but often come with the cost of correctness, reliability, and long term client retention. In contrast, engineering approaches like model-driven software engineering, prioritize structured design, correctness, automation and systematic validation.
Overall, the goal is to think like software engineers by balancing technical quality, business objectives, time and user experience, rather than simply focusing on writing code. 

---