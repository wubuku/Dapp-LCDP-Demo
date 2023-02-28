package org.dddml.suidemocontracts.domain.order;

import java.util.Set;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.Event;

public interface OrderItemState
{
    Long VERSION_ZERO = 0L;

    Long VERSION_NULL = VERSION_ZERO - 1;

    String getProductId();

    BigInteger getQuantity();

    BigInteger getItemAmount();

    Long getVersion();

    String getCreatedBy();

    Date getCreatedAt();

    String getUpdatedBy();

    Date getUpdatedAt();

    Boolean getActive();

    Boolean getDeleted();

    String getOrderId();

    interface MutableOrderItemState extends OrderItemState {
        void setProductId(String productId);

        void setQuantity(BigInteger quantity);

        void setItemAmount(BigInteger itemAmount);

        void setVersion(Long version);

        void setCreatedBy(String createdBy);

        void setCreatedAt(Date createdAt);

        void setUpdatedBy(String updatedBy);

        void setUpdatedAt(Date updatedAt);

        void setActive(Boolean active);

        void setDeleted(Boolean deleted);

        void setOrderId(String orderId);


        void mutate(Event e);

        //void when(OrderItemEvent.OrderItemStateCreated e);

        //void when(OrderItemEvent.OrderItemStateMergePatched e);

        //void when(OrderItemEvent.OrderItemStateRemoved e);
    }

    interface SqlOrderItemState extends MutableOrderItemState {
        OrderItemId getOrderItemId();

        void setOrderItemId(OrderItemId orderItemId);


        boolean isStateUnsaved();

        boolean getForReapplying();
    }
}

