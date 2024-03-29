// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.aptosdemocontracts.domain.order;

import java.util.*;
import java.math.BigInteger;
import org.dddml.aptosdemocontracts.domain.*;
import java.util.Date;
import org.dddml.aptosdemocontracts.specialization.*;
import org.dddml.aptosdemocontracts.domain.AbstractEvent;

public abstract class AbstractOrderEvent extends AbstractEvent implements OrderEvent.SqlOrderEvent, AptosEvent.MutableAptosEvent, HasStatus.MutableHasStatus {
    private OrderEventId orderEventId = new OrderEventId();

    public OrderEventId getOrderEventId() {
        return this.orderEventId;
    }

    public void setOrderEventId(OrderEventId eventId) {
        this.orderEventId = eventId;
    }
    
    public String getOrderId() {
        return getOrderEventId().getOrderId();
    }

    public void setOrderId(String orderId) {
        getOrderEventId().setOrderId(orderId);
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
        OrderItemEventId eventId = new OrderItemEventId(this.getOrderEventId().getOrderId(), 
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
        if (!oe.getOrderEventId().getOrderId().equals(e.getOrderItemEventId().getOrderId()))
        { 
            throw DomainError.named("inconsistentEventIds", "Outer Id OrderId %1$s but inner id OrderId %2$s", 
                oe.getOrderEventId().getOrderId(), e.getOrderItemEventId().getOrderId());
        }
    }

    protected OrderShipGroupEventDao getOrderShipGroupEventDao() {
        return (OrderShipGroupEventDao)ApplicationContext.current.get("orderShipGroupEventDao");
    }

    protected OrderShipGroupEventId newOrderShipGroupEventId(Integer shipGroupSeqId)
    {
        OrderShipGroupEventId eventId = new OrderShipGroupEventId(this.getOrderEventId().getOrderId(), 
            shipGroupSeqId, 
            this.getOrderEventId().getVersion());
        return eventId;
    }

    protected void throwOnInconsistentEventIds(OrderShipGroupEvent.SqlOrderShipGroupEvent e)
    {
        throwOnInconsistentEventIds(this, e);
    }

    public static void throwOnInconsistentEventIds(OrderEvent.SqlOrderEvent oe, OrderShipGroupEvent.SqlOrderShipGroupEvent e)
    {
        if (!oe.getOrderEventId().getOrderId().equals(e.getOrderShipGroupEventId().getOrderId()))
        { 
            throw DomainError.named("inconsistentEventIds", "Outer Id OrderId %1$s but inner id OrderId %2$s", 
                oe.getOrderEventId().getOrderId(), e.getOrderShipGroupEventId().getOrderId());
        }
    }


    public abstract String getEventClass();

    public static class OrderClobEvent extends AbstractOrderEvent {

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
            return "OrderClobEvent";
        }

    }

    public static class OrderCreated extends OrderClobEvent implements OrderEvent.OrderCreated {

        @Override
        public String getEventClass() {
            return "OrderCreated";
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

    public static class OrderItemRemoved extends OrderClobEvent implements OrderEvent.OrderItemRemoved {

        @Override
        public String getEventClass() {
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

    public static class OrderItemQuantityUpdated extends OrderClobEvent implements OrderEvent.OrderItemQuantityUpdated {

        @Override
        public String getEventClass() {
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

    public static class OrderEstimatedShipDateUpdated extends OrderClobEvent implements OrderEvent.OrderEstimatedShipDateUpdated {

        @Override
        public String getEventClass() {
            return "OrderEstimatedShipDateUpdated";
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

    public static class OrderShipGroupAdded extends OrderClobEvent implements OrderEvent.OrderShipGroupAdded {

        @Override
        public String getEventClass() {
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

    public static class OrderShipGroupQuantityCanceled extends OrderClobEvent implements OrderEvent.OrderShipGroupQuantityCanceled {

        @Override
        public String getEventClass() {
            return "OrderShipGroupQuantityCanceled";
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

        public BigInteger getCancelQuantity() {
            Object val = getDynamicProperties().get("cancelQuantity");
            if (val instanceof BigInteger) {
                return (BigInteger) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, BigInteger.class);
        }

        public void setCancelQuantity(BigInteger value) {
            getDynamicProperties().put("cancelQuantity", value);
        }

    }

    public static class OrderShipGroupItemRemoved extends OrderClobEvent implements OrderEvent.OrderShipGroupItemRemoved {

        @Override
        public String getEventClass() {
            return "OrderShipGroupItemRemoved";
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

    public static class OrderShipGroupRemoved extends OrderClobEvent implements OrderEvent.OrderShipGroupRemoved {

        @Override
        public String getEventClass() {
            return "OrderShipGroupRemoved";
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

    }


}

