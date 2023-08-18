// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.domain.product;

import java.util.List;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.roochdemocontracts.domain.*;
import org.dddml.roochdemocontracts.specialization.Event;
import org.dddml.roochdemocontracts.domain.Command;

public interface ProductAggregate {
    ProductState getState();

    List<Event> getChanges();

    void create(String name, BigInteger unitPrice, Long offChainVersion, String commandId, String requesterId, ProductCommands.Create c);

    void update(String name, BigInteger unitPrice, Long offChainVersion, String commandId, String requesterId, ProductCommands.Update c);

    void delete(Long offChainVersion, String commandId, String requesterId, ProductCommands.Delete c);

    void throwOnInvalidStateTransition(Command c);
}

