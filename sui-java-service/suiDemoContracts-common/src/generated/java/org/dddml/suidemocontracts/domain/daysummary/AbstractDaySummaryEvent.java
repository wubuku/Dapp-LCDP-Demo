package org.dddml.suidemocontracts.domain.daysummary;

import java.util.*;
import org.dddml.suidemocontracts.domain.*;
import java.util.Date;
import org.dddml.suidemocontracts.specialization.*;
import org.dddml.suidemocontracts.domain.AbstractEvent;

public abstract class AbstractDaySummaryEvent extends AbstractEvent implements DaySummaryEvent.SqlDaySummaryEvent 
{
    private DaySummaryEventId daySummaryEventId;

    public DaySummaryEventId getDaySummaryEventId() {
        return this.daySummaryEventId;
    }

    public void setDaySummaryEventId(DaySummaryEventId eventId) {
        this.daySummaryEventId = eventId;
    }
    
    public Day getDay() {
        return getDaySummaryEventId().getDay();
    }

    public void setDay(Day day) {
        getDaySummaryEventId().setDay(day);
    }

    private boolean eventReadOnly;

    public boolean getEventReadOnly() { return this.eventReadOnly; }

    public void setEventReadOnly(boolean readOnly) { this.eventReadOnly = readOnly; }

    public Long getVersion() {
        return getDaySummaryEventId().getVersion();
    }
    
    public void setVersion(Long version) {
        getDaySummaryEventId().setVersion(version);
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

    protected AbstractDaySummaryEvent() {
    }

    protected AbstractDaySummaryEvent(DaySummaryEventId eventId) {
        this.daySummaryEventId = eventId;
    }


    public abstract String getEventType();


    public static abstract class AbstractDaySummaryStateEvent extends AbstractDaySummaryEvent implements DaySummaryEvent.DaySummaryStateEvent {
        private String description;

        public String getDescription()
        {
            return this.description;
        }

        public void setDescription(String description)
        {
            this.description = description;
        }

        private int[] metadata;

        public int[] getMetadata()
        {
            return this.metadata;
        }

        public void setMetadata(int[] metadata)
        {
            this.metadata = metadata;
        }

        private int[] optionalData;

        public int[] getOptionalData()
        {
            return this.optionalData;
        }

        public void setOptionalData(int[] optionalData)
        {
            this.optionalData = optionalData;
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

        private Set<String> arrayData;

        public Set<String> getArrayData()
        {
            return this.arrayData;
        }

        public void setArrayData(Set<String> arrayData)
        {
            this.arrayData = arrayData;
        }

        protected AbstractDaySummaryStateEvent(DaySummaryEventId eventId) {
            super(eventId);
        }
    }

    public static abstract class AbstractDaySummaryStateCreated extends AbstractDaySummaryStateEvent implements DaySummaryEvent.DaySummaryStateCreated
    {
        public AbstractDaySummaryStateCreated() {
            this(new DaySummaryEventId());
        }

        public AbstractDaySummaryStateCreated(DaySummaryEventId eventId) {
            super(eventId);
        }

        public String getEventType() {
            return StateEventType.CREATED;
        }

    }


    public static abstract class AbstractDaySummaryStateMergePatched extends AbstractDaySummaryStateEvent implements DaySummaryEvent.DaySummaryStateMergePatched
    {
        public AbstractDaySummaryStateMergePatched() {
            this(new DaySummaryEventId());
        }

        public AbstractDaySummaryStateMergePatched(DaySummaryEventId eventId) {
            super(eventId);
        }

        public String getEventType() {
            return StateEventType.MERGE_PATCHED;
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


    }


    public static abstract class AbstractDaySummaryStateDeleted extends AbstractDaySummaryStateEvent implements DaySummaryEvent.DaySummaryStateDeleted
    {
        public AbstractDaySummaryStateDeleted() {
            this(new DaySummaryEventId());
        }

        public AbstractDaySummaryStateDeleted(DaySummaryEventId eventId) {
            super(eventId);
        }

        public String getEventType() {
            return StateEventType.DELETED;
        }

    }

    public static class SimpleDaySummaryStateCreated extends AbstractDaySummaryStateCreated
    {
        public SimpleDaySummaryStateCreated() {
        }

        public SimpleDaySummaryStateCreated(DaySummaryEventId eventId) {
            super(eventId);
        }
    }

    public static class SimpleDaySummaryStateMergePatched extends AbstractDaySummaryStateMergePatched
    {
        public SimpleDaySummaryStateMergePatched() {
        }

        public SimpleDaySummaryStateMergePatched(DaySummaryEventId eventId) {
            super(eventId);
        }
    }

    public static class SimpleDaySummaryStateDeleted extends AbstractDaySummaryStateDeleted
    {
        public SimpleDaySummaryStateDeleted() {
        }

        public SimpleDaySummaryStateDeleted(DaySummaryEventId eventId) {
            super(eventId);
        }
    }

}

