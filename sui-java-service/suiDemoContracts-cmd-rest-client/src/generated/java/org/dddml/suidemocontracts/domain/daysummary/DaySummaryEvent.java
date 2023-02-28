package org.dddml.suidemocontracts.domain.daysummary;

import java.util.*;
import org.dddml.suidemocontracts.domain.*;
import java.util.Date;
import org.dddml.suidemocontracts.specialization.Event;

public interface DaySummaryEvent extends Event {

    interface SqlDaySummaryEvent extends DaySummaryEvent {
        DaySummaryEventId getDaySummaryEventId();

        boolean getEventReadOnly();

        void setEventReadOnly(boolean readOnly);
    }

    Day getDay();

    //void setDay(Day day);

    Long getVersion();
    
    //void setVersion(Long version);

    String getCreatedBy();

    void setCreatedBy(String createdBy);

    Date getCreatedAt();

    void setCreatedAt(Date createdAt);

    String getCommandId();

    void setCommandId(String commandId);

    interface DaySummaryStateEvent extends DaySummaryEvent {
        String getDescription();

        void setDescription(String description);

        int[] getMetadata();

        void setMetadata(int[] metadata);

        int[] getOptionalData();

        void setOptionalData(int[] optionalData);

        Boolean getActive();

        void setActive(Boolean active);

        Set<String> getArrayData();

        //void setArrayData(Set<String> arrayData);

    }

    interface DaySummaryStateCreated extends DaySummaryStateEvent
    {
    
    }


    interface DaySummaryStateMergePatched extends DaySummaryStateEvent
    {
        Boolean getIsPropertyDescriptionRemoved();

        void setIsPropertyDescriptionRemoved(Boolean removed);

        Boolean getIsPropertyMetadataRemoved();

        void setIsPropertyMetadataRemoved(Boolean removed);

        Boolean getIsPropertyArrayDataRemoved();

        void setIsPropertyArrayDataRemoved(Boolean removed);

        Boolean getIsPropertyOptionalDataRemoved();

        void setIsPropertyOptionalDataRemoved(Boolean removed);

        Boolean getIsPropertyActiveRemoved();

        void setIsPropertyActiveRemoved(Boolean removed);



    }

    interface DaySummaryStateDeleted extends DaySummaryStateEvent
    {
    }


}

