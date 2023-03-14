package org.dddml.suidemocontracts.domain.orderv2;

import java.util.Set;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.Event;

public interface OrderShipGroupState
{
    Long VERSION_ZERO = 0L;

    Long VERSION_NULL = VERSION_ZERO - 1;

    Integer getShipGroupSeqId();

    String getShipmentMethod();

    Long getOffChainVersion();

    String getCreatedBy();

    Date getCreatedAt();

    String getUpdatedBy();

    Date getUpdatedAt();

    Boolean getActive();

    Boolean getDeleted();

    String getOrderV2OrderId();

    EntityStateCollection<String, OrderItemShipGroupAssociationState> getOrderItemShipGroupAssociations();

    interface MutableOrderShipGroupState extends OrderShipGroupState {
        void setShipGroupSeqId(Integer shipGroupSeqId);

        void setShipmentMethod(String shipmentMethod);

        void setOffChainVersion(Long offChainVersion);

        void setCreatedBy(String createdBy);

        void setCreatedAt(Date createdAt);

        void setUpdatedBy(String updatedBy);

        void setUpdatedAt(Date updatedAt);

        void setActive(Boolean active);

        void setDeleted(Boolean deleted);

        void setOrderV2OrderId(String orderV2OrderId);


        void mutate(Event e);

        //void when(OrderShipGroupEvent.OrderShipGroupStateCreated e);

        //void when(OrderShipGroupEvent.OrderShipGroupStateMergePatched e);

        //void when(OrderShipGroupEvent.OrderShipGroupStateRemoved e);
    }

    interface SqlOrderShipGroupState extends MutableOrderShipGroupState {
        OrderV2OrderShipGroupId getOrderV2OrderShipGroupId();

        void setOrderV2OrderShipGroupId(OrderV2OrderShipGroupId orderV2OrderShipGroupId);


        boolean isStateUnsaved();

        boolean getForReapplying();
    }
}

