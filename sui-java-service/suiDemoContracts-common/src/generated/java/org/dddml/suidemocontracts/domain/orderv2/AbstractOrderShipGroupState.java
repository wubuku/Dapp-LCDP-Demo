package org.dddml.suidemocontracts.domain.orderv2;

import java.util.*;
import java.math.*;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.*;
import org.dddml.suidemocontracts.domain.orderv2.OrderShipGroupEvent.*;

public abstract class AbstractOrderShipGroupState implements OrderShipGroupState.SqlOrderShipGroupState, Saveable {

    private OrderV2OrderShipGroupId orderV2OrderShipGroupId = new OrderV2OrderShipGroupId();

    public OrderV2OrderShipGroupId getOrderV2OrderShipGroupId() {
        return this.orderV2OrderShipGroupId;
    }

    public void setOrderV2OrderShipGroupId(OrderV2OrderShipGroupId orderV2OrderShipGroupId) {
        this.orderV2OrderShipGroupId = orderV2OrderShipGroupId;
    }

    private transient OrderV2State orderV2State;

    public OrderV2State getOrderV2State() {
        return orderV2State;
    }

    public void setOrderV2State(OrderV2State s) {
        orderV2State = s;
    }
    
    public String getOrderV2OrderId() {
        return this.getOrderV2OrderShipGroupId().getOrderV2OrderId();
    }
        
    public void setOrderV2OrderId(String orderV2OrderId) {
        this.getOrderV2OrderShipGroupId().setOrderV2OrderId(orderV2OrderId);
    }

    public Integer getShipGroupSeqId() {
        return this.getOrderV2OrderShipGroupId().getShipGroupSeqId();
    }
        
    public void setShipGroupSeqId(Integer shipGroupSeqId) {
        this.getOrderV2OrderShipGroupId().setShipGroupSeqId(shipGroupSeqId);
    }

    private String shipmentMethod;

    public String getShipmentMethod() {
        return this.shipmentMethod;
    }

