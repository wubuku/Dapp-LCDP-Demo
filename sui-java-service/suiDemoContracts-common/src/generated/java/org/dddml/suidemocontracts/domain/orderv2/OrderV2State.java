// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.suidemocontracts.domain.orderv2;

import java.util.*;
import java.math.*;
import java.math.BigInteger;
import org.dddml.suidemocontracts.domain.*;
import java.util.Date;
import org.dddml.suidemocontracts.specialization.Event;

public interface OrderV2State extends VersionedSuiMoveObject
{
    Long VERSION_ZERO = 0L;

    Long VERSION_NULL = VERSION_ZERO - 1;

    String getOrderId();

    String getId_();

    BigInteger getTotalAmount();

    Day getEstimatedShipDate();

    Long getOffChainVersion();

    String getCreatedBy();

    Date getCreatedAt();

    String getUpdatedBy();

    Date getUpdatedAt();

    Boolean getActive();

    Boolean getDeleted();

    EntityStateCollection<String, OrderV2ItemState> getItems();

    EntityStateCollection<Integer, OrderShipGroupState> getOrderShipGroups();

    interface MutableOrderV2State extends OrderV2State, VersionedSuiMoveObject.MutableVersionedSuiMoveObject {
        void setOrderId(String orderId);

        void setId_(String id);

        void setTotalAmount(BigInteger totalAmount);

        void setEstimatedShipDate(Day estimatedShipDate);

        void setOffChainVersion(Long offChainVersion);

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

