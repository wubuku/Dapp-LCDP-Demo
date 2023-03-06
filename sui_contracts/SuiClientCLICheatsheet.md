# Sui Client CLI Cheatsheet

## DomainName aggregate

### Register method

```shell
sui client call --package _PACKAGE_ID_ --module domain_name_aggregate --function register \
--args 'domain_name_id_top_level_domain' 'domain_name_id_second_level_domain' 'registration_period' '_DOMAIN_NAME_DOMAIN_NAME_ID_TABLE_OBJECT_ID_' \
--gas-budget 100000
```

### Renew method

```shell
sui client call --package _PACKAGE_ID_ --module domain_name_aggregate --function renew \
--args domain_name 'renew_period' \
--gas-budget 100000
```

## Order aggregate

### Create method

```shell
sui client call --package _PACKAGE_ID_ --module order_aggregate --function create \
--args '_PRODUCT_OBJECT_ID_' 'quantity' \
--gas-budget 100000
```

### RemoveItem method

```shell
sui client call --package _PACKAGE_ID_ --module order_aggregate --function remove_item \
--args order 'product_id' \
--gas-budget 100000
```

### UpdateItemQuantity method

```shell
sui client call --package _PACKAGE_ID_ --module order_aggregate --function update_item_quantity \
--args order 'product_id' 'quantity' \
--gas-budget 100000
```

## Product aggregate

### Create method

```shell
sui client call --package _PACKAGE_ID_ --module product_aggregate --function create \
--args 'name' 'unit_price' '_PRODUCT_PRODUCT_ID_GENERATOR_OBJECT_ID_' \
--gas-budget 100000
```

## OrderV2 aggregate

### Create method

```shell
sui client call --package _PACKAGE_ID_ --module order_v2_aggregate --function create \
--args 'order_id' '_PRODUCT_OBJECT_ID_' 'quantity' '_ORDER_V2_ORDER_ID_TABLE_OBJECT_ID_' \
--gas-budget 100000
```

### RemoveItem method

```shell
sui client call --package _PACKAGE_ID_ --module order_v2_aggregate --function remove_item \
--args order_v2 'product_id' \
--gas-budget 100000
```

### UpdateItemQuantity method

```shell
sui client call --package _PACKAGE_ID_ --module order_v2_aggregate --function update_item_quantity \
--args order_v2 'product_id' 'quantity' \
--gas-budget 100000
```

### UpdateEstimatedShipDate method

```shell
sui client call --package _PACKAGE_ID_ --module order_v2_aggregate --function update_estimated_ship_date \
--args order_v2 estimated_ship_date_month_year_number 'estimated_ship_date_month_year_calendar' estimated_ship_date_month_number estimated_ship_date_month_is_leap estimated_ship_date_number 'estimated_ship_date_time_zone' \
--gas-budget 100000
```

## DaySummary aggregate

### Create method

```shell
sui client call --package _PACKAGE_ID_ --module day_summary_aggregate --function create \
--args day_month_year_number 'day_month_year_calendar' day_month_number day_month_is_leap day_number 'day_time_zone' 'description' 'vector_u8_meta_data' array_data optional_data '_DAY_SUMMARY_DAY_SUMMARY_ID_TABLE_OBJECT_ID_' \
--gas-budget 100000
```


---

## Tips

You can escape single quotes in string arguments by using the following method when enclosing them within single quotes in a shell:

1. Close the current single quote.
2. Use a backslash `\` to escape the single quote.
3. Open a new set of single quotes to continue the string.

Here is an example of how to escape a single quote within a string enclosed by single quotes in a shell:

```shell
echo 'It'\''s a beautiful day'
```

