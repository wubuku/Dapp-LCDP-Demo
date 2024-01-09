// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.aptosdemocontracts.domain.order;

import java.util.*;
import java.math.*;
import java.math.BigInteger;
import org.dddml.aptosdemocontracts.domain.*;
import java.util.Date;
import org.dddml.aptosdemocontracts.specialization.*;
import org.dddml.aptosdemocontracts.domain.order.OrderEvent.*;

public abstract class AbstractOrderState implements OrderState.SqlOrderState, Saveable {

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

    private Set<OrderItemState> protectedItems = new HashSet<>();

    protected Set<OrderItemState> getProtectedItems() {
        return this.protectedItems;
    }

    protected void setProtectedItems(Set<OrderItemState> protectedItems) {
        this.protectedItems = protectedItems;
    }

    private EntityStateCollection<String, OrderItemState> items;

    public EntityStateCollection<String, OrderItemState> getItems() {
        return this.items;
    }

    public void setItems(EntityStateCollection<String, OrderItemState> items) {
        this.items = items;
    }

    private Set<OrderShipGroupState> protectedOrderShipGroups = new HashSet<>();

    protected Set<OrderShipGroupState> getProtectedOrderShipGroups() {
        return this.protectedOrderShipGroups;
    }

    protected void setProtectedOrderShipGroups(Set<OrderShipGroupState> protectedOrderShipGroups) {
        this.protectedOrderShipGroups = protectedOrderShipGroups;
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

    public AbstractOrderState(List<Event> events) {
        initializeForReapplying();
        if (events != null && events.size() > 0) {
            this.setOrderId(((OrderEvent.SqlOrderEvent) events.get(0)).getOrderEventId().getOrderId());
            for (Event e : events) {
                mutate(e);
                this.setOffChainVersion((this.getOffChainVersion() == null ? OrderState.VERSION_NULL : this.getOffChainVersion()) + 1);
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
        items = new SimpleOrderItemStateCollection();
        orderShipGroups = new SimpleOrderShipGroupStateCollection();
    }

    @Override
    public int hashCode() {
        return getOrderId().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        if (obj instanceof OrderState) {
            return Objects.equals(this.getOrderId(), ((OrderState)obj).getOrderId());
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
        } else if (e instanceof AbstractOrderEvent.OrderEstimatedShipDateUpdated) {
            when((AbstractOrderEvent.OrderEstimatedShipDateUpdated)e);
        } else if (e instanceof AbstractOrderEvent.OrderShipGroupAdded) {
            when((AbstractOrderEvent.OrderShipGroupAdded)e);
        } else if (e instanceof AbstractOrderEvent.OrderShipGroupQuantityCanceled) {
            when((AbstractOrderEvent.OrderShipGroupQuantityCanceled)e);
        } else if (e instanceof AbstractOrderEvent.OrderShipGroupItemRemoved) {
            when((AbstractOrderEvent.OrderShipGroupItemRemoved)e);
        } else if (e instanceof AbstractOrderEvent.OrderShipGroupRemoved) {
            when((AbstractOrderEvent.OrderShipGroupRemoved)e);
        } else {
            throw new UnsupportedOperationException(String.format("Unsupported event type: %1$s", e.getClass().getName()));
        }
    }

    public void merge(OrderState s) {
        if (s == this) {
            return;
        }
        this.setTotalAmount(s.getTotalAmount());
        this.setEstimatedShipDate(s.getEstimatedShipDate());
        this.setVersion(s.getVersion());
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
                    OrderItemState thisInnerState = ((EntityStateCollection.ModifiableEntityStateCollection<String, OrderItemState>)this.getItems()).getOrAddDefault(ss.getProductId());
                    ((AbstractOrderItemState) thisInnerState).merge(ss);
                }
            }
        }
        if (s.getItems() != null) {
            if (s.getItems() instanceof EntityStateCollection.RemovalLoggedEntityStateCollection) {
                if (((EntityStateCollection.RemovalLoggedEntityStateCollection)s.getItems()).getRemovedStates() != null) {
                    for (OrderItemState ss : ((EntityStateCollection.RemovalLoggedEntityStateCollection<String, OrderItemState>)s.getItems()).getRemovedStates()) {
                        OrderItemState thisInnerState = ((EntityStateCollection.ModifiableEntityStateCollection<String, OrderItemState>)this.getItems()).getOrAddDefault(ss.getProductId());
                        ((EntityStateCollection.ModifiableEntityStateCollection)this.getItems()).removeState(thisInnerState);
                    }
                }
            } else {
                if (s.getItems().isAllLoaded()) {
                    Set<String> removedStateIds = new HashSet<>(this.getItems().stream().map(i -> i.getProductId()).collect(java.util.stream.Collectors.toList()));
                    s.getItems().forEach(i -> removedStateIds.remove(i.getProductId()));
                    for (String i : removedStateIds) {
                        OrderItemState thisInnerState = ((EntityStateCollection.ModifiableEntityStateCollection<String, OrderItemState>)this.getItems()).getOrAddDefault(i);
                        ((EntityStateCollection.ModifiableEntityStateCollection)this.getItems()).removeState(thisInnerState);
                    }
                } else {
                    throw new UnsupportedOperationException();
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
                    OrderShipGroupState thisInnerState = ((EntityStateCollection.ModifiableEntityStateCollection<Integer, OrderShipGroupState>)this.getOrderShipGroups()).getOrAddDefault(ss.getShipGroupSeqId());
                    ((AbstractOrderShipGroupState) thisInnerState).merge(ss);
                }
            }
        }
        if (s.getOrderShipGroups() != null) {
            if (s.getOrderShipGroups() instanceof EntityStateCollection.RemovalLoggedEntityStateCollection) {
                if (((EntityStateCollection.RemovalLoggedEntityStateCollection)s.getOrderShipGroups()).getRemovedStates() != null) {
                    for (OrderShipGroupState ss : ((EntityStateCollection.RemovalLoggedEntityStateCollection<Integer, OrderShipGroupState>)s.getOrderShipGroups()).getRemovedStates()) {
                        OrderShipGroupState thisInnerState = ((EntityStateCollection.ModifiableEntityStateCollection<Integer, OrderShipGroupState>)this.getOrderShipGroups()).getOrAddDefault(ss.getShipGroupSeqId());
                        ((EntityStateCollection.ModifiableEntityStateCollection)this.getOrderShipGroups()).removeState(thisInnerState);
                    }
                }
            } else {
                if (s.getOrderShipGroups().isAllLoaded()) {
                    Set<Integer> removedStateIds = new HashSet<>(this.getOrderShipGroups().stream().map(i -> i.getShipGroupSeqId()).collect(java.util.stream.Collectors.toList()));
                    s.getOrderShipGroups().forEach(i -> removedStateIds.remove(i.getShipGroupSeqId()));
                    for (Integer i : removedStateIds) {
                        OrderShipGroupState thisInnerState = ((EntityStateCollection.ModifiableEntityStateCollection<Integer, OrderShipGroupState>)this.getOrderShipGroups()).getOrAddDefault(i);
                        ((EntityStateCollection.ModifiableEntityStateCollection)this.getOrderShipGroups()).removeState(thisInnerState);
                    }
                } else {
                    throw new UnsupportedOperationException();
                }
            }
        }
    }

