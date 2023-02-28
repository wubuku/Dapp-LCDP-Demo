package org.dddml.suidemocontracts.domain.orderv2;

import java.util.*;
import java.math.BigInteger;
import org.dddml.suidemocontracts.domain.*;
import java.util.Date;
import org.dddml.suidemocontracts.specialization.*;
import org.dddml.suidemocontracts.domain.orderv2.OrderV2Event.*;

public abstract class AbstractOrderV2State implements OrderV2State.SqlOrderV2State, Saveable {

    private String orderId;

    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    private BigInteger totalAmount;

    public BigInteger getTotalAmount() {
        return this.totalAmount;
    }

    public void setTotalAmount(BigInteger totalAmount) {
        this.totalAmount = totalAmount;
    }

    private Day estimatedShipDate;

    public Day getEstimatedShipDate() {
        return this.estimatedShipDate;
    }

    public void setEstimatedShipDate(Day estimatedShipDate) {
        this.estimatedShipDate = estimatedShipDate;
    }

    private Long version;

    public Long getVersion() {
        return this.version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    private String createdBy;

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    private Date createdAt;

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    private String updatedBy;

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    private Date updatedAt;

    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    private Boolean active;

    public Boolean getActive() {
        return this.active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    private Boolean deleted;

    public Boolean getDeleted() {
        return this.deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isStateUnsaved() {
        return this.getVersion() == null;
    }

    private EntityStateCollection<String, OrderV2ItemState> items;

    public EntityStateCollection<String, OrderV2ItemState> getItems() {
        return this.items;
    }

    public void setItems(EntityStateCollection<String, OrderV2ItemState> items) {
        this.items = items;
    }

    private Boolean stateReadOnly;

    public Boolean getStateReadOnly() { return this.stateReadOnly; }

    public void setStateReadOnly(Boolean readOnly) { this.stateReadOnly = readOnly; }

    private boolean forReapplying;

    public boolean getForReapplying() {
        return forReapplying;
    }

    public void setForReapplying(boolean forReapplying) {
        this.forReapplying = forReapplying;
    }

    public AbstractOrderV2State(List<Event> events) {
        initializeForReapplying();
        if (events != null && events.size() > 0) {
            this.setOrderId(((OrderV2Event.SqlOrderV2Event) events.get(0)).getOrderV2EventId().getOrderId());
            for (Event e : events) {
                mutate(e);
                this.setVersion((this.getVersion() == null ? OrderV2State.VERSION_NULL : this.getVersion()) + 1);
            }
        }
    }


    public AbstractOrderV2State() {
        initializeProperties();
    }

    protected void initializeForReapplying() {
        this.forReapplying = true;

        initializeProperties();
    }
    
    protected void initializeProperties() {
        items = new SimpleOrderV2ItemStateCollection(this);
    }

    @Override
    public int hashCode() {
        return getOrderId().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        if (obj instanceof OrderV2State) {
            return Objects.equals(this.getOrderId(), ((OrderV2State)obj).getOrderId());
        }
        return false;
    }


    public void mutate(Event e) {
        setStateReadOnly(false);
        if (e instanceof OrderV2StateCreated) {
            when((OrderV2StateCreated) e);
        } else if (e instanceof OrderV2StateMergePatched) {
            when((OrderV2StateMergePatched) e);
        } else if (e instanceof OrderV2StateDeleted) {
            when((OrderV2StateDeleted) e);
        } else {
            throw new UnsupportedOperationException(String.format("Unsupported event type: %1$s", e.getClass().getName()));
        }
    }

    public void when(OrderV2StateCreated e) {
        throwOnWrongEvent(e);

        this.setTotalAmount(e.getTotalAmount());
        this.setEstimatedShipDate(e.getEstimatedShipDate());
        this.setActive(e.getActive());

        this.setDeleted(false);

        this.setCreatedBy(e.getCreatedBy());
        this.setCreatedAt(e.getCreatedAt());

        for (OrderV2ItemEvent.OrderV2ItemStateCreated innerEvent : e.getOrderV2ItemEvents()) {
            OrderV2ItemState innerState = ((EntityStateCollection.ModifiableEntityStateCollection<String, OrderV2ItemState>)this.getItems()).getOrAdd(((OrderV2ItemEvent.SqlOrderV2ItemEvent)innerEvent).getOrderV2ItemEventId().getProductId());
            ((OrderV2ItemState.SqlOrderV2ItemState)innerState).mutate(innerEvent);
        }
    }

    protected void merge(OrderV2State s) {
        if (s == this) {
            return;
        }
        this.setTotalAmount(s.getTotalAmount());
        this.setEstimatedShipDate(s.getEstimatedShipDate());
        this.setActive(s.getActive());

        if (s.getItems() != null) {
            Iterable<OrderV2ItemState> iterable;
            if (s.getItems().isLazy()) {
                iterable = s.getItems().getLoadedStates();
            } else {
                iterable = s.getItems();
            }
            if (iterable != null) {
                for (OrderV2ItemState ss : iterable) {
                    OrderV2ItemState thisInnerState = ((EntityStateCollection.ModifiableEntityStateCollection<String, OrderV2ItemState>)this.getItems()).getOrAdd(ss.getProductId());
                    ((AbstractOrderV2ItemState) thisInnerState).merge(ss);
                }
            }
        }
        if (s.getItems() != null) {
            if (s.getItems() instanceof EntityStateCollection.ModifiableEntityStateCollection) {
                if (((EntityStateCollection.ModifiableEntityStateCollection)s.getItems()).getRemovedStates() != null) {
                    for (OrderV2ItemState ss : ((EntityStateCollection.ModifiableEntityStateCollection<String, OrderV2ItemState>)s.getItems()).getRemovedStates()) {
                        OrderV2ItemState thisInnerState = ((EntityStateCollection.ModifiableEntityStateCollection<String, OrderV2ItemState>)this.getItems()).getOrAdd(ss.getProductId());
                        ((AbstractOrderV2ItemStateCollection)this.getItems()).remove(thisInnerState);
                    }
                }
            } else {
                if (s.getItems().isAllLoaded()) {
                    Set<String> removedStateIds = new HashSet<>(this.getItems().stream().map(i -> i.getProductId()).collect(java.util.stream.Collectors.toList()));
                    s.getItems().forEach(i -> removedStateIds.remove(i.getProductId()));
                    for (String i : removedStateIds) {
                        OrderV2ItemState thisInnerState = ((EntityStateCollection.ModifiableEntityStateCollection<String, OrderV2ItemState>)this.getItems()).getOrAdd(i);
                        ((AbstractOrderV2ItemStateCollection)this.getItems()).remove(thisInnerState);
                    }
                }
            }
        }
    }

    public void when(OrderV2StateMergePatched e) {
        throwOnWrongEvent(e);

        if (e.getTotalAmount() == null) {
            if (e.getIsPropertyTotalAmountRemoved() != null && e.getIsPropertyTotalAmountRemoved()) {
                this.setTotalAmount(null);
            }
        } else {
            this.setTotalAmount(e.getTotalAmount());
        }
        if (e.getEstimatedShipDate() == null) {
            if (e.getIsPropertyEstimatedShipDateRemoved() != null && e.getIsPropertyEstimatedShipDateRemoved()) {
                this.setEstimatedShipDate(null);
            }
        } else {
            this.setEstimatedShipDate(e.getEstimatedShipDate());
        }
        if (e.getActive() == null) {
            if (e.getIsPropertyActiveRemoved() != null && e.getIsPropertyActiveRemoved()) {
                this.setActive(null);
            }
        } else {
            this.setActive(e.getActive());
        }

        this.setUpdatedBy(e.getCreatedBy());
        this.setUpdatedAt(e.getCreatedAt());

        for (OrderV2ItemEvent innerEvent : e.getOrderV2ItemEvents()) {
            OrderV2ItemState innerState = ((EntityStateCollection.ModifiableEntityStateCollection<String, OrderV2ItemState>)this.getItems()).getOrAdd(((OrderV2ItemEvent.SqlOrderV2ItemEvent)innerEvent).getOrderV2ItemEventId().getProductId());
            ((OrderV2ItemState.SqlOrderV2ItemState)innerState).mutate(innerEvent);
            if (innerEvent instanceof OrderV2ItemEvent.OrderV2ItemStateRemoved) {
                //OrderV2ItemEvent.OrderV2ItemStateRemoved removed = (OrderV2ItemEvent.OrderV2ItemStateRemoved)innerEvent;
                ((AbstractOrderV2ItemStateCollection)this.getItems()).remove(innerState);
            }
        }
    }

    public void when(OrderV2StateDeleted e) {
        throwOnWrongEvent(e);

        this.setDeleted(true);
        this.setUpdatedBy(e.getCreatedBy());
        this.setUpdatedAt(e.getCreatedAt());

        for (OrderV2ItemState innerState : this.getItems()) {
            ((AbstractOrderV2ItemStateCollection)this.getItems()).remove(innerState);
        
            OrderV2ItemEvent.OrderV2ItemStateRemoved innerE = e.newOrderV2ItemStateRemoved(innerState.getProductId());
            innerE.setCreatedAt(e.getCreatedAt());
            innerE.setCreatedBy(e.getCreatedBy());
            ((OrderV2ItemState.MutableOrderV2ItemState)innerState).mutate(innerE);
            //e.addOrderV2ItemEvent(innerE);
        }
    }

    public void save() {
        ((Saveable)items).save();

    }

    protected void throwOnWrongEvent(OrderV2Event event) {
        String stateEntityId = this.getOrderId(); // Aggregate Id
        String eventEntityId = ((OrderV2Event.SqlOrderV2Event)event).getOrderV2EventId().getOrderId(); // EntityBase.Aggregate.GetEventIdPropertyIdName();
        if (!stateEntityId.equals(eventEntityId)) {
            throw DomainError.named("mutateWrongEntity", "Entity Id %1$s in state but entity id %2$s in event", stateEntityId, eventEntityId);
        }


        Long stateVersion = this.getVersion();
        Long eventVersion = ((OrderV2Event.SqlOrderV2Event)event).getOrderV2EventId().getVersion();// Aggregate Version
        if (eventVersion == null) {
            throw new NullPointerException("event.getOrderV2EventId().getVersion() == null");
        }
        if (!(stateVersion == null && eventVersion.equals(OrderV2State.VERSION_NULL)) && !eventVersion.equals(stateVersion)) {
            throw DomainError.named("concurrencyConflict", "Conflict between state version (%1$s) and event version (%2$s)", stateVersion, eventVersion);
        }

    }


    public static class SimpleOrderV2State extends AbstractOrderV2State {

        public SimpleOrderV2State() {
        }

        public SimpleOrderV2State(List<Event> events) {
            super(events);
        }

        public static SimpleOrderV2State newForReapplying() {
            SimpleOrderV2State s = new SimpleOrderV2State();
            s.initializeForReapplying();
            return s;
        }

    }


    static class SimpleOrderV2ItemStateCollection extends AbstractOrderV2ItemStateCollection {
        public SimpleOrderV2ItemStateCollection(AbstractOrderV2State outerState) {
            super(outerState);
        }
    }


}

