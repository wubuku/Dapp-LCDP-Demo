// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.aptosdemocontracts.domain.order;

import java.util.*;
import java.math.BigInteger;
import org.dddml.aptosdemocontracts.domain.*;
import java.util.Date;
import org.dddml.aptosdemocontracts.specialization.*;

public abstract class AbstractOrderAggregate extends AbstractAggregate implements OrderAggregate {
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
        public void create(String productId, BigInteger quantity, Long offChainVersion, String commandId, String requesterId, OrderCommands.Create c) {
            java.util.function.Supplier<OrderEvent.OrderCreated> eventFactory = () -> newOrderCreated(productId, quantity, offChainVersion, commandId, requesterId);
            OrderEvent.OrderCreated e;
            try {
                e = verifyCreate(eventFactory, productId, quantity, c);
            } catch (Exception ex) {
                throw new DomainError("VerificationFailed", ex);
            }

            apply(e);
        }

        @Override
        public void removeItem(String productId, Long offChainVersion, String commandId, String requesterId, OrderCommands.RemoveItem c) {
            java.util.function.Supplier<OrderEvent.OrderItemRemoved> eventFactory = () -> newOrderItemRemoved(productId, offChainVersion, commandId, requesterId);
            OrderEvent.OrderItemRemoved e;
            try {
                e = verifyRemoveItem(eventFactory, productId, c);
            } catch (Exception ex) {
                throw new DomainError("VerificationFailed", ex);
            }

            apply(e);
        }

        @Override
        public void updateItemQuantity(String productId, BigInteger quantity, Long offChainVersion, String commandId, String requesterId, OrderCommands.UpdateItemQuantity c) {
            java.util.function.Supplier<OrderEvent.OrderItemQuantityUpdated> eventFactory = () -> newOrderItemQuantityUpdated(productId, quantity, offChainVersion, commandId, requesterId);
            OrderEvent.OrderItemQuantityUpdated e;
            try {
                e = verifyUpdateItemQuantity(eventFactory, productId, quantity, c);
            } catch (Exception ex) {
                throw new DomainError("VerificationFailed", ex);
            }

            apply(e);
        }

        @Override
        public void updateEstimatedShipDate(Day estimatedShipDate, Long offChainVersion, String commandId, String requesterId, OrderCommands.UpdateEstimatedShipDate c) {
            java.util.function.Supplier<OrderEvent.OrderEstimatedShipDateUpdated> eventFactory = () -> newOrderEstimatedShipDateUpdated(estimatedShipDate, offChainVersion, commandId, requesterId);
            OrderEvent.OrderEstimatedShipDateUpdated e;
            try {
                e = verifyUpdateEstimatedShipDate(eventFactory, estimatedShipDate, c);
            } catch (Exception ex) {
                throw new DomainError("VerificationFailed", ex);
            }

            apply(e);
        }

        @Override
        public void addOrderShipGroup(Integer shipGroupSeqId, String shipmentMethod, String productId, BigInteger quantity, Long offChainVersion, String commandId, String requesterId, OrderCommands.AddOrderShipGroup c) {
            java.util.function.Supplier<OrderEvent.OrderShipGroupAdded> eventFactory = () -> newOrderShipGroupAdded(shipGroupSeqId, shipmentMethod, productId, quantity, offChainVersion, commandId, requesterId);
            OrderEvent.OrderShipGroupAdded e;
            try {
                e = verifyAddOrderShipGroup(eventFactory, shipGroupSeqId, shipmentMethod, productId, quantity, c);
            } catch (Exception ex) {
                throw new DomainError("VerificationFailed", ex);
            }

            apply(e);
        }

        @Override
        public void cancelOrderShipGroupQuantity(Integer shipGroupSeqId, String productId, BigInteger cancelQuantity, Long offChainVersion, String commandId, String requesterId, OrderCommands.CancelOrderShipGroupQuantity c) {
            java.util.function.Supplier<OrderEvent.OrderShipGroupQuantityCanceled> eventFactory = () -> newOrderShipGroupQuantityCanceled(shipGroupSeqId, productId, cancelQuantity, offChainVersion, commandId, requesterId);
            OrderEvent.OrderShipGroupQuantityCanceled e;
            try {
                e = verifyCancelOrderShipGroupQuantity(eventFactory, shipGroupSeqId, productId, cancelQuantity, c);
            } catch (Exception ex) {
                throw new DomainError("VerificationFailed", ex);
            }

            apply(e);
        }

