package org.dddml.suidemocontracts.domain.orderv2;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.*;
import org.dddml.suidemocontracts.domain.orderv2.OrderV2ItemEvent.*;

public abstract class AbstractOrderV2ItemState implements OrderV2ItemState.SqlOrderV2ItemState {

    private OrderV2ItemId orderV2ItemId = new OrderV2ItemId();

    public OrderV2ItemId getOrderV2ItemId() {
        return this.orderV2ItemId;
    }

    public void setOrderV2ItemId(OrderV2ItemId orderV2ItemId) {
        this.orderV2ItemId = orderV2ItemId;
    }

    private transient OrderV2State orderV2State;

    public OrderV2State getOrderV2State() {
        return orderV2State;
    }

    public void setOrderV2State(OrderV2State s) {
        orderV2State = s;
    }
    
    public String getOrderV2OrderId() {
        return this.getOrderV2ItemId().getOrderV2OrderId();
    }
        
    public void setOrderV2OrderId(String orderV2OrderId) {
        this.getOrderV2ItemId().setOrderV2OrderId(orderV2OrderId);
    }

    public String getProductId() {
        return this.getOrderV2ItemId().getProductId();
    }
        
    public void setProductId(String productId) {
        this.getOrderV2ItemId().setProductId(productId);
    }

    private BigInteger quantity;

    public BigInteger getQuantity() {
        return this.quantity;
    }

    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    }

    private BigInteger itemAmount;

    public BigInteger getItemAmount() {
        return this.itemAmount;
    }

    public void setItemAmount(BigInteger itemAmount) {
        this.itemAmount = itemAmount;
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

    private Boolean deleted;

    public Boolean getDeleted() {
        return this.deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
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


    public AbstractOrderV2ItemState() {
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
        if (obj instanceof OrderV2ItemState) {
            return Objects.equals(this.getProductId(), ((OrderV2ItemState)obj).getProductId());
        }
        return false;
    }


    public void mutate(Event e) {
        setStateReadOnly(false);
        if (e instanceof OrderV2ItemStateCreated) {
            when((OrderV2ItemStateCreated) e);
        } else if (e instanceof OrderV2ItemStateMergePatched) {
            when((OrderV2ItemStateMergePatched) e);
        } else if (e instanceof OrderV2ItemStateRemoved) {
            when((OrderV2ItemStateRemoved) e);
        } else {
            throw new UnsupportedOperationException(String.format("Unsupported event type: %1$s", e.getClass().getName()));
        }
    }

    public void when(OrderV2ItemStateCreated e) {
        throwOnWrongEvent(e);

        this.setQuantity(e.getQuantity());
        this.setItemAmount(e.getItemAmount());
        this.setActive(e.getActive());

        this.setDeleted(false);

        this.setCreatedBy(e.getCreatedBy());
        this.setCreatedAt(e.getCreatedAt());

    }

    protected void merge(OrderV2ItemState s) {
        if (s == this) {
            return;
        }
        this.setQuantity(s.getQuantity());
        this.setItemAmount(s.getItemAmount());
        this.setActive(s.getActive());
    }

    public void when(OrderV2ItemStateMergePatched e) {
        throwOnWrongEvent(e);

        if (e.getQuantity() == null) {
            if (e.getIsPropertyQuantityRemoved() != null && e.getIsPropertyQuantityRemoved()) {
                this.setQuantity(null);
            }
        } else {
            this.setQuantity(e.getQuantity());
        }
        if (e.getItemAmount() == null) {
            if (e.getIsPropertyItemAmountRemoved() != null && e.getIsPropertyItemAmountRemoved()) {
                this.setItemAmount(null);
            }
        } else {
            this.setItemAmount(e.getItemAmount());
        }
        if (e.getActive() == null) {
            if (e.getIsPropertyActiveRemoved() != null && e.getIsPropertyActiveRemoved()) {
                this.setActive(null);
            }
        } else {
            this.setActive(e.getActive());
        }

        this.setUpdatedBy(e.getCreatedBy());
        this.setUpdatedAt(e.getCreatedAt());

    }

    public void when(OrderV2ItemStateRemoved e) {
        throwOnWrongEvent(e);

        this.setDeleted(true);
        this.setUpdatedBy(e.getCreatedBy());
        this.setUpdatedAt(e.getCreatedAt());

    }

    public void save() {
    }

    protected void throwOnWrongEvent(OrderV2ItemEvent event) {
        String stateEntityIdOrderV2OrderId = this.getOrderV2ItemId().getOrderV2OrderId();
        String eventEntityIdOrderV2OrderId = ((OrderV2ItemEvent.SqlOrderV2ItemEvent)event).getOrderV2ItemEventId().getOrderV2OrderId();
        if (!stateEntityIdOrderV2OrderId.equals(eventEntityIdOrderV2OrderId)) {
            throw DomainError.named("mutateWrongEntity", "Entity Id OrderV2OrderId %1$s in state but entity id OrderV2OrderId %2$s in event", stateEntityIdOrderV2OrderId, eventEntityIdOrderV2OrderId);
        }

        String stateEntityIdProductId = this.getOrderV2ItemId().getProductId();
        String eventEntityIdProductId = ((OrderV2ItemEvent.SqlOrderV2ItemEvent)event).getOrderV2ItemEventId().getProductId();
        if (!stateEntityIdProductId.equals(eventEntityIdProductId)) {
            throw DomainError.named("mutateWrongEntity", "Entity Id ProductId %1$s in state but entity id ProductId %2$s in event", stateEntityIdProductId, eventEntityIdProductId);
        }


        if (getForReapplying()) { return; }

        OrderV2ItemStateEvent stateEvent = event instanceof OrderV2ItemStateEvent ? (OrderV2ItemStateEvent)event : null;
        if (stateEvent == null) { return; }

        Long stateVersion = this.getVersion();
        Long stateEventStateVersion = stateEvent.getVersion();
        //if (stateEventStateVersion == null) {
        stateEventStateVersion = stateVersion == null ? OrderV2ItemState.VERSION_NULL : stateVersion;
        stateEvent.setVersion(stateEventStateVersion);
        //}
        //if (!(stateVersion == null && stateEventStateVersion.equals(OrderV2ItemState.VERSION_NULL)) && !stateEventStateVersion.equals(stateVersion))
        //{
        //    throw DomainError.named("concurrencyConflict", "Conflict between stateVersion (%1$s) and stateEventStateVersion (%2$s)", stateVersion, stateEventStateVersion);
        //}

    }


    public static class SimpleOrderV2ItemState extends AbstractOrderV2ItemState {

        public SimpleOrderV2ItemState() {
        }

        public static SimpleOrderV2ItemState newForReapplying() {
            SimpleOrderV2ItemState s = new SimpleOrderV2ItemState();
            s.initializeForReapplying();
            return s;
        }

    }



}

