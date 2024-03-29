// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.suidemocontracts.domain.orderv2;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.*;
import org.dddml.suidemocontracts.domain.AbstractEvent;

public abstract class AbstractOrderV2ItemEvent extends AbstractEvent implements OrderV2ItemEvent.SqlOrderV2ItemEvent {
    private OrderV2ItemEventId orderV2ItemEventId = new OrderV2ItemEventId();

    public OrderV2ItemEventId getOrderV2ItemEventId() {
        return this.orderV2ItemEventId;
    }

    public void setOrderV2ItemEventId(OrderV2ItemEventId eventId) {
        this.orderV2ItemEventId = eventId;
    }
    
    public String getProductId() {
        return getOrderV2ItemEventId().getProductId();
    }

    public void setProductId(String productId) {
        getOrderV2ItemEventId().setProductId(productId);
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

    protected AbstractOrderV2ItemEvent() {
    }

    protected AbstractOrderV2ItemEvent(OrderV2ItemEventId eventId) {
        this.orderV2ItemEventId = eventId;
    }


    public abstract String getEventType();


}

