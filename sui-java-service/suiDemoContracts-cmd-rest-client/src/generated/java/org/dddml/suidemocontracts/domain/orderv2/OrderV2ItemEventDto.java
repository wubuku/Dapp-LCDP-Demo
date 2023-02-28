package org.dddml.suidemocontracts.domain.orderv2;

import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.domain.AbstractEvent;

public class OrderV2ItemEventDto extends AbstractEvent implements OrderV2ItemEvent.OrderV2ItemStateEvent {
    public static class OrderV2ItemStateEventDto extends OrderV2ItemEventDto {
    }

    private OrderV2ItemEventId orderV2ItemEventId;

    OrderV2ItemEventId getOrderV2ItemEventId() {
        if (orderV2ItemEventId == null) { orderV2ItemEventId = new OrderV2ItemEventId(); }
        return orderV2ItemEventId;
    }

    void setOrderV2ItemEventId(OrderV2ItemEventId eventId) {
        this.orderV2ItemEventId = eventId;
    }

    public String getProductId() {
        return getOrderV2ItemEventId().getProductId();
    }

    public void setProductId(String productId) {
        getOrderV2ItemEventId().setProductId(productId);
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

    public OrderV2ItemEventDto toSubclass() {
        if (STATE_EVENT_TYPE_CREATED.equals(getEventType())) {
            OrderV2ItemStateCreatedDto e = new OrderV2ItemStateCreatedDto();
            copyTo(e);
            return e;
        }
        else if (STATE_EVENT_TYPE_MERGE_PATCHED.equals(getEventType())) {
            OrderV2ItemStateMergePatchedDto e = new OrderV2ItemStateMergePatchedDto();
            copyTo(e);
            return e;
        }
        else if (STATE_EVENT_TYPE_REMOVED.equals(getEventType())) {
            OrderV2ItemStateRemovedDto e = new OrderV2ItemStateRemovedDto();
            copyTo(e);
            return e;
        }

        throw new UnsupportedOperationException("Unknown event type:" + getEventType());
    }

    public void copyTo(OrderV2ItemEventDto e) {
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

	public static class OrderV2ItemStateCreatedDto extends OrderV2ItemEventDto implements OrderV2ItemEvent.OrderV2ItemStateCreated {

        @Override
        public String getEventType()
        {
            return STATE_EVENT_TYPE_CREATED;
        }

	}


	public static class OrderV2ItemStateMergePatchedDto extends OrderV2ItemStateEventDto implements OrderV2ItemEvent.OrderV2ItemStateMergePatched {

        @Override
        public String getEventType()
        {
            return STATE_EVENT_TYPE_MERGE_PATCHED;
        }

	}


	public static class OrderV2ItemStateRemovedDto extends OrderV2ItemStateEventDto implements OrderV2ItemEvent.OrderV2ItemStateRemoved {
        @Override
        public String getEventType()
        {
            return STATE_EVENT_TYPE_REMOVED;
        }


	}

}