    public void when(AbstractOrderEvent.OrderCreated e) {
        throwOnWrongEvent(e);

        String productId = e.getProductId();
        String ProductId = productId;
        BigInteger quantity = e.getQuantity();
        BigInteger Quantity = quantity;
        BigInteger unitPrice = e.getUnitPrice();
        BigInteger UnitPrice = unitPrice;
        BigInteger totalAmount = e.getTotalAmount();
        BigInteger TotalAmount = totalAmount;
        String owner = e.getOwner();
        String Owner = owner;
        BigInteger aptosEventVersion = e.getAptosEventVersion();
        BigInteger AptosEventVersion = aptosEventVersion;
        BigInteger aptosEventSequenceNumber = e.getAptosEventSequenceNumber();
        BigInteger AptosEventSequenceNumber = aptosEventSequenceNumber;
        String aptosEventType = e.getAptosEventType();
        String AptosEventType = aptosEventType;
        AptosEventGuid aptosEventGuid = e.getAptosEventGuid();
        AptosEventGuid AptosEventGuid = aptosEventGuid;
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

        OrderState updatedOrderState = (OrderState) ReflectUtils.invokeStaticMethod(
                    "org.dddml.aptosdemocontracts.domain.order.CreateLogic",
                    "mutate",
                    new Class[]{OrderState.class, String.class, BigInteger.class, BigInteger.class, BigInteger.class, String.class, BigInteger.class, BigInteger.class, String.class, AptosEventGuid.class, String.class, MutationContext.class},
                    new Object[]{this, productId, quantity, unitPrice, totalAmount, owner, aptosEventVersion, aptosEventSequenceNumber, aptosEventType, aptosEventGuid, status, MutationContext.forEvent(e, s -> {if (s == this) {return this;} else {throw new UnsupportedOperationException();}})}
            );

//package org.dddml.aptosdemocontracts.domain.order;
//
//public class CreateLogic {
//    public static OrderState mutate(OrderState orderState, String productId, BigInteger quantity, BigInteger unitPrice, BigInteger totalAmount, String owner, BigInteger aptosEventVersion, BigInteger aptosEventSequenceNumber, String aptosEventType, AptosEventGuid aptosEventGuid, String status, MutationContext<OrderState, OrderState.MutableOrderState> mutationContext) {
//    }
//}

        if (this != updatedOrderState) { merge(updatedOrderState); } //else do nothing

    }

