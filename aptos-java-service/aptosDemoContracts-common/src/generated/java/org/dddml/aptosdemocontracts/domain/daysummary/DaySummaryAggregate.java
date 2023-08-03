// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.aptosdemocontracts.domain.daysummary;

import java.util.List;
import org.dddml.aptosdemocontracts.domain.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.aptosdemocontracts.specialization.Event;
import org.dddml.aptosdemocontracts.domain.Command;

public interface DaySummaryAggregate {
    DaySummaryState getState();

    List<Event> getChanges();

    void create(String description, int[] metaData, String[] arrayData, String optionalData, Integer[] u16ArrayData, Long[] u32ArrayData, BigInteger[] u64ArrayData, BigInteger[] u128ArrayData, BigInteger[] u256ArrayData, Long offChainVersion, String commandId, String requesterId, DaySummaryCommands.Create c);

    void throwOnInvalidStateTransition(Command c);
}

