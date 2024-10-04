# 面向 MUD 开发者的 DDDML 简介

[English](IntroToDDDMLforMudDevelopers.md) | 中文


## 为什么你可能需要 DDDML（一门领域专用语言）

作为 MUD 的开发者，你可能会问：既然我们已经在使用 MUD 框架，为什么还需要引入另一种工具？

简单来说：为了提高开发效率和软件质量。

MUD 无疑是一个优秀的开发框架，但它也存在一些局限性。比如说，尽管 MUD 声称自己是一个通用的应用开发框架，而不仅仅适用于链上游戏开发，但实际上，大多数开发者仍将其视为全链游戏引擎。在链上游戏开发方面，MUD 没有引入任何链下的混合解决方案来提高游戏的 tickrate。MUD 可能希望尽可能保持“链上”特性，但这在某些情况下可能成为一种限制而非优势。

我们相信，采用模型驱动的低代码开发方法不仅能提供更高的开发效率，还能弥补 MUD 的这些不足。

说到“模型”这个词，你可能已经注意到，MUD 中也有“数据模型”的概念，但它与我们低代码方法采用的领域模型处于不同的抽象层次。MUD 配置文件中定义的模型是抽象层次较低的“物理数据模型”，基本上可以视为程序实现代码的一部分。

然而，我们都知道，软件开发远不止是编程实现。特别是对于复杂的软件项目，编码往往不是开发过程中最耗时的部分。要开发高质量的软件，我们需要在分析和设计阶段投入大量精力。更重要的是，我们必须确保实现的代码能够准确反映分析和顶层设计的成果，这样才能保证代码的良好可维护性。

我们的低代码开发方法采用了 DDD（领域驱动设计）风格的领域模型。这种模型是面向对象分析（OOA）和面向对象设计（OOD）的有机结合。为了描述这种领域模型，我们设计了一门富有表现力的 DSL（领域专用语言）。

这种方法的优势在于，我们可以在软件开发的需求分析阶段就开始构建概念领域模型；在随后的设计和编码阶段，我们能够在同一个模型的基础上不断完善，确保前期工作的价值得到充分利用。

因此，我们的低代码开发方法不仅能加速整个开发过程，还能提高软件的整体质量。

## 什么是 DDDML

DDDML（Domain-Driven Design Modeling Language，领域驱动设计建模语言）是我们开发的一门 DSL（Domain-Specific Language，领域专用语言），用于描述 DDD（领域驱动设计）风格的领域模型。

DDDML 允许我们在一个集中的地方记录和展示领域模型中的关键元素。它不仅可以在概念层面描述领域模型，还允许我们添加实现层面所需的细节。通过 DDDML，我们可以支持代码生成工具创建与领域模型紧密映射的软件代码。此外，我们还可以使用 DDDML 自动生成软件文档、数据库 Schema 和状态机图等。

DDDML 的核心是一个抽象的数据结构，我们称之为 DDDML DOM（Document Object Model，文档对象模型）。它是一个由不同类型节点组成的树形结构。我们规定，这个数据结构必须可以用 JSON（JavaScript Object Notation，JavaScript 对象表示法）表示。JSON 是一种轻量级的数据交换格式，由值、对象和数组等元素组成。我们也可以使用 YAML（YAML Ain't Markup Language），它是 JSON 的超集，对人类来说更易读。

在接下来的示例中，我们将使用基于 YAML 的 DDDML 来描述领域模型，展示其在实际应用中的优势。


## 通过示例理解 DDDML

### 你可能需要做的准备工作

如果你不想只是阅读一下文档，而是想要亲自动手，看看 DDDML 能为你做什么，那么你可能需要手头准备以下工具：

* 安装 [MUD](https://mud.dev/quickstart)。作为 MUD 开发者，我相信你可能已经完成了这项工作。
* 安装 [Docker](https://docs.docker.com/engine/install/)。
* 安装 [Cursor IDE](https://www.cursor.com)。如果你想要自己亲自验证一下 DSL + AI 发生的化学反应，那么你可能需要一个编程助手。


然后，你可以参考 MUD 的[“快速开始”](https://mud.dev/quickstart#installation)，创建一个最简单的 MUD 项目，并将其运行起来。
接下来，你就可以使用一个 IDE 打开这个项目，开始你的 DDDML 探索之旅了。


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

> **Hint**
>
> 如果你之前运行过 `dddappp-mud`，那么你需要删除旧的容器和镜像，以确保使用的是最新的镜像：
>
> ```shell
> docker rm $(docker ps -aq --filter "ancestor=wubuku/dddappp-mud:master")
> docker rmi wubuku/dddappp-mud:master
> ```

这个操作会修改 MUD 配置文件：`packages/contracts/mud.config.ts`，以及生成上面的模型中定义的方法（`Counter.Increase`）对应的业务逻辑实现的 Solidity 脚手架代码，就在这里：`packages/contracts/src/systems/CounterIncreaseLogic.sol`。

此时，这个文件的代码可能像[这样](https://gist.github.com/wubuku/d7a45b868cb8f21b74e41127baf3b28e)。

你会发现，在这个文件中已经存在两个函数，它们的签名部分已经写好了，你只需要填充函数体部分。

你甚至可以不用自己来写这些代码，而是让 AI 来完成这个“完形填空”。比如，在 [Cursor IDE](https://www.cursor.com) 中，可以这样做：
* 使用快捷键 Cmd + A 全选当前文件的代码。（我使用的是 macOS 系统，Windows 系统需要把 Cmd 换成 Ctrl。）
* 使用快捷键 Cmd + L 打开 Cursor 的 CHAT 窗口（我测试采用的模型是 claude-3.5-sonnet）。
* 输入 `complete the functions`，回车。

见证奇迹的时刻到了。这是 [AI 帮我完成的代码](https://gist.github.com/wubuku/2b3691a9af146316d2811774868eb932)。

AI 第一次为我生成的代码就通过了编译，而且，我没有看出在逻辑上有什么问题。简直完美！


### 博客示例

【待完成】


## 延伸阅读

【待完成】




