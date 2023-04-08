// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.suidemocontracts.sui.contract.service;

import com.github.wubuku.sui.bean.*;
import com.github.wubuku.sui.utils.*;
import org.dddml.suidemocontracts.domain.orderv2.*;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.sui.contract.DomainBeanUtils;
import org.dddml.suidemocontracts.sui.contract.OrderV2;
import org.dddml.suidemocontracts.sui.contract.OrderV2Item;
import org.dddml.suidemocontracts.sui.contract.OrderV2ItemDynamicField;
import org.dddml.suidemocontracts.sui.contract.OrderShipGroup;
import org.dddml.suidemocontracts.sui.contract.OrderShipGroupDynamicField;
import org.dddml.suidemocontracts.sui.contract.OrderItemShipGroupAssociation;
import org.dddml.suidemocontracts.sui.contract.OrderItemShipGroupAssociationDynamicField;
import org.dddml.suidemocontracts.sui.contract.OrderItemShipGroupAssocSubitem;
import org.dddml.suidemocontracts.sui.contract.OrderItemShipGroupAssocSubitemDynamicField;

import java.util.*;
import java.math.*;
import java.util.function.*;

public class SuiOrderV2StateRetriever {

    private SuiJsonRpcClient suiJsonRpcClient;

    private Function<String, OrderV2State.MutableOrderV2State> orderV2StateFactory;
    private BiFunction<OrderV2State, String, OrderV2ItemState.MutableOrderV2ItemState> orderV2ItemStateFactory;
    private BiFunction<OrderV2State, Integer, OrderShipGroupState.MutableOrderShipGroupState> orderShipGroupStateFactory;
    private BiFunction<OrderShipGroupState, String, OrderItemShipGroupAssociationState.MutableOrderItemShipGroupAssociationState> orderItemShipGroupAssociationStateFactory;
    private BiFunction<OrderItemShipGroupAssociationState, Day, OrderItemShipGroupAssocSubitemState.MutableOrderItemShipGroupAssocSubitemState> orderItemShipGroupAssocSubitemStateFactory;

    public SuiOrderV2StateRetriever(SuiJsonRpcClient suiJsonRpcClient,
                                  Function<String, OrderV2State.MutableOrderV2State> orderV2StateFactory,
                                  BiFunction<OrderV2State, String, OrderV2ItemState.MutableOrderV2ItemState> orderV2ItemStateFactory,
                                  BiFunction<OrderV2State, Integer, OrderShipGroupState.MutableOrderShipGroupState> orderShipGroupStateFactory,
                                  BiFunction<OrderShipGroupState, String, OrderItemShipGroupAssociationState.MutableOrderItemShipGroupAssociationState> orderItemShipGroupAssociationStateFactory,
                                  BiFunction<OrderItemShipGroupAssociationState, Day, OrderItemShipGroupAssocSubitemState.MutableOrderItemShipGroupAssocSubitemState> orderItemShipGroupAssocSubitemStateFactory
    ) {
        this.suiJsonRpcClient = suiJsonRpcClient;
        this.orderV2StateFactory = orderV2StateFactory;
        this.orderV2ItemStateFactory = orderV2ItemStateFactory;
        this.orderShipGroupStateFactory = orderShipGroupStateFactory;
        this.orderItemShipGroupAssociationStateFactory = orderItemShipGroupAssociationStateFactory;
        this.orderItemShipGroupAssocSubitemStateFactory = orderItemShipGroupAssocSubitemStateFactory;
    }

    public OrderV2State retrieveOrderV2State(String objectId) {
        SuiMoveObjectResponse<OrderV2> getObjectDataResponse = suiJsonRpcClient.getMoveObject(
                objectId, new SuiObjectDataOptions(true, true, true, true, true, true, true), OrderV2.class
        );

        OrderV2 orderV2 = getObjectDataResponse.getData().getContent().getFields();
        return toOrderV2State(orderV2);
    }

    private OrderV2State toOrderV2State(OrderV2 orderV2) {
        OrderV2State.MutableOrderV2State orderV2State = orderV2StateFactory.apply(orderV2.getOrderId());
        orderV2State.setId_(orderV2.getId().getId());
        orderV2State.setVersion(orderV2.getVersion());
        orderV2State.setTotalAmount(orderV2.getTotalAmount());
        orderV2State.setEstimatedShipDate(DomainBeanUtils.toDay(orderV2.getEstimatedShipDate()));
        if (orderV2.getItems() != null) {
            String orderV2ItemTableId = orderV2.getItems().getFields().getId().getId();
            List<OrderV2Item> items = getOrderV2Items(orderV2ItemTableId);
            for (OrderV2Item i : items) {
                orderV2State.getItems().add(toOrderV2ItemState(orderV2State, i));
            }
        }

        if (orderV2.getOrderShipGroups() != null) {
            String orderShipGroupTableId = orderV2.getOrderShipGroups().getFields().getId().getId();
            List<OrderShipGroup> orderShipGroups = getOrderShipGroups(orderShipGroupTableId);
            for (OrderShipGroup i : orderShipGroups) {
                orderV2State.getOrderShipGroups().add(toOrderShipGroupState(orderV2State, i));
            }
        }

        return orderV2State;
    }

