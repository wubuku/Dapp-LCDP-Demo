# Awesome DDDML

English | [中文版](./README_CN.md)

Efficiency in the development of software, especially complex software, has always been a big issue.

We have invented a very expressive DSL for domain modeling called DDDML.
Using it, it is not only possible to accurately describe our knowledge of domains, but it is also easy to map these models to software implementation code.

Compared to other "competitors", our DSL is much closer to the problem domain and to natural language, which enables it to have great combinability with AI.

Our main project is called "[dddappp](https://www.dddappp.org)", which stands for Domain-Driven DAPP low-code Platform.

However, the "model-driven development" approach we used in this project is not only applicable to decentralized application development.
In fact, we've been using the same approach to drive the development of traditional enterprise software and Internet applications since 2016.

Kindly note: we're not making a "no-code" app for end-users, we're building a low-code platform for developers.

> Let me explain a bit more here: there is a consensus  (de facto standard)  in the industry on what the core features of a Low-Code platform should have.
> The bottom line is that they must take a "model-driven" development approach.
> For our discussion of this, see: https://www.dddappp.org/#what-is-a-true-low-code-development-platform

## Articles and Demonstrations

### DDDML Introduction

An introductory article about DDDML: ["Introducing DDDML: The Key to Low-Code Development for Decentralized Applications"](https://github.com/wubuku/Dapp-LCDP-Demo/blob/main/IntroducingDDDML.md).

### DDDML Introduction for MUD Developers

If you want to develop decentralized applications based on EVM, especially using [MUD](https://mud.dev) framework, it might be more appropriate to start understanding DDDML from this introductory guide: https://github.com/wubuku/Dapp-LCDP-Demo/blob/main/docs/IntroToDDDMLforMudDevelopers_CN.md

* Case study 1, Less Code, More Output: https://gist.github.com/wubuku/4315a6b99e62e985a8001ffebe2c1487

* Case study 2, DDDML + AI vs. AI Alone: https://github.com/wubuku/CaseStudy-DDDML-plus-AI-vs-AI-Alone


### DDDML x AI

AI-assisted development of EVM/MUD-based decentralized applications: https://github.com/wubuku/hello-mud?tab=readme-ov-file#leveraging-ai

AI-assisted development of AO decentralized applications: https://github.com/dddappp/AI-Assisted-AO-Dapp-Example


### Using dddappp as a fully on-chain game engine

#### Developing Sui fully on-chain game using dddappp

This is a production-level real-world example: https://github.com/wubuku/infinite-sea

#### Example of developing an Aptos fully on-chain game

The original [constantinople](https://github.com/0xobelisk/constantinople) is a game based on the fully on-chain game engine [obelisk](https://obelisk.build) running on Sui. (Note: obelisk is not a project of ours.)

Here we tried to implement the Aptos Move version of this game using the dddappp low-code tool: https://github.com/wubuku/aptos-constantinople/blob/main/README_CN.md

The developer can follow the README to reproduce the entire development and testing process of the game's contract and indexer.
The model file is written, code is generated, business logic is filled in the three files, and the development is done.

One thing that might be worth mentioning is that Aptos has a limit on the size of the Move contract packages that can be published (no more than 60k).
This is a common problem for larger applications on Aptos.
We can declare some module information in the model file,
and then we can automatically generate multiple Move contract packages.
(Note: the "module" here means the module concept in the sense of "DDD domain model", not the "module" in the Move language.)

### Sui Blog Example

Repository: https://github.com/dddappp/sui-blog-example

It only requires 30 or so lines of code (all of which is a description of the domain model)
to be written by the developer,
and then generates a blog example that emulates [RoR Getting Started](https://guides.rubyonrails.org/getting_started.html) in one click,
without requiring the developer to write a single line of other code.

Especially, without writing a single line of code,
the 100% automatically generated off-chain query service (sometimes we call it an indexer) has many out-of-the-box features.

### Aptos Blog Example

The [Aptos version](https://github.com/dddappp/aptos-blog-example) of blog sample.

### Sui Crowdfunding Example

A crowdfunding dapp for educational demonstration purposes:

https://github.com/dddappp/sui-crowdfunding-example


### Move Forms

We built a side-project in Aptos' Singapore Hackathon, called [Move Forms](https://github.com/dddappp/aptos-forms-demo), which is a no-code tool based on dddappp, and won second place.

Based on our current  Solana PoC version, we have already enabled community partners to build a Web3 native form tool called Solana Forms. See: https://github.com/dddappp/solana-forms-demo


### A More Complex Sui Demo

If you are interested, you can find a more complex Sui Demo here: ["A Sui Demo"](https://github.com/dddappp/A-Sui-Demo).
We have used a variety of "made-up" scenarios to demonstrate the versatility of dddappp.

### Low-code Development AO Application PoC

See: https://github.com/dddappp/A-AO-Demo

### Low-code Development Solana Application PoC

See: https://github.com/dddappp/A-Solana-Demo

### The Key to AO Ecosystem's Success: Microservices Architecture in the Web3 Era

The article is divided into two parts:

1. https://x.com/ArweaveOasis/status/1808788659828584751
2. https://x.com/ArweaveOasis/status/1815207753654452414
