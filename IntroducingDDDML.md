# Introducing DDDML: The Key to Low-Code Development for Decentralized Applications



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