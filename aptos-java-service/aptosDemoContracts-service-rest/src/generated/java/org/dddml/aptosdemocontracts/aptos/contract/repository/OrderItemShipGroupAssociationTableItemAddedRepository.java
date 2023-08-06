// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.aptosdemocontracts.aptos.contract.repository;

import org.dddml.aptosdemocontracts.domain.order.OrderItemShipGroupAssociationId;
import org.dddml.aptosdemocontracts.aptos.contract.persistence.OrderItemShipGroupAssociationTableItemAdded;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

public interface OrderItemShipGroupAssociationTableItemAddedRepository extends JpaRepository<OrderItemShipGroupAssociationTableItemAdded, OrderItemShipGroupAssociationId> {

    @Transactional(readOnly = true)
    List<OrderItemShipGroupAssociationTableItemAdded> findByOrderBySequenceNumber(Pageable pageable);

    @Transactional(readOnly = true)
    OrderItemShipGroupAssociationTableItemAdded findFirstByOrderBySequenceNumber();

    @Transactional(readOnly = true)
    List<OrderItemShipGroupAssociationTableItemAdded> findByOrderItemShipGroupAssociationId_OrderIdAndOrderItemShipGroupAssociationId_OrderShipGroupShipGroupSeqId(String orderId, Integer orderShipGroupShipGroupSeqId);

}
