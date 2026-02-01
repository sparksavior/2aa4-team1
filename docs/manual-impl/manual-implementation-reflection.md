# Task 4 Reflection: From Design to Implementation

Date: 2026-02-01  

---

## Reflection Point 1: Domain Models and Iteration

### Did we get the domain models right at the first attempt?

No, we did not get the domain models right at the first attempt. We iterated between modeling and implementation regularly, which is natural and expected in software development.

### What iterations occurred and why?

The first major iteration was changing ID types from `String` to `int`. Our UML design specified all IDs as `String` type (`Tile.id: String`, `Intersection.id: String`, `Path.id: String`), but during implementation we realized that integer IDs with index-based access (`list.get(id)`) is simpler and more efficient than HashMap lookups. This is a common optimization pattern that became apparent only when we started coding. We documented this as a valid implementation deviation, noting that UML types are conceptual abstractions while implementation can optimize for performance.

The second iteration involved bidirectional relationships. Our UML model showed the Tile ↔ Intersection relationship as bidirectional, but our initial implementation only captured one direction (`Tile` knows about `Intersection`). During implementation, we discovered that intersections need to know which tiles they touch for efficient resource production. We added the reverse relationship to match the UML design, explicitly documenting `List<Intersection>` in Tile and `List<Tile>` in Intersection.

A third iteration concerned collection types. While our UML used generic `List<T>` notation, we implemented it with index-based access where ID equals index. This optimization wasn't apparent during design but emerged naturally during coding. The public method signatures still match the UML, so no UML change was needed.

Perhaps the most significant iteration was discovering missing R1.8 logic during flow analysis. The UML had a `discardHalfHand()` method, and the logic existed in code, but it wasn't being called when dice roll was 7. We found this integration issue during testing and refactored it to `handleDiceRoll7()` for better encapsulation. This demonstrates that some issues only become apparent when you trace through the actual execution flow.

### What does this say about design and human modeling capability?

The hint about abstraction levels is particularly relevant here. There's a natural gap between modeling (high-level, conceptual) and implementation (low-level, concrete). UML models at a higher abstraction level—it shows *what* exists and *how* things relate, but not necessarily *how* to implement efficiently. This abstraction gap means we can't predict all optimizations during design. For example, we didn't anticipate that integer IDs would be better until we started coding.

Human modeling has inherent limitations. We can't predict all optimizations upfront, we discover requirements during implementation (like the R1.8 integration issue), and iteration is not just acceptable but necessary. The assignment explicitly mentions "iterate between design and implementation" because this is expected in real software development.

The key insight is that UML models the *domain* (what exists), while code implements the *solution* (how it works). The gap between these levels naturally requires iteration. This doesn't indicate poor modeling capability—rather, it reflects the reality that design and implementation operate at different levels of abstraction, and the translation between them reveals details that weren't visible in the abstract model.

---

## Reflection Point 2: Challenges and Tools in Iterations

### Technical Challenges

One significant technical challenge was maintaining bidirectional relationships. Ensuring both directions of the Tile ↔ Intersection relationship stayed consistent required careful defensive programming. We added checks like `if (!intersection.getTiles().contains(tile))` to prevent duplicates. A UML-to-code synchronization tool that automatically maintains bidirectional relationships would have been helpful here.

Another challenge emerged when we realized during implementation that iterating all tiles for resource production was inefficient. We solved this by creating a `tilesByProductionNumber` index for O(1) lookup, but this optimization wasn't apparent during design. A performance analysis tool integrated with UML that suggests optimization patterns based on access patterns would help identify these opportunities earlier.

The most critical technical challenge was the missing R1.8 integration. We had the discard logic implemented, but it wasn't being called when dice roll was 7. This integration issue was found during flow analysis, not during design. An integration testing tool that validates UML sequence diagrams against actual code flow would help catch these issues earlier.

### Conceptual Challenges

The biggest conceptual challenge was balancing SOLID principles with UML complexity. Perfect SOLID would require 10+ more classes, 5+ interfaces, and 15+ more associations in our UML model. We made a trade-off decision to keep a simpler UML with some SRP violations in implementation. A UML refactoring tool that suggests class splits based on SRP analysis would help evaluate these trade-offs more systematically.

We also faced the challenge of initial setup not being specified in requirements. The assignment doesn't specify initial player state, but the simulation needs it to progress. We added initial setup and documented it as a design decision. A requirements traceability tool that maps requirements (R1.1-R1.9) to UML elements and code, flagging gaps, would help identify these missing pieces.

### Tools That Would Help

Several abstract tool categories would help with these challenges:

1. UML-to-Code Synchronization: A tool that keeps UML and code in sync, highlighting differences and suggesting updates when code deviates from the model.

2. Flow Analysis Integration: A tool that validates code flow against UML sequence diagrams, catching integration issues like the missing R1.8 call.

3. SOLID Principle Analyzer: A tool that analyzes code and suggests UML refactorings for better SOLID compliance, helping evaluate trade-offs between perfect design and model complexity.

