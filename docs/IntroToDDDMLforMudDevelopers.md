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


## How Does Our Low-Code Approach Change the Development Process?

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


## Understanding DDDML Through Examples

### Preparation You Might Need

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

With such a prompt, we can expect AI to generate the `blog.yaml` file above for us. I believe that by combining this prompt with that file, you should be able to better understand the meaning it expresses.

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

The base type of this enumeration is `u8`, which is an unsigned 8-bit integer. Its values from 1 to 7 correspond to Monday through Sunday. This typically means that the generated code will use a number to represent a day of the week, for example, `3` represents Wednesday.

Our DDDML code generation tool's MUD version currently simply generates a Solidity `library` for such enumerations, containing constant definitions like `uint8 constant MONDAY = 1;`.

> According to the DDDML specification, it's not mandatory to specify the `baseType` for enumeration objects. The code generation tool can generate appropriate code for DDDML-defined enumeration objects based on the features provided by different languages and the coding standards of the development team.
>
> Some languages, like Java and C#, have the `enum` keyword, while others don't. In such cases, the DDDML tool might replace the enumeration object (type) with the `baseType` declared in the enumeration object definition. Sometimes this is **not** a bad choice, as it may bring convenience in terms of serialization and persistence handling.


### Example from the Game Infinite Seas

The following example model is extracted from our fully on-chain game [Infinite Seas](http://infiniteseas.io), 
primarily retaining the `SkillProcess` entity and related value objects. 
The definition of `SkillProcess` has been simplified here, showing its properties and the `Create` method for creating this entity.


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
        parameters:
        event:
          name: SkillProcessCreated
```

In Infinite Seas, players can engage in various skill production activities, such as farming, woodcutting, mining, and crafting. However, the number of production processes a player can execute simultaneously is not unlimited; for example, a player can execute at most two farming processes, one woodcutting process, one mining process, and one crafting process at the same time. The `SkillProcess` entity is used to manage these processes.

You can see that the "domain ID" of `SkillProcess` is a value object we defined named `SkillProcessId`, which consists of three parts:

1. `SkillType`: The type of skill, such as farming, woodcutting, mining, crafting, etc.
2. `PlayerId`: The ID of the player executing the process.
3. `SequenceNumber`: The sequence number of the process. For example, the first farming process of a player has a `SequenceNumber` of `0`, the second is `1`. The maximum value this sequence number can take may increase as the player's level increases.

Unlike the `Article` entity in the blog example above, the `id` property of the `SkillProcess` entity does not include `generator` information. This means that when creating this entity, the ID needs to be provided by the frontend.

We also defined a value object `ItemIdQuantityPair`, which contains two properties: `ItemId` and `Quantity`. In the game's model, many places (object properties, method parameters) require the combination of "item ID and quantity." We can directly use the `ItemIdQuantityPair` type in these places, making the model expression more concise and clear.

This time, we did not use the `CRUD_IT` preprocessor to automatically add CRUD (Create/Update/Delete) methods to the `SkillProcess` entity; instead, we defined a `Create` method and declared this method as a "creation command" (`isCreationCommand: true`). 
When calling this method, the frontend needs to provide the value of the entity's ID (`SkillProcessId`), which does not need to be explicitly specified through `parameters`. 
The other information required to create a `SkillProcess` instance is determined by the backend (contract) and does not need to be specified by the frontend. 
Therefore, we did not explicitly define any parameters for the `Create` method here.

The explanation for other parts of the model has already been included in the YAML comments above, so we won't repeat it here.

Do you feel that this model is much more complex than the previous ones? 
Are you excited (or perhaps skeptical) about how AI will perform this time?

Open the file `SkillProcessCreateLogic.sol` using Cursor. Before AI works its magic, the file might look like [this](https://gist.github.com/wubuku/ac4f965f5c467190e89cf2128fe0ef7e) now. You should see that the tool has generated a lot of comments in the file, which you might find a bit excessive. However, our main goal is to allow AI (and of course, you) to refer to these comments to complete the business logic code.

I used the following prompt to guide AI in completing the code:

> Read the comments of the current file, and the file I referenced @SkillType.sol, and complete the functions.

The files to reference (such as `SkillType.sol` in the prompt above) are actually hinted at in the comments of the code we generated.

This time, the code AI completed for me looks like [this](https://gist.github.com/wubuku/f1b71f20d448edb2e10f53232fa7cb10). It passed compilation on the first try, with no obvious logical issues. 
Surprised? Unexpected? 😄


### More Examples

In the [`dddml`](https://github.com/wubuku/hello-mud/tree/main/dddml) directory of this [repository](https://github.com/wubuku/hello-mud), you can find more examples of DDDML models.

Most of these examples come from actual development projects and have been used in production environments.

### Conclusion

In this article, we explored the basic concepts of DDDML, its application in Dapp development, and how AI can assist in simplifying the development process. Through practical examples, we demonstrated how DDDML helps developers quickly build complex domain models and generate corresponding business logic code.

DDDML provides MUD developers with a powerful tool that significantly enhances development efficiency in domain modeling and business logic implementation. By introducing domain-specific languages and low-code development methods, development teams can focus more on system analysis and high-level design. The combination of DDDML and AI makes the coding phase extremely efficient and ensures consistency between the implemented code and the domain model, which is crucial for the development of complex applications.

Looking ahead, as AI technology continues to advance, we can expect the application of DDDML and the low-code tools built on it to become more widespread and in-depth in software development. Whether through automated code generation or intelligent domain modeling, developers will be able to realize their ideas and concepts more efficiently.

We can certainly have high expectations for the combination of DSL and AI.

