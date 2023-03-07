package org.dddml.suidemocontracts.domain.product;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.*;
import org.dddml.suidemocontracts.domain.product.ProductEvent.*;

public abstract class AbstractProductState implements ProductState.SqlProductState {

    private String productId;

    public String getProductId() {
        return this.productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    private String id_;

    public String getId_() {
        return this.id_;
    }

    public void setId_(String id) {
        this.id_ = id;
    }

    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private BigInteger unitPrice;

    public BigInteger getUnitPrice() {
        return this.unitPrice;
    }

    public void setUnitPrice(BigInteger unitPrice) {
        this.unitPrice = unitPrice;
    }

    private BigInteger version;

    public BigInteger getVersion() {
        return this.version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    private Long offChainVersion;

    public Long getOffChainVersion() {
        return this.offChainVersion;
    }

    public void setOffChainVersion(Long offChainVersion) {
        this.offChainVersion = offChainVersion;
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

    private String updatedBy;

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    private Date updatedAt;

    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    private Boolean active;

    public Boolean getActive() {
        return this.active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public boolean isStateUnsaved() {
        return this.getOffChainVersion() == null;
    }

    private Boolean stateReadOnly;

    public Boolean getStateReadOnly() { return this.stateReadOnly; }

    public void setStateReadOnly(Boolean readOnly) { this.stateReadOnly = readOnly; }

    private boolean forReapplying;

    public boolean getForReapplying() {
        return forReapplying;
    }

    public void setForReapplying(boolean forReapplying) {
        this.forReapplying = forReapplying;
    }

    private String commandId;

    public String getCommandId() {
        return this.commandId;
    }

    public void setCommandId(String commandId) {
        this.commandId = commandId;
    }

    public AbstractProductState(List<Event> events) {
        initializeForReapplying();
        if (events != null && events.size() > 0) {
            this.setProductId(((ProductEvent.SqlProductEvent) events.get(0)).getProductEventId().getProductId());
            for (Event e : events) {
                mutate(e);
                this.setOffChainVersion((this.getOffChainVersion() == null ? ProductState.VERSION_NULL : this.getOffChainVersion()) + 1);
            }
        }
    }


    public AbstractProductState() {
        initializeProperties();
    }

    protected void initializeForReapplying() {
        this.forReapplying = true;

        initializeProperties();
    }
    
    protected void initializeProperties() {
    }

    @Override
    public int hashCode() {
        return getProductId().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        if (obj instanceof ProductState) {
            return Objects.equals(this.getProductId(), ((ProductState)obj).getProductId());
        }
        return false;
    }


    public void mutate(Event e) {
        setStateReadOnly(false);
        if (false) { 
            ;
        } else if (e instanceof AbstractProductEvent.ProductCreated) {
            when((AbstractProductEvent.ProductCreated)e);
        } else {
            throw new UnsupportedOperationException(String.format("Unsupported event type: %1$s", e.getClass().getName()));
        }
    }

    protected void merge(ProductState s) {
        if (s == this) {
            return;
        }
        this.setName(s.getName());
        this.setUnitPrice(s.getUnitPrice());
        this.setVersion(s.getVersion());
        this.setActive(s.getActive());
    }

    public void when(AbstractProductEvent.ProductCreated e) {
        throwOnWrongEvent(e);

        String name = e.getName();
        String Name = name;
        BigInteger unitPrice = e.getUnitPrice();
        BigInteger UnitPrice = unitPrice;
        Long suiTimestamp = e.getSuiTimestamp();
        Long SuiTimestamp = suiTimestamp;
        String suiTxDigest = e.getSuiTxDigest();
        String SuiTxDigest = suiTxDigest;
        Long suiEventSeq = e.getSuiEventSeq();
        Long SuiEventSeq = suiEventSeq;
        String suiPackageId = e.getSuiPackageId();
        String SuiPackageId = suiPackageId;
        String suiTransactionModule = e.getSuiTransactionModule();
        String SuiTransactionModule = suiTransactionModule;
        String suiSender = e.getSuiSender();
        String SuiSender = suiSender;
        String suiType = e.getSuiType();
        String SuiType = suiType;

        if (this.getCreatedBy() == null){
            this.setCreatedBy(e.getCreatedBy());
        }
        if (this.getCreatedAt() == null){
            this.setCreatedAt(e.getCreatedAt());
        }
        this.setUpdatedBy(e.getCreatedBy());
        this.setUpdatedAt(e.getCreatedAt());

        ProductState updatedProductState = (ProductState) ReflectUtils.invokeStaticMethod(
                    "org.dddml.suidemocontracts.domain.product.CreateLogic",
                    "mutate",
                    new Class[]{ProductState.class, String.class, BigInteger.class, Long.class, String.class, Long.class, String.class, String.class, String.class, String.class, MutationContext.class},
                    new Object[]{this, name, unitPrice, suiTimestamp, suiTxDigest, suiEventSeq, suiPackageId, suiTransactionModule, suiSender, suiType, MutationContext.forEvent(e, s -> {if (s == this) {return this;} else {throw new UnsupportedOperationException();}})}
            );

//package org.dddml.suidemocontracts.domain.product;
//
//public class CreateLogic {
//    public static ProductState mutate(ProductState productState, String name, BigInteger unitPrice, Long suiTimestamp, String suiTxDigest, Long suiEventSeq, String suiPackageId, String suiTransactionModule, String suiSender, String suiType, MutationContext<ProductState, ProductState.MutableProductState> mutationContext) {
//    }
//}

        if (this != updatedProductState) { merge(updatedProductState); } //else do nothing

    }

    public void save() {
    }

    protected void throwOnWrongEvent(ProductEvent event) {
        String stateEntityId = this.getProductId(); // Aggregate Id
        String eventEntityId = ((ProductEvent.SqlProductEvent)event).getProductEventId().getProductId(); // EntityBase.Aggregate.GetEventIdPropertyIdName();
        if (!stateEntityId.equals(eventEntityId)) {
            throw DomainError.named("mutateWrongEntity", "Entity Id %1$s in state but entity id %2$s in event", stateEntityId, eventEntityId);
        }


        Long stateVersion = this.getOffChainVersion();

    }


    public static class SimpleProductState extends AbstractProductState {

        public SimpleProductState() {
        }

        public SimpleProductState(List<Event> events) {
            super(events);
        }

        public static SimpleProductState newForReapplying() {
            SimpleProductState s = new SimpleProductState();
            s.initializeForReapplying();
            return s;
        }

    }



}