        @Override
        public void removeOrderShipGroupItem(Integer shipGroupSeqId, String productId, Long offChainVersion, String commandId, String requesterId, OrderCommands.RemoveOrderShipGroupItem c) {
            java.util.function.Supplier<OrderEvent.OrderShipGroupItemRemoved> eventFactory = () -> newOrderShipGroupItemRemoved(shipGroupSeqId, productId, offChainVersion, commandId, requesterId);
            OrderEvent.OrderShipGroupItemRemoved e;
            try {
                e = verifyRemoveOrderShipGroupItem(eventFactory, shipGroupSeqId, productId, c);
            } catch (Exception ex) {
                throw new DomainError("VerificationFailed", ex);
            }

            apply(e);
        }

        @Override
        public void removeOrderShipGroup(Integer shipGroupSeqId, Long offChainVersion, String commandId, String requesterId, OrderCommands.RemoveOrderShipGroup c) {
            java.util.function.Supplier<OrderEvent.OrderShipGroupRemoved> eventFactory = () -> newOrderShipGroupRemoved(shipGroupSeqId, offChainVersion, commandId, requesterId);
            OrderEvent.OrderShipGroupRemoved e;
            try {
                e = verifyRemoveOrderShipGroup(eventFactory, shipGroupSeqId, c);
            } catch (Exception ex) {
                throw new DomainError("VerificationFailed", ex);
            }

            apply(e);
        }

        protected OrderEvent.OrderCreated verifyCreate(java.util.function.Supplier<OrderEvent.OrderCreated> eventFactory, String productId, BigInteger quantity, OrderCommands.Create c) {
            String ProductId = productId;
            BigInteger Quantity = quantity;

            OrderEvent.OrderCreated e = (OrderEvent.OrderCreated) ReflectUtils.invokeStaticMethod(
                    "org.dddml.aptosdemocontracts.domain.order.CreateLogic",
                    "verify",
                    new Class[]{java.util.function.Supplier.class, OrderState.class, String.class, BigInteger.class, VerificationContext.class},
                    new Object[]{eventFactory, getState(), productId, quantity, VerificationContext.forCommand(c)}
            );

//package org.dddml.aptosdemocontracts.domain.order;
//
//public class CreateLogic {
//    public static OrderEvent.OrderCreated verify(java.util.function.Supplier<OrderEvent.OrderCreated> eventFactory, OrderState orderState, String productId, BigInteger quantity, VerificationContext verificationContext) {
//    }
//}

            return e;
        }
           

        protected OrderEvent.OrderItemRemoved verifyRemoveItem(java.util.function.Supplier<OrderEvent.OrderItemRemoved> eventFactory, String productId, OrderCommands.RemoveItem c) {
            String ProductId = productId;

            OrderEvent.OrderItemRemoved e = (OrderEvent.OrderItemRemoved) ReflectUtils.invokeStaticMethod(
                    "org.dddml.aptosdemocontracts.domain.order.RemoveItemLogic",
                    "verify",
                    new Class[]{java.util.function.Supplier.class, OrderState.class, String.class, VerificationContext.class},
                    new Object[]{eventFactory, getState(), productId, VerificationContext.forCommand(c)}
            );

//package org.dddml.aptosdemocontracts.domain.order;
//
//public class RemoveItemLogic {
//    public static OrderEvent.OrderItemRemoved verify(java.util.function.Supplier<OrderEvent.OrderItemRemoved> eventFactory, OrderState orderState, String productId, VerificationContext verificationContext) {
//    }
//}

            return e;
        }
           

        protected OrderEvent.OrderItemQuantityUpdated verifyUpdateItemQuantity(java.util.function.Supplier<OrderEvent.OrderItemQuantityUpdated> eventFactory, String productId, BigInteger quantity, OrderCommands.UpdateItemQuantity c) {
            String ProductId = productId;
            BigInteger Quantity = quantity;

            OrderEvent.OrderItemQuantityUpdated e = (OrderEvent.OrderItemQuantityUpdated) ReflectUtils.invokeStaticMethod(
                    "org.dddml.aptosdemocontracts.domain.order.UpdateItemQuantityLogic",
                    "verify",
                    new Class[]{java.util.function.Supplier.class, OrderState.class, String.class, BigInteger.class, VerificationContext.class},
                    new Object[]{eventFactory, getState(), productId, quantity, VerificationContext.forCommand(c)}
            );

//package org.dddml.aptosdemocontracts.domain.order;
//
//public class UpdateItemQuantityLogic {
//    public static OrderEvent.OrderItemQuantityUpdated verify(java.util.function.Supplier<OrderEvent.OrderItemQuantityUpdated> eventFactory, OrderState orderState, String productId, BigInteger quantity, VerificationContext verificationContext) {
//    }
//}

            return e;
        }
           

