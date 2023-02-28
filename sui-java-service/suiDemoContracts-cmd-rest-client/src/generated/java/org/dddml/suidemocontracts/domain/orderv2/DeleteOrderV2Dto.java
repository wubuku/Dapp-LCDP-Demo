package org.dddml.suidemocontracts.domain.orderv2;


public class DeleteOrderV2Dto extends AbstractOrderV2CommandDto
{

    public DeleteOrderV2Dto() {
        this.commandType = COMMAND_TYPE_DELETE;
    }

    @Override
    public String getCommandType() {
        return COMMAND_TYPE_DELETE;
    }

}

