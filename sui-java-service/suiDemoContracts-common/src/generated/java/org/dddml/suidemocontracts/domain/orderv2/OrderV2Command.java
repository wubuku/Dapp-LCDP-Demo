package org.dddml.suidemocontracts.domain.orderv2;

import java.util.*;
import java.math.BigInteger;
import org.dddml.suidemocontracts.domain.*;
import java.util.Date;
import org.dddml.suidemocontracts.domain.Command;
import org.dddml.suidemocontracts.specialization.DomainError;

public interface OrderV2Command extends Command
{

    String getOrderId();

    void setOrderId(String orderId);

    Long getVersion();

    void setVersion(Long version);

    static void throwOnInvalidStateTransition(OrderV2State state, Command c) {
        if (state.getVersion() == null) {
            if (isCommandCreate((OrderV2Command)c)) {
                return;
            }
            throw DomainError.named("premature", "Can't do anything to unexistent aggregate");
        }
        if (state.getDeleted() != null && state.getDeleted()) {
            throw DomainError.named("zombie", "Can't do anything to deleted aggregate.");
        }
        if (isCommandCreate((OrderV2Command)c))
            throw DomainError.named("rebirth", "Can't create aggregate that already exists");
    }

    static boolean isCommandCreate(OrderV2Command c) {
        if ((c instanceof OrderV2Command.CreateOrderV2) 
            && (COMMAND_TYPE_CREATE.equals(c.getCommandType()) || c.getVersion().equals(OrderV2State.VERSION_NULL)))
            return true;
        if ((c instanceof OrderV2Command.MergePatchOrderV2))
            return false;
        if ((c instanceof OrderV2Command.DeleteOrderV2))
            return false;
        if (c.getVersion().equals(OrderV2State.VERSION_NULL))
            return true;
        return false;
    }

    interface CreateOrMergePatchOrderV2 extends OrderV2Command
    {

        BigInteger getTotalAmount();

        void setTotalAmount(BigInteger totalAmount);

        Day getEstimatedShipDate();

        void setEstimatedShipDate(Day estimatedShipDate);

        Boolean getActive();

        void setActive(Boolean active);

    }

    interface CreateOrderV2 extends CreateOrMergePatchOrderV2
    {
        CreateOrderV2ItemCommandCollection getCreateOrderV2ItemCommands();

        OrderV2ItemCommand.CreateOrderV2Item newCreateOrderV2Item();

    }

    interface MergePatchOrderV2 extends CreateOrMergePatchOrderV2
    {
        Boolean getIsPropertyTotalAmountRemoved();

        void setIsPropertyTotalAmountRemoved(Boolean removed);

        Boolean getIsPropertyEstimatedShipDateRemoved();

        void setIsPropertyEstimatedShipDateRemoved(Boolean removed);

        Boolean getIsPropertyActiveRemoved();

        void setIsPropertyActiveRemoved(Boolean removed);


        OrderV2ItemCommandCollection getOrderV2ItemCommands();

        OrderV2ItemCommand.CreateOrderV2Item newCreateOrderV2Item();

        OrderV2ItemCommand.MergePatchOrderV2Item newMergePatchOrderV2Item();

        OrderV2ItemCommand.RemoveOrderV2Item newRemoveOrderV2Item();

    }

	interface DeleteOrderV2 extends OrderV2Command
	{
	}

    interface CreateOrderV2ItemCommandCollection extends Iterable<OrderV2ItemCommand.CreateOrderV2Item>
    {
        void add(OrderV2ItemCommand.CreateOrderV2Item c);

        void remove(OrderV2ItemCommand.CreateOrderV2Item c);

        void clear();
    }

    interface OrderV2ItemCommandCollection extends Iterable<OrderV2ItemCommand>
    {
        void add(OrderV2ItemCommand c);

        void remove(OrderV2ItemCommand c);

        void clear();
    }

}

