package org.dddml.suidemocontracts.domain.product;

import java.util.List;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.Event;
import org.dddml.suidemocontracts.domain.Command;

public interface ProductAggregate
{
    ProductState getState();

    List<Event> getChanges();

    void create(String name, BigInteger unitPrice, Long offChainVersion, String commandId, String requesterId, ProductCommands.Create c);

    void throwOnInvalidStateTransition(Command c);
}

