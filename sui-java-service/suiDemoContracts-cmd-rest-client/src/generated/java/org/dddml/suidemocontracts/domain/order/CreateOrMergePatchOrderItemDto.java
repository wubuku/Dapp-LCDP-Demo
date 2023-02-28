package org.dddml.suidemocontracts.domain.order;

import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;

public class CreateOrMergePatchOrderItemDto extends AbstractOrderItemCommandDto
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

    public static class CreateOrderItemDto extends CreateOrMergePatchOrderItemDto
    {
        public CreateOrderItemDto() {
            this.commandType = COMMAND_TYPE_CREATE;
        }

        @Override
        public String getCommandType() {
            return COMMAND_TYPE_CREATE;
        }

    }

    public static class MergePatchOrderItemDto extends CreateOrMergePatchOrderItemDto
    {
        public MergePatchOrderItemDto() {
            this.commandType = COMMAND_TYPE_MERGE_PATCH;
        }

        @Override
        public String getCommandType() {
            return COMMAND_TYPE_MERGE_PATCH;
        }

    }

}