4. Performance Pattern Suggester: A tool that analyzes access patterns and suggests data structure optimizations, identifying opportunities like the production number index earlier.

5. Requirements Traceability: A tool that maps requirements (R1.1-R1.9) to UML elements and code, flagging gaps like missing initial setup.

These tools would help bridge the abstraction gap between design and implementation, catching issues earlier and making the iteration process more systematic.

---

## Reflection Point 3: OO Mechanisms and SOLID Principles

### OO Mechanisms in Implementation

Our implementation uses the same OO mechanisms as the UML design. We employed inheritance through the `Piece` → `Building` → `Settlement`/`City` hierarchy, exactly as modeled in UML. The `Building` class is an abstract base class, and `Settlement` and `City` extend it, matching the UML inheritance structure.

Polymorphism is used in `Board.produceResources()`, where we call `building.getVictoryPoints()` on a `Building` reference. This polymorphic call returns 1 for `Settlement` and 2 for `City`, demonstrating runtime polymorphism. This matches the UML design, which shows `Building` as abstract with concrete subclasses.

Encapsulation is consistently applied throughout. We use private fields with public getters, and private helper methods like `getTotalCards()` and `discardHalfHand()` hide implementation details. The UML shows visibility (`+` public, `-` private), and our implementation follows this pattern.

Composition is used extensively, with `Board` composing `List<Tile>`, `List<Intersection>`, and `List<Path>`. This matches the UML composition relationships, where Board "has-a" collection of tiles, intersections, and paths.

### SOLID Principles in Implementation

The SOLID principles show some differences between design and implementation. The Single Responsibility Principle is partially followed. Some classes like `GameConfig`, `Tile`, `Intersection`, and `Path` have single, clear responsibilities. However, `Player` combines resource management, building actions, agent behavior, and game rules. Similarly, `Board` combines setup, validation, and resource production, while `Simulator` mixes game loop, dice rolling, and output.

These SRP violations are different from the UML design, which is simpler with fewer classes. Perfect SRP would require splitting `Player` into `Player`, `PlayerBuilder`, `AgentStrategy`, and `PlayerRules`, and splitting `Board` into `Board`, `BoardValidator`, and `ResourceProducer`. This would add 10+ additional classes to the UML model, making it much more complex.

The Open/Closed Principle has limited extensibility in our implementation. Building costs are hard-coded in methods, and the agent strategy is fixed with no extension points. However, inheritance does allow extension—new building types could extend `Building`, and we use a factory pattern in `GameConfig.fromFile()`. Perfect OCP would require Strategy patterns for costs and agent behavior, adding 5+ classes and interfaces to the UML.

The Liskov Substitution Principle is fully met. `Settlement` and `City` properly substitute for `Building` in all contexts, matching the UML inheritance hierarchy. The Interface Segregation Principle is not applicable since we don't use interfaces in either design or implementation.

The Dependency Inversion Principle shows concrete dependencies throughout. `Player` depends on concrete `Board` class, and `Simulator` directly creates `Player` objects. Perfect DIP would require interfaces like `IBoard` and `IAgentStrategy`, adding an abstraction layer to the UML. We do use a factory pattern in `GameConfig.fromFile()`, which provides some abstraction.

### Why Are There Differences?

The differences between perfect SOLID compliance and our implementation stem from a deliberate trade-off decision. Perfect SOLID would require 10+ additional classes in UML (PlayerBuilder, AgentStrategy, BoardValidator, ResourceProducer, BuildingCost classes, etc.), 5+ interfaces (IBoard, IAgentStrategy, BuildingCost, GameOutput, etc.), and 15+ additional associations and relationships. This would make the UML model significantly more complex.

We chose a simpler approach for several reasons. The assignment scope focuses on functional requirements (R1.1-R1.9), and a simpler UML is easier to understand and maintain. Our current design demonstrates OO understanding while remaining practical, and all functional requirements are met. Additionally, the trade-offs provide good material for reflection on design decisions.

The key insight is that the abstraction gap between UML (conceptual) and code (concrete) naturally leads to some SOLID violations when prioritizing simplicity over perfect architecture. This is a valid engineering trade-off. For production code, we would refactor to perfect SOLID, but that's beyond the assignment scope.

---

## Overall Reflection

The implementation process revealed that iteration between design and implementation is not just expected, but necessary. The gap between UML's high-level abstraction and code's concrete reality means we discover optimizations, missing integrations, and implementation details during coding that weren't apparent in the model.

Our approach balanced OO principles with practical constraints. We used inheritance, polymorphism, encapsulation, and composition as modeled in UML, but made trade-offs on perfect SOLID compliance to keep the UML model manageable. This demonstrates understanding of both OO principles and practical engineering judgment.

The key lesson is that perfect design is not always practical design. Sometimes, a simpler model that meets requirements is better than a perfect model that's too complex to maintain. The abstraction gap between modeling and implementation is real, and navigating it requires iteration, trade-offs, and engineering judgment.
