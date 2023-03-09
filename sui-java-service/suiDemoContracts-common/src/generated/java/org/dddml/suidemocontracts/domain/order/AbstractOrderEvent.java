package org.dddml.suidemocontracts.domain.order;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.*;
import org.dddml.suidemocontracts.domain.AbstractEvent;

public abstract class AbstractOrderEvent extends AbstractEvent implements OrderEvent.SqlOrderEvent, SuiEventEnvelope.MutableSuiEventEnvelope, SuiMoveEvent.MutableSuiMoveEvent, HasStatus.MutableHasStatus 
{
    private OrderEventId orderEventId = new OrderEventId();

    public OrderEventId getOrderEventId() {
        return this.orderEventId;
    }

    public void setOrderEventId(OrderEventId eventId) {
        this.orderEventId = eventId;
    }
    
    public String getId() {
        return getOrderEventId().getId();
    }

    public void setId(String id) {
        getOrderEventId().setId(id);
    }

    private boolean eventReadOnly;

    public boolean getEventReadOnly() { return this.eventReadOnly; }

    public void setEventReadOnly(boolean readOnly) { this.eventReadOnly = readOnly; }

    public BigInteger getVersion() {
        return getOrderEventId().getVersion();
    }
    
    public void setVersion(BigInteger version) {
        getOrderEventId().setVersion(version);
    }

    private Long suiTimestamp;

    public Long getSuiTimestamp() {
        return this.suiTimestamp;
    }
    
    public void setSuiTimestamp(Long suiTimestamp) {
        this.suiTimestamp = suiTimestamp;
    }

    private String suiTxDigest;

    public String getSuiTxDigest() {
        return this.suiTxDigest;
    }
    
    public void setSuiTxDigest(String suiTxDigest) {
        this.suiTxDigest = suiTxDigest;
    }

    private Long suiEventSeq;

    public Long getSuiEventSeq() {
        return this.suiEventSeq;
    }
    
    public void setSuiEventSeq(Long suiEventSeq) {
        this.suiEventSeq = suiEventSeq;
    }

    private String suiPackageId;

    public String getSuiPackageId() {
        return this.suiPackageId;
    }
    
    public void setSuiPackageId(String suiPackageId) {
        this.suiPackageId = suiPackageId;
    }

    private String suiTransactionModule;

    public String getSuiTransactionModule() {
        return this.suiTransactionModule;
    }
    
    public void setSuiTransactionModule(String suiTransactionModule) {
        this.suiTransactionModule = suiTransactionModule;
    }

    private String suiSender;

    public String getSuiSender() {
        return this.suiSender;
    }
    
    public void setSuiSender(String suiSender) {
        this.suiSender = suiSender;
    }

    private String suiType;

    public String getSuiType() {
        return this.suiType;
    }
    
    public void setSuiType(String suiType) {
        this.suiType = suiType;
    }

    private String status;

    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
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

    protected AbstractOrderEvent() {
    }

    protected AbstractOrderEvent(OrderEventId eventId) {
        this.orderEventId = eventId;
    }

    protected OrderItemEventDao getOrderItemEventDao() {
        return (OrderItemEventDao)ApplicationContext.current.get("orderItemEventDao");
    }

    protected OrderItemEventId newOrderItemEventId(String productId)
    {
        OrderItemEventId eventId = new OrderItemEventId(this.getOrderEventId().getId(), 
            productId, 
            this.getOrderEventId().getVersion());
        return eventId;
    }

    protected void throwOnInconsistentEventIds(OrderItemEvent.SqlOrderItemEvent e)
    {
        throwOnInconsistentEventIds(this, e);
    }

    public static void throwOnInconsistentEventIds(OrderEvent.SqlOrderEvent oe, OrderItemEvent.SqlOrderItemEvent e)
    {
        if (!oe.getOrderEventId().getId().equals(e.getOrderItemEventId().getOrderId()))
        { 
            throw DomainError.named("inconsistentEventIds", "Outer Id Id %1$s but inner id OrderId %2$s", 
                oe.getOrderEventId().getId(), e.getOrderItemEventId().getOrderId());
        }
    }


    public abstract String getEventType();

    public static class OrderClobEvent extends  AbstractOrderEvent {

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
            return "OrderClobEvent";
        }

    }

    public static class OrderCreated extends OrderClobEvent {

        @Override
        public String getEventType() {
            return "OrderCreated";
        }

        public String getProduct() {
            Object val = getDynamicProperties().get("product");
            if (val instanceof String) {
                return (String) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, String.class);
        }

        public void setProduct(String value) {
            getDynamicProperties().put("product", value);
        }

        public BigInteger getQuantity() {
            Object val = getDynamicProperties().get("quantity");
            if (val instanceof BigInteger) {
                return (BigInteger) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, BigInteger.class);
        }

        public void setQuantity(BigInteger value) {
            getDynamicProperties().put("quantity", value);
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

        public BigInteger getTotalAmount() {
            Object val = getDynamicProperties().get("totalAmount");
            if (val instanceof BigInteger) {
                return (BigInteger) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, BigInteger.class);
        }

        public void setTotalAmount(BigInteger value) {
            getDynamicProperties().put("totalAmount", value);
        }

        public String getOwner() {
            Object val = getDynamicProperties().get("owner");
            if (val instanceof String) {
                return (String) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, String.class);
        }

        public void setOwner(String value) {
            getDynamicProperties().put("owner", value);
        }

    }

    public static class OrderItemRemoved extends OrderClobEvent {

        @Override
        public String getEventType() {
            return "OrderItemRemoved";
        }

        public String getProductId() {
            Object val = getDynamicProperties().get("productId");
            if (val instanceof String) {
                return (String) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, String.class);
        }

        public void setProductId(String value) {
            getDynamicProperties().put("productId", value);
        }

    }

    public static class OrderItemQuantityUpdated extends OrderClobEvent {

        @Override
        public String getEventType() {
            return "OrderItemQuantityUpdated";
        }

        public String getProductId() {
            Object val = getDynamicProperties().get("productId");
            if (val instanceof String) {
                return (String) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, String.class);
        }

        public void setProductId(String value) {
            getDynamicProperties().put("productId", value);
        }

        public BigInteger getQuantity() {
            Object val = getDynamicProperties().get("quantity");
            if (val instanceof BigInteger) {
                return (BigInteger) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, BigInteger.class);
        }

        public void setQuantity(BigInteger value) {
            getDynamicProperties().put("quantity", value);
        }

    }


}

