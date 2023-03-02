package org.dddml.suidemocontracts.domain.daysummary;

import java.util.*;
import org.dddml.suidemocontracts.domain.*;
import java.util.Date;
import org.dddml.suidemocontracts.domain.Command;
import org.dddml.suidemocontracts.specialization.DomainError;

public interface DaySummaryCommand extends Command
{

    Day getDay();

    void setDay(Day day);

    String getId();

    void setId(String id);

    Long getVersion();

    void setVersion(Long version);

    static void throwOnInvalidStateTransition(DaySummaryState state, Command c) {
        if (state.getVersion() == null) {
            if (isCommandCreate((DaySummaryCommand)c)) {
                return;
            }
            throw DomainError.named("premature", "Can't do anything to unexistent aggregate");
        }
        if (state.getDeleted() != null && state.getDeleted()) {
            throw DomainError.named("zombie", "Can't do anything to deleted aggregate.");
        }
        if (isCommandCreate((DaySummaryCommand)c))
            throw DomainError.named("rebirth", "Can't create aggregate that already exists");
    }

    static boolean isCommandCreate(DaySummaryCommand c) {
        if (c.getVersion().equals(DaySummaryState.VERSION_NULL))
            return true;
        return false;
    }

}