    private OrderV2ItemState toOrderV2ItemState(OrderV2State orderV2State, OrderV2Item orderV2Item) {
        OrderV2ItemState.MutableOrderV2ItemState orderV2ItemState = orderV2ItemStateFactory.apply(orderV2State, orderV2Item.getProductId());
        orderV2ItemState.setQuantity(orderV2Item.getQuantity());
        orderV2ItemState.setItemAmount(orderV2Item.getItemAmount());
        return orderV2ItemState;
    }

    private OrderShipGroupState toOrderShipGroupState(OrderV2State orderV2State, OrderShipGroup orderShipGroup) {
        OrderShipGroupState.MutableOrderShipGroupState orderShipGroupState = orderShipGroupStateFactory.apply(orderV2State, orderShipGroup.getShipGroupSeqId());
        orderShipGroupState.setShipmentMethod(orderShipGroup.getShipmentMethod());
        if (orderShipGroup.getOrderItemShipGroupAssociations() != null) {
            String orderItemShipGroupAssociationTableId = orderShipGroup.getOrderItemShipGroupAssociations().getFields().getId().getId();
            List<OrderItemShipGroupAssociation> orderItemShipGroupAssociations = getOrderItemShipGroupAssociations(orderItemShipGroupAssociationTableId);
            for (OrderItemShipGroupAssociation i : orderItemShipGroupAssociations) {
                orderShipGroupState.getOrderItemShipGroupAssociations().add(toOrderItemShipGroupAssociationState(orderShipGroupState, i));
            }
        }

        return orderShipGroupState;
    }

    private OrderItemShipGroupAssociationState toOrderItemShipGroupAssociationState(OrderShipGroupState orderShipGroupState, OrderItemShipGroupAssociation orderItemShipGroupAssociation) {
        OrderItemShipGroupAssociationState.MutableOrderItemShipGroupAssociationState orderItemShipGroupAssociationState = orderItemShipGroupAssociationStateFactory.apply(orderShipGroupState, orderItemShipGroupAssociation.getProductId());
        orderItemShipGroupAssociationState.setQuantity(orderItemShipGroupAssociation.getQuantity());
        orderItemShipGroupAssociationState.setCancelQuantity(orderItemShipGroupAssociation.getCancelQuantity());
        if (orderItemShipGroupAssociation.getSubitems() != null) {
            String orderItemShipGroupAssocSubitemTableId = orderItemShipGroupAssociation.getSubitems().getFields().getId().getId();
            List<OrderItemShipGroupAssocSubitem> subitems = getOrderItemShipGroupAssocSubitems(orderItemShipGroupAssocSubitemTableId);
            for (OrderItemShipGroupAssocSubitem i : subitems) {
                orderItemShipGroupAssociationState.getSubitems().add(toOrderItemShipGroupAssocSubitemState(orderItemShipGroupAssociationState, i));
            }
        }

        return orderItemShipGroupAssociationState;
    }

    private OrderItemShipGroupAssocSubitemState toOrderItemShipGroupAssocSubitemState(OrderItemShipGroupAssociationState orderItemShipGroupAssociationState, OrderItemShipGroupAssocSubitem orderItemShipGroupAssocSubitem) {
        OrderItemShipGroupAssocSubitemState.MutableOrderItemShipGroupAssocSubitemState orderItemShipGroupAssocSubitemState = orderItemShipGroupAssocSubitemStateFactory.apply(orderItemShipGroupAssociationState, DomainBeanUtils.toDay(orderItemShipGroupAssocSubitem.getOrderItemShipGroupAssocSubitemDay()));
        orderItemShipGroupAssocSubitemState.setDescription(orderItemShipGroupAssocSubitem.getDescription());
        return orderItemShipGroupAssocSubitemState;
    }

    private List<OrderV2Item> getOrderV2Items(String orderV2ItemTableId) {
        List<OrderV2Item> orderV2Items = new ArrayList<>();
        String cursor = null;
        while (true) {
            DynamicFieldPage orderV2ItemFieldPage = suiJsonRpcClient.getDynamicFields(orderV2ItemTableId, cursor, null);
            for (DynamicFieldInfo orderV2ItemFieldInfo : orderV2ItemFieldPage.getData()) {
                String fieldObjectId = orderV2ItemFieldInfo.getObjectId();
                SuiMoveObjectResponse<OrderV2ItemDynamicField> getOrderV2ItemFieldResponse
                        = suiJsonRpcClient.getMoveObject(fieldObjectId, new SuiObjectDataOptions(true, true, true, true, true, true, true), OrderV2ItemDynamicField.class);
                OrderV2Item orderV2Item = getOrderV2ItemFieldResponse
                        .getData().getContent().getFields().getValue().getFields();
                orderV2Items.add(orderV2Item);
            }
            cursor = orderV2ItemFieldPage.getNextCursor();
            if (!Page.hasNextPage(orderV2ItemFieldPage)) {
                break;
            }
        }
        return orderV2Items;
    }

