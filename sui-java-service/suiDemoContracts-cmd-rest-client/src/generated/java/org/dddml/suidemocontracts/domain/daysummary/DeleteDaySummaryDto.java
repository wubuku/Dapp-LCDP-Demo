package org.dddml.suidemocontracts.domain.daysummary;


public class DeleteDaySummaryDto extends AbstractDaySummaryCommandDto
{

    public DeleteDaySummaryDto() {
        this.commandType = COMMAND_TYPE_DELETE;
    }

    @Override
    public String getCommandType() {
        return COMMAND_TYPE_DELETE;
    }

}

