package org.dddml.suidemocontracts.domain.orderv2;

import java.util.Date;
import org.dddml.suidemocontracts.domain.*;

public interface OrderItemShipGroupAssocSubitemEventDao
{
    void save(OrderItemShipGroupAssocSubitemEvent e);

    Iterable<OrderItemShipGroupAssocSubitemEvent> findByOrderItemShipGroupAssociationEventId(OrderItemShipGroupAssociationEventId orderItemShipGroupAssociationEventId);

}

