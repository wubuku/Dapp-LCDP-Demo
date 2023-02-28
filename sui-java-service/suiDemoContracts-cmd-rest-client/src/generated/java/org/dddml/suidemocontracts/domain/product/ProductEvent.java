package org.dddml.suidemocontracts.domain.product;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.Event;

public interface ProductEvent extends Event {

    interface SqlProductEvent extends ProductEvent {
        ProductEventId getProductEventId();

        boolean getEventReadOnly();

        void setEventReadOnly(boolean readOnly);
    }

    String getProductId();

    //void setProductId(String productId);

    Long getVersion();
    
    //void setVersion(Long version);

    String getCreatedBy();

    void setCreatedBy(String createdBy);

    Date getCreatedAt();

    void setCreatedAt(Date createdAt);

    String getCommandId();

    void setCommandId(String commandId);

    interface ProductStateEvent extends ProductEvent {
        String getName();

        void setName(String name);

        BigInteger getUnitPrice();

        void setUnitPrice(BigInteger unitPrice);

        Boolean getActive();

        void setActive(Boolean active);

    }

    interface ProductStateCreated extends ProductStateEvent
    {
    
    }


}

