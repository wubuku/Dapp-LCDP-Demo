package org.dddml.suidemocontracts.domain.orderv2;

import org.dddml.suidemocontracts.domain.*;
import java.util.Date;

public interface OrderItemShipGroupAssocSubitemEventDao
{
    void save(OrderItemShipGroupAssocSubitemEvent e);

    Iterable<OrderItemShipGroupAssocSubitemEvent> findByOrderItemShipGroupAssociationEventId(OrderItemShipGroupAssociationEventId orderItemShipGroupAssociationEventId);

}

