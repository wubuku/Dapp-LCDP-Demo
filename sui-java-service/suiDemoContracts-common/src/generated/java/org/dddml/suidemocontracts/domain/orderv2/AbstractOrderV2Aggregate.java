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

    public void create(OrderV2Command.CreateOrderV2 c) {
        if (c.getVersion() == null) { c.setVersion(OrderV2State.VERSION_NULL); }
        OrderV2Event e = map(c);
        apply(e);
    }

    public void mergePatch(OrderV2Command.MergePatchOrderV2 c) {
        OrderV2Event e = map(c);
        apply(e);
    }

    public void delete(OrderV2Command.DeleteOrderV2 c) {
        OrderV2Event e = map(c);
        apply(e);
    }

    public void throwOnInvalidStateTransition(Command c) {
        OrderV2Command.throwOnInvalidStateTransition(this.state, c);
    }

    protected void apply(Event e) {
        onApplying(e);
        state.mutate(e);
        changes.add(e);
    }

    protected OrderV2Event map(OrderV2Command.CreateOrderV2 c) {
        OrderV2EventId stateEventId = new OrderV2EventId(c.getOrderId(), c.getVersion());
        OrderV2Event.OrderV2StateCreated e = newOrderV2StateCreated(stateEventId);
        e.setTotalAmount(c.getTotalAmount());
        e.setEstimatedShipDate(c.getEstimatedShipDate());
        e.setActive(c.getActive());
        ((AbstractOrderV2Event)e).setCommandId(c.getCommandId());
        e.setCreatedBy(c.getRequesterId());
        e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));
        Long version = c.getVersion();
        for (OrderV2ItemCommand.CreateOrderV2Item innerCommand : c.getCreateOrderV2ItemCommands()) {
            throwOnInconsistentCommands(c, innerCommand);
            OrderV2ItemEvent.OrderV2ItemStateCreated innerEvent = mapCreate(innerCommand, c, version, this.state);
            e.addOrderV2ItemEvent(innerEvent);
        }

        return e;
    }

    protected OrderV2Event map(OrderV2Command.MergePatchOrderV2 c) {
        OrderV2EventId stateEventId = new OrderV2EventId(c.getOrderId(), c.getVersion());
        OrderV2Event.OrderV2StateMergePatched e = newOrderV2StateMergePatched(stateEventId);
        e.setTotalAmount(c.getTotalAmount());
        e.setEstimatedShipDate(c.getEstimatedShipDate());
        e.setActive(c.getActive());
        e.setIsPropertyTotalAmountRemoved(c.getIsPropertyTotalAmountRemoved());
        e.setIsPropertyEstimatedShipDateRemoved(c.getIsPropertyEstimatedShipDateRemoved());
        e.setIsPropertyActiveRemoved(c.getIsPropertyActiveRemoved());
        ((AbstractOrderV2Event)e).setCommandId(c.getCommandId());
        e.setCreatedBy(c.getRequesterId());
        e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));
        Long version = c.getVersion();
        for (OrderV2ItemCommand innerCommand : c.getOrderV2ItemCommands()) {
            throwOnInconsistentCommands(c, innerCommand);
            OrderV2ItemEvent innerEvent = map(innerCommand, c, version, this.state);
            e.addOrderV2ItemEvent(innerEvent);
        }

        return e;
    }

    protected OrderV2Event map(OrderV2Command.DeleteOrderV2 c) {
        OrderV2EventId stateEventId = new OrderV2EventId(c.getOrderId(), c.getVersion());
        OrderV2Event.OrderV2StateDeleted e = newOrderV2StateDeleted(stateEventId);
        ((AbstractOrderV2Event)e).setCommandId(c.getCommandId());
        e.setCreatedBy(c.getRequesterId());
        e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));
        return e;
    }


    protected OrderV2ItemEvent map(OrderV2ItemCommand c, OrderV2Command outerCommand, Long version, OrderV2State outerState) {
        OrderV2ItemCommand.CreateOrderV2Item create = (c.getCommandType().equals(CommandType.CREATE)) ? ((OrderV2ItemCommand.CreateOrderV2Item)c) : null;
        if(create != null) {
            return mapCreate(create, outerCommand, version, outerState);
        }

        OrderV2ItemCommand.MergePatchOrderV2Item merge = (c.getCommandType().equals(CommandType.MERGE_PATCH)) ? ((OrderV2ItemCommand.MergePatchOrderV2Item)c) : null;
        if(merge != null) {
            return mapMergePatch(merge, outerCommand, version, outerState);
        }

        OrderV2ItemCommand.RemoveOrderV2Item remove = (c.getCommandType().equals(CommandType.REMOVE)) ? ((OrderV2ItemCommand.RemoveOrderV2Item)c) : null;
        if (remove != null) {
            return mapRemove(remove, outerCommand, version, outerState);
        }
        throw new UnsupportedOperationException();
    }

    protected OrderV2ItemEvent.OrderV2ItemStateCreated mapCreate(OrderV2ItemCommand.CreateOrderV2Item c, OrderV2Command outerCommand, Long version, OrderV2State outerState) {
        ((AbstractCommand)c).setRequesterId(outerCommand.getRequesterId());
        OrderV2ItemEventId stateEventId = new OrderV2ItemEventId(outerState.getOrderId(), c.getProductId(), version);
        OrderV2ItemEvent.OrderV2ItemStateCreated e = newOrderV2ItemStateCreated(stateEventId);
        OrderV2ItemState s = ((EntityStateCollection.ModifiableEntityStateCollection<String, OrderV2ItemState>)outerState.getItems()).getOrAdd(c.getProductId());

        e.setQuantity(c.getQuantity());
        e.setItemAmount(c.getItemAmount());
        e.setActive(c.getActive());
        e.setCreatedBy(c.getRequesterId());
        e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));

        return e;

    }// END map(ICreate... ////////////////////////////

    protected OrderV2ItemEvent.OrderV2ItemStateMergePatched mapMergePatch(OrderV2ItemCommand.MergePatchOrderV2Item c, OrderV2Command outerCommand, Long version, OrderV2State outerState) {
        ((AbstractCommand)c).setRequesterId(outerCommand.getRequesterId());
        OrderV2ItemEventId stateEventId = new OrderV2ItemEventId(outerState.getOrderId(), c.getProductId(), version);
        OrderV2ItemEvent.OrderV2ItemStateMergePatched e = newOrderV2ItemStateMergePatched(stateEventId);
        OrderV2ItemState s = ((EntityStateCollection.ModifiableEntityStateCollection<String, OrderV2ItemState>)outerState.getItems()).getOrAdd(c.getProductId());

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

    protected OrderV2ItemEvent.OrderV2ItemStateRemoved mapRemove(OrderV2ItemCommand.RemoveOrderV2Item c, OrderV2Command outerCommand, Long version, OrderV2State outerState) {
        ((AbstractCommand)c).setRequesterId(outerCommand.getRequesterId());
        OrderV2ItemEventId stateEventId = new OrderV2ItemEventId(outerState.getOrderId(), c.getProductId(), version);
        OrderV2ItemEvent.OrderV2ItemStateRemoved e = newOrderV2ItemStateRemoved(stateEventId);

        e.setCreatedBy(c.getRequesterId());
        e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));

        return e;

    }// END map(IRemove... ////////////////////////////

    protected void throwOnInconsistentCommands(OrderV2Command command, OrderV2ItemCommand innerCommand) {
        AbstractOrderV2Command properties = command instanceof AbstractOrderV2Command ? (AbstractOrderV2Command) command : null;
        AbstractOrderV2ItemCommand innerProperties = innerCommand instanceof AbstractOrderV2ItemCommand ? (AbstractOrderV2ItemCommand) innerCommand : null;
        if (properties == null || innerProperties == null) { return; }
        String outerOrderIdName = "OrderId";
        String outerOrderIdValue = properties.getOrderId();
        String innerOrderV2OrderIdName = "OrderV2OrderId";
        String innerOrderV2OrderIdValue = innerProperties.getOrderV2OrderId();
        if (innerOrderV2OrderIdValue == null) {
            innerProperties.setOrderV2OrderId(outerOrderIdValue);
        }
        else if (innerOrderV2OrderIdValue != outerOrderIdValue 
            && (innerOrderV2OrderIdValue == null || innerOrderV2OrderIdValue != null && !innerOrderV2OrderIdValue.equals(outerOrderIdValue))) {
            throw DomainError.named("inconsistentId", "Outer %1$s %2$s NOT equals inner %3$s %4$s", outerOrderIdName, outerOrderIdValue, innerOrderV2OrderIdName, innerOrderV2OrderIdValue);
        }
    }// END throwOnInconsistentCommands /////////////////////


    ////////////////////////

    protected OrderV2Event.OrderV2StateCreated newOrderV2StateCreated(Long version, String commandId, String requesterId) {
        OrderV2EventId stateEventId = new OrderV2EventId(this.state.getOrderId(), version);
        OrderV2Event.OrderV2StateCreated e = newOrderV2StateCreated(stateEventId);
        ((AbstractOrderV2Event)e).setCommandId(commandId);
        e.setCreatedBy(requesterId);
        e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));
        return e;
    }

    protected OrderV2Event.OrderV2StateMergePatched newOrderV2StateMergePatched(Long version, String commandId, String requesterId) {
        OrderV2EventId stateEventId = new OrderV2EventId(this.state.getOrderId(), version);
        OrderV2Event.OrderV2StateMergePatched e = newOrderV2StateMergePatched(stateEventId);
        ((AbstractOrderV2Event)e).setCommandId(commandId);
        e.setCreatedBy(requesterId);
        e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));
        return e;
    }

    protected OrderV2Event.OrderV2StateDeleted newOrderV2StateDeleted(Long version, String commandId, String requesterId) {
        OrderV2EventId stateEventId = new OrderV2EventId(this.state.getOrderId(), version);
        OrderV2Event.OrderV2StateDeleted e = newOrderV2StateDeleted(stateEventId);
        ((AbstractOrderV2Event)e).setCommandId(commandId);
        e.setCreatedBy(requesterId);
        e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));
        return e;
    }

    protected OrderV2Event.OrderV2StateCreated newOrderV2StateCreated(OrderV2EventId stateEventId) {
        return new AbstractOrderV2Event.SimpleOrderV2StateCreated(stateEventId);
    }

    protected OrderV2Event.OrderV2StateMergePatched newOrderV2StateMergePatched(OrderV2EventId stateEventId) {
        return new AbstractOrderV2Event.SimpleOrderV2StateMergePatched(stateEventId);
    }

    protected OrderV2Event.OrderV2StateDeleted newOrderV2StateDeleted(OrderV2EventId stateEventId) {
        return new AbstractOrderV2Event.SimpleOrderV2StateDeleted(stateEventId);
    }

    protected OrderV2ItemEvent.OrderV2ItemStateCreated newOrderV2ItemStateCreated(OrderV2ItemEventId stateEventId) {
        return new AbstractOrderV2ItemEvent.SimpleOrderV2ItemStateCreated(stateEventId);
    }

    protected OrderV2ItemEvent.OrderV2ItemStateMergePatched newOrderV2ItemStateMergePatched(OrderV2ItemEventId stateEventId) {
        return new AbstractOrderV2ItemEvent.SimpleOrderV2ItemStateMergePatched(stateEventId);
    }

    protected OrderV2ItemEvent.OrderV2ItemStateRemoved newOrderV2ItemStateRemoved(OrderV2ItemEventId stateEventId) {
        return new AbstractOrderV2ItemEvent.SimpleOrderV2ItemStateRemoved(stateEventId);
    }


    public static class SimpleOrderV2Aggregate extends AbstractOrderV2Aggregate {
        public SimpleOrderV2Aggregate(OrderV2State state) {
            super(state);
        }

        @Override
        public void removeItem(String productId, Long version, String commandId, String requesterId, OrderV2Commands.RemoveItem c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateItemQuantity(String productId, BigInteger quantity, Long version, String commandId, String requesterId, OrderV2Commands.UpdateItemQuantity c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateEstimatedShipDate(Day estimatedShipDate, Long version, String commandId, String requesterId, OrderV2Commands.UpdateEstimatedShipDate c) {
            throw new UnsupportedOperationException();
        }

    }

}

