// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.rooch.contract.service;

import com.github.wubuku.rooch.bean.AnnotatedEventView;
import com.github.wubuku.rooch.utils.RoochJsonRpcClient;
import com.github.wubuku.rooch.bean.EventPageView;
import com.github.wubuku.rooch.bean.PageView;

import org.dddml.roochdemocontracts.domain.order.AbstractOrderEvent;
import org.dddml.roochdemocontracts.rooch.contract.ContractConstants;
import org.dddml.roochdemocontracts.rooch.contract.DomainBeanUtils;
import org.dddml.roochdemocontracts.rooch.contract.order.OrderCreated;
import org.dddml.roochdemocontracts.rooch.contract.order.OrderItemRemoved;
import org.dddml.roochdemocontracts.rooch.contract.order.OrderItemQuantityUpdated;
import org.dddml.roochdemocontracts.rooch.contract.order.OrderEstimatedShipDateUpdated;
import org.dddml.roochdemocontracts.rooch.contract.order.OrderShipGroupAdded;
import org.dddml.roochdemocontracts.rooch.contract.order.OrderItemShipGroupAssocSubitemAdded;
import org.dddml.roochdemocontracts.rooch.contract.order.OrderShipGroupQuantityCanceled;
import org.dddml.roochdemocontracts.rooch.contract.order.OrderShipGroupItemRemoved;
import org.dddml.roochdemocontracts.rooch.contract.repository.OrderEventRepository;
import org.dddml.roochdemocontracts.rooch.contract.repository.OrderItemTableItemAddedRepository;
import org.dddml.roochdemocontracts.rooch.contract.OrderItemTableItemAdded;
import org.dddml.roochdemocontracts.rooch.contract.repository.OrderShipGroupTableItemAddedRepository;
import org.dddml.roochdemocontracts.rooch.contract.OrderShipGroupTableItemAdded;
import org.dddml.roochdemocontracts.rooch.contract.repository.OrderItemShipGroupAssociationTableItemAddedRepository;
import org.dddml.roochdemocontracts.rooch.contract.OrderItemShipGroupAssociationTableItemAdded;
import org.dddml.roochdemocontracts.rooch.contract.repository.OrderItemShipGroupAssocSubitemTableItemAddedRepository;
import org.dddml.roochdemocontracts.rooch.contract.OrderItemShipGroupAssocSubitemTableItemAdded;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.*;
import java.util.*;

@Service
public class OrderEventService {
    @Value("${rooch.contract.address}")
    private String contractAddress;

    @Autowired
    private RoochJsonRpcClient roochJsonRpcClient;

    @Autowired
    private OrderEventRepository orderEventRepository;

    @Autowired
    private OrderItemTableItemAddedRepository orderItemTableItemAddedRepository;

    @Autowired
    private OrderShipGroupTableItemAddedRepository orderShipGroupTableItemAddedRepository;

    @Autowired
    private OrderItemShipGroupAssociationTableItemAddedRepository orderItemShipGroupAssociationTableItemAddedRepository;

    @Autowired
    private OrderItemShipGroupAssocSubitemTableItemAddedRepository orderItemShipGroupAssocSubitemTableItemAddedRepository;


    @Transactional
    public void updateStatusToProcessed(AbstractOrderEvent event) {
        event.setStatus("D");
        orderEventRepository.save(event);
    }

    @Transactional
    public void pullOrderCreatedEvents() {
        if (contractAddress == null) {
            return;
        }
        long limit = 1L;
        String eventType = contractAddress + "::" + ContractConstants.ORDER_MODULE_ORDER_CREATED;
        BigInteger cursor = getOrderCreatedEventNextCursor();
        while (true) {
            EventPageView<OrderCreated> eventPage = roochJsonRpcClient.getEventsByEventHandle(
                    eventType, cursor, limit, OrderCreated.class
            );
            if (eventPage != null && eventPage.getData() != null && eventPage.getData().size() > 0) {
                cursor = eventPage.getNextCursor();
                for (AnnotatedEventView<OrderCreated> eventEnvelope : eventPage.getData()) {
                    saveOrderCreated(eventEnvelope);
                }
            } else {
                break;
            }
            if (!PageView.hasNextPage(eventPage)) {
                break;
            }
        }
    }

