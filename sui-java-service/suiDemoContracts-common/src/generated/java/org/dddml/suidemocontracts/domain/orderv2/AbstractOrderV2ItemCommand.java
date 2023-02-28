package org.dddml.suidemocontracts.domain.orderv2;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.domain.AbstractCommand;

public abstract class AbstractOrderV2ItemCommand extends AbstractCommand implements OrderV2ItemCommand
{

    private String productId;

    public String getProductId()
    {
        return this.productId;
    }

    public void setProductId(String productId)
    {
        this.productId = productId;
    }

    private String orderV2OrderId;

    public String getOrderV2OrderId()
    {
        return this.orderV2OrderId;
    }

    public void setOrderV2OrderId(String orderV2OrderId)
    {
        this.orderV2OrderId = orderV2OrderId;
    }


    public static abstract class AbstractCreateOrMergePatchOrderV2Item extends AbstractOrderV2ItemCommand implements CreateOrMergePatchOrderV2Item
    {

        private BigInteger quantity;

        public BigInteger getQuantity()
        {
            return this.quantity;
        }

        public void setQuantity(BigInteger quantity)
        {
            this.quantity = quantity;
        }

        private BigInteger itemAmount;

        public BigInteger getItemAmount()
        {
            return this.itemAmount;
        }

        public void setItemAmount(BigInteger itemAmount)
        {
            this.itemAmount = itemAmount;
        }

        private Boolean active;

        public Boolean getActive()
        {
            return this.active;
        }

        public void setActive(Boolean active)
        {
            this.active = active;
        }

    }

    public static abstract class AbstractCreateOrderV2Item extends AbstractCreateOrMergePatchOrderV2Item implements CreateOrderV2Item
    {
        @Override
        public String getCommandType() {
            return COMMAND_TYPE_CREATE;
        }

    }

    public static abstract class AbstractMergePatchOrderV2Item extends AbstractCreateOrMergePatchOrderV2Item implements MergePatchOrderV2Item
    {
        @Override
        public String getCommandType() {
            return COMMAND_TYPE_MERGE_PATCH;
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


    }

    public static class SimpleCreateOrderV2Item extends AbstractCreateOrderV2Item
    {
    }

    
    public static class SimpleMergePatchOrderV2Item extends AbstractMergePatchOrderV2Item
    {
    }

    
	public static class SimpleRemoveOrderV2Item extends AbstractOrderV2ItemCommand implements RemoveOrderV2Item
	{
        @Override
        public String getCommandType() {
            return COMMAND_TYPE_REMOVE;
        }
	}

    

}

