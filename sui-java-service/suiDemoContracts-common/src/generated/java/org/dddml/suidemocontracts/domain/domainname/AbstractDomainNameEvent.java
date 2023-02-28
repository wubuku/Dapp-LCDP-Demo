package org.dddml.suidemocontracts.domain.domainname;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.*;
import org.dddml.suidemocontracts.domain.AbstractEvent;

public abstract class AbstractDomainNameEvent extends AbstractEvent implements DomainNameEvent.SqlDomainNameEvent 
{
    private DomainNameEventId domainNameEventId;

    public DomainNameEventId getDomainNameEventId() {
        return this.domainNameEventId;
    }

    public void setDomainNameEventId(DomainNameEventId eventId) {
        this.domainNameEventId = eventId;
    }
    
    public DomainNameId getDomainNameId() {
        return getDomainNameEventId().getDomainNameId();
    }

    public void setDomainNameId(DomainNameId domainNameId) {
        getDomainNameEventId().setDomainNameId(domainNameId);
    }

    private boolean eventReadOnly;

    public boolean getEventReadOnly() { return this.eventReadOnly; }

    public void setEventReadOnly(boolean readOnly) { this.eventReadOnly = readOnly; }

    public Long getVersion() {
        return getDomainNameEventId().getVersion();
    }
    
    public void setVersion(Long version) {
        getDomainNameEventId().setVersion(version);
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

    protected AbstractDomainNameEvent() {
    }

    protected AbstractDomainNameEvent(DomainNameEventId eventId) {
        this.domainNameEventId = eventId;
    }


    public abstract String getEventType();


    public static abstract class AbstractDomainNameStateEvent extends AbstractDomainNameEvent implements DomainNameEvent.DomainNameStateEvent {
        private BigInteger expirationDate;

        public BigInteger getExpirationDate()
        {
            return this.expirationDate;
        }

        public void setExpirationDate(BigInteger expirationDate)
        {
            this.expirationDate = expirationDate;
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

        protected AbstractDomainNameStateEvent(DomainNameEventId eventId) {
            super(eventId);
        }
    }

    public static abstract class AbstractDomainNameStateCreated extends AbstractDomainNameStateEvent implements DomainNameEvent.DomainNameStateCreated
    {
        public AbstractDomainNameStateCreated() {
            this(new DomainNameEventId());
        }

        public AbstractDomainNameStateCreated(DomainNameEventId eventId) {
            super(eventId);
        }

        public String getEventType() {
            return StateEventType.CREATED;
        }

    }


    public static abstract class AbstractDomainNameStateMergePatched extends AbstractDomainNameStateEvent implements DomainNameEvent.DomainNameStateMergePatched
    {
        public AbstractDomainNameStateMergePatched() {
            this(new DomainNameEventId());
        }

        public AbstractDomainNameStateMergePatched(DomainNameEventId eventId) {
            super(eventId);
        }

        public String getEventType() {
            return StateEventType.MERGE_PATCHED;
        }

        private Boolean isPropertyExpirationDateRemoved;

        public Boolean getIsPropertyExpirationDateRemoved() {
            return this.isPropertyExpirationDateRemoved;
        }

        public void setIsPropertyExpirationDateRemoved(Boolean removed) {
            this.isPropertyExpirationDateRemoved = removed;
        }

        private Boolean isPropertyActiveRemoved;

        public Boolean getIsPropertyActiveRemoved() {
            return this.isPropertyActiveRemoved;
        }

        public void setIsPropertyActiveRemoved(Boolean removed) {
            this.isPropertyActiveRemoved = removed;
        }


    }


    public static abstract class AbstractDomainNameStateDeleted extends AbstractDomainNameStateEvent implements DomainNameEvent.DomainNameStateDeleted
    {
        public AbstractDomainNameStateDeleted() {
            this(new DomainNameEventId());
        }

        public AbstractDomainNameStateDeleted(DomainNameEventId eventId) {
            super(eventId);
        }

        public String getEventType() {
            return StateEventType.DELETED;
        }

    }

    public static class SimpleDomainNameStateCreated extends AbstractDomainNameStateCreated
    {
        public SimpleDomainNameStateCreated() {
        }

        public SimpleDomainNameStateCreated(DomainNameEventId eventId) {
            super(eventId);
        }
    }

    public static class SimpleDomainNameStateMergePatched extends AbstractDomainNameStateMergePatched
    {
        public SimpleDomainNameStateMergePatched() {
        }

        public SimpleDomainNameStateMergePatched(DomainNameEventId eventId) {
            super(eventId);
        }
    }

    public static class SimpleDomainNameStateDeleted extends AbstractDomainNameStateDeleted
    {
        public SimpleDomainNameStateDeleted() {
        }

        public SimpleDomainNameStateDeleted(DomainNameEventId eventId) {
            super(eventId);
        }
    }

}

