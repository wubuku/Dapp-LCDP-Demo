// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.aptosdemocontracts.domain.daysummary;

import java.util.*;
import org.dddml.aptosdemocontracts.domain.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.aptosdemocontracts.specialization.*;
import org.dddml.aptosdemocontracts.domain.AbstractEvent;

public abstract class AbstractDaySummaryEvent extends AbstractEvent implements DaySummaryEvent.SqlDaySummaryEvent, AptosEvent.MutableAptosEvent, HasStatus.MutableHasStatus {
    private DaySummaryEventId daySummaryEventId = new DaySummaryEventId();

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

    public BigInteger getVersion() {
        return getDaySummaryEventId().getVersion();
    }
    
    public void setVersion(BigInteger version) {
        getDaySummaryEventId().setVersion(version);
    }

    private BigInteger aptosEventVersion;

    public BigInteger getAptosEventVersion() {
        return this.aptosEventVersion;
    }
    
    public void setAptosEventVersion(BigInteger aptosEventVersion) {
        this.aptosEventVersion = aptosEventVersion;
    }

    private BigInteger aptosEventSequenceNumber;

    public BigInteger getAptosEventSequenceNumber() {
        return this.aptosEventSequenceNumber;
    }
    
    public void setAptosEventSequenceNumber(BigInteger aptosEventSequenceNumber) {
        this.aptosEventSequenceNumber = aptosEventSequenceNumber;
    }

    private String aptosEventType;

    public String getAptosEventType() {
        return this.aptosEventType;
    }
    
    public void setAptosEventType(String aptosEventType) {
        this.aptosEventType = aptosEventType;
    }

    private AptosEventGuid aptosEventGuid;

    public AptosEventGuid getAptosEventGuid() {
        return this.aptosEventGuid;
    }
    
    public void setAptosEventGuid(AptosEventGuid aptosEventGuid) {
        this.aptosEventGuid = aptosEventGuid;
    }

    private String status;

    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
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


    private String commandId;

    public String getCommandId() {
        return commandId;
    }

    public void setCommandId(String commandId) {
        this.commandId = commandId;
    }

    private String commandType;

    public String getCommandType() {
        return commandType;
    }

    public void setCommandType(String commandType) {
        this.commandType = commandType;
    }

    protected AbstractDaySummaryEvent() {
    }

    protected AbstractDaySummaryEvent(DaySummaryEventId eventId) {
        this.daySummaryEventId = eventId;
    }


    public abstract String getEventClass();

    public static class DaySummaryClobEvent extends AbstractDaySummaryEvent {

        protected Map<String, Object> getDynamicProperties() {
            return dynamicProperties;
        }

        protected void setDynamicProperties(Map<String, Object> dynamicProperties) {
            if (dynamicProperties == null) {
                throw new IllegalArgumentException("dynamicProperties is null.");
            }
            this.dynamicProperties = dynamicProperties;
        }

        private Map<String, Object> dynamicProperties = new HashMap<>();

        protected String getDynamicPropertiesLob() {
            return ApplicationContext.current.getClobConverter().toString(getDynamicProperties());
        }

        protected void setDynamicPropertiesLob(String text) {
            getDynamicProperties().clear();
            Map<String, Object> ps = ApplicationContext.current.getClobConverter().parseLobProperties(text);
            if (ps != null) {
                for (Map.Entry<String, Object> kv : ps.entrySet()) {
                    getDynamicProperties().put(kv.getKey(), kv.getValue());
                }
            }
        }

        @Override
        public String getEventClass() {
            return "DaySummaryClobEvent";
        }

    }

    public static class DaySummaryCreated extends DaySummaryClobEvent implements DaySummaryEvent.DaySummaryCreated {

        @Override
        public String getEventClass() {
            return "DaySummaryCreated";
        }

        public String getDescription() {
            Object val = getDynamicProperties().get("description");
            if (val instanceof String) {
                return (String) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, String.class);
        }

        public void setDescription(String value) {
            getDynamicProperties().put("description", value);
        }

        public String getMetaData() {
            Object val = getDynamicProperties().get("metaData");
            if (val instanceof String) {
                return (String) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, String.class);
        }

        public void setMetaData(String value) {
            getDynamicProperties().put("metaData", value);
        }

        public String[] getArrayData() {
            Object val = getDynamicProperties().get("arrayData");
            if (val instanceof String[]) {
                return (String[]) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, String[].class);
        }

        public void setArrayData(String[] value) {
            getDynamicProperties().put("arrayData", value);
        }

        public String getOptionalData() {
            Object val = getDynamicProperties().get("optionalData");
            if (val instanceof String) {
                return (String) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, String.class);
        }

        public void setOptionalData(String value) {
            getDynamicProperties().put("optionalData", value);
        }

        public Integer[] getU16ArrayData() {
            Object val = getDynamicProperties().get("u16ArrayData");
            if (val instanceof Integer[]) {
                return (Integer[]) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, Integer[].class);
        }

        public void setU16ArrayData(Integer[] value) {
            getDynamicProperties().put("u16ArrayData", value);
        }

        public Long[] getU32ArrayData() {
            Object val = getDynamicProperties().get("u32ArrayData");
            if (val instanceof Long[]) {
                return (Long[]) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, Long[].class);
        }

        public void setU32ArrayData(Long[] value) {
            getDynamicProperties().put("u32ArrayData", value);
        }

        public BigInteger[] getU64ArrayData() {
            Object val = getDynamicProperties().get("u64ArrayData");
            if (val instanceof BigInteger[]) {
                return (BigInteger[]) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, BigInteger[].class);
        }

        public void setU64ArrayData(BigInteger[] value) {
            getDynamicProperties().put("u64ArrayData", value);
        }

        public BigInteger[] getU128ArrayData() {
            Object val = getDynamicProperties().get("u128ArrayData");
            if (val instanceof BigInteger[]) {
                return (BigInteger[]) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, BigInteger[].class);
        }

        public void setU128ArrayData(BigInteger[] value) {
            getDynamicProperties().put("u128ArrayData", value);
        }

        public BigInteger[] getU256ArrayData() {
            Object val = getDynamicProperties().get("u256ArrayData");
            if (val instanceof BigInteger[]) {
                return (BigInteger[]) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, BigInteger[].class);
        }

        public void setU256ArrayData(BigInteger[] value) {
            getDynamicProperties().put("u256ArrayData", value);
        }

    }


}

