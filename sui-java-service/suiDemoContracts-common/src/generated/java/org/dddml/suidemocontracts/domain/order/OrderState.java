package org.dddml.suidemocontracts.domain.order;

import java.util.Set;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.Event;

public interface OrderState
{
    Long VERSION_ZERO = 0L;

    Long VERSION_NULL = VERSION_ZERO - 1;

    String getId();

    BigInteger getTotalAmount();

    Long getVersion();

    String getCreatedBy();

    Date getCreatedAt();

    String getUpdatedBy();

    Date getUpdatedAt();

    Boolean getActive();

    Boolean getDeleted();

    EntityStateCollection<String, OrderItemState> getItems();

    interface MutableOrderState extends OrderState {
        void setId(String id);

        void setTotalAmount(BigInteger totalAmount);

        void setVersion(Long version);

        void setCreatedBy(String createdBy);

        void setCreatedAt(Date createdAt);

        void setUpdatedBy(String updatedBy);

        void setUpdatedAt(Date updatedAt);

        void setActive(Boolean active);

        void setDeleted(Boolean deleted);


        void mutate(Event e);

        //void when(OrderEvent.OrderStateCreated e);

        //void when(OrderEvent.OrderStateMergePatched e);

        //void when(OrderEvent.OrderStateDeleted e);
    }

    interface SqlOrderState extends MutableOrderState {

        boolean isStateUnsaved();

        boolean getForReapplying();
    }
}

