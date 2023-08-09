// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.aptosdemocontracts.aptos.contract.service;


import com.github.wubuku.aptos.bean.AccountResource;
import com.github.wubuku.aptos.utils.*;
import org.dddml.aptosdemocontracts.aptos.contract.AptosAccount;
import org.dddml.aptosdemocontracts.aptos.contract.ContractConstants;
import org.dddml.aptosdemocontracts.aptos.contract.DomainBeanUtils;
import org.dddml.aptosdemocontracts.aptos.contract.repository.AptosAccountRepository;
import org.dddml.aptosdemocontracts.domain.order.*;
import org.dddml.aptosdemocontracts.domain.*;
import org.dddml.aptosdemocontracts.aptos.contract.Order;
import org.dddml.aptosdemocontracts.aptos.contract.OrderItem;
import org.dddml.aptosdemocontracts.aptos.contract.OrderShipGroup;
import org.dddml.aptosdemocontracts.aptos.contract.OrderItemShipGroupAssociation;
import org.dddml.aptosdemocontracts.aptos.contract.OrderItemShipGroupAssocSubitem;

import java.io.IOException;
import java.math.*;
import java.util.*;
import java.util.function.*;


public class AptosOrderStateRetriever {

    private NodeApiClient aptosNodeApiClient;

    private String aptosContractAddress;

    private AptosAccountRepository aptosAccountRepository;

    private Function<String, OrderState.MutableOrderState> orderStateFactory;

    private BiFunction<OrderState, String, OrderItemState.MutableOrderItemState> orderItemStateFactory;

    private OrderItemProductIdsGetter orderItemProductIdsGetter;

    private BiFunction<OrderState, Integer, OrderShipGroupState.MutableOrderShipGroupState> orderShipGroupStateFactory;

    private OrderShipGroupShipGroupSeqIdsGetter orderShipGroupShipGroupSeqIdsGetter;

    private BiFunction<OrderShipGroupState, String, OrderItemShipGroupAssociationState.MutableOrderItemShipGroupAssociationState> orderItemShipGroupAssociationStateFactory;

    private OrderItemShipGroupAssociationProductIdsGetter orderItemShipGroupAssociationProductIdsGetter;

    private BiFunction<OrderItemShipGroupAssociationState, Day, OrderItemShipGroupAssocSubitemState.MutableOrderItemShipGroupAssocSubitemState> orderItemShipGroupAssocSubitemStateFactory;

    private OrderItemShipGroupAssocSubitemOrderItemShipGroupAssocSubitemDaysGetter orderItemShipGroupAssocSubitemOrderItemShipGroupAssocSubitemDaysGetter;


    public AptosOrderStateRetriever(NodeApiClient aptosNodeApiClient,
                                    String aptosContractAddress,
                                    AptosAccountRepository aptosAccountRepository,
                                    Function<String, OrderState.MutableOrderState> orderStateFactory,
                                    BiFunction<OrderState, String, OrderItemState.MutableOrderItemState> orderItemStateFactory,
                                    OrderItemProductIdsGetter orderItemProductIdsGetter,
                                    BiFunction<OrderState, Integer, OrderShipGroupState.MutableOrderShipGroupState> orderShipGroupStateFactory,
                                    OrderShipGroupShipGroupSeqIdsGetter orderShipGroupShipGroupSeqIdsGetter,
                                    BiFunction<OrderShipGroupState, String, OrderItemShipGroupAssociationState.MutableOrderItemShipGroupAssociationState> orderItemShipGroupAssociationStateFactory,
                                    OrderItemShipGroupAssociationProductIdsGetter orderItemShipGroupAssociationProductIdsGetter,
                                    BiFunction<OrderItemShipGroupAssociationState, Day, OrderItemShipGroupAssocSubitemState.MutableOrderItemShipGroupAssocSubitemState> orderItemShipGroupAssocSubitemStateFactory,
                                    OrderItemShipGroupAssocSubitemOrderItemShipGroupAssocSubitemDaysGetter orderItemShipGroupAssocSubitemOrderItemShipGroupAssocSubitemDaysGetter
    ) {
        this.aptosNodeApiClient = aptosNodeApiClient;
        this.aptosContractAddress = aptosContractAddress;
        this.aptosAccountRepository = aptosAccountRepository;
        this.orderStateFactory = orderStateFactory;
        this.orderItemStateFactory = orderItemStateFactory;
        this.orderItemProductIdsGetter = orderItemProductIdsGetter;
        this.orderShipGroupStateFactory = orderShipGroupStateFactory;
        this.orderShipGroupShipGroupSeqIdsGetter = orderShipGroupShipGroupSeqIdsGetter;
        this.orderItemShipGroupAssociationStateFactory = orderItemShipGroupAssociationStateFactory;
        this.orderItemShipGroupAssociationProductIdsGetter = orderItemShipGroupAssociationProductIdsGetter;
        this.orderItemShipGroupAssocSubitemStateFactory = orderItemShipGroupAssocSubitemStateFactory;
        this.orderItemShipGroupAssocSubitemOrderItemShipGroupAssocSubitemDaysGetter = orderItemShipGroupAssocSubitemOrderItemShipGroupAssocSubitemDaysGetter;
    }

