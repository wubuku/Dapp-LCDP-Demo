// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.suidemocontracts.domain.daysummary;

import java.util.List;
import org.dddml.suidemocontracts.domain.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.specialization.Event;
import org.dddml.suidemocontracts.domain.Command;

public interface DaySummaryAggregate {
    DaySummaryState getState();

    List<Event> getChanges();

    void create(String description, int[] metaData, String[] arrayData, int[] optionalData, Long offChainVersion, String commandId, String requesterId, DaySummaryCommands.Create c);

    void delete(Long offChainVersion, String commandId, String requesterId, DaySummaryCommands.Delete c);

    void throwOnInvalidStateTransition(Command c);
}

