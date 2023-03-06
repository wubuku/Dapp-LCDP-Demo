# Sui Client CLI Cheatsheet


## DaySummary aggregate

### Create method

```shell
sui client call --package _PACKAGE_ID_ --module day_summary_aggregate --function create \
--args 2023 \"ChineseLunar\" 2 false 25 \"ShangHai\" \"Hello\" \"TestMetaData\" \"0x4264351c3b2e411111049c9493dc9e707fea010d\" \
--gas-budget 100000
```

```shell
sui client call --package _PACKAGE_ID_ --module day_summary_aggregate --function create \
--args 2023 \"ChineseLunar\" 2 false 25 \"ShangHai\" \"Hello\" \"TestMetaData\" '["TestArrayItem_1"]' '["TestOptionalValue"]' \"0x98b3eec656decfe0d7e365b66155fb8be774454a\" \
--gas-budget 30000
```

## DomainName aggregate

### Register method

```shell
sui client call --package _PACKAGE_ID_ --module domain_name_aggregate --function register \
--args \"Hello\" \"org\" \"20000\" \"0x0c67a428007447080926923e5e82a321a6dd6a8a\" \
--gas-budget 30000
```

### Renew method

```shell

```

## Order aggregate

### Create method

```shell
sui client call --package _PACKAGE_ID_ --module order_aggregate --function create \
--args \"_PRODUCT_ID_\" \"1\" \
--gas-budget 100000
```

## OrderV2 aggregate

### Create method

```shell

```

## Product aggregate

### Create method

```shell
sui client call --package _PACKAGE_ID_ --module product_aggregate --function create \
--args \"product_1\" \"100\" \"0x03fcf64b74c703a4bd03feb8a591b1c9062ae3fa\" \
--gas-budget 30000
```

