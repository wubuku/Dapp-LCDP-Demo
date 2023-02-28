package org.dddml.suidemocontracts.domain.order;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.domain.Command;
import org.dddml.suidemocontracts.specialization.DomainError;

public interface OrderCommand extends Command
{

    String getId();

    void setId(String id);

    Long getVersion();

    void setVersion(Long version);

    static void throwOnInvalidStateTransition(OrderState state, Command c) {
        if (state.getVersion() == null) {
            if (isCommandCreate((OrderCommand)c)) {
                return;
            }
            throw DomainError.named("premature", "Can't do anything to unexistent aggregate");
        }
        if (state.getDeleted() != null && state.getDeleted()) {
            throw DomainError.named("zombie", "Can't do anything to deleted aggregate.");
        }
        if (isCommandCreate((OrderCommand)c))
            throw DomainError.named("rebirth", "Can't create aggregate that already exists");
    }

    static boolean isCommandCreate(OrderCommand c) {
        if ((c instanceof OrderCommand.CreateOrder) 
            && (COMMAND_TYPE_CREATE.equals(c.getCommandType()) || c.getVersion().equals(OrderState.VERSION_NULL)))
            return true;
        if ((c instanceof OrderCommand.MergePatchOrder))
            return false;
        if ((c instanceof OrderCommand.DeleteOrder))
            return false;
        if (c.getVersion().equals(OrderState.VERSION_NULL))
            return true;
        return false;
    }

    interface CreateOrMergePatchOrder extends OrderCommand
    {

        BigInteger getTotalAmount();

        void setTotalAmount(BigInteger totalAmount);

        Boolean getActive();

        void setActive(Boolean active);

    }

    interface CreateOrder extends CreateOrMergePatchOrder
    {
        CreateOrderItemCommandCollection getCreateOrderItemCommands();

        OrderItemCommand.CreateOrderItem newCreateOrderItem();

    }

    interface MergePatchOrder extends CreateOrMergePatchOrder
    {
        Boolean getIsPropertyTotalAmountRemoved();

        void setIsPropertyTotalAmountRemoved(Boolean removed);

        Boolean getIsPropertyActiveRemoved();

        void setIsPropertyActiveRemoved(Boolean removed);


        OrderItemCommandCollection getOrderItemCommands();

        OrderItemCommand.CreateOrderItem newCreateOrderItem();

        OrderItemCommand.MergePatchOrderItem newMergePatchOrderItem();

        OrderItemCommand.RemoveOrderItem newRemoveOrderItem();

    }

	interface DeleteOrder extends OrderCommand
	{
	}

    interface CreateOrderItemCommandCollection extends Iterable<OrderItemCommand.CreateOrderItem>
    {
        void add(OrderItemCommand.CreateOrderItem c);

        void remove(OrderItemCommand.CreateOrderItem c);

        void clear();
    }

    interface OrderItemCommandCollection extends Iterable<OrderItemCommand>
    {
        void add(OrderItemCommand c);

        void remove(OrderItemCommand c);

        void clear();
    }

}

