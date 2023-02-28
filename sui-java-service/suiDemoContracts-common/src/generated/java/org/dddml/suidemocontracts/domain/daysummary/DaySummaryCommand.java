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
        if ((c instanceof DaySummaryCommand.CreateDaySummary) 
            && (COMMAND_TYPE_CREATE.equals(c.getCommandType()) || c.getVersion().equals(DaySummaryState.VERSION_NULL)))
            return true;
        if ((c instanceof DaySummaryCommand.MergePatchDaySummary))
            return false;
        if ((c instanceof DaySummaryCommand.DeleteDaySummary))
            return false;
        if (c.getVersion().equals(DaySummaryState.VERSION_NULL))
            return true;
        return false;
    }

    interface CreateOrMergePatchDaySummary extends DaySummaryCommand
    {

        String getDescription();

        void setDescription(String description);

        int[] getMetadata();

        void setMetadata(int[] metadata);

        int[] getOptionalData();

        void setOptionalData(int[] optionalData);

        Boolean getActive();

        void setActive(Boolean active);

        String[] getArrayData();

        void setArrayData(String[] arrayData);

    }

    interface CreateDaySummary extends CreateOrMergePatchDaySummary
    {
    }

    interface MergePatchDaySummary extends CreateOrMergePatchDaySummary
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

	interface DeleteDaySummary extends DaySummaryCommand
	{
	}

}

