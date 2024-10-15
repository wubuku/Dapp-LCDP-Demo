
# 事件风暴（Event Storming）指南

## 概述

事件风暴是一种用于探索和建模复杂业务领域的协作工作坊方法。它通过团队成员的共同参与，快速识别和分析业务中的关键事件，从而建立对业务流程的全面理解。

## 核心概念

### 事件
业务中已经发生的事实，通常用橙色便签表示。例如，"用户已注册"。

### 命令
触发事件的操作，通常用蓝色便签表示。例如，"注册用户"。

### 参与者
执行命令或触发事件的人或系统，通常用黄色便签表示。

### 外部系统
与业务相关的外部系统，通常用粉色便签表示。

### 策略
根据业务规则自动或手动触发的操作，通常用紫色便签表示。

### 读模型
提供给参与者用于决策的信息，通常用绿色便签表示。

### 聚合
一组相关的对象或实体，通常用大黄色便签表示。

## 事件风暴的优势

事件风暴的主要优势在于它能够在短时间内通过可视化和面对面的沟通，帮助团队成员建立对业务领域的共享理解，并发现潜在的改进点和风险。

## 便签详细信息

每种便签可能记录的额外信息：

### 事件（橙色便签）
- 时间戳：事件发生的具体时间或顺序
- 描述：对事件的详细描述，提供更多上下文信息
- 触发条件：事件发生的前提条件或原因
- 结果：事件发生后的直接结果或影响
- 相关参与者：与事件相关的人员或系统
- 备注：任何需要补充的说明或讨论中的重要观点

### 命令（蓝色便签）
- 执行时间：命令执行的具体时间
- 描述：对命令的详细描述
- 触发条件：命令被触发的条件或原因
- 预期结果：命令执行后的预期结果
- 相关事件：命令触发的事件
- 备注：任何需要补充的说明或讨论中的重要观点

### 参与者（黄色便签）
- 角色描述：参与者在业务流程中的角色和职责
- 权限：参与者的权限范围
- 相关命令和事件：参与者执行的命令和触发的事件
- 备注：任何需要补充的说明或讨论中的重要观点

### 外部系统（粉色便签）
- 系统描述：外部系统的功能和作用
- 接口：与外部系统交互的接口或方法
- 相关事件和命令：与外部系统相关的事件和命令
- 备注：任何需要补充的说明或讨论中的重要观点

### 策略（紫色便签）
- 策略描述：策略的详细描述和目的
- 触发条件：策略被触发的条件或原因
- 预期结果：策略执行后的预期结果
- 相关事件和命令：策略触发的事件和命令
- 备注：任何需要补充的说明或讨论中的重要观点

### 读模型（绿色便签）
- 数据描述：读模型中包含的数据和信息
- 来源：数据的来源和获取方式
- 使用场景：读模型在业务流程中的使用场景
- 相关事件和命令：读模型支持的事件和命令
- 备注：任何需要补充的说明或讨论中的重要观点

### 聚合（大黄色便签）
- 聚合描述：聚合对象或实体的详细描述
- 组成部分：聚合中包含的对象或实体
- 相关事件和命令：与聚合相关的事件和命令
- 备注：任何需要补充的说明或讨论中的重要观点

## 资源

### 颜色代码

标准 HTML 颜色代码（可能比实际便签颜色更鲜艳）：

- 橙色（用于事件）：#FFA500
- 蓝色（用于命令）：#1E90FF
- 黄色（用于参与者和聚合）：#FFFF00
- 粉色（用于外部系统）：#FFC0CB
- 紫色（用于策略）：#800080
- 绿色（用于读模型）：#00FF00

柔和的颜色代码（更接近实际便签颜色，更易于数字环境中阅读）：

- 橙色（淡）：#FFE4B5
- 蓝色（淡）：#87CEFA
- 黄色（淡）：#FFFACD
- 粉色（淡）：#FFB6C1
- 紫色（淡）：#DDA0DD
- 绿色（淡）：#90EE90

### 事件风暴便签模板

