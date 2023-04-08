# 使用 dddappp 低代码工具开发 Sui 去中心化应用


## Prerequisites

目前 dddappp 低代码工具以 Docker 镜像的方式发布，供开发者体验。

工具所生成应用的链下服务默认使用了 MySQL 数据库。

所以在开始体验前，你需要先：

* 安装 [Sui](https://docs.sui.io/build/install)。

* 安装 [Docker](https://docs.docker.com/engine/install/)。

* 安装 MySQL 数据库。

* 安装 JDK 和 Maven。工具目前生成的链下服务使用 Java 语言。

如果你已经安装了 Docker，可以使用 Docker 来运行一个 MySQL 数据库服务。比如：

```shell
sudo docker run -p 3306:3306 --name mysql \
-v ~/docker/mysql/conf:/etc/mysql \
-v ~/docker/mysql/logs:/var/log/mysql \
-v ~/docker/mysql/data:/var/lib/mysql \
-e MYSQL_ROOT_PASSWORD=123456 \
-d mysql:5.7
```


## 示例：重现 Demo 应用的开发过程

我们在 GitHub 上放置了一个使用 dddappp 低代码工具开发的 Demo 应用。这个应用的代码分为两部分：

* Sui Move 链上合约：https://github.com/wubuku/Dapp-LCDP-Demo/tree/main/sui_contracts

* Java 链下服务：https://github.com/wubuku/Dapp-LCDP-Demo/tree/main/sui-java-service

你可以按照下面的介绍重现该 Demo 的开发过程。


### 编写 DDDML 模型文件

你可以创建一个目录，比如叫做 `test`，来放置应用的所有代码，然后在该目录下面创建一个子目录 `dddml`。我们一般在 dddml 目录下放置按照 DDDML 的规范编写的模型文件。

你可以把这里的示例模型文件下载/拷贝到 dddml 目录：https://github.com/wubuku/Dapp-LCDP-Demo/tree/main/domain-model/sui

在这些模型中，有些生造的例子可能已经复杂到了有点“荒谬”的地步，但我们的工具没有被“难倒”。


### 运行 dddappp 项目创建工具

使用 Docker 运行项目创建工具：

```shell
docker run \
-v /PATH/TO/test:/myapp \
wubuku/dddappp:0.0.1 \
--dddmlDirectoryPath /myapp/dddml \
--boundedContextName Test.SuiTestProj1 \
--boundedContextJavaPackageName org.test.suitestproj1 \
--boundedContextSuiPackageName sui_test_proj1 \
--javaProjectsDirectoryPath /myapp/sui-java-service \
--javaProjectNamePrefix suitestproj1 \
--pomGroupId test.suitestproj1 \
--suiMoveProjectDirectoryPath /myapp/sui-contracts
```

上面的命令参数很直白：

* 注意将 `/PATH/TO/test` 替换为你实际放置应用代码的本机目录的路径。这一行表示将该本机目录挂载到容器内的 `/myapp` 目录。

* dddmlDirectoryPath 是 DDDML 模型文件所在的目录。它应该是容器内可以读取的目录路径。

* 把参数 boundedContextName 的值理解为你要开发的应用的名称即可。名称有多个部分时请使用点号分隔，每个部分使用 PascalCase 命名风格。Bounded-context 是领域驱动设计（DDD）中的一个术语，指的是一个特定的问题域范围，包含了特定的业务边界、约束和语言，这个概念你暂时不能理解也没有太大的关系。

* boundedContextJavaPackageName 是链下服务的 Java 包名。按照 Java 的命名规范，它应该全小写、各部分以点号分隔。

* boundedContextSuiPackageName 是链上 Sui 合约的包名。按照 Sui 的开发规范，它应该采用全小写的 snake_case 命名风格。

* javaProjectsDirectoryPath 是放置链下服务代码的目录路径。链下服务由多个模块（项目）组成。它应该使用容器内的可以读写的目录路径。

* javaProjectNamePrefix 是组成链下服务的各模块的名称前缀。建议使用一个全小写的名称。

* pomGroupId 链下服务的 GroupId，我们使用 Maven 作为链下服务的项目管理工具。它应该全小写、各部分以点号分隔。

* suiMoveProjectDirectoryPath 是放置链上 Sui 合约代码的目录路径。它应该使用容器内可以读写的目录路径。


上面的命令执行成功后，在本地目录 `/PATH/TO/test` 下应该会增加两个目录 `sui-java-service` 和 `sui-contracts`。

此时可以尝试编译链下服务。进入目录 `sui-java-service`，执行：

```shell
mvn compile
```

如果没有意外，编译应该可以成功。

此时，链上合约还不能通过编译，因为“业务逻辑”还没有实现。下面我们就来实现它们。


### 实现业务逻辑

工具已经在目录 `sui-contracts/sources` 下生成了一些以 `_logic.move` 结尾的文件。文件中包含实现业务逻辑的函数的脚手架代码，即函数的签名部分。现在你只需要填充其中函数的实现部分。

你可以考虑从这里拷贝已经写好的业务逻辑的实现代码：https://github.com/wubuku/Dapp-LCDP-Demo/tree/main/sui-contracts/sources

你还可以将这个 Demo 应用的代码库 clone 下来，然后执行像下面这样的一个 shell 脚本来完成拷贝工作（注意将 `_PATH_TO_/Dapp-LCDP-Demo` 和 `_PATH_TO_/test` 替换为你本机上的实际路径）：

```shell
#!/bin/bash

source_dir="_PATH_TO_/Dapp-LCDP-Demo/sui-contracts/sources"
target_dir="_PATH_TO_/test/sui-contracts/sources"

old_keyword="sui_contracts"
new_keyword="sui_test_proj1"

for file in "${source_dir}"/*_logic.move; do
  if [[ -f "$file" ]] && grep -q "$old_keyword" "$file"; then
    cp "$file" "${target_dir}/$(basename "$file")"
    sed -i "" "s/$old_keyword/$new_keyword/g" "${target_dir}/$(basename "$file")"
  fi
done
```

然后进入目录 `sui-contracts` 执行编译，应该可以编译成功了：

```shell
sui move build
```


### 发布 Sui 合约

在完成业务逻辑的编写之后，在目录 `sui-contracts` 下执行以下命令，将合约发布到链上：

```shell
sui client publish --gas-budget 30000
```

如果执行成功，会输出这次发布的交易摘要（Transaction Digest）。例如：

```shell
----- Transaction Digest ----
BZXe8c5nBjoyacUJTkcfoLgFuU9xWRksAMSfaEU3XrSM
----- Transaction Data ----
#...
```

记下这个交易摘要。


### 配置链下服务

打开位于目录 `sui-java-service/suitestproj1-service-rest/src/main/resources` 下的 `application-test.yml` 文件，设置发布的交易摘要。设置之后类似这样：

```yaml
sui:
  contract:
    jsonrpc:
      url: "https://fullnode.devnet.sui.io/"
      #url: "http://localhost:9000"
    package-publish-transaction: "BZXe8c5nBjoyacUJTkcfoLgFuU9xWRksAMSfaEU3XrSM"
```

这是链下服务唯一必需配置的地方，就是这么简单。


### 创建链下服务的数据库

使用 MySQL 客户端连接本地的 MySQL 服务器，执行以下脚本创建一个空的数据库（假设名称为 test2）：

```sql
CREATE SCHEMA `test2` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;
```

进入 `sui-java-service` 目录，打包 Java 项目：

```shell
mvn package
```

然后运行一个命令行工具，初始化数据库：

```shell
java -jar ./suitestproj1-service-cli/target/suitestproj1-service-cli-0.0.1-SNAPSHOT.jar ddl -d "./scripts" -c "jdbc:mysql://127.0.0.1:3306/test2?enabledTLSProtocols=TLSv1.2&characterEncoding=utf8&serverTimezone=GMT%2b0&useLegacyDatetimeCode=false" -u root -p 123456
```

### 启动链下服务

在 `sui-java-service` 目录下，执行以下命令启动链下服务：

```shell
mvn -pl suitestproj1-service-rest -am spring-boot:run
```

接下来你可以尝试提交一些交易，调用链上的 Move 函数了。


### 提交一些测试交易

在链下服务启动后，你可以访问这个网址，得到一个如何使用 Sui Client CLI 调用链上合约的速查表（Cheatsheet）：http://localhost:1023/api/sui.contract/SuiClientCLICheatsheet.md

在 Cheatsheet 中连刚才发布的合约的 Package Id、创建某些实体时需要使用到的（Id Generator 的）Object Id 都帮你填好了。你需要填写的参数有包含“类型和含义（名字）”的占位符。

你可以拷贝这些命令，视你的需要稍作修改，然后在命令行终端中直接执行。

比如，创建一个产品：

```shell
sui client call --package 0x88e91efb24e2e2fc9255af351d5035797071500df38b915f15b74271f389a595 --module product_aggregate --function create \
--args \"product_name_1\" \"1000\" \"0x95cf09389ed279d22e0df9baee507457e0359518c396569aa8f1247b918fb73d\" \
--gas-budget 100000
```

输出类似：

```shell
----- Transaction Effects ----
Status : Success
Created Objects:
  - ID: 0x96b16b23bcf70562889f2e6c74b3561d00829afade0b498dfa69bc085e2bc318 , Owner: Immutable
```

记录上面命令输出的产品的 Object Id，我们准备使用它来创建一个订单。

当然，你也可以链下服务的 RESTful API 来获得已经创建的产品的信息：http://localhost:1023/api/Products

执行下面的命令，可以创建一个订单：

```shell
sui client call --package 0x88e91efb24e2e2fc9255af351d5035797071500df38b915f15b74271f389a595 --module order_aggregate --function create \
--args \"0x96b16b23bcf70562889f2e6c74b3561d00829afade0b498dfa69bc085e2bc318\" \"1\" \
--gas-budget 100000
```

你可以访问 RESTful API 获取已创建的订单信息：http://localhost:1023/api/Orders

可以使用参数过滤订单信息，比如：http://localhost:1023/api/Orders?totalAmount=1000

---

我们知道，调用 Sui Move 合约的交易的输入参数只能包含基础类型或对象引用。而在我们的 DDDML 示例模型中，DaySummary 对象的领域 ID 是一个“多层嵌套的值对象”，这个 ID 由用户通过“Create 方法”赋值。你不用担心，工具生成的代码会帮你处理好这个问题。

执行下面的命令将创建一个 DaySummary 对象：

```shell
sui client call --package 0x88e91efb24e2e2fc9255af351d5035797071500df38b915f15b74271f389a595 --module day_summary_aggregate --function create \
--args 2023 \"ChineseLunar\" 4 false 10 \"Shanghai\" \"string_description\" \"vector_u8_meta_data\" '["string_array_data_item"]' '["vector_u8_optional_data"]' \"0x1676110fa451e75daf1d6a0204053662a8936e4267d475ac9e38101b9d1de340\" \
--gas-budget 100000
```

然后，通过这个接口，你可以获取已创建的 DaySummary 对象的列表：http://localhost:1023/api/DaySummaries


## 忽略某些文件的生成

项目创建工具生成的代码文件中，有一大部分是和模型相关的样板代码，当模型发生了修改后，重新运行项目创建工具，这些代码文件默认情况下会被重新生成和覆盖。这些文件的开头大多数都有“autogenerated”字样的注释。

如果你想要修改这些文件，并且在重新生成代码时它们不被覆盖，那么你可以在特定的目录下放置一个 `.dddmlignore` 文件来做到这一点：

* javaProjectsDirectoryPath 参数指定的目录。
* suiMoveProjectDirectoryPath 参数指定的目录。

`.dddmlignore` 文件与 `.gitignore` 文件类似，每一行代表一个忽略规则。比如，下面的规则表示需要忽略重新生成所有目录层级中以 Order 或 order 开头的文件：

```text
**/[Oo]rder*
```

## 敬请期待

### 增加 JSON Schema

你可能会觉得手写 DSL 模型有点麻烦。我们已经在完善 DDDML 的 JSON Schema。

DDDML 是一种基于 YAML 的 DSL，而 YAML 是 JSON 的超集，所以 JSON Schema 可以生效。

如果你的 IDE 支持，你可以配置一下，然后在用 DDDML 编写模型的时候，就有自动补全之类的支持了。

比如，在 VS Code 的 `settings.json` 文件中可以类似这样设置：

```json
{
  "yaml.schemas": {
    "/path/to/schema": [
      "*.yaml", # file match pattern
      "*.yml"
    ]
  }
}
```

更进一步的想法，是训练 AI 模型，让 AI 帮助我们编写 DDDML 代码。

### 增加更多对 DDDML 规范的支持

在开发基于 Sui 的 Dapp 方面，目前我们的命令行工具完成了对 DDDML 规范中比较基础的部分的支持。

在使用 DDDML 领域模型驱动传统应用开发方面，我们还有很多实践经验有待迁移到新的 Dapp 低代码开发工具上。

比如说：

* 为 Dapp 低代码工具增加对“领域基础类型”对支持。
    * 目前我们的 Dapp 低代码工具已经实现了对 Move 的基本类型（Primitive Types）的支持，以及对由基本类型组合而成的、几乎没有什么业务逻辑的“数据值对象”的支持。
    * DDDML 主张与鼓励大家使用领域中的术语进行分析和建模。我们希望做到：团队一致认可某个领域概念是值对象之后，就可以在 DDDML 文件中直接使用它来作为属性的类型。至于这个类型的具体实现，可以剥离到独立的库中。

* 我们可以在模型中声明实体的继承关系，工具可以为我们生成合适的代码，即使 Move 语言本身并没有对象继承的支持。

* 我们可以增加对状态机模式的支持。领域中有些实体可能具有一些重要的“定性”的属性，UML 专门提供了状态机图来描述它们。DDDML 自然也不会错过对状态机模式的支持。

* 我们可以增加对“树”——层次结构——的支持。我们经常会碰到一些需要构建层次结构的场景。比如一个公司的内部组织，它的各个部门、分支机构，可能就会组成一个甚至多个层次结构。

* ……

### 进一步探索“面向资源”与 DDDML 规范的结合

我们相信，DDDML 规范还可以进一步发展，以充分利用 Move 语言面向资源这一程序设计新范式的优势。

[TBD]


## 清理已经退出的 Docker 容器

执行命令：

```shell
docker rm $(docker ps -aq --filter "ancestor=wubuku/dddappp:0.0.1")
```

