package org.dddml.suidemocontracts.domain.order;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.Event;

public interface OrderEvent extends Event {

    interface SqlOrderEvent extends OrderEvent {
        OrderEventId getOrderEventId();

        boolean getEventReadOnly();

        void setEventReadOnly(boolean readOnly);
    }

    String getId();

    //void setId(String id);

    Long getVersion();
    
    //void setVersion(Long version);

    String getCreatedBy();

    void setCreatedBy(String createdBy);

    Date getCreatedAt();

    void setCreatedAt(Date createdAt);

    String getCommandId();

    void setCommandId(String commandId);

    interface OrderStateEvent extends OrderEvent {
        BigInteger getTotalAmount();

        void setTotalAmount(BigInteger totalAmount);

        Boolean getActive();

        void setActive(Boolean active);

    }

    interface OrderStateCreated extends OrderStateEvent
    {
        Iterable<OrderItemEvent.OrderItemStateCreated> getOrderItemEvents();
        
        void addOrderItemEvent(OrderItemEvent.OrderItemStateCreated e);

        OrderItemEvent.OrderItemStateCreated newOrderItemStateCreated(String productId);

    
    }


    interface OrderStateMergePatched extends OrderStateEvent
    {
        Boolean getIsPropertyTotalAmountRemoved();

        void setIsPropertyTotalAmountRemoved(Boolean removed);

        Boolean getIsPropertyActiveRemoved();

        void setIsPropertyActiveRemoved(Boolean removed);


        Iterable<OrderItemEvent> getOrderItemEvents();
        
        void addOrderItemEvent(OrderItemEvent e);

        OrderItemEvent.OrderItemStateCreated newOrderItemStateCreated(String productId);

        OrderItemEvent.OrderItemStateMergePatched newOrderItemStateMergePatched(String productId);

        OrderItemEvent.OrderItemStateRemoved newOrderItemStateRemoved(String productId);


    }

    interface OrderStateDeleted extends OrderStateEvent
    {
        Iterable<OrderItemEvent.OrderItemStateRemoved> getOrderItemEvents();
        
        void addOrderItemEvent(OrderItemEvent.OrderItemStateRemoved e);
        
        OrderItemEvent.OrderItemStateRemoved newOrderItemStateRemoved(String productId);

    }


}