    public void when(AbstractOrderEvent.OrderItemRemoved e) {
        throwOnWrongEvent(e);

        String productId = e.getProductId();
        String ProductId = productId;
        BigInteger aptosEventVersion = e.getAptosEventVersion();
        BigInteger AptosEventVersion = aptosEventVersion;
        BigInteger aptosEventSequenceNumber = e.getAptosEventSequenceNumber();
        BigInteger AptosEventSequenceNumber = aptosEventSequenceNumber;
        String aptosEventType = e.getAptosEventType();
        String AptosEventType = aptosEventType;
        AptosEventGuid aptosEventGuid = e.getAptosEventGuid();
        AptosEventGuid AptosEventGuid = aptosEventGuid;
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

        OrderState updatedOrderState = (OrderState) ReflectUtils.invokeStaticMethod(
                    "org.dddml.aptosdemocontracts.domain.order.RemoveItemLogic",
                    "mutate",
                    new Class[]{OrderState.class, String.class, BigInteger.class, BigInteger.class, String.class, AptosEventGuid.class, String.class, MutationContext.class},
                    new Object[]{this, productId, aptosEventVersion, aptosEventSequenceNumber, aptosEventType, aptosEventGuid, status, MutationContext.forEvent(e, s -> {if (s == this) {return this;} else {throw new UnsupportedOperationException();}})}
            );

//package org.dddml.aptosdemocontracts.domain.order;
//
//public class RemoveItemLogic {
//    public static OrderState mutate(OrderState orderState, String productId, BigInteger aptosEventVersion, BigInteger aptosEventSequenceNumber, String aptosEventType, AptosEventGuid aptosEventGuid, String status, MutationContext<OrderState, OrderState.MutableOrderState> mutationContext) {
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
        BigInteger aptosEventVersion = e.getAptosEventVersion();
        BigInteger AptosEventVersion = aptosEventVersion;
        BigInteger aptosEventSequenceNumber = e.getAptosEventSequenceNumber();
        BigInteger AptosEventSequenceNumber = aptosEventSequenceNumber;
        String aptosEventType = e.getAptosEventType();
        String AptosEventType = aptosEventType;
        AptosEventGuid aptosEventGuid = e.getAptosEventGuid();
        AptosEventGuid AptosEventGuid = aptosEventGuid;
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

        OrderState updatedOrderState = (OrderState) ReflectUtils.invokeStaticMethod(
                    "org.dddml.aptosdemocontracts.domain.order.UpdateItemQuantityLogic",
                    "mutate",
                    new Class[]{OrderState.class, String.class, BigInteger.class, BigInteger.class, BigInteger.class, String.class, AptosEventGuid.class, String.class, MutationContext.class},
                    new Object[]{this, productId, quantity, aptosEventVersion, aptosEventSequenceNumber, aptosEventType, aptosEventGuid, status, MutationContext.forEvent(e, s -> {if (s == this) {return this;} else {throw new UnsupportedOperationException();}})}
            );

//package org.dddml.aptosdemocontracts.domain.order;
//
//public class UpdateItemQuantityLogic {
//    public static OrderState mutate(OrderState orderState, String productId, BigInteger quantity, BigInteger aptosEventVersion, BigInteger aptosEventSequenceNumber, String aptosEventType, AptosEventGuid aptosEventGuid, String status, MutationContext<OrderState, OrderState.MutableOrderState> mutationContext) {
//    }
//}

        if (this != updatedOrderState) { merge(updatedOrderState); } //else do nothing

    }

