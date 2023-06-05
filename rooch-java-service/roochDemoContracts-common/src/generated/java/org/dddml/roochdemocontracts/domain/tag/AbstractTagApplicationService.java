// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.domain.tag;

import java.util.*;
import java.util.function.Consumer;
import org.dddml.support.criterion.Criterion;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.roochdemocontracts.domain.*;
import org.dddml.roochdemocontracts.specialization.*;

public abstract class AbstractTagApplicationService implements TagApplicationService {

    private EventStore eventStore;

    protected EventStore getEventStore()
    {
        return eventStore;
    }

    private TagStateRepository stateRepository;

    protected TagStateRepository getStateRepository() {
        return stateRepository;
    }

    private TagStateQueryRepository stateQueryRepository;

    protected TagStateQueryRepository getStateQueryRepository() {
        return stateQueryRepository;
    }

    private AggregateEventListener<TagAggregate, TagState> aggregateEventListener;

    public AggregateEventListener<TagAggregate, TagState> getAggregateEventListener() {
        return aggregateEventListener;
    }

    public void setAggregateEventListener(AggregateEventListener<TagAggregate, TagState> eventListener) {
        this.aggregateEventListener = eventListener;
    }

    public AbstractTagApplicationService(EventStore eventStore, TagStateRepository stateRepository, TagStateQueryRepository stateQueryRepository) {
        this.eventStore = eventStore;
        this.stateRepository = stateRepository;
        this.stateQueryRepository = stateQueryRepository;
    }

    public void when(TagCommands.Create c) {
        update(c, ar -> ar.create(c.getOffChainVersion(), c.getCommandId(), c.getRequesterId(), c));
    }

    public TagState get(String id) {
        TagState state = getStateRepository().get(id, true);
        return state;
    }

    public Iterable<TagState> getAll(Integer firstResult, Integer maxResults) {
        return getStateQueryRepository().getAll(firstResult, maxResults);
    }

    public Iterable<TagState> get(Iterable<Map.Entry<String, Object>> filter, List<String> orders, Integer firstResult, Integer maxResults) {
        return getStateQueryRepository().get(filter, orders, firstResult, maxResults);
    }

    public Iterable<TagState> get(Criterion filter, List<String> orders, Integer firstResult, Integer maxResults) {
        return getStateQueryRepository().get(filter, orders, firstResult, maxResults);
    }

    public Iterable<TagState> getByProperty(String propertyName, Object propertyValue, List<String> orders, Integer firstResult, Integer maxResults) {
        return getStateQueryRepository().getByProperty(propertyName, propertyValue, orders, firstResult, maxResults);
    }

    public long getCount(Iterable<Map.Entry<String, Object>> filter) {
        return getStateQueryRepository().getCount(filter);
    }

    public long getCount(Criterion filter) {
        return getStateQueryRepository().getCount(filter);
    }

    public TagEvent getEvent(String name, long version) {
        TagEvent e = (TagEvent)getEventStore().getEvent(toEventStoreAggregateId(name), version);
        if (e != null) {
            ((TagEvent.SqlTagEvent)e).setEventReadOnly(true); 
        } else if (version == -1) {
            return getEvent(name, 0);
        }
        return e;
    }

    public TagState getHistoryState(String name, long version) {
        EventStream eventStream = getEventStore().loadEventStream(AbstractTagEvent.class, toEventStoreAggregateId(name), version - 1);
        return new AbstractTagState.SimpleTagState(eventStream.getEvents());
    }


    public TagAggregate getTagAggregate(TagState state) {
        return new AbstractTagAggregate.SimpleTagAggregate(state);
    }

    public EventStoreAggregateId toEventStoreAggregateId(String aggregateId) {
        return new EventStoreAggregateId.SimpleEventStoreAggregateId(aggregateId);
    }

    protected void update(TagCommand c, Consumer<TagAggregate> action) {
        String aggregateId = c.getName();
        EventStoreAggregateId eventStoreAggregateId = toEventStoreAggregateId(aggregateId);
        TagState state = getStateRepository().get(aggregateId, false);
        boolean duplicate = isDuplicateCommand(c, eventStoreAggregateId, state);
        if (duplicate) { return; }

        TagAggregate aggregate = getTagAggregate(state);
        aggregate.throwOnInvalidStateTransition(c);
        action.accept(aggregate);
        persist(eventStoreAggregateId, c.getOffChainVersion() == null ? TagState.VERSION_NULL : c.getOffChainVersion(), aggregate, state); // State version may be null!

    }

    private void persist(EventStoreAggregateId eventStoreAggregateId, long version, TagAggregate aggregate, TagState state) {
        getEventStore().appendEvents(eventStoreAggregateId, version, 
            aggregate.getChanges(), (events) -> { 
                getStateRepository().save(state); 
            });
        if (aggregateEventListener != null) {
            aggregateEventListener.eventAppended(new AggregateEvent<>(aggregate, state, aggregate.getChanges()));
        }
    }

    protected boolean isDuplicateCommand(TagCommand command, EventStoreAggregateId eventStoreAggregateId, TagState state) {
        boolean duplicate = false;
        if (command.getOffChainVersion() == null) { command.setOffChainVersion(TagState.VERSION_NULL); }
        if (state.getOffChainVersion() != null && state.getOffChainVersion() > command.getOffChainVersion()) {
            Event lastEvent = getEventStore().getEvent(AbstractTagEvent.class, eventStoreAggregateId, command.getOffChainVersion());
            if (lastEvent != null && lastEvent instanceof AbstractEvent
               && command.getCommandId() != null && command.getCommandId().equals(((AbstractEvent) lastEvent).getCommandId())) {
                duplicate = true;
            }
        }
        return duplicate;
    }

    public static class SimpleTagApplicationService extends AbstractTagApplicationService {
        public SimpleTagApplicationService(EventStore eventStore, TagStateRepository stateRepository, TagStateQueryRepository stateQueryRepository)
        {
            super(eventStore, stateRepository, stateQueryRepository);
        }
    }

}