        protected OrderEvent.OrderEstimatedShipDateUpdated verifyUpdateEstimatedShipDate(java.util.function.Supplier<OrderEvent.OrderEstimatedShipDateUpdated> eventFactory, Day estimatedShipDate, OrderCommands.UpdateEstimatedShipDate c) {
            Day EstimatedShipDate = estimatedShipDate;

            OrderEvent.OrderEstimatedShipDateUpdated e = (OrderEvent.OrderEstimatedShipDateUpdated) ReflectUtils.invokeStaticMethod(
                    "org.dddml.aptosdemocontracts.domain.order.UpdateEstimatedShipDateLogic",
                    "verify",
                    new Class[]{java.util.function.Supplier.class, OrderState.class, Day.class, VerificationContext.class},
                    new Object[]{eventFactory, getState(), estimatedShipDate, VerificationContext.forCommand(c)}
            );

//package org.dddml.aptosdemocontracts.domain.order;
//
//public class UpdateEstimatedShipDateLogic {
//    public static OrderEvent.OrderEstimatedShipDateUpdated verify(java.util.function.Supplier<OrderEvent.OrderEstimatedShipDateUpdated> eventFactory, OrderState orderState, Day estimatedShipDate, VerificationContext verificationContext) {
//    }
//}

            return e;
        }
           

        protected OrderEvent.OrderShipGroupAdded verifyAddOrderShipGroup(java.util.function.Supplier<OrderEvent.OrderShipGroupAdded> eventFactory, Integer shipGroupSeqId, String shipmentMethod, String productId, BigInteger quantity, OrderCommands.AddOrderShipGroup c) {
            Integer ShipGroupSeqId = shipGroupSeqId;
            String ShipmentMethod = shipmentMethod;
            String ProductId = productId;
            BigInteger Quantity = quantity;

            OrderEvent.OrderShipGroupAdded e = (OrderEvent.OrderShipGroupAdded) ReflectUtils.invokeStaticMethod(
                    "org.dddml.aptosdemocontracts.domain.order.AddOrderShipGroupLogic",
                    "verify",
                    new Class[]{java.util.function.Supplier.class, OrderState.class, Integer.class, String.class, String.class, BigInteger.class, VerificationContext.class},
                    new Object[]{eventFactory, getState(), shipGroupSeqId, shipmentMethod, productId, quantity, VerificationContext.forCommand(c)}
            );

//package org.dddml.aptosdemocontracts.domain.order;
//
//public class AddOrderShipGroupLogic {
//    public static OrderEvent.OrderShipGroupAdded verify(java.util.function.Supplier<OrderEvent.OrderShipGroupAdded> eventFactory, OrderState orderState, Integer shipGroupSeqId, String shipmentMethod, String productId, BigInteger quantity, VerificationContext verificationContext) {
//    }
//}

            return e;
        }
           

        protected OrderEvent.OrderShipGroupQuantityCanceled verifyCancelOrderShipGroupQuantity(java.util.function.Supplier<OrderEvent.OrderShipGroupQuantityCanceled> eventFactory, Integer shipGroupSeqId, String productId, BigInteger cancelQuantity, OrderCommands.CancelOrderShipGroupQuantity c) {
            Integer ShipGroupSeqId = shipGroupSeqId;
            String ProductId = productId;
            BigInteger CancelQuantity = cancelQuantity;

            OrderEvent.OrderShipGroupQuantityCanceled e = (OrderEvent.OrderShipGroupQuantityCanceled) ReflectUtils.invokeStaticMethod(
                    "org.dddml.aptosdemocontracts.domain.order.CancelOrderShipGroupQuantityLogic",
                    "verify",
                    new Class[]{java.util.function.Supplier.class, OrderState.class, Integer.class, String.class, BigInteger.class, VerificationContext.class},
                    new Object[]{eventFactory, getState(), shipGroupSeqId, productId, cancelQuantity, VerificationContext.forCommand(c)}
            );

//package org.dddml.aptosdemocontracts.domain.order;
//
//public class CancelOrderShipGroupQuantityLogic {
//    public static OrderEvent.OrderShipGroupQuantityCanceled verify(java.util.function.Supplier<OrderEvent.OrderShipGroupQuantityCanceled> eventFactory, OrderState orderState, Integer shipGroupSeqId, String productId, BigInteger cancelQuantity, VerificationContext verificationContext) {
//    }
//}

            return e;
        }
           

