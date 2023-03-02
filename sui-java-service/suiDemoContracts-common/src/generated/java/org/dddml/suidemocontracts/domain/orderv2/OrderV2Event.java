package org.dddml.suidemocontracts.domain.orderv2;

import java.util.*;
import java.math.BigInteger;
import org.dddml.suidemocontracts.domain.*;
import java.util.Date;
import org.dddml.suidemocontracts.specialization.Event;

public interface OrderV2Event extends Event {

    interface SqlOrderV2Event extends OrderV2Event {
        OrderV2EventId getOrderV2EventId();

        boolean getEventReadOnly();

        void setEventReadOnly(boolean readOnly);
    }

    String getOrderId();

    //void setOrderId(String orderId);

    Long getVersion();
    
    //void setVersion(Long version);

    String getId();
    
    //void setId(String id);

    String getCreatedBy();

    void setCreatedBy(String createdBy);

    Date getCreatedAt();

    void setCreatedAt(Date createdAt);

    String getCommandId();

    void setCommandId(String commandId);


}

