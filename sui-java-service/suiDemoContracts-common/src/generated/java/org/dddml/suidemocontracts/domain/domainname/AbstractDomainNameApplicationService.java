package org.dddml.suidemocontracts.domain.domainname;

import java.util.*;
import java.util.function.Consumer;
import org.dddml.support.criterion.Criterion;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.*;

public abstract class AbstractDomainNameApplicationService implements DomainNameApplicationService
{

    private EventStore eventStore;

    protected EventStore getEventStore()
    {
        return eventStore;
    }

    private DomainNameStateRepository stateRepository;

    protected DomainNameStateRepository getStateRepository() {
        return stateRepository;
    }

    private DomainNameStateQueryRepository stateQueryRepository;

    protected DomainNameStateQueryRepository getStateQueryRepository() {
        return stateQueryRepository;
    }

    private AggregateEventListener<DomainNameAggregate, DomainNameState> aggregateEventListener;

    public AggregateEventListener<DomainNameAggregate, DomainNameState> getAggregateEventListener() {
        return aggregateEventListener;
    }

    public void setAggregateEventListener(AggregateEventListener<DomainNameAggregate, DomainNameState> eventListener) {
        this.aggregateEventListener = eventListener;
    }

    public AbstractDomainNameApplicationService(EventStore eventStore, DomainNameStateRepository stateRepository, DomainNameStateQueryRepository stateQueryRepository) {
        this.eventStore = eventStore;
        this.stateRepository = stateRepository;
        this.stateQueryRepository = stateQueryRepository;
    }

    public void when(DomainNameCommand.CreateDomainName c) {
        update(c, ar -> ar.create(c));
    }

    public void when(DomainNameCommand.MergePatchDomainName c) {
        update(c, ar -> ar.mergePatch(c));
    }

    public void when(DomainNameCommand.DeleteDomainName c) {
        update(c, ar -> ar.delete(c));
    }

    public void when(DomainNameCommands.Register c) {
        update(c, ar -> ar.register(c.getRegistrationPeriod(), c.getVersion(), c.getCommandId(), c.getRequesterId(), c));
    }

    public void when(DomainNameCommands.Renew c) {
        update(c, ar -> ar.renew(c.getRenewPeriod(), c.getVersion(), c.getCommandId(), c.getRequesterId(), c));
    }

    public DomainNameState get(DomainNameId id) {
        DomainNameState state = getStateRepository().get(id, true);
        return state;
    }

    public Iterable<DomainNameState> getAll(Integer firstResult, Integer maxResults) {
        return getStateQueryRepository().getAll(firstResult, maxResults);
    }

    public Iterable<DomainNameState> get(Iterable<Map.Entry<String, Object>> filter, List<String> orders, Integer firstResult, Integer maxResults) {
        return getStateQueryRepository().get(filter, orders, firstResult, maxResults);
    }

    public Iterable<DomainNameState> get(Criterion filter, List<String> orders, Integer firstResult, Integer maxResults) {
        return getStateQueryRepository().get(filter, orders, firstResult, maxResults);
    }

    public Iterable<DomainNameState> getByProperty(String propertyName, Object propertyValue, List<String> orders, Integer firstResult, Integer maxResults) {
        return getStateQueryRepository().getByProperty(propertyName, propertyValue, orders, firstResult, maxResults);
    }

    public long getCount(Iterable<Map.Entry<String, Object>> filter) {
        return getStateQueryRepository().getCount(filter);
    }

    public long getCount(Criterion filter) {
        return getStateQueryRepository().getCount(filter);
    }

    public DomainNameEvent getEvent(DomainNameId domainNameId, long version) {
        DomainNameEvent e = (DomainNameEvent)getEventStore().getEvent(toEventStoreAggregateId(domainNameId), version);
        if (e != null) {
            ((DomainNameEvent.SqlDomainNameEvent)e).setEventReadOnly(true); 
        } else if (version == -1) {
            return getEvent(domainNameId, 0);
        }
        return e;
    }

