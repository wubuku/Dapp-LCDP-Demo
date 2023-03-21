alter TABLE `order_item` add
  CONSTRAINT `FK_OrderItem_Order_StateId` 
  FOREIGN KEY 
    (`order_item_id_order_id`) 
  REFERENCES `order_T` 
    (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION;

alter TABLE `order_v2_item` add
  CONSTRAINT `FK_OrderV2Item_OrderV2_StateId` 
  FOREIGN KEY 
    (`order_v2_item_id_order_v2_order_id`) 
  REFERENCES `order_v2` 
    (`order_id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION;

