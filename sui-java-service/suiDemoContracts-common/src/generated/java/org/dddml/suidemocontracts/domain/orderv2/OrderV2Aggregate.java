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

    void create(OrderV2Command.CreateOrderV2 c);

    void mergePatch(OrderV2Command.MergePatchOrderV2 c);

    void delete(OrderV2Command.DeleteOrderV2 c);

    void removeItem(String productId, Long version, String commandId, String requesterId, OrderV2Commands.RemoveItem c);

    void updateItemQuantity(String productId, BigInteger quantity, Long version, String commandId, String requesterId, OrderV2Commands.UpdateItemQuantity c);

    void updateEstimatedShipDate(Day estimatedShipDate, Long version, String commandId, String requesterId, OrderV2Commands.UpdateEstimatedShipDate c);

    void throwOnInvalidStateTransition(Command c);
}

