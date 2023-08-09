// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.aptosdemocontracts.aptos.contract.service;

import com.github.wubuku.aptos.bean.Event;
import com.github.wubuku.aptos.utils.NodeApiClient;

import org.dddml.aptosdemocontracts.domain.order.AbstractOrderEvent;
import org.dddml.aptosdemocontracts.aptos.contract.ContractConstants;
import org.dddml.aptosdemocontracts.aptos.contract.DomainBeanUtils;
import org.dddml.aptosdemocontracts.aptos.contract.AptosAccount;

import org.dddml.aptosdemocontracts.aptos.contract.order.OrderCreated;
import org.dddml.aptosdemocontracts.aptos.contract.order.OrderItemRemoved;
import org.dddml.aptosdemocontracts.aptos.contract.order.OrderItemQuantityUpdated;
import org.dddml.aptosdemocontracts.aptos.contract.order.OrderEstimatedShipDateUpdated;
import org.dddml.aptosdemocontracts.aptos.contract.order.OrderShipGroupAdded;
import org.dddml.aptosdemocontracts.aptos.contract.order.OrderShipGroupQuantityCanceled;
import org.dddml.aptosdemocontracts.aptos.contract.order.OrderShipGroupItemRemoved;
import org.dddml.aptosdemocontracts.aptos.contract.order.OrderShipGroupRemoved;
import org.dddml.aptosdemocontracts.aptos.contract.repository.OrderEventRepository;
import org.dddml.aptosdemocontracts.aptos.contract.repository.AptosAccountRepository;
import org.dddml.aptosdemocontracts.aptos.contract.repository.OrderItemTableItemAddedRepository;
import org.dddml.aptosdemocontracts.aptos.contract.OrderItemTableItemAdded;
import org.dddml.aptosdemocontracts.aptos.contract.repository.OrderShipGroupTableItemAddedRepository;
import org.dddml.aptosdemocontracts.aptos.contract.OrderShipGroupTableItemAdded;
import org.dddml.aptosdemocontracts.aptos.contract.repository.OrderItemShipGroupAssociationTableItemAddedRepository;
import org.dddml.aptosdemocontracts.aptos.contract.OrderItemShipGroupAssociationTableItemAdded;
import org.dddml.aptosdemocontracts.aptos.contract.repository.OrderItemShipGroupAssocSubitemTableItemAddedRepository;
import org.dddml.aptosdemocontracts.aptos.contract.OrderItemShipGroupAssocSubitemTableItemAdded;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.*;
import java.util.*;


@Service
public class OrderEventService {

    @Value("${aptos.contract.address}")
    private String aptosContractAddress;

    @Autowired
    private AptosAccountRepository aptosAccountRepository;

