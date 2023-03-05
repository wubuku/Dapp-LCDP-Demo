package org.dddml.suidemocontracts.domain.daysummary;

import java.util.*;
import org.dddml.suidemocontracts.domain.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.specialization.Event;

public interface DaySummaryEvent extends Event, VersionedSuiMoveEvent {

    interface SqlDaySummaryEvent extends DaySummaryEvent {
        DaySummaryEventId getDaySummaryEventId();

        boolean getEventReadOnly();

        void setEventReadOnly(boolean readOnly);
    }

    Day getDay();

    //void setDay(Day day);

    Long getOffChainVersion();
    
    //void setOffChainVersion(Long offChainVersion);

    String getId_();
    
    //void setId_(String id);

    String getCreatedBy();

    void setCreatedBy(String createdBy);

    Date getCreatedAt();

    void setCreatedAt(Date createdAt);

    String getCommandId();

    void setCommandId(String commandId);


}

