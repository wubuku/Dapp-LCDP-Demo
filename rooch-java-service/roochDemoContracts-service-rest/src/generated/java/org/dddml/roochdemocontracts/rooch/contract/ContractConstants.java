// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.rooch.contract;

public class ContractConstants {
    public static final String DEFAULT_ROOCH_PACKAGE_NAME = "DEFAULT_ROOCH_PACKAGE";

    public static final String PRODUCT_MODULE_PRODUCT_ID_GENERATOR = "product::ProductIdGenerator";

    public static final String ARTICLE_MODULE_ARTICLE_CREATED = "article::ArticleCreated";

    public static final String ARTICLE_MODULE_REFERENCE_ADDED = "article::ReferenceAdded";

    public static final String ARTICLE_MODULE_REFERENCE_UPDATED = "article::ReferenceUpdated";

    public static final String ARTICLE_MODULE_REFERENCE_REMOVED = "article::ReferenceRemoved";

    public static final String TAG_MODULE_TAG_CREATED = "tag::TagCreated";

    public static final String PRODUCT_MODULE_PRODUCT_CRUD_EVENT = "product::ProductCrudEvent";

    public static final String ORDER_MODULE_ORDER_CREATED = "order::OrderCreated";

    public static final String ORDER_MODULE_ORDER_ITEM_REMOVED = "order::OrderItemRemoved";

    public static final String ORDER_MODULE_ORDER_ITEM_QUANTITY_UPDATED = "order::OrderItemQuantityUpdated";

    public static final String ORDER_MODULE_ORDER_ESTIMATED_SHIP_DATE_UPDATED = "order::OrderEstimatedShipDateUpdated";

    public static final String ORDER_MODULE_ORDER_SHIP_GROUP_ADDED = "order::OrderShipGroupAdded";

    public static final String ORDER_MODULE_ORDER_ITEM_SHIP_GROUP_ASSOC_SUBITEM_ADDED = "order::OrderItemShipGroupAssocSubitemAdded";

    public static final String ORDER_MODULE_ORDER_SHIP_GROUP_QUANTITY_CANCELED = "order::OrderShipGroupQuantityCanceled";

    public static final String ORDER_MODULE_ORDER_SHIP_GROUP_ITEM_REMOVED = "order::OrderShipGroupItemRemoved";

    public static final String DAY_SUMMARY_MODULE_DAY_SUMMARY_CREATED = "day_summary::DaySummaryCreated";

    public static final String DAY_SUMMARY_MODULE_DAY_SUMMARY_DELETED = "day_summary::DaySummaryDeleted";

    public static final String REFERENCE_TABLE_ITEM_ADDED = "article::ReferenceTableItemAdded";

    public static final String ORDER_ITEM_TABLE_ITEM_ADDED = "order::OrderItemTableItemAdded";

    public static final String ORDER_SHIP_GROUP_TABLE_ITEM_ADDED = "order::OrderShipGroupTableItemAdded";

    public static final String ORDER_ITEM_SHIP_GROUP_ASSOCIATION_TABLE_ITEM_ADDED = "order_ship_group::OrderItemShipGroupAssociationTableItemAdded";

    public static final String ORDER_ITEM_SHIP_GROUP_ASSOC_SUBITEM_TABLE_ITEM_ADDED = "order_item_ship_group_association::OrderItemShipGroupAssocSubitemTableItemAdded";

    public static String[] getMoveObjectIdGeneratorObjectTypes(String packageId) {
        return new String[]{
                packageId + "::" + PRODUCT_MODULE_PRODUCT_ID_GENERATOR,
        };
    }
}
