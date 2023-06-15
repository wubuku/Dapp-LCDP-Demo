package org.dddml.roochdemocontracts.rooch.contract.service;

import com.github.wubuku.rooch.bean.AnnotatedEventView;
import com.github.wubuku.rooch.utils.RoochJsonRpcClient;
import org.dddml.roochdemocontracts.rooch.contract.DomainBeanUtils;
import org.dddml.roochdemocontracts.rooch.contract.OrderItemShipGroupAssocSubitemTableItemAdded;
import org.dddml.roochdemocontracts.rooch.contract.OrderItemShipGroupAssociationTableItemAdded;
import org.dddml.roochdemocontracts.rooch.contract.repository.OrderItemShipGroupAssocSubitemTableItemAddedRepository;
import org.dddml.roochdemocontracts.rooch.contract.repository.OrderItemShipGroupAssociationTableItemAddedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.dddml.roochdemocontracts.rooch.contract.ContractConstants;

import java.math.BigInteger;
import java.util.List;

@Service
public class RoochOrderService {
    @Value("${rooch.contract.address}")
    private String contractAddress;

    @Autowired
    private RoochJsonRpcClient roochJsonRpcClient;

    @Autowired
    private OrderItemShipGroupAssociationTableItemAddedRepository orderItemShipGroupAssociationTableItemAddedRepository;

    @Autowired
    private OrderItemShipGroupAssocSubitemTableItemAddedRepository orderItemShipGroupAssocSubitemTableItemAddedRepository;


    @Transactional
    public void pullOrderItemShipGroupAssociationTableItemAddedEvents() {
        if (contractAddress == null) {
            return;
        }
        String eventType = contractAddress + "::" + ContractConstants.ORDER_ITEM_SHIP_GROUP_ASSOCIATION_TABLE_ITEM_ADDED;
        BigInteger cursor = getOrderItemShipGroupAssociationTableItemAddedEventNextCursor();
        long limit = 1L;
        while (true) {
            List<AnnotatedEventView<OrderItemShipGroupAssociationTableItemAdded>> eventPage = roochJsonRpcClient.getEventsByEventHandle(
                    eventType, cursor, limit, OrderItemShipGroupAssociationTableItemAdded.class
            );
            if (eventPage != null && !eventPage.isEmpty()) {
                // //////////////////////
                BigInteger nextCursor = eventPage.get(0).getEvent().getEventId().getEventSeq();
                if (cursor != null && nextCursor.compareTo(cursor) == 0) {
                    System.out.println("nextCursor == cursor, exit");
                    break; // todo should not go here?
                }
                cursor = nextCursor;
                // /////////////////////
                //todo cursor = eventPage.getNextCursor();
                for (AnnotatedEventView<OrderItemShipGroupAssociationTableItemAdded> eventEnvelope : eventPage) {
                    saveOrderItemShipGroupAssociationTableItemAdded(eventEnvelope);
                }
            } else {
                break;
            }
            if (eventPage == null || eventPage.size() == 0) { //todo !Page.hasNextPage(eventPage)
                break;
            }
        }
    }

    private void saveOrderItemShipGroupAssociationTableItemAdded(AnnotatedEventView<OrderItemShipGroupAssociationTableItemAdded> eventEnvelope) {
        org.dddml.roochdemocontracts.rooch.contract.persistence.OrderItemShipGroupAssociationTableItemAdded e = DomainBeanUtils.toPersistenceOrderItemShipGroupAssociationTableItemAdded(eventEnvelope);
        if (orderItemShipGroupAssociationTableItemAddedRepository.findById(e.getOrderItemShipGroupAssociationId()).isPresent()) {
            return;
        }
        orderItemShipGroupAssociationTableItemAddedRepository.save(e);
    }


    private BigInteger getOrderItemShipGroupAssociationTableItemAddedEventNextCursor() {
        org.dddml.roochdemocontracts.rooch.contract.persistence.OrderItemShipGroupAssociationTableItemAdded lastEvent = orderItemShipGroupAssociationTableItemAddedRepository.findFirstByOrderByRoochEventId_EventSeqDesc();
        return lastEvent == null ? null : lastEvent.getRoochEventId().getEventSeq();
    }

    @Transactional
    public void pullOrderItemShipGroupAssocSubitemTableItemAddedEvents() {
        if (contractAddress == null) {
            return;
        }
        String eventType = contractAddress + "::" + ContractConstants.ORDER_ITEM_SHIP_GROUP_ASSOC_SUBITEM_TABLE_ITEM_ADDED;
        BigInteger cursor = getOrderItemShipGroupAssocSubitemTableItemAddedEventNextCursor();
        long limit = 1L;
        while (true) {
            List<AnnotatedEventView<OrderItemShipGroupAssocSubitemTableItemAdded>> eventPage = roochJsonRpcClient.getEventsByEventHandle(
                    eventType, cursor, limit, OrderItemShipGroupAssocSubitemTableItemAdded.class
            );
            if (eventPage != null && !eventPage.isEmpty()) {
                cursor = eventPage.get(0).getEvent().getEventId().getEventSeq();
                for (AnnotatedEventView<OrderItemShipGroupAssocSubitemTableItemAdded> eventEnvelope : eventPage) {
                    saveOrderItemShipGroupAssocSubitemTableItemAdded(eventEnvelope);
                }
            } else {
                break;
            }
            if (eventPage == null || eventPage.size() == 0) { //!Page.hasNextPage(eventPage)
                break;
            }
        }
    }

    private void saveOrderItemShipGroupAssocSubitemTableItemAdded(AnnotatedEventView<OrderItemShipGroupAssocSubitemTableItemAdded> eventEnvelope) {
        org.dddml.roochdemocontracts.rooch.contract.persistence.OrderItemShipGroupAssocSubitemTableItemAdded e = DomainBeanUtils.toPersistenceOrderItemShipGroupAssocSubitemTableItemAdded(eventEnvelope);
        if (orderItemShipGroupAssocSubitemTableItemAddedRepository.findById(e.getOrderItemShipGroupAssocSubitemId()).isPresent()) {
            return;
        }
        orderItemShipGroupAssocSubitemTableItemAddedRepository.save(e);
    }

    private BigInteger getOrderItemShipGroupAssocSubitemTableItemAddedEventNextCursor() {
        org.dddml.roochdemocontracts.rooch.contract.persistence.OrderItemShipGroupAssocSubitemTableItemAdded lastEvent = orderItemShipGroupAssocSubitemTableItemAddedRepository.findFirstByOrderByRoochEventId_EventSeqDesc();
        return lastEvent == null  ? null : lastEvent.getRoochEventId().getEventSeq();
    }
}
