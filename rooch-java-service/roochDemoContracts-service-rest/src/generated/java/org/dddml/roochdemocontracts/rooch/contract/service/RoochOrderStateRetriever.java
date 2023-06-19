// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.rooch.contract.service;

import com.github.wubuku.rooch.bean.GetAnnotatedStatesResponseMoveStructItem;
import com.github.wubuku.rooch.utils.RoochJsonRpcClient;
import org.dddml.roochdemocontracts.domain.order.*;
import org.dddml.roochdemocontracts.domain.*;
import org.dddml.roochdemocontracts.rooch.contract.DomainBeanUtils;
import org.dddml.roochdemocontracts.rooch.bcs.BcsDomainBeanUtils;
import org.dddml.roochdemocontracts.rooch.contract.Order;
import org.dddml.roochdemocontracts.rooch.contract.OrderItem;
import org.dddml.roochdemocontracts.rooch.contract.OrderShipGroup;
import org.dddml.roochdemocontracts.rooch.contract.OrderItemShipGroupAssociation;
import org.dddml.roochdemocontracts.rooch.contract.OrderItemShipGroupAssocSubitem;

import java.util.*;
import java.math.*;
import java.util.function.*;

public class RoochOrderStateRetriever {

    private RoochJsonRpcClient roochJsonRpcClient;

    private Function<String, OrderState.MutableOrderState> orderStateFactory;

    private BiFunction<OrderState, String, OrderItemState.MutableOrderItemState> orderItemStateFactory;

    private OrderItemProductObjectIdsGetter orderItemProductObjectIdsGetter;

    private BiFunction<OrderState, Integer, OrderShipGroupState.MutableOrderShipGroupState> orderShipGroupStateFactory;

    private OrderShipGroupShipGroupSeqIdsGetter orderShipGroupShipGroupSeqIdsGetter;

    private BiFunction<OrderShipGroupState, String, OrderItemShipGroupAssociationState.MutableOrderItemShipGroupAssociationState> orderItemShipGroupAssociationStateFactory;

    private OrderItemShipGroupAssociationProductObjIdsGetter orderItemShipGroupAssociationProductObjIdsGetter;

    private BiFunction<OrderItemShipGroupAssociationState, Day, OrderItemShipGroupAssocSubitemState.MutableOrderItemShipGroupAssocSubitemState> orderItemShipGroupAssocSubitemStateFactory;

    private OrderItemShipGroupAssocSubitemOrderItemShipGroupAssocSubitemDaysGetter orderItemShipGroupAssocSubitemOrderItemShipGroupAssocSubitemDaysGetter;


    public RoochOrderStateRetriever(RoochJsonRpcClient roochJsonRpcClient,
                                    Function<String, OrderState.MutableOrderState> orderStateFactory,
                                    BiFunction<OrderState, String, OrderItemState.MutableOrderItemState> orderItemStateFactory,
                                    OrderItemProductObjectIdsGetter orderItemProductObjectIdsGetter,
                                    BiFunction<OrderState, Integer, OrderShipGroupState.MutableOrderShipGroupState> orderShipGroupStateFactory,
                                    OrderShipGroupShipGroupSeqIdsGetter orderShipGroupShipGroupSeqIdsGetter,
                                    BiFunction<OrderShipGroupState, String, OrderItemShipGroupAssociationState.MutableOrderItemShipGroupAssociationState> orderItemShipGroupAssociationStateFactory,
                                    OrderItemShipGroupAssociationProductObjIdsGetter orderItemShipGroupAssociationProductObjIdsGetter,
                                    BiFunction<OrderItemShipGroupAssociationState, Day, OrderItemShipGroupAssocSubitemState.MutableOrderItemShipGroupAssocSubitemState> orderItemShipGroupAssocSubitemStateFactory,
                                    OrderItemShipGroupAssocSubitemOrderItemShipGroupAssocSubitemDaysGetter orderItemShipGroupAssocSubitemOrderItemShipGroupAssocSubitemDaysGetter
    ) {
        this.roochJsonRpcClient = roochJsonRpcClient;
        this.orderStateFactory = orderStateFactory;
        this.orderItemStateFactory = orderItemStateFactory;
        this.orderItemProductObjectIdsGetter = orderItemProductObjectIdsGetter;
        this.orderShipGroupStateFactory = orderShipGroupStateFactory;
        this.orderShipGroupShipGroupSeqIdsGetter = orderShipGroupShipGroupSeqIdsGetter;
        this.orderItemShipGroupAssociationStateFactory = orderItemShipGroupAssociationStateFactory;
        this.orderItemShipGroupAssociationProductObjIdsGetter = orderItemShipGroupAssociationProductObjIdsGetter;
        this.orderItemShipGroupAssocSubitemStateFactory = orderItemShipGroupAssocSubitemStateFactory;
        this.orderItemShipGroupAssocSubitemOrderItemShipGroupAssocSubitemDaysGetter = orderItemShipGroupAssocSubitemOrderItemShipGroupAssocSubitemDaysGetter;
    }

