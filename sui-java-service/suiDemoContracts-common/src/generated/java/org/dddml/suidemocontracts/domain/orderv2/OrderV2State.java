package org.dddml.suidemocontracts.domain.orderv2;

import java.util.Set;
import java.math.BigInteger;
import org.dddml.suidemocontracts.domain.*;
import java.util.Date;
import org.dddml.suidemocontracts.specialization.Event;

public interface OrderV2State
{
    Long VERSION_ZERO = 0L;

    Long VERSION_NULL = VERSION_ZERO - 1;

    String getOrderId();

    String getId_();

    BigInteger getTotalAmount();

    Day getEstimatedShipDate();

    Long getVersion();

    String getCreatedBy();

    Date getCreatedAt();

    String getUpdatedBy();

    Date getUpdatedAt();

    Boolean getActive();

    Boolean getDeleted();

    EntityStateCollection<String, OrderV2ItemState> getItems();

    interface MutableOrderV2State extends OrderV2State {
        void setOrderId(String orderId);

        void setId_(String id);

        void setTotalAmount(BigInteger totalAmount);

        void setEstimatedShipDate(Day estimatedShipDate);

        void setVersion(Long version);

        void setCreatedBy(String createdBy);

        void setCreatedAt(Date createdAt);

        void setUpdatedBy(String updatedBy);

        void setUpdatedAt(Date updatedAt);

        void setActive(Boolean active);

        void setDeleted(Boolean deleted);


        void mutate(Event e);

        //void when(OrderV2Event.OrderV2StateCreated e);

        //void when(OrderV2Event.OrderV2StateMergePatched e);

        //void when(OrderV2Event.OrderV2StateDeleted e);
    }

    interface SqlOrderV2State extends MutableOrderV2State {

        boolean isStateUnsaved();

        boolean getForReapplying();
    }
}

