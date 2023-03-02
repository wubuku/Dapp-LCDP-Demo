package org.dddml.suidemocontracts.domain.orderv2;

import java.util.*;
import java.math.BigInteger;
import org.dddml.suidemocontracts.domain.*;
import java.util.Date;
import org.dddml.suidemocontracts.specialization.*;

public abstract class AbstractOrderV2Aggregate extends AbstractAggregate implements OrderV2Aggregate
{
    private OrderV2State.MutableOrderV2State state;

    private List<Event> changes = new ArrayList<Event>();

    public AbstractOrderV2Aggregate(OrderV2State state) {
        this.state = (OrderV2State.MutableOrderV2State)state;
    }

    public OrderV2State getState() {
        return this.state;
    }

    public List<Event> getChanges() {
        return this.changes;
    }

    public void throwOnInvalidStateTransition(Command c) {
        OrderV2Command.throwOnInvalidStateTransition(this.state, c);
    }

    protected void apply(Event e) {
        onApplying(e);
        state.mutate(e);
        changes.add(e);
    }


    ////////////////////////

    public static class SimpleOrderV2Aggregate extends AbstractOrderV2Aggregate {
        public SimpleOrderV2Aggregate(OrderV2State state) {
            super(state);
        }

        @Override
        public void create(String product, BigInteger quantity, Long version, String commandId, String requesterId, OrderV2Commands.Create c) {
            try {
                verifyCreate(product, quantity, c);
            } catch (Exception ex) {
                throw new DomainError("VerificationFailed", ex);
            }

            Event e = newOrderV2Created(product, quantity, version, commandId, requesterId);
            apply(e);
        }

        @Override
        public void removeItem(String productId, Long version, String commandId, String requesterId, OrderV2Commands.RemoveItem c) {
            try {
                verifyRemoveItem(productId, c);
            } catch (Exception ex) {
                throw new DomainError("VerificationFailed", ex);
            }

            Event e = newOrderV2ItemRemoved(productId, version, commandId, requesterId);
            apply(e);
        }

        @Override
        public void updateItemQuantity(String productId, BigInteger quantity, Long version, String commandId, String requesterId, OrderV2Commands.UpdateItemQuantity c) {
            try {
                verifyUpdateItemQuantity(productId, quantity, c);
            } catch (Exception ex) {
                throw new DomainError("VerificationFailed", ex);
            }

            Event e = newOrderV2ItemQuantityUpdated(productId, quantity, version, commandId, requesterId);
            apply(e);
        }

        @Override
        public void updateEstimatedShipDate(Day estimatedShipDate, Long version, String commandId, String requesterId, OrderV2Commands.UpdateEstimatedShipDate c) {
            try {
                verifyUpdateEstimatedShipDate(estimatedShipDate, c);
            } catch (Exception ex) {
                throw new DomainError("VerificationFailed", ex);
            }

            Event e = newOrderV2EstimatedShipDateUpdated(estimatedShipDate, version, commandId, requesterId);
            apply(e);
        }

        protected void verifyCreate(String product, BigInteger quantity, OrderV2Commands.Create c) {
            String Product = product;
            BigInteger Quantity = quantity;

            ReflectUtils.invokeStaticMethod(
                    "org.dddml.suidemocontracts.domain.orderv2.CreateLogic",
                    "verify",
                    new Class[]{OrderV2State.class, String.class, BigInteger.class, VerificationContext.class},
                    new Object[]{getState(), product, quantity, VerificationContext.forCommand(c)}
            );

//package org.dddml.suidemocontracts.domain.orderv2;
//
//public class CreateLogic {
//    public static void verify(OrderV2State orderV2State, String product, BigInteger quantity, VerificationContext verificationContext) {
//    }
//}

        }
           

        protected void verifyRemoveItem(String productId, OrderV2Commands.RemoveItem c) {
            String ProductId = productId;

            ReflectUtils.invokeStaticMethod(
                    "org.dddml.suidemocontracts.domain.orderv2.RemoveItemLogic",
                    "verify",
                    new Class[]{OrderV2State.class, String.class, VerificationContext.class},
                    new Object[]{getState(), productId, VerificationContext.forCommand(c)}
            );

//package org.dddml.suidemocontracts.domain.orderv2;
//
//public class RemoveItemLogic {
//    public static void verify(OrderV2State orderV2State, String productId, VerificationContext verificationContext) {
//    }
//}

        }
           

