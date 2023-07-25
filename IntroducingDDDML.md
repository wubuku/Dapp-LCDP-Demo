# Introducing DDDML: The Key to Low-Code Development for Decentralized Applications

English | [中文](IntroducingDDDML_CN.md)

## What is DDDML

DDDML (Domain-Driven Design Modeling Language) is a DSL (Domain-Specific Language) that we invented, which can be used to describe domain models in DDD (Domain-Driven Design) style.

DDDML allows us to record and present the key elements of the domain model in a centralized place. It can describe the domain model at the conceptual level, and also allows us to add details that the domain model needs at the implementation level. We can use DDDML to support code generation tools to generate software code that has a close mapping relationship with the domain model. We can also use DDDML to automatically generate software documentation, database schema, state machine diagrams, etc.

The core of DDDML is an abstract data structure, which we call DDDML DOM (Document Object Model). It is a tree structure composed of nodes of different types. We stipulate that this data structure must be able to be expressed using JSON (JavaScript Object Notation). JSON is a lightweight data exchange format, consisting of elements such as values, objects, arrays, etc. We can also use YAML (YAML Ain't Markup Language), which is a superset of JSON and has better readability for humans.

In the following example, we will use YAML-based DDDML to describe the domain model.

### What is DDD

As for what is DDD (Domain-Driven Design), this article only intends to introduce a few of the most basic concepts of DDD. More about DDD is a bit beyond the scope of this article, you can Google it yourself.

#### Entity

There is a class of objects that have *identifiers* (abbreviated as *ID*), no matter how the state of the object changes, its ID always remains unchanged. Such objects are called *entities*.

For example, our bank account (Account) always has a number (account number). We deposit money, withdraw money, and the money in the account changes, but the account number does not change. We can query the balance of the account through this account number. Therefore, we can model the bank account as an entity and choose its number as the ID of this entity.

For many developers, entities are very familiar concepts. Especially for developers who have used ORM frameworks, when you mention "entities", they may immediately think of "those objects that need to be mapped to tables (Table)".

#### Value Object

The following sentences are basically excerpted from the book DDD (with minor modifications), we think they are key points to understand DDD *value object*:

* Objects that describe specific aspects of the domain without identifiers are called value objects.

* Ignoring other types of objects and assuming that there are only two types of objects: entities and value objects, those objects that meet the definition of entities are treated as entities, and the remaining objects are value objects.

* It is recommended to implement value objects as "immutable". They are created by a constructor and never modified during their lifetime. Implemented as immutable and without identifiers, value objects can be safely shared and maintain *consistency*.

Developers who have used Hibernate ORM may notice that there is a concept in Hibernate: *Dependent Objects* (non-independent objects). You can think of it as referring to value objects. Dependent Objects are not mapped to tables (Table) in the database, they are mapped to columns in tables.

#### Aggregate and Aggregate Root, Internal Entities in Aggregate

*Aggregate* is one of the most important concepts of DDD at the tactical level. It is the key to DDD's ability to cope with the "core complexity of software" at the tactical level.

What is an aggregate? An aggregate draws boundaries between objects, especially between entities and entities. Entities within an aggregate are divided into two types: *aggregate root* and *internal entity* (or *non-aggregate root entity*).

An aggregate can only contain one aggregate root. When a client needs to access the state of an internal entity in an aggregate, the first thing it can get is the aggregate root, and then through this aggregate root, it can further access other entities within the aggregate.

The entities that can be accessed from an aggregate root can be considered as a whole. The life cycle of internal entities is controlled by their aggregate root. Many times, there is only one entity in an aggregate, which is the aggregate root.

##### Example: Order, OrderHeader, OrderItem

To model an "order", we might get:

* An aggregate called Order.
* The aggregate root of this order aggregate is an entity called OrderHeader.
* Through the OrderHeader entity, we can access a collection of internal entities in the aggregate called OrderItem.

Here, the names of the Order aggregate and the OrderHeader aggregate root are different, which shows the subtle difference between the two concepts of aggregate and aggregate root.

However, most of the time aggregates and their aggregate roots have the same name. It should be noted that our DDDML tool does not support specifying different names for aggregates and their aggregate roots when generating decentralized applications.

### Some DDDML Terms

In the following text, we will use some DDDML terms when explaining DDDML examples.

Considering that some terms used in JSON, such as Object, Array, are used too "abundantly" in the software development field, in order to avoid confusion, we decided to "replace" the names of some JSON concepts.

First of all, since DDDML DOM is a tree structure, it is natural for us to call the elements in this structure *nodes*.

In DDDML DOM, we call JSON Object as *map*, which is an unordered collection of key-value pairs. We call JSON Array as *list*, which is an ordered collection of values. We call JSON Value as *object*, which can be any type of value. We also have some other types of nodes, such as *string*, *number*, *boolean*, etc., which are consistent with the same-named concepts in JSON.

We can use *paths* to select nodes in DDDML DOM. A path is a string composed of node names separated by slashes. For example, `/aggregates/Car/entities/Tire/entities/Position/properties/MileAge` such a path can select the key node named `MileAge` on the second last line of the following YAML document.

```yaml
aggregates:
  Car:
    entities:
      Tire:
        entities:
          Position:
            properties:
              MileAge:
                type: long
```

## Why DDDML is Needed for Decentralized Application Development

Traditional enterprise application development platforms are driven by E-R (Entity-Relationship) models and/or relational models (the models used in SQL databases).

For example, [OutSystems](https://www.outsystems.com/) uses both E-R models and relational (data) models; some enterprise application development platforms use only one or the other. 

There is a fairly direct correspondence between the concepts used by E-R modeling and relational modeling.

Their modeling results (the generated code) can easily run on the traditional enterprise software technology infrastructures - SQL databases. But it's hard for them to run on brand new technology infrastructures such as blockchains - where the dominant smart contract platforms and "decentralized ledgers" are constructed too far from traditional SQL databases.

### The Key to Solving Problems is Modeling Paradigms

DDD-style domain models are OO (object-oriented) models in a relatively high level of abstraction.

Mapping from the higher abstraction level domain models to the lower abstraction level implementation object models, relational models, etc. is relatively easy and can often be done by automated tools.

The idea is that there should be a domain modeling language that can be used to accurately describe key concepts in the domain. This language should be a DSL that can be adopted by visualization tools as well as easy for humans to read and write.

With the domain models described by this DSL as the core, we can make a toolchain to generate the implementation code for various parts of applications from the models and then run the applications on various technical infrastructures.

## Explaining DDDML Through Examples

Although DDDML was originally created for driving low-code development of "traditional" enterprise and internet applications, theoretically, it can also be used for the development of decentralized applications based on "non-Move" platforms. However, for the purpose of this discussion, we will focus on the use of DDDML in the development of decentralized applications on Move.

It should be noted that the Move language is still in the early stages of development, and blockchain platforms that use Move as their smart contract language, such as Sui, Aptos, Starcoin, and Rooch, may have differences in the programming features they support. The main differences between them are not in the Move language itself, but in the different "state storage" models they support.

Therefore, some of the DDDML examples in this article may only be applicable to the generation of applications based on "specific Move smart contract platforms". We will specifically point this out during the explanation.

### Example 1: A Simple Domain Model

This is a DDDML document that describes a `Product` model:


```yaml
aggregates:
  Product:
    id:
      name: ProductId
      type: String
      length: 20
      generator: 
        class: sequence
        structName: ProductIdGenerator
    properties:
      Name:
        type: String
      UnitPrice:
        type: u128

    methods:
      Create:
        isCreationCommand: true
        parameters:
          Name:
            type: String
          UnitPrice:
            type: u128
        event:
          name: ProductCreated
```

We need to define one or more aggregate models under the `/aggregates` node. In the example above, we define an aggregate named "Product" (and a same-named aggregate root) under the `/aggregates/Product` key node.

The ID of the product aggregate root (entity) is a string with a length of 20. We use a generator of type `sequence` to generate this ID.

We can name the "struct" used by this generator, which is `ProductIdGenerator` in the example above, which can better control the generation result of Move code.

Then, the `Product` entity has two properties, namely `Name` and `UnitPrice`, which indicate the name and unit price of the product, respectively.

Under the name key node of each property, we use the `type` keyword to specify the type of the property. Here, we specify that the type of product name is string, and the type of product unit price is `u128` (unsigned 128-bit integer).

Under the `/aggregates/Product/methods` key node, we define the `Create` method for this aggregate.

We use `isCreationCommand: true` to indicate that this method is a creation command. That is, if it executes successfully, it will create an instance of a product object.

The parameters of the method are described under the `parameters` key node. The `/event/name` key node indicates that if this method executes successfully, it will trigger an event named `ProductCreated`.

#### Note different naming styles: camelCase and PascalCase

Careful readers may have noticed that in this DDDML document, some keys of Map (also known as "JSON Object") are named in `camelCase` style, while some are named in `PascalCase` (with an uppercase first letter) style.

This is intentional.

In the DDDML specification, those keys that need to appear in specific locations and have specific meanings are named in `camelCase` style. These keys belong to DDDML's *keywords*. And those names of "domain objects" outside the keywords defined by the DDDML specification, we strongly recommend using `PascalCase` style naming.

Because we intentionally chose different naming styles, when we read DDDML documents with our eyes, it is easy to distinguish which keys are DDDML keywords and which ones are concepts in the current domain model described by the document.


### Example 2: An "Order" Aggregate

This is part of a DDDML document that describes an "Order" model:

```yaml
aggregates:
  Order:
    id:
      name: OrderId
      type: string
      generator:
        class: assigned
        tableName: OrderIdTable
    properties:
      TotalAmount:
        type: u128
      EstimatedShipDate:
        type: Day
        optional: true
      Items:
        itemType: OrderItem

    entities:
      OrderItem:
        id:
          name: ProductObjectId
          type: ObjectID
#      OrderItem:
#        id:
#          name: ProductId # for Sui
#          type: String
        properties:
          Quantity:
            type: u64
          ItemAmount:
            type: u128

    methods:
      Create:
        isCreationCommand: true
        parameters:
          ProductObjId:
            type: ObjectID
#          Product: # for Sui
#            referenceType: Product
          Quantity:
            type: u64
        event:
          name: OrderCreated
          properties:
            UnitPrice:
              type: u128
            TotalAmount:
              type: u128
            Owner:
              type: address
```

The above DDDML code defines an aggregate named `Order` and a same-named aggregate root, as well as an internal entity in the aggregate named `OrderItem`.

#### The "Order" Aggregate

Under the `/aggregates/Order/id` key node, we define the ID of the order aggregate root. The name of the order ID is `OrderId`, and its type is `string`. The order ID is generated by a generator of class `assigned`, which means that this ID is assigned by the "user". Obviously, we need a "table" to ensure that an ID is not assigned to different object instances. In order to make the generated code as we wish, we can specify the naming of this table in the code, which is `OrderIdTable` here.

Under the `/aggregates/Order/properties` key node, we define the properties of the order, which represent the total amount, estimated ship date and order items of the order.

The `TotalAmount` property of the order is a property of type `u128`.

The `EstimatedShipDate` property of the order is an optional property (`optional: true`) of type `Day`. Here, `Day` is a custom value object, which we will see its definition below.

The order items (`Items`) property of the order is a collection (`itemType: OrderItem`) of elements of type `OrderItem`. Here, `OrderItem` is an internal entity in the aggregate.

#### The "Order Item" Entity

Under the `/aggregates/Order/entities/OrderItem` key node, we define the internal entity in the aggregate named "OrderItem".

The `id` defined here for order item is a local ID. A local ID means that for this entity (type), it only needs to ensure that this ID has uniqueness among different instances of this entity (type) within the same external entity (parent entity) instance.

We name the order item's ID as `ProductObjectId`, and declare its type as `ObjectID`. This name indicates that it points to a product (`Product`) object. If the business logic requires that different order items in an order cannot point to the same product, then the product's object ID can be used as the order item's ID.

Note that here, `ObjectID` is a platform-specific type. Here we assume that we are developing a decentralized application based on Rooch. In Rooch Framework, every object has a globally unique ID, which is of type `ObjectID`.

Under the `/aggregates/Order/entities/OrderItem/properties` node, we define the properties of order item, which represent quantity and amount.

#### Methods for Operating on "Order"

Under the `/aggregates/Order/methods/Create` key node, we define the create (`Create`) method for order.

This method is a creation command (`isCreationCommand: true`), and it has two parameters: product object ID and quantity.

Here we assume that the business requirement is to create the first order item at the same time when creating an order. So in this method, the two parameters respectively indicate the product's "ObjectID" and the order quantity of the first order item.

In addition, when the DDDML tool generates the code for this "creation command", it will automatically add a parameter that represents the aggregate root ID for you.

This method, if executed successfully, will trigger an event named `OrderCreated`. By default, the event object produced by this method will contain properties with the same name and type as all parameters. However, if you want to add more properties to this event, you can define them under the `/aggregates/Order/methods/Create/event` key node.

For example, in this example, we added three properties to the `OrderCreated` object, which represent the unit price of the product, total amount and owner of the order.



### Example 3: Composite Value Objects

This is an example DDDML document of a fabricated value object:

```yaml
valueObjects:
  Year:
    properties:
      Number:
        type: u16
      Calendar:
        type: String
        length: 50

  Month:
    properties:
      Year:
        type: Year
      Number:
        type: u8
      IsLeap:
        type: bool

  Day:
    properties:
      Month:
        type: Month
      Number:
        type: u8
      TimeZone:
        type: String
        length: 50
```

This example is fabricated, so we don't need to discuss whether the design of this model is reasonable. Here it just shows a feature of DDDML, which can use a value object as the type of another value object's property, and thus compose more complex value objects.

This document describes three value objects: `Year`, `Month` and `Day`.

Among them, the Year object has two properties: `Number` and `Calendar`. They represent the year and calendar type respectively.

The Month object has three properties: `Year`, `Number` and `IsLeap`. It embeds a `Year` object as its property, indicating the year; `Number` indicates the month; `IsLeap` indicates whether this month is a leap month.

The Day object has three properties: `Month`, `Number` and `TimeZone`. It embeds a `Month` object as its property, indicating the month; `Number` indicates the day of the month; `TimeZone` indicates the time zone.

These properties have different types, such as `u16`, `u8`, `bool` and `String`.

### Example 4: A Blog System

```yaml
aggregates:
  Article:
    id:
      name: Id
      type: ObjectID # only for Rooch
      #type: UID # only for Sui
      arbitrary: true

    properties:
      Title:
        type: String
      Author:
        type: address
      Content:
        type: String
      Tags:
        itemType: ObjectID
      References:
        itemType: Reference

    entities:
      Reference:
        id:
          name: ReferenceNumber
          type: u64
        properties:
          Title:
            type: String
          Author:
            type: String
          PublicationYear:
            type: u64
            optional: true
          # ...
          Url:
            type: String
            optional: true
          PageNumber:
            type: u64
            optional: true

    methods:
      Create:
        isCreationCommand: true
        parameters:
          Title:
            type: String
          Author:
            type: address
          Content:
            type: String
          References:
            itemType: ReferenceVO
          Tags:
            itemType: ObjectID
        event:
          name: ArticleCreated

      AddReference:
        parameters:
          ReferenceNumber:
            type: u64
          Title:
            type: String
          Url:
            type: String
            optional: true
        event:
          name: ReferenceAdded

      UpdateReference:
        parameters:
          ReferenceNumber:
            type: u64
          Title:
            type: String
          Url:
            type: String
            optional: true
          Author:
            type: String
            optional: true
        event:
          name: ReferenceUpdated

      RemoveReference:
        parameters:
          ReferenceNumber:
            type: u64
        event:
          name: ReferenceRemoved


  Tag:
    id:
      name: Name
      type: String
      generator:
        class: assigned
        tableName: TagNameTable
    methods:
      Create:
        isCreationCommand: true
        parameters:
        event:
          name: TagCreated

valueObjects:

  ReferenceVO:
    properties:
      ReferenceNumber:
        type: u64
      Title:
        type: String
      Url:
        type: String
        optional: true
```

This document contains two aggregates: `Article` and `Tag`, and a value object model: `ReferenceVO`.

#### The "Article" Aggregate

We use the `/aggregates/Article/id` key node to define the ID of the article.

Regarding how the ID of the aggregate root instance is generated, if the platform supports it, we can choose to use the platform-provided "object ID" without setting the `generator` information of the `id`.

This example assumes that we are developing our blog system based on Rooch, then we can choose to set the name of the article's ID to `id`, and its type to `ObjectID`, then the Move code generated by the DDDML tool will use the object ID provided by Rooch platform as the article's ID when creating an article instance. As for whether the `arbitrary` of `id` is `true`, it currently has no actual impact on our DDDML tool-generated code. It is more of descriptive information, indicating that we actually don't care about the format of the article's ID, as long as it is a unique ID.

Not only Rooch, but also other "Move smart contract platforms" (blockchains), such as Sui, also provide unique object IDs for each "object". If here we intend to generate Move code based on Sui, then we can set the name of the article's ID to `id`, and its type to `UID`, then the Sui Move code generated will use the UID provided by Sui platform as the article's ID when creating an article instance.

Under the `/aggregates/Article/properties` key node, we define the properties of the article: `Title`, `Author`, `Content`, `Tags` and `References`, which respectively represent the title, author, content, tags and references of the article.

Under the key nodes of these properties, we use `type` or `itemType` (only one of these two can be defined) to specify the type of (non-collection) property or the type of element of (collection) property.

Here, we specify that the type of article title is `String`, the type of article author is `address` (this is a special type, indicating Move account address), and the type of article content is `String`.

The tags (`tags`) property of the article is a collection (`itemType: ObjectID`) of elements of type `ObjectID`.

The references (`references`) property of the article is a "relationship" that navigates from the aggregate root entity (article) to the `Reference` internal entity in the aggregate. For OO models, one-to-many relationships between two entities are represented by a collection property of the former. So here we use `itemType: Reference` to describe the one-to-many relationship between articles and their references.

Obviously, we also need to further describe what the `Rreference` entity is, which are located under the `/aggregates/Article/entities/Reference` key node.

The ID of the internal entity in the aggregate `Reference` is a local ID. A local ID means that for this entity (type), it only needs to ensure that this ID has uniqueness among different instances of this entity (type) within the same external entity (parent entity) instance.

Here, we specify that the name of the reference's local ID is `ReferenceNumber`, and its type is `u64` (unsigned 64-bit integer).

Then, under the `/aggregates/Article/entities/Reference/properties` node, we define the properties of reference, such as `Title`, `Author`, `PublicationYear`, `Url`, etc. As their names suggest, they are respectively the title, author, publication year, URL and other properties of reference.

It is worth noting that some properties here have an `optional` value of `true`, such as `PublicationYear`, `Url`, etc. This means that these properties are optional, that is, they can have no value. When generating Move code, fields of type `Option<T>` will be generated for optional properties.

Under the `/aggregates/Article/methods/Create` key node, we define the "create article" method.

It is worth noting that this method's parameters (`parameters`) include a collection property with an element type of `ObjectID` called `Tags`, and a collection property with an element type of value object `ReferenceVO` called `References`. Below we will see the definition of value object `ReferenceVO`.

Then we also define several methods for operating on references.

* The "add reference" method has parameters including reference number `ReferenceNumber` and `Title`, etc. It should be noted that here we do not need to define a parameter representing the aggregate root ID in the method. The DDDML tool will automatically add this necessary parameter for you. This method executes successfully and triggers an event named "reference added" (`ReferenceAdded`).
* The "update reference" method has parameters including reference number, title, URL and author, etc. It triggers an event named "reference updated" (`ReferenceUpdated`).
* The "remove reference" method has one parameter: reference number. It triggers an event named "reference removed" (`ReferenceRemoved`).

#### The "Tag" Aggregate

Under the `/aggregates/Tag` key node, we define the tag aggregate.

The name of tag's (domain) ID is `Name`, and its type is `String`. Apart from this domain ID, tag has no more properties.

Tag object instance's ID is generated by a generator of type `assigned`, which means that this ID is assigned by the "user". Obviously, we need a "table" to ensure that an ID is not assigned to different object instances. In order to make the generated code as we wish, we can specify the naming of this table in the code, which is `TagNameTable` here.

Under the `/aggregates/Tag/methods` key node, we define a method `Create` for "creating" tags. We did not explicitly define any parameters for this method. When DDDML tool generates code for this method, it will automatically add a parameter representing aggregate root ID for you. This method executes successfully and triggers an event named "tag created" (`TagCreated`).

#### The "ReferenceVO" Value Object

Finally, under the `/valueObjects` key node, we define a value object named `ReferenceVO`. We mainly use this value object (type) in article's creation method parameters.

The properties of this value object include: reference number, title, URL and author. Some properties are optional.


### Example 5: Enum Objects

The following DDDML document describes two enum objects:

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

  Weekday2:
    baseType: String
    values:
      Monday:
        value: "Mon"
      Tuesday:
        value: "Tue"
      Wednesday:
        value: "Wed"
      Thursday:
        value: "Thu"
      Friday:
        value: "Fri"
      Saturday:
        value: "Sat"
      Sunday:
        value: "Sun"
```

These two enum objects are both used to represent the concept of "weekday", but they have different base types (`baseType`). The base type of `Weekday` is `u8`, which is an unsigned 8-bit integer. Its values range from 1 to 7, corresponding to Monday to Sunday. This means that the generated code will use a number to represent a day of the week, such as 3 for Wednesday.

The base type of `Weekday2` is `String`, which is a string. Its values are the English abbreviations of each weekday, such as `Mon` for Monday, `Tue` for Tuesday, and so on. This means that the generated code will use a short string to represent a day of the week, such as `Wed` for Wednesday.

It should be noted that the enum objects in DDDML are similar to the enum type in C-like languages, but not quite the same as the enum type in Rust.

According to the DDDML specification, the `baseType` of an enum object is not required to be specified. The DDDML code generation tool can generate appropriate code for the enum objects defined by DDDML according to the features provided by different languages, as well as the coding standards of the development team.

Some languages, such as Java and C#, have an `enum` keyword, while some languages do not have an `enum` type. In this case, the DDDML tool may replace the enum object (type) with the `baseType` declared in the enum object definition. Sometimes this is not a bad choice, as it may bring convenience in serialization and persistence processing.

### Example 6: Automatically Adding CrUD Methods and Control Events

The following is an example of using the `MOVE_CRUD_IT` preprocessor to automatically add CrUD (Create / Update / Delete) methods to a `Post` aggregate root entity:

```yaml
aggregates:
  Post:
    metadata:
      Preprocessors: [ "MOVE_CRUD_IT" ]
      CRUD_IT_NO_UPDATE: true
    id:
      name: PostId
      type: u128
      generator:
        class: sequence
        # structName: PostIdGenerator #The default value is actually this
    properties:
      Poster:
        type: address
      UserId:
        type: String
        length: 66
      Content:
        type: String
        length: 1000
      Digest: 
        type: String
        length: 66
```

Of course, we can also tell the preprocessor to ignore the generation of some of CrUD methods.

You may have noticed that in the above example, there is a `CRUD_IT_NO_UPDATE: true` line in the metadata of the aggregate root.
That's because the above example assumes the business requirement that users can't update posts, but only create and delete them.

---

Looking at the generated contract code, you may notice that creating a post triggers the `PostCreated` event, and deleting a post triggers the `PostDeleted` event, and that the code uses two different objects (`struct`s in the case of Move code) to represent them.
Also, in this case, an off-chain service pulling events may need to pull from both event streams, which may also be a bit cumbersome for the off-chain code to write.

You might want to merge the three event types for `Post` operations into one. This would reduce the amount of code by quite a bit.

We can do that. Modify the model file by adding these lines:

```yaml
    methods:
      Create:
        event:
          type: PostEvent
          discriminatorValue: 0
      Delete:
        event:
          type: PostEvent
          discriminatorValue: 2

    eventObjects:
      PostEvent:
        discriminator: EventType
        properties:
          EventType:
            type: u8
```

Specifically, the code added above means that the events triggered by `Create` and `Delete` posts are represented by the same object called `PostEvent`,
using only an `EventType` field to distinguish between them.

As you may have noticed, you don't need to define the `PostId`, `Poster`, etc. properties in the `PostEvent` event object, as you can see below;
the `MOVE_CRUD_IT` preprocessor generates them for you automatically.

```yaml
      PostEvent:
        discriminator: EventType
        properties:
          EventType:
            type: u8
          # 
          # You don't actually need to key in the following properties.
          # The preprocessor generates them automatically.            
          # 
          PostId:
            type: u128
          Version:
            type: u64
          Poster:
            type: address
          UserId:
            type: String
          Content:
            type: String
          Digest:
            type: String
```

### Example 7: Using Resources

In resource-oriented programming, a resource refers to a special kind of data that is non-copyable and non-droppable.

```yaml
typeDefinitions:
  Balance:
    moveType: "sui::balance::Balance"
    isResource: true
    defaultLogic:
      Move:
        'sui::balance::zero()'
    destroyLogic:
      Move:
        'sui::balance::destroy_zero({0})'

  SUI:
    moveType: "sui::sui::SUI"

singletonObjects:
  Blog:
    properties:
      Name:
        type: String
        length: 200
        defaultLogic:
          Move:
            'std::string::utf8(b"Unnamed Blog")'
      Articles:
        itemType: ID #Object ID
      Vault:
        type: "Balance<SUI>"

    methods:
      "__Init__":
        event:
          isObjectShared: true # Share the object after initialization.

      Donate:
        shouldCallByReference: true
        parameters:
          Amount:
            type: Balance<SUI>
        event:
          name: DonationReceived
          properties:
            Amount:
              type: u64
      Withdraw:
        shouldCallByReference: true
        parameters:
          Amount:
            type: u64
        result:
          type: Balance<SUI>
        event:
          name: VaultWithdrawn
```

In the example above, a type named "Balance" is defined under the `/typeDefinitions/Balance` key node, 
which is a resource type that represents a movable asset.
We use the `moveType` keyword to specify the corresponding type of this type in Move code, 
which is `sui::balance::Balance` - here we assume that we are developing a Dapp based on Sui.
Then, we specify the logic for creating the default value (empty value) required for initialization (`defaultLogic`) and the logic for destruction (`destroyLogic`) for this resource type.

A token type named "SUI" is defined under the `/typeDefinitions/SUI` key node.
We specify that the corresponding type of this type in Move code is `sui::sui::SUI`.

In Sui Move, singleton objects are generally created using the One-Time-Witness pattern in the module's `init` function.
We can define a method named `__Init__` in DDDML to control the logic of the `init` function.
In the example above, we indicate that the `Blog` singleton object is shared after it is created (`event/isObjectShared: true`), 
so that others can also use it.

We use the `Donate` method to accept `SUI` tokens donated by users.
`shouldCallByReference: true` indicates that this method needs to be called by a mutable reference of a Blog object. 
The keyword `shouldCallByReference` is currently only valid for the Sui Move platform.
The type of parameter `Amount` is `Balance<SUI>`, as mentioned above, this is a resource type.
This method will trigger an event named `DonationReceived` after execution.
The properties of the event are described under the `event/properties` key node. 
The event property `Amount` has a type of `u64`, which represents the amount of tokens donated. 
Obviously, the type of event properties cannot use resource types.

The `Withdraw` method, if executed successfully, can withdraw a certain `Amount` of `SUI` tokens from the blog's vault.
The return type of the method is described under the `result` key node as `Balance<SUI>`, as mentioned above, this is a resource type.


## How to Write DDDML models

### Using JSON Schema

Here is a schema file that still needs to be improved: https://raw.githubusercontent.com/wubuku/dddml-spec/master/schemas/dddml-schema.json

DDDML is a YAML-based DSL, and YAML is a superset of JSON, so JSON schema can be effective.

If your IDE supports it, you can configure it, and then have support for features like automatic completion when writing DDDML models.

For example, in VS Code's `.vscode/settings.json` file, you can set it up like this:

```json
{
    "yaml.schemas": {
        "https://raw.githubusercontent.com/wubuku/dddml-spec/master/schemas/dddml-schema.json": [
            "dddml/*.yaml", //file match pattern
            "dddml/*.yml"
        ]
    }
}
```
