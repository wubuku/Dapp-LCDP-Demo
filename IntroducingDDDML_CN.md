# DDDML 简介：开启去中心化应用低代码开发的钥匙

[English](IntroducingDDDML.md) | 中文

## 什么是 DDDML

DDDML（Domain-Driven Design Modeling Language，领域驱动设计建模语言）是我们发明的一种 DSL（Domain-Specific Language，领域专用语言），它可以用来描述 DDD（领域驱动设计）风格的领域模型。

DDDML 允许我们在一个地方集中地记录和展示领域模型中的关键元素。它可以在概念层面描述领域模型，也允许我们在其中添加领域模型在实现层面需要的细节。我们可以使用 DDDML 来支持代码生成工具去产生和领域模型之间具备亲密的映射关系的软件代码。我们还可以使用 DDDML 来自动化地产生软件的文档、数据库 Schema、状态机图等。

DDDML 的灵魂是一个抽象的数据结构，我们称之为 DDDML DOM（Document Object Model，文档对象模型）。它是一个树形结构，由不同类型的结点组成。我们规定，这个数据结构必须可以使用 JSON（JavaScript Object Notation，JavaScript 对象表示法）来表述。JSON 是一种轻量级的数据交换格式，它由值、对象、数组等元素组成。我们也可以使用 YAML（YAML Ain’t Markup Language），它是 JSON 的超集，对人类来说具备更好的可读性。

在下面的示例中，我们将会使用基于 YAML 的 DDDML 来描述领域模型。

### 什么是 DDD

至于什么是 DDD（领域驱动设计），本文只打算介绍几个 DDD 的最基本的概念。 关于 DDD 的更多内容已经有点超出本文的范畴，大家可以自行 Google 了解。

#### 实体

有一类对象拥有*标识符*（简称 *ID*），不管对象的状态如何变化，它的 ID 总是保持不变，这样的对象称之为*实体*。

比如说，我们的银行账户（Account），总是有一个编号（账号）的。我们存钱、取钱，账户里面的钱会发生变化，但是账号不变。我们通过这个账号，能够查询到账户的余额。所以，我们可以把银行账户建模为一个实体，选择它的编号作为这个实体的 ID。

对于很多开发人员来说，实体是他们非常熟悉的概念。特别是对于使用过 ORM 框架的开发人员，当你提到“实体”，他们可能马上就会想到“就是需要映射为数据表（Table）的那些对象嘛”。

#### 值对象

下面这几句话基本是从 DDD 一书中摘录出来的（有少量改写），我们认为它们是理解 DDD *值对象*的关键点：

* 用来描述领域的特定方面的、没有标识符的对象，叫做值对象。

* 忽略其他类型的对象，假设对象只有实体和值对象两种，那些符合实体定义的对象作为实体，剩下的对象就是值对象。

* 推荐将值对象实现为“不可变的（immutable）”。它们由一个构造器创建，并且在它们的生命周期内永远不会被修改。实现为不可变的，并且不具有标识符，值对象就能够被安全地共享，并且能维持*一致性*。

使用过 Hibernate ORM 的开发人员可能会注意到在 Hibernate 中有一个概念：*Dependent Objects*（非独立的对象）。你可以认为它所指的就是值对象。Dependent Objects 是不会被映射为数据库中的表（Table）的，它们会被映射为表中的列。

#### 聚合与聚合根、聚合内部实体

*聚合*（Aggregate）是 DDD 在战术层面最为重要的一个概念。它是 DDD 可以在战术层面应对“软件核心复杂性”的关键。

什么是聚合？聚合在对象之间，特别是实体与实体之间划出边界。聚合内的实体分为两种：*聚合根*与*聚合内部实体*（或者叫*非聚合根实体*）。

一个聚合只能包含一个聚合根。当客户端需要访问一个聚合内部实体的状态时，最先能得到的只有聚合根，然后通过这个聚合根，才能进一步访问到聚合内的其他实体。

从一个聚合根出发能够访问到的实体可以认为是一个整体。聚合内部实体的生命周期由它们所属的聚合根控制。很多时候，一个聚合内就只有聚合根这一个实体。

##### 例子：Order、OrderHeader、OrderItem

为“订单”建模，我们可能会得到：

* 一个叫做 Order（订单）的聚合（Aggregate）。
* 这个订单聚合的聚合根（Aggregate Root）是一个叫做 OrderHeader（订单头）的实体。
* 通过 OrderHeader（订单头）实体，我们可以访问到 OrderItem（订单行项）这个聚合内部实体的一个集合。

