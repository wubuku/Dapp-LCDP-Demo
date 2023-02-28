package org.dddml.suidemocontracts.domain.daysummary;


public class DeleteDaySummaryDto extends AbstractDaySummaryCommandDto implements DaySummaryCommand.DeleteDaySummary
{

    public DeleteDaySummaryDto() {
        this.commandType = COMMAND_TYPE_DELETE;
    }

    @Override
    public String getCommandType() {
        return COMMAND_TYPE_DELETE;
    }

    public DaySummaryCommand.DeleteDaySummary toDeleteDaySummary()
    {
        AbstractDaySummaryCommand.SimpleDeleteDaySummary command = new AbstractDaySummaryCommand.SimpleDeleteDaySummary();
        ((AbstractDaySummaryCommandDto)this).copyTo(command);
        return command;
    }
}

