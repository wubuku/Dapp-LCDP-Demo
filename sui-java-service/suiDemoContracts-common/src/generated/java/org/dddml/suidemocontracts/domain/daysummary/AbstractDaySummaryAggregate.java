package org.dddml.suidemocontracts.domain.daysummary;

import java.util.*;
import org.dddml.suidemocontracts.domain.*;
import java.util.Date;
import org.dddml.suidemocontracts.specialization.*;

public abstract class AbstractDaySummaryAggregate extends AbstractAggregate implements DaySummaryAggregate
{
    private DaySummaryState.MutableDaySummaryState state;

    private List<Event> changes = new ArrayList<Event>();

    public AbstractDaySummaryAggregate(DaySummaryState state) {
        this.state = (DaySummaryState.MutableDaySummaryState)state;
    }

    public DaySummaryState getState() {
        return this.state;
    }

    public List<Event> getChanges() {
        return this.changes;
    }

    public void create(DaySummaryCommand.CreateDaySummary c) {
        if (c.getVersion() == null) { c.setVersion(DaySummaryState.VERSION_NULL); }
        DaySummaryEvent e = map(c);
        apply(e);
    }

    public void mergePatch(DaySummaryCommand.MergePatchDaySummary c) {
        DaySummaryEvent e = map(c);
        apply(e);
    }

    public void delete(DaySummaryCommand.DeleteDaySummary c) {
        DaySummaryEvent e = map(c);
        apply(e);
    }

    public void throwOnInvalidStateTransition(Command c) {
        DaySummaryCommand.throwOnInvalidStateTransition(this.state, c);
    }

    protected void apply(Event e) {
        onApplying(e);
        state.mutate(e);
        changes.add(e);
    }

    protected DaySummaryEvent map(DaySummaryCommand.CreateDaySummary c) {
        DaySummaryEventId stateEventId = new DaySummaryEventId(c.getDay(), c.getVersion());
        DaySummaryEvent.DaySummaryStateCreated e = newDaySummaryStateCreated(stateEventId);
        e.setDescription(c.getDescription());
        e.setMetadata(c.getMetadata());
        ((AbstractDaySummaryEvent.AbstractDaySummaryStateEvent)e).setArrayData(c.getArrayData() == null ? null : java.util.Arrays.stream(c.getArrayData()).collect(java.util.stream.Collectors.toSet()));
        e.setOptionalData(c.getOptionalData());
        e.setActive(c.getActive());
        ((AbstractDaySummaryEvent)e).setCommandId(c.getCommandId());
        e.setCreatedBy(c.getRequesterId());
        e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));
        return e;
    }

    protected DaySummaryEvent map(DaySummaryCommand.MergePatchDaySummary c) {
        DaySummaryEventId stateEventId = new DaySummaryEventId(c.getDay(), c.getVersion());
        DaySummaryEvent.DaySummaryStateMergePatched e = newDaySummaryStateMergePatched(stateEventId);
        e.setDescription(c.getDescription());
        e.setMetadata(c.getMetadata());
        ((AbstractDaySummaryEvent.AbstractDaySummaryStateEvent)e).setArrayData(c.getArrayData() == null ? null : java.util.Arrays.stream(c.getArrayData()).collect(java.util.stream.Collectors.toSet()));
        e.setOptionalData(c.getOptionalData());
        e.setActive(c.getActive());
        e.setIsPropertyDescriptionRemoved(c.getIsPropertyDescriptionRemoved());
        e.setIsPropertyMetadataRemoved(c.getIsPropertyMetadataRemoved());
        e.setIsPropertyArrayDataRemoved(c.getIsPropertyArrayDataRemoved());
        e.setIsPropertyOptionalDataRemoved(c.getIsPropertyOptionalDataRemoved());
        e.setIsPropertyActiveRemoved(c.getIsPropertyActiveRemoved());
        ((AbstractDaySummaryEvent)e).setCommandId(c.getCommandId());
        e.setCreatedBy(c.getRequesterId());
        e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));
        return e;
    }

    protected DaySummaryEvent map(DaySummaryCommand.DeleteDaySummary c) {
        DaySummaryEventId stateEventId = new DaySummaryEventId(c.getDay(), c.getVersion());
        DaySummaryEvent.DaySummaryStateDeleted e = newDaySummaryStateDeleted(stateEventId);
        ((AbstractDaySummaryEvent)e).setCommandId(c.getCommandId());
        e.setCreatedBy(c.getRequesterId());
        e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));
        return e;
    }


    ////////////////////////

    protected DaySummaryEvent.DaySummaryStateCreated newDaySummaryStateCreated(Long version, String commandId, String requesterId) {
        DaySummaryEventId stateEventId = new DaySummaryEventId(this.state.getDay(), version);
        DaySummaryEvent.DaySummaryStateCreated e = newDaySummaryStateCreated(stateEventId);
        ((AbstractDaySummaryEvent)e).setCommandId(commandId);
        e.setCreatedBy(requesterId);
        e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));
        return e;
    }

    protected DaySummaryEvent.DaySummaryStateMergePatched newDaySummaryStateMergePatched(Long version, String commandId, String requesterId) {
        DaySummaryEventId stateEventId = new DaySummaryEventId(this.state.getDay(), version);
        DaySummaryEvent.DaySummaryStateMergePatched e = newDaySummaryStateMergePatched(stateEventId);
        ((AbstractDaySummaryEvent)e).setCommandId(commandId);
        e.setCreatedBy(requesterId);
        e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));
        return e;
    }

    protected DaySummaryEvent.DaySummaryStateDeleted newDaySummaryStateDeleted(Long version, String commandId, String requesterId) {
        DaySummaryEventId stateEventId = new DaySummaryEventId(this.state.getDay(), version);
        DaySummaryEvent.DaySummaryStateDeleted e = newDaySummaryStateDeleted(stateEventId);
        ((AbstractDaySummaryEvent)e).setCommandId(commandId);
        e.setCreatedBy(requesterId);
        e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));
        return e;
    }

    protected DaySummaryEvent.DaySummaryStateCreated newDaySummaryStateCreated(DaySummaryEventId stateEventId) {
        return new AbstractDaySummaryEvent.SimpleDaySummaryStateCreated(stateEventId);
    }

    protected DaySummaryEvent.DaySummaryStateMergePatched newDaySummaryStateMergePatched(DaySummaryEventId stateEventId) {
        return new AbstractDaySummaryEvent.SimpleDaySummaryStateMergePatched(stateEventId);
    }

    protected DaySummaryEvent.DaySummaryStateDeleted newDaySummaryStateDeleted(DaySummaryEventId stateEventId) {
        return new AbstractDaySummaryEvent.SimpleDaySummaryStateDeleted(stateEventId);
    }


    public static class SimpleDaySummaryAggregate extends AbstractDaySummaryAggregate {
        public SimpleDaySummaryAggregate(DaySummaryState state) {
            super(state);
        }

    }

}

