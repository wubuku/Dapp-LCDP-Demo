package org.dddml.suidemocontracts.domain.orderv2;

import java.math.BigInteger;
import java.util.Set;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.Event;

public interface OrderItemShipGroupAssociationState
{
    Long VERSION_ZERO = 0L;

    Long VERSION_NULL = VERSION_ZERO - 1;

    String getProductId();

    BigInteger getQuantity();

    BigInteger getCancelQuantity();

    Long getOffChainVersion();

    String getCreatedBy();

    Date getCreatedAt();

    String getUpdatedBy();

    Date getUpdatedAt();

    Boolean getActive();

    Boolean getDeleted();

    String getOrderV2OrderId();

    Integer getOrderShipGroupShipGroupSeqId();

    interface MutableOrderItemShipGroupAssociationState extends OrderItemShipGroupAssociationState {
        void setProductId(String productId);

        void setQuantity(BigInteger quantity);

        void setCancelQuantity(BigInteger cancelQuantity);

        void setOffChainVersion(Long offChainVersion);

        void setCreatedBy(String createdBy);

        void setCreatedAt(Date createdAt);

        void setUpdatedBy(String updatedBy);

        void setUpdatedAt(Date updatedAt);

        void setActive(Boolean active);

        void setDeleted(Boolean deleted);

        void setOrderV2OrderId(String orderV2OrderId);

        void setOrderShipGroupShipGroupSeqId(Integer orderShipGroupShipGroupSeqId);


        void mutate(Event e);

        //void when(OrderItemShipGroupAssociationEvent.OrderItemShipGroupAssociationStateCreated e);

        //void when(OrderItemShipGroupAssociationEvent.OrderItemShipGroupAssociationStateMergePatched e);

        //void when(OrderItemShipGroupAssociationEvent.OrderItemShipGroupAssociationStateRemoved e);
    }

    interface SqlOrderItemShipGroupAssociationState extends MutableOrderItemShipGroupAssociationState {
        OrderV2OrderItemShipGroupAssociationId getOrderV2OrderItemShipGroupAssociationId();

        void setOrderV2OrderItemShipGroupAssociationId(OrderV2OrderItemShipGroupAssociationId orderV2OrderItemShipGroupAssociationId);


        boolean isStateUnsaved();

        boolean getForReapplying();
    }
}

