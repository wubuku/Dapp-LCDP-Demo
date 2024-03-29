// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.aptosdemocontracts.domain.product;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.aptosdemocontracts.domain.*;
import org.dddml.aptosdemocontracts.specialization.Event;

public interface ProductEvent extends Event, AptosEvent, HasStatus {

    interface SqlProductEvent extends ProductEvent {
        ProductEventId getProductEventId();

        boolean getEventReadOnly();

        void setEventReadOnly(boolean readOnly);
    }

    String getProductId();

    //void setProductId(String productId);

    BigInteger getVersion();
    
    //void setVersion(BigInteger version);

    String getCreatedBy();

    void setCreatedBy(String createdBy);

    Date getCreatedAt();

    void setCreatedAt(Date createdAt);

    String getCommandId();

    void setCommandId(String commandId);


}

