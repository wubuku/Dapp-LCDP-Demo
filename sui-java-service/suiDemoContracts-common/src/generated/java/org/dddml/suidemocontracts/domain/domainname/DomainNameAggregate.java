package org.dddml.suidemocontracts.domain.domainname;

import java.util.List;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.Event;
import org.dddml.suidemocontracts.domain.Command;

public interface DomainNameAggregate
{
    DomainNameState getState();

    List<Event> getChanges();

    void create(DomainNameCommand.CreateDomainName c);

    void mergePatch(DomainNameCommand.MergePatchDomainName c);

    void delete(DomainNameCommand.DeleteDomainName c);

    void register(BigInteger registrationPeriod, Long version, String commandId, String requesterId, DomainNameCommands.Register c);

    void renew(BigInteger renewPeriod, Long version, String commandId, String requesterId, DomainNameCommands.Renew c);

    void throwOnInvalidStateTransition(Command c);
}

