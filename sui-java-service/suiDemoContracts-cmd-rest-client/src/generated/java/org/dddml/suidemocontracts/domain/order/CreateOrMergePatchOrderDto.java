package org.dddml.suidemocontracts.domain.order;

import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;

public class CreateOrMergePatchOrderDto extends AbstractOrderCommandDto
{

    /**
     * Total Amount
     */
    private BigInteger totalAmount;

    public BigInteger getTotalAmount()
    {
        return this.totalAmount;
    }

    public void setTotalAmount(BigInteger totalAmount)
    {
        this.totalAmount = totalAmount;
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


    private CreateOrMergePatchOrderItemDto[] items = new CreateOrMergePatchOrderItemDto[0];

    public CreateOrMergePatchOrderItemDto[] getItems()
    {
        return this.items;
    }

    public void setItems(CreateOrMergePatchOrderItemDto[] items)
    {
        this.items = items;
    }

    private Boolean isPropertyTotalAmountRemoved;

    public Boolean getIsPropertyTotalAmountRemoved()
    {
        return this.isPropertyTotalAmountRemoved;
    }

    public void setIsPropertyTotalAmountRemoved(Boolean removed)
    {
        this.isPropertyTotalAmountRemoved = removed;
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

    public static class CreateOrderDto extends CreateOrMergePatchOrderDto
    {
        public CreateOrderDto() {
            this.commandType = COMMAND_TYPE_CREATE;
        }

        @Override
        public String getCommandType() {
            return COMMAND_TYPE_CREATE;
        }

    }

    public static class MergePatchOrderDto extends CreateOrMergePatchOrderDto
    {
        public MergePatchOrderDto() {
            this.commandType = COMMAND_TYPE_MERGE_PATCH;
        }

        @Override
        public String getCommandType() {
            return COMMAND_TYPE_MERGE_PATCH;
        }

    }

}