    public void when(AbstractOrderEvent.OrderEstimatedShipDateUpdated e) {
        throwOnWrongEvent(e);

        Day estimatedShipDate = e.getEstimatedShipDate();
        Day EstimatedShipDate = estimatedShipDate;
        BigInteger aptosEventVersion = e.getAptosEventVersion();
        BigInteger AptosEventVersion = aptosEventVersion;
        BigInteger aptosEventSequenceNumber = e.getAptosEventSequenceNumber();
        BigInteger AptosEventSequenceNumber = aptosEventSequenceNumber;
        String aptosEventType = e.getAptosEventType();
        String AptosEventType = aptosEventType;
        AptosEventGuid aptosEventGuid = e.getAptosEventGuid();
        AptosEventGuid AptosEventGuid = aptosEventGuid;
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

        OrderState updatedOrderState = (OrderState) ReflectUtils.invokeStaticMethod(
                    "org.dddml.aptosdemocontracts.domain.order.UpdateEstimatedShipDateLogic",
                    "mutate",
                    new Class[]{OrderState.class, Day.class, BigInteger.class, BigInteger.class, String.class, AptosEventGuid.class, String.class, MutationContext.class},
                    new Object[]{this, estimatedShipDate, aptosEventVersion, aptosEventSequenceNumber, aptosEventType, aptosEventGuid, status, MutationContext.forEvent(e, s -> {if (s == this) {return this;} else {throw new UnsupportedOperationException();}})}
            );

//package org.dddml.aptosdemocontracts.domain.order;
//
//public class UpdateEstimatedShipDateLogic {
//    public static OrderState mutate(OrderState orderState, Day estimatedShipDate, BigInteger aptosEventVersion, BigInteger aptosEventSequenceNumber, String aptosEventType, AptosEventGuid aptosEventGuid, String status, MutationContext<OrderState, OrderState.MutableOrderState> mutationContext) {
//    }
//}

        if (this != updatedOrderState) { merge(updatedOrderState); } //else do nothing

    }

    public void when(AbstractOrderEvent.OrderShipGroupAdded e) {
        throwOnWrongEvent(e);

        Integer shipGroupSeqId = e.getShipGroupSeqId();
        Integer ShipGroupSeqId = shipGroupSeqId;
        String shipmentMethod = e.getShipmentMethod();
        String ShipmentMethod = shipmentMethod;
        String productId = e.getProductId();
        String ProductId = productId;
        BigInteger quantity = e.getQuantity();
        BigInteger Quantity = quantity;
        BigInteger aptosEventVersion = e.getAptosEventVersion();
        BigInteger AptosEventVersion = aptosEventVersion;
        BigInteger aptosEventSequenceNumber = e.getAptosEventSequenceNumber();
        BigInteger AptosEventSequenceNumber = aptosEventSequenceNumber;
        String aptosEventType = e.getAptosEventType();
        String AptosEventType = aptosEventType;
        AptosEventGuid aptosEventGuid = e.getAptosEventGuid();
        AptosEventGuid AptosEventGuid = aptosEventGuid;
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

        OrderState updatedOrderState = (OrderState) ReflectUtils.invokeStaticMethod(
                    "org.dddml.aptosdemocontracts.domain.order.AddOrderShipGroupLogic",
                    "mutate",
                    new Class[]{OrderState.class, Integer.class, String.class, String.class, BigInteger.class, BigInteger.class, BigInteger.class, String.class, AptosEventGuid.class, String.class, MutationContext.class},
                    new Object[]{this, shipGroupSeqId, shipmentMethod, productId, quantity, aptosEventVersion, aptosEventSequenceNumber, aptosEventType, aptosEventGuid, status, MutationContext.forEvent(e, s -> {if (s == this) {return this;} else {throw new UnsupportedOperationException();}})}
            );

//package org.dddml.aptosdemocontracts.domain.order;
//
//public class AddOrderShipGroupLogic {
//    public static OrderState mutate(OrderState orderState, Integer shipGroupSeqId, String shipmentMethod, String productId, BigInteger quantity, BigInteger aptosEventVersion, BigInteger aptosEventSequenceNumber, String aptosEventType, AptosEventGuid aptosEventGuid, String status, MutationContext<OrderState, OrderState.MutableOrderState> mutationContext) {
//    }
//}

        if (this != updatedOrderState) { merge(updatedOrderState); } //else do nothing

    }

