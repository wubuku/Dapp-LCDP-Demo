// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.suidemocontracts.domain.order;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.Event;

public interface OrderEvent extends Event, SuiEventEnvelope, SuiMoveEvent, HasStatus {

    interface SqlOrderEvent extends OrderEvent {
        OrderEventId getOrderEventId();

        boolean getEventReadOnly();

        void setEventReadOnly(boolean readOnly);
    }

    interface OrderCreated extends OrderEvent {
        String getProduct();

        void setProduct(String value);

        BigInteger getQuantity();

        void setQuantity(BigInteger value);

        BigInteger getUnitPrice();

        void setUnitPrice(BigInteger value);

        BigInteger getTotalAmount();

        void setTotalAmount(BigInteger value);

        String getOwner();

        void setOwner(String value);

    }

    interface OrderItemRemoved extends OrderEvent {
        String getProductId();

        void setProductId(String value);

    }

    interface OrderItemQuantityUpdated extends OrderEvent {
        String getProductId();

        void setProductId(String value);

        BigInteger getQuantity();

        void setQuantity(BigInteger value);

    }

    interface OrderDeleted extends OrderEvent {
    }

    String getId();

    //void setId(String id);

    BigInteger getVersion();
    
    //void setVersion(BigInteger version);

    String getCreatedBy();

    void setCreatedBy(String createdBy);

    Date getCreatedAt();

    void setCreatedAt(Date createdAt);

    String getCommandId();

    void setCommandId(String commandId);


}

