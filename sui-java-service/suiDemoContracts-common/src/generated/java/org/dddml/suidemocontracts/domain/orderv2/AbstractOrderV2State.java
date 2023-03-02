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
        if (false) { 
            ;
        } else if (e instanceof AbstractOrderV2Event.OrderV2Created) {
            when((AbstractOrderV2Event.OrderV2Created)e);
        } else if (e instanceof AbstractOrderV2Event.OrderV2ItemRemoved) {
            when((AbstractOrderV2Event.OrderV2ItemRemoved)e);
        } else if (e instanceof AbstractOrderV2Event.OrderV2ItemQuantityUpdated) {
            when((AbstractOrderV2Event.OrderV2ItemQuantityUpdated)e);
        } else if (e instanceof AbstractOrderV2Event.OrderV2EstimatedShipDateUpdated) {
            when((AbstractOrderV2Event.OrderV2EstimatedShipDateUpdated)e);
        } else {
            throw new UnsupportedOperationException(String.format("Unsupported event type: %1$s", e.getClass().getName()));
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

    public void when(AbstractOrderV2Event.OrderV2Created e) {
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

        OrderV2State updatedOrderV2State = (OrderV2State) ReflectUtils.invokeStaticMethod(
                    "org.dddml.suidemocontracts.domain.orderv2.CreateLogic",
                    "mutate",
                    new Class[]{OrderV2State.class, String.class, BigInteger.class, BigInteger.class, BigInteger.class, String.class, MutationContext.class},
                    new Object[]{this, product, quantity, unitPrice, totalAmount, owner, MutationContext.forEvent(e, s -> {if (s == this) {return this;} else {throw new UnsupportedOperationException();}})}
            );

//package org.dddml.suidemocontracts.domain.orderv2;
//
//public class CreateLogic {
//    public static OrderV2State mutate(OrderV2State orderV2State, String product, BigInteger quantity, BigInteger unitPrice, BigInteger totalAmount, String owner, MutationContext<OrderV2State, OrderV2State.MutableOrderV2State> mutationContext) {
//    }
//}

        if (this != updatedOrderV2State) { merge(updatedOrderV2State); } //else do nothing

    }

    public void when(AbstractOrderV2Event.OrderV2ItemRemoved e) {
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

        OrderV2State updatedOrderV2State = (OrderV2State) ReflectUtils.invokeStaticMethod(
                    "org.dddml.suidemocontracts.domain.orderv2.RemoveItemLogic",
                    "mutate",
                    new Class[]{OrderV2State.class, String.class, MutationContext.class},
                    new Object[]{this, productId, MutationContext.forEvent(e, s -> {if (s == this) {return this;} else {throw new UnsupportedOperationException();}})}
            );

//package org.dddml.suidemocontracts.domain.orderv2;
//
//public class RemoveItemLogic {
//    public static OrderV2State mutate(OrderV2State orderV2State, String productId, MutationContext<OrderV2State, OrderV2State.MutableOrderV2State> mutationContext) {
//    }
//}

        if (this != updatedOrderV2State) { merge(updatedOrderV2State); } //else do nothing

    }

    public void when(AbstractOrderV2Event.OrderV2ItemQuantityUpdated e) {
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

        OrderV2State updatedOrderV2State = (OrderV2State) ReflectUtils.invokeStaticMethod(
                    "org.dddml.suidemocontracts.domain.orderv2.UpdateItemQuantityLogic",
                    "mutate",
                    new Class[]{OrderV2State.class, String.class, BigInteger.class, MutationContext.class},
                    new Object[]{this, productId, quantity, MutationContext.forEvent(e, s -> {if (s == this) {return this;} else {throw new UnsupportedOperationException();}})}
            );

//package org.dddml.suidemocontracts.domain.orderv2;
//
//public class UpdateItemQuantityLogic {
//    public static OrderV2State mutate(OrderV2State orderV2State, String productId, BigInteger quantity, MutationContext<OrderV2State, OrderV2State.MutableOrderV2State> mutationContext) {
//    }
//}

        if (this != updatedOrderV2State) { merge(updatedOrderV2State); } //else do nothing

    }

    public void when(AbstractOrderV2Event.OrderV2EstimatedShipDateUpdated e) {
        throwOnWrongEvent(e);

        Day estimatedShipDate = e.getEstimatedShipDate();
        Day EstimatedShipDate = estimatedShipDate;

        if (this.getCreatedBy() == null){
            this.setCreatedBy(e.getCreatedBy());
        }
        if (this.getCreatedAt() == null){
            this.setCreatedAt(e.getCreatedAt());
        }
        this.setUpdatedBy(e.getCreatedBy());
        this.setUpdatedAt(e.getCreatedAt());

        OrderV2State updatedOrderV2State = (OrderV2State) ReflectUtils.invokeStaticMethod(
                    "org.dddml.suidemocontracts.domain.orderv2.UpdateEstimatedShipDateLogic",
                    "mutate",
                    new Class[]{OrderV2State.class, Day.class, MutationContext.class},
                    new Object[]{this, estimatedShipDate, MutationContext.forEvent(e, s -> {if (s == this) {return this;} else {throw new UnsupportedOperationException();}})}
            );

//package org.dddml.suidemocontracts.domain.orderv2;
//
//public class UpdateEstimatedShipDateLogic {
//    public static OrderV2State mutate(OrderV2State orderV2State, Day estimatedShipDate, MutationContext<OrderV2State, OrderV2State.MutableOrderV2State> mutationContext) {
//    }
//}

        if (this != updatedOrderV2State) { merge(updatedOrderV2State); } //else do nothing

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

