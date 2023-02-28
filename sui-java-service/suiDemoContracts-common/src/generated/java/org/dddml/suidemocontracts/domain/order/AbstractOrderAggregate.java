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

    public void create(OrderCommand.CreateOrder c) {
        if (c.getVersion() == null) { c.setVersion(OrderState.VERSION_NULL); }
        OrderEvent e = map(c);
        apply(e);
    }

    public void mergePatch(OrderCommand.MergePatchOrder c) {
        OrderEvent e = map(c);
        apply(e);
    }

    public void delete(OrderCommand.DeleteOrder c) {
        OrderEvent e = map(c);
        apply(e);
    }

    public void throwOnInvalidStateTransition(Command c) {
        OrderCommand.throwOnInvalidStateTransition(this.state, c);
    }

    protected void apply(Event e) {
        onApplying(e);
        state.mutate(e);
        changes.add(e);
    }

    protected OrderEvent map(OrderCommand.CreateOrder c) {
        OrderEventId stateEventId = new OrderEventId(c.getId(), c.getVersion());
        OrderEvent.OrderStateCreated e = newOrderStateCreated(stateEventId);
        e.setTotalAmount(c.getTotalAmount());
        e.setActive(c.getActive());
        ((AbstractOrderEvent)e).setCommandId(c.getCommandId());
        e.setCreatedBy(c.getRequesterId());
        e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));
        Long version = c.getVersion();
        for (OrderItemCommand.CreateOrderItem innerCommand : c.getCreateOrderItemCommands()) {
            throwOnInconsistentCommands(c, innerCommand);
            OrderItemEvent.OrderItemStateCreated innerEvent = mapCreate(innerCommand, c, version, this.state);
            e.addOrderItemEvent(innerEvent);
        }

        return e;
    }

    protected OrderEvent map(OrderCommand.MergePatchOrder c) {
        OrderEventId stateEventId = new OrderEventId(c.getId(), c.getVersion());
        OrderEvent.OrderStateMergePatched e = newOrderStateMergePatched(stateEventId);
        e.setTotalAmount(c.getTotalAmount());
        e.setActive(c.getActive());
        e.setIsPropertyTotalAmountRemoved(c.getIsPropertyTotalAmountRemoved());
        e.setIsPropertyActiveRemoved(c.getIsPropertyActiveRemoved());
        ((AbstractOrderEvent)e).setCommandId(c.getCommandId());
        e.setCreatedBy(c.getRequesterId());
        e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));
        Long version = c.getVersion();
        for (OrderItemCommand innerCommand : c.getOrderItemCommands()) {
            throwOnInconsistentCommands(c, innerCommand);
            OrderItemEvent innerEvent = map(innerCommand, c, version, this.state);
            e.addOrderItemEvent(innerEvent);
        }

        return e;
    }

    protected OrderEvent map(OrderCommand.DeleteOrder c) {
        OrderEventId stateEventId = new OrderEventId(c.getId(), c.getVersion());
        OrderEvent.OrderStateDeleted e = newOrderStateDeleted(stateEventId);
        ((AbstractOrderEvent)e).setCommandId(c.getCommandId());
        e.setCreatedBy(c.getRequesterId());
        e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));
        return e;
    }


    protected OrderItemEvent map(OrderItemCommand c, OrderCommand outerCommand, Long version, OrderState outerState) {
        OrderItemCommand.CreateOrderItem create = (c.getCommandType().equals(CommandType.CREATE)) ? ((OrderItemCommand.CreateOrderItem)c) : null;
        if(create != null) {
            return mapCreate(create, outerCommand, version, outerState);
        }

        OrderItemCommand.MergePatchOrderItem merge = (c.getCommandType().equals(CommandType.MERGE_PATCH)) ? ((OrderItemCommand.MergePatchOrderItem)c) : null;
        if(merge != null) {
            return mapMergePatch(merge, outerCommand, version, outerState);
        }

        OrderItemCommand.RemoveOrderItem remove = (c.getCommandType().equals(CommandType.REMOVE)) ? ((OrderItemCommand.RemoveOrderItem)c) : null;
        if (remove != null) {
            return mapRemove(remove, outerCommand, version, outerState);
        }
        throw new UnsupportedOperationException();
    }

    protected OrderItemEvent.OrderItemStateCreated mapCreate(OrderItemCommand.CreateOrderItem c, OrderCommand outerCommand, Long version, OrderState outerState) {
        ((AbstractCommand)c).setRequesterId(outerCommand.getRequesterId());
        OrderItemEventId stateEventId = new OrderItemEventId(outerState.getId(), c.getProductId(), version);
        OrderItemEvent.OrderItemStateCreated e = newOrderItemStateCreated(stateEventId);
        OrderItemState s = ((EntityStateCollection.ModifiableEntityStateCollection<String, OrderItemState>)outerState.getItems()).getOrAdd(c.getProductId());

        e.setQuantity(c.getQuantity());
        e.setItemAmount(c.getItemAmount());
        e.setActive(c.getActive());
        e.setCreatedBy(c.getRequesterId());
        e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));

        return e;

    }// END map(ICreate... ////////////////////////////

    protected OrderItemEvent.OrderItemStateMergePatched mapMergePatch(OrderItemCommand.MergePatchOrderItem c, OrderCommand outerCommand, Long version, OrderState outerState) {
        ((AbstractCommand)c).setRequesterId(outerCommand.getRequesterId());
        OrderItemEventId stateEventId = new OrderItemEventId(outerState.getId(), c.getProductId(), version);
        OrderItemEvent.OrderItemStateMergePatched e = newOrderItemStateMergePatched(stateEventId);
        OrderItemState s = ((EntityStateCollection.ModifiableEntityStateCollection<String, OrderItemState>)outerState.getItems()).getOrAdd(c.getProductId());

        e.setQuantity(c.getQuantity());
        e.setItemAmount(c.getItemAmount());
        e.setActive(c.getActive());
        e.setIsPropertyQuantityRemoved(c.getIsPropertyQuantityRemoved());
        e.setIsPropertyItemAmountRemoved(c.getIsPropertyItemAmountRemoved());
        e.setIsPropertyActiveRemoved(c.getIsPropertyActiveRemoved());

        e.setCreatedBy(c.getRequesterId());
        e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));

        return e;

    }// END map(IMergePatch... ////////////////////////////

    protected OrderItemEvent.OrderItemStateRemoved mapRemove(OrderItemCommand.RemoveOrderItem c, OrderCommand outerCommand, Long version, OrderState outerState) {
        ((AbstractCommand)c).setRequesterId(outerCommand.getRequesterId());
        OrderItemEventId stateEventId = new OrderItemEventId(outerState.getId(), c.getProductId(), version);
        OrderItemEvent.OrderItemStateRemoved e = newOrderItemStateRemoved(stateEventId);

        e.setCreatedBy(c.getRequesterId());
        e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));

        return e;

    }// END map(IRemove... ////////////////////////////

    protected void throwOnInconsistentCommands(OrderCommand command, OrderItemCommand innerCommand) {
        AbstractOrderCommand properties = command instanceof AbstractOrderCommand ? (AbstractOrderCommand) command : null;
        AbstractOrderItemCommand innerProperties = innerCommand instanceof AbstractOrderItemCommand ? (AbstractOrderItemCommand) innerCommand : null;
        if (properties == null || innerProperties == null) { return; }
        String outerIdName = "Id";
        String outerIdValue = properties.getId();
        String innerOrderIdName = "OrderId";
        String innerOrderIdValue = innerProperties.getOrderId();
        if (innerOrderIdValue == null) {
            innerProperties.setOrderId(outerIdValue);
        }
        else if (innerOrderIdValue != outerIdValue 
            && (innerOrderIdValue == null || innerOrderIdValue != null && !innerOrderIdValue.equals(outerIdValue))) {
            throw DomainError.named("inconsistentId", "Outer %1$s %2$s NOT equals inner %3$s %4$s", outerIdName, outerIdValue, innerOrderIdName, innerOrderIdValue);
        }
    }// END throwOnInconsistentCommands /////////////////////


    ////////////////////////

    protected OrderEvent.OrderStateCreated newOrderStateCreated(Long version, String commandId, String requesterId) {
        OrderEventId stateEventId = new OrderEventId(this.state.getId(), version);
        OrderEvent.OrderStateCreated e = newOrderStateCreated(stateEventId);
        ((AbstractOrderEvent)e).setCommandId(commandId);
        e.setCreatedBy(requesterId);
        e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));
        return e;
    }

    protected OrderEvent.OrderStateMergePatched newOrderStateMergePatched(Long version, String commandId, String requesterId) {
        OrderEventId stateEventId = new OrderEventId(this.state.getId(), version);
        OrderEvent.OrderStateMergePatched e = newOrderStateMergePatched(stateEventId);
        ((AbstractOrderEvent)e).setCommandId(commandId);
        e.setCreatedBy(requesterId);
        e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));
        return e;
    }

    protected OrderEvent.OrderStateDeleted newOrderStateDeleted(Long version, String commandId, String requesterId) {
        OrderEventId stateEventId = new OrderEventId(this.state.getId(), version);
        OrderEvent.OrderStateDeleted e = newOrderStateDeleted(stateEventId);
        ((AbstractOrderEvent)e).setCommandId(commandId);
        e.setCreatedBy(requesterId);
        e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));
        return e;
    }

    protected OrderEvent.OrderStateCreated newOrderStateCreated(OrderEventId stateEventId) {
        return new AbstractOrderEvent.SimpleOrderStateCreated(stateEventId);
    }

    protected OrderEvent.OrderStateMergePatched newOrderStateMergePatched(OrderEventId stateEventId) {
        return new AbstractOrderEvent.SimpleOrderStateMergePatched(stateEventId);
    }

    protected OrderEvent.OrderStateDeleted newOrderStateDeleted(OrderEventId stateEventId) {
        return new AbstractOrderEvent.SimpleOrderStateDeleted(stateEventId);
    }

    protected OrderItemEvent.OrderItemStateCreated newOrderItemStateCreated(OrderItemEventId stateEventId) {
        return new AbstractOrderItemEvent.SimpleOrderItemStateCreated(stateEventId);
    }

    protected OrderItemEvent.OrderItemStateMergePatched newOrderItemStateMergePatched(OrderItemEventId stateEventId) {
        return new AbstractOrderItemEvent.SimpleOrderItemStateMergePatched(stateEventId);
    }

    protected OrderItemEvent.OrderItemStateRemoved newOrderItemStateRemoved(OrderItemEventId stateEventId) {
        return new AbstractOrderItemEvent.SimpleOrderItemStateRemoved(stateEventId);
    }


    public static class SimpleOrderAggregate extends AbstractOrderAggregate {
        public SimpleOrderAggregate(OrderState state) {
            super(state);
        }

        @Override
        public void removeItem(String productId, Long version, String commandId, String requesterId, OrderCommands.RemoveItem c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateItemQuantity(String productId, BigInteger quantity, Long version, String commandId, String requesterId, OrderCommands.UpdateItemQuantity c) {
            throw new UnsupportedOperationException();
        }

    }

}

