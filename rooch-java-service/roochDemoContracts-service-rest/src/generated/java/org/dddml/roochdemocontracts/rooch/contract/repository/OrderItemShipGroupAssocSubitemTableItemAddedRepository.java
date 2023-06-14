package org.dddml.roochdemocontracts.rooch.contract.repository;

import org.dddml.roochdemocontracts.domain.order.OrderItemShipGroupAssocSubitemId;
import org.dddml.roochdemocontracts.rooch.contract.persistence.OrderItemShipGroupAssocSubitemTableItemAdded;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderItemShipGroupAssocSubitemTableItemAddedRepository extends JpaRepository<OrderItemShipGroupAssocSubitemTableItemAdded, OrderItemShipGroupAssocSubitemId> {

    @Transactional(readOnly = true)
    List<OrderItemShipGroupAssocSubitemTableItemAdded> findAllByOrderByRoochEventId_EventSeqDesc(Pageable pageable);

    @Transactional
    List<OrderItemShipGroupAssocSubitemTableItemAdded> findAllByRoochEventId_EventHandleIdOrderByRoochEventId_EventSeqDesc(String eventHandleId, Pageable pageable);
}