#### 事件
<table>
  <tr>
    <th style="background-color: #FFE4B5; color: #8B4513;">事件（如，用户已注册）</th>
  </tr>
  <tr>
    <td style="background-color: #FFE4B5; color: #8B4513;">时间戳</td>
    <td style="background-color: #FFE4B5; color: #8B4513;">事件发生的具体时间或顺序</td>
  </tr>
  <tr>
    <td style="background-color: #FFE4B5; color: #8B4513;">描述</td>
    <td style="background-color: #FFE4B5; color: #8B4513;">对事件的详细描述，提供更多上下文信息</td>
  </tr>
  <tr>
    <td style="background-color: #FFE4B5; color: #8B4513;">触发条件</td>
    <td style="background-color: #FFE4B5; color: #8B4513;">事件发生的前提条件或原因</td>
  </tr>
  <tr>
    <td style="background-color: #FFE4B5; color: #8B4513;">结果</td>
    <td style="background-color: #FFE4B5; color: #8B4513;">事件发生后的直接结果或影响</td>
  </tr>
  <tr>
    <td style="background-color: #FFE4B5; color: #8B4513;">相关参与者</td>
    <td style="background-color: #FFE4B5; color: #8B4513;">与事件相关的人员或系统</td>
  </tr>
  <tr>
    <td style="background-color: #FFE4B5; color: #8B4513;">备注</td>
    <td style="background-color: #FFE4B5; color: #8B4513;">任何需要补充的说明或讨论中的重要观点</td>
  </tr>
</table>

#### 命令
<table>
  <tr>
    <th style="background-color: #87CEFA; color: #00008B;">命令（如，注册用户）</th>
  </tr>
  <tr>
    <td style="background-color: #87CEFA; color: #00008B;">执行时间</td>
    <td style="background-color: #87CEFA; color: #00008B;">命令执行的具体时间</td>
  </tr>
  <tr>
    <td style="background-color: #87CEFA; color: #00008B;">描述</td>
    <td style="background-color: #87CEFA; color: #00008B;">对命令的详细描述</td>
  </tr>
  <tr>
    <td style="background-color: #87CEFA; color: #00008B;">触发条件</td>
    <td style="background-color: #87CEFA; color: #00008B;">命令被触发的条件或原因</td>
  </tr>
  <tr>
    <td style="background-color: #87CEFA; color: #00008B;">预期结果</td>
    <td style="background-color: #87CEFA; color: #00008B;">命令执行后的预期结果</td>
  </tr>
  <tr>
    <td style="background-color: #87CEFA; color: #00008B;">相关事件</td>
    <td style="background-color: #87CEFA; color: #00008B;">命令触发的事件</td>
  </tr>
  <tr>
    <td style="background-color: #87CEFA; color: #00008B;">备注</td>
    <td style="background-color: #87CEFA; color: #00008B;">任何需要补充的说明或讨论中的重要观点</td>
  </tr>
</table>

#### 参与者
<table>
  <tr>
    <th style="background-color: #FFFACD; color: #8B8B00;">参与者</th>
  </tr>
  <tr>
    <td style="background-color: #FFFACD; color: #8B8B00;">角色描述</td>
    <td style="background-color: #FFFACD; color: #8B8B00;">参与者在业务流程中的角色和职责</td>
  </tr>
  <tr>
    <td style="background-color: #FFFACD; color: #8B8B00;">权限</td>
    <td style="background-color: #FFFACD; color: #8B8B00;">参与者的权限范围</td>
  </tr>
  <tr>
    <td style="background-color: #FFFACD; color: #8B8B00;">相关命令和事件</td>
    <td style="background-color: #FFFACD; color: #8B8B00;">参与者执行的命令和触发的事件</td>
  </tr>
  <tr>
    <td style="background-color: #FFFACD; color: #8B8B00;">备注</td>
    <td style="background-color: #FFFACD; color: #8B8B00;">任何需要补充的说明或讨论中的重要观点</td>
  </tr>
</table>

