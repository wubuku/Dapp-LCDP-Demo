// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.domain.order;

import java.util.*;
import java.math.*;
import java.math.BigInteger;
import org.dddml.roochdemocontracts.domain.*;
import java.util.Date;
import org.dddml.roochdemocontracts.specialization.Event;

public interface OrderState extends VersionedRoochMoveObject
{
    Long VERSION_ZERO = 0L;

    Long VERSION_NULL = VERSION_ZERO - 1;

    String getOrderId();

    String getId_();

    BigInteger getTotalAmount();

    Day getEstimatedShipDate();

    String getFavoriteDeliveryWeekday();

    Long getOffChainVersion();

    String getCreatedBy();

    Date getCreatedAt();

    String getUpdatedBy();

    Date getUpdatedAt();

    Boolean getActive();

    Boolean getDeleted();

    Set<Integer> getDeliveryWeekdays();

    EntityStateCollection<String, OrderItemState> getItems();

    EntityStateCollection<Integer, OrderShipGroupState> getOrderShipGroups();

    interface MutableOrderState extends OrderState, VersionedRoochMoveObject.MutableVersionedRoochMoveObject {
        void setOrderId(String orderId);

        void setId_(String id);

        void setTotalAmount(BigInteger totalAmount);

        void setEstimatedShipDate(Day estimatedShipDate);

        void setFavoriteDeliveryWeekday(String favoriteDeliveryWeekday);

        void setOffChainVersion(Long offChainVersion);

        void setCreatedBy(String createdBy);

        void setCreatedAt(Date createdAt);

        void setUpdatedBy(String updatedBy);

        void setUpdatedAt(Date updatedAt);

        void setActive(Boolean active);

        void setDeleted(Boolean deleted);

        void setDeliveryWeekdays(Set<Integer> deliveryWeekdays);


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

