package org.dddml.suidemocontracts.domain.product;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.Event;

public interface ProductEvent extends Event, SuiEventEnvelope, SuiMoveEvent, HasSuiEventNextCursor {

    interface SqlProductEvent extends ProductEvent {
        ProductEventId getProductEventId();

        boolean getEventReadOnly();

        void setEventReadOnly(boolean readOnly);
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

