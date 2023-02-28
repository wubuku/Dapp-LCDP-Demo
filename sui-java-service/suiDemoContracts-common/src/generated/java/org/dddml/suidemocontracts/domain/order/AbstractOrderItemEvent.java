package org.dddml.suidemocontracts.domain.order;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.*;
import org.dddml.suidemocontracts.domain.AbstractEvent;

public abstract class AbstractOrderItemEvent extends AbstractEvent implements OrderItemEvent.SqlOrderItemEvent 
{
    private OrderItemEventId orderItemEventId;

    public OrderItemEventId getOrderItemEventId() {
        return this.orderItemEventId;
    }

    public void setOrderItemEventId(OrderItemEventId eventId) {
        this.orderItemEventId = eventId;
    }
    
    public String getProductId() {
        return getOrderItemEventId().getProductId();
    }

    public void setProductId(String productId) {
        getOrderItemEventId().setProductId(productId);
    }

    private boolean eventReadOnly;

    public boolean getEventReadOnly() { return this.eventReadOnly; }

    public void setEventReadOnly(boolean readOnly) { this.eventReadOnly = readOnly; }

    private String createdBy;

    public String getCreatedBy()
    {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }

    private Date createdAt;

    public Date getCreatedAt()
    {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt)
    {
        this.createdAt = createdAt;
    }


    private String commandId;

    public String getCommandId() {
        return commandId;
    }

    public void setCommandId(String commandId) {
        this.commandId = commandId;
    }

    protected AbstractOrderItemEvent() {
    }

    protected AbstractOrderItemEvent(OrderItemEventId eventId) {
        this.orderItemEventId = eventId;
    }


    public abstract String getEventType();


    public static abstract class AbstractOrderItemStateEvent extends AbstractOrderItemEvent implements OrderItemEvent.OrderItemStateEvent {
        private Long version;

        public Long getVersion()
        {
            return this.version;
        }

        public void setVersion(Long version)
        {
            this.version = version;
        }

        private BigInteger quantity;

        public BigInteger getQuantity()
        {
            return this.quantity;
        }

        public void setQuantity(BigInteger quantity)
        {
            this.quantity = quantity;
        }

        private BigInteger itemAmount;

        public BigInteger getItemAmount()
        {
            return this.itemAmount;
        }

        public void setItemAmount(BigInteger itemAmount)
        {
            this.itemAmount = itemAmount;
        }

        private Boolean active;

        public Boolean getActive()
        {
            return this.active;
        }

        public void setActive(Boolean active)
        {
            this.active = active;
        }

        protected AbstractOrderItemStateEvent(OrderItemEventId eventId) {
            super(eventId);
        }
    }

    public static abstract class AbstractOrderItemStateCreated extends AbstractOrderItemStateEvent implements OrderItemEvent.OrderItemStateCreated
    {
        public AbstractOrderItemStateCreated() {
            this(new OrderItemEventId());
        }

        public AbstractOrderItemStateCreated(OrderItemEventId eventId) {
            super(eventId);
        }

        public String getEventType() {
            return StateEventType.CREATED;
        }

    }


    public static abstract class AbstractOrderItemStateMergePatched extends AbstractOrderItemStateEvent implements OrderItemEvent.OrderItemStateMergePatched
    {
        public AbstractOrderItemStateMergePatched() {
            this(new OrderItemEventId());
        }

        public AbstractOrderItemStateMergePatched(OrderItemEventId eventId) {
            super(eventId);
        }

        public String getEventType() {
            return StateEventType.MERGE_PATCHED;
        }

        private Boolean isPropertyQuantityRemoved;

        public Boolean getIsPropertyQuantityRemoved() {
            return this.isPropertyQuantityRemoved;
        }

        public void setIsPropertyQuantityRemoved(Boolean removed) {
            this.isPropertyQuantityRemoved = removed;
        }

        private Boolean isPropertyItemAmountRemoved;

        public Boolean getIsPropertyItemAmountRemoved() {
            return this.isPropertyItemAmountRemoved;
        }

        public void setIsPropertyItemAmountRemoved(Boolean removed) {
            this.isPropertyItemAmountRemoved = removed;
        }

        private Boolean isPropertyActiveRemoved;

        public Boolean getIsPropertyActiveRemoved() {
            return this.isPropertyActiveRemoved;
        }

        public void setIsPropertyActiveRemoved(Boolean removed) {
            this.isPropertyActiveRemoved = removed;
        }


    }


    public static abstract class AbstractOrderItemStateRemoved extends AbstractOrderItemStateEvent implements OrderItemEvent.OrderItemStateRemoved
    {
        public AbstractOrderItemStateRemoved() {
            this(new OrderItemEventId());
        }

        public AbstractOrderItemStateRemoved(OrderItemEventId eventId) {
            super(eventId);
        }

        public String getEventType() {
            return StateEventType.REMOVED;
        }

    }

    public static class SimpleOrderItemStateCreated extends AbstractOrderItemStateCreated
    {
        public SimpleOrderItemStateCreated() {
        }

        public SimpleOrderItemStateCreated(OrderItemEventId eventId) {
            super(eventId);
        }
    }

    public static class SimpleOrderItemStateMergePatched extends AbstractOrderItemStateMergePatched
    {
        public SimpleOrderItemStateMergePatched() {
        }

        public SimpleOrderItemStateMergePatched(OrderItemEventId eventId) {
            super(eventId);
        }
    }

    public static class SimpleOrderItemStateRemoved extends AbstractOrderItemStateRemoved
    {
        public SimpleOrderItemStateRemoved() {
        }

        public SimpleOrderItemStateRemoved(OrderItemEventId eventId) {
            super(eventId);
        }
    }

}

