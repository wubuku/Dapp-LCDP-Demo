package org.dddml.suidemocontracts.domain.domainname;

import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.domain.AbstractEvent;

public class DomainNameEventDto extends AbstractEvent implements DomainNameEvent.DomainNameStateEvent {
    public static class DomainNameStateEventDto extends DomainNameEventDto {
    }

    private DomainNameEventId domainNameEventId;

    DomainNameEventId getDomainNameEventId() {
        if (domainNameEventId == null) { domainNameEventId = new DomainNameEventId(); }
        return domainNameEventId;
    }

    void setDomainNameEventId(DomainNameEventId eventId) {
        this.domainNameEventId = eventId;
    }

    public DomainNameId getDomainNameId() {
        return getDomainNameEventId().getDomainNameId();
    }

    public void setDomainNameId(DomainNameId domainNameId) {
        getDomainNameEventId().setDomainNameId(domainNameId);
    }

    public Long getVersion() {
        return getDomainNameEventId().getVersion();
    }
    
    public void setVersion(Long version) {
        getDomainNameEventId().setVersion(version);
    }

    private BigInteger expirationDate;

    public BigInteger getExpirationDate() {
        return this.expirationDate;
    }

    public void setExpirationDate(BigInteger expirationDate) {
        this.expirationDate = expirationDate;
    }

    private Boolean active;

    public Boolean getActive() {
        return this.active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

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

    public DomainNameEventDto toSubclass() {
        if (STATE_EVENT_TYPE_CREATED.equals(getEventType())) {
            DomainNameStateCreatedDto e = new DomainNameStateCreatedDto();
            copyTo(e);
            return e;
        }
        else if (STATE_EVENT_TYPE_MERGE_PATCHED.equals(getEventType())) {
            DomainNameStateMergePatchedDto e = new DomainNameStateMergePatchedDto();
            copyTo(e);
            return e;
        }
        else if (STATE_EVENT_TYPE_DELETED.equals(getEventType())) {
            DomainNameStateDeletedDto e = new DomainNameStateDeletedDto();
            copyTo(e);
            return e;
        }

        throw new UnsupportedOperationException("Unknown event type:" + getEventType());
    }

    public void copyTo(DomainNameEventDto e) {
        e.setDomainNameId(this.getDomainNameId());
        e.setVersion(this.getVersion());
        e.setExpirationDate(this.getExpirationDate());
        e.setActive(this.getActive());
        e.setCreatedBy(this.getCreatedBy());
        e.setCreatedAt(this.getCreatedAt());
        e.setIsPropertyExpirationDateRemoved(this.getIsPropertyExpirationDateRemoved());
        e.setIsPropertyActiveRemoved(this.getIsPropertyActiveRemoved());
    }

	public static class DomainNameStateCreatedDto extends DomainNameEventDto implements DomainNameEvent.DomainNameStateCreated {

        @Override
        public String getEventType()
        {
            return STATE_EVENT_TYPE_CREATED;
        }

	}


	public static class DomainNameStateMergePatchedDto extends DomainNameStateEventDto implements DomainNameEvent.DomainNameStateMergePatched {

        @Override
        public String getEventType()
        {
            return STATE_EVENT_TYPE_MERGE_PATCHED;
        }

	}


	public static class DomainNameStateDeletedDto extends DomainNameStateEventDto implements DomainNameEvent.DomainNameStateDeleted {

        @Override
        public String getEventType()
        {
            return STATE_EVENT_TYPE_DELETED;
        }
	


	}

}

