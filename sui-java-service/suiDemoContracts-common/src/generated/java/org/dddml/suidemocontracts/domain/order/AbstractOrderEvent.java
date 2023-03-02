package org.dddml.suidemocontracts.domain.order;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.*;
import org.dddml.suidemocontracts.domain.AbstractEvent;

public abstract class AbstractOrderEvent extends AbstractEvent implements OrderEvent.SqlOrderEvent 
{
    private OrderEventId orderEventId;

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

    public Long getVersion() {
        return getOrderEventId().getVersion();
    }
    
    public void setVersion(Long version) {
        getOrderEventId().setVersion(version);
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
            return "OrderClobEvent";
        }

    }

    public static class OrderCreated extends OrderClobEvent {

        @Override
        public String getEventType() {
            return "OrderCreated";
        }

        public String getProduct() {
            Object val = getLobProperties().get("product");
            if (val instanceof String) {
                return (String) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, String.class);
        }

        public void setProduct(String value) {
            getLobProperties().put("product", value);
        }

        public BigInteger getQuantity() {
            Object val = getLobProperties().get("quantity");
            if (val instanceof BigInteger) {
                return (BigInteger) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, BigInteger.class);
        }

        public void setQuantity(BigInteger value) {
            getLobProperties().put("quantity", value);
        }

        public BigInteger getUnitPrice() {
            Object val = getLobProperties().get("unitPrice");
            if (val instanceof BigInteger) {
                return (BigInteger) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, BigInteger.class);
        }

        public void setUnitPrice(BigInteger value) {
            getLobProperties().put("unitPrice", value);
        }

        public BigInteger getTotalAmount() {
            Object val = getLobProperties().get("totalAmount");
            if (val instanceof BigInteger) {
                return (BigInteger) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, BigInteger.class);
        }

        public void setTotalAmount(BigInteger value) {
            getLobProperties().put("totalAmount", value);
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

    public static class OrderItemRemoved extends OrderClobEvent {

        @Override
        public String getEventType() {
            return "OrderItemRemoved";
        }

        public String getProductId() {
            Object val = getLobProperties().get("productId");
            if (val instanceof String) {
                return (String) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, String.class);
        }

        public void setProductId(String value) {
            getLobProperties().put("productId", value);
        }

    }

    public static class OrderItemQuantityUpdated extends OrderClobEvent {

        @Override
        public String getEventType() {
            return "OrderItemQuantityUpdated";
        }

        public String getProductId() {
            Object val = getLobProperties().get("productId");
            if (val instanceof String) {
                return (String) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, String.class);
        }

        public void setProductId(String value) {
            getLobProperties().put("productId", value);
        }

        public BigInteger getQuantity() {
            Object val = getLobProperties().get("quantity");
            if (val instanceof BigInteger) {
                return (BigInteger) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, BigInteger.class);
        }

        public void setQuantity(BigInteger value) {
            getLobProperties().put("quantity", value);
        }

    }


}

