// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.suidemocontracts.domain.product;

import java.util.List;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.Event;
import org.dddml.suidemocontracts.domain.Command;

public interface ProductAggregate {
    ProductState getState();

    List<Event> getChanges();

    void create(String name, BigInteger unitPrice, String owner, Long offChainVersion, String commandId, String requesterId, ProductCommands.Create c);

    void update(String name, BigInteger unitPrice, String owner, Long offChainVersion, String commandId, String requesterId, ProductCommands.Update c);

    void delete(Long offChainVersion, String commandId, String requesterId, ProductCommands.Delete c);

    void throwOnInvalidStateTransition(Command c);
}

