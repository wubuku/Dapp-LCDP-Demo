// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.domain.daysummary;

import java.util.*;
import java.math.*;
import org.dddml.roochdemocontracts.domain.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.roochdemocontracts.specialization.*;
import org.dddml.roochdemocontracts.domain.daysummary.DaySummaryEvent.*;

public abstract class AbstractDaySummaryState implements DaySummaryState.SqlDaySummaryState {

    private Day day;

    public Day getDay() {
        return this.day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    private String id_;

    public String getId_() {
        return this.id_;
    }

    public void setId_(String id) {
        this.id_ = id;
    }

    private String description;

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String metadata;

    public String getMetadata() {
        return this.metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    private String optionalData;

    public String getOptionalData() {
        return this.optionalData;
    }

    public void setOptionalData(String optionalData) {
        this.optionalData = optionalData;
    }

    private BigInteger version;

    public BigInteger getVersion() {
        return this.version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    private Long offChainVersion;

    public Long getOffChainVersion() {
        return this.offChainVersion;
    }

    public void setOffChainVersion(Long offChainVersion) {
        this.offChainVersion = offChainVersion;
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

    private Set<Integer> u16ArrayData;

    public Set<Integer> getU16ArrayData() {
        return this.u16ArrayData;
    }

    public void setU16ArrayData(Set<Integer> u16ArrayData) {
        this.u16ArrayData = u16ArrayData;
    }

    private Set<Long> u32ArrayData;

    public Set<Long> getU32ArrayData() {
        return this.u32ArrayData;
    }

    public void setU32ArrayData(Set<Long> u32ArrayData) {
        this.u32ArrayData = u32ArrayData;
    }

    private Set<BigInteger> u64ArrayData;

    public Set<BigInteger> getU64ArrayData() {
        return this.u64ArrayData;
    }

    public void setU64ArrayData(Set<BigInteger> u64ArrayData) {
        this.u64ArrayData = u64ArrayData;
    }

    private Set<BigInteger> u128ArrayData;

    public Set<BigInteger> getU128ArrayData() {
        return this.u128ArrayData;
    }

    public void setU128ArrayData(Set<BigInteger> u128ArrayData) {
        this.u128ArrayData = u128ArrayData;
    }

    private Set<BigInteger> u256ArrayData;

    public Set<BigInteger> getU256ArrayData() {
        return this.u256ArrayData;
    }

    public void setU256ArrayData(Set<BigInteger> u256ArrayData) {
        this.u256ArrayData = u256ArrayData;
    }

    public boolean isStateUnsaved() {
        return this.getOffChainVersion() == null;
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
                this.setOffChainVersion((this.getOffChainVersion() == null ? DaySummaryState.VERSION_NULL : this.getOffChainVersion()) + 1);
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
        } else if (e instanceof AbstractDaySummaryEvent.DaySummaryDeleted) {
            when((AbstractDaySummaryEvent.DaySummaryDeleted)e);
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
        this.setU16ArrayData(s.getU16ArrayData());
        this.setU32ArrayData(s.getU32ArrayData());
        this.setU64ArrayData(s.getU64ArrayData());
        this.setU128ArrayData(s.getU128ArrayData());
        this.setU256ArrayData(s.getU256ArrayData());
        this.setVersion(s.getVersion());
        this.setActive(s.getActive());
    }

    public void when(AbstractDaySummaryEvent.DaySummaryCreated e) {
        throwOnWrongEvent(e);

        String description = e.getDescription();
        String Description = description;
        String metaData = e.getMetaData();
        String MetaData = metaData;
        String[] arrayData = e.getArrayData();
        String[] ArrayData = arrayData;
        String optionalData = e.getOptionalData();
        String OptionalData = optionalData;
        Integer[] u16ArrayData = e.getU16ArrayData();
        Integer[] U16ArrayData = u16ArrayData;
        Long[] u32ArrayData = e.getU32ArrayData();
        Long[] U32ArrayData = u32ArrayData;
        BigInteger[] u64ArrayData = e.getU64ArrayData();
        BigInteger[] U64ArrayData = u64ArrayData;
        BigInteger[] u128ArrayData = e.getU128ArrayData();
        BigInteger[] U128ArrayData = u128ArrayData;
        BigInteger[] u256ArrayData = e.getU256ArrayData();
        BigInteger[] U256ArrayData = u256ArrayData;
        RoochEventId roochEventId = e.getRoochEventId();
        RoochEventId RoochEventId = roochEventId;
        String roochSender = e.getRoochSender();
        String RoochSender = roochSender;
        String roochTxHash = e.getRoochTxHash();
        String RoochTxHash = roochTxHash;
        String roochTypeTag = e.getRoochTypeTag();
        String RoochTypeTag = roochTypeTag;
        Long roochTimestampMs = e.getRoochTimestampMs();
        Long RoochTimestampMs = roochTimestampMs;
        BigInteger roochBlockHeight = e.getRoochBlockHeight();
        BigInteger RoochBlockHeight = roochBlockHeight;
        Long roochEventIndex = e.getRoochEventIndex();
        Long RoochEventIndex = roochEventIndex;
        String status = e.getStatus();
        String Status = status;

        if (this.getCreatedBy() == null){
            this.setCreatedBy(e.getCreatedBy());
        }
        if (this.getCreatedAt() == null){
            this.setCreatedAt(e.getCreatedAt());
        }
        this.setUpdatedBy(e.getCreatedBy());
        this.setUpdatedAt(e.getCreatedAt());

        DaySummaryState updatedDaySummaryState = (DaySummaryState) ReflectUtils.invokeStaticMethod(
                    "org.dddml.roochdemocontracts.domain.daysummary.CreateLogic",
                    "mutate",
                    new Class[]{DaySummaryState.class, String.class, String.class, String[].class, String.class, Integer[].class, Long[].class, BigInteger[].class, BigInteger[].class, BigInteger[].class, RoochEventId.class, String.class, String.class, String.class, Long.class, BigInteger.class, Long.class, String.class, MutationContext.class},
                    new Object[]{this, description, metaData, arrayData, optionalData, u16ArrayData, u32ArrayData, u64ArrayData, u128ArrayData, u256ArrayData, roochEventId, roochSender, roochTxHash, roochTypeTag, roochTimestampMs, roochBlockHeight, roochEventIndex, status, MutationContext.forEvent(e, s -> {if (s == this) {return this;} else {throw new UnsupportedOperationException();}})}
            );

//package org.dddml.roochdemocontracts.domain.daysummary;
//
//public class CreateLogic {
//    public static DaySummaryState mutate(DaySummaryState daySummaryState, String description, String metaData, String[] arrayData, String optionalData, Integer[] u16ArrayData, Long[] u32ArrayData, BigInteger[] u64ArrayData, BigInteger[] u128ArrayData, BigInteger[] u256ArrayData, RoochEventId roochEventId, String roochSender, String roochTxHash, String roochTypeTag, Long roochTimestampMs, BigInteger roochBlockHeight, Long roochEventIndex, String status, MutationContext<DaySummaryState, DaySummaryState.MutableDaySummaryState> mutationContext) {
//    }
//}

        if (this != updatedDaySummaryState) { merge(updatedDaySummaryState); } //else do nothing

    }

    public void when(AbstractDaySummaryEvent.DaySummaryDeleted e) {
        throwOnWrongEvent(e);

        RoochEventId roochEventId = e.getRoochEventId();
        RoochEventId RoochEventId = roochEventId;
        String roochSender = e.getRoochSender();
        String RoochSender = roochSender;
        String roochTxHash = e.getRoochTxHash();
        String RoochTxHash = roochTxHash;
        String roochTypeTag = e.getRoochTypeTag();
        String RoochTypeTag = roochTypeTag;
        Long roochTimestampMs = e.getRoochTimestampMs();
        Long RoochTimestampMs = roochTimestampMs;
        BigInteger roochBlockHeight = e.getRoochBlockHeight();
        BigInteger RoochBlockHeight = roochBlockHeight;
        Long roochEventIndex = e.getRoochEventIndex();
        Long RoochEventIndex = roochEventIndex;
        String status = e.getStatus();
        String Status = status;

        if (this.getCreatedBy() == null){
            this.setCreatedBy(e.getCreatedBy());
        }
        if (this.getCreatedAt() == null){
            this.setCreatedAt(e.getCreatedAt());
        }
        this.setUpdatedBy(e.getCreatedBy());
        this.setUpdatedAt(e.getCreatedAt());

        DaySummaryState updatedDaySummaryState = (DaySummaryState) ReflectUtils.invokeStaticMethod(
                    "org.dddml.roochdemocontracts.domain.daysummary.DeleteLogic",
                    "mutate",
                    new Class[]{DaySummaryState.class, RoochEventId.class, String.class, String.class, String.class, Long.class, BigInteger.class, Long.class, String.class, MutationContext.class},
                    new Object[]{this, roochEventId, roochSender, roochTxHash, roochTypeTag, roochTimestampMs, roochBlockHeight, roochEventIndex, status, MutationContext.forEvent(e, s -> {if (s == this) {return this;} else {throw new UnsupportedOperationException();}})}
            );

//package org.dddml.roochdemocontracts.domain.daysummary;
//
//public class DeleteLogic {
//    public static DaySummaryState mutate(DaySummaryState daySummaryState, RoochEventId roochEventId, String roochSender, String roochTxHash, String roochTypeTag, Long roochTimestampMs, BigInteger roochBlockHeight, Long roochEventIndex, String status, MutationContext<DaySummaryState, DaySummaryState.MutableDaySummaryState> mutationContext) {
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


        Long stateVersion = this.getOffChainVersion();

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