在这里，Order 聚合与 OrderHeader 聚合根两者的名字并不相同，这显示了聚合与聚合根这两个概念的微妙的区别。

不过，大多数时候聚合和它的聚合根拥有同样的名字。需要指出的是，目前我们的 DDDML 工具在生成去中心化应用时，并不支持为聚合以及聚合根指定不同的名字。


### 一些 DDDML 术语

在下面的行文中，我们在讲解 DDDML 示例时会使用一些 DDDML 的术语。

考虑到 JSON 中使用的一些术语，比如 Object、Array，在软件开发领域实在是用得太“滥”了，为了避免混淆，我们决定对部分 JSON 概念的名字进行“替换”。

首先，既然 DDDML DOM 是一个树结构，那么我们对这个结构中的元素称之为*结点*（Node）就是很自然的事情了。

在 DDDML DOM 中，我们把 JSON 的对象称为 Map，它是一个无序的键值对的集合。我们把 JSON 的数组称为 List，它是一个有序的值的集合。我们把 JSON 的值称为 Object，它可以是任意类型的值。我们还有一些其他类型的结点，比如 String、Number、Boolean 等，它们和 JSON 中的同名概念所指一致。

我们可以使用*路径*来选取 DDDML DOM 中的结点。路径是由斜杠分隔的结点名称组成的字符串。比如说，`/aggregates/Car/entities/Tire/entities/Position/properties/MileAge` 这样一个路径，就可以选取下面的 YAML 文档倒数第二行那个名为 MileAge 的键结点。

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

## 去中心化应用开发为何需要 DDDML

可能大家首先会想到一个问题：难道“传统”低代码平台不可以用来开发去中心化应用吗？

我们认为，已有的传统低代码平台并不适合去中心化应用的开发。这是因为，传统的企业应用程序开发平台主要依靠实体关系模型（E-R模型）和关系模型（SQL数据库中使用的模型）推动开发。

