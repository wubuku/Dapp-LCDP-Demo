package org.dddml.suidemocontracts.domain.orderv2;

import java.util.List;
import java.math.BigInteger;
import org.dddml.suidemocontracts.domain.*;
import java.util.Date;
import org.dddml.suidemocontracts.specialization.Event;
import org.dddml.suidemocontracts.domain.Command;

public interface OrderV2Aggregate
{
    OrderV2State getState();

    List<Event> getChanges();

    void create(String product, BigInteger quantity, Long offChainVersion, String commandId, String requesterId, OrderV2Commands.Create c);

    void removeItem(String productId, Long offChainVersion, String commandId, String requesterId, OrderV2Commands.RemoveItem c);

    void updateItemQuantity(String productId, BigInteger quantity, Long offChainVersion, String commandId, String requesterId, OrderV2Commands.UpdateItemQuantity c);

    void updateEstimatedShipDate(Day estimatedShipDate, Long offChainVersion, String commandId, String requesterId, OrderV2Commands.UpdateEstimatedShipDate c);

    void throwOnInvalidStateTransition(Command c);
}

