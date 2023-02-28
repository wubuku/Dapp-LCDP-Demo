package org.dddml.suidemocontracts.domain.daysummary;

import java.util.*;
import org.dddml.suidemocontracts.domain.*;
import java.util.Date;
import org.dddml.suidemocontracts.specialization.*;
import org.dddml.suidemocontracts.domain.daysummary.DaySummaryEvent.*;

public abstract class AbstractDaySummaryState implements DaySummaryState.SqlDaySummaryState {

    private Day day;

    public Day getDay() {
        return this.day;
    }

    public void setDay(Day day) {
        this.day = day;
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

    private Long version;

    public Long getVersion() {
        return this.version;
    }

    public void setVersion(Long version) {
        this.version = version;
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

    private String updatedBy;

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    private Date updatedAt;

    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    private Boolean active;

    public Boolean getActive() {
        return this.active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    private Boolean deleted;

    public Boolean getDeleted() {
        return this.deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    private Set<String> arrayData;

    public Set<String> getArrayData() {
        return this.arrayData;
    }

    public void setArrayData(Set<String> arrayData) {
        this.arrayData = arrayData;
    }

    public boolean isStateUnsaved() {
        return this.getVersion() == null;
    }

    private Boolean stateReadOnly;

    public Boolean getStateReadOnly() { return this.stateReadOnly; }

    public void setStateReadOnly(Boolean readOnly) { this.stateReadOnly = readOnly; }

    private boolean forReapplying;

    public boolean getForReapplying() {
        return forReapplying;
    }

    public void setForReapplying(boolean forReapplying) {
        this.forReapplying = forReapplying;
    }

    public AbstractDaySummaryState(List<Event> events) {
        initializeForReapplying();
        if (events != null && events.size() > 0) {
            this.setDay(((DaySummaryEvent.SqlDaySummaryEvent) events.get(0)).getDaySummaryEventId().getDay());
            for (Event e : events) {
                mutate(e);
                this.setVersion((this.getVersion() == null ? DaySummaryState.VERSION_NULL : this.getVersion()) + 1);
            }
        }
    }


    public AbstractDaySummaryState() {
        initializeProperties();
    }

    protected void initializeForReapplying() {
        this.forReapplying = true;

        initializeProperties();
    }
    
    protected void initializeProperties() {
    }

    @Override
    public int hashCode() {
        return getDay().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        if (obj instanceof DaySummaryState) {
            return Objects.equals(this.getDay(), ((DaySummaryState)obj).getDay());
        }
        return false;
    }


    public void mutate(Event e) {
        setStateReadOnly(false);
        if (e instanceof DaySummaryStateCreated) {
            when((DaySummaryStateCreated) e);
        } else if (e instanceof DaySummaryStateMergePatched) {
            when((DaySummaryStateMergePatched) e);
        } else if (e instanceof DaySummaryStateDeleted) {
            when((DaySummaryStateDeleted) e);
        } else {
            throw new UnsupportedOperationException(String.format("Unsupported event type: %1$s", e.getClass().getName()));
        }
    }

    public void when(DaySummaryStateCreated e) {
        throwOnWrongEvent(e);

        this.setDescription(e.getDescription());
        this.setMetadata(e.getMetadata());
        this.setArrayData(e.getArrayData());
        this.setOptionalData(e.getOptionalData());
        this.setActive(e.getActive());

        this.setDeleted(false);

        this.setCreatedBy(e.getCreatedBy());
        this.setCreatedAt(e.getCreatedAt());

    }

    protected void merge(DaySummaryState s) {
        if (s == this) {
            return;
        }
        this.setDescription(s.getDescription());
        this.setMetadata(s.getMetadata());
        this.setArrayData(s.getArrayData());
        this.setOptionalData(s.getOptionalData());
        this.setActive(s.getActive());
    }

    public void when(DaySummaryStateMergePatched e) {
        throwOnWrongEvent(e);

        if (e.getDescription() == null) {
            if (e.getIsPropertyDescriptionRemoved() != null && e.getIsPropertyDescriptionRemoved()) {
                this.setDescription(null);
            }
        } else {
            this.setDescription(e.getDescription());
        }
        if (e.getMetadata() == null) {
            if (e.getIsPropertyMetadataRemoved() != null && e.getIsPropertyMetadataRemoved()) {
                this.setMetadata(null);
            }
        } else {
            this.setMetadata(e.getMetadata());
        }
        if (e.getArrayData() == null) {
            if (e.getIsPropertyArrayDataRemoved() != null && e.getIsPropertyArrayDataRemoved()) {
                this.setArrayData(null);
            }
        } else {
            this.setArrayData(e.getArrayData());
        }
        if (e.getOptionalData() == null) {
            if (e.getIsPropertyOptionalDataRemoved() != null && e.getIsPropertyOptionalDataRemoved()) {
                this.setOptionalData(null);
            }
        } else {
            this.setOptionalData(e.getOptionalData());
        }
        if (e.getActive() == null) {
            if (e.getIsPropertyActiveRemoved() != null && e.getIsPropertyActiveRemoved()) {
                this.setActive(null);
            }
        } else {
            this.setActive(e.getActive());
        }

        this.setUpdatedBy(e.getCreatedBy());
        this.setUpdatedAt(e.getCreatedAt());

    }

    public void when(DaySummaryStateDeleted e) {
        throwOnWrongEvent(e);

        this.setDeleted(true);
        this.setUpdatedBy(e.getCreatedBy());
        this.setUpdatedAt(e.getCreatedAt());

    }

    public void save() {
    }

    protected void throwOnWrongEvent(DaySummaryEvent event) {
        Day stateEntityId = this.getDay(); // Aggregate Id
        Day eventEntityId = ((DaySummaryEvent.SqlDaySummaryEvent)event).getDaySummaryEventId().getDay(); // EntityBase.Aggregate.GetEventIdPropertyIdName();
        if (!stateEntityId.equals(eventEntityId)) {
            throw DomainError.named("mutateWrongEntity", "Entity Id %1$s in state but entity id %2$s in event", stateEntityId, eventEntityId);
        }


        Long stateVersion = this.getVersion();
        Long eventVersion = ((DaySummaryEvent.SqlDaySummaryEvent)event).getDaySummaryEventId().getVersion();// Aggregate Version
        if (eventVersion == null) {
            throw new NullPointerException("event.getDaySummaryEventId().getVersion() == null");
        }
        if (!(stateVersion == null && eventVersion.equals(DaySummaryState.VERSION_NULL)) && !eventVersion.equals(stateVersion)) {
            throw DomainError.named("concurrencyConflict", "Conflict between state version (%1$s) and event version (%2$s)", stateVersion, eventVersion);
        }

    }


    public static class SimpleDaySummaryState extends AbstractDaySummaryState {

        public SimpleDaySummaryState() {
        }

        public SimpleDaySummaryState(List<Event> events) {
            super(events);
        }

        public static SimpleDaySummaryState newForReapplying() {
            SimpleDaySummaryState s = new SimpleDaySummaryState();
            s.initializeForReapplying();
            return s;
        }

    }



}

