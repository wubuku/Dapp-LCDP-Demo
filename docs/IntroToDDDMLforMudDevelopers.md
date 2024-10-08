# Introduction to DDDML for MUD Developers

## Why You Might Need a Domain-Specific Language

As a MUD developer, you might wonder: Why introduce another tool when we're already using the MUD framework?

The simple answer: To enhance development efficiency and software quality.

MUD is an excellent development framework with a strong focus on fully on-chain game development. While it's designed as a general-purpose application development framework, its current strengths lie primarily in creating on-chain games. MUD's architecture prioritizes on-chain integrity, which aligns perfectly with certain use cases but may present challenges for others. For instance, implementing off-chain hybrid solutions to improve game tickrate could be more complex within MUD's current framework.

To complement MUD's strengths, we propose a model-driven low-code development approach. This method leverages MUD's robust data model design while introducing domain models at a higher abstraction level through our innovative, AI-native Language. This Domain-Specific Language is designed from the ground up to seamlessly integrate with AI technologies, enhancing the development process and opening new possibilities for Web3 creators.

Our approach not only increases efficiency but also expands MUD's capabilities, offering developers more options in their Web3 toolkit. By integrating MUD's implementation framework with our domain-driven approach, we ensure the final code reflects both low-level efficiency and high-level design principles, enhancing maintainability and scalability.


## Empowering On-Chain Game Creators: DSL and Low-Code Solutions

Our low-code approach, using Domain-Driven Design (DDD), aligns **particularly well with game development processes**. Game design's comprehensive planning phase makes it easier to translate high-level concepts into software design, especially beneficial for on-chain games requiring precise modeling.

We've developed DDDML, an expressive Domain-Specific Language, to facilitate this process. DDDML allows developers to describe complex game mechanics in a human-readable and machine-processable format, representing game entities, attributes, relationships, and behaviors.

This approach bridges the gap between game design and on-chain implementation, accelerating development and enhancing the final product's quality and consistency.


## AI-Powered DSL and Low-Code: The Future of Dapp Innovation

DDDML (Domain-Driven Design Modeling Language) centralizes the documentation of key domain model elements, supporting code generation tools and automating various aspects of software development.

Our low-code approach transforms the development process, focusing on two key aspects:

1. Domain Modeling
   * Create model files using DDDML (our domain-specific language).
   * We plan to introduce AI-assisted modeling to further simplify this process and enhance the quality of domain models.

2. Implementing Specific Business Logic
   * If the application only needs basic CRUD (Create, Read, Update, Delete) operations, then no additional code needs to be written.
   * For more complex business logic, developers need to write code using the language specific to the smart contract platform (for example, Solidity is commonly used on EVM platforms).
   * Our long-term goal is to develop a platform-independent expression language that allows developers to write cross-platform business logic more easily.

We firmly believe that AI technology can powerfully support both domain modeling and business logic implementation. This article will focus on our attempts in implementing business logic, where AI's performance has been truly impressive.

As for AI assistance in domain modeling, while not the focus here, we have developed specific, feasible ideas and are actively conducting experimental research. The initial results are promising, and these advancements make us excited about the future of AI-assisted domain modeling. We're committed to pushing boundaries in both areas, with tangible progress already evident in business logic implementation.

## Understanding DDDML Through Examples

DDDML centralizes the documentation and presentation of key domain model elements. It describes domain models conceptually and allows for the addition of implementation details.