    public OrderState retrieveOrderState(String orderId) {
        String resourceAccountAddress = getResourceAccountAddress();
        AccountResource<Order.Tables> accountResource;
        try {
            accountResource = aptosNodeApiClient.getAccountResource(resourceAccountAddress,
                    this.aptosContractAddress + "::" + ContractConstants.ORDER_MODULE_TABLES,
                    Order.Tables.class,
                    null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String tableHandle = accountResource.getData().getOrderTable().getHandle();
        Order order;
        try {
            order = aptosNodeApiClient.getTableItem(
                    tableHandle,
                    ContractConstants.toNumericalAddressType(ContractConstants.ORDER_ID_TYPE, this.aptosContractAddress),
                    this.aptosContractAddress + "::" + ContractConstants.ORDER_MODULE_ORDER,
                    orderId,
                    Order.class,
                    null
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return toOrderState(order);
    }

    private OrderState toOrderState(Order order) {
        OrderState.MutableOrderState orderState = orderStateFactory.apply(order.getOrderId());
        orderState.setVersion(order.getVersion());
        orderState.setTotalAmount(order.getTotalAmount());
        orderState.setEstimatedShipDate(DomainBeanUtils.toDay(order.getEstimatedShipDate().getVec().size() == 0 ? null : order.getEstimatedShipDate().getVec().get(0)));
        if (order.getItems() != null) {
            String orderItemTableHandle = order.getItems().getInner().getHandle();
            List<OrderItem> items = getOrderItems(orderItemTableHandle, orderItemProductIdsGetter.getOrderItemProductIds(orderState.getOrderId()));
            for (OrderItem i : items) {
                orderState.getItems().add(toOrderItemState(orderState, i));
            }
        }

        if (order.getOrderShipGroups() != null) {
            String orderShipGroupTableHandle = order.getOrderShipGroups().getInner().getHandle();
            List<OrderShipGroup> orderShipGroups = getOrderShipGroups(orderShipGroupTableHandle, orderShipGroupShipGroupSeqIdsGetter.getOrderShipGroupShipGroupSeqIds(orderState.getOrderId()));
            for (OrderShipGroup i : orderShipGroups) {
                orderState.getOrderShipGroups().add(toOrderShipGroupState(orderState, i));
            }
        }

        return orderState;
    }

    private OrderItemState toOrderItemState(OrderState orderState, OrderItem orderItem) {
        OrderItemState.MutableOrderItemState orderItemState = orderItemStateFactory.apply(orderState, orderItem.getProductId());
        orderItemState.setQuantity(orderItem.getQuantity());
        orderItemState.setItemAmount(orderItem.getItemAmount());
        return orderItemState;
    }

    private OrderShipGroupState toOrderShipGroupState(OrderState orderState, OrderShipGroup orderShipGroup) {
        OrderShipGroupState.MutableOrderShipGroupState orderShipGroupState = orderShipGroupStateFactory.apply(orderState, orderShipGroup.getShipGroupSeqId());
        orderShipGroupState.setShipmentMethod(orderShipGroup.getShipmentMethod());
        if (orderShipGroup.getOrderItemShipGroupAssociations() != null) {
            String orderItemShipGroupAssociationTableHandle = orderShipGroup.getOrderItemShipGroupAssociations().getInner().getHandle();
            List<OrderItemShipGroupAssociation> orderItemShipGroupAssociations = getOrderItemShipGroupAssociations(orderItemShipGroupAssociationTableHandle, orderItemShipGroupAssociationProductIdsGetter.getOrderItemShipGroupAssociationProductIds(orderShipGroupState.getOrderId(), orderShipGroupState.getShipGroupSeqId()));
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
            String orderItemShipGroupAssocSubitemTableHandle = orderItemShipGroupAssociation.getSubitems().getInner().getHandle();
            List<OrderItemShipGroupAssocSubitem> subitems = getOrderItemShipGroupAssocSubitems(orderItemShipGroupAssocSubitemTableHandle, orderItemShipGroupAssocSubitemOrderItemShipGroupAssocSubitemDaysGetter.getOrderItemShipGroupAssocSubitemOrderItemShipGroupAssocSubitemDays(orderItemShipGroupAssociationState.getOrderId(), orderItemShipGroupAssociationState.getOrderShipGroupShipGroupSeqId(), orderItemShipGroupAssociationState.getProductId()));
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

    private List<OrderItem> getOrderItems(String orderItemTableHandle, List<String> productIds) {
        List<OrderItem> orderItems = new ArrayList<>();

        for (String productId : productIds) {
            OrderItem orderItem;
            try {
                orderItem = aptosNodeApiClient.getTableItem(
                        orderItemTableHandle,
                        ContractConstants.toNumericalAddressType(ContractConstants.ORDER_ITEM_ID_TYPE, this.aptosContractAddress),
                        this.aptosContractAddress + "::" + ContractConstants.ORDER_ITEM_MODULE_ORDER_ITEM,
                        productId,
                        OrderItem.class,
                        null
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (orderItem != null)
            {
                orderItems.add(orderItem);
            }
        }
        return orderItems;
    }

    private List<OrderShipGroup> getOrderShipGroups(String orderShipGroupTableHandle, List<Integer> shipGroupSeqIds) {
        List<OrderShipGroup> orderShipGroups = new ArrayList<>();

        for (Integer shipGroupSeqId : shipGroupSeqIds) {
            OrderShipGroup orderShipGroup;
            try {
                orderShipGroup = aptosNodeApiClient.getTableItem(
                        orderShipGroupTableHandle,
                        ContractConstants.toNumericalAddressType(ContractConstants.ORDER_SHIP_GROUP_ID_TYPE, this.aptosContractAddress),
                        this.aptosContractAddress + "::" + ContractConstants.ORDER_SHIP_GROUP_MODULE_ORDER_SHIP_GROUP,
                        shipGroupSeqId,
                        OrderShipGroup.class,
                        null
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (orderShipGroup != null)
            {
                orderShipGroups.add(orderShipGroup);
            }
        }
        return orderShipGroups;
    }

    private List<OrderItemShipGroupAssociation> getOrderItemShipGroupAssociations(String orderItemShipGroupAssociationTableHandle, List<String> productIds) {
        List<OrderItemShipGroupAssociation> orderItemShipGroupAssociations = new ArrayList<>();

        for (String productId : productIds) {
            OrderItemShipGroupAssociation orderItemShipGroupAssociation;
            try {
                orderItemShipGroupAssociation = aptosNodeApiClient.getTableItem(
                        orderItemShipGroupAssociationTableHandle,
                        ContractConstants.toNumericalAddressType(ContractConstants.ORDER_ITEM_SHIP_GROUP_ASSOCIATION_ID_TYPE, this.aptosContractAddress),
                        this.aptosContractAddress + "::" + ContractConstants.ORDER_ITEM_SHIP_GROUP_ASSOCIATION_MODULE_ORDER_ITEM_SHIP_GROUP_ASSOCIATION,
                        productId,
                        OrderItemShipGroupAssociation.class,
                        null
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (orderItemShipGroupAssociation != null)
            {
                orderItemShipGroupAssociations.add(orderItemShipGroupAssociation);
            }
        }
        return orderItemShipGroupAssociations;
    }

    private List<OrderItemShipGroupAssocSubitem> getOrderItemShipGroupAssocSubitems(String orderItemShipGroupAssocSubitemTableHandle, List<Day> orderItemShipGroupAssocSubitemDays) {
        List<OrderItemShipGroupAssocSubitem> orderItemShipGroupAssocSubitems = new ArrayList<>();

        for (Day orderItemShipGroupAssocSubitemDay : orderItemShipGroupAssocSubitemDays) {
            OrderItemShipGroupAssocSubitem orderItemShipGroupAssocSubitem;
            try {
                orderItemShipGroupAssocSubitem = aptosNodeApiClient.getTableItem(
                        orderItemShipGroupAssocSubitemTableHandle,
                        ContractConstants.toNumericalAddressType(ContractConstants.ORDER_ITEM_SHIP_GROUP_ASSOC_SUBITEM_ID_TYPE, this.aptosContractAddress),
                        this.aptosContractAddress + "::" + ContractConstants.ORDER_ITEM_SHIP_GROUP_ASSOC_SUBITEM_MODULE_ORDER_ITEM_SHIP_GROUP_ASSOC_SUBITEM,
                        orderItemShipGroupAssocSubitemDay,
                        OrderItemShipGroupAssocSubitem.class,
                        null
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (orderItemShipGroupAssocSubitem != null)
            {
                orderItemShipGroupAssocSubitems.add(orderItemShipGroupAssocSubitem);
            }
        }
        return orderItemShipGroupAssocSubitems;
    }

    private String getResourceAccountAddress() {
        return aptosAccountRepository.findById(ContractConstants.RESOURCE_ACCOUNT_ADDRESS)
                .map(AptosAccount::getAddress).orElse(null);
    }

    public interface OrderItemProductIdsGetter {
        List<String> getOrderItemProductIds(String orderId);
    }

    public interface OrderShipGroupShipGroupSeqIdsGetter {
        List<Integer> getOrderShipGroupShipGroupSeqIds(String orderId);
    }

    public interface OrderItemShipGroupAssociationProductIdsGetter {
        List<String> getOrderItemShipGroupAssociationProductIds(String orderId, Integer orderShipGroupShipGroupSeqId);
    }

    public interface OrderItemShipGroupAssocSubitemOrderItemShipGroupAssocSubitemDaysGetter {
        List<Day> getOrderItemShipGroupAssocSubitemOrderItemShipGroupAssocSubitemDays(String orderId, Integer orderShipGroupShipGroupSeqId, String orderItemShipGroupAssociationProductId);
    }

}

