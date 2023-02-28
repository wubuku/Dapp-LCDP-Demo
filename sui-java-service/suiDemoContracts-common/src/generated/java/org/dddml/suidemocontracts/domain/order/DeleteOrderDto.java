package org.dddml.suidemocontracts.domain.order;


public class DeleteOrderDto extends AbstractOrderCommandDto implements OrderCommand.DeleteOrder
{

    public DeleteOrderDto() {
        this.commandType = COMMAND_TYPE_DELETE;
    }

    @Override
    public String getCommandType() {
        return COMMAND_TYPE_DELETE;
    }

    public OrderCommand.DeleteOrder toDeleteOrder()
    {
        AbstractOrderCommand.SimpleDeleteOrder command = new AbstractOrderCommand.SimpleDeleteOrder();
        ((AbstractOrderCommandDto)this).copyTo(command);
        return command;
    }
}

