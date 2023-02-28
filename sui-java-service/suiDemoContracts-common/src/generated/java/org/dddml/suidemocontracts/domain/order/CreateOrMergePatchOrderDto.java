package org.dddml.suidemocontracts.domain.order;

import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;

public class CreateOrMergePatchOrderDto extends AbstractOrderCommandDto implements OrderCommand.CreateOrMergePatchOrder
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

    public void copyTo(CreateOrMergePatchOrder command)
    {
        ((AbstractOrderCommandDto) this).copyTo(command);
        command.setTotalAmount(this.getTotalAmount());
        command.setActive(this.getActive());
    }

    public OrderCommand toCommand()
    {
        if (getCommandType() == null) {
            setCommandType(COMMAND_TYPE_MERGE_PATCH);
        }
        if (COMMAND_TYPE_CREATE.equals(getCommandType())) {
            AbstractOrderCommand.SimpleCreateOrder command = new AbstractOrderCommand.SimpleCreateOrder();
            copyTo((AbstractOrderCommand.AbstractCreateOrder) command);
            if (this.getItems() != null) {
                for (CreateOrMergePatchOrderItemDto cmd : this.getItems()) {
                    command.getItems().add((OrderItemCommand.CreateOrderItem) cmd.toCommand());
                }
            }
            return command;
        } else if (COMMAND_TYPE_MERGE_PATCH.equals(getCommandType())) {
            AbstractOrderCommand.SimpleMergePatchOrder command = new AbstractOrderCommand.SimpleMergePatchOrder();
            copyTo((AbstractOrderCommand.SimpleMergePatchOrder) command);
            if (this.getItems() != null) {
                for (CreateOrMergePatchOrderItemDto cmd : this.getItems()) {
                    command.getOrderItemCommands().add(cmd.toCommand());
                }
            }
            return command;
        } 
        throw new UnsupportedOperationException("Unknown command type:" + getCommandType());
    }


    public OrderCommand toSubclass() {
        if (getCommandType() == null) {
            setCommandType(COMMAND_TYPE_MERGE_PATCH);
        }
        if (COMMAND_TYPE_CREATE.equals(getCommandType()) || null == getCommandType()) {
            CreateOrderDto command = new CreateOrderDto();
            copyTo((CreateOrder) command);
            if (this.getItems() != null) {
                for (CreateOrMergePatchOrderItemDto cmd : this.getItems()) {
                    if (cmd.getCommandType() == null) { cmd.setCommandType(COMMAND_TYPE_CREATE); }
                    command.getCreateOrderItemCommands().add((OrderItemCommand.CreateOrderItem) cmd.toSubclass());
                }
            }
            return command;
        } else if (COMMAND_TYPE_MERGE_PATCH.equals(getCommandType())) {
            MergePatchOrderDto command = new MergePatchOrderDto();
            copyTo((MergePatchOrder) command);
            if (this.getItems() != null) {
                for (CreateOrMergePatchOrderItemDto cmd : this.getItems()) {
                    command.getOrderItemCommands().add(cmd.toSubclass());
                }
            }
            return command;
        } 
        throw new UnsupportedOperationException("Unknown command type:" + getCommandType());
    }

    public void copyTo(CreateOrder command)
    {
        copyTo((CreateOrMergePatchOrder) command);
    }

    public void copyTo(MergePatchOrder command)
    {
        copyTo((CreateOrMergePatchOrder) command);
        command.setIsPropertyTotalAmountRemoved(this.getIsPropertyTotalAmountRemoved());
        command.setIsPropertyActiveRemoved(this.getIsPropertyActiveRemoved());
    }

    public static class CreateOrderDto extends CreateOrMergePatchOrderDto implements OrderCommand.CreateOrder
    {
        public CreateOrderDto() {
            this.commandType = COMMAND_TYPE_CREATE;
        }

        @Override
        public String getCommandType() {
            return COMMAND_TYPE_CREATE;
        }
        public OrderCommand.CreateOrder toCreateOrder()
        {
            return (OrderCommand.CreateOrder) toCommand();
        }


        @Override
        public CreateOrderItemCommandCollection getCreateOrderItemCommands() {
            return new CreateOrderItemCommandCollection() {
                @Override
                public void add(OrderItemCommand.CreateOrderItem c) {
                    java.util.List<CreateOrMergePatchOrderItemDto> list = new java.util.ArrayList<>(java.util.Arrays.asList(getItems()));
                    list.add((CreateOrMergePatchOrderItemDto) c);
                    setItems(list.toArray(new CreateOrMergePatchOrderItemDto[0]));
                }

                @Override
                public void remove(OrderItemCommand.CreateOrderItem c) {
                    java.util.List<CreateOrMergePatchOrderItemDto> list = new java.util.ArrayList<>(java.util.Arrays.asList(getItems()));
                    list.remove((CreateOrMergePatchOrderItemDto) c);
                    setItems(list.toArray(new CreateOrMergePatchOrderItemDto[0]));
                }

                @Override
                public void clear() {
                    setItems(new CreateOrMergePatchOrderItemDto[]{});
                }

                @Override
                public java.util.Iterator<OrderItemCommand.CreateOrderItem> iterator() {
                    return java.util.Arrays.stream(getItems())
                            .map(e -> {if (e.getCommandType()==null) e.setCommandType(COMMAND_TYPE_CREATE);return (OrderItemCommand.CreateOrderItem) e.toSubclass();}).iterator();
                }
            };
        }

        @Override
        public OrderItemCommand.CreateOrderItem newCreateOrderItem() {
            return new CreateOrMergePatchOrderItemDto.CreateOrderItemDto();
        }

    }

    public static class MergePatchOrderDto extends CreateOrMergePatchOrderDto implements OrderCommand.MergePatchOrder
    {
        public MergePatchOrderDto() {
            this.commandType = COMMAND_TYPE_MERGE_PATCH;
        }

        @Override
        public String getCommandType() {
            return COMMAND_TYPE_MERGE_PATCH;
        }
        public OrderCommand.MergePatchOrder toMergePatchOrder()
        {
            return (OrderCommand.MergePatchOrder) toCommand();
        }


        @Override
        public OrderItemCommandCollection getOrderItemCommands() {
            return new OrderItemCommandCollection() {
                @Override
                public void add(OrderItemCommand c) {
                    java.util.List<CreateOrMergePatchOrderItemDto> list = new java.util.ArrayList<>(java.util.Arrays.asList(getItems()));
                    list.add((CreateOrMergePatchOrderItemDto) c);
                    setItems(list.toArray(new CreateOrMergePatchOrderItemDto[0]));
                }

                @Override
                public void remove(OrderItemCommand c) {
                    java.util.List<CreateOrMergePatchOrderItemDto> list = new java.util.ArrayList<>(java.util.Arrays.asList(getItems()));
                    list.remove((CreateOrMergePatchOrderItemDto) c);
                    setItems(list.toArray(new CreateOrMergePatchOrderItemDto[0]));
                }

                @Override
                public void clear() {
                    setItems(new CreateOrMergePatchOrderItemDto[]{});
                }

                @Override
                public java.util.Iterator<OrderItemCommand> iterator() {
                    return java.util.Arrays.stream(getItems())
                            .map(e -> (OrderItemCommand) e.toSubclass()).iterator();
                }
            };
        }

        @Override
        public OrderItemCommand.CreateOrderItem newCreateOrderItem() {
            return new CreateOrMergePatchOrderItemDto.CreateOrderItemDto();
        }

        @Override
        public OrderItemCommand.MergePatchOrderItem newMergePatchOrderItem() {
            return new CreateOrMergePatchOrderItemDto.MergePatchOrderItemDto();
        }

        @Override
        public OrderItemCommand.RemoveOrderItem newRemoveOrderItem() {
            return new RemoveOrderItemDto();
        }

    }

}

