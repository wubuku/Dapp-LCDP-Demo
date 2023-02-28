package org.dddml.suidemocontracts.domain.orderv2;

import java.util.*;
import java.math.BigInteger;
import org.dddml.suidemocontracts.domain.*;
import java.util.Date;
import org.dddml.suidemocontracts.specialization.*;
import org.dddml.suidemocontracts.domain.AbstractEvent;

public abstract class AbstractOrderV2Event extends AbstractEvent implements OrderV2Event.SqlOrderV2Event 
{
    private OrderV2EventId orderV2EventId;

    public OrderV2EventId getOrderV2EventId() {
        return this.orderV2EventId;
    }

    public void setOrderV2EventId(OrderV2EventId eventId) {
        this.orderV2EventId = eventId;
    }
    
    public String getOrderId() {
        return getOrderV2EventId().getOrderId();
    }

    public void setOrderId(String orderId) {
        getOrderV2EventId().setOrderId(orderId);
    }

    private boolean eventReadOnly;

    public boolean getEventReadOnly() { return this.eventReadOnly; }

    public void setEventReadOnly(boolean readOnly) { this.eventReadOnly = readOnly; }

    public Long getVersion() {
        return getOrderV2EventId().getVersion();
    }
    
    public void setVersion(Long version) {
        getOrderV2EventId().setVersion(version);
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

    protected AbstractOrderV2Event() {
    }

    protected AbstractOrderV2Event(OrderV2EventId eventId) {
        this.orderV2EventId = eventId;
    }

    protected OrderV2ItemEventDao getOrderV2ItemEventDao() {
        return (OrderV2ItemEventDao)ApplicationContext.current.get("orderV2ItemEventDao");
    }

    protected OrderV2ItemEventId newOrderV2ItemEventId(String productId)
    {
        OrderV2ItemEventId eventId = new OrderV2ItemEventId(this.getOrderV2EventId().getOrderId(), 
            productId, 
            this.getOrderV2EventId().getVersion());
        return eventId;
    }

    protected void throwOnInconsistentEventIds(OrderV2ItemEvent.SqlOrderV2ItemEvent e)
    {
        throwOnInconsistentEventIds(this, e);
    }

    public static void throwOnInconsistentEventIds(OrderV2Event.SqlOrderV2Event oe, OrderV2ItemEvent.SqlOrderV2ItemEvent e)
    {
        if (!oe.getOrderV2EventId().getOrderId().equals(e.getOrderV2ItemEventId().getOrderV2OrderId()))
        { 
            throw DomainError.named("inconsistentEventIds", "Outer Id OrderId %1$s but inner id OrderV2OrderId %2$s", 
                oe.getOrderV2EventId().getOrderId(), e.getOrderV2ItemEventId().getOrderV2OrderId());
        }
    }

    public OrderV2ItemEvent.OrderV2ItemStateCreated newOrderV2ItemStateCreated(String productId) {
        return new AbstractOrderV2ItemEvent.SimpleOrderV2ItemStateCreated(newOrderV2ItemEventId(productId));
    }

    public OrderV2ItemEvent.OrderV2ItemStateMergePatched newOrderV2ItemStateMergePatched(String productId) {
        return new AbstractOrderV2ItemEvent.SimpleOrderV2ItemStateMergePatched(newOrderV2ItemEventId(productId));
    }

    public OrderV2ItemEvent.OrderV2ItemStateRemoved newOrderV2ItemStateRemoved(String productId) {
        return new AbstractOrderV2ItemEvent.SimpleOrderV2ItemStateRemoved(newOrderV2ItemEventId(productId));
    }


    public abstract String getEventType();


    public static abstract class AbstractOrderV2StateEvent extends AbstractOrderV2Event implements OrderV2Event.OrderV2StateEvent {
        private BigInteger totalAmount;

        public BigInteger getTotalAmount()
        {
            return this.totalAmount;
        }

        public void setTotalAmount(BigInteger totalAmount)
        {
            this.totalAmount = totalAmount;
        }

        private Day estimatedShipDate;

        public Day getEstimatedShipDate()
        {
            return this.estimatedShipDate;
        }

        public void setEstimatedShipDate(Day estimatedShipDate)
        {
            this.estimatedShipDate = estimatedShipDate;
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

        protected AbstractOrderV2StateEvent(OrderV2EventId eventId) {
            super(eventId);
        }
    }

    public static abstract class AbstractOrderV2StateCreated extends AbstractOrderV2StateEvent implements OrderV2Event.OrderV2StateCreated, Saveable
    {
        public AbstractOrderV2StateCreated() {
            this(new OrderV2EventId());
        }

        public AbstractOrderV2StateCreated(OrderV2EventId eventId) {
            super(eventId);
        }

        public String getEventType() {
            return StateEventType.CREATED;
        }

        private Map<OrderV2ItemEventId, OrderV2ItemEvent.OrderV2ItemStateCreated> orderV2ItemEvents = new HashMap<OrderV2ItemEventId, OrderV2ItemEvent.OrderV2ItemStateCreated>();
        
        private Iterable<OrderV2ItemEvent.OrderV2ItemStateCreated> readOnlyOrderV2ItemEvents;

        public Iterable<OrderV2ItemEvent.OrderV2ItemStateCreated> getOrderV2ItemEvents()
        {
            if (!getEventReadOnly())
            {
                return this.orderV2ItemEvents.values();
            }
            else
            {
                if (readOnlyOrderV2ItemEvents != null) { return readOnlyOrderV2ItemEvents; }
                OrderV2ItemEventDao eventDao = getOrderV2ItemEventDao();
                List<OrderV2ItemEvent.OrderV2ItemStateCreated> eL = new ArrayList<OrderV2ItemEvent.OrderV2ItemStateCreated>();
                for (OrderV2ItemEvent e : eventDao.findByOrderV2EventId(this.getOrderV2EventId()))
                {
                    ((OrderV2ItemEvent.SqlOrderV2ItemEvent)e).setEventReadOnly(true);
                    eL.add((OrderV2ItemEvent.OrderV2ItemStateCreated)e);
                }
                return (readOnlyOrderV2ItemEvents = eL);
            }
        }

        public void setOrderV2ItemEvents(Iterable<OrderV2ItemEvent.OrderV2ItemStateCreated> es)
        {
            if (es != null)
            {
                for (OrderV2ItemEvent.OrderV2ItemStateCreated e : es)
                {
                    addOrderV2ItemEvent(e);
                }
            }
            else { this.orderV2ItemEvents.clear(); }
        }
        
        public void addOrderV2ItemEvent(OrderV2ItemEvent.OrderV2ItemStateCreated e)
        {
            throwOnInconsistentEventIds((OrderV2ItemEvent.SqlOrderV2ItemEvent)e);
            this.orderV2ItemEvents.put(((OrderV2ItemEvent.SqlOrderV2ItemEvent)e).getOrderV2ItemEventId(), e);
        }

        public void save()
        {
            for (OrderV2ItemEvent.OrderV2ItemStateCreated e : this.getOrderV2ItemEvents()) {
                getOrderV2ItemEventDao().save(e);
            }
        }
    }


    public static abstract class AbstractOrderV2StateMergePatched extends AbstractOrderV2StateEvent implements OrderV2Event.OrderV2StateMergePatched, Saveable
    {
        public AbstractOrderV2StateMergePatched() {
            this(new OrderV2EventId());
        }

        public AbstractOrderV2StateMergePatched(OrderV2EventId eventId) {
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


        private Map<OrderV2ItemEventId, OrderV2ItemEvent> orderV2ItemEvents = new HashMap<OrderV2ItemEventId, OrderV2ItemEvent>();
        
        private Iterable<OrderV2ItemEvent> readOnlyOrderV2ItemEvents;

        public Iterable<OrderV2ItemEvent> getOrderV2ItemEvents()
        {
            if (!getEventReadOnly())
            {
                return this.orderV2ItemEvents.values();
            }
            else
            {
                if (readOnlyOrderV2ItemEvents != null) { return readOnlyOrderV2ItemEvents; }
                OrderV2ItemEventDao eventDao = getOrderV2ItemEventDao();
                List<OrderV2ItemEvent> eL = new ArrayList<OrderV2ItemEvent>();
                for (OrderV2ItemEvent e : eventDao.findByOrderV2EventId(this.getOrderV2EventId()))
                {
                    ((OrderV2ItemEvent.SqlOrderV2ItemEvent)e).setEventReadOnly(true);
                    eL.add((OrderV2ItemEvent)e);
                }
                return (readOnlyOrderV2ItemEvents = eL);
            }
        }

        public void setOrderV2ItemEvents(Iterable<OrderV2ItemEvent> es)
        {
            if (es != null)
            {
                for (OrderV2ItemEvent e : es)
                {
                    addOrderV2ItemEvent(e);
                }
            }
            else { this.orderV2ItemEvents.clear(); }
        }
        
        public void addOrderV2ItemEvent(OrderV2ItemEvent e)
        {
            throwOnInconsistentEventIds((OrderV2ItemEvent.SqlOrderV2ItemEvent)e);
            this.orderV2ItemEvents.put(((OrderV2ItemEvent.SqlOrderV2ItemEvent)e).getOrderV2ItemEventId(), e);
        }

        public void save()
        {
            for (OrderV2ItemEvent e : this.getOrderV2ItemEvents()) {
                getOrderV2ItemEventDao().save(e);
            }
        }
    }


    public static abstract class AbstractOrderV2StateDeleted extends AbstractOrderV2StateEvent implements OrderV2Event.OrderV2StateDeleted, Saveable
    {
        public AbstractOrderV2StateDeleted() {
            this(new OrderV2EventId());
        }

        public AbstractOrderV2StateDeleted(OrderV2EventId eventId) {
            super(eventId);
        }

        public String getEventType() {
            return StateEventType.DELETED;
        }

		
        private Map<OrderV2ItemEventId, OrderV2ItemEvent.OrderV2ItemStateRemoved> orderV2ItemEvents = new HashMap<OrderV2ItemEventId, OrderV2ItemEvent.OrderV2ItemStateRemoved>();
        
        private Iterable<OrderV2ItemEvent.OrderV2ItemStateRemoved> readOnlyOrderV2ItemEvents;

        public Iterable<OrderV2ItemEvent.OrderV2ItemStateRemoved> getOrderV2ItemEvents()
        {
            if (!getEventReadOnly())
            {
                return this.orderV2ItemEvents.values();
            }
            else
            {
                if (readOnlyOrderV2ItemEvents != null) { return readOnlyOrderV2ItemEvents; }
                OrderV2ItemEventDao eventDao = getOrderV2ItemEventDao();
                List<OrderV2ItemEvent.OrderV2ItemStateRemoved> eL = new ArrayList<OrderV2ItemEvent.OrderV2ItemStateRemoved>();
                for (OrderV2ItemEvent e : eventDao.findByOrderV2EventId(this.getOrderV2EventId()))
                {
                    ((OrderV2ItemEvent.SqlOrderV2ItemEvent)e).setEventReadOnly(true);
                    eL.add((OrderV2ItemEvent.OrderV2ItemStateRemoved)e);
                }
                return (readOnlyOrderV2ItemEvents = eL);
            }
        }

        public void setOrderV2ItemEvents(Iterable<OrderV2ItemEvent.OrderV2ItemStateRemoved> es)
        {
            if (es != null)
            {
                for (OrderV2ItemEvent.OrderV2ItemStateRemoved e : es)
                {
                    addOrderV2ItemEvent(e);
                }
            }
            else { this.orderV2ItemEvents.clear(); }
        }
        
        public void addOrderV2ItemEvent(OrderV2ItemEvent.OrderV2ItemStateRemoved e)
        {
            throwOnInconsistentEventIds((OrderV2ItemEvent.SqlOrderV2ItemEvent)e);
            this.orderV2ItemEvents.put(((OrderV2ItemEvent.SqlOrderV2ItemEvent)e).getOrderV2ItemEventId(), e);
        }

        public void save()
        {
            for (OrderV2ItemEvent.OrderV2ItemStateRemoved e : this.getOrderV2ItemEvents()) {
                getOrderV2ItemEventDao().save(e);
            }
        }
    }

    public static class SimpleOrderV2StateCreated extends AbstractOrderV2StateCreated
    {
        public SimpleOrderV2StateCreated() {
        }

        public SimpleOrderV2StateCreated(OrderV2EventId eventId) {
            super(eventId);
        }
    }

    public static class SimpleOrderV2StateMergePatched extends AbstractOrderV2StateMergePatched
    {
        public SimpleOrderV2StateMergePatched() {
        }

        public SimpleOrderV2StateMergePatched(OrderV2EventId eventId) {
            super(eventId);
        }
    }

    public static class SimpleOrderV2StateDeleted extends AbstractOrderV2StateDeleted
    {
        public SimpleOrderV2StateDeleted() {
        }

        public SimpleOrderV2StateDeleted(OrderV2EventId eventId) {
            super(eventId);
        }
    }

}

