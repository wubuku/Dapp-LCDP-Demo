package org.dddml.suidemocontracts.domain.orderv2;

import java.util.*;
import java.util.function.Consumer;
import org.dddml.support.criterion.Criterion;
import java.math.BigInteger;
import org.dddml.suidemocontracts.domain.*;
import java.util.Date;
import org.dddml.suidemocontracts.specialization.*;

public abstract class AbstractOrderV2ApplicationService implements OrderV2ApplicationService
{

    private EventStore eventStore;

    protected EventStore getEventStore()
    {
        return eventStore;
    }

    private OrderV2StateRepository stateRepository;

    protected OrderV2StateRepository getStateRepository() {
        return stateRepository;
    }

    private OrderV2StateQueryRepository stateQueryRepository;

    protected OrderV2StateQueryRepository getStateQueryRepository() {
        return stateQueryRepository;
    }

    private AggregateEventListener<OrderV2Aggregate, OrderV2State> aggregateEventListener;

    public AggregateEventListener<OrderV2Aggregate, OrderV2State> getAggregateEventListener() {
        return aggregateEventListener;
    }

    public void setAggregateEventListener(AggregateEventListener<OrderV2Aggregate, OrderV2State> eventListener) {
        this.aggregateEventListener = eventListener;
    }

    public AbstractOrderV2ApplicationService(EventStore eventStore, OrderV2StateRepository stateRepository, OrderV2StateQueryRepository stateQueryRepository) {
        this.eventStore = eventStore;
        this.stateRepository = stateRepository;
        this.stateQueryRepository = stateQueryRepository;
    }

    public void when(OrderV2Commands.Create c) {
        update(c, ar -> ar.create(c.getProduct(), c.getQuantity(), c.getOffChainVersion(), c.getCommandId(), c.getRequesterId(), c));
    }

    public void when(OrderV2Commands.RemoveItem c) {
        update(c, ar -> ar.removeItem(c.getProductId(), c.getOffChainVersion(), c.getCommandId(), c.getRequesterId(), c));
    }

    public void when(OrderV2Commands.UpdateItemQuantity c) {
        update(c, ar -> ar.updateItemQuantity(c.getProductId(), c.getQuantity(), c.getOffChainVersion(), c.getCommandId(), c.getRequesterId(), c));
    }

    public void when(OrderV2Commands.UpdateEstimatedShipDate c) {
        update(c, ar -> ar.updateEstimatedShipDate(c.getEstimatedShipDate(), c.getOffChainVersion(), c.getCommandId(), c.getRequesterId(), c));
    }

    public OrderV2State get(String id) {
        OrderV2State state = getStateRepository().get(id, true);
        return state;
    }

    public Iterable<OrderV2State> getAll(Integer firstResult, Integer maxResults) {
        return getStateQueryRepository().getAll(firstResult, maxResults);
    }

    public Iterable<OrderV2State> get(Iterable<Map.Entry<String, Object>> filter, List<String> orders, Integer firstResult, Integer maxResults) {
        return getStateQueryRepository().get(filter, orders, firstResult, maxResults);
    }

    public Iterable<OrderV2State> get(Criterion filter, List<String> orders, Integer firstResult, Integer maxResults) {
        return getStateQueryRepository().get(filter, orders, firstResult, maxResults);
    }

    public Iterable<OrderV2State> getByProperty(String propertyName, Object propertyValue, List<String> orders, Integer firstResult, Integer maxResults) {
        return getStateQueryRepository().getByProperty(propertyName, propertyValue, orders, firstResult, maxResults);
    }

    public long getCount(Iterable<Map.Entry<String, Object>> filter) {
        return getStateQueryRepository().getCount(filter);
    }

    public long getCount(Criterion filter) {
        return getStateQueryRepository().getCount(filter);
    }

    public OrderV2Event getEvent(String orderId, long version) {
        OrderV2Event e = (OrderV2Event)getEventStore().getEvent(toEventStoreAggregateId(orderId), version);
        if (e != null) {
            ((OrderV2Event.SqlOrderV2Event)e).setEventReadOnly(true); 
        } else if (version == -1) {
            return getEvent(orderId, 0);
        }
        return e;
    }

    public OrderV2State getHistoryState(String orderId, long version) {
        EventStream eventStream = getEventStore().loadEventStream(AbstractOrderV2Event.class, toEventStoreAggregateId(orderId), version - 1);
        return new AbstractOrderV2State.SimpleOrderV2State(eventStream.getEvents());
    }

    public OrderV2ItemState getOrderV2Item(String orderV2OrderId, String productId) {
        return getStateQueryRepository().getOrderV2Item(orderV2OrderId, productId);
    }

    public Iterable<OrderV2ItemState> getOrderV2Items(String orderV2OrderId, Criterion filter, List<String> orders) {
        return getStateQueryRepository().getOrderV2Items(orderV2OrderId, filter, orders);
    }


    public OrderV2Aggregate getOrderV2Aggregate(OrderV2State state) {
        return new AbstractOrderV2Aggregate.SimpleOrderV2Aggregate(state);
    }

    public EventStoreAggregateId toEventStoreAggregateId(String aggregateId) {
        return new EventStoreAggregateId.SimpleEventStoreAggregateId(aggregateId);
    }

    protected void update(OrderV2Command c, Consumer<OrderV2Aggregate> action) {
        String aggregateId = c.getOrderId();
        EventStoreAggregateId eventStoreAggregateId = toEventStoreAggregateId(aggregateId);
        OrderV2State state = getStateRepository().get(aggregateId, false);
        boolean duplicate = isDuplicateCommand(c, eventStoreAggregateId, state);
        if (duplicate) { return; }

        OrderV2Aggregate aggregate = getOrderV2Aggregate(state);
        aggregate.throwOnInvalidStateTransition(c);
        action.accept(aggregate);
        persist(eventStoreAggregateId, c.getOffChainVersion() == null ? OrderV2State.VERSION_NULL : c.getOffChainVersion(), aggregate, state); // State version may be null!

    }

    private void persist(EventStoreAggregateId eventStoreAggregateId, long version, OrderV2Aggregate aggregate, OrderV2State state) {
        getEventStore().appendEvents(eventStoreAggregateId, version, 
            aggregate.getChanges(), (events) -> { 
                getStateRepository().save(state); 
            });
        if (aggregateEventListener != null) {
            aggregateEventListener.eventAppended(new AggregateEvent<>(aggregate, state, aggregate.getChanges()));
        }
    }

    protected boolean isDuplicateCommand(OrderV2Command command, EventStoreAggregateId eventStoreAggregateId, OrderV2State state) {
        boolean duplicate = false;
        if (command.getOffChainVersion() == null) { command.setOffChainVersion(OrderV2State.VERSION_NULL); }
        if (state.getOffChainVersion() != null && state.getOffChainVersion() > command.getOffChainVersion()) {
            Event lastEvent = getEventStore().getEvent(AbstractOrderV2Event.class, eventStoreAggregateId, command.getOffChainVersion());
            if (lastEvent != null && lastEvent instanceof AbstractEvent
               && command.getCommandId() != null && command.getCommandId().equals(((AbstractEvent) lastEvent).getCommandId())) {
                duplicate = true;
            }
        }
        return duplicate;
    }

    public static class SimpleOrderV2ApplicationService extends AbstractOrderV2ApplicationService {
        public SimpleOrderV2ApplicationService(EventStore eventStore, OrderV2StateRepository stateRepository, OrderV2StateQueryRepository stateQueryRepository)
        {
            super(eventStore, stateRepository, stateQueryRepository);
        }
    }

}