    @Autowired
    private NodeApiClient aptosNodeApiClient;

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
        String resourceAccountAddress = getResourceAccountAddress();
        if (resourceAccountAddress == null) {
            return;
        }
        int limit = 1;
        BigInteger cursor = getOrderCreatedEventNextCursor();
        if (cursor == null) {
            cursor = BigInteger.ZERO;
        }
        while (true) {
            List<Event<OrderCreated>> eventPage;
            try {
                eventPage = aptosNodeApiClient.getEventsByEventHandle(
                        resourceAccountAddress,
                        this.aptosContractAddress + "::" + ContractConstants.ORDER_MODULE_EVENTS,
                        ContractConstants.ORDER_MODULE_ORDER_CREATED_HANDLE_FIELD,
                        OrderCreated.class,
                        cursor.longValue(),
                        limit
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (eventPage != null && eventPage.size() > 0) {
                cursor = cursor.add(BigInteger.ONE);
                for (Event<OrderCreated> eventEnvelope : eventPage) {
                    saveOrderCreated(eventEnvelope);
                }
            } else {
                break;
            }
        }
    }

    private BigInteger getOrderCreatedEventNextCursor() {
        AbstractOrderEvent.OrderCreated lastEvent = orderEventRepository.findFirstOrderCreatedByOrderByAptosEventSequenceNumber();
        return lastEvent != null ? lastEvent.getAptosEventSequenceNumber() : null;
    }

    private void saveOrderCreated(Event<OrderCreated> eventEnvelope) {
        AbstractOrderEvent.OrderCreated orderCreated = DomainBeanUtils.toOrderCreated(eventEnvelope);
        if (orderEventRepository.findById(orderCreated.getOrderEventId()).isPresent()) {
            return;
        }
        orderEventRepository.save(orderCreated);
    }

    @Transactional
    public void pullOrderItemRemovedEvents() {
        String resourceAccountAddress = getResourceAccountAddress();
        if (resourceAccountAddress == null) {
            return;
        }
        int limit = 1;
        BigInteger cursor = getOrderItemRemovedEventNextCursor();
        if (cursor == null) {
            cursor = BigInteger.ZERO;
        }
        while (true) {
            List<Event<OrderItemRemoved>> eventPage;
            try {
                eventPage = aptosNodeApiClient.getEventsByEventHandle(
                        resourceAccountAddress,
                        this.aptosContractAddress + "::" + ContractConstants.ORDER_MODULE_EVENTS,
                        ContractConstants.ORDER_MODULE_ORDER_ITEM_REMOVED_HANDLE_FIELD,
                        OrderItemRemoved.class,
                        cursor.longValue(),
                        limit
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (eventPage != null && eventPage.size() > 0) {
                cursor = cursor.add(BigInteger.ONE);
                for (Event<OrderItemRemoved> eventEnvelope : eventPage) {
                    saveOrderItemRemoved(eventEnvelope);
                }
            } else {
                break;
            }
        }
    }

    private BigInteger getOrderItemRemovedEventNextCursor() {
        AbstractOrderEvent.OrderItemRemoved lastEvent = orderEventRepository.findFirstOrderItemRemovedByOrderByAptosEventSequenceNumber();
        return lastEvent != null ? lastEvent.getAptosEventSequenceNumber() : null;
    }

    private void saveOrderItemRemoved(Event<OrderItemRemoved> eventEnvelope) {
        AbstractOrderEvent.OrderItemRemoved orderItemRemoved = DomainBeanUtils.toOrderItemRemoved(eventEnvelope);
        if (orderEventRepository.findById(orderItemRemoved.getOrderEventId()).isPresent()) {
            return;
        }
        orderEventRepository.save(orderItemRemoved);
    }

    @Transactional
    public void pullOrderItemQuantityUpdatedEvents() {
        String resourceAccountAddress = getResourceAccountAddress();
        if (resourceAccountAddress == null) {
            return;
        }
        int limit = 1;
        BigInteger cursor = getOrderItemQuantityUpdatedEventNextCursor();
        if (cursor == null) {
            cursor = BigInteger.ZERO;
        }
        while (true) {
            List<Event<OrderItemQuantityUpdated>> eventPage;
            try {
                eventPage = aptosNodeApiClient.getEventsByEventHandle(
                        resourceAccountAddress,
                        this.aptosContractAddress + "::" + ContractConstants.ORDER_MODULE_EVENTS,
                        ContractConstants.ORDER_MODULE_ORDER_ITEM_QUANTITY_UPDATED_HANDLE_FIELD,
                        OrderItemQuantityUpdated.class,
                        cursor.longValue(),
                        limit
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (eventPage != null && eventPage.size() > 0) {
                cursor = cursor.add(BigInteger.ONE);
                for (Event<OrderItemQuantityUpdated> eventEnvelope : eventPage) {
                    saveOrderItemQuantityUpdated(eventEnvelope);
                }
            } else {
                break;
            }
        }
    }

    private BigInteger getOrderItemQuantityUpdatedEventNextCursor() {
        AbstractOrderEvent.OrderItemQuantityUpdated lastEvent = orderEventRepository.findFirstOrderItemQuantityUpdatedByOrderByAptosEventSequenceNumber();
        return lastEvent != null ? lastEvent.getAptosEventSequenceNumber() : null;
    }

    private void saveOrderItemQuantityUpdated(Event<OrderItemQuantityUpdated> eventEnvelope) {
        AbstractOrderEvent.OrderItemQuantityUpdated orderItemQuantityUpdated = DomainBeanUtils.toOrderItemQuantityUpdated(eventEnvelope);
        if (orderEventRepository.findById(orderItemQuantityUpdated.getOrderEventId()).isPresent()) {
            return;
        }
        orderEventRepository.save(orderItemQuantityUpdated);
    }

    @Transactional
    public void pullOrderEstimatedShipDateUpdatedEvents() {
        String resourceAccountAddress = getResourceAccountAddress();
        if (resourceAccountAddress == null) {
            return;
        }
        int limit = 1;
        BigInteger cursor = getOrderEstimatedShipDateUpdatedEventNextCursor();
        if (cursor == null) {
            cursor = BigInteger.ZERO;
        }
        while (true) {
            List<Event<OrderEstimatedShipDateUpdated>> eventPage;
            try {
                eventPage = aptosNodeApiClient.getEventsByEventHandle(
                        resourceAccountAddress,
                        this.aptosContractAddress + "::" + ContractConstants.ORDER_MODULE_EVENTS,
                        ContractConstants.ORDER_MODULE_ORDER_ESTIMATED_SHIP_DATE_UPDATED_HANDLE_FIELD,
                        OrderEstimatedShipDateUpdated.class,
                        cursor.longValue(),
                        limit
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (eventPage != null && eventPage.size() > 0) {
                cursor = cursor.add(BigInteger.ONE);
                for (Event<OrderEstimatedShipDateUpdated> eventEnvelope : eventPage) {
                    saveOrderEstimatedShipDateUpdated(eventEnvelope);
                }
            } else {
                break;
            }
        }
    }

    private BigInteger getOrderEstimatedShipDateUpdatedEventNextCursor() {
        AbstractOrderEvent.OrderEstimatedShipDateUpdated lastEvent = orderEventRepository.findFirstOrderEstimatedShipDateUpdatedByOrderByAptosEventSequenceNumber();
        return lastEvent != null ? lastEvent.getAptosEventSequenceNumber() : null;
    }

    private void saveOrderEstimatedShipDateUpdated(Event<OrderEstimatedShipDateUpdated> eventEnvelope) {
        AbstractOrderEvent.OrderEstimatedShipDateUpdated orderEstimatedShipDateUpdated = DomainBeanUtils.toOrderEstimatedShipDateUpdated(eventEnvelope);
        if (orderEventRepository.findById(orderEstimatedShipDateUpdated.getOrderEventId()).isPresent()) {
            return;
        }
        orderEventRepository.save(orderEstimatedShipDateUpdated);
    }

    @Transactional
    public void pullOrderShipGroupAddedEvents() {
        String resourceAccountAddress = getResourceAccountAddress();
        if (resourceAccountAddress == null) {
            return;
        }
        int limit = 1;
        BigInteger cursor = getOrderShipGroupAddedEventNextCursor();
        if (cursor == null) {
            cursor = BigInteger.ZERO;
        }
        while (true) {
            List<Event<OrderShipGroupAdded>> eventPage;
            try {
                eventPage = aptosNodeApiClient.getEventsByEventHandle(
                        resourceAccountAddress,
                        this.aptosContractAddress + "::" + ContractConstants.ORDER_MODULE_EVENTS,
                        ContractConstants.ORDER_MODULE_ORDER_SHIP_GROUP_ADDED_HANDLE_FIELD,
                        OrderShipGroupAdded.class,
                        cursor.longValue(),
                        limit
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (eventPage != null && eventPage.size() > 0) {
                cursor = cursor.add(BigInteger.ONE);
                for (Event<OrderShipGroupAdded> eventEnvelope : eventPage) {
                    saveOrderShipGroupAdded(eventEnvelope);
                }
            } else {
                break;
            }
        }
    }

    private BigInteger getOrderShipGroupAddedEventNextCursor() {
        AbstractOrderEvent.OrderShipGroupAdded lastEvent = orderEventRepository.findFirstOrderShipGroupAddedByOrderByAptosEventSequenceNumber();
        return lastEvent != null ? lastEvent.getAptosEventSequenceNumber() : null;
    }

    private void saveOrderShipGroupAdded(Event<OrderShipGroupAdded> eventEnvelope) {
        AbstractOrderEvent.OrderShipGroupAdded orderShipGroupAdded = DomainBeanUtils.toOrderShipGroupAdded(eventEnvelope);
        if (orderEventRepository.findById(orderShipGroupAdded.getOrderEventId()).isPresent()) {
            return;
        }
        orderEventRepository.save(orderShipGroupAdded);
    }

    @Transactional
    public void pullOrderShipGroupQuantityCanceledEvents() {
        String resourceAccountAddress = getResourceAccountAddress();
        if (resourceAccountAddress == null) {
            return;
        }
        int limit = 1;
        BigInteger cursor = getOrderShipGroupQuantityCanceledEventNextCursor();
        if (cursor == null) {
            cursor = BigInteger.ZERO;
        }
        while (true) {
            List<Event<OrderShipGroupQuantityCanceled>> eventPage;
            try {
                eventPage = aptosNodeApiClient.getEventsByEventHandle(
                        resourceAccountAddress,
                        this.aptosContractAddress + "::" + ContractConstants.ORDER_MODULE_EVENTS,
                        ContractConstants.ORDER_MODULE_ORDER_SHIP_GROUP_QUANTITY_CANCELED_HANDLE_FIELD,
                        OrderShipGroupQuantityCanceled.class,
                        cursor.longValue(),
                        limit
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (eventPage != null && eventPage.size() > 0) {
                cursor = cursor.add(BigInteger.ONE);
                for (Event<OrderShipGroupQuantityCanceled> eventEnvelope : eventPage) {
                    saveOrderShipGroupQuantityCanceled(eventEnvelope);
                }
            } else {
                break;
            }
        }
    }

    private BigInteger getOrderShipGroupQuantityCanceledEventNextCursor() {
        AbstractOrderEvent.OrderShipGroupQuantityCanceled lastEvent = orderEventRepository.findFirstOrderShipGroupQuantityCanceledByOrderByAptosEventSequenceNumber();
        return lastEvent != null ? lastEvent.getAptosEventSequenceNumber() : null;
    }

    private void saveOrderShipGroupQuantityCanceled(Event<OrderShipGroupQuantityCanceled> eventEnvelope) {
        AbstractOrderEvent.OrderShipGroupQuantityCanceled orderShipGroupQuantityCanceled = DomainBeanUtils.toOrderShipGroupQuantityCanceled(eventEnvelope);
        if (orderEventRepository.findById(orderShipGroupQuantityCanceled.getOrderEventId()).isPresent()) {
            return;
        }
        orderEventRepository.save(orderShipGroupQuantityCanceled);
    }

    @Transactional
    public void pullOrderShipGroupItemRemovedEvents() {
        String resourceAccountAddress = getResourceAccountAddress();
        if (resourceAccountAddress == null) {
            return;
        }
        int limit = 1;
        BigInteger cursor = getOrderShipGroupItemRemovedEventNextCursor();
        if (cursor == null) {
            cursor = BigInteger.ZERO;
        }
        while (true) {
            List<Event<OrderShipGroupItemRemoved>> eventPage;
            try {
                eventPage = aptosNodeApiClient.getEventsByEventHandle(
                        resourceAccountAddress,
                        this.aptosContractAddress + "::" + ContractConstants.ORDER_MODULE_EVENTS,
                        ContractConstants.ORDER_MODULE_ORDER_SHIP_GROUP_ITEM_REMOVED_HANDLE_FIELD,
                        OrderShipGroupItemRemoved.class,
                        cursor.longValue(),
                        limit
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (eventPage != null && eventPage.size() > 0) {
                cursor = cursor.add(BigInteger.ONE);
                for (Event<OrderShipGroupItemRemoved> eventEnvelope : eventPage) {
                    saveOrderShipGroupItemRemoved(eventEnvelope);
                }
            } else {
                break;
            }
        }
    }

    private BigInteger getOrderShipGroupItemRemovedEventNextCursor() {
        AbstractOrderEvent.OrderShipGroupItemRemoved lastEvent = orderEventRepository.findFirstOrderShipGroupItemRemovedByOrderByAptosEventSequenceNumber();
        return lastEvent != null ? lastEvent.getAptosEventSequenceNumber() : null;
    }

    private void saveOrderShipGroupItemRemoved(Event<OrderShipGroupItemRemoved> eventEnvelope) {
        AbstractOrderEvent.OrderShipGroupItemRemoved orderShipGroupItemRemoved = DomainBeanUtils.toOrderShipGroupItemRemoved(eventEnvelope);
        if (orderEventRepository.findById(orderShipGroupItemRemoved.getOrderEventId()).isPresent()) {
            return;
        }
        orderEventRepository.save(orderShipGroupItemRemoved);
    }

    @Transactional
    public void pullOrderShipGroupRemovedEvents() {
        String resourceAccountAddress = getResourceAccountAddress();
        if (resourceAccountAddress == null) {
            return;
        }
        int limit = 1;
        BigInteger cursor = getOrderShipGroupRemovedEventNextCursor();
        if (cursor == null) {
            cursor = BigInteger.ZERO;
        }
        while (true) {
            List<Event<OrderShipGroupRemoved>> eventPage;
            try {
                eventPage = aptosNodeApiClient.getEventsByEventHandle(
                        resourceAccountAddress,
                        this.aptosContractAddress + "::" + ContractConstants.ORDER_MODULE_EVENTS,
                        ContractConstants.ORDER_MODULE_ORDER_SHIP_GROUP_REMOVED_HANDLE_FIELD,
                        OrderShipGroupRemoved.class,
                        cursor.longValue(),
                        limit
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (eventPage != null && eventPage.size() > 0) {
                cursor = cursor.add(BigInteger.ONE);
                for (Event<OrderShipGroupRemoved> eventEnvelope : eventPage) {
                    saveOrderShipGroupRemoved(eventEnvelope);
                }
            } else {
                break;
            }
        }
    }

    private BigInteger getOrderShipGroupRemovedEventNextCursor() {
        AbstractOrderEvent.OrderShipGroupRemoved lastEvent = orderEventRepository.findFirstOrderShipGroupRemovedByOrderByAptosEventSequenceNumber();
        return lastEvent != null ? lastEvent.getAptosEventSequenceNumber() : null;
    }

    private void saveOrderShipGroupRemoved(Event<OrderShipGroupRemoved> eventEnvelope) {
        AbstractOrderEvent.OrderShipGroupRemoved orderShipGroupRemoved = DomainBeanUtils.toOrderShipGroupRemoved(eventEnvelope);
        if (orderEventRepository.findById(orderShipGroupRemoved.getOrderEventId()).isPresent()) {
            return;
        }
        orderEventRepository.save(orderShipGroupRemoved);
    }

    @Transactional
    public void pullOrderItemTableItemAddedEvents() {
        String resourceAccountAddress = getResourceAccountAddress();
        if (resourceAccountAddress == null) {
            return;
        }
        int limit = 1;
        BigInteger cursor = getOrderItemTableItemAddedEventNextCursor();
        if (cursor == null) {
            cursor = BigInteger.ZERO;
        }
        while (true) {
            List<Event<OrderItemTableItemAdded>> eventPage;
            try {
                eventPage = aptosNodeApiClient.getEventsByEventHandle(
                        resourceAccountAddress,
                        this.aptosContractAddress + "::" + ContractConstants.ORDER_MODULE_EVENTS,
                        ContractConstants.ORDER_ITEM_TABLE_ITEM_ADDED_HANDLE_FIELD,
                        OrderItemTableItemAdded.class,
                        cursor.longValue(),
                        limit
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (eventPage != null && eventPage.size() > 0) {
                cursor = cursor.add(BigInteger.ONE);
                for (Event<OrderItemTableItemAdded> eventEnvelope : eventPage) {
                    saveOrderItemTableItemAdded(eventEnvelope);
                }
            } else {
                break;
            }
        }
    }

    private BigInteger getOrderItemTableItemAddedEventNextCursor() {
        org.dddml.aptosdemocontracts.aptos.contract.persistence.OrderItemTableItemAdded lastEvent = orderItemTableItemAddedRepository.findFirstByOrderByAptosEventSequenceNumber();
        return lastEvent != null ? lastEvent.getAptosEventSequenceNumber() : null;
    }

    private void saveOrderItemTableItemAdded(Event<OrderItemTableItemAdded> eventEnvelope) {
        org.dddml.aptosdemocontracts.aptos.contract.persistence.OrderItemTableItemAdded orderItemTableItemAdded = DomainBeanUtils.toPersistenceOrderItemTableItemAdded(eventEnvelope);
        if (orderItemTableItemAddedRepository.findById(orderItemTableItemAdded.getOrderItemId()).isPresent()) {
            return;
        }
        orderItemTableItemAddedRepository.save(orderItemTableItemAdded);
    }

    @Transactional
    public void pullOrderShipGroupTableItemAddedEvents() {
        String resourceAccountAddress = getResourceAccountAddress();
        if (resourceAccountAddress == null) {
            return;
        }
        int limit = 1;
        BigInteger cursor = getOrderShipGroupTableItemAddedEventNextCursor();
        if (cursor == null) {
            cursor = BigInteger.ZERO;
        }
        while (true) {
            List<Event<OrderShipGroupTableItemAdded>> eventPage;
            try {
                eventPage = aptosNodeApiClient.getEventsByEventHandle(
                        resourceAccountAddress,
                        this.aptosContractAddress + "::" + ContractConstants.ORDER_MODULE_EVENTS,
                        ContractConstants.ORDER_SHIP_GROUP_TABLE_ITEM_ADDED_HANDLE_FIELD,
                        OrderShipGroupTableItemAdded.class,
                        cursor.longValue(),
                        limit
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (eventPage != null && eventPage.size() > 0) {
                cursor = cursor.add(BigInteger.ONE);
                for (Event<OrderShipGroupTableItemAdded> eventEnvelope : eventPage) {
                    saveOrderShipGroupTableItemAdded(eventEnvelope);
                }
            } else {
                break;
            }
        }
    }

    private BigInteger getOrderShipGroupTableItemAddedEventNextCursor() {
        org.dddml.aptosdemocontracts.aptos.contract.persistence.OrderShipGroupTableItemAdded lastEvent = orderShipGroupTableItemAddedRepository.findFirstByOrderByAptosEventSequenceNumber();
        return lastEvent != null ? lastEvent.getAptosEventSequenceNumber() : null;
    }

    private void saveOrderShipGroupTableItemAdded(Event<OrderShipGroupTableItemAdded> eventEnvelope) {
        org.dddml.aptosdemocontracts.aptos.contract.persistence.OrderShipGroupTableItemAdded orderShipGroupTableItemAdded = DomainBeanUtils.toPersistenceOrderShipGroupTableItemAdded(eventEnvelope);
        if (orderShipGroupTableItemAddedRepository.findById(orderShipGroupTableItemAdded.getOrderShipGroupId()).isPresent()) {
            return;
        }
        orderShipGroupTableItemAddedRepository.save(orderShipGroupTableItemAdded);
    }

    @Transactional
    public void pullOrderItemShipGroupAssociationTableItemAddedEvents() {
        String resourceAccountAddress = getResourceAccountAddress();
        if (resourceAccountAddress == null) {
            return;
        }
        int limit = 1;
        BigInteger cursor = getOrderItemShipGroupAssociationTableItemAddedEventNextCursor();
        if (cursor == null) {
            cursor = BigInteger.ZERO;
        }
        while (true) {
            List<Event<OrderItemShipGroupAssociationTableItemAdded>> eventPage;
            try {
                eventPage = aptosNodeApiClient.getEventsByEventHandle(
                        resourceAccountAddress,
                        this.aptosContractAddress + "::" + ContractConstants.ORDER_SHIP_GROUP_MODULE_EVENTS,
                        ContractConstants.ORDER_ITEM_SHIP_GROUP_ASSOCIATION_TABLE_ITEM_ADDED_HANDLE_FIELD,
                        OrderItemShipGroupAssociationTableItemAdded.class,
                        cursor.longValue(),
                        limit
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (eventPage != null && eventPage.size() > 0) {
                cursor = cursor.add(BigInteger.ONE);
                for (Event<OrderItemShipGroupAssociationTableItemAdded> eventEnvelope : eventPage) {
                    saveOrderItemShipGroupAssociationTableItemAdded(eventEnvelope);
                }
            } else {
                break;
            }
        }
    }

    private BigInteger getOrderItemShipGroupAssociationTableItemAddedEventNextCursor() {
        org.dddml.aptosdemocontracts.aptos.contract.persistence.OrderItemShipGroupAssociationTableItemAdded lastEvent = orderItemShipGroupAssociationTableItemAddedRepository.findFirstByOrderByAptosEventSequenceNumber();
        return lastEvent != null ? lastEvent.getAptosEventSequenceNumber() : null;
    }

    private void saveOrderItemShipGroupAssociationTableItemAdded(Event<OrderItemShipGroupAssociationTableItemAdded> eventEnvelope) {
        org.dddml.aptosdemocontracts.aptos.contract.persistence.OrderItemShipGroupAssociationTableItemAdded orderItemShipGroupAssociationTableItemAdded = DomainBeanUtils.toPersistenceOrderItemShipGroupAssociationTableItemAdded(eventEnvelope);
        if (orderItemShipGroupAssociationTableItemAddedRepository.findById(orderItemShipGroupAssociationTableItemAdded.getOrderItemShipGroupAssociationId()).isPresent()) {
            return;
        }
        orderItemShipGroupAssociationTableItemAddedRepository.save(orderItemShipGroupAssociationTableItemAdded);
    }

    @Transactional
    public void pullOrderItemShipGroupAssocSubitemTableItemAddedEvents() {
        String resourceAccountAddress = getResourceAccountAddress();
        if (resourceAccountAddress == null) {
            return;
        }
        int limit = 1;
        BigInteger cursor = getOrderItemShipGroupAssocSubitemTableItemAddedEventNextCursor();
        if (cursor == null) {
            cursor = BigInteger.ZERO;
        }
        while (true) {
            List<Event<OrderItemShipGroupAssocSubitemTableItemAdded>> eventPage;
            try {
                eventPage = aptosNodeApiClient.getEventsByEventHandle(
                        resourceAccountAddress,
                        this.aptosContractAddress + "::" + ContractConstants.ORDER_ITEM_SHIP_GROUP_ASSOCIATION_MODULE_EVENTS,
                        ContractConstants.ORDER_ITEM_SHIP_GROUP_ASSOC_SUBITEM_TABLE_ITEM_ADDED_HANDLE_FIELD,
                        OrderItemShipGroupAssocSubitemTableItemAdded.class,
                        cursor.longValue(),
                        limit
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (eventPage != null && eventPage.size() > 0) {
                cursor = cursor.add(BigInteger.ONE);
                for (Event<OrderItemShipGroupAssocSubitemTableItemAdded> eventEnvelope : eventPage) {
                    saveOrderItemShipGroupAssocSubitemTableItemAdded(eventEnvelope);
                }
            } else {
                break;
            }
        }
    }

    private BigInteger getOrderItemShipGroupAssocSubitemTableItemAddedEventNextCursor() {
        org.dddml.aptosdemocontracts.aptos.contract.persistence.OrderItemShipGroupAssocSubitemTableItemAdded lastEvent = orderItemShipGroupAssocSubitemTableItemAddedRepository.findFirstByOrderByAptosEventSequenceNumber();
        return lastEvent != null ? lastEvent.getAptosEventSequenceNumber() : null;
    }

    private void saveOrderItemShipGroupAssocSubitemTableItemAdded(Event<OrderItemShipGroupAssocSubitemTableItemAdded> eventEnvelope) {
        org.dddml.aptosdemocontracts.aptos.contract.persistence.OrderItemShipGroupAssocSubitemTableItemAdded orderItemShipGroupAssocSubitemTableItemAdded = DomainBeanUtils.toPersistenceOrderItemShipGroupAssocSubitemTableItemAdded(eventEnvelope);
        if (orderItemShipGroupAssocSubitemTableItemAddedRepository.findById(orderItemShipGroupAssocSubitemTableItemAdded.getOrderItemShipGroupAssocSubitemId()).isPresent()) {
            return;
        }
        orderItemShipGroupAssocSubitemTableItemAddedRepository.save(orderItemShipGroupAssocSubitemTableItemAdded);
    }

    private String getResourceAccountAddress() {
        return aptosAccountRepository.findById(ContractConstants.RESOURCE_ACCOUNT_ADDRESS)
                .map(AptosAccount::getAddress).orElse(null);
    }
}
