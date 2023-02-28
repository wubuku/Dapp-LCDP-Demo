package org.dddml.suidemocontracts.domain.order;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.*;
import org.dddml.suidemocontracts.domain.AbstractEvent;

public abstract class AbstractOrderEvent extends AbstractEvent implements OrderEvent.SqlOrderEvent 
{
    private OrderEventId orderEventId;

    public OrderEventId getOrderEventId() {
        return this.orderEventId;
    }

    public void setOrderEventId(OrderEventId eventId) {
        this.orderEventId = eventId;
    }
    
    public String getId() {
        return getOrderEventId().getId();
    }

    public void setId(String id) {
        getOrderEventId().setId(id);
    }

    private boolean eventReadOnly;

    public boolean getEventReadOnly() { return this.eventReadOnly; }

    public void setEventReadOnly(boolean readOnly) { this.eventReadOnly = readOnly; }

    public Long getVersion() {
        return getOrderEventId().getVersion();
    }
    
    public void setVersion(Long version) {
        getOrderEventId().setVersion(version);
    }

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

    protected AbstractOrderEvent() {
    }

    protected AbstractOrderEvent(OrderEventId eventId) {
        this.orderEventId = eventId;
    }

    protected OrderItemEventDao getOrderItemEventDao() {
        return (OrderItemEventDao)ApplicationContext.current.get("orderItemEventDao");
    }

    protected OrderItemEventId newOrderItemEventId(String productId)
    {
        OrderItemEventId eventId = new OrderItemEventId(this.getOrderEventId().getId(), 
            productId, 
            this.getOrderEventId().getVersion());
        return eventId;
    }

    protected void throwOnInconsistentEventIds(OrderItemEvent.SqlOrderItemEvent e)
    {
        throwOnInconsistentEventIds(this, e);
    }

    public static void throwOnInconsistentEventIds(OrderEvent.SqlOrderEvent oe, OrderItemEvent.SqlOrderItemEvent e)
    {
        if (!oe.getOrderEventId().getId().equals(e.getOrderItemEventId().getOrderId()))
        { 
            throw DomainError.named("inconsistentEventIds", "Outer Id Id %1$s but inner id OrderId %2$s", 
                oe.getOrderEventId().getId(), e.getOrderItemEventId().getOrderId());
        }
    }

    public OrderItemEvent.OrderItemStateCreated newOrderItemStateCreated(String productId) {
        return new AbstractOrderItemEvent.SimpleOrderItemStateCreated(newOrderItemEventId(productId));
    }

    public OrderItemEvent.OrderItemStateMergePatched newOrderItemStateMergePatched(String productId) {
        return new AbstractOrderItemEvent.SimpleOrderItemStateMergePatched(newOrderItemEventId(productId));
    }

    public OrderItemEvent.OrderItemStateRemoved newOrderItemStateRemoved(String productId) {
        return new AbstractOrderItemEvent.SimpleOrderItemStateRemoved(newOrderItemEventId(productId));
    }


    public abstract String getEventType();


    public static abstract class AbstractOrderStateEvent extends AbstractOrderEvent implements OrderEvent.OrderStateEvent {
        private BigInteger totalAmount;

        public BigInteger getTotalAmount()
        {
            return this.totalAmount;
        }

        public void setTotalAmount(BigInteger totalAmount)
        {
            this.totalAmount = totalAmount;
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

        protected AbstractOrderStateEvent(OrderEventId eventId) {
            super(eventId);
        }
    }

    public static abstract class AbstractOrderStateCreated extends AbstractOrderStateEvent implements OrderEvent.OrderStateCreated, Saveable
    {
        public AbstractOrderStateCreated() {
            this(new OrderEventId());
        }

        public AbstractOrderStateCreated(OrderEventId eventId) {
            super(eventId);
        }

        public String getEventType() {
            return StateEventType.CREATED;
        }

        private Map<OrderItemEventId, OrderItemEvent.OrderItemStateCreated> orderItemEvents = new HashMap<OrderItemEventId, OrderItemEvent.OrderItemStateCreated>();
        
        private Iterable<OrderItemEvent.OrderItemStateCreated> readOnlyOrderItemEvents;

        public Iterable<OrderItemEvent.OrderItemStateCreated> getOrderItemEvents()
        {
            if (!getEventReadOnly())
            {
                return this.orderItemEvents.values();
            }
            else
            {
                if (readOnlyOrderItemEvents != null) { return readOnlyOrderItemEvents; }
                OrderItemEventDao eventDao = getOrderItemEventDao();
                List<OrderItemEvent.OrderItemStateCreated> eL = new ArrayList<OrderItemEvent.OrderItemStateCreated>();
                for (OrderItemEvent e : eventDao.findByOrderEventId(this.getOrderEventId()))
                {
                    ((OrderItemEvent.SqlOrderItemEvent)e).setEventReadOnly(true);
                    eL.add((OrderItemEvent.OrderItemStateCreated)e);
                }
                return (readOnlyOrderItemEvents = eL);
            }
        }

        public void setOrderItemEvents(Iterable<OrderItemEvent.OrderItemStateCreated> es)
        {
            if (es != null)
            {
                for (OrderItemEvent.OrderItemStateCreated e : es)
                {
                    addOrderItemEvent(e);
                }
            }
            else { this.orderItemEvents.clear(); }
        }
        
        public void addOrderItemEvent(OrderItemEvent.OrderItemStateCreated e)
        {
            throwOnInconsistentEventIds((OrderItemEvent.SqlOrderItemEvent)e);
            this.orderItemEvents.put(((OrderItemEvent.SqlOrderItemEvent)e).getOrderItemEventId(), e);
        }

