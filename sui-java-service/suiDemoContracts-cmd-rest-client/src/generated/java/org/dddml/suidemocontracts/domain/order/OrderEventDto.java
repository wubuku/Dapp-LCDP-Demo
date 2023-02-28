package org.dddml.suidemocontracts.domain.order;

import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.domain.AbstractEvent;

public class OrderEventDto extends AbstractEvent implements OrderEvent.OrderStateEvent {
    public static class OrderStateEventDto extends OrderEventDto {
    }

    private OrderEventId orderEventId;

    OrderEventId getOrderEventId() {
        if (orderEventId == null) { orderEventId = new OrderEventId(); }
        return orderEventId;
    }

    void setOrderEventId(OrderEventId eventId) {
        this.orderEventId = eventId;
    }

    public String getId() {
        return getOrderEventId().getId();
    }

    public void setId(String id) {
        getOrderEventId().setId(id);
    }

    public Long getVersion() {
        return getOrderEventId().getVersion();
    }
    
    public void setVersion(Long version) {
        getOrderEventId().setVersion(version);
    }

    private BigInteger totalAmount;

    public BigInteger getTotalAmount() {
        return this.totalAmount;
    }

    public void setTotalAmount(BigInteger totalAmount) {
        this.totalAmount = totalAmount;
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

    private Boolean isPropertyTotalAmountRemoved;

    public Boolean getIsPropertyTotalAmountRemoved() {
        return this.isPropertyTotalAmountRemoved;
    }

    public void setIsPropertyTotalAmountRemoved(Boolean removed) {
        this.isPropertyTotalAmountRemoved = removed;
    }

    private Boolean isPropertyActiveRemoved;

    public Boolean getIsPropertyActiveRemoved() {
        return this.isPropertyActiveRemoved;
    }

    public void setIsPropertyActiveRemoved(Boolean removed) {
        this.isPropertyActiveRemoved = removed;
    }

    protected OrderItemEventDto[] orderItemEvents;

    //public OrderItemEventDto[] getOrderItemEventArray() {
    //    return this.orderItemEvents;
    //}

    public void setOrderItemEvents(OrderItemEventDto[] events) {
        this.orderItemEvents = events;
    }

    public OrderEventDto toSubclass() {
        if (STATE_EVENT_TYPE_CREATED.equals(getEventType())) {
            OrderStateCreatedDto e = new OrderStateCreatedDto();
            copyTo(e);
            return e;
        }
        else if (STATE_EVENT_TYPE_MERGE_PATCHED.equals(getEventType())) {
            OrderStateMergePatchedDto e = new OrderStateMergePatchedDto();
            copyTo(e);
            return e;
        }
        else if (STATE_EVENT_TYPE_DELETED.equals(getEventType())) {
            OrderStateDeletedDto e = new OrderStateDeletedDto();
            copyTo(e);
            return e;
        }

        throw new UnsupportedOperationException("Unknown event type:" + getEventType());
    }

    public void copyTo(OrderEventDto e) {
        e.setId(this.getId());
        e.setVersion(this.getVersion());
        e.setTotalAmount(this.getTotalAmount());
        e.setActive(this.getActive());
        e.setCreatedBy(this.getCreatedBy());
        e.setCreatedAt(this.getCreatedAt());
        e.setIsPropertyTotalAmountRemoved(this.getIsPropertyTotalAmountRemoved());
        e.setIsPropertyActiveRemoved(this.getIsPropertyActiveRemoved());
        if (this.orderItemEvents != null) {
            OrderItemEventDto[] orderItemStateEventDtos = new OrderItemEventDto[this.orderItemEvents.length];
            for (int i = 0; i < this.orderItemEvents.length; i++) {
                orderItemStateEventDtos[i] = this.orderItemEvents[i].toSubclass();
            }
            e.orderItemEvents = orderItemStateEventDtos;
        }
    }

	public static class OrderStateCreatedDto extends OrderEventDto implements OrderEvent.OrderStateCreated {

        @Override
        public String getEventType()
        {
            return STATE_EVENT_TYPE_CREATED;
        }

        @Override
        public Iterable<OrderItemEvent.OrderItemStateCreated> getOrderItemEvents() {
            if (orderItemEvents == null) return null;
            return java.util.Arrays.stream(orderItemEvents).map(i -> (OrderItemEvent.OrderItemStateCreated)i).collect(java.util.stream.Collectors.toList());
        }

        @Override
        public void addOrderItemEvent(OrderItemEvent.OrderItemStateCreated e) {
            java.util.List<OrderItemEventDto> events = orderItemEvents == null ? new java.util.ArrayList<>() : new java.util.ArrayList<>(java.util.Arrays.asList(orderItemEvents));
            events.add((OrderItemEventDto)e);
            orderItemEvents = events.toArray(new OrderItemEventDto[0]);
        }

        @Override
        public OrderItemEvent.OrderItemStateCreated newOrderItemStateCreated(String productId) {
            return new OrderItemEventDto.OrderItemStateCreatedDto();
        }

	}


	public static class OrderStateMergePatchedDto extends OrderStateEventDto implements OrderEvent.OrderStateMergePatched {

        @Override
        public String getEventType()
        {
            return STATE_EVENT_TYPE_MERGE_PATCHED;
        }

        @Override
        public Iterable<OrderItemEvent> getOrderItemEvents() {
            if (orderItemEvents == null) return null;
            return java.util.Arrays.stream(orderItemEvents).map(i -> (OrderItemEvent)i).collect(java.util.stream.Collectors.toList());
        }
        
        @Override
        public void addOrderItemEvent(OrderItemEvent e) {
            java.util.List<OrderItemEventDto> events = orderItemEvents == null ? new java.util.ArrayList<>() : new java.util.ArrayList<>(java.util.Arrays.asList(orderItemEvents));
            events.add((OrderItemEventDto)e);
            orderItemEvents = events.toArray(new OrderItemEventDto[0]);
        }

        @Override
        public OrderItemEvent.OrderItemStateCreated newOrderItemStateCreated(String productId) {
            return new OrderItemEventDto.OrderItemStateCreatedDto();
        }

        @Override
        public OrderItemEvent.OrderItemStateMergePatched newOrderItemStateMergePatched(String productId) {
            return new OrderItemEventDto.OrderItemStateMergePatchedDto();
        }

        @Override
        public OrderItemEvent.OrderItemStateRemoved newOrderItemStateRemoved(String productId) {
            return new OrderItemEventDto.OrderItemStateRemovedDto();
        }


	}


	public static class OrderStateDeletedDto extends OrderStateEventDto implements OrderEvent.OrderStateDeleted {

        @Override
        public String getEventType()
        {
            return STATE_EVENT_TYPE_DELETED;
        }
	

        @Override
        public Iterable<OrderItemEvent.OrderItemStateRemoved> getOrderItemEvents() {
            if (orderItemEvents == null) return null;
            return java.util.Arrays.stream(orderItemEvents).map(i -> (OrderItemEvent.OrderItemStateRemoved)i).collect(java.util.stream.Collectors.toList());
        }
        
        @Override
        public void addOrderItemEvent(OrderItemEvent.OrderItemStateRemoved e) {
            java.util.List<OrderItemEventDto> events = orderItemEvents == null ? new java.util.ArrayList<>() : new java.util.ArrayList<>(java.util.Arrays.asList(orderItemEvents));
            events.add((OrderItemEventDto)e);
            orderItemEvents = events.toArray(new OrderItemEventDto[0]);
        }
        
        @Override
        public OrderItemEvent.OrderItemStateRemoved newOrderItemStateRemoved(String productId) {
            return new OrderItemEventDto.OrderItemStateRemovedDto();
        }
        

	}

}