    public void when(AbstractOrderEvent.OrderShipGroupQuantityCanceled e) {
        throwOnWrongEvent(e);

        Integer shipGroupSeqId = e.getShipGroupSeqId();
        Integer ShipGroupSeqId = shipGroupSeqId;
        String productId = e.getProductId();
        String ProductId = productId;
        BigInteger cancelQuantity = e.getCancelQuantity();
        BigInteger CancelQuantity = cancelQuantity;
        BigInteger aptosEventVersion = e.getAptosEventVersion();
        BigInteger AptosEventVersion = aptosEventVersion;
        BigInteger aptosEventSequenceNumber = e.getAptosEventSequenceNumber();
        BigInteger AptosEventSequenceNumber = aptosEventSequenceNumber;
        String aptosEventType = e.getAptosEventType();
        String AptosEventType = aptosEventType;
        AptosEventGuid aptosEventGuid = e.getAptosEventGuid();
        AptosEventGuid AptosEventGuid = aptosEventGuid;
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

        OrderState updatedOrderState = (OrderState) ReflectUtils.invokeStaticMethod(
                    "org.dddml.aptosdemocontracts.domain.order.CancelOrderShipGroupQuantityLogic",
                    "mutate",
                    new Class[]{OrderState.class, Integer.class, String.class, BigInteger.class, BigInteger.class, BigInteger.class, String.class, AptosEventGuid.class, String.class, MutationContext.class},
                    new Object[]{this, shipGroupSeqId, productId, cancelQuantity, aptosEventVersion, aptosEventSequenceNumber, aptosEventType, aptosEventGuid, status, MutationContext.forEvent(e, s -> {if (s == this) {return this;} else {throw new UnsupportedOperationException();}})}
            );

//package org.dddml.aptosdemocontracts.domain.order;
//
//public class CancelOrderShipGroupQuantityLogic {
//    public static OrderState mutate(OrderState orderState, Integer shipGroupSeqId, String productId, BigInteger cancelQuantity, BigInteger aptosEventVersion, BigInteger aptosEventSequenceNumber, String aptosEventType, AptosEventGuid aptosEventGuid, String status, MutationContext<OrderState, OrderState.MutableOrderState> mutationContext) {
//    }
//}

        if (this != updatedOrderState) { merge(updatedOrderState); } //else do nothing

    }

    public void when(AbstractOrderEvent.OrderShipGroupItemRemoved e) {
        throwOnWrongEvent(e);

        Integer shipGroupSeqId = e.getShipGroupSeqId();
        Integer ShipGroupSeqId = shipGroupSeqId;
        String productId = e.getProductId();
        String ProductId = productId;
        BigInteger aptosEventVersion = e.getAptosEventVersion();
        BigInteger AptosEventVersion = aptosEventVersion;
        BigInteger aptosEventSequenceNumber = e.getAptosEventSequenceNumber();
        BigInteger AptosEventSequenceNumber = aptosEventSequenceNumber;
        String aptosEventType = e.getAptosEventType();
        String AptosEventType = aptosEventType;
        AptosEventGuid aptosEventGuid = e.getAptosEventGuid();
        AptosEventGuid AptosEventGuid = aptosEventGuid;
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

        OrderState updatedOrderState = (OrderState) ReflectUtils.invokeStaticMethod(
                    "org.dddml.aptosdemocontracts.domain.order.RemoveOrderShipGroupItemLogic",
                    "mutate",
                    new Class[]{OrderState.class, Integer.class, String.class, BigInteger.class, BigInteger.class, String.class, AptosEventGuid.class, String.class, MutationContext.class},
                    new Object[]{this, shipGroupSeqId, productId, aptosEventVersion, aptosEventSequenceNumber, aptosEventType, aptosEventGuid, status, MutationContext.forEvent(e, s -> {if (s == this) {return this;} else {throw new UnsupportedOperationException();}})}
            );

//package org.dddml.aptosdemocontracts.domain.order;
//
//public class RemoveOrderShipGroupItemLogic {
//    public static OrderState mutate(OrderState orderState, Integer shipGroupSeqId, String productId, BigInteger aptosEventVersion, BigInteger aptosEventSequenceNumber, String aptosEventType, AptosEventGuid aptosEventGuid, String status, MutationContext<OrderState, OrderState.MutableOrderState> mutationContext) {
//    }
//}

        if (this != updatedOrderState) { merge(updatedOrderState); } //else do nothing

    }

