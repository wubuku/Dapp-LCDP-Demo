package org.dddml.suidemocontracts.domain.order;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.Event;

public interface OrderItemEvent extends Event {

    interface SqlOrderItemEvent extends OrderItemEvent {
        OrderItemEventId getOrderItemEventId();

        boolean getEventReadOnly();

        void setEventReadOnly(boolean readOnly);
    }

    String getProductId();

    //void setProductId(String productId);

    String getCreatedBy();

    void setCreatedBy(String createdBy);

    Date getCreatedAt();

    void setCreatedAt(Date createdAt);

    String getCommandId();

    void setCommandId(String commandId);

    interface OrderItemStateEvent extends OrderItemEvent {
        Long getVersion();

        void setVersion(Long version);

        BigInteger getQuantity();

        void setQuantity(BigInteger quantity);

        BigInteger getItemAmount();

        void setItemAmount(BigInteger itemAmount);

        Boolean getActive();

        void setActive(Boolean active);

    }

    interface OrderItemStateCreated extends OrderItemStateEvent
    {
    
    }


    interface OrderItemStateMergePatched extends OrderItemStateEvent
    {
        Boolean getIsPropertyQuantityRemoved();

        void setIsPropertyQuantityRemoved(Boolean removed);

        Boolean getIsPropertyItemAmountRemoved();

        void setIsPropertyItemAmountRemoved(Boolean removed);

        Boolean getIsPropertyActiveRemoved();

        void setIsPropertyActiveRemoved(Boolean removed);



    }

    interface OrderItemStateRemoved extends OrderItemStateEvent
    {
    }


}