    private BigInteger getOrderCreatedEventNextCursor() {
        AbstractOrderEvent.OrderCreated lastEvent = orderEventRepository.findFirstOrderCreatedByOrderByRoochEventId_EventSeqDesc();
        return lastEvent != null ? lastEvent.getRoochEventId().getEventSeq() : null;
    }

    private void saveOrderCreated(AnnotatedEventView<OrderCreated> eventEnvelope) {
        AbstractOrderEvent.OrderCreated orderCreated = DomainBeanUtils.toOrderCreated(eventEnvelope);
        if (orderEventRepository.findById(orderCreated.getOrderEventId()).isPresent()) {
            return;
        }
        orderEventRepository.save(orderCreated);
    }

    @Transactional
    public void pullOrderItemRemovedEvents() {
        if (contractAddress == null) {
            return;
        }
        long limit = 1L;
        String eventType = contractAddress + "::" + ContractConstants.ORDER_MODULE_ORDER_ITEM_REMOVED;
        BigInteger cursor = getOrderItemRemovedEventNextCursor();
        while (true) {
            EventPageView<OrderItemRemoved> eventPage = roochJsonRpcClient.getEventsByEventHandle(
                    eventType, cursor, limit, OrderItemRemoved.class
            );
            if (eventPage != null && eventPage.getData() != null && eventPage.getData().size() > 0) {
                cursor = eventPage.getNextCursor();
                for (AnnotatedEventView<OrderItemRemoved> eventEnvelope : eventPage.getData()) {
                    saveOrderItemRemoved(eventEnvelope);
                }
            } else {
                break;
            }
            if (!PageView.hasNextPage(eventPage)) {
                break;
            }
        }
    }

    private BigInteger getOrderItemRemovedEventNextCursor() {
        AbstractOrderEvent.OrderItemRemoved lastEvent = orderEventRepository.findFirstOrderItemRemovedByOrderByRoochEventId_EventSeqDesc();
        return lastEvent != null ? lastEvent.getRoochEventId().getEventSeq() : null;
    }

    private void saveOrderItemRemoved(AnnotatedEventView<OrderItemRemoved> eventEnvelope) {
        AbstractOrderEvent.OrderItemRemoved orderItemRemoved = DomainBeanUtils.toOrderItemRemoved(eventEnvelope);
        if (orderEventRepository.findById(orderItemRemoved.getOrderEventId()).isPresent()) {
            return;
        }
        orderEventRepository.save(orderItemRemoved);
    }

    @Transactional
    public void pullOrderItemQuantityUpdatedEvents() {
        if (contractAddress == null) {
            return;
        }
        long limit = 1L;
        String eventType = contractAddress + "::" + ContractConstants.ORDER_MODULE_ORDER_ITEM_QUANTITY_UPDATED;
        BigInteger cursor = getOrderItemQuantityUpdatedEventNextCursor();
        while (true) {
            EventPageView<OrderItemQuantityUpdated> eventPage = roochJsonRpcClient.getEventsByEventHandle(
                    eventType, cursor, limit, OrderItemQuantityUpdated.class
            );
            if (eventPage != null && eventPage.getData() != null && eventPage.getData().size() > 0) {
                cursor = eventPage.getNextCursor();
                for (AnnotatedEventView<OrderItemQuantityUpdated> eventEnvelope : eventPage.getData()) {
                    saveOrderItemQuantityUpdated(eventEnvelope);
                }
            } else {
                break;
            }
            if (!PageView.hasNextPage(eventPage)) {
                break;
            }
        }
    }

    private BigInteger getOrderItemQuantityUpdatedEventNextCursor() {
        AbstractOrderEvent.OrderItemQuantityUpdated lastEvent = orderEventRepository.findFirstOrderItemQuantityUpdatedByOrderByRoochEventId_EventSeqDesc();
        return lastEvent != null ? lastEvent.getRoochEventId().getEventSeq() : null;
    }

