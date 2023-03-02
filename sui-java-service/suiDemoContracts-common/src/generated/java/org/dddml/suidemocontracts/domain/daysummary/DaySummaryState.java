package org.dddml.suidemocontracts.domain.daysummary;

import java.util.Set;
import org.dddml.suidemocontracts.domain.*;
import java.util.Date;
import org.dddml.suidemocontracts.specialization.Event;

public interface DaySummaryState
{
    Long VERSION_ZERO = 0L;

    Long VERSION_NULL = VERSION_ZERO - 1;

    Day getDay();

    String getId();

    String getDescription();

    int[] getMetadata();

    int[] getOptionalData();

    Long getVersion();

    String getCreatedBy();

    Date getCreatedAt();

    String getUpdatedBy();

    Date getUpdatedAt();

    Boolean getActive();

    Boolean getDeleted();

    Set<String> getArrayData();

    interface MutableDaySummaryState extends DaySummaryState {
        void setDay(Day day);

        void setId(String id);

        void setDescription(String description);

        void setMetadata(int[] metadata);

        void setOptionalData(int[] optionalData);

        void setVersion(Long version);

        void setCreatedBy(String createdBy);

        void setCreatedAt(Date createdAt);

        void setUpdatedBy(String updatedBy);

        void setUpdatedAt(Date updatedAt);

        void setActive(Boolean active);

        void setDeleted(Boolean deleted);

        void setArrayData(Set<String> arrayData);


        void mutate(Event e);

        //void when(DaySummaryEvent.DaySummaryStateCreated e);

        //void when(DaySummaryEvent.DaySummaryStateMergePatched e);

        //void when(DaySummaryEvent.DaySummaryStateDeleted e);
    }

    interface SqlDaySummaryState extends MutableDaySummaryState {

        boolean isStateUnsaved();

        boolean getForReapplying();
    }
}

