package org.dddml.suidemocontracts.domain.order;

import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.domain.AbstractEvent;

public class OrderItemEventDto extends AbstractEvent implements OrderItemEvent.OrderItemStateEvent {
    public static class OrderItemStateEventDto extends OrderItemEventDto {
    }

    private OrderItemEventId orderItemEventId;

    OrderItemEventId getOrderItemEventId() {
        if (orderItemEventId == null) { orderItemEventId = new OrderItemEventId(); }
        return orderItemEventId;
    }

    void setOrderItemEventId(OrderItemEventId eventId) {
        this.orderItemEventId = eventId;
    }

    public String getProductId() {
        return getOrderItemEventId().getProductId();
    }

    public void setProductId(String productId) {
        getOrderItemEventId().setProductId(productId);
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

    private Boolean active;

    public Boolean getActive() {
        return this.active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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

    private Long version;

    public Long getVersion() {
        return this.version;
    }

    public void setVersion(Long version) {
        this.version = version;
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

    public OrderItemEventDto toSubclass() {
        if (STATE_EVENT_TYPE_CREATED.equals(getEventType())) {
            OrderItemStateCreatedDto e = new OrderItemStateCreatedDto();
            copyTo(e);
            return e;
        }
        else if (STATE_EVENT_TYPE_MERGE_PATCHED.equals(getEventType())) {
            OrderItemStateMergePatchedDto e = new OrderItemStateMergePatchedDto();
            copyTo(e);
            return e;
        }
        else if (STATE_EVENT_TYPE_REMOVED.equals(getEventType())) {
            OrderItemStateRemovedDto e = new OrderItemStateRemovedDto();
            copyTo(e);
            return e;
        }

        throw new UnsupportedOperationException("Unknown event type:" + getEventType());
    }

    public void copyTo(OrderItemEventDto e) {
        e.setProductId(this.getProductId());
        e.setQuantity(this.getQuantity());
        e.setItemAmount(this.getItemAmount());
        e.setActive(this.getActive());
        e.setCreatedBy(this.getCreatedBy());
        e.setCreatedAt(this.getCreatedAt());
        e.setVersion(this.getVersion());
        e.setIsPropertyQuantityRemoved(this.getIsPropertyQuantityRemoved());
        e.setIsPropertyItemAmountRemoved(this.getIsPropertyItemAmountRemoved());
        e.setIsPropertyActiveRemoved(this.getIsPropertyActiveRemoved());
    }

	public static class OrderItemStateCreatedDto extends OrderItemEventDto implements OrderItemEvent.OrderItemStateCreated {

        @Override
        public String getEventType()
        {
            return STATE_EVENT_TYPE_CREATED;
        }

	}


	public static class OrderItemStateMergePatchedDto extends OrderItemStateEventDto implements OrderItemEvent.OrderItemStateMergePatched {

        @Override
        public String getEventType()
        {
            return STATE_EVENT_TYPE_MERGE_PATCHED;
        }

	}


	public static class OrderItemStateRemovedDto extends OrderItemStateEventDto implements OrderItemEvent.OrderItemStateRemoved {
        @Override
        public String getEventType()
        {
            return STATE_EVENT_TYPE_REMOVED;
        }


	}

}