    private void saveOrderItemQuantityUpdated(AnnotatedEventView<OrderItemQuantityUpdated> eventEnvelope) {
        AbstractOrderEvent.OrderItemQuantityUpdated orderItemQuantityUpdated = DomainBeanUtils.toOrderItemQuantityUpdated(eventEnvelope);
        if (orderEventRepository.findById(orderItemQuantityUpdated.getOrderEventId()).isPresent()) {
            return;
        }
        orderEventRepository.save(orderItemQuantityUpdated);
    }

    @Transactional
    public void pullOrderEstimatedShipDateUpdatedEvents() {
        if (contractAddress == null) {
            return;
        }
        long limit = 1L;
        String eventType = contractAddress + "::" + ContractConstants.ORDER_MODULE_ORDER_ESTIMATED_SHIP_DATE_UPDATED;
        BigInteger cursor = getOrderEstimatedShipDateUpdatedEventNextCursor();
        while (true) {
            EventPageView<OrderEstimatedShipDateUpdated> eventPage = roochJsonRpcClient.getEventsByEventHandle(
                    eventType, cursor, limit, OrderEstimatedShipDateUpdated.class
            );
            if (eventPage != null && eventPage.getData() != null && eventPage.getData().size() > 0) {
                cursor = eventPage.getNextCursor();
                for (AnnotatedEventView<OrderEstimatedShipDateUpdated> eventEnvelope : eventPage.getData()) {
                    saveOrderEstimatedShipDateUpdated(eventEnvelope);
                }
            } else {
                break;
            }
            if (!PageView.hasNextPage(eventPage)) {
                break;
            }
        }
    }

    private BigInteger getOrderEstimatedShipDateUpdatedEventNextCursor() {
        AbstractOrderEvent.OrderEstimatedShipDateUpdated lastEvent = orderEventRepository.findFirstOrderEstimatedShipDateUpdatedByOrderByRoochEventId_EventSeqDesc();
        return lastEvent != null ? lastEvent.getRoochEventId().getEventSeq() : null;
    }

    private void saveOrderEstimatedShipDateUpdated(AnnotatedEventView<OrderEstimatedShipDateUpdated> eventEnvelope) {
        AbstractOrderEvent.OrderEstimatedShipDateUpdated orderEstimatedShipDateUpdated = DomainBeanUtils.toOrderEstimatedShipDateUpdated(eventEnvelope);
        if (orderEventRepository.findById(orderEstimatedShipDateUpdated.getOrderEventId()).isPresent()) {
            return;
        }
        orderEventRepository.save(orderEstimatedShipDateUpdated);
    }

    @Transactional
    public void pullOrderShipGroupAddedEvents() {
        if (contractAddress == null) {
            return;
        }
        long limit = 1L;
        String eventType = contractAddress + "::" + ContractConstants.ORDER_MODULE_ORDER_SHIP_GROUP_ADDED;
        BigInteger cursor = getOrderShipGroupAddedEventNextCursor();
        while (true) {
            EventPageView<OrderShipGroupAdded> eventPage = roochJsonRpcClient.getEventsByEventHandle(
                    eventType, cursor, limit, OrderShipGroupAdded.class
            );
            if (eventPage != null && eventPage.getData() != null && eventPage.getData().size() > 0) {
                cursor = eventPage.getNextCursor();
                for (AnnotatedEventView<OrderShipGroupAdded> eventEnvelope : eventPage.getData()) {
                    saveOrderShipGroupAdded(eventEnvelope);
                }
            } else {
                break;
            }
            if (!PageView.hasNextPage(eventPage)) {
                break;
            }
        }
    }

    private BigInteger getOrderShipGroupAddedEventNextCursor() {
        AbstractOrderEvent.OrderShipGroupAdded lastEvent = orderEventRepository.findFirstOrderShipGroupAddedByOrderByRoochEventId_EventSeqDesc();
        return lastEvent != null ? lastEvent.getRoochEventId().getEventSeq() : null;
    }

