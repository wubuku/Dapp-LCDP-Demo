package org.dddml.suidemocontracts.domain.product;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.*;
import org.dddml.suidemocontracts.domain.AbstractEvent;

public abstract class AbstractProductEvent extends AbstractEvent implements ProductEvent.SqlProductEvent 
{
    private ProductState.MutableProductState state;

    public ProductState.MutableProductState getProductState() {
        return state;
    }

    public ProductEventId getProductEventId() {
        ProductEventId eventId = new ProductEventId(state.getProductId(), null);
        return eventId;
    }

    public void setProductEventId(ProductEventId eventId) {
        this.state.setProductId(eventId.getProductId());
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

    private SuiEventId nextCursor;

    public SuiEventId getNextCursor() {
        return this.nextCursor;
    }
    
    public void setNextCursor(SuiEventId nextCursor) {
        this.nextCursor = nextCursor;
    }

    public String getCreatedBy()
    {
        return this.state.getCreatedBy();
    }

    public void setCreatedBy(String createdBy)
    {
        this.state.setCreatedBy(createdBy);
    }

    public Date getCreatedAt()
    {
        return this.state.getCreatedAt();
    }

    public void setCreatedAt(Date createdAt)
    {
        this.state.setCreatedAt(createdAt);
    }


    public String getCommandId() {
        return this.state.getCommandId();
    }

    public void setCommandId(String commandId) {
        this.state.setCommandId(commandId);
    }

    protected AbstractProductEvent() {
        this(new AbstractProductState.SimpleProductState());
    }

    protected AbstractProductEvent(ProductEventId eventId) {
        this(new AbstractProductState.SimpleProductState());
        setProductEventId(eventId);
    }

    protected AbstractProductEvent(ProductState s) {
        if (s == null) { throw new IllegalArgumentException(); }
        this.state = (ProductState.MutableProductState)s;
    }


    public abstract String getEventType();

    public static class ProductClobEvent extends  AbstractProductEvent {

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
            return "ProductClobEvent";
        }

    }

    public static class ProductCreated extends ProductClobEvent {

        @Override
        public String getEventType() {
            return "ProductCreated";
        }

        public String getName() {
            Object val = getLobProperties().get("name");
            if (val instanceof String) {
                return (String) val;
            }
            return ApplicationContext.current.getTypeConverter().convertValue(val, String.class);
        }

        public void setName(String value) {
            getLobProperties().put("name", value);
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

    }


}