DDDML uses an abstract data structure called the DDDML DOM (Document Object Model), a tree structure of various node types. This structure is representable in JSON (JavaScript Object Notation) or YAML (YAML Ain't Markup Language), a more human-readable superset of JSON.

In the following examples, we'll use YAML-based DDDML to describe domain models.

### Preparation You Might Need

With DDDML, we can support code generation tools to produce software code that closely aligns with the domain model. Additionally, DDDML can automatically generate software documentation, database schemas, state machine diagrams, API specifications, and more.

If you don't want to just read the documentation, but want to get hands-on and see what DDDML can do for you, you might need to prepare the following tools:

* Install [MUD](https://mud.dev/quickstart). As a MUD developer, I believe you may have already completed this step.
* Install [Docker](https://docs.docker.com/engine/install/).
* Install [Cursor IDE](https://www.cursor.com). If you want to verify the chemistry between DSL and AI yourself, you might need a programming assistant.

Then, you can refer to MUD's ["Quick Start"](https://mud.dev/quickstart#installation) to create a simple MUD project and get it running.
Next, you can open this project with an IDE and start your DDDML magic journey.


### Hello World

Create a `dddml` directory in your project root, then put a `Counter.yaml` file in this directory with the following content:


```yaml
singletonObjects:
  Counter:
    properties:
      Value:
        type: u32 # We use "u32" here, which maps to Solidity's "uint32".
        # type: uint32 # Alternatively, you can use "uint32" directly.
    methods:
      Increase:
        shouldCreateOnDemand: true
        # This prevents the generation of "assert object exists" code.
        result:
          isObjectReturned: true
          # Returns the current value of the object.
        event:
          name: CounterIncreased
          properties:
            OldValue:
              type: u32 # "uint32" is also acceptable here.
```

In the root directory of your codebase, execute:

```shell
docker run \
-v .:/myapp \
wubuku/dddappp-mud:master \
--dddmlDirectoryPath /myapp/dddml \
--boundedContextName Hello.Mud \
--mudProjectDirectoryPath /myapp \
--boundedContextJavaPackageName org.dddml.suiinfinitesea \
--javaProjectsDirectoryPath /myapp/mud-java-service \
--javaProjectNamePrefix hellomud \
--pomGroupId dddml.hellomud \
--enableMultipleMoveProjects
```

> **Hint**
>
> If you've run `dddappp-mud` before and are now encountering strange issues, you can try deleting the old containers and images first to ensure you're using the latest image:
>
> ```shell
> docker rm $(docker ps -aq --filter "ancestor=wubuku/dddappp-mud:master")
> docker rmi wubuku/dddappp-mud:master
> ```

This operation will modify the MUD configuration file: `packages/contracts/mud.config.ts`, and generate the Solidity scaffold code for the business logic implementation corresponding to the method defined in the above model (`Counter.Increase`), located here: `packages/contracts/src/systems/CounterIncreaseLogic.sol`.
You may have noticed the naming pattern of this file, which consists of the name of the entity + the name of the method + `Logic.sol`

At this point, the code in this file might look like [this](https://gist.github.com/wubuku/d7a45b868cb8f21b74e41127baf3b28e).

You'll find that there are already two functions in this file, with their signatures already written. You only need to fill in the function bodies.

You don't even have to write this code yourself; you can let AI complete this "fill-in-the-blank" task. For example, in [Cursor IDE](https://www.cursor.com), you can do this:

* Use the shortcut Cmd + A to select all the code in the current file. (I'm using macOS; for Windows systems, replace Cmd with Ctrl.)
* Use the shortcut Cmd + L to open Cursor's CHAT window (I tested using the claude-3.5-sonnet model).
* Enter `complete the functions` and press Enter.

It's time to witness the magic. Here's the [code AI completed for me](https://gist.github.com/wubuku/2b3691a9af146316d2811774868eb932).

The code generated by AI for me passed compilation on the first try, and I couldn't see any logical problems. Perfect!


### Blog Example

In the `dddml` directory, create a `blog.yaml` file with the following content:

```yaml
aggregates:
  Article:
    metadata:
      Preprocessors: ["CRUD_IT"] # Automatically generate Create/Update methods for articles
      CRUD_IT_NO_DELETE: true # But do not generate Delete methods
    id:
      name: Id
      type: u64
      generator:
        class: sequence # We want to use an auto-generated sequence number as the article ID
        #tableName: ArticleIdGenerator # Default value
    properties:
      Author:
        type: address
      Title:
        type: String
      Body:
        type: String
      Comments:
        itemType: Comment

    entities:
      Comment:
        metadata:
          Preprocessors: [ "CRUD_IT" ]
        id:
          name: CommentSeqId
          type: u64
          generator:
            class: sequence # Similarly, use an auto-generated sequence number as the comment ID
            # tableName: CommentSeqIdGenerator # Default value
        globalId:
          # Here we explicitly specify the two column names corresponding to the ID in the comment table, 
          # instead of using default values
          columnNames:
            - ArticleId
            - CommentSeqId
        properties:
          Commenter:
            type: String
          Body:
            type: String
```

For developers with some object-oriented programming (OOP) experience, understanding the content expressed by the DDDML model shouldn't be too difficult.
However, you might still find writing DSL by hand a bit cumbersome.
To address this, we plan to implement a GUI DDDML modeler in the future. At the same time, we are also exploring the possibilities of AI-assisted modeling.
Perhaps in the near future, you'll only need to use prompts similar to the one below to get a `blog.yaml` file like the one above:


```text
Please design a DDDML model for a Dapp using the MUD framework. This is a blog application with the following features:

1. Main entities:
   - Article
   - Comment

2. Article entity:
   - Properties: title, body, author
   - Operations: support creation and modification, but not deletion

3. Comment entity:
   - Properties: comment content, commenter name
   - Association: must be associated with a specific article, cannot exist independently
   - Operations: support full CRUD functionality (Create, Read, Update, Delete)

4. Special requirements:
   - Commenter name: users can input a display name when posting a comment
   - Article author: consider using an address type to represent the author

Please generate a model that conforms to the DDDML specification based on these requirements.
```

With such a prompt, we can expect AI to generate the `blog.yaml` file above for us. 
By reviewing the prompt alongside the YAML file, you should gain a clearer understanding of its meaning—so I won't elaborate further.

Now, run the `docker run` command again, and you can see what files with the `Logic.sol` name suffix have been generated. If the generation is successful, it should include:

* `ArticleCreateLogic.sol`
* `ArticleUpdateLogic.sol`
* `ArticleAddCommentLogic.sol`
* `ArticleUpdateCommentLogic.sol`
* `ArticleRemoveCommentLogic.sol`

This time, AI doesn't even have a chance to show off. All the function implementations have been written for you. As we mentioned earlier, if the CRUD operations on entities are exactly the business logic your application needs, then you don't need to write anything else yourself.


#### Let's Talk About "Data Types" Briefly

In DDDML, you can use all [field types supported by the MUD data model](https://mud.dev/store/data-model#field-types) to declare property and parameter types.
Moreover, DDDML supports even more data types. This is because DDDML's type system is a higher-level abstraction that can map abstract types in the "domain model" to specific implementation types when generating code.

Specifically, we use the following "basic data types" in DDDML models:

##### 1. Integer Types

For example:

- `u8` (can also be written as `uint8`)
- `u16` (can also be written as `uint16`)
- `u32` (can also be written as `uint32`)
- `u64` (can also be written as `uint64`)
- `u256` (can also be written as `uint256`)
- `int32` (can also be written as `i32`)
- `uint32` (equivalent to `u32`)

##### 2. Boolean Type

- `bool`

##### 3. String Type

- `string` (can also be written as `String`)

##### 4. Address Type

- `address`

##### 5. Bytes Type

- `bytes`

##### 6. Array Types

For example:

- `uint8[]`
- `uint32[]`
- `uint64[]`
- `uint256[]`


##### 7. Enumeration Types

The following DDDML document describes an enumeration object `Weekday`:

```yaml
enumObjects:
  Weekday:
    baseType: u8
    values:
      Monday:
        value: 1
      Tuesday:
        value: 2
      Wednesday:
        value: 3
      Thursday:
        value: 4
      Friday:
        value: 5
      Saturday:
        value: 6
      Sunday:
        value: 7
```

The base type of this enumeration is `u8`, an unsigned 8-bit integer. Values from 1 to 7 represent Monday through Sunday, with `3` indicating Wednesday.

In the MUD version of our DDDML code generation tool, such enumerations are converted into a Solidity `library` with constant definitions like `uint8 constant MONDAY = 1;`.

> According to the DDDML specification, specifying a `baseType` for enumeration objects is optional. The code generation tool can produce suitable code for DDDML-defined enumerations based on language features and team coding standards.
>
> Languages like Java and C# have the `enum` keyword, while some others do not. In these cases, the DDDML tool might substitute the enumeration object with the declared `baseType`. 
> Sometimes this is **not** a bad choice, as it may bring convenience in terms of serialization and persistence handling.

##### 8. Composite Types (Value Objects)

We can also construct composite types (Value Objects) using these basic types, as demonstrated in the example below.


### Example from the Game Infinite Seas

The following model is from our fully on-chain game [Infinite Seas](http://infiniteseas.io), focusing on the `SkillProcess` entity and related value objects. The `SkillProcess` definition is simplified here, highlighting its properties and the `Create` method.

```yaml
enumObjects:
  SkillType:
    baseType: u8
    values:
      Farming:
        value: 0
      Woodcutting:
        value: 1
      Crafting:
        value: 6

valueObjects:
  SkillProcessId:
    properties:
      SkillType:
        type: SkillType
      PlayerId:
        type: u256
      SequenceNumber:
        type: u8

  ItemIdQuantityPair:
    properties:
      ItemId:
        type: u32 # ID of the item
      Quantity:
        type: u32 # Quantity of the item

aggregates:
  SkillProcess:
    id:
      name: SkillProcessId
      type: SkillProcessId
    properties:
      ItemId: # ID of the item produced by the process
        type: u32
      StartedAt: # Time when the process started
        type: u64
      CreationTime: # Time required to produce the product
        type: u64
      Completed: # Whether the process is completed
        type: bool
      EndedAt: # Time when the process ended
        type: u64
      BatchSize: # Batch size of the product produced
        type: u32
      Existing: # Whether the process already exists
        type: bool
        # When all other properties of a legally existing object may have default values,
        # we need a specialized property to identify whether it exists or not
      ProductionMaterials: # Input materials for production
        itemType: ItemIdQuantityPair
        tableName: SkillPrcMtrl 
        # Here we give a short name to the table that stores the state of this property.
        # If we don't set it, the code generation tool will also give this table a default name, but it might be longer.
        # And MUD will truncate overly long table names, which may cause naming conflicts.
        description: "Actual input materials for production"

    methods:
      Create:
        isCreationCommand: true
        # parameters:
        event:
          name: SkillProcessCreated
```

In Infinite Seas, players can participate in various skill-based activities like farming, woodcutting, mining, and crafting. 
However, the number of concurrent production processes is limited. 
For instance, a player can manage up to two farming processes, one woodcutting process, one mining process, and one crafting process simultaneously. 
The `SkillProcess` entity is designed to manage these activities.

The "domain ID" of `SkillProcess` is a value object called `SkillProcessId`, comprising three components:

1. `SkillType`: The type of skill, such as farming or crafting.
2. `PlayerId`: The ID of the player executing the process.
3. `SequenceNumber`: The sequence number of the process, starting at `0` for the first process and increasing with the player's level.

Unlike the `Article` entity in the previous example, the `SkillProcess` entity's `id` property lacks `generator` information, meaning the ID must be provided by the frontend during creation.

We also defined a value object `ItemIdQuantityPair`, which includes `ItemId` and `Quantity`. 
This type is used throughout the game's model to represent item ID and quantity combinations, simplifying the model's expression.

For the `SkillProcess` entity, we opted not to use the `CRUD_IT` preprocessor to automatically add CRUD methods. 
Instead, we defined a `Create` method, marked as a "creation command" (`isCreationCommand: true`). 
The frontend provides the entity's ID (`SkillProcessId`), while the backend determines other necessary information, eliminating the need for explicit parameters in the `Create` method.

Further explanations of the model are included in the YAML comments above.

Does this model seem more complex than the previous ones? Are you curious about how AI will handle it?

Open the `SkillProcessCreateLogic.sol` file using Cursor. 
Before AI steps in, the file might look like [this](https://gist.github.com/wubuku/ac4f965f5c467190e89cf2128fe0ef7e). 
The tool generates numerous comments, which might seem excessive, but they are intended to guide AI (and you) in completing the business logic code.

I used this prompt to guide AI:

> Read the comments of the current file, and the file I referenced @SkillType.sol, and complete the functions.

The necessary files, like `SkillType.sol`, are hinted at in the generated code's comments.

The AI-generated code looked like [this](https://gist.github.com/wubuku/f1b71f20d448edb2e10f53232fa7cb10) and compiled successfully on the first try, with no apparent logical issues. 
Surprised? Unexpected? 😄

> **Hint**
> 
> The comments might mention some files that are not used, or omit some that should be used. 
> However, generally speaking, 
> developers should be able to judge which files are needed to implement the current business logic. 
> DDD advocates for the entire development team to maintain the same domain model. 
> If the team follows this best practice, developers can provide better guidance to AI.


### More Examples

In the [`dddml`](https://github.com/wubuku/hello-mud/tree/main/dddml) directory of this [repository](https://github.com/wubuku/hello-mud), you can find more examples of DDDML models.

Most of these examples come from actual development projects and have been used in production environments.


### Conclusion

This article delved into the fundamentals of DDDML, its role in Dapp development, and the ways AI can streamline the development process. Through practical examples, we showed how DDDML enables developers to swiftly construct complex domain models and generate corresponding business logic code.

For MUD developers, DDDML is a powerful tool that significantly boosts efficiency in domain modeling and business logic implementation. By leveraging domain-specific languages and low-code development methods, teams can concentrate more on system analysis and high-level design. The synergy between DDDML and AI not only makes coding more efficient but also ensures that the implemented code aligns with the domain model, which is vital for developing complex applications.

As AI technology continues to evolve, we anticipate that DDDML and its associated low-code tools will become more prevalent and deeply integrated into software development. Whether through automated code generation or intelligent domain modeling, developers will be able to bring their ideas to life more effectively.

The future of combining DSL and AI holds great promise.

