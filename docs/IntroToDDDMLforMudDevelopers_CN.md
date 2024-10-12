# 面向 MUD 开发者的 DDDML 简介

[English](IntroToDDDMLforMudDevelopers.md) | 中文

## 为什么你可能需要一门领域特定语言

作为 MUD 的开发者，你可能会问：既然我们已经在使用 MUD 框架，为什么还需要引入另一种工具？

简单来说：为了提高开发效率和软件质量。

MUD 无疑是一个优秀的开发框架，特别擅长全链游戏开发。尽管它被设计为通用的应用开发框架，但目前其主要优势仍集中在创建全链游戏方面。MUD 的架构优先考虑“全链”——链上的完整性，这完全符合某些用例的需求，但对其他情况可能带来挑战，例如实现链下混合解决方案以提高游戏的 tickrate。

为了补充 MUD 的优势，我们开发了一种创新的、AI 原生的领域特定语言（DSL）。这种语言在更高抽象层次上引入领域模型，同时受益于 MUD 强大的数据模型设计。

通过将 MUD 的实现框架与我们的方法相结合，我们既增强了开发效率，又确保了实现代码能反映高层设计——这对软件质量以及软件的可维护性至关重要，为开发者提供了更全面的 Web3 创作工具包。


## 赋能链上游戏创作者：DSL 和低代码解决方案

我们使用领域驱动设计（DDD）的低代码方法**特别适合游戏开发流程**。游戏设计（特别是“大游戏”）需要的全面规划阶段使得通过 DSL 将高级概念转化为软件设计变得更加容易，这对需要精确建模的链上游戏尤其有益。

我们开发了 DDDML，一种富有表现力的领域特定语言，用于促进这一过程。DDDML 允许开发者以人类可读和机器可处理的格式描述复杂的游戏机制，表示游戏实体、属性、关系和行为。

这种方法弥合了游戏设计和链上实现之间的差距，加速了开发过程，并提高了最终产品的质量和一致性。


## AI 驱动的 DSL 和低代码：Dapp 创新的未来

DDDML（领域驱动设计建模语言）集中记录了关键领域模型元素，支持代码生成工具，并自动化了软件开发的各个方面。

我们的低代码方法改变了开发流程，它可以让开发者的主要精力集中在两个关键方面：

1. 领域建模
   * 使用 DDDML（我们的领域特定语言）创建模型文件。
   * 我们计划引入 AI 辅助建模，进一步简化这一过程并提高领域模型的质量。

2. 实现具体的业务逻辑
   * 如果应用只需要基本的 CRUD（创建、读取、更新、删除）操作，那么甚至不需要编写额外的代码。
   * 对于更复杂的业务逻辑，开发者需要使用特定智能合约平台的语言（例如，EVM 平台上常用的 Solidity）来编写代码。
   * 我们的长期目标是开发一种平台无关的表达语言，使得开发者可以更轻松地编写跨平台的业务逻辑。

我们坚信 AI 技术能在领域建模和业务逻辑实现这两个方面为开发者提供强大的支持。我们致力于在这两个领域推动边界，在业务逻辑实现方面更是已经取得了切实的进展。

本文将重点介绍我们在实现业务逻辑方面的尝试，AI 在这方面的表现确实令人印象深刻。至于领域建模方面的 AI 辅助，虽然不是本文的重点，但我们已经开发了一些具体可行的想法，并正在积极进行实验性研究。初步结果看起来充满希望，这些进展让我们对 AI 辅助领域建模的未来感到兴奋。


## 通过示例理解 DDDML

DDDML 集中记录和展示了领域模型中的关键元素。它在概念层面描述领域模型，同时允许添加实现细节。

DDDML 使用一种称为 DDDML DOM（文档对象模型）的抽象数据结构，这是一个由各种节点类型组成的树形结构。这种结构可以用 JSON（JavaScript 对象表示法）或 YAML（YAML Ain't Markup Language）表示，后者是 JSON 的超集，更易于人类阅读。

