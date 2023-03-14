package org.dddml.suidemocontracts.domain.order;

import java.util.*;
import java.math.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.*;
import org.dddml.suidemocontracts.domain.order.OrderItemEvent.*;

public abstract class AbstractOrderItemState implements OrderItemState.SqlOrderItemState {

    private OrderItemId orderItemId = new OrderItemId();

    public OrderItemId getOrderItemId() {
        return this.orderItemId;
    }

    public void setOrderItemId(OrderItemId orderItemId) {
        this.orderItemId = orderItemId;
    }

    private transient OrderState orderState;

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState s) {
        orderState = s;
    }
    
    public String getOrderId() {
        return this.getOrderItemId().getOrderId();
    }
        
    public void setOrderId(String orderId) {
        this.getOrderItemId().setOrderId(orderId);
    }

    public String getProductId() {
        return this.getOrderItemId().getProductId();
    }
        
    public void setProductId(String productId) {
        this.getOrderItemId().setProductId(productId);
    }

    private BigInteger quantity;

    public BigInteger getQuantity() {
        return this.quantity;
    }

    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    }

    private BigInteger itemAmount;

    public BigInteger getItemAmount() {
        return this.itemAmount;
    }

    public void setItemAmount(BigInteger itemAmount) {
        this.itemAmount = itemAmount;
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


    public AbstractOrderItemState() {
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
        if (obj instanceof OrderItemState) {
            return Objects.equals(this.getProductId(), ((OrderItemState)obj).getProductId());
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

    protected void merge(OrderItemState s) {
        if (s == this) {
            return;
        }
        this.setQuantity(s.getQuantity());
        this.setItemAmount(s.getItemAmount());
        this.setActive(s.getActive());
    }

    public void save() {
    }

    protected void throwOnWrongEvent(OrderItemEvent event) {
        String stateEntityIdOrderId = this.getOrderItemId().getOrderId();
        String eventEntityIdOrderId = ((OrderItemEvent.SqlOrderItemEvent)event).getOrderItemEventId().getOrderId();
        if (!stateEntityIdOrderId.equals(eventEntityIdOrderId)) {
            throw DomainError.named("mutateWrongEntity", "Entity Id OrderId %1$s in state but entity id OrderId %2$s in event", stateEntityIdOrderId, eventEntityIdOrderId);
        }

        String stateEntityIdProductId = this.getOrderItemId().getProductId();
        String eventEntityIdProductId = ((OrderItemEvent.SqlOrderItemEvent)event).getOrderItemEventId().getProductId();
        if (!stateEntityIdProductId.equals(eventEntityIdProductId)) {
            throw DomainError.named("mutateWrongEntity", "Entity Id ProductId %1$s in state but entity id ProductId %2$s in event", stateEntityIdProductId, eventEntityIdProductId);
        }


        if (getForReapplying()) { return; }

    }


    public static class SimpleOrderItemState extends AbstractOrderItemState {

        public SimpleOrderItemState() {
        }

        public static SimpleOrderItemState newForReapplying() {
            SimpleOrderItemState s = new SimpleOrderItemState();
            s.initializeForReapplying();
            return s;
        }

    }



}

