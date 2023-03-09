package org.dddml.suidemocontracts.domain.order;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.*;

public abstract class AbstractOrderAggregate extends AbstractAggregate implements OrderAggregate
{
    private OrderState.MutableOrderState state;

    private List<Event> changes = new ArrayList<Event>();

    public AbstractOrderAggregate(OrderState state) {
        this.state = (OrderState.MutableOrderState)state;
    }

    public OrderState getState() {
        return this.state;
    }

    public List<Event> getChanges() {
        return this.changes;
    }

    public void throwOnInvalidStateTransition(Command c) {
        OrderCommand.throwOnInvalidStateTransition(this.state, c);
    }

    protected void apply(Event e) {
        onApplying(e);
        state.mutate(e);
        changes.add(e);
    }


    ////////////////////////

    public static class SimpleOrderAggregate extends AbstractOrderAggregate {
        public SimpleOrderAggregate(OrderState state) {
            super(state);
        }

        @Override
        public void create(String product, BigInteger quantity, Long offChainVersion, String commandId, String requesterId, OrderCommands.Create c) {
            try {
                verifyCreate(product, quantity, c);
            } catch (Exception ex) {
                throw new DomainError("VerificationFailed", ex);
            }

            Event e = newOrderCreated(product, quantity, offChainVersion, commandId, requesterId);
            apply(e);
        }

        @Override
        public void removeItem(String productId, Long offChainVersion, String commandId, String requesterId, OrderCommands.RemoveItem c) {
            try {
                verifyRemoveItem(productId, c);
            } catch (Exception ex) {
                throw new DomainError("VerificationFailed", ex);
            }

            Event e = newOrderItemRemoved(productId, offChainVersion, commandId, requesterId);
            apply(e);
        }

        @Override
        public void updateItemQuantity(String productId, BigInteger quantity, Long offChainVersion, String commandId, String requesterId, OrderCommands.UpdateItemQuantity c) {
            try {
                verifyUpdateItemQuantity(productId, quantity, c);
            } catch (Exception ex) {
                throw new DomainError("VerificationFailed", ex);
            }

            Event e = newOrderItemQuantityUpdated(productId, quantity, offChainVersion, commandId, requesterId);
            apply(e);
        }

        protected void verifyCreate(String product, BigInteger quantity, OrderCommands.Create c) {
            String Product = product;
            BigInteger Quantity = quantity;

            ReflectUtils.invokeStaticMethod(
                    "org.dddml.suidemocontracts.domain.order.CreateLogic",
                    "verify",
                    new Class[]{OrderState.class, String.class, BigInteger.class, VerificationContext.class},
                    new Object[]{getState(), product, quantity, VerificationContext.forCommand(c)}
            );

//package org.dddml.suidemocontracts.domain.order;
//
//public class CreateLogic {
//    public static void verify(OrderState orderState, String product, BigInteger quantity, VerificationContext verificationContext) {
//    }
//}

        }
           

        protected void verifyRemoveItem(String productId, OrderCommands.RemoveItem c) {
            String ProductId = productId;

            ReflectUtils.invokeStaticMethod(
                    "org.dddml.suidemocontracts.domain.order.RemoveItemLogic",
                    "verify",
                    new Class[]{OrderState.class, String.class, VerificationContext.class},
                    new Object[]{getState(), productId, VerificationContext.forCommand(c)}
            );

//package org.dddml.suidemocontracts.domain.order;
//
//public class RemoveItemLogic {
//    public static void verify(OrderState orderState, String productId, VerificationContext verificationContext) {
//    }
//}

        }
           

        protected void verifyUpdateItemQuantity(String productId, BigInteger quantity, OrderCommands.UpdateItemQuantity c) {
            String ProductId = productId;
            BigInteger Quantity = quantity;

            ReflectUtils.invokeStaticMethod(
                    "org.dddml.suidemocontracts.domain.order.UpdateItemQuantityLogic",
                    "verify",
                    new Class[]{OrderState.class, String.class, BigInteger.class, VerificationContext.class},
                    new Object[]{getState(), productId, quantity, VerificationContext.forCommand(c)}
            );

//package org.dddml.suidemocontracts.domain.order;
//
//public class UpdateItemQuantityLogic {
//    public static void verify(OrderState orderState, String productId, BigInteger quantity, VerificationContext verificationContext) {
//    }
//}

        }
           

