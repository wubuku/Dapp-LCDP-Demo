package org.dddml.suidemocontracts.domain.order;

import java.util.List;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.Event;
import org.dddml.suidemocontracts.domain.Command;

public interface OrderAggregate
{
    OrderState getState();

    List<Event> getChanges();

    void create(String product, BigInteger quantity, Long version, String commandId, String requesterId, OrderCommands.Create c);

    void removeItem(String productId, Long version, String commandId, String requesterId, OrderCommands.RemoveItem c);

    void updateItemQuantity(String productId, BigInteger quantity, Long version, String commandId, String requesterId, OrderCommands.UpdateItemQuantity c);

    void throwOnInvalidStateTransition(Command c);
}

