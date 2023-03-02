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

    private String surrogateId;

    public String getSurrogateId() {
        return this.surrogateId;
    }
    
    public void setSurrogateId(String id) {
        this.surrogateId = id;
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

        protected Map<String, Object> getLobProperties() {
            return lobProperties;
        }

        protected void setLobProperties(Map<String, Object> lobProperties) {
            if (lobProperties == null) {
                throw new IllegalArgumentException("lobProperties is null.");
            }
            this.lobProperties = lobProperties;
        }

        private Map<String, Object> lobProperties = new HashMap<>();

        protected String getLobText() {
            return ApplicationContext.current.getClobConverter().toString(getLobProperties());
        }

        protected void setLobText(String text) {
            getLobProperties().clear();
            Map<String, Object> ps = ApplicationContext.current.getClobConverter().parseLobProperties(text);
            if (ps != null) {
                for (Map.Entry<String, Object> kv : ps.entrySet()) {
                    getLobProperties().put(kv.getKey(), kv.getValue());
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
            Object val = getLobProperties().get("description");
            if (val instanceof String) {
                return (String) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, String.class);
        }

        public void setDescription(String value) {
            getLobProperties().put("description", value);
        }

        public int[] getMetaData() {
            Object val = getLobProperties().get("metaData");
            if (val instanceof int[]) {
                return (int[]) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, int[].class);
        }

        public void setMetaData(int[] value) {
            getLobProperties().put("metaData", value);
        }

        public String[] getArrayData() {
            Object val = getLobProperties().get("arrayData");
            if (val instanceof String[]) {
                return (String[]) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, String[].class);
        }

        public void setArrayData(String[] value) {
            getLobProperties().put("arrayData", value);
        }

        public int[] getOptionalData() {
            Object val = getLobProperties().get("optionalData");
            if (val instanceof int[]) {
                return (int[]) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, int[].class);
        }

        public void setOptionalData(int[] value) {
            getLobProperties().put("optionalData", value);
        }

    }


}

