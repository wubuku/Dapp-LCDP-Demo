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

    String getCreatedBy();

    void setCreatedBy(String createdBy);

    Date getCreatedAt();

    void setCreatedAt(Date createdAt);

    String getCommandId();

    void setCommandId(String commandId);

    interface OrderV2StateEvent extends OrderV2Event {
        BigInteger getTotalAmount();

        void setTotalAmount(BigInteger totalAmount);

        Day getEstimatedShipDate();

        void setEstimatedShipDate(Day estimatedShipDate);

        Boolean getActive();

        void setActive(Boolean active);

    }

    interface OrderV2StateCreated extends OrderV2StateEvent
    {
        Iterable<OrderV2ItemEvent.OrderV2ItemStateCreated> getOrderV2ItemEvents();
        
        void addOrderV2ItemEvent(OrderV2ItemEvent.OrderV2ItemStateCreated e);

        OrderV2ItemEvent.OrderV2ItemStateCreated newOrderV2ItemStateCreated(String productId);

    
    }


    interface OrderV2StateMergePatched extends OrderV2StateEvent
    {
        Boolean getIsPropertyTotalAmountRemoved();

        void setIsPropertyTotalAmountRemoved(Boolean removed);

        Boolean getIsPropertyEstimatedShipDateRemoved();

        void setIsPropertyEstimatedShipDateRemoved(Boolean removed);

        Boolean getIsPropertyActiveRemoved();

        void setIsPropertyActiveRemoved(Boolean removed);


        Iterable<OrderV2ItemEvent> getOrderV2ItemEvents();
        
        void addOrderV2ItemEvent(OrderV2ItemEvent e);

        OrderV2ItemEvent.OrderV2ItemStateCreated newOrderV2ItemStateCreated(String productId);

        OrderV2ItemEvent.OrderV2ItemStateMergePatched newOrderV2ItemStateMergePatched(String productId);

        OrderV2ItemEvent.OrderV2ItemStateRemoved newOrderV2ItemStateRemoved(String productId);


    }

    interface OrderV2StateDeleted extends OrderV2StateEvent
    {
        Iterable<OrderV2ItemEvent.OrderV2ItemStateRemoved> getOrderV2ItemEvents();
        
        void addOrderV2ItemEvent(OrderV2ItemEvent.OrderV2ItemStateRemoved e);
        
        OrderV2ItemEvent.OrderV2ItemStateRemoved newOrderV2ItemStateRemoved(String productId);

    }


}