    public void setShipmentMethod(String shipmentMethod) {
        this.shipmentMethod = shipmentMethod;
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

    private EntityStateCollection<String, OrderItemShipGroupAssociationState> orderItemShipGroupAssociations;

    public EntityStateCollection<String, OrderItemShipGroupAssociationState> getOrderItemShipGroupAssociations() {
        return this.orderItemShipGroupAssociations;
    }

    public void setOrderItemShipGroupAssociations(EntityStateCollection<String, OrderItemShipGroupAssociationState> orderItemShipGroupAssociations) {
        this.orderItemShipGroupAssociations = orderItemShipGroupAssociations;
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


    public AbstractOrderShipGroupState() {
        initializeProperties();
    }

    protected void initializeForReapplying() {
        this.forReapplying = true;

        initializeProperties();
    }
    
    protected void initializeProperties() {
        orderItemShipGroupAssociations = new SimpleOrderItemShipGroupAssociationStateCollection(this);
    }

    @Override
    public int hashCode() {
        return getShipGroupSeqId().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        if (obj instanceof OrderShipGroupState) {
            return Objects.equals(this.getShipGroupSeqId(), ((OrderShipGroupState)obj).getShipGroupSeqId());
        }
        return false;
    }


    public void mutate(Event e) {
        setStateReadOnly(false);
        if (false) { 
            ;
        } else {
            throw new UnsupportedOperationException(String.format("Unsupported event type: %1$s", e.getClass().getName()));
        }
    }

    protected void merge(OrderShipGroupState s) {
        if (s == this) {
            return;
        }
        this.setShipmentMethod(s.getShipmentMethod());
        this.setActive(s.getActive());

        if (s.getOrderItemShipGroupAssociations() != null) {
            Iterable<OrderItemShipGroupAssociationState> iterable;
            if (s.getOrderItemShipGroupAssociations().isLazy()) {
                iterable = s.getOrderItemShipGroupAssociations().getLoadedStates();
            } else {
                iterable = s.getOrderItemShipGroupAssociations();
            }
            if (iterable != null) {
                for (OrderItemShipGroupAssociationState ss : iterable) {
                    OrderItemShipGroupAssociationState thisInnerState = ((EntityStateCollection.ModifiableEntityStateCollection<String, OrderItemShipGroupAssociationState>)this.getOrderItemShipGroupAssociations()).getOrAdd(ss.getProductId());
                    ((AbstractOrderItemShipGroupAssociationState) thisInnerState).merge(ss);
                }
            }
        }
        if (s.getOrderItemShipGroupAssociations() != null) {
            if (s.getOrderItemShipGroupAssociations() instanceof EntityStateCollection.ModifiableEntityStateCollection) {
                if (((EntityStateCollection.ModifiableEntityStateCollection)s.getOrderItemShipGroupAssociations()).getRemovedStates() != null) {
                    for (OrderItemShipGroupAssociationState ss : ((EntityStateCollection.ModifiableEntityStateCollection<String, OrderItemShipGroupAssociationState>)s.getOrderItemShipGroupAssociations()).getRemovedStates()) {
                        OrderItemShipGroupAssociationState thisInnerState = ((EntityStateCollection.ModifiableEntityStateCollection<String, OrderItemShipGroupAssociationState>)this.getOrderItemShipGroupAssociations()).getOrAdd(ss.getProductId());
                        ((AbstractOrderItemShipGroupAssociationStateCollection)this.getOrderItemShipGroupAssociations()).remove(thisInnerState);
                    }
                }
            } else {
                if (s.getOrderItemShipGroupAssociations().isAllLoaded()) {
                    Set<String> removedStateIds = new HashSet<>(this.getOrderItemShipGroupAssociations().stream().map(i -> i.getProductId()).collect(java.util.stream.Collectors.toList()));
                    s.getOrderItemShipGroupAssociations().forEach(i -> removedStateIds.remove(i.getProductId()));
                    for (String i : removedStateIds) {
                        OrderItemShipGroupAssociationState thisInnerState = ((EntityStateCollection.ModifiableEntityStateCollection<String, OrderItemShipGroupAssociationState>)this.getOrderItemShipGroupAssociations()).getOrAdd(i);
                        ((AbstractOrderItemShipGroupAssociationStateCollection)this.getOrderItemShipGroupAssociations()).remove(thisInnerState);
                    }
                }
            }
        }
    }

    public void save() {
        ((Saveable)orderItemShipGroupAssociations).save();

    }

    protected void throwOnWrongEvent(OrderShipGroupEvent event) {
        String stateEntityIdOrderV2OrderId = this.getOrderV2OrderShipGroupId().getOrderV2OrderId();
        String eventEntityIdOrderV2OrderId = ((OrderShipGroupEvent.SqlOrderShipGroupEvent)event).getOrderShipGroupEventId().getOrderV2OrderId();
        if (!stateEntityIdOrderV2OrderId.equals(eventEntityIdOrderV2OrderId)) {
            throw DomainError.named("mutateWrongEntity", "Entity Id OrderV2OrderId %1$s in state but entity id OrderV2OrderId %2$s in event", stateEntityIdOrderV2OrderId, eventEntityIdOrderV2OrderId);
        }

        Integer stateEntityIdShipGroupSeqId = this.getOrderV2OrderShipGroupId().getShipGroupSeqId();
        Integer eventEntityIdShipGroupSeqId = ((OrderShipGroupEvent.SqlOrderShipGroupEvent)event).getOrderShipGroupEventId().getShipGroupSeqId();
        if (!stateEntityIdShipGroupSeqId.equals(eventEntityIdShipGroupSeqId)) {
            throw DomainError.named("mutateWrongEntity", "Entity Id ShipGroupSeqId %1$s in state but entity id ShipGroupSeqId %2$s in event", stateEntityIdShipGroupSeqId, eventEntityIdShipGroupSeqId);
        }


        if (getForReapplying()) { return; }

    }


    public static class SimpleOrderShipGroupState extends AbstractOrderShipGroupState {

        public SimpleOrderShipGroupState() {
        }

        public static SimpleOrderShipGroupState newForReapplying() {
            SimpleOrderShipGroupState s = new SimpleOrderShipGroupState();
            s.initializeForReapplying();
            return s;
        }

    }


    static class SimpleOrderItemShipGroupAssociationStateCollection extends AbstractOrderItemShipGroupAssociationStateCollection {
        public SimpleOrderItemShipGroupAssociationStateCollection(AbstractOrderShipGroupState outerState) {
            super(outerState);
        }
    }


}

