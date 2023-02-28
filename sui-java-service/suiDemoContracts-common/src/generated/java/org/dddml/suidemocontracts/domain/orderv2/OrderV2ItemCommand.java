package org.dddml.suidemocontracts.domain.orderv2;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.domain.Command;
import org.dddml.suidemocontracts.specialization.DomainError;

public interface OrderV2ItemCommand extends Command
{

    String getProductId();

    void setProductId(String productId);

    interface CreateOrMergePatchOrderV2Item extends OrderV2ItemCommand
    {

        BigInteger getQuantity();

        void setQuantity(BigInteger quantity);

        BigInteger getItemAmount();

        void setItemAmount(BigInteger itemAmount);

        Boolean getActive();

        void setActive(Boolean active);

    }

    interface CreateOrderV2Item extends CreateOrMergePatchOrderV2Item
    {
    }

    interface MergePatchOrderV2Item extends CreateOrMergePatchOrderV2Item
    {
        Boolean getIsPropertyQuantityRemoved();

        void setIsPropertyQuantityRemoved(Boolean removed);

        Boolean getIsPropertyItemAmountRemoved();

        void setIsPropertyItemAmountRemoved(Boolean removed);

        Boolean getIsPropertyActiveRemoved();

        void setIsPropertyActiveRemoved(Boolean removed);


    }

	interface RemoveOrderV2Item extends OrderV2ItemCommand
	{
	}

}