    public void when(AbstractOrderEvent.OrderShipGroupRemoved e) {
        throwOnWrongEvent(e);

        Integer shipGroupSeqId = e.getShipGroupSeqId();
        Integer ShipGroupSeqId = shipGroupSeqId;
        BigInteger aptosEventVersion = e.getAptosEventVersion();
        BigInteger AptosEventVersion = aptosEventVersion;
        BigInteger aptosEventSequenceNumber = e.getAptosEventSequenceNumber();
        BigInteger AptosEventSequenceNumber = aptosEventSequenceNumber;
        String aptosEventType = e.getAptosEventType();
        String AptosEventType = aptosEventType;
        AptosEventGuid aptosEventGuid = e.getAptosEventGuid();
        AptosEventGuid AptosEventGuid = aptosEventGuid;
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

        OrderState updatedOrderState = (OrderState) ReflectUtils.invokeStaticMethod(
                    "org.dddml.aptosdemocontracts.domain.order.RemoveOrderShipGroupLogic",
                    "mutate",
                    new Class[]{OrderState.class, Integer.class, BigInteger.class, BigInteger.class, String.class, AptosEventGuid.class, String.class, MutationContext.class},
                    new Object[]{this, shipGroupSeqId, aptosEventVersion, aptosEventSequenceNumber, aptosEventType, aptosEventGuid, status, MutationContext.forEvent(e, s -> {if (s == this) {return this;} else {throw new UnsupportedOperationException();}})}
            );

//package org.dddml.aptosdemocontracts.domain.order;
//
//public class RemoveOrderShipGroupLogic {
//    public static OrderState mutate(OrderState orderState, Integer shipGroupSeqId, BigInteger aptosEventVersion, BigInteger aptosEventSequenceNumber, String aptosEventType, AptosEventGuid aptosEventGuid, String status, MutationContext<OrderState, OrderState.MutableOrderState> mutationContext) {
//    }
//}

        if (this != updatedOrderState) { merge(updatedOrderState); } //else do nothing

    }

    public void save() {
        if (items instanceof Saveable) {
            ((Saveable)items).save();
        }
        if (orderShipGroups instanceof Saveable) {
            ((Saveable)orderShipGroups).save();
        }
    }

