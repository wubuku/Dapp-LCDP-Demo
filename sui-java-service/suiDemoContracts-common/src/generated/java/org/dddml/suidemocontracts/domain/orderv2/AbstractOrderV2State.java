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

    private String id_;

    public String getId_() {
        return this.id_;
    }

    public void setId_(String id) {
        this.id_ = id;
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

    private BigInteger version;

    public BigInteger getVersion() {
        return this.version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    private Long offChainVersion;

    public Long getOffChainVersion() {
        return this.offChainVersion;
    }

    public void setOffChainVersion(Long offChainVersion) {
        this.offChainVersion = offChainVersion;
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
        return this.getOffChainVersion() == null;
    }

    private EntityStateCollection<String, OrderV2ItemState> items;

    public EntityStateCollection<String, OrderV2ItemState> getItems() {
        return this.items;
    }

    public void setItems(EntityStateCollection<String, OrderV2ItemState> items) {
        this.items = items;
    }

    private EntityStateCollection<Integer, OrderShipGroupState> orderShipGroups;

    public EntityStateCollection<Integer, OrderShipGroupState> getOrderShipGroups() {
        return this.orderShipGroups;
    }

    public void setOrderShipGroups(EntityStateCollection<Integer, OrderShipGroupState> orderShipGroups) {
        this.orderShipGroups = orderShipGroups;
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
                this.setOffChainVersion((this.getOffChainVersion() == null ? OrderV2State.VERSION_NULL : this.getOffChainVersion()) + 1);
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
        orderShipGroups = new SimpleOrderShipGroupStateCollection(this);
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
        this.setVersion(s.getVersion());
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

        if (s.getOrderShipGroups() != null) {
            Iterable<OrderShipGroupState> iterable;
            if (s.getOrderShipGroups().isLazy()) {
                iterable = s.getOrderShipGroups().getLoadedStates();
            } else {
                iterable = s.getOrderShipGroups();
            }
            if (iterable != null) {
                for (OrderShipGroupState ss : iterable) {
                    OrderShipGroupState thisInnerState = ((EntityStateCollection.ModifiableEntityStateCollection<Integer, OrderShipGroupState>)this.getOrderShipGroups()).getOrAdd(ss.getShipGroupSeqId());
                    ((AbstractOrderShipGroupState) thisInnerState).merge(ss);
                }
            }
        }
        if (s.getOrderShipGroups() != null) {
            if (s.getOrderShipGroups() instanceof EntityStateCollection.ModifiableEntityStateCollection) {
                if (((EntityStateCollection.ModifiableEntityStateCollection)s.getOrderShipGroups()).getRemovedStates() != null) {
                    for (OrderShipGroupState ss : ((EntityStateCollection.ModifiableEntityStateCollection<Integer, OrderShipGroupState>)s.getOrderShipGroups()).getRemovedStates()) {
                        OrderShipGroupState thisInnerState = ((EntityStateCollection.ModifiableEntityStateCollection<Integer, OrderShipGroupState>)this.getOrderShipGroups()).getOrAdd(ss.getShipGroupSeqId());
                        ((AbstractOrderShipGroupStateCollection)this.getOrderShipGroups()).remove(thisInnerState);
                    }
                }
            } else {
                if (s.getOrderShipGroups().isAllLoaded()) {
                    Set<Integer> removedStateIds = new HashSet<>(this.getOrderShipGroups().stream().map(i -> i.getShipGroupSeqId()).collect(java.util.stream.Collectors.toList()));
                    s.getOrderShipGroups().forEach(i -> removedStateIds.remove(i.getShipGroupSeqId()));
                    for (Integer i : removedStateIds) {
                        OrderShipGroupState thisInnerState = ((EntityStateCollection.ModifiableEntityStateCollection<Integer, OrderShipGroupState>)this.getOrderShipGroups()).getOrAdd(i);
                        ((AbstractOrderShipGroupStateCollection)this.getOrderShipGroups()).remove(thisInnerState);
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
        Long suiTimestamp = e.getSuiTimestamp();
        Long SuiTimestamp = suiTimestamp;
        String suiTxDigest = e.getSuiTxDigest();
        String SuiTxDigest = suiTxDigest;
        Long suiEventSeq = e.getSuiEventSeq();
        Long SuiEventSeq = suiEventSeq;
        String suiPackageId = e.getSuiPackageId();
        String SuiPackageId = suiPackageId;
        String suiTransactionModule = e.getSuiTransactionModule();
        String SuiTransactionModule = suiTransactionModule;
        String suiSender = e.getSuiSender();
        String SuiSender = suiSender;
        String suiType = e.getSuiType();
        String SuiType = suiType;
        String status = e.getStatus();
        String Status = status;

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
                    new Class[]{OrderV2State.class, String.class, BigInteger.class, BigInteger.class, BigInteger.class, String.class, Long.class, String.class, Long.class, String.class, String.class, String.class, String.class, String.class, MutationContext.class},
                    new Object[]{this, product, quantity, unitPrice, totalAmount, owner, suiTimestamp, suiTxDigest, suiEventSeq, suiPackageId, suiTransactionModule, suiSender, suiType, status, MutationContext.forEvent(e, s -> {if (s == this) {return this;} else {throw new UnsupportedOperationException();}})}
            );

//package org.dddml.suidemocontracts.domain.orderv2;
//
//public class CreateLogic {
//    public static OrderV2State mutate(OrderV2State orderV2State, String product, BigInteger quantity, BigInteger unitPrice, BigInteger totalAmount, String owner, Long suiTimestamp, String suiTxDigest, Long suiEventSeq, String suiPackageId, String suiTransactionModule, String suiSender, String suiType, String status, MutationContext<OrderV2State, OrderV2State.MutableOrderV2State> mutationContext) {
//    }
//}

        if (this != updatedOrderV2State) { merge(updatedOrderV2State); } //else do nothing

    }

    public void when(AbstractOrderV2Event.OrderV2ItemRemoved e) {
        throwOnWrongEvent(e);

        String productId = e.getProductId();
        String ProductId = productId;
        Long suiTimestamp = e.getSuiTimestamp();
        Long SuiTimestamp = suiTimestamp;
        String suiTxDigest = e.getSuiTxDigest();
        String SuiTxDigest = suiTxDigest;
        Long suiEventSeq = e.getSuiEventSeq();
        Long SuiEventSeq = suiEventSeq;
        String suiPackageId = e.getSuiPackageId();
        String SuiPackageId = suiPackageId;
        String suiTransactionModule = e.getSuiTransactionModule();
        String SuiTransactionModule = suiTransactionModule;
        String suiSender = e.getSuiSender();
        String SuiSender = suiSender;
        String suiType = e.getSuiType();
        String SuiType = suiType;
        String status = e.getStatus();
        String Status = status;

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
                    new Class[]{OrderV2State.class, String.class, Long.class, String.class, Long.class, String.class, String.class, String.class, String.class, String.class, MutationContext.class},
                    new Object[]{this, productId, suiTimestamp, suiTxDigest, suiEventSeq, suiPackageId, suiTransactionModule, suiSender, suiType, status, MutationContext.forEvent(e, s -> {if (s == this) {return this;} else {throw new UnsupportedOperationException();}})}
            );

//package org.dddml.suidemocontracts.domain.orderv2;
//
//public class RemoveItemLogic {
//    public static OrderV2State mutate(OrderV2State orderV2State, String productId, Long suiTimestamp, String suiTxDigest, Long suiEventSeq, String suiPackageId, String suiTransactionModule, String suiSender, String suiType, String status, MutationContext<OrderV2State, OrderV2State.MutableOrderV2State> mutationContext) {
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
        Long suiTimestamp = e.getSuiTimestamp();
        Long SuiTimestamp = suiTimestamp;
        String suiTxDigest = e.getSuiTxDigest();
        String SuiTxDigest = suiTxDigest;
        Long suiEventSeq = e.getSuiEventSeq();
        Long SuiEventSeq = suiEventSeq;
        String suiPackageId = e.getSuiPackageId();
        String SuiPackageId = suiPackageId;
        String suiTransactionModule = e.getSuiTransactionModule();
        String SuiTransactionModule = suiTransactionModule;
        String suiSender = e.getSuiSender();
        String SuiSender = suiSender;
        String suiType = e.getSuiType();
        String SuiType = suiType;
        String status = e.getStatus();
        String Status = status;

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
                    new Class[]{OrderV2State.class, String.class, BigInteger.class, Long.class, String.class, Long.class, String.class, String.class, String.class, String.class, String.class, MutationContext.class},
                    new Object[]{this, productId, quantity, suiTimestamp, suiTxDigest, suiEventSeq, suiPackageId, suiTransactionModule, suiSender, suiType, status, MutationContext.forEvent(e, s -> {if (s == this) {return this;} else {throw new UnsupportedOperationException();}})}
            );

//package org.dddml.suidemocontracts.domain.orderv2;
//
//public class UpdateItemQuantityLogic {
//    public static OrderV2State mutate(OrderV2State orderV2State, String productId, BigInteger quantity, Long suiTimestamp, String suiTxDigest, Long suiEventSeq, String suiPackageId, String suiTransactionModule, String suiSender, String suiType, String status, MutationContext<OrderV2State, OrderV2State.MutableOrderV2State> mutationContext) {
//    }
//}

        if (this != updatedOrderV2State) { merge(updatedOrderV2State); } //else do nothing

    }

    public void when(AbstractOrderV2Event.OrderV2EstimatedShipDateUpdated e) {
        throwOnWrongEvent(e);

        Day estimatedShipDate = e.getEstimatedShipDate();
        Day EstimatedShipDate = estimatedShipDate;
        Long suiTimestamp = e.getSuiTimestamp();
        Long SuiTimestamp = suiTimestamp;
        String suiTxDigest = e.getSuiTxDigest();
        String SuiTxDigest = suiTxDigest;
        Long suiEventSeq = e.getSuiEventSeq();
        Long SuiEventSeq = suiEventSeq;
        String suiPackageId = e.getSuiPackageId();
        String SuiPackageId = suiPackageId;
        String suiTransactionModule = e.getSuiTransactionModule();
        String SuiTransactionModule = suiTransactionModule;
        String suiSender = e.getSuiSender();
        String SuiSender = suiSender;
        String suiType = e.getSuiType();
        String SuiType = suiType;
        String status = e.getStatus();
        String Status = status;

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
                    new Class[]{OrderV2State.class, Day.class, Long.class, String.class, Long.class, String.class, String.class, String.class, String.class, String.class, MutationContext.class},
                    new Object[]{this, estimatedShipDate, suiTimestamp, suiTxDigest, suiEventSeq, suiPackageId, suiTransactionModule, suiSender, suiType, status, MutationContext.forEvent(e, s -> {if (s == this) {return this;} else {throw new UnsupportedOperationException();}})}
            );

//package org.dddml.suidemocontracts.domain.orderv2;
//
//public class UpdateEstimatedShipDateLogic {
//    public static OrderV2State mutate(OrderV2State orderV2State, Day estimatedShipDate, Long suiTimestamp, String suiTxDigest, Long suiEventSeq, String suiPackageId, String suiTransactionModule, String suiSender, String suiType, String status, MutationContext<OrderV2State, OrderV2State.MutableOrderV2State> mutationContext) {
//    }
//}

        if (this != updatedOrderV2State) { merge(updatedOrderV2State); } //else do nothing

    }

    public void save() {
        ((Saveable)items).save();

        ((Saveable)orderShipGroups).save();

    }

    protected void throwOnWrongEvent(OrderV2Event event) {
        String stateEntityId = this.getOrderId(); // Aggregate Id
        String eventEntityId = ((OrderV2Event.SqlOrderV2Event)event).getOrderV2EventId().getOrderId(); // EntityBase.Aggregate.GetEventIdPropertyIdName();
        if (!stateEntityId.equals(eventEntityId)) {
            throw DomainError.named("mutateWrongEntity", "Entity Id %1$s in state but entity id %2$s in event", stateEntityId, eventEntityId);
        }


        Long stateVersion = this.getOffChainVersion();

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

    static class SimpleOrderShipGroupStateCollection extends AbstractOrderShipGroupStateCollection {
        public SimpleOrderShipGroupStateCollection(AbstractOrderV2State outerState) {
            super(outerState);
        }
    }


}

