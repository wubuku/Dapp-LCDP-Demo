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
        ProductEventId eventId = new ProductEventId(state.getProductId(), ProductState.VERSION_NULL);
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

    public Long getVersion() {
        return getProductEventId().getVersion();
    }
    
    public void setVersion(Long version) {
        getProductEventId().setVersion(version);
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


    public static abstract class AbstractProductStateEvent extends AbstractProductEvent implements ProductEvent.ProductStateEvent {
        public String getName()
        {
            return this.getProductState().getName();
        }

        public void setName(String name)
        {
            this.getProductState().setName(name);
        }

        public BigInteger getUnitPrice()
        {
            return this.getProductState().getUnitPrice();
        }

        public void setUnitPrice(BigInteger unitPrice)
        {
            this.getProductState().setUnitPrice(unitPrice);
        }

        public Boolean getActive()
        {
            return this.getProductState().getActive();
        }

        public void setActive(Boolean active)
        {
            this.getProductState().setActive(active);
        }

        protected AbstractProductStateEvent(ProductEventId eventId) {
            super(eventId);
        }

        public AbstractProductStateEvent(ProductState s) {
            super(s);
        }
    }

    public static abstract class AbstractProductStateCreated extends AbstractProductStateEvent implements ProductEvent.ProductStateCreated
    {
        public AbstractProductStateCreated() {
            this(new ProductEventId());
        }

        public AbstractProductStateCreated(ProductEventId eventId) {
            super(eventId);
        }

        public AbstractProductStateCreated(ProductState s) {
            super(s);
        }

        public String getEventType() {
            return StateEventType.CREATED;
        }

    }



    public static class SimpleProductStateCreated extends AbstractProductStateCreated
    {
        public SimpleProductStateCreated() {
        }

        public SimpleProductStateCreated(ProductEventId eventId) {
            super(eventId);
        }

        public SimpleProductStateCreated(ProductState s) {
            super(s);
        }
    }

}

