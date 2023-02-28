package org.dddml.suidemocontracts.domain.orderv2;

import java.util.*;
import java.math.BigInteger;
import org.dddml.suidemocontracts.domain.*;
import java.util.Date;
import org.dddml.suidemocontracts.domain.AbstractCommand;

public abstract class AbstractOrderV2Command extends AbstractCommand implements OrderV2Command
{

    private String orderId;

    public String getOrderId()
    {
        return this.orderId;
    }

    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
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


    public static abstract class AbstractCreateOrMergePatchOrderV2 extends AbstractOrderV2Command implements CreateOrMergePatchOrderV2
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

        private Day estimatedShipDate;

        public Day getEstimatedShipDate()
        {
            return this.estimatedShipDate;
        }

        public void setEstimatedShipDate(Day estimatedShipDate)
        {
            this.estimatedShipDate = estimatedShipDate;
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

        public OrderV2ItemCommand.CreateOrderV2Item newCreateOrderV2Item()
        {
            AbstractOrderV2ItemCommand.SimpleCreateOrderV2Item c = new AbstractOrderV2ItemCommand.SimpleCreateOrderV2Item();
            c.setOrderV2OrderId(this.getOrderId());

            return c;
        }

        public OrderV2ItemCommand.MergePatchOrderV2Item newMergePatchOrderV2Item()
        {
            AbstractOrderV2ItemCommand.SimpleMergePatchOrderV2Item c = new AbstractOrderV2ItemCommand.SimpleMergePatchOrderV2Item();
            c.setOrderV2OrderId(this.getOrderId());

            return c;
        }

        public OrderV2ItemCommand.RemoveOrderV2Item newRemoveOrderV2Item()
        {
            AbstractOrderV2ItemCommand.SimpleRemoveOrderV2Item c = new AbstractOrderV2ItemCommand.SimpleRemoveOrderV2Item();
            c.setOrderV2OrderId(this.getOrderId());

            return c;
        }

    }

    public static abstract class AbstractCreateOrderV2 extends AbstractCreateOrMergePatchOrderV2 implements CreateOrderV2
    {
        @Override
        public String getCommandType() {
            return COMMAND_TYPE_CREATE;
        }

        private CreateOrderV2ItemCommandCollection createOrderV2ItemCommands = new SimpleCreateOrderV2ItemCommandCollection();

        public CreateOrderV2ItemCommandCollection getCreateOrderV2ItemCommands() {
            return this.createOrderV2ItemCommands;
        }

        public CreateOrderV2ItemCommandCollection getItems() {
            return this.createOrderV2ItemCommands; //items;
        }

    }

    public static abstract class AbstractMergePatchOrderV2 extends AbstractCreateOrMergePatchOrderV2 implements MergePatchOrderV2
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


        private OrderV2ItemCommandCollection orderV2ItemCommands = new SimpleOrderV2ItemCommandCollection();

        public OrderV2ItemCommandCollection getOrderV2ItemCommands()
        {
            return this.orderV2ItemCommands;
        }

    }

    public static class SimpleCreateOrderV2 extends AbstractCreateOrderV2
    {
    }

    
    public static class SimpleMergePatchOrderV2 extends AbstractMergePatchOrderV2
    {
    }

    
	public static class SimpleDeleteOrderV2 extends AbstractOrderV2Command implements DeleteOrderV2
	{
        @Override
        public String getCommandType() {
            return COMMAND_TYPE_DELETE;
        }
	}

    
    public static class SimpleCreateOrderV2ItemCommandCollection implements CreateOrderV2ItemCommandCollection {
        private List<OrderV2ItemCommand.CreateOrderV2Item> innerCommands = new ArrayList<OrderV2ItemCommand.CreateOrderV2Item>();

        public void add(OrderV2ItemCommand.CreateOrderV2Item c) {
            innerCommands.add(c);
        }

        public void remove(OrderV2ItemCommand.CreateOrderV2Item c) {
            innerCommands.remove(c);
        }

        public void clear() {
            innerCommands.clear();
        }

        @Override
        public Iterator<OrderV2ItemCommand.CreateOrderV2Item> iterator() {
            return innerCommands.iterator();
        }
    }

    public static class SimpleOrderV2ItemCommandCollection implements OrderV2ItemCommandCollection {
        private List<OrderV2ItemCommand> innerCommands = new ArrayList<OrderV2ItemCommand>();

        public void add(OrderV2ItemCommand c) {
            innerCommands.add(c);
        }

        public void remove(OrderV2ItemCommand c) {
            innerCommands.remove(c);
        }

        public void clear() {
            innerCommands.clear();
        }

        @Override
        public Iterator<OrderV2ItemCommand> iterator() {
            return innerCommands.iterator();
        }
    }


}

