package org.dddml.suidemocontracts.domain.orderv2;

import java.util.*;
import java.math.BigInteger;
import org.dddml.suidemocontracts.domain.*;
import java.util.Date;
import org.dddml.suidemocontracts.specialization.*;
import org.dddml.suidemocontracts.domain.AbstractEvent;

public abstract class AbstractOrderV2Event extends AbstractEvent implements OrderV2Event.SqlOrderV2Event, SuiEventEnvelope.MutableSuiEventEnvelope, SuiMoveEvent.MutableSuiMoveEvent, HasStatus.MutableHasStatus 
{
    private OrderV2EventId orderV2EventId = new OrderV2EventId();

    public OrderV2EventId getOrderV2EventId() {
        return this.orderV2EventId;
    }

    public void setOrderV2EventId(OrderV2EventId eventId) {
        this.orderV2EventId = eventId;
    }
    
    public String getOrderId() {
        return getOrderV2EventId().getOrderId();
    }

    public void setOrderId(String orderId) {
        getOrderV2EventId().setOrderId(orderId);
    }

    private boolean eventReadOnly;

    public boolean getEventReadOnly() { return this.eventReadOnly; }

    public void setEventReadOnly(boolean readOnly) { this.eventReadOnly = readOnly; }

    public BigInteger getVersion() {
        return getOrderV2EventId().getVersion();
    }
    
    public void setVersion(BigInteger version) {
        getOrderV2EventId().setVersion(version);
    }

    private String id_;

    public String getId_() {
        return this.id_;
    }
    
    public void setId_(String id) {
        this.id_ = id;
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

    protected AbstractOrderV2Event() {
    }

    protected AbstractOrderV2Event(OrderV2EventId eventId) {
        this.orderV2EventId = eventId;
    }

    protected OrderV2ItemEventDao getOrderV2ItemEventDao() {
        return (OrderV2ItemEventDao)ApplicationContext.current.get("orderV2ItemEventDao");
    }

    protected OrderV2ItemEventId newOrderV2ItemEventId(String productId)
    {
        OrderV2ItemEventId eventId = new OrderV2ItemEventId(this.getOrderV2EventId().getOrderId(), 
            productId, 
            this.getOrderV2EventId().getVersion());
        return eventId;
    }

    protected void throwOnInconsistentEventIds(OrderV2ItemEvent.SqlOrderV2ItemEvent e)
    {
        throwOnInconsistentEventIds(this, e);
    }

    public static void throwOnInconsistentEventIds(OrderV2Event.SqlOrderV2Event oe, OrderV2ItemEvent.SqlOrderV2ItemEvent e)
    {
        if (!oe.getOrderV2EventId().getOrderId().equals(e.getOrderV2ItemEventId().getOrderV2OrderId()))
        { 
            throw DomainError.named("inconsistentEventIds", "Outer Id OrderId %1$s but inner id OrderV2OrderId %2$s", 
                oe.getOrderV2EventId().getOrderId(), e.getOrderV2ItemEventId().getOrderV2OrderId());
        }
    }

    protected OrderShipGroupEventDao getOrderShipGroupEventDao() {
        return (OrderShipGroupEventDao)ApplicationContext.current.get("orderShipGroupEventDao");
    }

    protected OrderShipGroupEventId newOrderShipGroupEventId(Integer shipGroupSeqId)
    {
        OrderShipGroupEventId eventId = new OrderShipGroupEventId(this.getOrderV2EventId().getOrderId(), 
            shipGroupSeqId, 
            this.getOrderV2EventId().getVersion());
        return eventId;
    }

    protected void throwOnInconsistentEventIds(OrderShipGroupEvent.SqlOrderShipGroupEvent e)
    {
        throwOnInconsistentEventIds(this, e);
    }

    public static void throwOnInconsistentEventIds(OrderV2Event.SqlOrderV2Event oe, OrderShipGroupEvent.SqlOrderShipGroupEvent e)
    {
        if (!oe.getOrderV2EventId().getOrderId().equals(e.getOrderShipGroupEventId().getOrderV2OrderId()))
        { 
            throw DomainError.named("inconsistentEventIds", "Outer Id OrderId %1$s but inner id OrderV2OrderId %2$s", 
                oe.getOrderV2EventId().getOrderId(), e.getOrderShipGroupEventId().getOrderV2OrderId());
        }
    }


    public abstract String getEventType();

    public static class OrderV2ClobEvent extends  AbstractOrderV2Event {

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
            return "OrderV2ClobEvent";
        }

    }

    public static class OrderV2Created extends OrderV2ClobEvent {

        @Override
        public String getEventType() {
            return "OrderV2Created";
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

    public static class OrderV2ItemRemoved extends OrderV2ClobEvent {

        @Override
        public String getEventType() {
            return "OrderV2ItemRemoved";
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

    public static class OrderV2ItemQuantityUpdated extends OrderV2ClobEvent {

        @Override
        public String getEventType() {
            return "OrderV2ItemQuantityUpdated";
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

    public static class OrderV2EstimatedShipDateUpdated extends OrderV2ClobEvent {

        @Override
        public String getEventType() {
            return "OrderV2EstimatedShipDateUpdated";
        }

        public Day getEstimatedShipDate() {
            Object val = getDynamicProperties().get("estimatedShipDate");
            if (val instanceof Day) {
                return (Day) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, Day.class);
        }

        public void setEstimatedShipDate(Day value) {
            getDynamicProperties().put("estimatedShipDate", value);
        }

    }

    public static class OrderShipGroupAdded extends OrderV2ClobEvent {

        @Override
        public String getEventType() {
            return "OrderShipGroupAdded";
        }

        public Integer getShipGroupSeqId() {
            Object val = getDynamicProperties().get("shipGroupSeqId");
            if (val instanceof Integer) {
                return (Integer) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, Integer.class);
        }

        public void setShipGroupSeqId(Integer value) {
            getDynamicProperties().put("shipGroupSeqId", value);
        }

        public String getShipmentMethod() {
            Object val = getDynamicProperties().get("shipmentMethod");
            if (val instanceof String) {
                return (String) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, String.class);
        }

        public void setShipmentMethod(String value) {
            getDynamicProperties().put("shipmentMethod", value);
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

