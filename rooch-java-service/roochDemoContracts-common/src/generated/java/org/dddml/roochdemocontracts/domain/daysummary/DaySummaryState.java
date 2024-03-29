// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.domain.daysummary;

import java.util.*;
import java.math.*;
import org.dddml.roochdemocontracts.domain.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.roochdemocontracts.specialization.Event;

public interface DaySummaryState extends VersionedRoochMoveObject
{
    Long VERSION_ZERO = 0L;

    Long VERSION_NULL = VERSION_ZERO - 1;

    Day getDay();

    String getId_();

    String getDescription();

    String getMetadata();

    String getOptionalData();

    Long getOffChainVersion();

    String getCreatedBy();

    Date getCreatedAt();

    String getUpdatedBy();

    Date getUpdatedAt();

    Boolean getActive();

    Boolean getDeleted();

    List<String> getArrayData();

    List<Integer> getU16ArrayData();

    List<Long> getU32ArrayData();

    Set<BigInteger> getU64ArrayData();

    Set<BigInteger> getU128ArrayData();

    List<BigInteger> getU256ArrayData();

    interface MutableDaySummaryState extends DaySummaryState, VersionedRoochMoveObject.MutableVersionedRoochMoveObject {
        void setDay(Day day);

        void setId_(String id);

        void setDescription(String description);

        void setMetadata(String metadata);

        void setOptionalData(String optionalData);

        void setOffChainVersion(Long offChainVersion);

        void setCreatedBy(String createdBy);

        void setCreatedAt(Date createdAt);

        void setUpdatedBy(String updatedBy);

        void setUpdatedAt(Date updatedAt);

        void setActive(Boolean active);

        void setDeleted(Boolean deleted);

        void setArrayData(List<String> arrayData);

        void setU16ArrayData(List<Integer> u16ArrayData);

        void setU32ArrayData(List<Long> u32ArrayData);

        void setU64ArrayData(Set<BigInteger> u64ArrayData);

        void setU128ArrayData(Set<BigInteger> u128ArrayData);

        void setU256ArrayData(List<BigInteger> u256ArrayData);


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

