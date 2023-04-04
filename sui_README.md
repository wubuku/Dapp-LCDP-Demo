# 使用 dddappp 低代码工具开发 Sui 去中心化应用

## Prerequisites

目前 dddappp 低代码工具以 Docker 镜像的方式发布，供开发者体验。

工具生成的应用的链下服务默认使用了 MySQL 作为数据库。

所以你需要先：

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

## 示例：重新 Demo 应用的开发过程

### 编写一些 DDDML 模型文件

你可以创建一个目录，比如叫做 `test`，来放置应用的所有代码，然后在该目录下面创建一个子目录 `dddml`。我们在 dddml 目录下放置按照 DDDML 的规范编写模型文件。

你可以考虑从这里的示例模型文件下载/拷贝到 dddml 目录下：https://github.com/wubuku/Dapp-LCDP-Demo/tree/main/domain-model/sui

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

* 注意将 `/PATH/TO/test` 替换为你本地放置应用代码的实际目录的路径。这一行表示将一个本地目录挂载到容器内的 `/myapp` 目录。

* Bounded-context 是领域驱动设计（DDD）中的一个术语，指的是一个特定的问题域范围，包含了特定的业务边界、约束和语言。暂时不能理解也没有关系，你把 boundedContextName 理解为你要开发的应用的名称即可。名称有多个部分时请使用点号分隔，每个部分使用 PascalCase 命名风格。

* boundedContextJavaPackageName 是链下服务的 Java 包名。

* boundedContextSuiPackageName 是链上 Sui 合约的包名。

* javaProjectsDirectoryPath 是放置链下服务代码的目录路径。链下服务由多个模块（项目）组成。它应该使用容器内的目录路径。

* javaProjectNamePrefix 是链下服务的各模块的名称前缀。

* pomGroupId 链下服务的 GroupId，我们使用 Maven 作为链下服务的项目管理工具。

* suiMoveProjectDirectoryPath 是放置链上 Sui 合约代码的目录路径。它应该使用容器内的目录路径。


上面的命令执行成功后，在目录 `/PATH/TO/test` 下，应该增加了两个目录 `sui-java-service` 和 `sui-contracts`。

此时可以尝试编辑链下服务。进入目录 `sui-java-service`，执行：

```shell
mvn compile
```

如果没有意外，应该可以成功。

### 实现业务逻辑

工具在目录 `sui-contracts/sources` 下生成了一些以 `_logic.move` 结尾的文件，里面已经包含实现业务逻辑的函数的脚手架代码（函数签名部分）。你只需要填充其中函数的实现部分。 

你可以考虑从这里拷贝已经写好的业务逻辑的实现代码：https://github.com/wubuku/Dapp-LCDP-Demo/tree/main/sui-contracts/sources

你可以将 Demo 代码库 clone 下来，然后执行一个 shell 脚本来做这个工作：

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

### 发布 Sui 合约

在完成业务逻辑的编码之后，执行以下命令，将合约发布到链上：

```shell
sui client publish --gas-budget 30000
```



