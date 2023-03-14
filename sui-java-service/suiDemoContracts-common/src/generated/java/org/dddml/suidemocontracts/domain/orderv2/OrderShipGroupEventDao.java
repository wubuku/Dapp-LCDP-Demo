package org.dddml.suidemocontracts.domain.orderv2;

import java.util.Date;
import org.dddml.suidemocontracts.domain.*;

public interface OrderShipGroupEventDao
{
    void save(OrderShipGroupEvent e);

    Iterable<OrderShipGroupEvent> findByOrderV2EventId(OrderV2EventId orderV2EventId);

}

