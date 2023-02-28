package org.dddml.suidemocontracts.domain.order;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.domain.Command;
import org.dddml.suidemocontracts.specialization.DomainError;

public interface OrderItemCommand extends Command
{

    String getProductId();

    void setProductId(String productId);

    interface CreateOrMergePatchOrderItem extends OrderItemCommand
    {

        BigInteger getQuantity();

        void setQuantity(BigInteger quantity);

        BigInteger getItemAmount();

        void setItemAmount(BigInteger itemAmount);

        Boolean getActive();

        void setActive(Boolean active);

    }

    interface CreateOrderItem extends CreateOrMergePatchOrderItem
    {
    }

    interface MergePatchOrderItem extends CreateOrMergePatchOrderItem
    {
        Boolean getIsPropertyQuantityRemoved();

        void setIsPropertyQuantityRemoved(Boolean removed);

        Boolean getIsPropertyItemAmountRemoved();

        void setIsPropertyItemAmountRemoved(Boolean removed);

        Boolean getIsPropertyActiveRemoved();

        void setIsPropertyActiveRemoved(Boolean removed);


    }

	interface RemoveOrderItem extends OrderItemCommand
	{
	}

}

