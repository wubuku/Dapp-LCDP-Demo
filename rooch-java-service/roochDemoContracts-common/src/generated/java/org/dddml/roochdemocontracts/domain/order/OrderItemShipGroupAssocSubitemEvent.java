// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.domain.order;

import java.util.*;
import org.dddml.roochdemocontracts.domain.*;
import java.util.Date;
import org.dddml.roochdemocontracts.specialization.Event;

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

