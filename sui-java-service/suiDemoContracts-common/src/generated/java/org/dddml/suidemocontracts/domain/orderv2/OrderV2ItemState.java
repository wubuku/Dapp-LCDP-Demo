package org.dddml.suidemocontracts.domain.orderv2;

import java.util.Set;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.Event;

public interface OrderV2ItemState
{
    Long VERSION_ZERO = 0L;

    Long VERSION_NULL = VERSION_ZERO - 1;

    String getProductId();

    BigInteger getQuantity();

    BigInteger getItemAmount();

    Long getOffChainVersion();

    String getCreatedBy();

    Date getCreatedAt();

    String getUpdatedBy();

    Date getUpdatedAt();

    Boolean getActive();

    Boolean getDeleted();

    String getOrderV2OrderId();

    interface MutableOrderV2ItemState extends OrderV2ItemState {
        void setProductId(String productId);

        void setQuantity(BigInteger quantity);

        void setItemAmount(BigInteger itemAmount);

        void setOffChainVersion(Long offChainVersion);

        void setCreatedBy(String createdBy);

        void setCreatedAt(Date createdAt);

        void setUpdatedBy(String updatedBy);

        void setUpdatedAt(Date updatedAt);

        void setActive(Boolean active);

        void setDeleted(Boolean deleted);

        void setOrderV2OrderId(String orderV2OrderId);


        void mutate(Event e);

        //void when(OrderV2ItemEvent.OrderV2ItemStateCreated e);

        //void when(OrderV2ItemEvent.OrderV2ItemStateMergePatched e);

        //void when(OrderV2ItemEvent.OrderV2ItemStateRemoved e);
    }

    interface SqlOrderV2ItemState extends MutableOrderV2ItemState {
        OrderV2ItemId getOrderV2ItemId();

        void setOrderV2ItemId(OrderV2ItemId orderV2ItemId);


        boolean isStateUnsaved();

        boolean getForReapplying();
    }
}