    public OrderState retrieveOrderState(String objectId) {
        List<GetAnnotatedStatesResponseMoveStructItem<Order.MoveObject>> getObjectListResponse = roochJsonRpcClient.getMoveStructAnnotatedStates(
                "/object/" + objectId,
                Order.MoveObject.class
        );
        if (getObjectListResponse.size() == 0) {
            return null;
        }
        Order.MoveObject order = getObjectListResponse.get(0).getMoveValue().getValue();
        return toOrderState(order);
    }

    private OrderState toOrderState(Order.MoveObject orderObj) {
        Order order = orderObj.getValue().getValue();
        OrderState.MutableOrderState orderState = orderStateFactory.apply(order.getOrderId());
        orderState.setId_(orderObj.getId());
        orderState.setVersion(order.getVersion());
        orderState.setTotalAmount(order.getTotalAmount());
        orderState.setEstimatedShipDate(DomainBeanUtils.toDay(order.getEstimatedShipDate().getValue().getVec().length == 0 ? null : order.getEstimatedShipDate().getValue().getVec()[0]));
        if (order.getItems() != null) {
            String orderItemTableHandle = order.getItems().getValue().getHandle();
            List<OrderItem> items = getOrderItems(orderItemTableHandle, orderItemProductObjectIdsGetter.getOrderItemProductObjectIds(orderState.getOrderId()));
            for (OrderItem i : items) {
                orderState.getItems().add(toOrderItemState(orderState, i));
            }
        }

        if (order.getOrderShipGroups() != null) {
            String orderShipGroupTableHandle = order.getOrderShipGroups().getValue().getHandle();
            List<OrderShipGroup> orderShipGroups = getOrderShipGroups(orderShipGroupTableHandle, orderShipGroupShipGroupSeqIdsGetter.getOrderShipGroupShipGroupSeqIds(orderState.getOrderId()));
            for (OrderShipGroup i : orderShipGroups) {
                orderState.getOrderShipGroups().add(toOrderShipGroupState(orderState, i));
            }
        }

        return orderState;
    }

    private OrderItemState toOrderItemState(OrderState orderState, OrderItem orderItem) {
        OrderItemState.MutableOrderItemState orderItemState = orderItemStateFactory.apply(orderState, orderItem.getProductObjectId());
        orderItemState.setQuantity(orderItem.getQuantity());
        orderItemState.setItemAmount(orderItem.getItemAmount());
        return orderItemState;
    }

    private OrderShipGroupState toOrderShipGroupState(OrderState orderState, OrderShipGroup orderShipGroup) {
        OrderShipGroupState.MutableOrderShipGroupState orderShipGroupState = orderShipGroupStateFactory.apply(orderState, orderShipGroup.getShipGroupSeqId());
        orderShipGroupState.setShipmentMethod(orderShipGroup.getShipmentMethod());
        if (orderShipGroup.getOrderItemShipGroupAssociations() != null) {
            String orderItemShipGroupAssociationTableHandle = orderShipGroup.getOrderItemShipGroupAssociations().getValue().getHandle();
            List<OrderItemShipGroupAssociation> orderItemShipGroupAssociations = getOrderItemShipGroupAssociations(orderItemShipGroupAssociationTableHandle, orderItemShipGroupAssociationProductObjIdsGetter.getOrderItemShipGroupAssociationProductObjIds(orderShipGroupState.getOrderId(), orderShipGroupState.getShipGroupSeqId()));
            for (OrderItemShipGroupAssociation i : orderItemShipGroupAssociations) {
                orderShipGroupState.getOrderItemShipGroupAssociations().add(toOrderItemShipGroupAssociationState(orderShipGroupState, i));
            }
        }

        return orderShipGroupState;
    }

