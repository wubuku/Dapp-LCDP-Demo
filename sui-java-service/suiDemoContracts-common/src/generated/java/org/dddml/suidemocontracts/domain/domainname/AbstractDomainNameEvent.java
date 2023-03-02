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

    protected AbstractDomainNameEvent() {
    }

    protected AbstractDomainNameEvent(DomainNameEventId eventId) {
        this.domainNameEventId = eventId;
    }


    public abstract String getEventType();

    public static class DomainNameClobEvent extends  AbstractDomainNameEvent {

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
            return "DomainNameClobEvent";
        }

    }

    public static class Registered extends DomainNameClobEvent {

        @Override
        public String getEventType() {
            return "Registered";
        }

        public BigInteger getRegistrationPeriod() {
            Object val = getLobProperties().get("registrationPeriod");
            if (val instanceof BigInteger) {
                return (BigInteger) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, BigInteger.class);
        }

        public void setRegistrationPeriod(BigInteger value) {
            getLobProperties().put("registrationPeriod", value);
        }

        public String getOwner() {
            Object val = getLobProperties().get("owner");
            if (val instanceof String) {
                return (String) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, String.class);
        }

        public void setOwner(String value) {
            getLobProperties().put("owner", value);
        }

    }

    public static class Renewed extends DomainNameClobEvent {

        @Override
        public String getEventType() {
            return "Renewed";
        }

        public BigInteger getRenewPeriod() {
            Object val = getLobProperties().get("renewPeriod");
            if (val instanceof BigInteger) {
                return (BigInteger) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, BigInteger.class);
        }

        public void setRenewPeriod(BigInteger value) {
            getLobProperties().put("renewPeriod", value);
        }

        public String getAccount() {
            Object val = getLobProperties().get("account");
            if (val instanceof String) {
                return (String) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, String.class);
        }

        public void setAccount(String value) {
            getLobProperties().put("account", value);
        }

    }


}
