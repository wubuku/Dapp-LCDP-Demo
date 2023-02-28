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

    private Long version;

    public Long getVersion() {
        return this.version;
    }

    public void setVersion(Long version) {
        this.version = version;
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
        return this.getVersion() == null;
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
                this.setVersion((this.getVersion() == null ? ProductState.VERSION_NULL : this.getVersion()) + 1);
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
        if (e instanceof ProductStateCreated) {
            when((ProductStateCreated) e);
        } else {
            throw new UnsupportedOperationException(String.format("Unsupported event type: %1$s", e.getClass().getName()));
        }
    }

    public void when(ProductStateCreated e) {
        throwOnWrongEvent(e);

        this.setName(e.getName());
        this.setUnitPrice(e.getUnitPrice());
        this.setActive(e.getActive());

        this.setCreatedBy(e.getCreatedBy());
        this.setCreatedAt(e.getCreatedAt());

    }

    protected void merge(ProductState s) {
        if (s == this) {
            return;
        }
        this.setName(s.getName());
        this.setUnitPrice(s.getUnitPrice());
        this.setActive(s.getActive());
    }

    public void save() {
    }

    protected void throwOnWrongEvent(ProductEvent event) {
        String stateEntityId = this.getProductId(); // Aggregate Id
        String eventEntityId = ((ProductEvent.SqlProductEvent)event).getProductEventId().getProductId(); // EntityBase.Aggregate.GetEventIdPropertyIdName();
        if (!stateEntityId.equals(eventEntityId)) {
            throw DomainError.named("mutateWrongEntity", "Entity Id %1$s in state but entity id %2$s in event", stateEntityId, eventEntityId);
        }


        Long stateVersion = this.getVersion();
        Long eventVersion = ((ProductEvent.SqlProductEvent)event).getProductEventId().getVersion();// Aggregate Version
        if (eventVersion == null) {
            throw new NullPointerException("event.getProductEventId().getVersion() == null");
        }
        if (!(stateVersion == null && eventVersion.equals(ProductState.VERSION_NULL)) && !eventVersion.equals(stateVersion)) {
            throw DomainError.named("concurrencyConflict", "Conflict between state version (%1$s) and event version (%2$s)", stateVersion, eventVersion);
        }

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

