// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.suidemocontracts.domain.daysummary;

import java.util.*;
import java.math.*;
import org.dddml.suidemocontracts.domain.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.specialization.Event;

public interface DaySummaryState extends VersionedSuiMoveObject
{
    Long VERSION_ZERO = 0L;

    Long VERSION_NULL = VERSION_ZERO - 1;

    Day getDay();

    String getId_();

    String getDescription();

    int[] getMetadata();

    int[] getOptionalData();

    Long getOffChainVersion();

    String getCreatedBy();

    Date getCreatedAt();

    String getUpdatedBy();

    Date getUpdatedAt();

    Boolean getActive();

    Boolean getDeleted();

    List<String> getArrayData();

    interface MutableDaySummaryState extends DaySummaryState, VersionedSuiMoveObject.MutableVersionedSuiMoveObject {
        void setDay(Day day);

        void setId_(String id);

        void setDescription(String description);

        void setMetadata(int[] metadata);

        void setOptionalData(int[] optionalData);

        void setOffChainVersion(Long offChainVersion);

        void setCreatedBy(String createdBy);

        void setCreatedAt(Date createdAt);

        void setUpdatedBy(String updatedBy);

        void setUpdatedAt(Date updatedAt);

        void setActive(Boolean active);

        void setDeleted(Boolean deleted);

        void setArrayData(List<String> arrayData);


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

