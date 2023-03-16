package org.dddml.suidemocontracts.domain.orderv2;

import java.util.*;
import org.dddml.suidemocontracts.domain.*;
import java.util.Date;
import org.dddml.suidemocontracts.specialization.Event;

public interface OrderItemShipGroupAssocSubitemEvent extends Event {

    interface SqlOrderItemShipGroupAssocSubitemEvent extends OrderItemShipGroupAssocSubitemEvent {
        OrderItemShipGroupAssocSubitemEventId getOrderItemShipGroupAssocSubitemEventId();

        boolean getEventReadOnly();

        void setEventReadOnly(boolean readOnly);
    }

    Day getOrderItemShipGroupAssocSubitemDay();

    //void setOrderItemShipGroupAssocSubitemDay(Day orderItemShipGroupAssocSubitemDay);

    String getCreatedBy();

    void setCreatedBy(String createdBy);

    Date getCreatedAt();

    void setCreatedAt(Date createdAt);

    String getCommandId();

    void setCommandId(String commandId);


}