        protected void verifyUpdateItemQuantity(String productId, BigInteger quantity, OrderV2Commands.UpdateItemQuantity c) {
            String ProductId = productId;
            BigInteger Quantity = quantity;

            ReflectUtils.invokeStaticMethod(
                    "org.dddml.suidemocontracts.domain.orderv2.UpdateItemQuantityLogic",
                    "verify",
                    new Class[]{OrderV2State.class, String.class, BigInteger.class, VerificationContext.class},
                    new Object[]{getState(), productId, quantity, VerificationContext.forCommand(c)}
            );

//package org.dddml.suidemocontracts.domain.orderv2;
//
//public class UpdateItemQuantityLogic {
//    public static void verify(OrderV2State orderV2State, String productId, BigInteger quantity, VerificationContext verificationContext) {
//    }
//}

        }
           

        protected void verifyUpdateEstimatedShipDate(Day estimatedShipDate, OrderV2Commands.UpdateEstimatedShipDate c) {
            Day EstimatedShipDate = estimatedShipDate;

            ReflectUtils.invokeStaticMethod(
                    "org.dddml.suidemocontracts.domain.orderv2.UpdateEstimatedShipDateLogic",
                    "verify",
                    new Class[]{OrderV2State.class, Day.class, VerificationContext.class},
                    new Object[]{getState(), estimatedShipDate, VerificationContext.forCommand(c)}
            );

//package org.dddml.suidemocontracts.domain.orderv2;
//
//public class UpdateEstimatedShipDateLogic {
//    public static void verify(OrderV2State orderV2State, Day estimatedShipDate, VerificationContext verificationContext) {
//    }
//}

        }
           

        protected AbstractOrderV2Event.OrderV2Created newOrderV2Created(String product, BigInteger quantity, Long version, String commandId, String requesterId) {
            OrderV2EventId eventId = new OrderV2EventId(getState().getOrderId(), version);
            AbstractOrderV2Event.OrderV2Created e = new AbstractOrderV2Event.OrderV2Created();

            e.setProduct(product);
            e.setQuantity(quantity);
            e.setUnitPrice(null); // todo Need to update 'verify' method to return event properties.
            e.setTotalAmount(null); // todo Need to update 'verify' method to return event properties.
            e.setOwner(null); // todo Need to update 'verify' method to return event properties.

            e.setCommandId(commandId);
            e.setCreatedBy(requesterId);
            e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));

            e.setOrderV2EventId(eventId);
            return e;
        }

        protected AbstractOrderV2Event.OrderV2ItemRemoved newOrderV2ItemRemoved(String productId, Long version, String commandId, String requesterId) {
            OrderV2EventId eventId = new OrderV2EventId(getState().getOrderId(), version);
            AbstractOrderV2Event.OrderV2ItemRemoved e = new AbstractOrderV2Event.OrderV2ItemRemoved();

            e.setProductId(productId);

            e.setCommandId(commandId);
            e.setCreatedBy(requesterId);
            e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));

            e.setOrderV2EventId(eventId);
            return e;
        }

        protected AbstractOrderV2Event.OrderV2ItemQuantityUpdated newOrderV2ItemQuantityUpdated(String productId, BigInteger quantity, Long version, String commandId, String requesterId) {
            OrderV2EventId eventId = new OrderV2EventId(getState().getOrderId(), version);
            AbstractOrderV2Event.OrderV2ItemQuantityUpdated e = new AbstractOrderV2Event.OrderV2ItemQuantityUpdated();

            e.setProductId(productId);
            e.setQuantity(quantity);

            e.setCommandId(commandId);
            e.setCreatedBy(requesterId);
            e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));

            e.setOrderV2EventId(eventId);
            return e;
        }

        protected AbstractOrderV2Event.OrderV2EstimatedShipDateUpdated newOrderV2EstimatedShipDateUpdated(Day estimatedShipDate, Long version, String commandId, String requesterId) {
            OrderV2EventId eventId = new OrderV2EventId(getState().getOrderId(), version);
            AbstractOrderV2Event.OrderV2EstimatedShipDateUpdated e = new AbstractOrderV2Event.OrderV2EstimatedShipDateUpdated();

            e.setEstimatedShipDate(estimatedShipDate);

            e.setCommandId(commandId);
            e.setCreatedBy(requesterId);
            e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));

            e.setOrderV2EventId(eventId);
            return e;
        }

    }

}