        protected OrderEvent.OrderShipGroupItemRemoved verifyRemoveOrderShipGroupItem(java.util.function.Supplier<OrderEvent.OrderShipGroupItemRemoved> eventFactory, Integer shipGroupSeqId, String productId, OrderCommands.RemoveOrderShipGroupItem c) {
            Integer ShipGroupSeqId = shipGroupSeqId;
            String ProductId = productId;

            OrderEvent.OrderShipGroupItemRemoved e = (OrderEvent.OrderShipGroupItemRemoved) ReflectUtils.invokeStaticMethod(
                    "org.dddml.aptosdemocontracts.domain.order.RemoveOrderShipGroupItemLogic",
                    "verify",
                    new Class[]{java.util.function.Supplier.class, OrderState.class, Integer.class, String.class, VerificationContext.class},
                    new Object[]{eventFactory, getState(), shipGroupSeqId, productId, VerificationContext.forCommand(c)}
            );

//package org.dddml.aptosdemocontracts.domain.order;
//
//public class RemoveOrderShipGroupItemLogic {
//    public static OrderEvent.OrderShipGroupItemRemoved verify(java.util.function.Supplier<OrderEvent.OrderShipGroupItemRemoved> eventFactory, OrderState orderState, Integer shipGroupSeqId, String productId, VerificationContext verificationContext) {
//    }
//}

            return e;
        }
           

        protected OrderEvent.OrderShipGroupRemoved verifyRemoveOrderShipGroup(java.util.function.Supplier<OrderEvent.OrderShipGroupRemoved> eventFactory, Integer shipGroupSeqId, OrderCommands.RemoveOrderShipGroup c) {
            Integer ShipGroupSeqId = shipGroupSeqId;

            OrderEvent.OrderShipGroupRemoved e = (OrderEvent.OrderShipGroupRemoved) ReflectUtils.invokeStaticMethod(
                    "org.dddml.aptosdemocontracts.domain.order.RemoveOrderShipGroupLogic",
                    "verify",
                    new Class[]{java.util.function.Supplier.class, OrderState.class, Integer.class, VerificationContext.class},
                    new Object[]{eventFactory, getState(), shipGroupSeqId, VerificationContext.forCommand(c)}
            );

//package org.dddml.aptosdemocontracts.domain.order;
//
//public class RemoveOrderShipGroupLogic {
//    public static OrderEvent.OrderShipGroupRemoved verify(java.util.function.Supplier<OrderEvent.OrderShipGroupRemoved> eventFactory, OrderState orderState, Integer shipGroupSeqId, VerificationContext verificationContext) {
//    }
//}

            return e;
        }
           

