package org.dddml.suidemocontracts.domain.order;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.Event;

public interface OrderEvent extends Event, VersionedSuiMoveEvent {

    interface SqlOrderEvent extends OrderEvent {
        OrderEventId getOrderEventId();

        boolean getEventReadOnly();

        void setEventReadOnly(boolean readOnly);
    }

    String getId();

    //void setId(String id);

    Long getOffChainVersion();
    
    //void setOffChainVersion(Long offChainVersion);

    String getCreatedBy();

    void setCreatedBy(String createdBy);

    Date getCreatedAt();

    void setCreatedAt(Date createdAt);

    String getCommandId();

    void setCommandId(String commandId);


}

