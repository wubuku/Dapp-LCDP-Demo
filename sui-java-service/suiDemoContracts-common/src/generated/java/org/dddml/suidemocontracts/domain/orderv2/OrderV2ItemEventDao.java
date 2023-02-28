package org.dddml.suidemocontracts.domain.orderv2;

import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;

public interface OrderV2ItemEventDao
{
    void save(OrderV2ItemEvent e);

    Iterable<OrderV2ItemEvent> findByOrderV2EventId(OrderV2EventId orderV2EventId);

}