    protected void throwOnWrongEvent(OrderEvent event) {
        String stateEntityId = this.getOrderId(); // Aggregate Id
        String eventEntityId = ((OrderEvent.SqlOrderEvent)event).getOrderEventId().getOrderId(); // EntityBase.Aggregate.GetEventIdPropertyIdName();
        if (!stateEntityId.equals(eventEntityId)) {
            throw DomainError.named("mutateWrongEntity", "Entity Id %1$s in state but entity id %2$s in event", stateEntityId, eventEntityId);
        }


        Long stateVersion = this.getOffChainVersion();

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


    class SimpleOrderItemStateCollection implements EntityStateCollection.ModifiableEntityStateCollection<String, OrderItemState>, Collection<OrderItemState> {

        @Override
        public OrderItemState get(String productId) {
            return protectedItems.stream().filter(
                            e -> e.getProductId().equals(productId))
                    .findFirst().orElse(null);
        }

        @Override
        public boolean isLazy() {
            return false;
        }

        @Override
        public boolean isAllLoaded() {
            return true;
        }

        @Override
        public Collection<OrderItemState> getLoadedStates() {
            return protectedItems;
        }

        @Override
        public OrderItemState getOrAddDefault(String productId) {
            OrderItemState s = get(productId);
            if (s == null) {
                OrderItemId globalId = new OrderItemId(getOrderId(), productId);
                AbstractOrderItemState state = new AbstractOrderItemState.SimpleOrderItemState();
                state.setOrderItemId(globalId);
                add(state);
                s = state;
            }
            return s;
        }

        @Override
        public int size() {
            return protectedItems.size();
        }

        @Override
        public boolean isEmpty() {
            return protectedItems.isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            return protectedItems.contains(o);
        }

        @Override
        public Iterator<OrderItemState> iterator() {
            return protectedItems.iterator();
        }

        @Override
        public java.util.stream.Stream<OrderItemState> stream() {
            return protectedItems.stream();
        }

        @Override
        public Object[] toArray() {
            return protectedItems.toArray();
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return protectedItems.toArray(a);
        }

        @Override
        public boolean add(OrderItemState s) {
            if (s instanceof AbstractOrderItemState) {
                AbstractOrderItemState state = (AbstractOrderItemState) s;
                state.setProtectedOrderState(AbstractOrderState.this);
            }
            return protectedItems.add(s);
        }

        @Override
        public boolean remove(Object o) {
            if (o instanceof AbstractOrderItemState) {
                AbstractOrderItemState s = (AbstractOrderItemState) o;
                s.setProtectedOrderState(null);
            }
            return protectedItems.remove(o);
        }

        @Override
        public boolean removeState(OrderItemState s) {
            return remove(s);
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return protectedItems.contains(c);
        }

        @Override
        public boolean addAll(Collection<? extends OrderItemState> c) {
            return protectedItems.addAll(c);
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return protectedItems.removeAll(c);
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return protectedItems.retainAll(c);
        }

        @Override
        public void clear() {
            protectedItems.clear();
        }
    }

    class SimpleOrderShipGroupStateCollection implements EntityStateCollection.ModifiableEntityStateCollection<Integer, OrderShipGroupState>, Collection<OrderShipGroupState> {

        @Override
        public OrderShipGroupState get(Integer shipGroupSeqId) {
            return protectedOrderShipGroups.stream().filter(
                            e -> e.getShipGroupSeqId().equals(shipGroupSeqId))
                    .findFirst().orElse(null);
        }

        @Override
        public boolean isLazy() {
            return false;
        }

        @Override
        public boolean isAllLoaded() {
            return true;
        }

        @Override
        public Collection<OrderShipGroupState> getLoadedStates() {
            return protectedOrderShipGroups;
        }

        @Override
        public OrderShipGroupState getOrAddDefault(Integer shipGroupSeqId) {
            OrderShipGroupState s = get(shipGroupSeqId);
            if (s == null) {
                OrderShipGroupId globalId = new OrderShipGroupId(getOrderId(), shipGroupSeqId);
                AbstractOrderShipGroupState state = new AbstractOrderShipGroupState.SimpleOrderShipGroupState();
                state.setOrderShipGroupId(globalId);
                add(state);
                s = state;
            }
            return s;
        }

        @Override
        public int size() {
            return protectedOrderShipGroups.size();
        }

        @Override
        public boolean isEmpty() {
            return protectedOrderShipGroups.isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            return protectedOrderShipGroups.contains(o);
        }

        @Override
        public Iterator<OrderShipGroupState> iterator() {
            return protectedOrderShipGroups.iterator();
        }

        @Override
        public java.util.stream.Stream<OrderShipGroupState> stream() {
            return protectedOrderShipGroups.stream();
        }

        @Override
        public Object[] toArray() {
            return protectedOrderShipGroups.toArray();
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return protectedOrderShipGroups.toArray(a);
        }

        @Override
        public boolean add(OrderShipGroupState s) {
            if (s instanceof AbstractOrderShipGroupState) {
                AbstractOrderShipGroupState state = (AbstractOrderShipGroupState) s;
                state.setProtectedOrderState(AbstractOrderState.this);
            }
            return protectedOrderShipGroups.add(s);
        }

        @Override
        public boolean remove(Object o) {
            if (o instanceof AbstractOrderShipGroupState) {
                AbstractOrderShipGroupState s = (AbstractOrderShipGroupState) o;
                s.setProtectedOrderState(null);
            }
            return protectedOrderShipGroups.remove(o);
        }

        @Override
        public boolean removeState(OrderShipGroupState s) {
            return remove(s);
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return protectedOrderShipGroups.contains(c);
        }

        @Override
        public boolean addAll(Collection<? extends OrderShipGroupState> c) {
            return protectedOrderShipGroups.addAll(c);
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return protectedOrderShipGroups.removeAll(c);
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return protectedOrderShipGroups.retainAll(c);
        }

        @Override
        public void clear() {
            protectedOrderShipGroups.clear();
        }
    }


}

