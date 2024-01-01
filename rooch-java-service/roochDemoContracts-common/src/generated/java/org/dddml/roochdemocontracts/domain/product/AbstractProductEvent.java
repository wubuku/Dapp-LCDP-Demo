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

    protected AbstractProductEvent() {
    }

    protected AbstractProductEvent(ProductEventId eventId) {
        this.productEventId = eventId;
    }


    public abstract String getEventType();

    public static class ProductClobEvent extends AbstractProductEvent {

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

    public static class ProductCrudEvent extends ProductClobEvent implements ProductEvent.ProductCrudEvent {

        @Override
        public String getEventType() {
            return "ProductCrudEvent";
        }

        public Integer getCrudType() {
            Object val = getDynamicProperties().get("crudType");
            if (val instanceof Integer) {
                return (Integer) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, Integer.class);
        }

        public void setCrudType(Integer value) {
            getDynamicProperties().put("crudType", value);
        }

        public String getId() {
            Object val = getDynamicProperties().get("id");
            if (val instanceof String) {
                return (String) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, String.class);
        }

        public void setId(String value) {
            getDynamicProperties().put("id", value);
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

