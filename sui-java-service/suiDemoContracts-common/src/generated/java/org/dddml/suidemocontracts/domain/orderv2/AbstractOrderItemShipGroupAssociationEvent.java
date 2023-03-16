package org.dddml.suidemocontracts.domain.orderv2;

import java.util.*;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.*;
import org.dddml.suidemocontracts.domain.AbstractEvent;

public abstract class AbstractOrderItemShipGroupAssociationEvent extends AbstractEvent implements OrderItemShipGroupAssociationEvent.SqlOrderItemShipGroupAssociationEvent, SuiEventEnvelope.MutableSuiEventEnvelope, SuiMoveEvent.MutableSuiMoveEvent, HasStatus.MutableHasStatus 
{
    private OrderItemShipGroupAssociationEventId orderItemShipGroupAssociationEventId = new OrderItemShipGroupAssociationEventId();

    public OrderItemShipGroupAssociationEventId getOrderItemShipGroupAssociationEventId() {
        return this.orderItemShipGroupAssociationEventId;
    }

    public void setOrderItemShipGroupAssociationEventId(OrderItemShipGroupAssociationEventId eventId) {
        this.orderItemShipGroupAssociationEventId = eventId;
    }
    
    public String getProductId() {
        return getOrderItemShipGroupAssociationEventId().getProductId();
    }

    public void setProductId(String productId) {
        getOrderItemShipGroupAssociationEventId().setProductId(productId);
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

    protected AbstractOrderItemShipGroupAssociationEvent() {
    }

    protected AbstractOrderItemShipGroupAssociationEvent(OrderItemShipGroupAssociationEventId eventId) {
        this.orderItemShipGroupAssociationEventId = eventId;
    }

    protected OrderItemShipGroupAssocSubitemEventDao getOrderItemShipGroupAssocSubitemEventDao() {
        return (OrderItemShipGroupAssocSubitemEventDao)ApplicationContext.current.get("orderItemShipGroupAssocSubitemEventDao");
    }

    protected OrderItemShipGroupAssocSubitemEventId newOrderItemShipGroupAssocSubitemEventId(Day orderItemShipGroupAssocSubitemDay)
    {
        OrderItemShipGroupAssocSubitemEventId eventId = new OrderItemShipGroupAssocSubitemEventId(this.getOrderItemShipGroupAssociationEventId().getOrderV2OrderId(), this.getOrderItemShipGroupAssociationEventId().getOrderShipGroupShipGroupSeqId(), this.getOrderItemShipGroupAssociationEventId().getProductId(), 
            orderItemShipGroupAssocSubitemDay, 
            this.getOrderItemShipGroupAssociationEventId().getVersion());
        return eventId;
    }

    protected void throwOnInconsistentEventIds(OrderItemShipGroupAssocSubitemEvent.SqlOrderItemShipGroupAssocSubitemEvent e)
    {
        throwOnInconsistentEventIds(this, e);
    }

    public static void throwOnInconsistentEventIds(OrderItemShipGroupAssociationEvent.SqlOrderItemShipGroupAssociationEvent oe, OrderItemShipGroupAssocSubitemEvent.SqlOrderItemShipGroupAssocSubitemEvent e)
    {
        if (!oe.getOrderItemShipGroupAssociationEventId().getOrderV2OrderId().equals(e.getOrderItemShipGroupAssocSubitemEventId().getOrderV2OrderId()))
        { 
            throw DomainError.named("inconsistentEventIds", "Outer Id OrderV2OrderId %1$s but inner id OrderV2OrderId %2$s", 
                oe.getOrderItemShipGroupAssociationEventId().getOrderV2OrderId(), e.getOrderItemShipGroupAssocSubitemEventId().getOrderV2OrderId());
        }
        if (!oe.getOrderItemShipGroupAssociationEventId().getOrderShipGroupShipGroupSeqId().equals(e.getOrderItemShipGroupAssocSubitemEventId().getOrderShipGroupShipGroupSeqId()))
        { 
            throw DomainError.named("inconsistentEventIds", "Outer Id OrderShipGroupShipGroupSeqId %1$s but inner id OrderShipGroupShipGroupSeqId %2$s", 
                oe.getOrderItemShipGroupAssociationEventId().getOrderShipGroupShipGroupSeqId(), e.getOrderItemShipGroupAssocSubitemEventId().getOrderShipGroupShipGroupSeqId());
        }
        if (!oe.getOrderItemShipGroupAssociationEventId().getProductId().equals(e.getOrderItemShipGroupAssocSubitemEventId().getOrderItemShipGroupAssociationProductId()))
        { 
            throw DomainError.named("inconsistentEventIds", "Outer Id ProductId %1$s but inner id OrderItemShipGroupAssociationProductId %2$s", 
                oe.getOrderItemShipGroupAssociationEventId().getProductId(), e.getOrderItemShipGroupAssocSubitemEventId().getOrderItemShipGroupAssociationProductId());
        }
    }


    public abstract String getEventType();


}

