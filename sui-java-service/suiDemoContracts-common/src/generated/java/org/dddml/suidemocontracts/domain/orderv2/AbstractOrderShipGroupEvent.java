package org.dddml.suidemocontracts.domain.orderv2;

import java.util.*;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.*;
import org.dddml.suidemocontracts.domain.AbstractEvent;

public abstract class AbstractOrderShipGroupEvent extends AbstractEvent implements OrderShipGroupEvent.SqlOrderShipGroupEvent, SuiEventEnvelope.MutableSuiEventEnvelope, SuiMoveEvent.MutableSuiMoveEvent, HasStatus.MutableHasStatus 
{
    private OrderShipGroupEventId orderShipGroupEventId = new OrderShipGroupEventId();

    public OrderShipGroupEventId getOrderShipGroupEventId() {
        return this.orderShipGroupEventId;
    }

    public void setOrderShipGroupEventId(OrderShipGroupEventId eventId) {
        this.orderShipGroupEventId = eventId;
    }
    
    public Integer getShipGroupSeqId() {
        return getOrderShipGroupEventId().getShipGroupSeqId();
    }

    public void setShipGroupSeqId(Integer shipGroupSeqId) {
        getOrderShipGroupEventId().setShipGroupSeqId(shipGroupSeqId);
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

    protected AbstractOrderShipGroupEvent() {
    }

    protected AbstractOrderShipGroupEvent(OrderShipGroupEventId eventId) {
        this.orderShipGroupEventId = eventId;
    }

    protected OrderItemShipGroupAssociationEventDao getOrderItemShipGroupAssociationEventDao() {
        return (OrderItemShipGroupAssociationEventDao)ApplicationContext.current.get("orderItemShipGroupAssociationEventDao");
    }

    protected OrderItemShipGroupAssociationEventId newOrderItemShipGroupAssociationEventId(String productId)
    {
        OrderItemShipGroupAssociationEventId eventId = new OrderItemShipGroupAssociationEventId(this.getOrderShipGroupEventId().getOrderV2OrderId(), this.getOrderShipGroupEventId().getShipGroupSeqId(), 
            productId, 
            this.getOrderShipGroupEventId().getVersion());
        return eventId;
    }

    protected void throwOnInconsistentEventIds(OrderItemShipGroupAssociationEvent.SqlOrderItemShipGroupAssociationEvent e)
    {
        throwOnInconsistentEventIds(this, e);
    }

    public static void throwOnInconsistentEventIds(OrderShipGroupEvent.SqlOrderShipGroupEvent oe, OrderItemShipGroupAssociationEvent.SqlOrderItemShipGroupAssociationEvent e)
    {
        if (!oe.getOrderShipGroupEventId().getOrderV2OrderId().equals(e.getOrderItemShipGroupAssociationEventId().getOrderV2OrderId()))
        { 
            throw DomainError.named("inconsistentEventIds", "Outer Id OrderV2OrderId %1$s but inner id OrderV2OrderId %2$s", 
                oe.getOrderShipGroupEventId().getOrderV2OrderId(), e.getOrderItemShipGroupAssociationEventId().getOrderV2OrderId());
        }
        if (!oe.getOrderShipGroupEventId().getShipGroupSeqId().equals(e.getOrderItemShipGroupAssociationEventId().getOrderShipGroupShipGroupSeqId()))
        { 
            throw DomainError.named("inconsistentEventIds", "Outer Id ShipGroupSeqId %1$s but inner id OrderShipGroupShipGroupSeqId %2$s", 
                oe.getOrderShipGroupEventId().getShipGroupSeqId(), e.getOrderItemShipGroupAssociationEventId().getOrderShipGroupShipGroupSeqId());
        }
    }


    public abstract String getEventType();


}

