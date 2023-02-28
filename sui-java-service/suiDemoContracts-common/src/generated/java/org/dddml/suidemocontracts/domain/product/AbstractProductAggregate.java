package org.dddml.suidemocontracts.domain.product;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.*;

public abstract class AbstractProductAggregate extends AbstractAggregate implements ProductAggregate
{
    private ProductState.MutableProductState state;

    private List<Event> changes = new ArrayList<Event>();

    public AbstractProductAggregate(ProductState state) {
        this.state = (ProductState.MutableProductState)state;
    }

    public ProductState getState() {
        return this.state;
    }

    public List<Event> getChanges() {
        return this.changes;
    }

    public void create(ProductCommand.CreateProduct c) {
        if (c.getVersion() == null) { c.setVersion(ProductState.VERSION_NULL); }
        ProductEvent e = map(c);
        apply(e);
    }

    public void throwOnInvalidStateTransition(Command c) {
        ProductCommand.throwOnInvalidStateTransition(this.state, c);
    }

    protected void apply(Event e) {
        onApplying(e);
        state.mutate(e);
        changes.add(e);
    }

    protected ProductEvent map(ProductCommand.CreateProduct c) {
        ProductEventId stateEventId = new ProductEventId(c.getProductId(), c.getVersion());
        ProductEvent.ProductStateCreated e = newProductStateCreated(stateEventId);
        e.setName(c.getName());
        e.setUnitPrice(c.getUnitPrice());
        e.setActive(c.getActive());
        ((AbstractProductEvent)e).setCommandId(c.getCommandId());
        e.setCreatedBy(c.getRequesterId());
        e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));
        return e;
    }


    ////////////////////////

    protected ProductEvent.ProductStateCreated newProductStateCreated(Long version, String commandId, String requesterId) {
        ProductEventId stateEventId = new ProductEventId(this.state.getProductId(), version);
        ProductEvent.ProductStateCreated e = newProductStateCreated(stateEventId);
        ((AbstractProductEvent)e).setCommandId(commandId);
        e.setCreatedBy(requesterId);
        e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));
        return e;
    }

    protected ProductEvent.ProductStateCreated newProductStateCreated(ProductEventId stateEventId) {
        return new AbstractProductEvent.SimpleProductStateCreated(stateEventId);
    }


    public static class SimpleProductAggregate extends AbstractProductAggregate {
        public SimpleProductAggregate(ProductState state) {
            super(state);
        }

    }

}