        protected AbstractOrderEvent.OrderCreated newOrderCreated(String product, BigInteger quantity, Long offChainVersion, String commandId, String requesterId) {
            OrderEventId eventId = new OrderEventId(getState().getId(), null);
            AbstractOrderEvent.OrderCreated e = new AbstractOrderEvent.OrderCreated();

            e.setProduct(product);
            e.setQuantity(quantity);
            e.setUnitPrice(null); // todo Need to update 'verify' method to return event properties.
            e.setTotalAmount(null); // todo Need to update 'verify' method to return event properties.
            e.setOwner(null); // todo Need to update 'verify' method to return event properties.
            e.setSuiTimestamp(null); // todo Need to update 'verify' method to return event properties.
            e.setSuiTxDigest(null); // todo Need to update 'verify' method to return event properties.
            e.setSuiEventSeq(null); // todo Need to update 'verify' method to return event properties.
            e.setSuiPackageId(null); // todo Need to update 'verify' method to return event properties.
            e.setSuiTransactionModule(null); // todo Need to update 'verify' method to return event properties.
            e.setSuiSender(null); // todo Need to update 'verify' method to return event properties.
            e.setSuiType(null); // todo Need to update 'verify' method to return event properties.
            e.setStatus(null); // todo Need to update 'verify' method to return event properties.

            e.setCommandId(commandId);
            e.setCreatedBy(requesterId);
            e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));

            e.setOrderEventId(eventId);
            return e;
        }

        protected AbstractOrderEvent.OrderItemRemoved newOrderItemRemoved(String productId, Long offChainVersion, String commandId, String requesterId) {
            OrderEventId eventId = new OrderEventId(getState().getId(), null);
            AbstractOrderEvent.OrderItemRemoved e = new AbstractOrderEvent.OrderItemRemoved();

            e.setProductId(productId);
            e.setSuiTimestamp(null); // todo Need to update 'verify' method to return event properties.
            e.setSuiTxDigest(null); // todo Need to update 'verify' method to return event properties.
            e.setSuiEventSeq(null); // todo Need to update 'verify' method to return event properties.
            e.setSuiPackageId(null); // todo Need to update 'verify' method to return event properties.
            e.setSuiTransactionModule(null); // todo Need to update 'verify' method to return event properties.
            e.setSuiSender(null); // todo Need to update 'verify' method to return event properties.
            e.setSuiType(null); // todo Need to update 'verify' method to return event properties.
            e.setStatus(null); // todo Need to update 'verify' method to return event properties.

            e.setCommandId(commandId);
            e.setCreatedBy(requesterId);
            e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));

            e.setOrderEventId(eventId);
            return e;
        }

        protected AbstractOrderEvent.OrderItemQuantityUpdated newOrderItemQuantityUpdated(String productId, BigInteger quantity, Long offChainVersion, String commandId, String requesterId) {
            OrderEventId eventId = new OrderEventId(getState().getId(), null);
            AbstractOrderEvent.OrderItemQuantityUpdated e = new AbstractOrderEvent.OrderItemQuantityUpdated();

            e.setProductId(productId);
            e.setQuantity(quantity);
            e.setSuiTimestamp(null); // todo Need to update 'verify' method to return event properties.
            e.setSuiTxDigest(null); // todo Need to update 'verify' method to return event properties.
            e.setSuiEventSeq(null); // todo Need to update 'verify' method to return event properties.
            e.setSuiPackageId(null); // todo Need to update 'verify' method to return event properties.
            e.setSuiTransactionModule(null); // todo Need to update 'verify' method to return event properties.
            e.setSuiSender(null); // todo Need to update 'verify' method to return event properties.
            e.setSuiType(null); // todo Need to update 'verify' method to return event properties.
            e.setStatus(null); // todo Need to update 'verify' method to return event properties.

            e.setCommandId(commandId);
            e.setCreatedBy(requesterId);
            e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));

            e.setOrderEventId(eventId);
            return e;
        }

    }

}

