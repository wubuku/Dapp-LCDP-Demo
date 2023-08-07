// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.aptosdemocontracts.domain.order;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.aptosdemocontracts.domain.*;
import org.dddml.aptosdemocontracts.specialization.*;
import org.dddml.aptosdemocontracts.domain.AbstractEvent;

public abstract class AbstractOrderItemEvent extends AbstractEvent implements OrderItemEvent.SqlOrderItemEvent, AptosEvent.MutableAptosEvent, HasStatus.MutableHasStatus {
    private OrderItemEventId orderItemEventId = new OrderItemEventId();

    public OrderItemEventId getOrderItemEventId() {
        return this.orderItemEventId;
    }

    public void setOrderItemEventId(OrderItemEventId eventId) {
        this.orderItemEventId = eventId;
    }
    
    public String getProductId() {
        return getOrderItemEventId().getProductId();
    }

    public void setProductId(String productId) {
        getOrderItemEventId().setProductId(productId);
    }

    private boolean eventReadOnly;

    public boolean getEventReadOnly() { return this.eventReadOnly; }

    public void setEventReadOnly(boolean readOnly) { this.eventReadOnly = readOnly; }

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


    private String commandId;

    public String getCommandId() {
        return commandId;
    }

    public void setCommandId(String commandId) {
        this.commandId = commandId;
    }

    protected AbstractOrderItemEvent() {
    }

    protected AbstractOrderItemEvent(OrderItemEventId eventId) {
        this.orderItemEventId = eventId;
    }


    public abstract String getEventType();


}