        protected AbstractOrderEvent.OrderCreated newOrderCreated(String productId, BigInteger quantity, Long offChainVersion, String commandId, String requesterId) {
            OrderEventId eventId = new OrderEventId(getState().getOrderId(), null);
            AbstractOrderEvent.OrderCreated e = new AbstractOrderEvent.OrderCreated();

            e.setProductId(productId);
            e.setQuantity(quantity);
            e.setUnitPrice(null);
            e.setTotalAmount(null);
            e.setOwner(null);
            e.setAptosEventVersion(null);
            e.setAptosEventSequenceNumber(null);
            e.setAptosEventType(null);
            e.setAptosEventGuid(null);
            e.setStatus(null);

            e.setCommandId(commandId);
            e.setCreatedBy(requesterId);
            e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));

            e.setOrderEventId(eventId);
            return e;
        }

        protected AbstractOrderEvent.OrderItemRemoved newOrderItemRemoved(String productId, Long offChainVersion, String commandId, String requesterId) {
            OrderEventId eventId = new OrderEventId(getState().getOrderId(), null);
            AbstractOrderEvent.OrderItemRemoved e = new AbstractOrderEvent.OrderItemRemoved();

            e.setProductId(productId);
            e.setAptosEventVersion(null);
            e.setAptosEventSequenceNumber(null);
            e.setAptosEventType(null);
            e.setAptosEventGuid(null);
            e.setStatus(null);

            e.setCommandId(commandId);
            e.setCreatedBy(requesterId);
            e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));

            e.setOrderEventId(eventId);
            return e;
        }

        protected AbstractOrderEvent.OrderItemQuantityUpdated newOrderItemQuantityUpdated(String productId, BigInteger quantity, Long offChainVersion, String commandId, String requesterId) {
            OrderEventId eventId = new OrderEventId(getState().getOrderId(), null);
            AbstractOrderEvent.OrderItemQuantityUpdated e = new AbstractOrderEvent.OrderItemQuantityUpdated();

            e.setProductId(productId);
            e.setQuantity(quantity);
            e.setAptosEventVersion(null);
            e.setAptosEventSequenceNumber(null);
            e.setAptosEventType(null);
            e.setAptosEventGuid(null);
            e.setStatus(null);

            e.setCommandId(commandId);
            e.setCreatedBy(requesterId);
            e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));

            e.setOrderEventId(eventId);
            return e;
        }

        protected AbstractOrderEvent.OrderEstimatedShipDateUpdated newOrderEstimatedShipDateUpdated(Day estimatedShipDate, Long offChainVersion, String commandId, String requesterId) {
            OrderEventId eventId = new OrderEventId(getState().getOrderId(), null);
            AbstractOrderEvent.OrderEstimatedShipDateUpdated e = new AbstractOrderEvent.OrderEstimatedShipDateUpdated();

            e.setEstimatedShipDate(estimatedShipDate);
            e.setAptosEventVersion(null);
            e.setAptosEventSequenceNumber(null);
            e.setAptosEventType(null);
            e.setAptosEventGuid(null);
            e.setStatus(null);

            e.setCommandId(commandId);
            e.setCreatedBy(requesterId);
            e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));

            e.setOrderEventId(eventId);
            return e;
        }

        protected AbstractOrderEvent.OrderShipGroupAdded newOrderShipGroupAdded(Integer shipGroupSeqId, String shipmentMethod, String productId, BigInteger quantity, Long offChainVersion, String commandId, String requesterId) {
            OrderEventId eventId = new OrderEventId(getState().getOrderId(), null);
            AbstractOrderEvent.OrderShipGroupAdded e = new AbstractOrderEvent.OrderShipGroupAdded();

            e.setShipGroupSeqId(shipGroupSeqId);
            e.setShipmentMethod(shipmentMethod);
            e.setProductId(productId);
            e.setQuantity(quantity);
            e.setAptosEventVersion(null);
            e.setAptosEventSequenceNumber(null);
            e.setAptosEventType(null);
            e.setAptosEventGuid(null);
            e.setStatus(null);

            e.setCommandId(commandId);
            e.setCreatedBy(requesterId);
            e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));

            e.setOrderEventId(eventId);
            return e;
        }

        protected AbstractOrderEvent.OrderShipGroupQuantityCanceled newOrderShipGroupQuantityCanceled(Integer shipGroupSeqId, String productId, BigInteger cancelQuantity, Long offChainVersion, String commandId, String requesterId) {
            OrderEventId eventId = new OrderEventId(getState().getOrderId(), null);
            AbstractOrderEvent.OrderShipGroupQuantityCanceled e = new AbstractOrderEvent.OrderShipGroupQuantityCanceled();

            e.setShipGroupSeqId(shipGroupSeqId);
            e.setProductId(productId);
            e.setCancelQuantity(cancelQuantity);
            e.setAptosEventVersion(null);
            e.setAptosEventSequenceNumber(null);
            e.setAptosEventType(null);
            e.setAptosEventGuid(null);
            e.setStatus(null);

            e.setCommandId(commandId);
            e.setCreatedBy(requesterId);
            e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));

            e.setOrderEventId(eventId);
            return e;
        }

        protected AbstractOrderEvent.OrderShipGroupItemRemoved newOrderShipGroupItemRemoved(Integer shipGroupSeqId, String productId, Long offChainVersion, String commandId, String requesterId) {
            OrderEventId eventId = new OrderEventId(getState().getOrderId(), null);
            AbstractOrderEvent.OrderShipGroupItemRemoved e = new AbstractOrderEvent.OrderShipGroupItemRemoved();

            e.setShipGroupSeqId(shipGroupSeqId);
            e.setProductId(productId);
            e.setAptosEventVersion(null);
            e.setAptosEventSequenceNumber(null);
            e.setAptosEventType(null);
            e.setAptosEventGuid(null);
            e.setStatus(null);

            e.setCommandId(commandId);
            e.setCreatedBy(requesterId);
            e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));

            e.setOrderEventId(eventId);
            return e;
        }

        protected AbstractOrderEvent.OrderShipGroupRemoved newOrderShipGroupRemoved(Integer shipGroupSeqId, Long offChainVersion, String commandId, String requesterId) {
            OrderEventId eventId = new OrderEventId(getState().getOrderId(), null);
            AbstractOrderEvent.OrderShipGroupRemoved e = new AbstractOrderEvent.OrderShipGroupRemoved();

            e.setShipGroupSeqId(shipGroupSeqId);
            e.setAptosEventVersion(null);
            e.setAptosEventSequenceNumber(null);
            e.setAptosEventType(null);
            e.setAptosEventGuid(null);
            e.setStatus(null);

            e.setCommandId(commandId);
            e.setCreatedBy(requesterId);
            e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));

            e.setOrderEventId(eventId);
            return e;
        }

    }

}

