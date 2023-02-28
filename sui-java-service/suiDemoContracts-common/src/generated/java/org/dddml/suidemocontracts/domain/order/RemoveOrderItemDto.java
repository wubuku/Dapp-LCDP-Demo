package org.dddml.suidemocontracts.domain.order;


public class RemoveOrderItemDto extends CreateOrMergePatchOrderItemDto implements OrderItemCommand.RemoveOrderItem
{

    public RemoveOrderItemDto() {
        this.commandType = COMMAND_TYPE_REMOVE;
    }

    @Override
    public String getCommandType() {
        return COMMAND_TYPE_REMOVE;
    }

    public OrderItemCommand.RemoveOrderItem toRemoveOrderItem()
    {
        AbstractOrderItemCommand.SimpleRemoveOrderItem command = new AbstractOrderItemCommand.SimpleRemoveOrderItem();
        ((AbstractOrderItemCommandDto)this).copyTo(command);
        return command;
    }
}

