package org.dddml.suidemocontracts.domain.orderv2;


public class RemoveOrderV2ItemDto extends CreateOrMergePatchOrderV2ItemDto
{

    public RemoveOrderV2ItemDto() {
        this.commandType = COMMAND_TYPE_REMOVE;
    }

    @Override
    public String getCommandType() {
        return COMMAND_TYPE_REMOVE;
    }

}

