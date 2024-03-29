// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.domain.article;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.roochdemocontracts.domain.*;
import org.dddml.roochdemocontracts.specialization.*;
import org.dddml.roochdemocontracts.domain.AbstractEvent;

public abstract class AbstractReferenceEvent extends AbstractEvent implements ReferenceEvent.SqlReferenceEvent {
    private ReferenceEventId referenceEventId = new ReferenceEventId();

    public ReferenceEventId getReferenceEventId() {
        return this.referenceEventId;
    }

    public void setReferenceEventId(ReferenceEventId eventId) {
        this.referenceEventId = eventId;
    }
    
    public BigInteger getReferenceNumber() {
        return getReferenceEventId().getReferenceNumber();
    }

    public void setReferenceNumber(BigInteger referenceNumber) {
        getReferenceEventId().setReferenceNumber(referenceNumber);
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

    protected AbstractReferenceEvent() {
    }

    protected AbstractReferenceEvent(ReferenceEventId eventId) {
        this.referenceEventId = eventId;
    }


    public abstract String getEventType();


}

