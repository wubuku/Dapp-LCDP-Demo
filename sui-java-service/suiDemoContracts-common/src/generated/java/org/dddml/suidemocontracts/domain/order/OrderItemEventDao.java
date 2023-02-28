package org.dddml.suidemocontracts.domain.order;

import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;

public interface OrderItemEventDao
{
    void save(OrderItemEvent e);

    Iterable<OrderItemEvent> findByOrderEventId(OrderEventId orderEventId);

}

