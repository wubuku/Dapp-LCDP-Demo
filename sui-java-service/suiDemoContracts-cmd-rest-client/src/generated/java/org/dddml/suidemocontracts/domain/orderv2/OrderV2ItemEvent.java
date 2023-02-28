package org.dddml.suidemocontracts.domain.orderv2;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.Event;

public interface OrderV2ItemEvent extends Event {

    interface SqlOrderV2ItemEvent extends OrderV2ItemEvent {
        OrderV2ItemEventId getOrderV2ItemEventId();

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

    interface OrderV2ItemStateEvent extends OrderV2ItemEvent {
        Long getVersion();

        void setVersion(Long version);

        BigInteger getQuantity();

        void setQuantity(BigInteger quantity);

        BigInteger getItemAmount();

        void setItemAmount(BigInteger itemAmount);

        Boolean getActive();

        void setActive(Boolean active);

    }

    interface OrderV2ItemStateCreated extends OrderV2ItemStateEvent
    {
    
    }


    interface OrderV2ItemStateMergePatched extends OrderV2ItemStateEvent
    {
        Boolean getIsPropertyQuantityRemoved();

        void setIsPropertyQuantityRemoved(Boolean removed);

        Boolean getIsPropertyItemAmountRemoved();

        void setIsPropertyItemAmountRemoved(Boolean removed);

        Boolean getIsPropertyActiveRemoved();

        void setIsPropertyActiveRemoved(Boolean removed);



    }

    interface OrderV2ItemStateRemoved extends OrderV2ItemStateEvent
    {
    }


}

