package org.dddml.suidemocontracts.domain.orderv2;


public class RemoveOrderV2ItemDto extends CreateOrMergePatchOrderV2ItemDto implements OrderV2ItemCommand.RemoveOrderV2Item
{

    public RemoveOrderV2ItemDto() {
        this.commandType = COMMAND_TYPE_REMOVE;
    }

    @Override
    public String getCommandType() {
        return COMMAND_TYPE_REMOVE;
    }

    public OrderV2ItemCommand.RemoveOrderV2Item toRemoveOrderV2Item()
    {
        AbstractOrderV2ItemCommand.SimpleRemoveOrderV2Item command = new AbstractOrderV2ItemCommand.SimpleRemoveOrderV2Item();
        ((AbstractOrderV2ItemCommandDto)this).copyTo(command);
        return command;
    }
}

