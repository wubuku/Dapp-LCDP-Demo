package org.dddml.suidemocontracts.domain.orderv2;

import java.util.*;
import java.math.*;
import org.dddml.suidemocontracts.domain.*;
import java.util.Date;
import org.dddml.suidemocontracts.specialization.*;
import org.dddml.suidemocontracts.domain.orderv2.OrderItemShipGroupAssocSubitemEvent.*;

public abstract class AbstractOrderItemShipGroupAssocSubitemState implements OrderItemShipGroupAssocSubitemState.SqlOrderItemShipGroupAssocSubitemState {

    private OrderV2OrderItemShipGroupAssocSubitemId orderV2OrderItemShipGroupAssocSubitemId = new OrderV2OrderItemShipGroupAssocSubitemId();

    public OrderV2OrderItemShipGroupAssocSubitemId getOrderV2OrderItemShipGroupAssocSubitemId() {
        return this.orderV2OrderItemShipGroupAssocSubitemId;
    }

    public void setOrderV2OrderItemShipGroupAssocSubitemId(OrderV2OrderItemShipGroupAssocSubitemId orderV2OrderItemShipGroupAssocSubitemId) {
        this.orderV2OrderItemShipGroupAssocSubitemId = orderV2OrderItemShipGroupAssocSubitemId;
    }

    private transient OrderV2State orderV2State;

    public OrderV2State getOrderV2State() {
        return orderV2State;
    }

    public void setOrderV2State(OrderV2State s) {
        orderV2State = s;
    }
    
    private OrderItemShipGroupAssociationState protectedOrderItemShipGroupAssociationState;

    protected OrderItemShipGroupAssociationState getProtectedOrderItemShipGroupAssociationState() {
        return protectedOrderItemShipGroupAssociationState;
    }

    protected void setProtectedOrderItemShipGroupAssociationState(OrderItemShipGroupAssociationState protectedOrderItemShipGroupAssociationState) {
        this.protectedOrderItemShipGroupAssociationState = protectedOrderItemShipGroupAssociationState;
    }

    public String getOrderV2OrderId() {
        return this.getOrderV2OrderItemShipGroupAssocSubitemId().getOrderV2OrderId();
    }
        
    public void setOrderV2OrderId(String orderV2OrderId) {
        this.getOrderV2OrderItemShipGroupAssocSubitemId().setOrderV2OrderId(orderV2OrderId);
    }

    public Integer getOrderShipGroupShipGroupSeqId() {
        return this.getOrderV2OrderItemShipGroupAssocSubitemId().getOrderShipGroupShipGroupSeqId();
    }
        
    public void setOrderShipGroupShipGroupSeqId(Integer orderShipGroupShipGroupSeqId) {
        this.getOrderV2OrderItemShipGroupAssocSubitemId().setOrderShipGroupShipGroupSeqId(orderShipGroupShipGroupSeqId);
    }

    public String getOrderItemShipGroupAssociationProductId() {
        return this.getOrderV2OrderItemShipGroupAssocSubitemId().getOrderItemShipGroupAssociationProductId();
    }
        
    public void setOrderItemShipGroupAssociationProductId(String orderItemShipGroupAssociationProductId) {
        this.getOrderV2OrderItemShipGroupAssocSubitemId().setOrderItemShipGroupAssociationProductId(orderItemShipGroupAssociationProductId);
    }

    public Day getOrderItemShipGroupAssocSubitemDay() {
        return this.getOrderV2OrderItemShipGroupAssocSubitemId().getOrderItemShipGroupAssocSubitemDay();
    }
        
    public void setOrderItemShipGroupAssocSubitemDay(Day orderItemShipGroupAssocSubitemDay) {
        this.getOrderV2OrderItemShipGroupAssocSubitemId().setOrderItemShipGroupAssocSubitemDay(orderItemShipGroupAssocSubitemDay);
    }

    private String description;

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
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


