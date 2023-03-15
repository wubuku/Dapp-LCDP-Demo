package org.dddml.suidemocontracts.domain.orderv2;

import java.util.*;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.*;
import org.dddml.suidemocontracts.domain.AbstractEvent;

public abstract class AbstractOrderItemShipGroupAssocSubitemEvent extends AbstractEvent implements OrderItemShipGroupAssocSubitemEvent.SqlOrderItemShipGroupAssocSubitemEvent, SuiEventEnvelope.MutableSuiEventEnvelope, SuiMoveEvent.MutableSuiMoveEvent, HasStatus.MutableHasStatus 
{
    private OrderItemShipGroupAssocSubitemEventId orderItemShipGroupAssocSubitemEventId = new OrderItemShipGroupAssocSubitemEventId();

    public OrderItemShipGroupAssocSubitemEventId getOrderItemShipGroupAssocSubitemEventId() {
        return this.orderItemShipGroupAssocSubitemEventId;
    }

    public void setOrderItemShipGroupAssocSubitemEventId(OrderItemShipGroupAssocSubitemEventId eventId) {
        this.orderItemShipGroupAssocSubitemEventId = eventId;
    }
    
    public Integer getOrderItemShipGroupAssocSubitemSeqId() {
        return getOrderItemShipGroupAssocSubitemEventId().getOrderItemShipGroupAssocSubitemSeqId();
    }

    public void setOrderItemShipGroupAssocSubitemSeqId(Integer orderItemShipGroupAssocSubitemSeqId) {
        getOrderItemShipGroupAssocSubitemEventId().setOrderItemShipGroupAssocSubitemSeqId(orderItemShipGroupAssocSubitemSeqId);
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

    protected AbstractOrderItemShipGroupAssocSubitemEvent() {
    }

    protected AbstractOrderItemShipGroupAssocSubitemEvent(OrderItemShipGroupAssocSubitemEventId eventId) {
        this.orderItemShipGroupAssocSubitemEventId = eventId;
    }


    public abstract String getEventType();


}