    private void saveOrderShipGroupAdded(AnnotatedEventView<OrderShipGroupAdded> eventEnvelope) {
        AbstractOrderEvent.OrderShipGroupAdded orderShipGroupAdded = DomainBeanUtils.toOrderShipGroupAdded(eventEnvelope);
        if (orderEventRepository.findById(orderShipGroupAdded.getOrderEventId()).isPresent()) {
            return;
        }
        orderEventRepository.save(orderShipGroupAdded);
    }

    @Transactional
    public void pullOrderItemShipGroupAssocSubitemAddedEvents() {
        if (contractAddress == null) {
            return;
        }
        long limit = 1L;
        String eventType = contractAddress + "::" + ContractConstants.ORDER_MODULE_ORDER_ITEM_SHIP_GROUP_ASSOC_SUBITEM_ADDED;
        BigInteger cursor = getOrderItemShipGroupAssocSubitemAddedEventNextCursor();
        while (true) {
            EventPageView<OrderItemShipGroupAssocSubitemAdded> eventPage = roochJsonRpcClient.getEventsByEventHandle(
                    eventType, cursor, limit, OrderItemShipGroupAssocSubitemAdded.class
            );
            if (eventPage != null && eventPage.getData() != null && eventPage.getData().size() > 0) {
                cursor = eventPage.getNextCursor();
                for (AnnotatedEventView<OrderItemShipGroupAssocSubitemAdded> eventEnvelope : eventPage.getData()) {
                    saveOrderItemShipGroupAssocSubitemAdded(eventEnvelope);
                }
            } else {
                break;
            }
            if (!PageView.hasNextPage(eventPage)) {
                break;
            }
        }
    }

    private BigInteger getOrderItemShipGroupAssocSubitemAddedEventNextCursor() {
        AbstractOrderEvent.OrderItemShipGroupAssocSubitemAdded lastEvent = orderEventRepository.findFirstOrderItemShipGroupAssocSubitemAddedByOrderByRoochEventId_EventSeqDesc();
        return lastEvent != null ? lastEvent.getRoochEventId().getEventSeq() : null;
    }

    private void saveOrderItemShipGroupAssocSubitemAdded(AnnotatedEventView<OrderItemShipGroupAssocSubitemAdded> eventEnvelope) {
        AbstractOrderEvent.OrderItemShipGroupAssocSubitemAdded orderItemShipGroupAssocSubitemAdded = DomainBeanUtils.toOrderItemShipGroupAssocSubitemAdded(eventEnvelope);
        if (orderEventRepository.findById(orderItemShipGroupAssocSubitemAdded.getOrderEventId()).isPresent()) {
            return;
        }
        orderEventRepository.save(orderItemShipGroupAssocSubitemAdded);
    }

    @Transactional
    public void pullOrderShipGroupQuantityCanceledEvents() {
        if (contractAddress == null) {
            return;
        }
        long limit = 1L;
        String eventType = contractAddress + "::" + ContractConstants.ORDER_MODULE_ORDER_SHIP_GROUP_QUANTITY_CANCELED;
        BigInteger cursor = getOrderShipGroupQuantityCanceledEventNextCursor();
        while (true) {
            EventPageView<OrderShipGroupQuantityCanceled> eventPage = roochJsonRpcClient.getEventsByEventHandle(
                    eventType, cursor, limit, OrderShipGroupQuantityCanceled.class
            );
            if (eventPage != null && eventPage.getData() != null && eventPage.getData().size() > 0) {
                cursor = eventPage.getNextCursor();
                for (AnnotatedEventView<OrderShipGroupQuantityCanceled> eventEnvelope : eventPage.getData()) {
                    saveOrderShipGroupQuantityCanceled(eventEnvelope);
                }
            } else {
                break;
            }
            if (!PageView.hasNextPage(eventPage)) {
                break;
            }
        }
    }

