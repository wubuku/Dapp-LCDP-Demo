package org.dddml.roochdemocontracts.rooch.contract.service;

import com.github.wubuku.rooch.bean.AnnotatedEventView;
import com.github.wubuku.rooch.utils.RoochJsonRpcClient;
import org.dddml.roochdemocontracts.rooch.contract.DomainBeanUtils;
import org.dddml.roochdemocontracts.rooch.contract.OrderItemShipGroupAssocSubitemTableItemAdded;
import org.dddml.roochdemocontracts.rooch.contract.OrderItemShipGroupAssociationTableItemAdded;
import org.dddml.roochdemocontracts.rooch.contract.repository.OrderItemShipGroupAssocSubitemTableItemAddedRepository;
import org.dddml.roochdemocontracts.rooch.contract.repository.OrderItemShipGroupAssociationTableItemAddedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Service
public class RoochOrderService {

    @Autowired
    private OrderItemShipGroupAssociationTableItemAddedRepository orderItemShipGroupAssociationTableItemAddedRepository;

    @Autowired
    private OrderItemShipGroupAssocSubitemTableItemAddedRepository orderItemShipGroupAssocSubitemTableItemAddedRepository;

    @Autowired
    private RoochJsonRpcClient roochJsonRpcClient;


    @Transactional
    public void pullOrderItemShipGroupAssociationTableItemAddedEvents() {
        String contractAddress = "0xf8e38d63a5208d499725e7ac4851c4a0836e45e2230041b7e3cf43e4738c47b4";//todo getDefaultContractAddress();
        if (contractAddress == null) {
            return;
        }
        String eventType = contractAddress + "::" + "order_ship_group::OrderItemShipGroupAssociationTableItemAdded";//todo contractAddress + "::" + ContractConstants.DAY_SUMMARY_MODULE_DAY_SUMMARY_CREATED;
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
        List<org.dddml.roochdemocontracts.rooch.contract.persistence.OrderItemShipGroupAssociationTableItemAdded> lastEvents = orderItemShipGroupAssociationTableItemAddedRepository.findByOrderByRoochEventId_EventSeqDesc(Pageable.ofSize(1));
        return lastEvents == null || lastEvents.size() == 0 ? null : lastEvents.get(0).getRoochEventId().getEventSeq();
    }

    @Transactional
    public void pullOrderItemShipGroupAssocSubitemTableItemAddedEvents() {
        String contractAddress = "0xf8e38d63a5208d499725e7ac4851c4a0836e45e2230041b7e3cf43e4738c47b4";//todo getDefaultContractAddress();
        if (contractAddress == null) {
            return;
        }
        String eventType = contractAddress + "::" + "order_item_ship_group_association::OrderItemShipGroupAssocSubitemTableItemAdded";//todo contractAddress + "::" + ContractConstants.DAY_SUMMARY_MODULE_DAY_SUMMARY_CREATED;
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
        List<org.dddml.roochdemocontracts.rooch.contract.persistence.OrderItemShipGroupAssocSubitemTableItemAdded> lastEvents = orderItemShipGroupAssocSubitemTableItemAddedRepository.findByOrderByRoochEventId_EventSeqDesc(Pageable.ofSize(1));
        return lastEvents == null || lastEvents.size() == 0 ? null : lastEvents.get(0).getRoochEventId().getEventSeq();
    }
}