例如，[OutSystems](https://www.outsystems.com/) 既使用 E-R 模型，也使用关系（数据）模型，而一些企业应用程序开发平台只使用其中的一种。

E-R 模型和关系模型所使用的概念有很直接的对应关系。它们所生成的模型代码可以轻松运行在传统的企业软件技术基础架构上，如 SQL 数据库。但是，它们很难运行在全新的去中心化技术基础架构上——在这个世界里，主流的智能合约平台和“去中心化账本”的建构方式与传统的 SQL 数据库相距甚远。

### 解决问题的关键是建模范式

DDD 风格的领域模型是相对高度抽象的面向对象模型。

将高抽象级别的领域模型映射到低抽象级别的实现对象模型、关系模型等相对容易，并且通常可以通过自动化工具完成。

所以，关键思想是我们应该有一个领域建模语言，可以用来准确地描述领域中的关键概念。这种语言应该是一个 DSL，可以被可视化工具采用，同时易于人类阅读和编写。

以这个 DSL 描述的领域模型为核心，我们可以制作一个工具链，从模型中生成各种应用程序的实现代码，然后在各种技术基础架构上运行应用程序。

## 通过示例讲解 DDDML

下面我们将以“开发基于 Move 的去中心化应用”为背景来讲解 DDDML 的使用。 虽然 DDDML 一开始诞生的时候，是用于驱动“传统”企业应用以及互联网应用的低代码开发；并且，从理论上来讲它也可以用于基于“非 Move”的去中心化应用的开发。

需要说明的是，Move 语言还处于发展的早期阶段，采用 Move 作为智能合约语言的区块链/平台，比如 Sui、Aptos、Starcoin、Rooch 等，它们支持的编程特性可能会有所差异。它们的主要差异其实并不在于 Move 语言本身，而在于所支持“状态存储”模型各有所不同。

所以，本文中的 DDDML 示例，有些代码可能仅适用于基于“特定 Move 智能合约平台”的应用的生成，对此，在讲解的时候我们会特别指出。

### 示例 1：一个“产品”聚合

这是一个描述“产品”模型的 DDDML 文档：

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

我们需要在 `/aggregates` 这个结点下定义一个或多个聚合模型。上面的例子中，在 `/aggregates/Product` 这个键结点下定义一个名为“产品”的聚合（以及同名的聚合根）。

产品这个聚合根（实体）的 ID 是一个字符串，长度为 20。 我们使用一个类型（class）为 `sequence` 的生成器来生成这个 ID。

我们可以给这个生成器所使用“结构体”命名，在上例中名为 `ProductIdGenerator`，这可以更好地控制 Move 代码的生成结果。

然后，产品这个实体具有两个属性，分别是 `Name` 和 `UnitPrice`，顾名思义，它们表示产品的名称和单价。

在每个属性的名称键结点下，我们使用 `type` 这个关键字来指定属性的类型。在这里，我们指定了产品名称的类型是字符串，产品单价的类型是 `u128`（无符号 128 位整数）。

在 `/aggregates/Product/methods` 这个键结点下，我们了定义这个聚合的 `Create` 方法。

我们使用 `isCreationCommand: true` 指出该方法是一个创建命令。即如果它执行成功，就会创建一个产品对象的实例。

在 `parameters` 这个键结点下描述了方法的参数。`event/name` 这个键结点指出这个方法如果执行成功，会触发一个名为“产品创建”（ProductCreated）的事件。

#### 注意不同的命名风格：camelCase 与 PascalCase

细心的读者可能已经注意到，在这个 DDDML 文档中，有些 Map（也就是“JSON 的 Object”）的键（key）是以 `camelCase` 风格命名的，有些则是以（大写字母开头的）`PascalCase` 风格命名的。

这其实是有意为之的。

在 DDDML 规范中的定义的那些需要在特定的位置出现、具有特定含义的键（key），都使用了 `camelCase` 风格的命名，这样的键（key）属于 DDDML 的*关键字*。而在 DDDML 规范定义的关键字之外的那些“领域对象”的**名称**，我们强烈建议使用 `PascalCase` 风格命名。

因为我们有意选择了不同的命名风格，所以当我们通过肉眼阅读 DDDML 文档的时候，就很容易从中分辨出哪些是 DDDML 的关键字，哪些是文档描述的当前领域模型中的概念。

### 示例 2：一个“订单”聚合

这是一个描述“订单”模型的 DDDML 文档的一部分：

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

上面的 DDDML 代码定义了一个名为 `Order` 的聚合及同名聚合根，以及一个名为 `OrderItem` 的聚合内部实体。

#### “订单”聚合

在 `/aggregates/Order/id` 这个键结点下，我们定义了订单聚合根的 ID。订单的 ID 的名字为 `OrderId`，类型为 `string`。订单的 ID 由一个类型（class）为 `assigned` 的生成器生成，意思是这个 ID 是由“用户”赋予的。显然，我们需要一个“表”来保证一个 ID 不会被赋予不同的对象实例。为了让生成的代码如我们所愿，我们可以指定这个表在代码中的命名，这里是 `OrderIdTable`。

在 `/aggregates/Order/properties` 这个键结点下，我们定义了订单的属性分别表示订单的总金额、预计发货日期和订单项。

订单的总金额（`TotalAmount`）属性是一个类型为 `u128` 的属性。

订单的预计发货日期（`EstimatedShipDate`）属性是一个类型为 `Day` 的可选属性（`optional: true`）。这里的 `Day` 是一个自定义的值对象，下面我们会看到它的定义。

订单的订单项（`Items`）属性是一个由类型是 `OrderItem` 的元素所组成的集合（`itemType: OrderItem`）。这里的 `OrderItem` 是一个聚合内部实体。

#### “订单项”实体

在 `/aggregates/Order/entities/OrderItem` 这个键结点下，我们定义了“订单项”这个聚合内部实体。

在这里定义的订单项的 `id` 是个 local ID（局部 ID）。所谓的 Local ID，指的是对于这个实体（类型）来说，只要保证在同一个外部实体（父实体）的实例内、该实体（类型）的不同实例之间这个 ID 的值具备唯一性就可以了。

我们将订单项的 ID 命名为 `ProductObjectId`，声明其类型为 `ObjectID`。这个名字表明它指向一个产品（Product）对象。如果业务逻辑要求是一个订单内的不同的订单项不能指向同一个产品，那么产品的对象 ID 就可以用来作为订单项的 ID。

注意，这里的 `ObjectID` 是一个平台特定的类型。这里我们假设是在开发一个基于 Rooch 的去中心化应用。在 Rooch Framework 中，每个对象都有一个全局唯一的 ID，这个 ID 的类型是 `ObjectID`。

在 `/aggregates/Order/entities/OrderItem/properties` 结点下我们定义了订单项的属性，分别表示数量和金额。

#### 操作“订单”的方法

在 `/aggregates/Order/methods/Create` 这个键结点下，我们定义了订单的创建（`Create`）方法。

这个方法是一个创建命令（`isCreationCommand: true`），它有两个参数：产品对象 ID 和数量。

这里我们假设业务需求是在创建订单的时候，必须同时创建第一个订单项。所以在这个方法中，两个参数分别表示的是第一个订单项的产品的“对象 ID”以及这个产品的订购数量。

另外，在 DDDML 工具生成这个“创建命令”的代码时，会自动为你添加一个表示聚合根 ID 的参数。

这个方法如执行成功，会触发一个名为 `OrderCreated` 的事件。 默认情况下，这个方法产生的事件对象会包含与所有参数同名、同类型的属性。但是，如果你想要为这个事件添加更多的属性，你可以在 `/aggregates/Order/methods/Create/event` 这个键结点下定义它们。

比如，在这个例子中，我们为 `OrderCreated` 对象添加了三个属性，分别表示产品的单价、订单的总金额和订单的所有者。

### 示例 3：组合值对象

这是一个生造的值对象的示例 DDDML 文档：

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

这个例子是生造的，那么我们不需要去讨论这个模型的设计是否合理。在此它只是展示了 DDDML 的一个特性，可以将把一个值对象作为另一个值对象的属性的类型，从而组合出更复杂的值对象。

这个文档描述了三个值对象：Year、Month 和 Day。

其中，Year 对象有两个属性：Number 和 Calendar。分别表示年份和日历类型。

Month 对象有三个属性：Year、Number 和 IsLeap。它嵌入了一个 Year 对象作为它的属性，表示年份；Number 表示月份；IsLeap 表示这个月是否是闰月。

Day 对象有三个属性：Month、Number 和 TimeZone。它嵌入了一个 Month 对象作为它的属性，表示月份；Number 表示日期；TimeZone 表示时区。

这些属性都有不同的类型，如 u16、u8、bool 和 String。

### 示例 4：一个 Blog 系统

这是一个描述 blog 系统的 DDDML 模型文档：

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

这个文档包含了两个聚合：`Article`（文章）和 `Tag`（标签），以及一个值对象模型：`ReferenceVO`。

#### “文章”聚合

我们使用 `/aggregates/Article/id` 这个键结点来定义文章的 ID。

关于聚合根实例的 ID 如何生成，如果平台支持，我们可以选择使用平台提供的“对象” ID，而不需要设置 `id` 的 `generator` 信息。

这个例子假设我们是基于 Rooch 来开发我们的 blog 系统，那么，可以选择将文章的 ID 的名字设置为 `id`，类型设置为 `ObjectID`，则 DDDML 工具生成的 Move 代码在创建文章时就会使用 Rooch 平台提供的对象 ID 作为文章的 ID。 至于 `id` 的 `arbitrary` 是否为 `true`，目前对我们的 DDDML 工具生成代码并无实际影响。它更多是一种描述性的信息，表示我们其实并不关心文章的 ID 的格式之类，只要它是一个唯一的 ID 就好。

不只是 Rooch，其他 “Move 智能合约平台”（链），比如 Sui，也是为每个“对象”提供了唯一的 object ID 的。如果这里我们打算生成基于 Sui 的 Move 代码，那么可以将文章的 ID 的名字设置为 `id`，类型设置为 `UID`，则生成的 Sui Move 代码在创建文章时就会使用 Sui 平台提供的 UID 作为文章的 ID。

在 `/aggregates/Article/properties` 这个键结点下，我们定义了文章的属性：`Title`、`Author`、`Content`、`Tags` 和 `References`，它们分别表示文章的标题、作者、内容、标签和引用。

在这些属性的键结点下，我们使用 `type` 或 `itemType`（这两者只能定义一个）来指定（非集合）属性的类型或（集合）属性的元素的类型。

在这里，我们指定了文章标题的类型是 `String`，文章作者的类型是 `address`（这是一个特殊的类型，表示账户地址），文章内容的类型是 `String`。

文章的标签（tags）属性是一个由类型是 `ObjectID` 的元素所组成的集合（`itemType: ObjectID`）。

文章的引用（references）属性是一个从聚合根实体（文章）导航到 `Reference`（文章的引用）这个聚合内部实体的“关系”。 对于 OO 模型来说，两个实体之间 one-to-many 的关系是使用前者的一个集合属性来表示的。所以这里我们使用 `itemType: Reference` 来描述文章和它的引用之间的 one-to-many 关系。

显然，我们还需要进一步描述“引用”这个实体是什么，这些描述位于 `/aggregates/Article/entities/Reference` 这个键结点下。

聚合内部实体 `Reference` 的 ID 是个 local ID。所谓的 Local ID，指的是对于这个实体（类型）来说，只要保证在同一个外部实体（父实体）的实例内、该实体（类型）的不同实例之间这个 ID 的值具备唯一性就可以了。

在这里，我们指定了引用的 local ID 的名称是 `ReferenceNumber`，类型是 `u64`（无符号 64 位整数）。

然后，在 `/aggregates/Article/entities/Reference/properties` 结点下我们定义了引用的属性，`Title`、`Author`、`PublicationYear`、`Url` 等。顾名思义，它们分别是引用的标题、作者、出版年份、网址等属性。

值得注意的是，这里面有些属性的 `optional` 值为 `true`，比如 `PublicationYear`、`Url` 等。这表示这些属性是可选的，即可以没有值。在生成 Move 代码时，会为可选属性生成 `Option<T>` 类型的字段。

在 `/aggregates/Article/methods/Create` 这个键结点下，我们定义了“创建文章”的方法（`Create`）。

值得注意的是，这个方法的参数（`parameters`）包含一个元素类型为 `ObjectID` 的集合属性 `Tags`，以及一个元素类型为 `ReferenceVO` 值对象的集合属性 `References`。下面我们会看到值对象 `ReferenceVO` 的定义。

然后我们还定义了用于操作引用的几个方法。

* “添加引用”方法的参数包括引用编号 `ReferenceNumber` 和 `Title` 等。需要注意的是，这里我们并不要在方法中定义一个表示聚合根 ID 的参数，DDDML 工具会为你自动添加这个必须的参数。这个方法执行成功会触发一个名为“引用已添加”（`ReferenceAdded`）的事件。
* “更新引用”方法的参数包括引用编号、标题、网址和作者（`Author`）等。它会触发一个名为“引用已更新”（`ReferenceUpdated`）的事件。
* “移除引用”方法包含一个参数：引用编号。它会触发一个名为“引用已移除”（`ReferenceRemoved`）的事件。

#### “标签”聚合

在 `/aggregates/Tag` 这个键结点下，我们定义了标签聚合。

标签的（领域）ID 的名称是 `Name`，类型是 `String`。除了这个领域 ID，标签没有其他更多属性。

Tag 对象实例的 ID 由一个类型（class）为 `assigned` 的生成器生成，意思是这个 ID 是由“用户”赋予的。显然，我们需要一个“表”来保证一个 ID 不会被赋予不同的对象实例。为了让生成的代码如我们所愿，我们可以指定这个表在代码中的命名，这里是 `TagNameTable`。

在 `/aggregates/Tag/methods` 这个键结点下，我们定义了一个“创建”（`Create`）标签的方法。 我们没有为这个方法显式定义任何参数（`parameters`）。在 DDDML 工具生成这个方法的代码时，会自动为你添加一个表示聚合根 ID 的参数。这个方法执行成功会触发一个名为“标签已创建”（`TagCreated`）的事件。

#### “ReferenceVO” 值对象

最后，在 `/valueObjects` 这个键结点下， 我们定义了一个名为 `ReferenceVO` 值对象。我们主要在“创建文章”的方法参数中使用了这个值对象（类型）。

该值对象的属性包括： 引用编号、标题、网址和作者。其中有些属性是可选的。


### 示例 5：枚举对象

下面的 DDDML 文档描述了两个枚举对象：

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

这两个枚举对象都用来表示“星期几”的概念，但它们有不同的基类型（`baseType`）。`Weekday` 的基类型是 `u8`，即无符号 8 位整数。它的值从 1 到 7 对应星期一到星期日。这意味着生成的代码会用一个数字来代表一个星期的某天，例如 3 就是星期三。

`Weekday2` 的基类型是 String，即字符串。它的值是每个星期的英文缩写，例如 Mon 就是 Monday，Tue 就是 Tuesday，依此类推。这意味着生成的代码会用一个简短的字符串来代表一个星期的某天，例如 Wed 就是星期三。

需要注意的是，DDDML 中的枚举对象类似于 C 系语言中的 enum 类型，而与 Rust 中的 enum 类型不太相同。

按照 DDDML 规范，枚举对象的 `baseType` 并不是必须指定的。 DDDML 代码生成工具可以视不同语言能够提供的特性，以及开发团队的编码规范等因素，为 DDDML 定义的枚举对象生成合适的代码。

有些语言中，如 Java 和 C#，有 enum 关键字，而有些语言中则没有枚举类型。在这种情况下，DDDML 工具可能会把枚举对象（类型）替换为枚举对象定义中声明的 baseType（基类型），有时候这也**不算**一个太糟糕的选择，毕竟这可能带来序列化、持久化处理方面的便利。

### 示例 6：自动添加 CrUD 方法以及控制事件

下面是一个使用 `MOVE_CRUD_IT` 预处理器自动给一个 `Post` 聚合根实体添加 CrUD（Create / Update / Delete）方法的例子：

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

当然，我们也可以让预处理器忽略其中某些 CrUD 方法的生成。

你可能已经发现，在上面的例子中，聚合根的元数据中存在 `CRUD_IT_NO_UPDATE: true` 这一行。
那是因为，上面的例子假设了的业务需求是：用户不能更新贴子，只能创建和删除贴子。

---

查看生成的合约代码，你可能发现，添加贴子触发的是 `PostCreated` 事件，删除贴子触发的是 `PostDeleted` 事件，在代码使用两个不同的对象（对于 Move 代码来说，是结构体）来表示它们。这看着有点冗余。
还有就是，这种情况下，链下的服务拉取事件的时候可能需要从两个事件流拉取，代码写起来可能也会相对麻烦一点。

你可能希望将操作 `Post` 操作的事件合并为一种，这可以减少不少的代码量。

这当然可以做到。你可以修改模型文件，添加以下几行：

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

具体来说，上面添加的代码表示的是，“创建”和“删除”帖子触发的事件都用同一个名为 `PostEvent` 的对象来表示，仅使用一个 `EventType` 字段来区分它们。

你可能已经发现，你不需要像下面这样，在 `PostEvent` 这个事件对象中定义 `PostId`、`Poster` 等属性，`MOVE_CRUD_IT` 预处理器会自动为你生成它们。

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

### 示例 7：使用资源

在面向资源编程中，资源指的是一种不可复制、不可丢弃的特殊数据。

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

上面的例子中，在 `/typeDefinitions/Balance` 这个键结点下定义了一个名为“余额”的类型，它是一个资源类型，表示一种可移动的资产。
我们使用 `moveType` 这个关键字来指定这个类型在 Move 代码中的对应类型，即 `sui::balance::Balance`——这里我们假设正在开发的是基于 Sui 的 Dapp。
然后，我们为这个资源类型指定初始化时需要的创建默认值（空值）的逻辑（`defaultLogic`），以及销毁操作的逻辑（`destroyLogic`）。

在 `/typeDefinitions/SUI` 这个键结点下定义了一个名为“SUI”的代币类型。
我们指定这个类型在 Move 代码中的对应类型为 `sui::sui::SUI`。

在 Sui Move 中，单例对象一般使用 One-Time-Witness 模式在模块的 `init` 函数中创建。
我们可以在 DDDML 中定义一个名为 `__Init__` 的方法来控制 `init` 函数的逻辑。
在上面的例子中，我们指示 `Blog` 单例对象创建之后即共享出去（`event/isObjectShared: true`），让其他人可以也可以使用它。

我们使用 `Donate` 这个方法接受用户捐赠的 `SUI` 代币。
`shouldCallByReference: true` 指出这个方法需要通过一个 `Blog` 对象的 Mutable 引用来调用。关键字 `shouldCallByReference` 目前只针对 Sui Move 平台有效。
参数 `Amount` 的类型是 `Balance<SUI>`，如上面所言，这是一个资源类型。
这个方法执行后会触发一个名为“捐赠已收到”（`DonationReceived`）的事件。
在 `event/properties` 这个键结点下描述了事件的属性。事件属性 `Amount` 的类型是 `u64`，表示捐赠的代币数量。显然，事件属性的类型不能使用资源类型。

`Withdraw` 方法如果执行成功，可以从博客的资金库中提取出一定数量（`Amount`）的 `SUI` 代币。
在 `result` 这个键结点下描述了方法的返回值类型是 `Balance<SUI>`，如上所述，这是一个资源类型。


## 如何编写 DDDML 模型

### 使用 JSON Schema

这里有一个尚待完善的 DDDML JSON Schema 文件：https://raw.githubusercontent.com/wubuku/dddml-spec/master/schemas/dddml-schema.json

DDDML 是一种基于 YAML 的 DSL，而 YAML 是 JSON 的超集，所以 JSON Schema 可以生效。

如果你的 IDE 支持，你可以配置一下，然后在编写 DDDML 模型的时候，就有自动补全之类的支持了。

比如，在 VS Code 的 `.vscode/settings.json` 文件中可以像这样设置：

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