    private BigInteger getOrderShipGroupQuantityCanceledEventNextCursor() {
        AbstractOrderEvent.OrderShipGroupQuantityCanceled lastEvent = orderEventRepository.findFirstOrderShipGroupQuantityCanceledByOrderByRoochEventId_EventSeqDesc();
        return lastEvent != null ? lastEvent.getRoochEventId().getEventSeq() : null;
    }

    private void saveOrderShipGroupQuantityCanceled(AnnotatedEventView<OrderShipGroupQuantityCanceled> eventEnvelope) {
        AbstractOrderEvent.OrderShipGroupQuantityCanceled orderShipGroupQuantityCanceled = DomainBeanUtils.toOrderShipGroupQuantityCanceled(eventEnvelope);
        if (orderEventRepository.findById(orderShipGroupQuantityCanceled.getOrderEventId()).isPresent()) {
            return;
        }
        orderEventRepository.save(orderShipGroupQuantityCanceled);
    }

    @Transactional
    public void pullOrderShipGroupItemRemovedEvents() {
        if (contractAddress == null) {
            return;
        }
        long limit = 1L;
        String eventType = contractAddress + "::" + ContractConstants.ORDER_MODULE_ORDER_SHIP_GROUP_ITEM_REMOVED;
        BigInteger cursor = getOrderShipGroupItemRemovedEventNextCursor();
        while (true) {
            EventPageView<OrderShipGroupItemRemoved> eventPage = roochJsonRpcClient.getEventsByEventHandle(
                    eventType, cursor, limit, OrderShipGroupItemRemoved.class
            );
            if (eventPage != null && eventPage.getData() != null && eventPage.getData().size() > 0) {
                cursor = eventPage.getNextCursor();
                for (AnnotatedEventView<OrderShipGroupItemRemoved> eventEnvelope : eventPage.getData()) {
                    saveOrderShipGroupItemRemoved(eventEnvelope);
                }
            } else {
                break;
            }
            if (!PageView.hasNextPage(eventPage)) {
                break;
            }
        }
    }

    private BigInteger getOrderShipGroupItemRemovedEventNextCursor() {
        AbstractOrderEvent.OrderShipGroupItemRemoved lastEvent = orderEventRepository.findFirstOrderShipGroupItemRemovedByOrderByRoochEventId_EventSeqDesc();
        return lastEvent != null ? lastEvent.getRoochEventId().getEventSeq() : null;
    }

    private void saveOrderShipGroupItemRemoved(AnnotatedEventView<OrderShipGroupItemRemoved> eventEnvelope) {
        AbstractOrderEvent.OrderShipGroupItemRemoved orderShipGroupItemRemoved = DomainBeanUtils.toOrderShipGroupItemRemoved(eventEnvelope);
        if (orderEventRepository.findById(orderShipGroupItemRemoved.getOrderEventId()).isPresent()) {
            return;
        }
        orderEventRepository.save(orderShipGroupItemRemoved);
    }

    @Transactional
    public void pullOrderItemTableItemAddedEvents() {
        if (contractAddress == null) {
            return;
        }
        long limit = 1L;
        String eventType = contractAddress + "::" + ContractConstants.ORDER_ITEM_TABLE_ITEM_ADDED;
        BigInteger cursor = getOrderItemTableItemAddedEventNextCursor();
        while (true) {
            EventPageView<OrderItemTableItemAdded> eventPage = roochJsonRpcClient.getEventsByEventHandle(
                    eventType, cursor, limit, OrderItemTableItemAdded.class
            );
            if (eventPage != null && eventPage.getData() != null && eventPage.getData().size() > 0) {
                cursor = eventPage.getNextCursor();
                for (AnnotatedEventView<OrderItemTableItemAdded> eventEnvelope : eventPage.getData()) {
                    saveOrderItemTableItemAdded(eventEnvelope);
                }
            } else {
                break;
            }
            if (!PageView.hasNextPage(eventPage)) {
                break;
            }
        }
    }

