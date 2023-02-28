package org.dddml.suidemocontracts.domain.daysummary;

import java.util.List;
import org.dddml.suidemocontracts.domain.*;
import java.util.Date;
import org.dddml.suidemocontracts.specialization.Event;
import org.dddml.suidemocontracts.domain.Command;

public interface DaySummaryAggregate
{
    DaySummaryState getState();

    List<Event> getChanges();

    void create(DaySummaryCommand.CreateDaySummary c);

    void mergePatch(DaySummaryCommand.MergePatchDaySummary c);

    void delete(DaySummaryCommand.DeleteDaySummary c);

    void throwOnInvalidStateTransition(Command c);
}

