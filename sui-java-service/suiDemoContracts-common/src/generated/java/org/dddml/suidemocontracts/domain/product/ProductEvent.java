// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.suidemocontracts.domain.product;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.Event;

public interface ProductEvent extends Event, SuiEventEnvelope, SuiMoveEvent, HasStatus {

    interface SqlProductEvent extends ProductEvent {
        ProductEventId getProductEventId();

        boolean getEventReadOnly();

        void setEventReadOnly(boolean readOnly);
    }

    interface ProductCrudEvent extends ProductEvent {
        Integer getCrudType();

        void setCrudType(Integer value);

        String getId();

        void setId(String value);

        String getName();

        void setName(String value);

        BigInteger getUnitPrice();

        void setUnitPrice(BigInteger value);

        String getOwner();

        void setOwner(String value);

    }

    String getProductId();

    //void setProductId(String productId);

    BigInteger getVersion();
    
    //void setVersion(BigInteger version);

    String getId_();
    
    //void setId_(String id);

    String getCreatedBy();

    void setCreatedBy(String createdBy);

    Date getCreatedAt();

    void setCreatedAt(Date createdAt);

    String getCommandId();

    void setCommandId(String commandId);


}

