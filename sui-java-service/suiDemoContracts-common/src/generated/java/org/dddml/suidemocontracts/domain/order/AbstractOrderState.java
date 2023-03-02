package org.dddml.suidemocontracts.domain.order;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.*;
import org.dddml.suidemocontracts.domain.order.OrderEvent.*;

public abstract class AbstractOrderState implements OrderState.SqlOrderState, Saveable {

    private String id;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private BigInteger totalAmount;

    public BigInteger getTotalAmount() {
        return this.totalAmount;
    }

    public void setTotalAmount(BigInteger totalAmount) {
        this.totalAmount = totalAmount;
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

    private EntityStateCollection<String, OrderItemState> items;

    public EntityStateCollection<String, OrderItemState> getItems() {
        return this.items;
    }

    public void setItems(EntityStateCollection<String, OrderItemState> items) {
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

    public AbstractOrderState(List<Event> events) {
        initializeForReapplying();
        if (events != null && events.size() > 0) {
            this.setId(((OrderEvent.SqlOrderEvent) events.get(0)).getOrderEventId().getId());
            for (Event e : events) {
                mutate(e);
                this.setVersion((this.getVersion() == null ? OrderState.VERSION_NULL : this.getVersion()) + 1);
            }
        }
    }


    public AbstractOrderState() {
        initializeProperties();
    }

    protected void initializeForReapplying() {
        this.forReapplying = true;

        initializeProperties();
    }
    
    protected void initializeProperties() {
        items = new SimpleOrderItemStateCollection(this);
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        if (obj instanceof OrderState) {
            return Objects.equals(this.getId(), ((OrderState)obj).getId());
        }
        return false;
    }


    public void mutate(Event e) {
        setStateReadOnly(false);
        if (false) { 
            ;
        } else if (e instanceof AbstractOrderEvent.OrderCreated) {
            when((AbstractOrderEvent.OrderCreated)e);
        } else if (e instanceof AbstractOrderEvent.OrderItemRemoved) {
            when((AbstractOrderEvent.OrderItemRemoved)e);
        } else if (e instanceof AbstractOrderEvent.OrderItemQuantityUpdated) {
            when((AbstractOrderEvent.OrderItemQuantityUpdated)e);
        } else {
            throw new UnsupportedOperationException(String.format("Unsupported event type: %1$s", e.getClass().getName()));
        }
    }

    protected void merge(OrderState s) {
        if (s == this) {
            return;
        }
        this.setTotalAmount(s.getTotalAmount());
        this.setActive(s.getActive());

        if (s.getItems() != null) {
            Iterable<OrderItemState> iterable;
            if (s.getItems().isLazy()) {
                iterable = s.getItems().getLoadedStates();
            } else {
                iterable = s.getItems();
            }
            if (iterable != null) {
                for (OrderItemState ss : iterable) {
                    OrderItemState thisInnerState = ((EntityStateCollection.ModifiableEntityStateCollection<String, OrderItemState>)this.getItems()).getOrAdd(ss.getProductId());
                    ((AbstractOrderItemState) thisInnerState).merge(ss);
                }
            }
        }
        if (s.getItems() != null) {
            if (s.getItems() instanceof EntityStateCollection.ModifiableEntityStateCollection) {
                if (((EntityStateCollection.ModifiableEntityStateCollection)s.getItems()).getRemovedStates() != null) {
                    for (OrderItemState ss : ((EntityStateCollection.ModifiableEntityStateCollection<String, OrderItemState>)s.getItems()).getRemovedStates()) {
                        OrderItemState thisInnerState = ((EntityStateCollection.ModifiableEntityStateCollection<String, OrderItemState>)this.getItems()).getOrAdd(ss.getProductId());
                        ((AbstractOrderItemStateCollection)this.getItems()).remove(thisInnerState);
                    }
                }
            } else {
                if (s.getItems().isAllLoaded()) {
                    Set<String> removedStateIds = new HashSet<>(this.getItems().stream().map(i -> i.getProductId()).collect(java.util.stream.Collectors.toList()));
                    s.getItems().forEach(i -> removedStateIds.remove(i.getProductId()));
                    for (String i : removedStateIds) {
                        OrderItemState thisInnerState = ((EntityStateCollection.ModifiableEntityStateCollection<String, OrderItemState>)this.getItems()).getOrAdd(i);
                        ((AbstractOrderItemStateCollection)this.getItems()).remove(thisInnerState);
                    }
                }
            }
        }
    }

    public void when(AbstractOrderEvent.OrderCreated e) {
        throwOnWrongEvent(e);

        String product = e.getProduct();
        String Product = product;
        BigInteger quantity = e.getQuantity();
        BigInteger Quantity = quantity;
        BigInteger unitPrice = e.getUnitPrice();
        BigInteger UnitPrice = unitPrice;
        BigInteger totalAmount = e.getTotalAmount();
        BigInteger TotalAmount = totalAmount;
        String owner = e.getOwner();
        String Owner = owner;

        if (this.getCreatedBy() == null){
            this.setCreatedBy(e.getCreatedBy());
        }
        if (this.getCreatedAt() == null){
            this.setCreatedAt(e.getCreatedAt());
        }
        this.setUpdatedBy(e.getCreatedBy());
        this.setUpdatedAt(e.getCreatedAt());

        OrderState updatedOrderState = (OrderState) ReflectUtils.invokeStaticMethod(
                    "org.dddml.suidemocontracts.domain.order.CreateLogic",
                    "mutate",
                    new Class[]{OrderState.class, String.class, BigInteger.class, BigInteger.class, BigInteger.class, String.class, MutationContext.class},
                    new Object[]{this, product, quantity, unitPrice, totalAmount, owner, MutationContext.forEvent(e, s -> {if (s == this) {return this;} else {throw new UnsupportedOperationException();}})}
            );

//package org.dddml.suidemocontracts.domain.order;
//
//public class CreateLogic {
//    public static OrderState mutate(OrderState orderState, String product, BigInteger quantity, BigInteger unitPrice, BigInteger totalAmount, String owner, MutationContext<OrderState, OrderState.MutableOrderState> mutationContext) {
//    }
//}

        if (this != updatedOrderState) { merge(updatedOrderState); } //else do nothing

    }

    public void when(AbstractOrderEvent.OrderItemRemoved e) {
        throwOnWrongEvent(e);

        String productId = e.getProductId();
        String ProductId = productId;

        if (this.getCreatedBy() == null){
            this.setCreatedBy(e.getCreatedBy());
        }
        if (this.getCreatedAt() == null){
            this.setCreatedAt(e.getCreatedAt());
        }
        this.setUpdatedBy(e.getCreatedBy());
        this.setUpdatedAt(e.getCreatedAt());

        OrderState updatedOrderState = (OrderState) ReflectUtils.invokeStaticMethod(
                    "org.dddml.suidemocontracts.domain.order.RemoveItemLogic",
                    "mutate",
                    new Class[]{OrderState.class, String.class, MutationContext.class},
                    new Object[]{this, productId, MutationContext.forEvent(e, s -> {if (s == this) {return this;} else {throw new UnsupportedOperationException();}})}
            );

//package org.dddml.suidemocontracts.domain.order;
//
//public class RemoveItemLogic {
//    public static OrderState mutate(OrderState orderState, String productId, MutationContext<OrderState, OrderState.MutableOrderState> mutationContext) {
//    }
//}

        if (this != updatedOrderState) { merge(updatedOrderState); } //else do nothing

    }

    public void when(AbstractOrderEvent.OrderItemQuantityUpdated e) {
        throwOnWrongEvent(e);

        String productId = e.getProductId();
        String ProductId = productId;
        BigInteger quantity = e.getQuantity();
        BigInteger Quantity = quantity;

        if (this.getCreatedBy() == null){
            this.setCreatedBy(e.getCreatedBy());
        }
        if (this.getCreatedAt() == null){
            this.setCreatedAt(e.getCreatedAt());
        }
        this.setUpdatedBy(e.getCreatedBy());
        this.setUpdatedAt(e.getCreatedAt());

        OrderState updatedOrderState = (OrderState) ReflectUtils.invokeStaticMethod(
                    "org.dddml.suidemocontracts.domain.order.UpdateItemQuantityLogic",
                    "mutate",
                    new Class[]{OrderState.class, String.class, BigInteger.class, MutationContext.class},
                    new Object[]{this, productId, quantity, MutationContext.forEvent(e, s -> {if (s == this) {return this;} else {throw new UnsupportedOperationException();}})}
            );

//package org.dddml.suidemocontracts.domain.order;
//
//public class UpdateItemQuantityLogic {
//    public static OrderState mutate(OrderState orderState, String productId, BigInteger quantity, MutationContext<OrderState, OrderState.MutableOrderState> mutationContext) {
//    }
//}

        if (this != updatedOrderState) { merge(updatedOrderState); } //else do nothing

    }

    public void save() {
        ((Saveable)items).save();

    }

    protected void throwOnWrongEvent(OrderEvent event) {
        String stateEntityId = this.getId(); // Aggregate Id
        String eventEntityId = ((OrderEvent.SqlOrderEvent)event).getOrderEventId().getId(); // EntityBase.Aggregate.GetEventIdPropertyIdName();
        if (!stateEntityId.equals(eventEntityId)) {
            throw DomainError.named("mutateWrongEntity", "Entity Id %1$s in state but entity id %2$s in event", stateEntityId, eventEntityId);
        }


        Long stateVersion = this.getVersion();
        Long eventVersion = ((OrderEvent.SqlOrderEvent)event).getOrderEventId().getVersion();// Aggregate Version
        if (eventVersion == null) {
            throw new NullPointerException("event.getOrderEventId().getVersion() == null");
        }
        if (!(stateVersion == null && eventVersion.equals(OrderState.VERSION_NULL)) && !eventVersion.equals(stateVersion)) {
            throw DomainError.named("concurrencyConflict", "Conflict between state version (%1$s) and event version (%2$s)", stateVersion, eventVersion);
        }

    }


    public static class SimpleOrderState extends AbstractOrderState {

        public SimpleOrderState() {
        }

        public SimpleOrderState(List<Event> events) {
            super(events);
        }

        public static SimpleOrderState newForReapplying() {
            SimpleOrderState s = new SimpleOrderState();
            s.initializeForReapplying();
            return s;
        }

    }


    static class SimpleOrderItemStateCollection extends AbstractOrderItemStateCollection {
        public SimpleOrderItemStateCollection(AbstractOrderState outerState) {
            super(outerState);
        }
    }


}
