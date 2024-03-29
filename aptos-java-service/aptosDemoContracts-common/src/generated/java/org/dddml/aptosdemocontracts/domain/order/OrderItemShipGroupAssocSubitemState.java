// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.aptosdemocontracts.domain.order;

import java.util.*;
import java.math.*;
import org.dddml.aptosdemocontracts.domain.*;
import java.util.Date;
import org.dddml.aptosdemocontracts.specialization.Event;

public interface OrderItemShipGroupAssocSubitemState
{
    Long VERSION_ZERO = 0L;

    Long VERSION_NULL = VERSION_ZERO - 1;

    Day getOrderItemShipGroupAssocSubitemDay();

    String getDescription();

    Long getOffChainVersion();

    String getCreatedBy();

    Date getCreatedAt();

    String getUpdatedBy();

    Date getUpdatedAt();

    Boolean getActive();

    Boolean getDeleted();

    String getOrderId();

    Integer getOrderShipGroupShipGroupSeqId();

    String getOrderItemShipGroupAssociationProductId();

    interface MutableOrderItemShipGroupAssocSubitemState extends OrderItemShipGroupAssocSubitemState {
        void setOrderItemShipGroupAssocSubitemDay(Day orderItemShipGroupAssocSubitemDay);

        void setDescription(String description);

        void setOffChainVersion(Long offChainVersion);

        void setCreatedBy(String createdBy);

        void setCreatedAt(Date createdAt);

        void setUpdatedBy(String updatedBy);

        void setUpdatedAt(Date updatedAt);

        void setActive(Boolean active);

        void setDeleted(Boolean deleted);

        void setOrderId(String orderId);

        void setOrderShipGroupShipGroupSeqId(Integer orderShipGroupShipGroupSeqId);

        void setOrderItemShipGroupAssociationProductId(String orderItemShipGroupAssociationProductId);


        void mutate(Event e);

        //void when(OrderItemShipGroupAssocSubitemEvent.OrderItemShipGroupAssocSubitemStateCreated e);

        //void when(OrderItemShipGroupAssocSubitemEvent.OrderItemShipGroupAssocSubitemStateMergePatched e);

        //void when(OrderItemShipGroupAssocSubitemEvent.OrderItemShipGroupAssocSubitemStateRemoved e);
    }

    interface SqlOrderItemShipGroupAssocSubitemState extends MutableOrderItemShipGroupAssocSubitemState {
        OrderItemShipGroupAssocSubitemId getOrderItemShipGroupAssocSubitemId();

        void setOrderItemShipGroupAssocSubitemId(OrderItemShipGroupAssocSubitemId orderItemShipGroupAssocSubitemId);


        boolean isStateUnsaved();

        boolean getForReapplying();
    }
}

