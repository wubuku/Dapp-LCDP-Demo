package org.dddml.suidemocontracts.domain.orderv2;

import java.math.BigInteger;
import java.util.*;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.*;
import org.dddml.suidemocontracts.domain.orderv2.OrderItemShipGroupAssociationEvent.*;

public abstract class AbstractOrderItemShipGroupAssociationState implements OrderItemShipGroupAssociationState.SqlOrderItemShipGroupAssociationState {

    private OrderV2OrderItemShipGroupAssociationId orderV2OrderItemShipGroupAssociationId = new OrderV2OrderItemShipGroupAssociationId();

    public OrderV2OrderItemShipGroupAssociationId getOrderV2OrderItemShipGroupAssociationId() {
        return this.orderV2OrderItemShipGroupAssociationId;
    }

    public void setOrderV2OrderItemShipGroupAssociationId(OrderV2OrderItemShipGroupAssociationId orderV2OrderItemShipGroupAssociationId) {
        this.orderV2OrderItemShipGroupAssociationId = orderV2OrderItemShipGroupAssociationId;
    }

    private transient OrderV2State orderV2State;

    public OrderV2State getOrderV2State() {
        return orderV2State;
    }

    public void setOrderV2State(OrderV2State s) {
        orderV2State = s;
    }
    
    public String getOrderV2OrderId() {
        return this.getOrderV2OrderItemShipGroupAssociationId().getOrderV2OrderId();
    }
        
    public void setOrderV2OrderId(String orderV2OrderId) {
        this.getOrderV2OrderItemShipGroupAssociationId().setOrderV2OrderId(orderV2OrderId);
    }

    public Integer getOrderShipGroupShipGroupSeqId() {
        return this.getOrderV2OrderItemShipGroupAssociationId().getOrderShipGroupShipGroupSeqId();
    }
        
    public void setOrderShipGroupShipGroupSeqId(Integer orderShipGroupShipGroupSeqId) {
        this.getOrderV2OrderItemShipGroupAssociationId().setOrderShipGroupShipGroupSeqId(orderShipGroupShipGroupSeqId);
    }

    public String getProductId() {
        return this.getOrderV2OrderItemShipGroupAssociationId().getProductId();
    }
        
    public void setProductId(String productId) {
        this.getOrderV2OrderItemShipGroupAssociationId().setProductId(productId);
    }

    private BigInteger quantity;

    public BigInteger getQuantity() {
        return this.quantity;
    }

    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    }

    private BigInteger cancelQuantity;

    public BigInteger getCancelQuantity() {
        return this.cancelQuantity;
    }

    public void setCancelQuantity(BigInteger cancelQuantity) {
        this.cancelQuantity = cancelQuantity;
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


    public AbstractOrderItemShipGroupAssociationState() {
        initializeProperties();
    }

    protected void initializeForReapplying() {
        this.forReapplying = true;

        initializeProperties();
    }
    
    protected void initializeProperties() {
    }

    @Override
    public int hashCode() {
        return getProductId().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        if (obj instanceof OrderItemShipGroupAssociationState) {
            return Objects.equals(this.getProductId(), ((OrderItemShipGroupAssociationState)obj).getProductId());
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

    protected void merge(OrderItemShipGroupAssociationState s) {
        if (s == this) {
            return;
        }
        this.setQuantity(s.getQuantity());
        this.setCancelQuantity(s.getCancelQuantity());
        this.setActive(s.getActive());
    }

    public void save() {
    }

    protected void throwOnWrongEvent(OrderItemShipGroupAssociationEvent event) {
        String stateEntityIdOrderV2OrderId = this.getOrderV2OrderItemShipGroupAssociationId().getOrderV2OrderId();
        String eventEntityIdOrderV2OrderId = ((OrderItemShipGroupAssociationEvent.SqlOrderItemShipGroupAssociationEvent)event).getOrderItemShipGroupAssociationEventId().getOrderV2OrderId();
        if (!stateEntityIdOrderV2OrderId.equals(eventEntityIdOrderV2OrderId)) {
            throw DomainError.named("mutateWrongEntity", "Entity Id OrderV2OrderId %1$s in state but entity id OrderV2OrderId %2$s in event", stateEntityIdOrderV2OrderId, eventEntityIdOrderV2OrderId);
        }

        Integer stateEntityIdOrderShipGroupShipGroupSeqId = this.getOrderV2OrderItemShipGroupAssociationId().getOrderShipGroupShipGroupSeqId();
        Integer eventEntityIdOrderShipGroupShipGroupSeqId = ((OrderItemShipGroupAssociationEvent.SqlOrderItemShipGroupAssociationEvent)event).getOrderItemShipGroupAssociationEventId().getOrderShipGroupShipGroupSeqId();
        if (!stateEntityIdOrderShipGroupShipGroupSeqId.equals(eventEntityIdOrderShipGroupShipGroupSeqId)) {
            throw DomainError.named("mutateWrongEntity", "Entity Id OrderShipGroupShipGroupSeqId %1$s in state but entity id OrderShipGroupShipGroupSeqId %2$s in event", stateEntityIdOrderShipGroupShipGroupSeqId, eventEntityIdOrderShipGroupShipGroupSeqId);
        }

        String stateEntityIdProductId = this.getOrderV2OrderItemShipGroupAssociationId().getProductId();
        String eventEntityIdProductId = ((OrderItemShipGroupAssociationEvent.SqlOrderItemShipGroupAssociationEvent)event).getOrderItemShipGroupAssociationEventId().getProductId();
        if (!stateEntityIdProductId.equals(eventEntityIdProductId)) {
            throw DomainError.named("mutateWrongEntity", "Entity Id ProductId %1$s in state but entity id ProductId %2$s in event", stateEntityIdProductId, eventEntityIdProductId);
        }


        if (getForReapplying()) { return; }

    }


    public static class SimpleOrderItemShipGroupAssociationState extends AbstractOrderItemShipGroupAssociationState {

        public SimpleOrderItemShipGroupAssociationState() {
        }

        public static SimpleOrderItemShipGroupAssociationState newForReapplying() {
            SimpleOrderItemShipGroupAssociationState s = new SimpleOrderItemShipGroupAssociationState();
            s.initializeForReapplying();
            return s;
        }

    }



}

