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


    public abstract String getEventType();


}

