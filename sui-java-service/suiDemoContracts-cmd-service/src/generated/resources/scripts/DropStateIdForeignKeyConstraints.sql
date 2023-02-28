set @var=if((SELECT true FROM information_schema.TABLE_CONSTRAINTS WHERE
            CONSTRAINT_SCHEMA = DATABASE() AND
            TABLE_NAME        = 'order_item' AND
            CONSTRAINT_NAME   = 'FK_OrderItem_Order_StateId' AND
            CONSTRAINT_TYPE   = 'FOREIGN KEY') = true,'ALTER TABLE order_item
            drop foreign key FK_OrderItem_Order_StateId','select 1');

prepare stmt from @var;
execute stmt;
deallocate prepare stmt;

set @var=if((SELECT true FROM information_schema.TABLE_CONSTRAINTS WHERE
            CONSTRAINT_SCHEMA = DATABASE() AND
            TABLE_NAME        = 'order_v2_item' AND
            CONSTRAINT_NAME   = 'FK_OrderV2Item_OrderV2_StateId' AND
            CONSTRAINT_TYPE   = 'FOREIGN KEY') = true,'ALTER TABLE order_v2_item
            drop foreign key FK_OrderV2Item_OrderV2_StateId','select 1');

prepare stmt from @var;
execute stmt;
deallocate prepare stmt;