    private List<OrderShipGroup> getOrderShipGroups(String orderShipGroupTableId) {
        List<OrderShipGroup> orderShipGroups = new ArrayList<>();
        String cursor = null;
        while (true) {
            DynamicFieldPage orderShipGroupFieldPage = suiJsonRpcClient.getDynamicFields(orderShipGroupTableId, cursor, null);
            for (DynamicFieldInfo orderShipGroupFieldInfo : orderShipGroupFieldPage.getData()) {
                String fieldObjectId = orderShipGroupFieldInfo.getObjectId();
                SuiMoveObjectResponse<OrderShipGroupDynamicField> getOrderShipGroupFieldResponse
                        = suiJsonRpcClient.getMoveObject(fieldObjectId, new SuiObjectDataOptions(true, true, true, true, true, true, true), OrderShipGroupDynamicField.class);
                OrderShipGroup orderShipGroup = getOrderShipGroupFieldResponse
                        .getData().getContent().getFields().getValue().getFields();
                orderShipGroups.add(orderShipGroup);
            }
            cursor = orderShipGroupFieldPage.getNextCursor();
            if (!Page.hasNextPage(orderShipGroupFieldPage)) {
                break;
            }
        }
        return orderShipGroups;
    }

    private List<OrderItemShipGroupAssociation> getOrderItemShipGroupAssociations(String orderItemShipGroupAssociationTableId) {
        List<OrderItemShipGroupAssociation> orderItemShipGroupAssociations = new ArrayList<>();
        String cursor = null;
        while (true) {
            DynamicFieldPage orderItemShipGroupAssociationFieldPage = suiJsonRpcClient.getDynamicFields(orderItemShipGroupAssociationTableId, cursor, null);
            for (DynamicFieldInfo orderItemShipGroupAssociationFieldInfo : orderItemShipGroupAssociationFieldPage.getData()) {
                String fieldObjectId = orderItemShipGroupAssociationFieldInfo.getObjectId();
                SuiMoveObjectResponse<OrderItemShipGroupAssociationDynamicField> getOrderItemShipGroupAssociationFieldResponse
                        = suiJsonRpcClient.getMoveObject(fieldObjectId, new SuiObjectDataOptions(true, true, true, true, true, true, true), OrderItemShipGroupAssociationDynamicField.class);
                OrderItemShipGroupAssociation orderItemShipGroupAssociation = getOrderItemShipGroupAssociationFieldResponse
                        .getData().getContent().getFields().getValue().getFields();
                orderItemShipGroupAssociations.add(orderItemShipGroupAssociation);
            }
            cursor = orderItemShipGroupAssociationFieldPage.getNextCursor();
            if (!Page.hasNextPage(orderItemShipGroupAssociationFieldPage)) {
                break;
            }
        }
        return orderItemShipGroupAssociations;
    }

    private List<OrderItemShipGroupAssocSubitem> getOrderItemShipGroupAssocSubitems(String orderItemShipGroupAssocSubitemTableId) {
        List<OrderItemShipGroupAssocSubitem> orderItemShipGroupAssocSubitems = new ArrayList<>();
        String cursor = null;
        while (true) {
            DynamicFieldPage orderItemShipGroupAssocSubitemFieldPage = suiJsonRpcClient.getDynamicFields(orderItemShipGroupAssocSubitemTableId, cursor, null);
            for (DynamicFieldInfo orderItemShipGroupAssocSubitemFieldInfo : orderItemShipGroupAssocSubitemFieldPage.getData()) {
                String fieldObjectId = orderItemShipGroupAssocSubitemFieldInfo.getObjectId();
                SuiMoveObjectResponse<OrderItemShipGroupAssocSubitemDynamicField> getOrderItemShipGroupAssocSubitemFieldResponse
                        = suiJsonRpcClient.getMoveObject(fieldObjectId, new SuiObjectDataOptions(true, true, true, true, true, true, true), OrderItemShipGroupAssocSubitemDynamicField.class);
                OrderItemShipGroupAssocSubitem orderItemShipGroupAssocSubitem = getOrderItemShipGroupAssocSubitemFieldResponse
                        .getData().getContent().getFields().getValue().getFields();
                orderItemShipGroupAssocSubitems.add(orderItemShipGroupAssocSubitem);
            }
            cursor = orderItemShipGroupAssocSubitemFieldPage.getNextCursor();
            if (!Page.hasNextPage(orderItemShipGroupAssocSubitemFieldPage)) {
                break;
            }
        }
        return orderItemShipGroupAssocSubitems;
    }

    
}

