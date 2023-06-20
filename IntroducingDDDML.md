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


[To be continued...]

