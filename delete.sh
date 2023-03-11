#!/bin/sh

rm sui_contracts/sources/day.move
rm sui_contracts/sources/day_summary.move
rm sui_contracts/sources/day_summary_aggregate.move
rm sui_contracts/sources/domain_name.move
rm sui_contracts/sources/domain_name_aggregate.move
rm sui_contracts/sources/month.move
rm sui_contracts/sources/order.move
rm sui_contracts/sources/order_aggregate.move
rm sui_contracts/sources/order_item.move
rm sui_contracts/sources/order_v2.move
rm sui_contracts/sources/order_v2_aggregate.move
rm sui_contracts/sources/order_v2_item.move
rm sui_contracts/sources/product.move
rm sui_contracts/sources/product_aggregate.move
rm sui_contracts/sources/year.move

rm -rf sui-java-service/suiDemoContracts-cmd-rest/src/generated/java/org/dddml/suidemocontracts/restful/resource
rm -rf sui-java-service/suiDemoContracts-common/src/generated/java/org/dddml/suidemocontracts/domain/daysummary
rm -rf sui-java-service/suiDemoContracts-common/src/generated/java/org/dddml/suidemocontracts/domain/order
rm -rf sui-java-service/suiDemoContracts-common/src/generated/java/org/dddml/suidemocontracts/domain/orderv2
rm -rf sui-java-service/suiDemoContracts-common/src/generated/java/org/dddml/suidemocontracts/domain/product
rm -rf sui-java-service/suiDemoContracts-common/src/generated/java/org/dddml/suidemocontracts/domain/domainname
rm sui-java-service/suiDemoContracts-common/src/generated/java/org/dddml/suidemocontracts/domain/*.java
rm sui-java-service/suiDemoContracts-cmd-service/src/generated/resources/hibernate/*.hbm.xml
rm sui-java-service/suiDemoContracts-cmd-rest/src/main/resources/sui/contract/*Cheatsheet*.md
rm -rf sui-java-service/suiDemoContracts-cmd-service/src/generated/java/org/dddml/suidemocontracts/domain/daysummary/hibernate
rm -rf sui-java-service/suiDemoContracts-cmd-service/src/generated/java/org/dddml/suidemocontracts/domain/order/hibernate
rm -rf sui-java-service/suiDemoContracts-cmd-service/src/generated/java/org/dddml/suidemocontracts/domain/orderv2/hibernate
rm -rf sui-java-service/suiDemoContracts-cmd-service/src/generated/java/org/dddml/suidemocontracts/domain/product/hibernate
rm -rf sui-java-service/suiDemoContracts-cmd-service/src/generated/java/org/dddml/suidemocontracts/domain/domainname/hibernate


