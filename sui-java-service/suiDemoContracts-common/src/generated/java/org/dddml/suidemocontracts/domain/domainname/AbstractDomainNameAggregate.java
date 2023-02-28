package org.dddml.suidemocontracts.domain.domainname;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.*;

public abstract class AbstractDomainNameAggregate extends AbstractAggregate implements DomainNameAggregate
{
    private DomainNameState.MutableDomainNameState state;

    private List<Event> changes = new ArrayList<Event>();

    public AbstractDomainNameAggregate(DomainNameState state) {
        this.state = (DomainNameState.MutableDomainNameState)state;
    }

    public DomainNameState getState() {
        return this.state;
    }

    public List<Event> getChanges() {
        return this.changes;
    }

    public void create(DomainNameCommand.CreateDomainName c) {
        if (c.getVersion() == null) { c.setVersion(DomainNameState.VERSION_NULL); }
        DomainNameEvent e = map(c);
        apply(e);
    }

    public void mergePatch(DomainNameCommand.MergePatchDomainName c) {
        DomainNameEvent e = map(c);
        apply(e);
    }

    public void delete(DomainNameCommand.DeleteDomainName c) {
        DomainNameEvent e = map(c);
        apply(e);
    }

    public void throwOnInvalidStateTransition(Command c) {
        DomainNameCommand.throwOnInvalidStateTransition(this.state, c);
    }

    protected void apply(Event e) {
        onApplying(e);
        state.mutate(e);
        changes.add(e);
    }

    protected DomainNameEvent map(DomainNameCommand.CreateDomainName c) {
        DomainNameEventId stateEventId = new DomainNameEventId(c.getDomainNameId(), c.getVersion());
        DomainNameEvent.DomainNameStateCreated e = newDomainNameStateCreated(stateEventId);
        e.setExpirationDate(c.getExpirationDate());
        e.setActive(c.getActive());
        ((AbstractDomainNameEvent)e).setCommandId(c.getCommandId());
        e.setCreatedBy(c.getRequesterId());
        e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));
        return e;
    }

    protected DomainNameEvent map(DomainNameCommand.MergePatchDomainName c) {
        DomainNameEventId stateEventId = new DomainNameEventId(c.getDomainNameId(), c.getVersion());
        DomainNameEvent.DomainNameStateMergePatched e = newDomainNameStateMergePatched(stateEventId);
        e.setExpirationDate(c.getExpirationDate());
        e.setActive(c.getActive());
        e.setIsPropertyExpirationDateRemoved(c.getIsPropertyExpirationDateRemoved());
        e.setIsPropertyActiveRemoved(c.getIsPropertyActiveRemoved());
        ((AbstractDomainNameEvent)e).setCommandId(c.getCommandId());
        e.setCreatedBy(c.getRequesterId());
        e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));
        return e;
    }

    protected DomainNameEvent map(DomainNameCommand.DeleteDomainName c) {
        DomainNameEventId stateEventId = new DomainNameEventId(c.getDomainNameId(), c.getVersion());
        DomainNameEvent.DomainNameStateDeleted e = newDomainNameStateDeleted(stateEventId);
        ((AbstractDomainNameEvent)e).setCommandId(c.getCommandId());
        e.setCreatedBy(c.getRequesterId());
        e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));
        return e;
    }


    ////////////////////////

    protected DomainNameEvent.DomainNameStateCreated newDomainNameStateCreated(Long version, String commandId, String requesterId) {
        DomainNameEventId stateEventId = new DomainNameEventId(this.state.getDomainNameId(), version);
        DomainNameEvent.DomainNameStateCreated e = newDomainNameStateCreated(stateEventId);
        ((AbstractDomainNameEvent)e).setCommandId(commandId);
        e.setCreatedBy(requesterId);
        e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));
        return e;
    }

    protected DomainNameEvent.DomainNameStateMergePatched newDomainNameStateMergePatched(Long version, String commandId, String requesterId) {
        DomainNameEventId stateEventId = new DomainNameEventId(this.state.getDomainNameId(), version);
        DomainNameEvent.DomainNameStateMergePatched e = newDomainNameStateMergePatched(stateEventId);
        ((AbstractDomainNameEvent)e).setCommandId(commandId);
        e.setCreatedBy(requesterId);
        e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));
        return e;
    }

    protected DomainNameEvent.DomainNameStateDeleted newDomainNameStateDeleted(Long version, String commandId, String requesterId) {
        DomainNameEventId stateEventId = new DomainNameEventId(this.state.getDomainNameId(), version);
        DomainNameEvent.DomainNameStateDeleted e = newDomainNameStateDeleted(stateEventId);
        ((AbstractDomainNameEvent)e).setCommandId(commandId);
        e.setCreatedBy(requesterId);
        e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));
        return e;
    }

    protected DomainNameEvent.DomainNameStateCreated newDomainNameStateCreated(DomainNameEventId stateEventId) {
        return new AbstractDomainNameEvent.SimpleDomainNameStateCreated(stateEventId);
    }

    protected DomainNameEvent.DomainNameStateMergePatched newDomainNameStateMergePatched(DomainNameEventId stateEventId) {
        return new AbstractDomainNameEvent.SimpleDomainNameStateMergePatched(stateEventId);
    }

    protected DomainNameEvent.DomainNameStateDeleted newDomainNameStateDeleted(DomainNameEventId stateEventId) {
        return new AbstractDomainNameEvent.SimpleDomainNameStateDeleted(stateEventId);
    }


    public static class SimpleDomainNameAggregate extends AbstractDomainNameAggregate {
        public SimpleDomainNameAggregate(DomainNameState state) {
            super(state);
        }

        @Override
        public void register(BigInteger registrationPeriod, Long version, String commandId, String requesterId, DomainNameCommands.Register c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void renew(BigInteger renewPeriod, Long version, String commandId, String requesterId, DomainNameCommands.Renew c) {
            throw new UnsupportedOperationException();
        }

    }

}

