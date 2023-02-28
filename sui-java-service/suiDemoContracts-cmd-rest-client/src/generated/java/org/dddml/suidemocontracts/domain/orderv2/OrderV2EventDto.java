package org.dddml.suidemocontracts.domain.orderv2;

import java.math.BigInteger;
import org.dddml.suidemocontracts.domain.*;
import java.util.Date;
import org.dddml.suidemocontracts.domain.AbstractEvent;

public class OrderV2EventDto extends AbstractEvent implements OrderV2Event.OrderV2StateEvent {
    public static class OrderV2StateEventDto extends OrderV2EventDto {
    }

    private OrderV2EventId orderV2EventId;

    OrderV2EventId getOrderV2EventId() {
        if (orderV2EventId == null) { orderV2EventId = new OrderV2EventId(); }
        return orderV2EventId;
    }

    void setOrderV2EventId(OrderV2EventId eventId) {
        this.orderV2EventId = eventId;
    }

    public String getOrderId() {
        return getOrderV2EventId().getOrderId();
    }

    public void setOrderId(String orderId) {
        getOrderV2EventId().setOrderId(orderId);
    }

    public Long getVersion() {
        return getOrderV2EventId().getVersion();
    }
    
    public void setVersion(Long version) {
        getOrderV2EventId().setVersion(version);
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

    private Boolean isPropertyEstimatedShipDateRemoved;

    public Boolean getIsPropertyEstimatedShipDateRemoved() {
        return this.isPropertyEstimatedShipDateRemoved;
    }

    public void setIsPropertyEstimatedShipDateRemoved(Boolean removed) {
        this.isPropertyEstimatedShipDateRemoved = removed;
    }

    private Boolean isPropertyActiveRemoved;

    public Boolean getIsPropertyActiveRemoved() {
        return this.isPropertyActiveRemoved;
    }

    public void setIsPropertyActiveRemoved(Boolean removed) {
        this.isPropertyActiveRemoved = removed;
    }

    protected OrderV2ItemEventDto[] orderV2ItemEvents;

    //public OrderV2ItemEventDto[] getOrderV2ItemEventArray() {
    //    return this.orderV2ItemEvents;
    //}

    public void setOrderV2ItemEvents(OrderV2ItemEventDto[] events) {
        this.orderV2ItemEvents = events;
    }

    public OrderV2EventDto toSubclass() {
        if (STATE_EVENT_TYPE_CREATED.equals(getEventType())) {
            OrderV2StateCreatedDto e = new OrderV2StateCreatedDto();
            copyTo(e);
            return e;
        }
        else if (STATE_EVENT_TYPE_MERGE_PATCHED.equals(getEventType())) {
            OrderV2StateMergePatchedDto e = new OrderV2StateMergePatchedDto();
            copyTo(e);
            return e;
        }
        else if (STATE_EVENT_TYPE_DELETED.equals(getEventType())) {
            OrderV2StateDeletedDto e = new OrderV2StateDeletedDto();
            copyTo(e);
            return e;
        }

        throw new UnsupportedOperationException("Unknown event type:" + getEventType());
    }

    public void copyTo(OrderV2EventDto e) {
        e.setOrderId(this.getOrderId());
        e.setVersion(this.getVersion());
        e.setTotalAmount(this.getTotalAmount());
        e.setEstimatedShipDate(this.getEstimatedShipDate());
        e.setActive(this.getActive());
        e.setCreatedBy(this.getCreatedBy());
        e.setCreatedAt(this.getCreatedAt());
        e.setIsPropertyTotalAmountRemoved(this.getIsPropertyTotalAmountRemoved());
        e.setIsPropertyEstimatedShipDateRemoved(this.getIsPropertyEstimatedShipDateRemoved());
        e.setIsPropertyActiveRemoved(this.getIsPropertyActiveRemoved());
        if (this.orderV2ItemEvents != null) {
            OrderV2ItemEventDto[] orderV2ItemStateEventDtos = new OrderV2ItemEventDto[this.orderV2ItemEvents.length];
            for (int i = 0; i < this.orderV2ItemEvents.length; i++) {
                orderV2ItemStateEventDtos[i] = this.orderV2ItemEvents[i].toSubclass();
            }
            e.orderV2ItemEvents = orderV2ItemStateEventDtos;
        }
    }

	public static class OrderV2StateCreatedDto extends OrderV2EventDto implements OrderV2Event.OrderV2StateCreated {

        @Override
        public String getEventType()
        {
            return STATE_EVENT_TYPE_CREATED;
        }

        @Override
        public Iterable<OrderV2ItemEvent.OrderV2ItemStateCreated> getOrderV2ItemEvents() {
            if (orderV2ItemEvents == null) return null;
            return java.util.Arrays.stream(orderV2ItemEvents).map(i -> (OrderV2ItemEvent.OrderV2ItemStateCreated)i).collect(java.util.stream.Collectors.toList());
        }

        @Override
        public void addOrderV2ItemEvent(OrderV2ItemEvent.OrderV2ItemStateCreated e) {
            java.util.List<OrderV2ItemEventDto> events = orderV2ItemEvents == null ? new java.util.ArrayList<>() : new java.util.ArrayList<>(java.util.Arrays.asList(orderV2ItemEvents));
            events.add((OrderV2ItemEventDto)e);
            orderV2ItemEvents = events.toArray(new OrderV2ItemEventDto[0]);
        }

        @Override
        public OrderV2ItemEvent.OrderV2ItemStateCreated newOrderV2ItemStateCreated(String productId) {
            return new OrderV2ItemEventDto.OrderV2ItemStateCreatedDto();
        }

	}


	public static class OrderV2StateMergePatchedDto extends OrderV2StateEventDto implements OrderV2Event.OrderV2StateMergePatched {

        @Override
        public String getEventType()
        {
            return STATE_EVENT_TYPE_MERGE_PATCHED;
        }

        @Override
        public Iterable<OrderV2ItemEvent> getOrderV2ItemEvents() {
            if (orderV2ItemEvents == null) return null;
            return java.util.Arrays.stream(orderV2ItemEvents).map(i -> (OrderV2ItemEvent)i).collect(java.util.stream.Collectors.toList());
        }
        
        @Override
        public void addOrderV2ItemEvent(OrderV2ItemEvent e) {
            java.util.List<OrderV2ItemEventDto> events = orderV2ItemEvents == null ? new java.util.ArrayList<>() : new java.util.ArrayList<>(java.util.Arrays.asList(orderV2ItemEvents));
            events.add((OrderV2ItemEventDto)e);
            orderV2ItemEvents = events.toArray(new OrderV2ItemEventDto[0]);
        }

        @Override
        public OrderV2ItemEvent.OrderV2ItemStateCreated newOrderV2ItemStateCreated(String productId) {
            return new OrderV2ItemEventDto.OrderV2ItemStateCreatedDto();
        }

        @Override
        public OrderV2ItemEvent.OrderV2ItemStateMergePatched newOrderV2ItemStateMergePatched(String productId) {
            return new OrderV2ItemEventDto.OrderV2ItemStateMergePatchedDto();
        }

        @Override
        public OrderV2ItemEvent.OrderV2ItemStateRemoved newOrderV2ItemStateRemoved(String productId) {
            return new OrderV2ItemEventDto.OrderV2ItemStateRemovedDto();
        }


	}


	public static class OrderV2StateDeletedDto extends OrderV2StateEventDto implements OrderV2Event.OrderV2StateDeleted {

        @Override
        public String getEventType()
        {
            return STATE_EVENT_TYPE_DELETED;
        }
	

        @Override
        public Iterable<OrderV2ItemEvent.OrderV2ItemStateRemoved> getOrderV2ItemEvents() {
            if (orderV2ItemEvents == null) return null;
            return java.util.Arrays.stream(orderV2ItemEvents).map(i -> (OrderV2ItemEvent.OrderV2ItemStateRemoved)i).collect(java.util.stream.Collectors.toList());
        }
        
        @Override
        public void addOrderV2ItemEvent(OrderV2ItemEvent.OrderV2ItemStateRemoved e) {
            java.util.List<OrderV2ItemEventDto> events = orderV2ItemEvents == null ? new java.util.ArrayList<>() : new java.util.ArrayList<>(java.util.Arrays.asList(orderV2ItemEvents));
            events.add((OrderV2ItemEventDto)e);
            orderV2ItemEvents = events.toArray(new OrderV2ItemEventDto[0]);
        }
        
        @Override
        public OrderV2ItemEvent.OrderV2ItemStateRemoved newOrderV2ItemStateRemoved(String productId) {
            return new OrderV2ItemEventDto.OrderV2ItemStateRemovedDto();
        }
        

	}

}

