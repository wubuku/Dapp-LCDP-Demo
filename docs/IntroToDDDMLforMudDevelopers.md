# Introduction to DDDML for MUD Developers

English | [中文](IntroToDDDMLforMudDevelopers_CN.md)

## Why You Might Need a Domain-Specific Language

As a MUD developer, you might ask: Since we're already using the MUD framework, why do we need to introduce another tool?

The simple answer: To improve development efficiency and software quality.

MUD is undoubtedly an excellent development framework, but it also has some limitations. For instance, although MUD claims to be a general-purpose application development framework, not just for on-chain game development, in reality, most developers still view it as a fully on-chain game engine. In terms of on-chain game development, MUD hasn't introduced any off-chain hybrid solutions to improve game tickrate. MUD may want to maintain its "on-chain" characteristics as much as possible, but this can sometimes be a limitation rather than an advantage.

To address these limitations and further enhance development efficiency, we propose a model-driven low-code development approach.

We believe that adopting a model-driven low-code development approach can not only provide higher development efficiency but also compensate for these shortcomings of MUD.

Speaking of "models," you may have noticed that MUD also has the concept of "data models," but these are at a different level of abstraction from the domain models used in our low-code approach. The models defined in MUD configuration files are lower-level "physical data models," essentially part of the program implementation code.

However, we all know that software development is far more than just programming implementation. Especially for complex software projects, coding is often not the most time-consuming part of the development process. To develop high-quality software, we need to invest significant effort in the analysis and design phases. More importantly, we must ensure that the implemented code accurately reflects the results of analysis and high-level design, thus ensuring good maintainability of the code.

Our low-code development approach adopts a DDD (Domain-Driven Design) style domain model. This model is an organic combination of Object-Oriented Analysis (OOA) and Object-Oriented Design (OOD). To describe this domain model, we designed an expressive DSL (Domain-Specific Language) - DDDML.

The advantage of this approach is that we can start building conceptual domain models in the requirements analysis phase of software development; in the subsequent design and coding phases, we can continuously refine based on the same model, ensuring that the value of the early work is fully utilized.

Therefore, our low-code development approach not only accelerates the entire development process but also improves the overall quality of the software.

## What is DDDML

DDDML (Domain-Driven Design Modeling Language) is a DSL (Domain-Specific Language) we developed to describe DDD (Domain-Driven Design) style domain models.

DDDML allows us to document and showcase key elements of the domain model in one centralized place. It can describe the domain model at a conceptual level and allows us to add implementation-level details. Through DDDML, we can support code generation tools to create software code that closely maps to the domain model. Additionally, we can use DDDML to automatically generate software documentation, database schemas, state machine diagrams, API specifications, and more.

The core of DDDML is an abstract data structure we call the DDDML DOM (Document Object Model). It's a tree structure composed of different types of nodes. We stipulate that this data structure must be representable in JSON (JavaScript Object Notation). JSON is a lightweight data exchange format composed of elements such as values, objects, and arrays. We can also use YAML (YAML Ain't Markup Language), which is a superset of JSON and more human-readable.

In the following examples, we will use YAML-based DDDML to describe domain models, demonstrating its advantages in practical applications.

## How Does Our Low-Code Development Approach Change the Dapp Development Process?

After adopting our low-code development approach, Dapp developers' work will primarily focus on two aspects:

1. Domain Modeling
   * Create model files using DDDML (our domain-specific language). This can be done manually or using visual tools.
   * In the future, we plan to introduce AI-assisted modeling to further simplify this process and enhance the quality of domain models.

2. Implementing Specific Business Logic
   * If the application only needs basic CRUD (Create, Read, Update, Delete) operations, then no additional code needs to be written.
   * For more complex business logic, developers need to write code using the language specific to the smart contract platform (for example, Solidity is commonly used on EVM platforms).
   * Our long-term goal is to develop a platform-independent expression language that allows developers to write cross-platform business logic more easily.

We firmly believe that AI technology can provide powerful support for developers in both these aspects. This article will focus on introducing some of our attempts in implementing business logic. To be frank, AI's performance in this area has been impressive.

As for AI assistance in domain modeling, although it's not the focus of this article, we already have some very specific and feasible ideas and are conducting experimental explorations. The initial results are promising, and these advancements make us excited about the future of AI-assisted domain modeling.