在接下来的示例中，我们将使用基于 YAML 的 DDDML 来描述领域模型。

### 你可能需要做的准备工作

通过 DDDML，我们可以支持代码生成工具生成与领域模型紧密对应的软件代码。此外，DDDML 还可以自动生成软件文档、数据库模式、状态机图、API 规范等。

如果你不想只是阅读文档，而是想要亲自动手，看看 DDDML 能为你做什么，那么你可能需要准备以下工具：

* 安装 [MUD](https://mud.dev/quickstart)。作为 MUD 开发者，我相信你可能已经完成了这项工作。
* 安装 [Docker](https://docs.docker.com/engine/install/)。
* 安装 [Cursor IDE](https://www.cursor.com)。如果你想要自己亲自验证一下 DSL + AI 发生的化学反应，那么你可能需要一个编程助手。

然后，你可以参考 MUD 的[“快速开始”](https://mud.dev/quickstart#installation)，创建一个最简单的 MUD 项目，并将其运行起来。
接下来，你就可以使用 IDE 打开这个项目，开始你的 DDDML 探索之旅了。


### Hello World

在你的项目根目录中创建一个 `dddml` 目录，然后在这个目录中放入一个 `Counter.yaml` 文件，其内容如下：

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


在代码库的根目录，执行：

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

> **提示**
>
> 如果你之前运行过 `dddappp-mud`，现在碰到了什么奇怪的问题，那么你可以尝试先删除旧的容器和镜像，以确保使用的是最新的镜像：
>
> ```shell
> docker rm $(docker ps -aq --filter "ancestor=wubuku/dddappp-mud:master")
> docker rmi wubuku/dddappp-mud:master
> ```

这个操作会修改 MUD 配置文件：`packages/contracts/mud.config.ts`，以及生成上面的模型中定义的方法（`Counter.Increase`）对应的业务逻辑实现的 Solidity 脚手架代码，就在这里：`packages/contracts/src/systems/CounterIncreaseLogic.sol`。
你可能已经发现了这个文件的命名规律，它是由实体的名称 + 方法的名称 + `Logic.sol` 组成的。

此时，这个文件的代码可能像[这样](https://gist.github.com/wubuku/d7a45b868cb8f21b74e41127baf3b28e)。

你会发现，在这个文件中已经存在两个函数，它们的签名部分已经写好了，你只需要填充函数体部分。

你甚至可以不用自己来写这些代码，而是让 AI 来完成这个“完形填空”。比如，在 [Cursor IDE](https://www.cursor.com) 中，可以这样做：

* 使用快捷键 Cmd + A 全选当前文件的代码。（我使用的是 macOS 系统，Windows 系统需要把 Cmd 换成 Ctrl。）
* 使用快捷键 Cmd + L 打开 Cursor 的 CHAT 窗口（我测试采用的模型是 claude-3.5-sonnet）。
* 输入 `complete the functions`，回车。

见证奇迹的时刻到了。这是 [AI 帮我完成的代码](https://gist.github.com/wubuku/2b3691a9af146316d2811774868eb932)。

AI 第一次为我生成的代码就通过了编译，而且，我没有看出在逻辑上有什么问题。简直完美！


### 博客示例

在 `dddml` 目录下，创建一个 `blog.yaml` 文件，其内容如下：

```yaml
aggregates:
  Article:
    metadata:
      Preprocessors: ["CRUD_IT"] # 自动生成文章的 Create/Update 方法
      CRUD_IT_NO_DELETE: true # 但是不要生成 Delete 方法
    id:
      name: Id
      type: u64
      generator:
        class: sequence # 我们希望使用一个自动生成的序号来作为文章的 ID
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
            class: sequence # 同样，使用自动生成的序号来作为评论的 ID
            # tableName: CommentSeqIdGenerator # Default value
        globalId:
          columnNames: # 这里我们显式地指定了评论表的 ID 对应的两个列名，而没有使用默认值
            - ArticleId
            - CommentSeqId
        properties:
          Commenter:
            type: String
          Body:
            type: String
```

对于稍有面向对象编程（OOP）经验的开发者来说，理解 DDDML 模型所表达的内容应该不会太困难。
然而，你可能仍然会觉得手写 DSL 有些麻烦。
为此，我们计划在未来实现一个图形化界面（GUI）的 DDDML 建模器。同时，我们也在探索 AI 辅助建模的可能性。
也许在不久的将来，你只需要使用类似下面的提示词，就能得到像上面那样的 `blog.yaml` 文件：

```text
请为我设计一个使用 MUD 框架的 Dapp 的 DDDML 模型。这是一个博客应用，具有以下特征：

1. 主要实体：
   - 文章（Article）
   - 评论（Comment）

2. 文章实体：
   - 属性：标题、正文、作者
   - 操作：支持创建和修改，但不允许删除

3. 评论实体：
   - 属性：评论内容、评论者名称
   - 关联：必须关联到特定文章，不能独立存在
   - 操作：支持完整的 CRUD 功能（创建、读取、更新、删除）

4. 特殊要求：
   - 评论者名称：用户在发表评论时可以输入一个显示名称
   - 文章作者：考虑使用地址类型来表示作者

请根据这些需求，生成一个符合 DDDML 规范的模型。
```

有了这样的提示词，我们就可以期待 AI 为我们生成上面的 `blog.yaml` 文件。
我相信，结合这段提示词再看看上面的 YAML 文件，你应该能更好地理解它所表达的含义了——所以这里我就不再赘述了。

现在，再次执行 `docker run` 命令，你可以看看都生成了什么哪些以 `Logic.sol` 为名字后缀的文件。如果生成成功，那么应该包含：

* `ArticleCreateLogic.sol`
* `ArticleUpdateLogic.sol`
* `ArticleAddCommentLogic.sol`
* `ArticleUpdateCommentLogic.sol`
* `ArticleRemoveCommentLogic.sol`

这一次 AI 甚至没有用武之地，所有的函数工具都帮你写好了。就像我们前文说过的，如果对实体的 CRUD 操作就是应用想要的业务逻辑，那么你不用再自己再去写什么了。


#### 初步了解一下“数据类型”

在 DDDML 中，你可以使用所有 [MUD 数据模型支持的字段类型](https://mud.dev/store/data-model#field-types)来声明属性和参数的类型。
不仅如此，DDDML 还支持更多的数据类型。这是因为 DDDML 的类型系统是一个更高层次的抽象，它能够在生成代码时将“领域模型”中的抽象类型映射到具体的实现类型。

具体来说，在 DDDML 模型中我们使用以下一些“基本数据类型”：

##### 1. 整数类型

例如：

- `u8`（也可以写作 `uint8`）
- `u16`（也可以写作 `uint16`）
- `u32`（也可以写作 `uint32`）
- `u64`（也可以写作 `uint64`）
- `u256`（也可以写作 `uint256`）
- `int32`（也可以写作 `i32`）
- `uint32`（等同于 `u32`）

##### 2. 布尔类型

- `bool`

##### 3. 字符串类型

- `string`（也可以写作 `String`）

##### 4. 地址类型

- `address`

##### 5. 字节类型

- `bytes`

##### 6. 数组类型

例如：

- `uint8[]`
- `uint32[]`
- `uint64[]`
- `uint256[]`


##### 7. 枚举类型

下面的 DDDML 文档描述了一个枚举对象 `Weekday`：

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

这个枚举的基类型是 `u8`，即无符号 8 位整数。它的值从 1 到 7 对应星期一到星期日。这通常意味着生成的代码会用一个数字来代表一个星期的某天，例如 `3` 就是星期三。

我们的 DDDML 代码生成工具的 MUD 版本目前只是简单地为这样的枚举生成一个 Solidity `library`，里面包含了使用类似 `uint8 constant MONDAY = 1;` 这样的常量定义。

> 按照 DDDML 规范，枚举对象的 `baseType` 并不是必须指定的。代码生成工具可以视不同语言能够提供的特性，以及开发团队的编码规范等因素，为 DDDML 定义的枚举对象生成合适的代码。
>
> 有些语言中，如 Java 和 C#，有 enum 关键字，而有些语言中则没有枚举类型。在这种情况下，DDDML 工具可能会把枚举对象（类型）替换为枚举对象定义中声明的 `baseType`，有时候这也**不算**一个太糟糕的选择，毕竟这可能带来序列化、持久化处理方面的便利。


##### 8. 复合类型（Value Object）

当然，我们还可以使用这些基本类型构造复合类型（Value Object），我们在下面的示例中会看到。


### 来自全链游戏 Infinite Seas 的示例

下面的示例模型是从我们开发的全链游戏 [Infinite Seas](http://infiniteseas.io) 中摘取出来的，主要是保留了 `SkillProcess` 实体和相关的值对象。
`SkillProcess` 定义也有所简化，这里展示了它的属性，以及创建这个实体的 `Create` 方法。

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
        type: u32 # 物品的 ID
      Quantity:
        type: u32 # 物品的数量

aggregates:
  SkillProcess:
    id:
      name: SkillProcessId
      type: SkillProcessId
    properties:
      ItemId: # 进程所生产的物品的 ID
        type: u32
      StartedAt: # 进程开始的时间   
        type: u64
      CreationTime: # 生产产品所需要的时间
        type: u64
      Completed: # 进程是否已完成
        type: bool
      EndedAt: # 进程结束的时间
        type: u64
      BatchSize: # 生产产品的批次大小
        type: u32
      Existing: # 进程是否已存在
        type: bool
        # 当一个合法存在的对象的所有其他属性都可能为“默认值”时，我们需要一个专门的属性来识别它是否存在
      ProductionMaterials: # 生产所投入的原材料
        itemType: ItemIdQuantityPair
        tableName: SkillPrcMtrl 
        # 这里我们给保存这个属性的状态的 Table 取了一个短名字。
        # 如果我们不设置它，代码生成工具也会给这个 Table 取一个默认的名字，但是这个名字可能比较长。
        # 而 MUD 会对过长的表名进行截断，这可能会造成命名冲突
        description: "Actual input materials for production"

    methods:
      Create:
        isCreationCommand: true
        # parameters:
        event:
          name: SkillProcessCreated
```

在 Infinite Seas 中，玩家可以进行多种技能的生产活动，例如务农、伐木、采矿、建造等。
但是玩家同一时间可以执行的生产进程并不是无限的；比如说，一个玩家同一时间最多只能执行两个务农进程，一个伐木进程，一个采矿进程，一个建造进程等。
`SkillProcess` 就是用来管理这些进程的实体。

你可以看到 `SkillProcess` 的“领域 ID”的类型是我们定义了一个名为 `SkillProcessId` 的值对象，它由三个部分组成：

1. `SkillType`：技能的类型，例如务农、伐木、采矿、建造等。
2. `PlayerId`：执行该进程的玩家的 ID。
3. `SequenceNumber`：该进程的序列号。比如玩家的第一个务农进程，其 `SequenceNumber` 就是 `0`，第二个是 `1`。这个序列号可取的最大值，可能会随着玩家等级的提升而增加。

和上面博客示例中的 `Article` 实体不同，实体 `SkillProcess` 的 `id` 属性没有包含 `generator` 的信息。
这意味着在创建这个实体时，ID 需要由前端传入的。

我们还定义了一个值对象 `ItemIdQuantityPair`，它包含两个属性 `ItemId` 和 `Quantity`。
在游戏的模型中，很多地方（对象的属性、方法的参数）都需要用到“物品的 ID 和数量”这样的组合。
我们在这些地方可以直接使用 `ItemIdQuantityPair` 这个类型，这让模型的表述更加简洁明了。

这一次，我们没有使用 `CRUD_IT` 预处理器为实体 `SkillProcess` 自动添加 CRUD（Create/Update/Delete）方法；
而是为其定义了一个 `Create` 方法，并声明该方法是一个“创建命令”（`isCreationCommand: true`）。
调用此方法时，前端需要传入实体的 ID（`SkillProcessId`），无需通过参数显式说明；
创建 `SkillProcess` 实例所需的其他信息由后端（合约）决定，而非前端指定。
因此，我们没有为 `Create` 方法显式定义任何参数。

关于模型的其他部分解释，我们已在上面的 YAML 注释中详细说明，这里不再赘述。

是否觉得这个模型比之前的复杂了许多？是否期待（或怀疑）这次 AI 的表现？

使用 Cursor 打开文件 `SkillProcessCreateLogic.sol`，在 AI 开始工作前，文件可能看起来像[这样](https://gist.github.com/wubuku/ac4f965f5c467190e89cf2128fe0ef7e)。
你会发现工具在文件中生成了大量注释，可能觉得注释有些“过多”；
但我们的主要目的是让 AI（当然，你也可以）参考这些注释来完成业务逻辑代码的编写。

我使用了以下提示词来引导 AI 完成代码编写：

> Read the comments of the current file, and the file I referenced @SkillType.sol, and complete the functions.

需要引用哪些文件（如提示词中的 `SkillType.sol`），在我们生成的代码注释中也有提示。

这次，AI 为我完成的代码是[这样](https://gist.github.com/wubuku/f1b71f20d448edb2e10f53232fa7cb10)的。同样，一次就通过了编译，没有明显的逻辑问题。惊不惊喜？意不意外？😄

> **提示**
> 
> 在 DDDML 工具生成的代码注释中可能提到了一些不需要使用的文件，或遗漏了一些应该使用的文件，
> 但一般来说，开发者应该能够判断实现当前业务逻辑需要哪些文件。
> DDD 主张整个开发团队维护同一个领域模型，如果团队遵循这一最佳实践，开发者就能为 AI 提供更好的提示。


### 更多的例子

在这个[代码库](https://github.com/wubuku/hello-mud)的 [`dddml`](https://github.com/wubuku/hello-mud/tree/main/dddml) 目录下，你可以找到更多 DDDML 模型的例子。

这些例子大多数都来自于实际的开发项目，并且已经被用于生产环境。


### 总结

在这篇文章中，我们探讨了 DDDML 的基本概念、其在 Dapp 开发中的应用，以及如何通过 AI 辅助来简化开发过程。通过实际的示例，我们展示了 DDDML 如何帮助开发者快速构建复杂的领域模型，并生成相应的业务逻辑代码。

DDDML 为 MUD 开发者提供了一种强大的工具，能够在领域建模和业务逻辑实现方面显著提高开发效率。通过引入领域特定语言和低代码开发方法，开发团队可以将更多精力投入到软件的系统分析和高层次设计上。DDDML 和 AI 的结合使得编码阶段的工作变得极其高效，并确保实现代码与领域模型的一致性——这对于复杂应用的开发至关重要。

展望未来，随着 AI 技术的不断进步，我们可以期待 DDDML 和基于它构建的低代码工具在软件开发中的应用将更加广泛和深入。无论是通过自动化代码生成，还是通过智能化的领域建模，开发者都将能够更高效地实现他们的创意和想法。

我们完全可以对 DSL 与 AI 的结合抱有更多期待。


---

作者是 Web3 低代码平台 [dddappp](https://dddappp.org) 的创始人。文中提到的全链游戏 Infinite Seas 就是使用 dddappp 开发的。

[Infinite Seas Studios](http://infiniteseas.io) 团队使用几乎一样的 DDDML 模型来驱动 Infinite Seas 游戏的三个不同版本的开发：

* Sui 版本：https://github.com/wubuku/infinite-seas
* Aptos 版本：https://github.com/wubuku/aptos-infinite-seas
* EVM 版本：https://github.com/wubuku/hello-mud