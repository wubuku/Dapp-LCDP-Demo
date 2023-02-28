package org.dddml.suidemocontracts.domain.orderv2;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.*;
import org.dddml.suidemocontracts.domain.AbstractEvent;

public abstract class AbstractOrderV2ItemEvent extends AbstractEvent implements OrderV2ItemEvent.SqlOrderV2ItemEvent 
{
    private OrderV2ItemEventId orderV2ItemEventId;

    public OrderV2ItemEventId getOrderV2ItemEventId() {
        return this.orderV2ItemEventId;
    }

    public void setOrderV2ItemEventId(OrderV2ItemEventId eventId) {
        this.orderV2ItemEventId = eventId;
    }
    
    public String getProductId() {
        return getOrderV2ItemEventId().getProductId();
    }

    public void setProductId(String productId) {
        getOrderV2ItemEventId().setProductId(productId);
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

    protected AbstractOrderV2ItemEvent() {
    }

    protected AbstractOrderV2ItemEvent(OrderV2ItemEventId eventId) {
        this.orderV2ItemEventId = eventId;
    }


    public abstract String getEventType();


    public static abstract class AbstractOrderV2ItemStateEvent extends AbstractOrderV2ItemEvent implements OrderV2ItemEvent.OrderV2ItemStateEvent {
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

        protected AbstractOrderV2ItemStateEvent(OrderV2ItemEventId eventId) {
            super(eventId);
        }
    }

    public static abstract class AbstractOrderV2ItemStateCreated extends AbstractOrderV2ItemStateEvent implements OrderV2ItemEvent.OrderV2ItemStateCreated
    {
        public AbstractOrderV2ItemStateCreated() {
            this(new OrderV2ItemEventId());
        }

        public AbstractOrderV2ItemStateCreated(OrderV2ItemEventId eventId) {
            super(eventId);
        }

        public String getEventType() {
            return StateEventType.CREATED;
        }

    }


    public static abstract class AbstractOrderV2ItemStateMergePatched extends AbstractOrderV2ItemStateEvent implements OrderV2ItemEvent.OrderV2ItemStateMergePatched
    {
        public AbstractOrderV2ItemStateMergePatched() {
            this(new OrderV2ItemEventId());
        }

        public AbstractOrderV2ItemStateMergePatched(OrderV2ItemEventId eventId) {
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


    public static abstract class AbstractOrderV2ItemStateRemoved extends AbstractOrderV2ItemStateEvent implements OrderV2ItemEvent.OrderV2ItemStateRemoved
    {
        public AbstractOrderV2ItemStateRemoved() {
            this(new OrderV2ItemEventId());
        }

        public AbstractOrderV2ItemStateRemoved(OrderV2ItemEventId eventId) {
            super(eventId);
        }

        public String getEventType() {
            return StateEventType.REMOVED;
        }

    }

    public static class SimpleOrderV2ItemStateCreated extends AbstractOrderV2ItemStateCreated
    {
        public SimpleOrderV2ItemStateCreated() {
        }

        public SimpleOrderV2ItemStateCreated(OrderV2ItemEventId eventId) {
            super(eventId);
        }
    }

    public static class SimpleOrderV2ItemStateMergePatched extends AbstractOrderV2ItemStateMergePatched
    {
        public SimpleOrderV2ItemStateMergePatched() {
        }

        public SimpleOrderV2ItemStateMergePatched(OrderV2ItemEventId eventId) {
            super(eventId);
        }
    }

    public static class SimpleOrderV2ItemStateRemoved extends AbstractOrderV2ItemStateRemoved
    {
        public SimpleOrderV2ItemStateRemoved() {
        }

        public SimpleOrderV2ItemStateRemoved(OrderV2ItemEventId eventId) {
            super(eventId);
        }
    }

}

