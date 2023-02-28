package org.dddml.suidemocontracts.domain.orderv2;


public class DeleteOrderV2Dto extends AbstractOrderV2CommandDto implements OrderV2Command.DeleteOrderV2
{

    public DeleteOrderV2Dto() {
        this.commandType = COMMAND_TYPE_DELETE;
    }

    @Override
    public String getCommandType() {
        return COMMAND_TYPE_DELETE;
    }

    public OrderV2Command.DeleteOrderV2 toDeleteOrderV2()
    {
        AbstractOrderV2Command.SimpleDeleteOrderV2 command = new AbstractOrderV2Command.SimpleDeleteOrderV2();
        ((AbstractOrderV2CommandDto)this).copyTo(command);
        return command;
    }
}

