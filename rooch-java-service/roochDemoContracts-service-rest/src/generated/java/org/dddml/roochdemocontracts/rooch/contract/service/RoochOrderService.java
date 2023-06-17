// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.rooch.contract.service;

import com.github.wubuku.rooch.utils.RoochJsonRpcClient;
import org.dddml.roochdemocontracts.domain.*;
import org.dddml.roochdemocontracts.domain.order.*;
import org.dddml.roochdemocontracts.rooch.contract.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.*;
import java.util.*;
import java.math.*;

@Service
public class RoochOrderService {

    @Autowired
    private OrderStateRepository orderStateRepository;

    @Autowired
    private OrderItemTableItemAddedRepository orderItemTableItemAddedRepository;
    @Autowired
    private OrderShipGroupTableItemAddedRepository orderShipGroupTableItemAddedRepository;
    @Autowired
    private OrderItemShipGroupAssociationTableItemAddedRepository orderItemShipGroupAssociationTableItemAddedRepository;
    @Autowired
    private OrderItemShipGroupAssocSubitemTableItemAddedRepository orderItemShipGroupAssocSubitemTableItemAddedRepository;
    @Autowired
    private OrderEventService orderEventService;

    private RoochOrderStateRetriever roochOrderStateRetriever;

    @Autowired
    public RoochOrderService(RoochJsonRpcClient roochJsonRpcClient) {
        this.roochOrderStateRetriever = new RoochOrderStateRetriever(roochJsonRpcClient,
                orderId -> {
                    OrderState.MutableOrderState s = new AbstractOrderState.SimpleOrderState();
                    s.setOrderId(orderId);
                    return s;
                },
                (orderState, productObjectId) -> (OrderItemState.MutableOrderItemState)
                        ((EntityStateCollection.ModifiableEntityStateCollection<String, OrderItemState>) orderState.getItems()).getOrAdd(productObjectId),
                orderId -> {
                    orderEventService.pullOrderItemTableItemAddedEvents();
                    return orderItemTableItemAddedRepository.findByOrderItemId_OrderId(orderId).stream()
                            .map(i -> i.getOrderItemId().getProductObjectId()).collect(Collectors.toList());
                },
                (orderState, shipGroupSeqId) -> (OrderShipGroupState.MutableOrderShipGroupState)
                        ((EntityStateCollection.ModifiableEntityStateCollection<Integer, OrderShipGroupState>) orderState.getOrderShipGroups()).getOrAdd(shipGroupSeqId),
                orderId -> {
                    orderEventService.pullOrderShipGroupTableItemAddedEvents();
                    return orderShipGroupTableItemAddedRepository.findByOrderShipGroupId_OrderId(orderId).stream()
                            .map(i -> i.getOrderShipGroupId().getShipGroupSeqId()).collect(Collectors.toList());
                },
                (orderShipGroupState, productObjId) -> (OrderItemShipGroupAssociationState.MutableOrderItemShipGroupAssociationState)
                        ((EntityStateCollection.ModifiableEntityStateCollection<String, OrderItemShipGroupAssociationState>) orderShipGroupState.getOrderItemShipGroupAssociations()).getOrAdd(productObjId),
                (orderId, orderShipGroupShipGroupSeqId) -> {
                    orderEventService.pullOrderItemShipGroupAssociationTableItemAddedEvents();
                    return orderItemShipGroupAssociationTableItemAddedRepository.findByOrderItemShipGroupAssociationId_OrderIdAndOrderItemShipGroupAssociationId_OrderShipGroupShipGroupSeqId(orderId, orderShipGroupShipGroupSeqId).stream()
                            .map(i -> i.getOrderItemShipGroupAssociationId().getProductObjId()).collect(Collectors.toList());
                },
                (orderItemShipGroupAssociationState, orderItemShipGroupAssocSubitemDay) -> (OrderItemShipGroupAssocSubitemState.MutableOrderItemShipGroupAssocSubitemState)
                        ((EntityStateCollection.ModifiableEntityStateCollection<Day, OrderItemShipGroupAssocSubitemState>) orderItemShipGroupAssociationState.getSubitems()).getOrAdd(orderItemShipGroupAssocSubitemDay),
                (orderId, orderShipGroupShipGroupSeqId, orderItemShipGroupAssociationProductObjId) -> {
                    orderEventService.pullOrderItemShipGroupAssocSubitemTableItemAddedEvents();
                    return orderItemShipGroupAssocSubitemTableItemAddedRepository.findByOrderItemShipGroupAssocSubitemId_OrderIdAndOrderItemShipGroupAssocSubitemId_OrderShipGroupShipGroupSeqIdAndOrderItemShipGroupAssocSubitemId_OrderItemShipGroupAssociationProductObjId(orderId, orderShipGroupShipGroupSeqId, orderItemShipGroupAssociationProductObjId).stream()
                            .map(i -> i.getOrderItemShipGroupAssocSubitemId().getOrderItemShipGroupAssocSubitemDay()).collect(Collectors.toList());
                }
        );
    }

    @Transactional
    public void updateOrderState(String objectId) {
        OrderState orderState = roochOrderStateRetriever.retrieveOrderState(objectId);
        orderStateRepository.merge(orderState);
    }


}

