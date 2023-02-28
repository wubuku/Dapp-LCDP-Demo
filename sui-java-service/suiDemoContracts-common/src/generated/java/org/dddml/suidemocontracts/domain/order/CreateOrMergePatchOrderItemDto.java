package org.dddml.suidemocontracts.domain.order;

import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;

public class CreateOrMergePatchOrderItemDto extends AbstractOrderItemCommandDto implements OrderItemCommand.CreateOrMergePatchOrderItem
{

    /**
     * Quantity
     */
    private BigInteger quantity;

    public BigInteger getQuantity()
    {
        return this.quantity;
    }

    public void setQuantity(BigInteger quantity)
    {
        this.quantity = quantity;
    }

    /**
     * Item Amount
     */
    private BigInteger itemAmount;

    public BigInteger getItemAmount()
    {
        return this.itemAmount;
    }

    public void setItemAmount(BigInteger itemAmount)
    {
        this.itemAmount = itemAmount;
    }

    /**
     * Active
     */
    private Boolean active;

    public Boolean getActive()
    {
        return this.active;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
    }


    private Boolean isPropertyQuantityRemoved;

    public Boolean getIsPropertyQuantityRemoved()
    {
        return this.isPropertyQuantityRemoved;
    }

    public void setIsPropertyQuantityRemoved(Boolean removed)
    {
        this.isPropertyQuantityRemoved = removed;
    }

    private Boolean isPropertyItemAmountRemoved;

    public Boolean getIsPropertyItemAmountRemoved()
    {
        return this.isPropertyItemAmountRemoved;
    }

    public void setIsPropertyItemAmountRemoved(Boolean removed)
    {
        this.isPropertyItemAmountRemoved = removed;
    }

    private Boolean isPropertyActiveRemoved;

    public Boolean getIsPropertyActiveRemoved()
    {
        return this.isPropertyActiveRemoved;
    }

    public void setIsPropertyActiveRemoved(Boolean removed)
    {
        this.isPropertyActiveRemoved = removed;
    }

    public void copyTo(CreateOrMergePatchOrderItem command)
    {
        ((AbstractOrderItemCommandDto) this).copyTo(command);
        command.setQuantity(this.getQuantity());
        command.setItemAmount(this.getItemAmount());
        command.setActive(this.getActive());
    }

    public OrderItemCommand toCommand()
    {
        if (getCommandType() == null) {
            setCommandType(COMMAND_TYPE_MERGE_PATCH);
        }
        if (COMMAND_TYPE_CREATE.equals(getCommandType())) {
            AbstractOrderItemCommand.SimpleCreateOrderItem command = new AbstractOrderItemCommand.SimpleCreateOrderItem();
            copyTo((AbstractOrderItemCommand.AbstractCreateOrderItem) command);
            return command;
        } else if (COMMAND_TYPE_MERGE_PATCH.equals(getCommandType())) {
            AbstractOrderItemCommand.SimpleMergePatchOrderItem command = new AbstractOrderItemCommand.SimpleMergePatchOrderItem();
            copyTo((AbstractOrderItemCommand.SimpleMergePatchOrderItem) command);
            return command;
        } 
        else if (COMMAND_TYPE_REMOVE.equals(getCommandType())) {
            AbstractOrderItemCommand.SimpleRemoveOrderItem command = new AbstractOrderItemCommand.SimpleRemoveOrderItem();
            ((AbstractOrderItemCommandDto) this).copyTo(command);
            return command;
        }
        throw new UnsupportedOperationException("Unknown command type:" + getCommandType());
    }


    public OrderItemCommand toSubclass() {
        if (getCommandType() == null) {
            setCommandType(COMMAND_TYPE_MERGE_PATCH);
        }
        if (COMMAND_TYPE_CREATE.equals(getCommandType()) || null == getCommandType()) {
            CreateOrderItemDto command = new CreateOrderItemDto();
            copyTo((CreateOrderItem) command);
            return command;
        } else if (COMMAND_TYPE_MERGE_PATCH.equals(getCommandType())) {
            MergePatchOrderItemDto command = new MergePatchOrderItemDto();
            copyTo((MergePatchOrderItem) command);
            return command;
        } 
        else if (COMMAND_TYPE_REMOVE.equals(getCommandType())) {
            RemoveOrderItemDto command = new RemoveOrderItemDto();
            ((AbstractOrderItemCommandDto) this).copyTo(command);
            return command;
        }
        throw new UnsupportedOperationException("Unknown command type:" + getCommandType());
    }

    public void copyTo(CreateOrderItem command)
    {
        copyTo((CreateOrMergePatchOrderItem) command);
    }

    public void copyTo(MergePatchOrderItem command)
    {
        copyTo((CreateOrMergePatchOrderItem) command);
        command.setIsPropertyQuantityRemoved(this.getIsPropertyQuantityRemoved());
        command.setIsPropertyItemAmountRemoved(this.getIsPropertyItemAmountRemoved());
        command.setIsPropertyActiveRemoved(this.getIsPropertyActiveRemoved());
    }

    public static class CreateOrderItemDto extends CreateOrMergePatchOrderItemDto implements OrderItemCommand.CreateOrderItem
    {
        public CreateOrderItemDto() {
            this.commandType = COMMAND_TYPE_CREATE;
        }

        @Override
        public String getCommandType() {
            return COMMAND_TYPE_CREATE;
        }
        public OrderItemCommand.CreateOrderItem toCreateOrderItem()
        {
            return (OrderItemCommand.CreateOrderItem) toCommand();
        }

    }

    public static class MergePatchOrderItemDto extends CreateOrMergePatchOrderItemDto implements OrderItemCommand.MergePatchOrderItem
    {
        public MergePatchOrderItemDto() {
            this.commandType = COMMAND_TYPE_MERGE_PATCH;
        }

        @Override
        public String getCommandType() {
            return COMMAND_TYPE_MERGE_PATCH;
        }
        public OrderItemCommand.MergePatchOrderItem toMergePatchOrderItem()
        {
            return (OrderItemCommand.MergePatchOrderItem) toCommand();
        }

    }

}

