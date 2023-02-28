package org.dddml.suidemocontracts.domain.orderv2;

import java.math.BigInteger;
import org.dddml.suidemocontracts.domain.*;
import java.util.Date;

public class CreateOrMergePatchOrderV2Dto extends AbstractOrderV2CommandDto
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
     * Estimated Ship Date
     */
    private Day estimatedShipDate;

    public Day getEstimatedShipDate()
    {
        return this.estimatedShipDate;
    }

    public void setEstimatedShipDate(Day estimatedShipDate)
    {
        this.estimatedShipDate = estimatedShipDate;
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


    private CreateOrMergePatchOrderV2ItemDto[] items = new CreateOrMergePatchOrderV2ItemDto[0];

    public CreateOrMergePatchOrderV2ItemDto[] getItems()
    {
        return this.items;
    }

    public void setItems(CreateOrMergePatchOrderV2ItemDto[] items)
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

    private Boolean isPropertyEstimatedShipDateRemoved;

    public Boolean getIsPropertyEstimatedShipDateRemoved()
    {
        return this.isPropertyEstimatedShipDateRemoved;
    }

    public void setIsPropertyEstimatedShipDateRemoved(Boolean removed)
    {
        this.isPropertyEstimatedShipDateRemoved = removed;
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

    public static class CreateOrderV2Dto extends CreateOrMergePatchOrderV2Dto
    {
        public CreateOrderV2Dto() {
            this.commandType = COMMAND_TYPE_CREATE;
        }

        @Override
        public String getCommandType() {
            return COMMAND_TYPE_CREATE;
        }

    }

    public static class MergePatchOrderV2Dto extends CreateOrMergePatchOrderV2Dto
    {
        public MergePatchOrderV2Dto() {
            this.commandType = COMMAND_TYPE_MERGE_PATCH;
        }

        @Override
        public String getCommandType() {
            return COMMAND_TYPE_MERGE_PATCH;
        }

    }

}

