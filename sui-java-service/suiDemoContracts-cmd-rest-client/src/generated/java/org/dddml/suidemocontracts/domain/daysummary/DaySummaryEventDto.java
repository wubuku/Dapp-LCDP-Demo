package org.dddml.suidemocontracts.domain.daysummary;

import org.dddml.suidemocontracts.domain.*;
import java.util.Date;
import org.dddml.suidemocontracts.domain.AbstractEvent;

public class DaySummaryEventDto extends AbstractEvent implements DaySummaryEvent.DaySummaryStateEvent {
    public static class DaySummaryStateEventDto extends DaySummaryEventDto {
    }

    private DaySummaryEventId daySummaryEventId;

    DaySummaryEventId getDaySummaryEventId() {
        if (daySummaryEventId == null) { daySummaryEventId = new DaySummaryEventId(); }
        return daySummaryEventId;
    }

    void setDaySummaryEventId(DaySummaryEventId eventId) {
        this.daySummaryEventId = eventId;
    }

    public Day getDay() {
        return getDaySummaryEventId().getDay();
    }

    public void setDay(Day day) {
        getDaySummaryEventId().setDay(day);
    }

    public Long getVersion() {
        return getDaySummaryEventId().getVersion();
    }
    
    public void setVersion(Long version) {
        getDaySummaryEventId().setVersion(version);
    }

    private String description;

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private int[] metadata;

    public int[] getMetadata() {
        return this.metadata;
    }

    public void setMetadata(int[] metadata) {
        this.metadata = metadata;
    }

    private int[] optionalData;

    public int[] getOptionalData() {
        return this.optionalData;
    }

    public void setOptionalData(int[] optionalData) {
        this.optionalData = optionalData;
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

    protected String[] arrayData;

    public java.util.Set<String> getArrayData() {
        return this.arrayData == null ? null : java.util.Arrays.stream(this.arrayData).collect(java.util.stream.Collectors.toSet());
    }

    public void setArrayData(String[] arrayData) {
        this.arrayData = arrayData;
    }

    private Boolean isPropertyDescriptionRemoved;

    public Boolean getIsPropertyDescriptionRemoved() {
        return this.isPropertyDescriptionRemoved;
    }

    public void setIsPropertyDescriptionRemoved(Boolean removed) {
        this.isPropertyDescriptionRemoved = removed;
    }

    private Boolean isPropertyMetadataRemoved;

    public Boolean getIsPropertyMetadataRemoved() {
        return this.isPropertyMetadataRemoved;
    }

    public void setIsPropertyMetadataRemoved(Boolean removed) {
        this.isPropertyMetadataRemoved = removed;
    }

    private Boolean isPropertyArrayDataRemoved;

    public Boolean getIsPropertyArrayDataRemoved() {
        return this.isPropertyArrayDataRemoved;
    }

    public void setIsPropertyArrayDataRemoved(Boolean removed) {
        this.isPropertyArrayDataRemoved = removed;
    }

    private Boolean isPropertyOptionalDataRemoved;

    public Boolean getIsPropertyOptionalDataRemoved() {
        return this.isPropertyOptionalDataRemoved;
    }

    public void setIsPropertyOptionalDataRemoved(Boolean removed) {
        this.isPropertyOptionalDataRemoved = removed;
    }

    private Boolean isPropertyActiveRemoved;

    public Boolean getIsPropertyActiveRemoved() {
        return this.isPropertyActiveRemoved;
    }

    public void setIsPropertyActiveRemoved(Boolean removed) {
        this.isPropertyActiveRemoved = removed;
    }

    public DaySummaryEventDto toSubclass() {
        if (STATE_EVENT_TYPE_CREATED.equals(getEventType())) {
            DaySummaryStateCreatedDto e = new DaySummaryStateCreatedDto();
            copyTo(e);
            return e;
        }
        else if (STATE_EVENT_TYPE_MERGE_PATCHED.equals(getEventType())) {
            DaySummaryStateMergePatchedDto e = new DaySummaryStateMergePatchedDto();
            copyTo(e);
            return e;
        }
        else if (STATE_EVENT_TYPE_DELETED.equals(getEventType())) {
            DaySummaryStateDeletedDto e = new DaySummaryStateDeletedDto();
            copyTo(e);
            return e;
        }

        throw new UnsupportedOperationException("Unknown event type:" + getEventType());
    }

    public void copyTo(DaySummaryEventDto e) {
        e.setDay(this.getDay());
        e.setVersion(this.getVersion());
        e.setDescription(this.getDescription());
        e.setMetadata(this.getMetadata());
        e.setOptionalData(this.getOptionalData());
        e.setActive(this.getActive());
        e.setCreatedBy(this.getCreatedBy());
        e.setCreatedAt(this.getCreatedAt());
        e.arrayData = this.arrayData;
        e.setIsPropertyDescriptionRemoved(this.getIsPropertyDescriptionRemoved());
        e.setIsPropertyMetadataRemoved(this.getIsPropertyMetadataRemoved());
        e.setIsPropertyArrayDataRemoved(this.getIsPropertyArrayDataRemoved());
        e.setIsPropertyOptionalDataRemoved(this.getIsPropertyOptionalDataRemoved());
        e.setIsPropertyActiveRemoved(this.getIsPropertyActiveRemoved());
    }

	public static class DaySummaryStateCreatedDto extends DaySummaryEventDto implements DaySummaryEvent.DaySummaryStateCreated {

        @Override
        public String getEventType()
        {
            return STATE_EVENT_TYPE_CREATED;
        }

	}


	public static class DaySummaryStateMergePatchedDto extends DaySummaryStateEventDto implements DaySummaryEvent.DaySummaryStateMergePatched {

        @Override
        public String getEventType()
        {
            return STATE_EVENT_TYPE_MERGE_PATCHED;
        }

	}


	public static class DaySummaryStateDeletedDto extends DaySummaryStateEventDto implements DaySummaryEvent.DaySummaryStateDeleted {

        @Override
        public String getEventType()
        {
            return STATE_EVENT_TYPE_DELETED;
        }
	


	}

}

