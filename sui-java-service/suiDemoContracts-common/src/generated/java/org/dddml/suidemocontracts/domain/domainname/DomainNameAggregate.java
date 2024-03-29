// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.suidemocontracts.domain.domainname;

import java.util.List;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.Event;
import org.dddml.suidemocontracts.domain.Command;

public interface DomainNameAggregate {
    DomainNameState getState();

    List<Event> getChanges();

    void register(BigInteger registrationPeriod, Long offChainVersion, String commandId, String requesterId, DomainNameCommands.Register c);

    void renew(BigInteger renewPeriod, Long offChainVersion, String commandId, String requesterId, DomainNameCommands.Renew c);

    void throwOnInvalidStateTransition(Command c);
}

