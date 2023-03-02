package org.dddml.suidemocontracts.domain.daysummary;

import java.util.*;
import java.util.function.Consumer;
import org.dddml.support.criterion.Criterion;
import org.dddml.suidemocontracts.domain.*;
import java.util.Date;
import org.dddml.suidemocontracts.specialization.*;

public abstract class AbstractDaySummaryApplicationService implements DaySummaryApplicationService
{

    private EventStore eventStore;

    protected EventStore getEventStore()
    {
        return eventStore;
    }

    private DaySummaryStateRepository stateRepository;

    protected DaySummaryStateRepository getStateRepository() {
        return stateRepository;
    }

    private DaySummaryStateQueryRepository stateQueryRepository;

    protected DaySummaryStateQueryRepository getStateQueryRepository() {
        return stateQueryRepository;
    }

    private AggregateEventListener<DaySummaryAggregate, DaySummaryState> aggregateEventListener;

    public AggregateEventListener<DaySummaryAggregate, DaySummaryState> getAggregateEventListener() {
        return aggregateEventListener;
    }

    public void setAggregateEventListener(AggregateEventListener<DaySummaryAggregate, DaySummaryState> eventListener) {
        this.aggregateEventListener = eventListener;
    }

    public AbstractDaySummaryApplicationService(EventStore eventStore, DaySummaryStateRepository stateRepository, DaySummaryStateQueryRepository stateQueryRepository) {
        this.eventStore = eventStore;
        this.stateRepository = stateRepository;
        this.stateQueryRepository = stateQueryRepository;
    }

    public void when(DaySummaryCommands.Create c) {
        update(c, ar -> ar.create(c.getDescription(), c.getMetaData(), c.getArrayData(), c.getOptionalData(), c.getVersion(), c.getCommandId(), c.getRequesterId(), c));
    }

    public DaySummaryState get(Day id) {
        DaySummaryState state = getStateRepository().get(id, true);
        return state;
    }

    public Iterable<DaySummaryState> getAll(Integer firstResult, Integer maxResults) {
        return getStateQueryRepository().getAll(firstResult, maxResults);
    }

    public Iterable<DaySummaryState> get(Iterable<Map.Entry<String, Object>> filter, List<String> orders, Integer firstResult, Integer maxResults) {
        return getStateQueryRepository().get(filter, orders, firstResult, maxResults);
    }

    public Iterable<DaySummaryState> get(Criterion filter, List<String> orders, Integer firstResult, Integer maxResults) {
        return getStateQueryRepository().get(filter, orders, firstResult, maxResults);
    }

    public Iterable<DaySummaryState> getByProperty(String propertyName, Object propertyValue, List<String> orders, Integer firstResult, Integer maxResults) {
        return getStateQueryRepository().getByProperty(propertyName, propertyValue, orders, firstResult, maxResults);
    }

    public long getCount(Iterable<Map.Entry<String, Object>> filter) {
        return getStateQueryRepository().getCount(filter);
    }

    public long getCount(Criterion filter) {
        return getStateQueryRepository().getCount(filter);
    }

    public DaySummaryEvent getEvent(Day day, long version) {
        DaySummaryEvent e = (DaySummaryEvent)getEventStore().getEvent(toEventStoreAggregateId(day), version);
        if (e != null) {
            ((DaySummaryEvent.SqlDaySummaryEvent)e).setEventReadOnly(true); 
        } else if (version == -1) {
            return getEvent(day, 0);
        }
        return e;
    }

    public DaySummaryState getHistoryState(Day day, long version) {
        EventStream eventStream = getEventStore().loadEventStream(AbstractDaySummaryEvent.class, toEventStoreAggregateId(day), version - 1);
        return new AbstractDaySummaryState.SimpleDaySummaryState(eventStream.getEvents());
    }


    public DaySummaryAggregate getDaySummaryAggregate(DaySummaryState state) {
        return new AbstractDaySummaryAggregate.SimpleDaySummaryAggregate(state);
    }

    public EventStoreAggregateId toEventStoreAggregateId(Day aggregateId) {
        return new EventStoreAggregateId.SimpleEventStoreAggregateId(aggregateId);
    }

    protected void update(DaySummaryCommand c, Consumer<DaySummaryAggregate> action) {
        Day aggregateId = c.getDay();
        EventStoreAggregateId eventStoreAggregateId = toEventStoreAggregateId(aggregateId);
        DaySummaryState state = getStateRepository().get(aggregateId, false);
        boolean duplicate = isDuplicateCommand(c, eventStoreAggregateId, state);
        if (duplicate) { return; }

        DaySummaryAggregate aggregate = getDaySummaryAggregate(state);
        aggregate.throwOnInvalidStateTransition(c);
        action.accept(aggregate);
        persist(eventStoreAggregateId, c.getVersion() == null ? DaySummaryState.VERSION_NULL : c.getVersion(), aggregate, state); // State version may be null!

    }

    private void persist(EventStoreAggregateId eventStoreAggregateId, long version, DaySummaryAggregate aggregate, DaySummaryState state) {
        getEventStore().appendEvents(eventStoreAggregateId, version, 
            aggregate.getChanges(), (events) -> { 
                getStateRepository().save(state); 
            });
        if (aggregateEventListener != null) {
            aggregateEventListener.eventAppended(new AggregateEvent<>(aggregate, state, aggregate.getChanges()));
        }
    }

    protected boolean isDuplicateCommand(DaySummaryCommand command, EventStoreAggregateId eventStoreAggregateId, DaySummaryState state) {
        boolean duplicate = false;
        if (command.getVersion() == null) { command.setVersion(DaySummaryState.VERSION_NULL); }
        if (state.getVersion() != null && state.getVersion() > command.getVersion()) {
            Event lastEvent = getEventStore().getEvent(AbstractDaySummaryEvent.class, eventStoreAggregateId, command.getVersion());
            if (lastEvent != null && lastEvent instanceof AbstractEvent
               && command.getCommandId() != null && command.getCommandId().equals(((AbstractEvent) lastEvent).getCommandId())) {
                duplicate = true;
            }
        }
        return duplicate;
    }

    public static class SimpleDaySummaryApplicationService extends AbstractDaySummaryApplicationService {
        public SimpleDaySummaryApplicationService(EventStore eventStore, DaySummaryStateRepository stateRepository, DaySummaryStateQueryRepository stateQueryRepository)
        {
            super(eventStore, stateRepository, stateQueryRepository);
        }
    }

}