    public DomainNameState getHistoryState(DomainNameId domainNameId, long version) {
        EventStream eventStream = getEventStore().loadEventStream(AbstractDomainNameEvent.class, toEventStoreAggregateId(domainNameId), version - 1);
        return new AbstractDomainNameState.SimpleDomainNameState(eventStream.getEvents());
    }


    public DomainNameAggregate getDomainNameAggregate(DomainNameState state) {
        return new AbstractDomainNameAggregate.SimpleDomainNameAggregate(state);
    }

    public EventStoreAggregateId toEventStoreAggregateId(DomainNameId aggregateId) {
        return new EventStoreAggregateId.SimpleEventStoreAggregateId(aggregateId);
    }

    protected void update(DomainNameCommand c, Consumer<DomainNameAggregate> action) {
        DomainNameId aggregateId = c.getDomainNameId();
        EventStoreAggregateId eventStoreAggregateId = toEventStoreAggregateId(aggregateId);
        DomainNameState state = getStateRepository().get(aggregateId, false);
        boolean duplicate = isDuplicateCommand(c, eventStoreAggregateId, state);
        if (duplicate) { return; }

        DomainNameAggregate aggregate = getDomainNameAggregate(state);
        aggregate.throwOnInvalidStateTransition(c);
        action.accept(aggregate);
        persist(eventStoreAggregateId, c.getVersion() == null ? DomainNameState.VERSION_NULL : c.getVersion(), aggregate, state); // State version may be null!

    }

    private void persist(EventStoreAggregateId eventStoreAggregateId, long version, DomainNameAggregate aggregate, DomainNameState state) {
        getEventStore().appendEvents(eventStoreAggregateId, version, 
            aggregate.getChanges(), (events) -> { 
                getStateRepository().save(state); 
            });
        if (aggregateEventListener != null) {
            aggregateEventListener.eventAppended(new AggregateEvent<>(aggregate, state, aggregate.getChanges()));
        }
    }

    public void initialize(DomainNameEvent.DomainNameStateCreated stateCreated) {
        DomainNameId aggregateId = ((DomainNameEvent.SqlDomainNameEvent)stateCreated).getDomainNameEventId().getDomainNameId();
        DomainNameState.SqlDomainNameState state = new AbstractDomainNameState.SimpleDomainNameState();
        state.setDomainNameId(aggregateId);

        DomainNameAggregate aggregate = getDomainNameAggregate(state);
        ((AbstractDomainNameAggregate) aggregate).apply(stateCreated);

        EventStoreAggregateId eventStoreAggregateId = toEventStoreAggregateId(aggregateId);
        persist(eventStoreAggregateId, ((DomainNameEvent.SqlDomainNameEvent)stateCreated).getDomainNameEventId().getVersion(), aggregate, state);
    }

    protected boolean isDuplicateCommand(DomainNameCommand command, EventStoreAggregateId eventStoreAggregateId, DomainNameState state) {
        boolean duplicate = false;
        if (command.getVersion() == null) { command.setVersion(DomainNameState.VERSION_NULL); }
        if (state.getVersion() != null && state.getVersion() > command.getVersion()) {
            Event lastEvent = getEventStore().getEvent(AbstractDomainNameEvent.class, eventStoreAggregateId, command.getVersion());
            if (lastEvent != null && lastEvent instanceof AbstractEvent
               && command.getCommandId() != null && command.getCommandId().equals(((AbstractEvent) lastEvent).getCommandId())) {
                duplicate = true;
            }
        }
        return duplicate;
    }

    public static class SimpleDomainNameApplicationService extends AbstractDomainNameApplicationService {
        public SimpleDomainNameApplicationService(EventStore eventStore, DomainNameStateRepository stateRepository, DomainNameStateQueryRepository stateQueryRepository)
        {
            super(eventStore, stateRepository, stateQueryRepository);
        }
    }

}

