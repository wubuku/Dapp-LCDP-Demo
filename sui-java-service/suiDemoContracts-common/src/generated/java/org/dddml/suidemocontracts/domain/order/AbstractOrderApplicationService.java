package org.dddml.suidemocontracts.domain.order;

import java.util.*;
import java.util.function.Consumer;
import org.dddml.support.criterion.Criterion;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.*;

public abstract class AbstractOrderApplicationService implements OrderApplicationService
{

    private EventStore eventStore;

    protected EventStore getEventStore()
    {
        return eventStore;
    }

    private OrderStateRepository stateRepository;

    protected OrderStateRepository getStateRepository() {
        return stateRepository;
    }

    private OrderStateQueryRepository stateQueryRepository;

    protected OrderStateQueryRepository getStateQueryRepository() {
        return stateQueryRepository;
    }

    private AggregateEventListener<OrderAggregate, OrderState> aggregateEventListener;

    public AggregateEventListener<OrderAggregate, OrderState> getAggregateEventListener() {
        return aggregateEventListener;
    }

    public void setAggregateEventListener(AggregateEventListener<OrderAggregate, OrderState> eventListener) {
        this.aggregateEventListener = eventListener;
    }

    public AbstractOrderApplicationService(EventStore eventStore, OrderStateRepository stateRepository, OrderStateQueryRepository stateQueryRepository) {
        this.eventStore = eventStore;
        this.stateRepository = stateRepository;
        this.stateQueryRepository = stateQueryRepository;
    }

    public void when(OrderCommand.CreateOrder c) {
        update(c, ar -> ar.create(c));
    }

    public void when(OrderCommand.MergePatchOrder c) {
        update(c, ar -> ar.mergePatch(c));
    }

    public void when(OrderCommand.DeleteOrder c) {
        update(c, ar -> ar.delete(c));
    }

    public void when(OrderCommands.RemoveItem c) {
        update(c, ar -> ar.removeItem(c.getProductId(), c.getVersion(), c.getCommandId(), c.getRequesterId(), c));
    }

    public void when(OrderCommands.UpdateItemQuantity c) {
        update(c, ar -> ar.updateItemQuantity(c.getProductId(), c.getQuantity(), c.getVersion(), c.getCommandId(), c.getRequesterId(), c));
    }

    public OrderState get(String id) {
        OrderState state = getStateRepository().get(id, true);
        return state;
    }

    public Iterable<OrderState> getAll(Integer firstResult, Integer maxResults) {
        return getStateQueryRepository().getAll(firstResult, maxResults);
    }

    public Iterable<OrderState> get(Iterable<Map.Entry<String, Object>> filter, List<String> orders, Integer firstResult, Integer maxResults) {
        return getStateQueryRepository().get(filter, orders, firstResult, maxResults);
    }

    public Iterable<OrderState> get(Criterion filter, List<String> orders, Integer firstResult, Integer maxResults) {
        return getStateQueryRepository().get(filter, orders, firstResult, maxResults);
    }

    public Iterable<OrderState> getByProperty(String propertyName, Object propertyValue, List<String> orders, Integer firstResult, Integer maxResults) {
        return getStateQueryRepository().getByProperty(propertyName, propertyValue, orders, firstResult, maxResults);
    }

    public long getCount(Iterable<Map.Entry<String, Object>> filter) {
        return getStateQueryRepository().getCount(filter);
    }

    public long getCount(Criterion filter) {
        return getStateQueryRepository().getCount(filter);
    }

    public OrderEvent getEvent(String id, long version) {
        OrderEvent e = (OrderEvent)getEventStore().getEvent(toEventStoreAggregateId(id), version);
        if (e != null) {
            ((OrderEvent.SqlOrderEvent)e).setEventReadOnly(true); 
        } else if (version == -1) {
            return getEvent(id, 0);
        }
        return e;
    }

    public OrderState getHistoryState(String id, long version) {
        EventStream eventStream = getEventStore().loadEventStream(AbstractOrderEvent.class, toEventStoreAggregateId(id), version - 1);
        return new AbstractOrderState.SimpleOrderState(eventStream.getEvents());
    }

    public OrderItemState getOrderItem(String orderId, String productId) {
        return getStateQueryRepository().getOrderItem(orderId, productId);
    }

    public Iterable<OrderItemState> getOrderItems(String orderId, Criterion filter, List<String> orders) {
        return getStateQueryRepository().getOrderItems(orderId, filter, orders);
    }


    public OrderAggregate getOrderAggregate(OrderState state) {
        return new AbstractOrderAggregate.SimpleOrderAggregate(state);
    }

    public EventStoreAggregateId toEventStoreAggregateId(String aggregateId) {
        return new EventStoreAggregateId.SimpleEventStoreAggregateId(aggregateId);
    }

    protected void update(OrderCommand c, Consumer<OrderAggregate> action) {
        String aggregateId = c.getId();
        EventStoreAggregateId eventStoreAggregateId = toEventStoreAggregateId(aggregateId);
        OrderState state = getStateRepository().get(aggregateId, false);
        boolean duplicate = isDuplicateCommand(c, eventStoreAggregateId, state);
        if (duplicate) { return; }

        OrderAggregate aggregate = getOrderAggregate(state);
        aggregate.throwOnInvalidStateTransition(c);
        action.accept(aggregate);
        persist(eventStoreAggregateId, c.getVersion() == null ? OrderState.VERSION_NULL : c.getVersion(), aggregate, state); // State version may be null!

    }

    private void persist(EventStoreAggregateId eventStoreAggregateId, long version, OrderAggregate aggregate, OrderState state) {
        getEventStore().appendEvents(eventStoreAggregateId, version, 
            aggregate.getChanges(), (events) -> { 
                getStateRepository().save(state); 
            });
        if (aggregateEventListener != null) {
            aggregateEventListener.eventAppended(new AggregateEvent<>(aggregate, state, aggregate.getChanges()));
        }
    }

    public void initialize(OrderEvent.OrderStateCreated stateCreated) {
        String aggregateId = ((OrderEvent.SqlOrderEvent)stateCreated).getOrderEventId().getId();
        OrderState.SqlOrderState state = new AbstractOrderState.SimpleOrderState();
        state.setId(aggregateId);

        OrderAggregate aggregate = getOrderAggregate(state);
        ((AbstractOrderAggregate) aggregate).apply(stateCreated);

        EventStoreAggregateId eventStoreAggregateId = toEventStoreAggregateId(aggregateId);
        persist(eventStoreAggregateId, ((OrderEvent.SqlOrderEvent)stateCreated).getOrderEventId().getVersion(), aggregate, state);
    }

    protected boolean isDuplicateCommand(OrderCommand command, EventStoreAggregateId eventStoreAggregateId, OrderState state) {
        boolean duplicate = false;
        if (command.getVersion() == null) { command.setVersion(OrderState.VERSION_NULL); }
        if (state.getVersion() != null && state.getVersion() > command.getVersion()) {
            Event lastEvent = getEventStore().getEvent(AbstractOrderEvent.class, eventStoreAggregateId, command.getVersion());
            if (lastEvent != null && lastEvent instanceof AbstractEvent
               && command.getCommandId() != null && command.getCommandId().equals(((AbstractEvent) lastEvent).getCommandId())) {
                duplicate = true;
            }
        }
        return duplicate;
    }

    public static class SimpleOrderApplicationService extends AbstractOrderApplicationService {
        public SimpleOrderApplicationService(EventStore eventStore, OrderStateRepository stateRepository, OrderStateQueryRepository stateQueryRepository)
        {
            super(eventStore, stateRepository, stateQueryRepository);
        }
    }

}

