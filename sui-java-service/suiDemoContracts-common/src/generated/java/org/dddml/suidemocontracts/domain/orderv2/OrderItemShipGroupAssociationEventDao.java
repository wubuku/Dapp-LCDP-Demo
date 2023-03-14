package org.dddml.suidemocontracts.domain.orderv2;

import java.util.Date;
import org.dddml.suidemocontracts.domain.*;

public interface OrderItemShipGroupAssociationEventDao
{
    void save(OrderItemShipGroupAssociationEvent e);

    Iterable<OrderItemShipGroupAssociationEvent> findByOrderShipGroupEventId(OrderShipGroupEventId orderShipGroupEventId);

}

