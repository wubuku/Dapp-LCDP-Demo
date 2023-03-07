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

    public void throwOnInvalidStateTransition(Command c) {
        ProductCommand.throwOnInvalidStateTransition(this.state, c);
    }

    protected void apply(Event e) {
        onApplying(e);
        state.mutate(e);
        changes.add(e);
    }


    ////////////////////////

    public static class SimpleProductAggregate extends AbstractProductAggregate {
        public SimpleProductAggregate(ProductState state) {
            super(state);
        }

        @Override
        public void create(String name, BigInteger unitPrice, Long offChainVersion, String commandId, String requesterId, ProductCommands.Create c) {
            try {
                verifyCreate(name, unitPrice, c);
            } catch (Exception ex) {
                throw new DomainError("VerificationFailed", ex);
            }

            Event e = newProductCreated(name, unitPrice, offChainVersion, commandId, requesterId);
            apply(e);
        }

        protected void verifyCreate(String name, BigInteger unitPrice, ProductCommands.Create c) {
            String Name = name;
            BigInteger UnitPrice = unitPrice;

            ReflectUtils.invokeStaticMethod(
                    "org.dddml.suidemocontracts.domain.product.CreateLogic",
                    "verify",
                    new Class[]{ProductState.class, String.class, BigInteger.class, VerificationContext.class},
                    new Object[]{getState(), name, unitPrice, VerificationContext.forCommand(c)}
            );

//package org.dddml.suidemocontracts.domain.product;
//
//public class CreateLogic {
//    public static void verify(ProductState productState, String name, BigInteger unitPrice, VerificationContext verificationContext) {
//    }
//}

        }
           

        protected AbstractProductEvent.ProductCreated newProductCreated(String name, BigInteger unitPrice, Long offChainVersion, String commandId, String requesterId) {
            ProductEventId eventId = new ProductEventId(getState().getProductId(), null);
            AbstractProductEvent.ProductCreated e = new AbstractProductEvent.ProductCreated();

            e.setName(name);
            e.setUnitPrice(unitPrice);
            e.setSuiTimestamp(null); // todo Need to update 'verify' method to return event properties.
            e.setSuiTxDigest(null); // todo Need to update 'verify' method to return event properties.
            e.setSuiEventSeq(null); // todo Need to update 'verify' method to return event properties.
            e.setSuiPackageId(null); // todo Need to update 'verify' method to return event properties.
            e.setSuiTransactionModule(null); // todo Need to update 'verify' method to return event properties.
            e.setSuiSender(null); // todo Need to update 'verify' method to return event properties.
            e.setSuiType(null); // todo Need to update 'verify' method to return event properties.
            e.setNextCursor(null); // todo Need to update 'verify' method to return event properties.

            e.setCommandId(commandId);
            e.setCreatedBy(requesterId);
            e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));

            e.setProductEventId(eventId);
            return e;
        }

    }

}