#### 外部系统
<table>
  <tr>
    <th style="background-color: #FFB6C1; color: #8B0000;">外部系统</th>
  </tr>
  <tr>
    <td style="background-color: #FFB6C1; color: #8B0000;">系统描述</td>
    <td style="background-color: #FFB6C1; color: #8B0000;">外部系统的功能和作用</td>
  </tr>
  <tr>
    <td style="background-color: #FFB6C1; color: #8B0000;">接口</td>
    <td style="background-color: #FFB6C1; color: #8B0000;">与外部系统交互的接口或方法</td>
  </tr>
  <tr>
    <td style="background-color: #FFB6C1; color: #8B0000;">相关事件和命令</td>
    <td style="background-color: #FFB6C1; color: #8B0000;">与外部系统相关的事件和命令</td>
  </tr>
  <tr>
    <td style="background-color: #FFB6C1; color: #8B0000;">备注</td>
    <td style="background-color: #FFB6C1; color: #8B0000;">任何需要补充的说明或讨论中的重要观点</td>
  </tr>
</table>

#### 策略
<table>
  <tr>
    <th style="background-color: #DDA0DD; color: #4B0082;">策略</th>
  </tr>
  <tr>
    <td style="background-color: #DDA0DD; color: #4B0082;">策略描述</td>
    <td style="background-color: #DDA0DD; color: #4B0082;">策略的详细描述和目的</td>
  </tr>
  <tr>
    <td style="background-color: #DDA0DD; color: #4B0082;">触发条件</td>
    <td style="background-color: #DDA0DD; color: #4B0082;">策略被触发的条件或原因</td>
  </tr>
  <tr>
    <td style="background-color: #DDA0DD; color: #4B0082;">预期结果</td>
    <td style="background-color: #DDA0DD; color: #4B0082;">策略执行后的预期结果</td>
  </tr>
  <tr>
    <td style="background-color: #DDA0DD; color: #4B0082;">相关事件和命令</td>
    <td style="background-color: #DDA0DD; color: #4B0082;">策略触发的事件和命令</td>
  </tr>
  <tr>
    <td style="background-color: #DDA0DD; color: #4B0082;">备注</td>
    <td style="background-color: #DDA0DD; color: #4B0082;">任何需要补充的说明或讨论中的重要观点</td>
  </tr>
</table>

#### 读模型
<table>
  <tr>
    <th style="background-color: #90EE90; color: #006400;">读模型</th>
  </tr>
  <tr>
    <td style="background-color: #90EE90; color: #006400;">数据描述</td>
    <td style="background-color: #90EE90; color: #006400;">读模型中包含的数据和信息</td>
  </tr>
  <tr>
    <td style="background-color: #90EE90; color: #006400;">来源</td>
    <td style="background-color: #90EE90; color: #006400;">数据的来源和获取方式</td>
  </tr>
  <tr>
    <td style="background-color: #90EE90; color: #006400;">使用场景</td>
    <td style="background-color: #90EE90; color: #006400;">读模型在业务流程中的使用场景</td>
  </tr>
  <tr>
    <td style="background-color: #90EE90; color: #006400;">相关事件和命令</td>
    <td style="background-color: #90EE90; color: #006400;">读模型支持的事件和命令</td>
  </tr>
  <tr>
    <td style="background-color: #90EE90; color: #006400;">备注</td>
    <td style="background-color: #90EE90; color: #006400;">任何需要补充的说明或讨论中的重要观点</td>
  </tr>
</table>

#### 聚合
<table>
  <tr>
    <th style="background-color: #FFFACD; color: #8B8B00;">聚合</th>
  </tr>
  <tr>
    <td style="background-color: #FFFACD; color: #8B8B00;">聚合描述</td>
    <td style="background-color: #FFFACD; color: #8B8B00;">聚合对象或实体的详细描述</td>
  </tr>
  <tr>
    <td style="background-color: #FFFACD; color: #8B8B00;">组成部分</td>
    <td style="background-color: #FFFACD; color: #8B8B00;">聚合中包含的对象或实体</td>
  </tr>
  <tr>
    <td style="background-color: #FFFACD; color: #8B8B00;">相关事件和命令</td>
    <td style="background-color: #FFFACD; color: #8B8B00;">与聚合相关的事件和命令</td>
  </tr>
  <tr>
    <td style="background-color: #FFFACD; color: #8B8B00;">备注</td>
    <td style="background-color: #FFFACD; color: #8B8B00;">任何需要补充的说明或讨论中的重要观点</td>
  </tr>
</table>
