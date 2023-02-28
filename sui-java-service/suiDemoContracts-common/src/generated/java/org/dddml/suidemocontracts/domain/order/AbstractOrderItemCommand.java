package org.dddml.suidemocontracts.domain.order;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.domain.AbstractCommand;

public abstract class AbstractOrderItemCommand extends AbstractCommand implements OrderItemCommand
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

    private String orderId;

    public String getOrderId()
    {
        return this.orderId;
    }

    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }


    public static abstract class AbstractCreateOrMergePatchOrderItem extends AbstractOrderItemCommand implements CreateOrMergePatchOrderItem
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

    public static abstract class AbstractCreateOrderItem extends AbstractCreateOrMergePatchOrderItem implements CreateOrderItem
    {
        @Override
        public String getCommandType() {
            return COMMAND_TYPE_CREATE;
        }

    }

    public static abstract class AbstractMergePatchOrderItem extends AbstractCreateOrMergePatchOrderItem implements MergePatchOrderItem
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

    public static class SimpleCreateOrderItem extends AbstractCreateOrderItem
    {
    }

    
    public static class SimpleMergePatchOrderItem extends AbstractMergePatchOrderItem
    {
    }

    
	public static class SimpleRemoveOrderItem extends AbstractOrderItemCommand implements RemoveOrderItem
	{
        @Override
        public String getCommandType() {
            return COMMAND_TYPE_REMOVE;
        }
	}

    

}