        public void save()
        {
            for (OrderItemEvent.OrderItemStateCreated e : this.getOrderItemEvents()) {
                getOrderItemEventDao().save(e);
            }
        }
    }


    public static abstract class AbstractOrderStateMergePatched extends AbstractOrderStateEvent implements OrderEvent.OrderStateMergePatched, Saveable
    {
        public AbstractOrderStateMergePatched() {
            this(new OrderEventId());
        }

        public AbstractOrderStateMergePatched(OrderEventId eventId) {
            super(eventId);
        }

        public String getEventType() {
            return StateEventType.MERGE_PATCHED;
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


        private Map<OrderItemEventId, OrderItemEvent> orderItemEvents = new HashMap<OrderItemEventId, OrderItemEvent>();
        
        private Iterable<OrderItemEvent> readOnlyOrderItemEvents;

        public Iterable<OrderItemEvent> getOrderItemEvents()
        {
            if (!getEventReadOnly())
            {
                return this.orderItemEvents.values();
            }
            else
            {
                if (readOnlyOrderItemEvents != null) { return readOnlyOrderItemEvents; }
                OrderItemEventDao eventDao = getOrderItemEventDao();
                List<OrderItemEvent> eL = new ArrayList<OrderItemEvent>();
                for (OrderItemEvent e : eventDao.findByOrderEventId(this.getOrderEventId()))
                {
                    ((OrderItemEvent.SqlOrderItemEvent)e).setEventReadOnly(true);
                    eL.add((OrderItemEvent)e);
                }
                return (readOnlyOrderItemEvents = eL);
            }
        }

        public void setOrderItemEvents(Iterable<OrderItemEvent> es)
        {
            if (es != null)
            {
                for (OrderItemEvent e : es)
                {
                    addOrderItemEvent(e);
                }
            }
            else { this.orderItemEvents.clear(); }
        }
        
        public void addOrderItemEvent(OrderItemEvent e)
        {
            throwOnInconsistentEventIds((OrderItemEvent.SqlOrderItemEvent)e);
            this.orderItemEvents.put(((OrderItemEvent.SqlOrderItemEvent)e).getOrderItemEventId(), e);
        }

        public void save()
        {
            for (OrderItemEvent e : this.getOrderItemEvents()) {
                getOrderItemEventDao().save(e);
            }
        }
    }


    public static abstract class AbstractOrderStateDeleted extends AbstractOrderStateEvent implements OrderEvent.OrderStateDeleted, Saveable
    {
        public AbstractOrderStateDeleted() {
            this(new OrderEventId());
        }

        public AbstractOrderStateDeleted(OrderEventId eventId) {
            super(eventId);
        }

        public String getEventType() {
            return StateEventType.DELETED;
        }

		
        private Map<OrderItemEventId, OrderItemEvent.OrderItemStateRemoved> orderItemEvents = new HashMap<OrderItemEventId, OrderItemEvent.OrderItemStateRemoved>();
        
        private Iterable<OrderItemEvent.OrderItemStateRemoved> readOnlyOrderItemEvents;

        public Iterable<OrderItemEvent.OrderItemStateRemoved> getOrderItemEvents()
        {
            if (!getEventReadOnly())
            {
                return this.orderItemEvents.values();
            }
            else
            {
                if (readOnlyOrderItemEvents != null) { return readOnlyOrderItemEvents; }
                OrderItemEventDao eventDao = getOrderItemEventDao();
                List<OrderItemEvent.OrderItemStateRemoved> eL = new ArrayList<OrderItemEvent.OrderItemStateRemoved>();
                for (OrderItemEvent e : eventDao.findByOrderEventId(this.getOrderEventId()))
                {
                    ((OrderItemEvent.SqlOrderItemEvent)e).setEventReadOnly(true);
                    eL.add((OrderItemEvent.OrderItemStateRemoved)e);
                }
                return (readOnlyOrderItemEvents = eL);
            }
        }

        public void setOrderItemEvents(Iterable<OrderItemEvent.OrderItemStateRemoved> es)
        {
            if (es != null)
            {
                for (OrderItemEvent.OrderItemStateRemoved e : es)
                {
                    addOrderItemEvent(e);
                }
            }
            else { this.orderItemEvents.clear(); }
        }
        
        public void addOrderItemEvent(OrderItemEvent.OrderItemStateRemoved e)
        {
            throwOnInconsistentEventIds((OrderItemEvent.SqlOrderItemEvent)e);
            this.orderItemEvents.put(((OrderItemEvent.SqlOrderItemEvent)e).getOrderItemEventId(), e);
        }

        public void save()
        {
            for (OrderItemEvent.OrderItemStateRemoved e : this.getOrderItemEvents()) {
                getOrderItemEventDao().save(e);
            }
        }
    }

    public static class SimpleOrderStateCreated extends AbstractOrderStateCreated
    {
        public SimpleOrderStateCreated() {
        }

        public SimpleOrderStateCreated(OrderEventId eventId) {
            super(eventId);
        }
    }

    public static class SimpleOrderStateMergePatched extends AbstractOrderStateMergePatched
    {
        public SimpleOrderStateMergePatched() {
        }

        public SimpleOrderStateMergePatched(OrderEventId eventId) {
            super(eventId);
        }
    }

    public static class SimpleOrderStateDeleted extends AbstractOrderStateDeleted
    {
        public SimpleOrderStateDeleted() {
        }

        public SimpleOrderStateDeleted(OrderEventId eventId) {
            super(eventId);
        }
    }

}