    public AbstractOrderItemShipGroupAssocSubitemState() {
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
        return getOrderItemShipGroupAssocSubitemDay().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        if (obj instanceof OrderItemShipGroupAssocSubitemState) {
            return Objects.equals(this.getOrderItemShipGroupAssocSubitemDay(), ((OrderItemShipGroupAssocSubitemState)obj).getOrderItemShipGroupAssocSubitemDay());
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

    protected void merge(OrderItemShipGroupAssocSubitemState s) {
        if (s == this) {
            return;
        }
        this.setDescription(s.getDescription());
        this.setActive(s.getActive());
    }

    public void save() {
    }

    protected void throwOnWrongEvent(OrderItemShipGroupAssocSubitemEvent event) {
        String stateEntityIdOrderV2OrderId = this.getOrderV2OrderItemShipGroupAssocSubitemId().getOrderV2OrderId();
        String eventEntityIdOrderV2OrderId = ((OrderItemShipGroupAssocSubitemEvent.SqlOrderItemShipGroupAssocSubitemEvent)event).getOrderItemShipGroupAssocSubitemEventId().getOrderV2OrderId();
        if (!stateEntityIdOrderV2OrderId.equals(eventEntityIdOrderV2OrderId)) {
            throw DomainError.named("mutateWrongEntity", "Entity Id OrderV2OrderId %1$s in state but entity id OrderV2OrderId %2$s in event", stateEntityIdOrderV2OrderId, eventEntityIdOrderV2OrderId);
        }

        Integer stateEntityIdOrderShipGroupShipGroupSeqId = this.getOrderV2OrderItemShipGroupAssocSubitemId().getOrderShipGroupShipGroupSeqId();
        Integer eventEntityIdOrderShipGroupShipGroupSeqId = ((OrderItemShipGroupAssocSubitemEvent.SqlOrderItemShipGroupAssocSubitemEvent)event).getOrderItemShipGroupAssocSubitemEventId().getOrderShipGroupShipGroupSeqId();
        if (!stateEntityIdOrderShipGroupShipGroupSeqId.equals(eventEntityIdOrderShipGroupShipGroupSeqId)) {
            throw DomainError.named("mutateWrongEntity", "Entity Id OrderShipGroupShipGroupSeqId %1$s in state but entity id OrderShipGroupShipGroupSeqId %2$s in event", stateEntityIdOrderShipGroupShipGroupSeqId, eventEntityIdOrderShipGroupShipGroupSeqId);
        }

        String stateEntityIdOrderItemShipGroupAssociationProductId = this.getOrderV2OrderItemShipGroupAssocSubitemId().getOrderItemShipGroupAssociationProductId();
        String eventEntityIdOrderItemShipGroupAssociationProductId = ((OrderItemShipGroupAssocSubitemEvent.SqlOrderItemShipGroupAssocSubitemEvent)event).getOrderItemShipGroupAssocSubitemEventId().getOrderItemShipGroupAssociationProductId();
        if (!stateEntityIdOrderItemShipGroupAssociationProductId.equals(eventEntityIdOrderItemShipGroupAssociationProductId)) {
            throw DomainError.named("mutateWrongEntity", "Entity Id OrderItemShipGroupAssociationProductId %1$s in state but entity id OrderItemShipGroupAssociationProductId %2$s in event", stateEntityIdOrderItemShipGroupAssociationProductId, eventEntityIdOrderItemShipGroupAssociationProductId);
        }

        Day stateEntityIdOrderItemShipGroupAssocSubitemDay = this.getOrderV2OrderItemShipGroupAssocSubitemId().getOrderItemShipGroupAssocSubitemDay();
        Day eventEntityIdOrderItemShipGroupAssocSubitemDay = ((OrderItemShipGroupAssocSubitemEvent.SqlOrderItemShipGroupAssocSubitemEvent)event).getOrderItemShipGroupAssocSubitemEventId().getOrderItemShipGroupAssocSubitemDay();
        if (!stateEntityIdOrderItemShipGroupAssocSubitemDay.equals(eventEntityIdOrderItemShipGroupAssocSubitemDay)) {
            throw DomainError.named("mutateWrongEntity", "Entity Id OrderItemShipGroupAssocSubitemDay %1$s in state but entity id OrderItemShipGroupAssocSubitemDay %2$s in event", stateEntityIdOrderItemShipGroupAssocSubitemDay, eventEntityIdOrderItemShipGroupAssocSubitemDay);
        }


        if (getForReapplying()) { return; }

    }


    public static class SimpleOrderItemShipGroupAssocSubitemState extends AbstractOrderItemShipGroupAssocSubitemState {

        public SimpleOrderItemShipGroupAssocSubitemState() {
        }

        public static SimpleOrderItemShipGroupAssocSubitemState newForReapplying() {
            SimpleOrderItemShipGroupAssocSubitemState s = new SimpleOrderItemShipGroupAssocSubitemState();
            s.initializeForReapplying();
            return s;
        }

    }



}