    private BigInteger getOrderItemTableItemAddedEventNextCursor() {
        org.dddml.roochdemocontracts.rooch.contract.persistence.OrderItemTableItemAdded lastEvent = orderItemTableItemAddedRepository.findFirstByOrderByRoochEventId_EventSeqDesc();
        return lastEvent != null ? lastEvent.getRoochEventId().getEventSeq() : null;
    }

    private void saveOrderItemTableItemAdded(AnnotatedEventView<OrderItemTableItemAdded> eventEnvelope) {
        org.dddml.roochdemocontracts.rooch.contract.persistence.OrderItemTableItemAdded orderItemTableItemAdded = DomainBeanUtils.toPersistenceOrderItemTableItemAdded(eventEnvelope);
        if (orderItemTableItemAddedRepository.findById(orderItemTableItemAdded.getOrderItemId()).isPresent()) {
            return;
        }
        orderItemTableItemAddedRepository.save(orderItemTableItemAdded);
    }

    @Transactional
    public void pullOrderShipGroupTableItemAddedEvents() {
        if (contractAddress == null) {
            return;
        }
        long limit = 1L;
        String eventType = contractAddress + "::" + ContractConstants.ORDER_SHIP_GROUP_TABLE_ITEM_ADDED;
        BigInteger cursor = getOrderShipGroupTableItemAddedEventNextCursor();
        while (true) {
            EventPageView<OrderShipGroupTableItemAdded> eventPage = roochJsonRpcClient.getEventsByEventHandle(
                    eventType, cursor, limit, OrderShipGroupTableItemAdded.class
            );
            if (eventPage != null && eventPage.getData() != null && eventPage.getData().size() > 0) {
                cursor = eventPage.getNextCursor();
                for (AnnotatedEventView<OrderShipGroupTableItemAdded> eventEnvelope : eventPage.getData()) {
                    saveOrderShipGroupTableItemAdded(eventEnvelope);
                }
            } else {
                break;
            }
            if (!PageView.hasNextPage(eventPage)) {
                break;
            }
        }
    }

    private BigInteger getOrderShipGroupTableItemAddedEventNextCursor() {
        org.dddml.roochdemocontracts.rooch.contract.persistence.OrderShipGroupTableItemAdded lastEvent = orderShipGroupTableItemAddedRepository.findFirstByOrderByRoochEventId_EventSeqDesc();
        return lastEvent != null ? lastEvent.getRoochEventId().getEventSeq() : null;
    }

    private void saveOrderShipGroupTableItemAdded(AnnotatedEventView<OrderShipGroupTableItemAdded> eventEnvelope) {
        org.dddml.roochdemocontracts.rooch.contract.persistence.OrderShipGroupTableItemAdded orderShipGroupTableItemAdded = DomainBeanUtils.toPersistenceOrderShipGroupTableItemAdded(eventEnvelope);
        if (orderShipGroupTableItemAddedRepository.findById(orderShipGroupTableItemAdded.getOrderShipGroupId()).isPresent()) {
            return;
        }
        orderShipGroupTableItemAddedRepository.save(orderShipGroupTableItemAdded);
    }

    @Transactional
    public void pullOrderItemShipGroupAssociationTableItemAddedEvents() {
        if (contractAddress == null) {
            return;
        }
        long limit = 1L;
        String eventType = contractAddress + "::" + ContractConstants.ORDER_ITEM_SHIP_GROUP_ASSOCIATION_TABLE_ITEM_ADDED;
        BigInteger cursor = getOrderItemShipGroupAssociationTableItemAddedEventNextCursor();
        while (true) {
            EventPageView<OrderItemShipGroupAssociationTableItemAdded> eventPage = roochJsonRpcClient.getEventsByEventHandle(
                    eventType, cursor, limit, OrderItemShipGroupAssociationTableItemAdded.class
            );
            if (eventPage != null && eventPage.getData() != null && eventPage.getData().size() > 0) {
                cursor = eventPage.getNextCursor();
                for (AnnotatedEventView<OrderItemShipGroupAssociationTableItemAdded> eventEnvelope : eventPage.getData()) {
                    saveOrderItemShipGroupAssociationTableItemAdded(eventEnvelope);
                }
            } else {
                break;
            }
            if (!PageView.hasNextPage(eventPage)) {
                break;
            }
        }
    }

