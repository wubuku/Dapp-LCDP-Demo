package org.dddml.suidemocontracts.domain.order;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.domain.AbstractCommand;

public abstract class AbstractOrderCommand extends AbstractCommand implements OrderCommand
{

    private String id;

    public String getId()
    {
        return this.id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    private Long version;

    public Long getVersion()
    {
        return this.version;
    }

    public void setVersion(Long version)
    {
        this.version = version;
    }


    public static abstract class AbstractCreateOrMergePatchOrder extends AbstractOrderCommand implements CreateOrMergePatchOrder
    {

        private BigInteger totalAmount;

        public BigInteger getTotalAmount()
        {
            return this.totalAmount;
        }

        public void setTotalAmount(BigInteger totalAmount)
        {
            this.totalAmount = totalAmount;
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

        public OrderItemCommand.CreateOrderItem newCreateOrderItem()
        {
            AbstractOrderItemCommand.SimpleCreateOrderItem c = new AbstractOrderItemCommand.SimpleCreateOrderItem();
            c.setOrderId(this.getId());

            return c;
        }

        public OrderItemCommand.MergePatchOrderItem newMergePatchOrderItem()
        {
            AbstractOrderItemCommand.SimpleMergePatchOrderItem c = new AbstractOrderItemCommand.SimpleMergePatchOrderItem();
            c.setOrderId(this.getId());

            return c;
        }

        public OrderItemCommand.RemoveOrderItem newRemoveOrderItem()
        {
            AbstractOrderItemCommand.SimpleRemoveOrderItem c = new AbstractOrderItemCommand.SimpleRemoveOrderItem();
            c.setOrderId(this.getId());

            return c;
        }

    }

    public static abstract class AbstractCreateOrder extends AbstractCreateOrMergePatchOrder implements CreateOrder
    {
        @Override
        public String getCommandType() {
            return COMMAND_TYPE_CREATE;
        }

        private CreateOrderItemCommandCollection createOrderItemCommands = new SimpleCreateOrderItemCommandCollection();

        public CreateOrderItemCommandCollection getCreateOrderItemCommands() {
            return this.createOrderItemCommands;
        }

        public CreateOrderItemCommandCollection getItems() {
            return this.createOrderItemCommands; //items;
        }

    }

    public static abstract class AbstractMergePatchOrder extends AbstractCreateOrMergePatchOrder implements MergePatchOrder
    {
        @Override
        public String getCommandType() {
            return COMMAND_TYPE_MERGE_PATCH;
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


        private OrderItemCommandCollection orderItemCommands = new SimpleOrderItemCommandCollection();

        public OrderItemCommandCollection getOrderItemCommands()
        {
            return this.orderItemCommands;
        }

    }

    public static class SimpleCreateOrder extends AbstractCreateOrder
    {
    }

    
    public static class SimpleMergePatchOrder extends AbstractMergePatchOrder
    {
    }

    
	public static class SimpleDeleteOrder extends AbstractOrderCommand implements DeleteOrder
	{
        @Override
        public String getCommandType() {
            return COMMAND_TYPE_DELETE;
        }
	}

    
    public static class SimpleCreateOrderItemCommandCollection implements CreateOrderItemCommandCollection {
        private List<OrderItemCommand.CreateOrderItem> innerCommands = new ArrayList<OrderItemCommand.CreateOrderItem>();

        public void add(OrderItemCommand.CreateOrderItem c) {
            innerCommands.add(c);
        }

        public void remove(OrderItemCommand.CreateOrderItem c) {
            innerCommands.remove(c);
        }

        public void clear() {
            innerCommands.clear();
        }

        @Override
        public Iterator<OrderItemCommand.CreateOrderItem> iterator() {
            return innerCommands.iterator();
        }
    }

    public static class SimpleOrderItemCommandCollection implements OrderItemCommandCollection {
        private List<OrderItemCommand> innerCommands = new ArrayList<OrderItemCommand>();

        public void add(OrderItemCommand c) {
            innerCommands.add(c);
        }

        public void remove(OrderItemCommand c) {
            innerCommands.remove(c);
        }

        public void clear() {
            innerCommands.clear();
        }

        @Override
        public Iterator<OrderItemCommand> iterator() {
            return innerCommands.iterator();
        }
    }


}

