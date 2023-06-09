// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.domain.daysummary;

import java.util.*;
import org.dddml.roochdemocontracts.domain.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.roochdemocontracts.specialization.*;
import org.dddml.roochdemocontracts.domain.AbstractEvent;

public abstract class AbstractDaySummaryEvent extends AbstractEvent implements DaySummaryEvent.SqlDaySummaryEvent, RoochEvent.MutableRoochEvent, HasStatus.MutableHasStatus {
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

    private String id_;

    public String getId_() {
        return this.id_;
    }
    
    public void setId_(String id) {
        this.id_ = id;
    }

    private RoochEventId roochEventId;

    public RoochEventId getRoochEventId() {
        return this.roochEventId;
    }
    
    public void setRoochEventId(RoochEventId roochEventId) {
        this.roochEventId = roochEventId;
    }

    private String roochSender;

    public String getRoochSender() {
        return this.roochSender;
    }
    
    public void setRoochSender(String roochSender) {
        this.roochSender = roochSender;
    }

    private String roochTxHash;

    public String getRoochTxHash() {
        return this.roochTxHash;
    }
    
    public void setRoochTxHash(String roochTxHash) {
        this.roochTxHash = roochTxHash;
    }

    private String roochTypeTag;

    public String getRoochTypeTag() {
        return this.roochTypeTag;
    }
    
    public void setRoochTypeTag(String roochTypeTag) {
        this.roochTypeTag = roochTypeTag;
    }

    private Long roochTimestampMs;

    public Long getRoochTimestampMs() {
        return this.roochTimestampMs;
    }
    
    public void setRoochTimestampMs(Long roochTimestampMs) {
        this.roochTimestampMs = roochTimestampMs;
    }

    private BigInteger roochBlockHeight;

    public BigInteger getRoochBlockHeight() {
        return this.roochBlockHeight;
    }
    
    public void setRoochBlockHeight(BigInteger roochBlockHeight) {
        this.roochBlockHeight = roochBlockHeight;
    }

    private Long roochEventIndex;

    public Long getRoochEventIndex() {
        return this.roochEventIndex;
    }
    
    public void setRoochEventIndex(Long roochEventIndex) {
        this.roochEventIndex = roochEventIndex;
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


    public abstract String getEventType();

    public static class DaySummaryClobEvent extends  AbstractDaySummaryEvent {

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
        public String getEventType() {
            return "DaySummaryClobEvent";
        }

    }

    public static class DaySummaryCreated extends DaySummaryClobEvent {

        @Override
        public String getEventType() {
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

        public int[] getMetaData() {
            Object val = getDynamicProperties().get("metaData");
            if (val instanceof int[]) {
                return (int[]) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, int[].class);
        }

        public void setMetaData(int[] value) {
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