    private OrderItemShipGroupAssociationState toOrderItemShipGroupAssociationState(OrderShipGroupState orderShipGroupState, OrderItemShipGroupAssociation orderItemShipGroupAssociation) {
        OrderItemShipGroupAssociationState.MutableOrderItemShipGroupAssociationState orderItemShipGroupAssociationState = orderItemShipGroupAssociationStateFactory.apply(orderShipGroupState, orderItemShipGroupAssociation.getProductObjId());
        orderItemShipGroupAssociationState.setQuantity(orderItemShipGroupAssociation.getQuantity());
        orderItemShipGroupAssociationState.setCancelQuantity(orderItemShipGroupAssociation.getCancelQuantity());
        if (orderItemShipGroupAssociation.getSubitems() != null) {
            String orderItemShipGroupAssocSubitemTableHandle = orderItemShipGroupAssociation.getSubitems().getValue().getHandle();
            List<OrderItemShipGroupAssocSubitem> subitems = getOrderItemShipGroupAssocSubitems(orderItemShipGroupAssocSubitemTableHandle, orderItemShipGroupAssocSubitemOrderItemShipGroupAssocSubitemDaysGetter.getOrderItemShipGroupAssocSubitemOrderItemShipGroupAssocSubitemDays(orderItemShipGroupAssociationState.getOrderId(), orderItemShipGroupAssociationState.getOrderShipGroupShipGroupSeqId(), orderItemShipGroupAssociationState.getProductObjId()));
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

    private List<OrderItem> getOrderItems(String orderItemTableHandle, List<String> productObjectIds) {
        List<OrderItem> orderItems = new ArrayList<>();

        for (String productObjectId : productObjectIds) {
            String key = productObjectId;
            List<GetAnnotatedStatesResponseMoveStructItem<OrderItem>> getOrderItemTableItemResponse = roochJsonRpcClient
                    .getMoveStructAnnotatedStates("/table/" + orderItemTableHandle + "/" + key, OrderItem.class);
            if (getOrderItemTableItemResponse.size() == 1 && getOrderItemTableItemResponse.get(0) != null) {
                orderItems.add(getOrderItemTableItemResponse.get(0).getMoveValue().getValue());
            }
        }
        return orderItems;
    }

    private List<OrderShipGroup> getOrderShipGroups(String orderShipGroupTableHandle, List<Integer> shipGroupSeqIds) {
        List<OrderShipGroup> orderShipGroups = new ArrayList<>();

        for (Integer shipGroupSeqId : shipGroupSeqIds) {
            String key = com.github.wubuku.rooch.utils.HexUtils.byteArrayToHexWithPrefix(com.github.wubuku.rooch.bcs.BcsUtils.serializeU8(shipGroupSeqId.byteValue()));
            List<GetAnnotatedStatesResponseMoveStructItem<OrderShipGroup>> getOrderShipGroupTableItemResponse = roochJsonRpcClient
                    .getMoveStructAnnotatedStates("/table/" + orderShipGroupTableHandle + "/" + key, OrderShipGroup.class);
            if (getOrderShipGroupTableItemResponse.size() == 1 && getOrderShipGroupTableItemResponse.get(0) != null) {
                orderShipGroups.add(getOrderShipGroupTableItemResponse.get(0).getMoveValue().getValue());
            }
        }
        return orderShipGroups;
    }

    private List<OrderItemShipGroupAssociation> getOrderItemShipGroupAssociations(String orderItemShipGroupAssociationTableHandle, List<String> productObjIds) {
        List<OrderItemShipGroupAssociation> orderItemShipGroupAssociations = new ArrayList<>();

        for (String productObjId : productObjIds) {
            String key = productObjId;
            List<GetAnnotatedStatesResponseMoveStructItem<OrderItemShipGroupAssociation>> getOrderItemShipGroupAssociationTableItemResponse = roochJsonRpcClient
                    .getMoveStructAnnotatedStates("/table/" + orderItemShipGroupAssociationTableHandle + "/" + key, OrderItemShipGroupAssociation.class);
            if (getOrderItemShipGroupAssociationTableItemResponse.size() == 1 && getOrderItemShipGroupAssociationTableItemResponse.get(0) != null) {
                orderItemShipGroupAssociations.add(getOrderItemShipGroupAssociationTableItemResponse.get(0).getMoveValue().getValue());
            }
        }
        return orderItemShipGroupAssociations;
    }

    private List<OrderItemShipGroupAssocSubitem> getOrderItemShipGroupAssocSubitems(String orderItemShipGroupAssocSubitemTableHandle, List<Day> orderItemShipGroupAssocSubitemDays) {
        List<OrderItemShipGroupAssocSubitem> orderItemShipGroupAssocSubitems = new ArrayList<>();

        for (Day orderItemShipGroupAssocSubitemDay : orderItemShipGroupAssocSubitemDays) {
            String key = BcsDomainBeanUtils.toBcsHex(orderItemShipGroupAssocSubitemDay);
            List<GetAnnotatedStatesResponseMoveStructItem<OrderItemShipGroupAssocSubitem>> getOrderItemShipGroupAssocSubitemTableItemResponse = roochJsonRpcClient
                    .getMoveStructAnnotatedStates("/table/" + orderItemShipGroupAssocSubitemTableHandle + "/" + key, OrderItemShipGroupAssocSubitem.class);
            if (getOrderItemShipGroupAssocSubitemTableItemResponse.size() == 1 && getOrderItemShipGroupAssocSubitemTableItemResponse.get(0) != null) {
                orderItemShipGroupAssocSubitems.add(getOrderItemShipGroupAssocSubitemTableItemResponse.get(0).getMoveValue().getValue());
            }
        }
        return orderItemShipGroupAssocSubitems;
    }

    public interface OrderItemProductObjectIdsGetter {
        List<String> getOrderItemProductObjectIds(String orderId);
    }

    public interface OrderShipGroupShipGroupSeqIdsGetter {
        List<Integer> getOrderShipGroupShipGroupSeqIds(String orderId);
    }

    public interface OrderItemShipGroupAssociationProductObjIdsGetter {
        List<String> getOrderItemShipGroupAssociationProductObjIds(String orderId, Integer orderShipGroupShipGroupSeqId);
    }

    public interface OrderItemShipGroupAssocSubitemOrderItemShipGroupAssocSubitemDaysGetter {
        List<Day> getOrderItemShipGroupAssocSubitemOrderItemShipGroupAssocSubitemDays(String orderId, Integer orderShipGroupShipGroupSeqId, String orderItemShipGroupAssociationProductObjId);
    }

}

