// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.rooch.contract.repository;

import org.dddml.roochdemocontracts.domain.order.OrderShipGroupId;
import org.dddml.roochdemocontracts.rooch.contract.persistence.OrderShipGroupTableItemAdded;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.math.*;
import java.util.List;

public interface OrderShipGroupTableItemAddedRepository extends JpaRepository<OrderShipGroupTableItemAdded, OrderShipGroupId> {

    @Transactional(readOnly = true)
    List<OrderShipGroupTableItemAdded> findByOrderByRoochEventId_EventSeqDesc(Pageable pageable);

    @Transactional(readOnly = true)
    List<OrderShipGroupTableItemAdded> findByRoochEventId_EventHandleIdOrderByRoochEventId_EventSeqDesc(String eventHandleId, Pageable pageable);

    @Transactional(readOnly = true)
    OrderShipGroupTableItemAdded findFirstByOrderByRoochEventId_EventSeqDesc();

    @Transactional(readOnly = true)
    List<OrderShipGroupTableItemAdded> findByOrderShipGroupId_OrderId(String orderId);

}
