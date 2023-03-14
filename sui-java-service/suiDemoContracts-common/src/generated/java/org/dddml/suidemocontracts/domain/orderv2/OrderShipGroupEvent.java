package org.dddml.suidemocontracts.domain.orderv2;

import java.util.*;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.Event;

public interface OrderShipGroupEvent extends Event {

    interface SqlOrderShipGroupEvent extends OrderShipGroupEvent {
        OrderShipGroupEventId getOrderShipGroupEventId();

        boolean getEventReadOnly();

        void setEventReadOnly(boolean readOnly);
    }

    Integer getShipGroupSeqId();

    //void setShipGroupSeqId(Integer shipGroupSeqId);

    String getCreatedBy();

    void setCreatedBy(String createdBy);

    Date getCreatedAt();

    void setCreatedAt(Date createdAt);

    String getCommandId();

    void setCommandId(String commandId);


}

