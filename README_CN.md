# Awesome DDDML

[English](./README.md) | 中文版

软件开发的效率，尤其是复杂软件的开发效率，一直是一个重大问题。

我们发明了一种非常富有表现力的领域建模 DSL，称为 DDDML。
使用它，不仅可以准确描述我们对领域的认知，还可以轻松地将这些模型映射到软件实现代码。

与其他“竞争对手”相比，我们的 DSL 更接近问题域和自然语言，这使得它能够与 AI 有很好的组合性。

我们的主要项目名为 [dddappp](https://www.dddappp.org)，代表“领域驱动的 DAPP 低代码平台”（Domain-Driven DAPP low-code Platform）。

然而，我们在这个项目中使用的“模型驱动开发”方法不仅适用于去中心化应用程序开发。
事实上，自 2016 年以来，我们一直在使用相同的方法来驱动传统企业软件和互联网应用程序的开发。

请注意：我们所做的不是开发为最终用户“无代码”应用程序，我们是为开发人员构建低代码平台。

> 让我在这里多解释一下：业界对低代码平台应具备的核心特性有一个共识（事实上的标准）。
> 最基本的是，它们必须采用“模型驱动”的开发方法。
> 关于这一点的讨论，请参见：https://www.dddappp.org/#what-is-a-true-low-code-development-platform


## 文章与演示

### DDDML 介绍

关于 DDDML 的入门介绍：["Introducing DDDML: The Key to Low-Code Development for Decentralized Applications"](https://github.com/wubuku/Dapp-LCDP-Demo/blob/main/IntroducingDDDML.md).

### 面向 MUD 开发者的 DDDML 介绍

如果你想要基于 EVM，特别是 [MUD](https://mud.dev) 框架，开发去中心化应用，可能从这个入门介绍开始了解 DDDML 会更合适：https://github.com/wubuku/Dapp-LCDP-Demo/blob/main/docs/IntroToDDDMLforMudDevelopers_CN.md

### DDDML x AI

AI 辅助开发基于 EVM/MUD 的去中心化应用：https://github.com/wubuku/hello-mud?tab=readme-ov-file#leveraging-ai

AI 辅助开发 AO 去中心化应用：https://github.com/dddappp/AI-Assisted-AO-Dapp-Example/blob/main/README_CN.md

### 将 dddappp 作为全链游戏引擎使用

#### 使用 dddappp 开发 Sui 全链游戏

这个一个生产级的实际案例：https://github.com/wubuku/infinite-sea

#### 用于开发 Aptos 全链游戏的示例

原版的 [constantinople](https://github.com/0xobelisk/constantinople) 是一个基于全链游戏引擎 [obelisk](https://obelisk.build) 开发的运行在 Sui 上的游戏。（注：obelisk 不是我们的项目。）

我们这里尝试了使用 dddappp 低代码开发方式，实现这个游戏的 Aptos Move 版本：https://github.com/wubuku/aptos-constantinople/blob/main/README_CN.md

开发者可以按照 README 的介绍，复现整个游戏的合约和 indexer 的开发和测试过程。模型文件写一下，生成代码，在三个文件里面填了下业务逻辑，开发就完成了。

有个地方可能值得一提。Aptos 对发布的 Move 合约包的大小有限制（不能超过60k）。这个问题在 Aptos 上开发稍微大点的应用都会碰到。我们现在可以在模型文件里面声明一些模块信息，然后就可以自动拆分（生成）多个 Move 合约项目。（注：这里说的模块是指领域模型意义上的模块，不是 Move 语言的那个模块。）


### Sui 博客示例

代码库：https://github.com/dddappp/sui-blog-example

只需要写 30 行左右的代码（全部是领域模型的描述）——除此以外不需要开发者写一行其他代码——就可以一键生成一个博客；
类似 [RoR 入门指南](https://guides.rubyonrails.org/getting_started.html) 的开发体验，

特别是，一行代码都不用写，100% 自动生成的链下查询服务（有时候我们称之为 indexer）即具备很多开箱即用的功能。


### Aptos 博客示例

上面的博客示例的 [Aptos 版本](https://github.com/dddappp/aptos-blog-example)。

### Sui 众筹 Dapp

一个以教学演示为目的“众筹” Dapp：

https://github.com/dddappp/sui-crowdfunding-example

### Move Forms

我们在 Aptos 新加坡黑客马拉松中开发了一个基于 dddappp 的无代码工具侧项目，名为 [Move Forms](https://github.com/dddappp/aptos-forms-demo)，并获得了第二名。

基于我们当前的 Solana PoC 版本，我们已经使社区合作伙伴能够构建一个名为 Solana Forms 的 Web3 原生表单工具。详见：https://github.com/dddappp/solana-forms-demo


### 复杂的 Sui 演示

如果你有进一步了解的兴趣，可以在这里找到一个更复杂的 Sui 演示：["A Sui Demo"](https://github.com/dddappp/A-Sui-Demo)。
我们使用了各种“生造”的场景，来展示 dddappp 的各种能力。

### 低代码开发 AO 应用的 PoC

见：https://github.com/dddappp/A-AO-Demo

### 低代码开发 Solana 应用的 PoC

见：https://github.com/dddappp/A-Solana-Demo

### The Key to AO Ecosystem's Success: Microservices Architecture in the Web3 Era

文章分两部分：

1. https://x.com/ArweaveOasis/status/1808788659828584751
2. https://x.com/ArweaveOasis/status/1815207753654452414
