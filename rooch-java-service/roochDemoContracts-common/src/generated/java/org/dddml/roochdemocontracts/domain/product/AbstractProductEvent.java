// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.domain.product;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.roochdemocontracts.domain.*;
import org.dddml.roochdemocontracts.specialization.*;
import org.dddml.roochdemocontracts.domain.AbstractEvent;

public abstract class AbstractProductEvent extends AbstractEvent implements ProductEvent.SqlProductEvent, RoochEvent.MutableRoochEvent, HasStatus.MutableHasStatus {
    private ProductEventId productEventId = new ProductEventId();

    public ProductEventId getProductEventId() {
        return this.productEventId;
    }

    public void setProductEventId(ProductEventId eventId) {
        this.productEventId = eventId;
    }
    
    public String getProductId() {
        return getProductEventId().getProductId();
    }

    public void setProductId(String productId) {
        getProductEventId().setProductId(productId);
    }

    private boolean eventReadOnly;

    public boolean getEventReadOnly() { return this.eventReadOnly; }

    public void setEventReadOnly(boolean readOnly) { this.eventReadOnly = readOnly; }

    public BigInteger getVersion() {
        return getProductEventId().getVersion();
    }
    
    public void setVersion(BigInteger version) {
        getProductEventId().setVersion(version);
    }

    private String id_;

    public String getId_() {
        return this.id_;
    }
    
    public void setId_(String id) {
        this.id_ = id;
    }

    private BigInteger roochEventVersion;

    public BigInteger getRoochEventVersion() {
        return this.roochEventVersion;
    }
    
    public void setRoochEventVersion(BigInteger roochEventVersion) {
        this.roochEventVersion = roochEventVersion;
    }

    private BigInteger roochEventSequenceNumber;

    public BigInteger getRoochEventSequenceNumber() {
        return this.roochEventSequenceNumber;
    }
    
    public void setRoochEventSequenceNumber(BigInteger roochEventSequenceNumber) {
        this.roochEventSequenceNumber = roochEventSequenceNumber;
    }

    private String roochEventType;

    public String getRoochEventType() {
        return this.roochEventType;
    }
    
    public void setRoochEventType(String roochEventType) {
        this.roochEventType = roochEventType;
    }

    private RoochEventGuid roochEventGuid;

    public RoochEventGuid getRoochEventGuid() {
        return this.roochEventGuid;
    }
    
    public void setRoochEventGuid(RoochEventGuid roochEventGuid) {
        this.roochEventGuid = roochEventGuid;
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

    protected AbstractProductEvent() {
    }

    protected AbstractProductEvent(ProductEventId eventId) {
        this.productEventId = eventId;
    }


    public abstract String getEventType();

    public static class ProductClobEvent extends  AbstractProductEvent {

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
            return "ProductClobEvent";
        }

    }

    public static class ProductCreated extends ProductClobEvent {

        @Override
        public String getEventType() {
            return "ProductCreated";
        }

        public String getName() {
            Object val = getDynamicProperties().get("name");
            if (val instanceof String) {
                return (String) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, String.class);
        }

        public void setName(String value) {
            getDynamicProperties().put("name", value);
        }

        public BigInteger getUnitPrice() {
            Object val = getDynamicProperties().get("unitPrice");
            if (val instanceof BigInteger) {
                return (BigInteger) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, BigInteger.class);
        }

        public void setUnitPrice(BigInteger value) {
            getDynamicProperties().put("unitPrice", value);
        }

    }


}