    private BigInteger getOrderItemShipGroupAssociationTableItemAddedEventNextCursor() {
        org.dddml.roochdemocontracts.rooch.contract.persistence.OrderItemShipGroupAssociationTableItemAdded lastEvent = orderItemShipGroupAssociationTableItemAddedRepository.findFirstByOrderByRoochEventId_EventSeqDesc();
        return lastEvent != null ? lastEvent.getRoochEventId().getEventSeq() : null;
    }

    private void saveOrderItemShipGroupAssociationTableItemAdded(AnnotatedEventView<OrderItemShipGroupAssociationTableItemAdded> eventEnvelope) {
        org.dddml.roochdemocontracts.rooch.contract.persistence.OrderItemShipGroupAssociationTableItemAdded orderItemShipGroupAssociationTableItemAdded = DomainBeanUtils.toPersistenceOrderItemShipGroupAssociationTableItemAdded(eventEnvelope);
        if (orderItemShipGroupAssociationTableItemAddedRepository.findById(orderItemShipGroupAssociationTableItemAdded.getOrderItemShipGroupAssociationId()).isPresent()) {
            return;
        }
        orderItemShipGroupAssociationTableItemAddedRepository.save(orderItemShipGroupAssociationTableItemAdded);
    }

    @Transactional
    public void pullOrderItemShipGroupAssocSubitemTableItemAddedEvents() {
        if (contractAddress == null) {
            return;
        }
        long limit = 1L;
        String eventType = contractAddress + "::" + ContractConstants.ORDER_ITEM_SHIP_GROUP_ASSOC_SUBITEM_TABLE_ITEM_ADDED;
        BigInteger cursor = getOrderItemShipGroupAssocSubitemTableItemAddedEventNextCursor();
        while (true) {
            EventPageView<OrderItemShipGroupAssocSubitemTableItemAdded> eventPage = roochJsonRpcClient.getEventsByEventHandle(
                    eventType, cursor, limit, OrderItemShipGroupAssocSubitemTableItemAdded.class
            );
            if (eventPage != null && eventPage.getData() != null && eventPage.getData().size() > 0) {
                cursor = eventPage.getNextCursor();
                for (AnnotatedEventView<OrderItemShipGroupAssocSubitemTableItemAdded> eventEnvelope : eventPage.getData()) {
                    saveOrderItemShipGroupAssocSubitemTableItemAdded(eventEnvelope);
                }
            } else {
                break;
            }
            if (!PageView.hasNextPage(eventPage)) {
                break;
            }
        }
    }

    private BigInteger getOrderItemShipGroupAssocSubitemTableItemAddedEventNextCursor() {
        org.dddml.roochdemocontracts.rooch.contract.persistence.OrderItemShipGroupAssocSubitemTableItemAdded lastEvent = orderItemShipGroupAssocSubitemTableItemAddedRepository.findFirstByOrderByRoochEventId_EventSeqDesc();
        return lastEvent != null ? lastEvent.getRoochEventId().getEventSeq() : null;
    }

    private void saveOrderItemShipGroupAssocSubitemTableItemAdded(AnnotatedEventView<OrderItemShipGroupAssocSubitemTableItemAdded> eventEnvelope) {
        org.dddml.roochdemocontracts.rooch.contract.persistence.OrderItemShipGroupAssocSubitemTableItemAdded orderItemShipGroupAssocSubitemTableItemAdded = DomainBeanUtils.toPersistenceOrderItemShipGroupAssocSubitemTableItemAdded(eventEnvelope);
        if (orderItemShipGroupAssocSubitemTableItemAddedRepository.findById(orderItemShipGroupAssocSubitemTableItemAdded.getOrderItemShipGroupAssocSubitemId()).isPresent()) {
            return;
        }
        orderItemShipGroupAssocSubitemTableItemAddedRepository.save(orderItemShipGroupAssocSubitemTableItemAdded);
    }

}
