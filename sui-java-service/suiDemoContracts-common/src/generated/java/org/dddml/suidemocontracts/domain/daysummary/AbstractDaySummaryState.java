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
        if (false) { 
            ;
        } else if (e instanceof AbstractDaySummaryEvent.DaySummaryCreated) {
            when((AbstractDaySummaryEvent.DaySummaryCreated)e);
        } else {
            throw new UnsupportedOperationException(String.format("Unsupported event type: %1$s", e.getClass().getName()));
        }
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

    public void when(AbstractDaySummaryEvent.DaySummaryCreated e) {
        throwOnWrongEvent(e);

        String description = e.getDescription();
        String Description = description;
        int[] metaData = e.getMetaData();
        int[] MetaData = metaData;
        String[] arrayData = e.getArrayData();
        String[] ArrayData = arrayData;
        int[] optionalData = e.getOptionalData();
        int[] OptionalData = optionalData;

        if (this.getCreatedBy() == null){
            this.setCreatedBy(e.getCreatedBy());
        }
        if (this.getCreatedAt() == null){
            this.setCreatedAt(e.getCreatedAt());
        }
        this.setUpdatedBy(e.getCreatedBy());
        this.setUpdatedAt(e.getCreatedAt());

        DaySummaryState updatedDaySummaryState = (DaySummaryState) ReflectUtils.invokeStaticMethod(
                    "org.dddml.suidemocontracts.domain.daysummary.CreateLogic",
                    "mutate",
                    new Class[]{DaySummaryState.class, String.class, int[].class, String[].class, int[].class, MutationContext.class},
                    new Object[]{this, description, metaData, arrayData, optionalData, MutationContext.forEvent(e, s -> {if (s == this) {return this;} else {throw new UnsupportedOperationException();}})}
            );

//package org.dddml.suidemocontracts.domain.daysummary;
//
//public class CreateLogic {
//    public static DaySummaryState mutate(DaySummaryState daySummaryState, String description, int[] metaData, String[] arrayData, int[] optionalData, MutationContext<DaySummaryState, DaySummaryState.MutableDaySummaryState> mutationContext) {
//    }
//}

        if (this != updatedDaySummaryState) { merge(updatedDaySummaryState); } //else do nothing

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

