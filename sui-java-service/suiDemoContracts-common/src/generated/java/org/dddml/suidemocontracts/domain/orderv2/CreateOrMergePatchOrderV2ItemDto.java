package org.dddml.suidemocontracts.domain.orderv2;

import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;

public class CreateOrMergePatchOrderV2ItemDto extends AbstractOrderV2ItemCommandDto implements OrderV2ItemCommand.CreateOrMergePatchOrderV2Item
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

    public void copyTo(CreateOrMergePatchOrderV2Item command)
    {
        ((AbstractOrderV2ItemCommandDto) this).copyTo(command);
        command.setQuantity(this.getQuantity());
        command.setItemAmount(this.getItemAmount());
        command.setActive(this.getActive());
    }

    public OrderV2ItemCommand toCommand()
    {
        if (getCommandType() == null) {
            setCommandType(COMMAND_TYPE_MERGE_PATCH);
        }
        if (COMMAND_TYPE_CREATE.equals(getCommandType())) {
            AbstractOrderV2ItemCommand.SimpleCreateOrderV2Item command = new AbstractOrderV2ItemCommand.SimpleCreateOrderV2Item();
            copyTo((AbstractOrderV2ItemCommand.AbstractCreateOrderV2Item) command);
            return command;
        } else if (COMMAND_TYPE_MERGE_PATCH.equals(getCommandType())) {
            AbstractOrderV2ItemCommand.SimpleMergePatchOrderV2Item command = new AbstractOrderV2ItemCommand.SimpleMergePatchOrderV2Item();
            copyTo((AbstractOrderV2ItemCommand.SimpleMergePatchOrderV2Item) command);
            return command;
        } 
        else if (COMMAND_TYPE_REMOVE.equals(getCommandType())) {
            AbstractOrderV2ItemCommand.SimpleRemoveOrderV2Item command = new AbstractOrderV2ItemCommand.SimpleRemoveOrderV2Item();
            ((AbstractOrderV2ItemCommandDto) this).copyTo(command);
            return command;
        }
        throw new UnsupportedOperationException("Unknown command type:" + getCommandType());
    }


    public OrderV2ItemCommand toSubclass() {
        if (getCommandType() == null) {
            setCommandType(COMMAND_TYPE_MERGE_PATCH);
        }
        if (COMMAND_TYPE_CREATE.equals(getCommandType()) || null == getCommandType()) {
            CreateOrderV2ItemDto command = new CreateOrderV2ItemDto();
            copyTo((CreateOrderV2Item) command);
            return command;
        } else if (COMMAND_TYPE_MERGE_PATCH.equals(getCommandType())) {
            MergePatchOrderV2ItemDto command = new MergePatchOrderV2ItemDto();
            copyTo((MergePatchOrderV2Item) command);
            return command;
        } 
        else if (COMMAND_TYPE_REMOVE.equals(getCommandType())) {
            RemoveOrderV2ItemDto command = new RemoveOrderV2ItemDto();
            ((AbstractOrderV2ItemCommandDto) this).copyTo(command);
            return command;
        }
        throw new UnsupportedOperationException("Unknown command type:" + getCommandType());
    }

    public void copyTo(CreateOrderV2Item command)
    {
        copyTo((CreateOrMergePatchOrderV2Item) command);
    }

    public void copyTo(MergePatchOrderV2Item command)
    {
        copyTo((CreateOrMergePatchOrderV2Item) command);
        command.setIsPropertyQuantityRemoved(this.getIsPropertyQuantityRemoved());
        command.setIsPropertyItemAmountRemoved(this.getIsPropertyItemAmountRemoved());
        command.setIsPropertyActiveRemoved(this.getIsPropertyActiveRemoved());
    }

    public static class CreateOrderV2ItemDto extends CreateOrMergePatchOrderV2ItemDto implements OrderV2ItemCommand.CreateOrderV2Item
    {
        public CreateOrderV2ItemDto() {
            this.commandType = COMMAND_TYPE_CREATE;
        }

        @Override
        public String getCommandType() {
            return COMMAND_TYPE_CREATE;
        }
        public OrderV2ItemCommand.CreateOrderV2Item toCreateOrderV2Item()
        {
            return (OrderV2ItemCommand.CreateOrderV2Item) toCommand();
        }

    }

    public static class MergePatchOrderV2ItemDto extends CreateOrMergePatchOrderV2ItemDto implements OrderV2ItemCommand.MergePatchOrderV2Item
    {
        public MergePatchOrderV2ItemDto() {
            this.commandType = COMMAND_TYPE_MERGE_PATCH;
        }

        @Override
        public String getCommandType() {
            return COMMAND_TYPE_MERGE_PATCH;
        }
        public OrderV2ItemCommand.MergePatchOrderV2Item toMergePatchOrderV2Item()
        {
            return (OrderV2ItemCommand.MergePatchOrderV2Item) toCommand();
        }

    }

}

