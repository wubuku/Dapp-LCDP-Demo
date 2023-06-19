// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.rooch.contract.repository;

import org.dddml.roochdemocontracts.domain.order.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface OrderEventRepository extends JpaRepository<AbstractOrderEvent, OrderEventId> {

    List<AbstractOrderEvent> findByStatusIsNull();

    AbstractOrderEvent.OrderCreated findFirstOrderCreatedByOrderByRoochEventId_EventSeqDesc();

    AbstractOrderEvent.OrderItemRemoved findFirstOrderItemRemovedByOrderByRoochEventId_EventSeqDesc();

    AbstractOrderEvent.OrderItemQuantityUpdated findFirstOrderItemQuantityUpdatedByOrderByRoochEventId_EventSeqDesc();

    AbstractOrderEvent.OrderEstimatedShipDateUpdated findFirstOrderEstimatedShipDateUpdatedByOrderByRoochEventId_EventSeqDesc();

    AbstractOrderEvent.OrderShipGroupAdded findFirstOrderShipGroupAddedByOrderByRoochEventId_EventSeqDesc();

    AbstractOrderEvent.OrderItemShipGroupAssocSubitemAdded findFirstOrderItemShipGroupAssocSubitemAddedByOrderByRoochEventId_EventSeqDesc();

    AbstractOrderEvent.OrderShipGroupQuantityCanceled findFirstOrderShipGroupQuantityCanceledByOrderByRoochEventId_EventSeqDesc();

    AbstractOrderEvent.OrderShipGroupItemRemoved findFirstOrderShipGroupItemRemovedByOrderByRoochEventId_EventSeqDesc();

}
