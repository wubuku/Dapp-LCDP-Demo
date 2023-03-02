package org.dddml.suidemocontracts.domain.product;

import java.util.*;
import java.util.function.Consumer;
import org.dddml.support.criterion.Criterion;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.*;

public abstract class AbstractProductApplicationService implements ProductApplicationService
{

    private EventStore eventStore;

    protected EventStore getEventStore()
    {
        return eventStore;
    }

    private ProductStateRepository stateRepository;

    protected ProductStateRepository getStateRepository() {
        return stateRepository;
    }

    private ProductStateQueryRepository stateQueryRepository;

    protected ProductStateQueryRepository getStateQueryRepository() {
        return stateQueryRepository;
    }

    private AggregateEventListener<ProductAggregate, ProductState> aggregateEventListener;

    public AggregateEventListener<ProductAggregate, ProductState> getAggregateEventListener() {
        return aggregateEventListener;
    }

    public void setAggregateEventListener(AggregateEventListener<ProductAggregate, ProductState> eventListener) {
        this.aggregateEventListener = eventListener;
    }

    public AbstractProductApplicationService(EventStore eventStore, ProductStateRepository stateRepository, ProductStateQueryRepository stateQueryRepository) {
        this.eventStore = eventStore;
        this.stateRepository = stateRepository;
        this.stateQueryRepository = stateQueryRepository;
    }

    public void when(ProductCommands.Create c) {
        update(c, ar -> ar.create(c.getName(), c.getUnitPrice(), c.getVersion(), c.getCommandId(), c.getRequesterId(), c));
    }

    public ProductState get(String id) {
        ProductState state = getStateRepository().get(id, true);
        return state;
    }

    public Iterable<ProductState> getAll(Integer firstResult, Integer maxResults) {
        return getStateQueryRepository().getAll(firstResult, maxResults);
    }

    public Iterable<ProductState> get(Iterable<Map.Entry<String, Object>> filter, List<String> orders, Integer firstResult, Integer maxResults) {
        return getStateQueryRepository().get(filter, orders, firstResult, maxResults);
    }

    public Iterable<ProductState> get(Criterion filter, List<String> orders, Integer firstResult, Integer maxResults) {
        return getStateQueryRepository().get(filter, orders, firstResult, maxResults);
    }

    public Iterable<ProductState> getByProperty(String propertyName, Object propertyValue, List<String> orders, Integer firstResult, Integer maxResults) {
        return getStateQueryRepository().getByProperty(propertyName, propertyValue, orders, firstResult, maxResults);
    }

    public long getCount(Iterable<Map.Entry<String, Object>> filter) {
        return getStateQueryRepository().getCount(filter);
    }

    public long getCount(Criterion filter) {
        return getStateQueryRepository().getCount(filter);
    }

    public ProductEvent getEvent(String productId, long version) {
        ProductEvent e = (ProductEvent)getEventStore().getEvent(toEventStoreAggregateId(productId), version);
        if (e != null) {
            ((ProductEvent.SqlProductEvent)e).setEventReadOnly(true); 
        } else if (version == -1) {
            return getEvent(productId, 0);
        }
        return e;
    }

    public ProductState getHistoryState(String productId, long version) {
        EventStream eventStream = getEventStore().loadEventStream(AbstractProductEvent.class, toEventStoreAggregateId(productId), version - 1);
        return new AbstractProductState.SimpleProductState(eventStream.getEvents());
    }


    public ProductAggregate getProductAggregate(ProductState state) {
        return new AbstractProductAggregate.SimpleProductAggregate(state);
    }

    public EventStoreAggregateId toEventStoreAggregateId(String aggregateId) {
        return new EventStoreAggregateId.SimpleEventStoreAggregateId(aggregateId);
    }

    protected void update(ProductCommand c, Consumer<ProductAggregate> action) {
        String aggregateId = c.getProductId();
        EventStoreAggregateId eventStoreAggregateId = toEventStoreAggregateId(aggregateId);
        ProductState state = getStateRepository().get(aggregateId, false);
        boolean duplicate = isDuplicateCommand(c, eventStoreAggregateId, state);
        if (duplicate) { return; }

        ProductAggregate aggregate = getProductAggregate(state);
        aggregate.throwOnInvalidStateTransition(c);
        action.accept(aggregate);
        persist(eventStoreAggregateId, c.getVersion() == null ? ProductState.VERSION_NULL : c.getVersion(), aggregate, state); // State version may be null!

    }

    private void persist(EventStoreAggregateId eventStoreAggregateId, long version, ProductAggregate aggregate, ProductState state) {
        getEventStore().appendEvents(eventStoreAggregateId, version, 
            aggregate.getChanges(), (events) -> { 
            });
        if (aggregateEventListener != null) {
            aggregateEventListener.eventAppended(new AggregateEvent<>(aggregate, state, aggregate.getChanges()));
        }
    }

    protected boolean isDuplicateCommand(ProductCommand command, EventStoreAggregateId eventStoreAggregateId, ProductState state) {
        boolean duplicate = false;
        if (command.getVersion() == null) { command.setVersion(ProductState.VERSION_NULL); }
        if (state.getVersion() != null && state.getVersion() > command.getVersion()) {
            Event lastEvent = getEventStore().getEvent(AbstractProductEvent.class, eventStoreAggregateId, command.getVersion());
            if (lastEvent != null && lastEvent instanceof AbstractEvent
               && command.getCommandId() != null && command.getCommandId().equals(((AbstractEvent) lastEvent).getCommandId())) {
                duplicate = true;
            }
        }
        return duplicate;
    }

    public static class SimpleProductApplicationService extends AbstractProductApplicationService {
        public SimpleProductApplicationService(EventStore eventStore, ProductStateRepository stateRepository, ProductStateQueryRepository stateQueryRepository)
        {
            super(eventStore, stateRepository, stateQueryRepository);
        }
    }

}
