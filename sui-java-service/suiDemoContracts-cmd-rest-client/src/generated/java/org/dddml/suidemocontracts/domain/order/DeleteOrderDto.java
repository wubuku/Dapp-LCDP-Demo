package org.dddml.suidemocontracts.domain.order;


public class DeleteOrderDto extends AbstractOrderCommandDto
{

    public DeleteOrderDto() {
        this.commandType = COMMAND_TYPE_DELETE;
    }

    @Override
    public String getCommandType() {
        return COMMAND_TYPE_DELETE;
    }

}

