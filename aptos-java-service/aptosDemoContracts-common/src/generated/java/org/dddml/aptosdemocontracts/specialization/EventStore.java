package org.dddml.aptosdemocontracts.specialization;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * Created by Yang on 2016/7/19.
 */
public interface EventStore {
    EventStream loadEventStream(EventStoreAggregateId aggregateId);

    void appendEvents(EventStoreAggregateId aggregateId, long version, Collection<Event> events, Consumer<Collection<Event>> afterEventsAppended);

    Event getEvent(Class eventType, EventStoreAggregateId eventStoreAggregateId, long version);

    boolean isEventWithCommandIdExisted(Class eventType, EventStoreAggregateId eventStoreAggregateId, String commandId);

    Event getEvent(EventStoreAggregateId eventStoreAggregateId, long version);

    EventStream loadEventStream(Class eventType, EventStoreAggregateId eventStoreAggregateId, long version);
}
