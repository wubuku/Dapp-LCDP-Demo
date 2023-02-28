package org.dddml.suidemocontracts.domain.orderv2;

import java.math.BigInteger;
import org.dddml.suidemocontracts.domain.*;
import java.util.Date;

public class CreateOrMergePatchOrderV2Dto extends AbstractOrderV2CommandDto implements OrderV2Command.CreateOrMergePatchOrderV2
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

    public void copyTo(CreateOrMergePatchOrderV2 command)
    {
        ((AbstractOrderV2CommandDto) this).copyTo(command);
        command.setTotalAmount(this.getTotalAmount());
        command.setEstimatedShipDate(this.getEstimatedShipDate());
        command.setActive(this.getActive());
    }

    public OrderV2Command toCommand()
    {
        if (getCommandType() == null) {
            setCommandType(COMMAND_TYPE_MERGE_PATCH);
        }
        if (COMMAND_TYPE_CREATE.equals(getCommandType())) {
            AbstractOrderV2Command.SimpleCreateOrderV2 command = new AbstractOrderV2Command.SimpleCreateOrderV2();
            copyTo((AbstractOrderV2Command.AbstractCreateOrderV2) command);
            if (this.getItems() != null) {
                for (CreateOrMergePatchOrderV2ItemDto cmd : this.getItems()) {
                    command.getItems().add((OrderV2ItemCommand.CreateOrderV2Item) cmd.toCommand());
                }
            }
            return command;
        } else if (COMMAND_TYPE_MERGE_PATCH.equals(getCommandType())) {
            AbstractOrderV2Command.SimpleMergePatchOrderV2 command = new AbstractOrderV2Command.SimpleMergePatchOrderV2();
            copyTo((AbstractOrderV2Command.SimpleMergePatchOrderV2) command);
            if (this.getItems() != null) {
                for (CreateOrMergePatchOrderV2ItemDto cmd : this.getItems()) {
                    command.getOrderV2ItemCommands().add(cmd.toCommand());
                }
            }
            return command;
        } 
        throw new UnsupportedOperationException("Unknown command type:" + getCommandType());
    }


    public OrderV2Command toSubclass() {
        if (getCommandType() == null) {
            setCommandType(COMMAND_TYPE_MERGE_PATCH);
        }
        if (COMMAND_TYPE_CREATE.equals(getCommandType()) || null == getCommandType()) {
            CreateOrderV2Dto command = new CreateOrderV2Dto();
            copyTo((CreateOrderV2) command);
            if (this.getItems() != null) {
                for (CreateOrMergePatchOrderV2ItemDto cmd : this.getItems()) {
                    if (cmd.getCommandType() == null) { cmd.setCommandType(COMMAND_TYPE_CREATE); }
                    command.getCreateOrderV2ItemCommands().add((OrderV2ItemCommand.CreateOrderV2Item) cmd.toSubclass());
                }
            }
            return command;
        } else if (COMMAND_TYPE_MERGE_PATCH.equals(getCommandType())) {
            MergePatchOrderV2Dto command = new MergePatchOrderV2Dto();
            copyTo((MergePatchOrderV2) command);
            if (this.getItems() != null) {
                for (CreateOrMergePatchOrderV2ItemDto cmd : this.getItems()) {
                    command.getOrderV2ItemCommands().add(cmd.toSubclass());
                }
            }
            return command;
        } 
        throw new UnsupportedOperationException("Unknown command type:" + getCommandType());
    }

    public void copyTo(CreateOrderV2 command)
    {
        copyTo((CreateOrMergePatchOrderV2) command);
    }

    public void copyTo(MergePatchOrderV2 command)
    {
        copyTo((CreateOrMergePatchOrderV2) command);
        command.setIsPropertyTotalAmountRemoved(this.getIsPropertyTotalAmountRemoved());
        command.setIsPropertyEstimatedShipDateRemoved(this.getIsPropertyEstimatedShipDateRemoved());
        command.setIsPropertyActiveRemoved(this.getIsPropertyActiveRemoved());
    }

    public static class CreateOrderV2Dto extends CreateOrMergePatchOrderV2Dto implements OrderV2Command.CreateOrderV2
    {
        public CreateOrderV2Dto() {
            this.commandType = COMMAND_TYPE_CREATE;
        }

        @Override
        public String getCommandType() {
            return COMMAND_TYPE_CREATE;
        }
        public OrderV2Command.CreateOrderV2 toCreateOrderV2()
        {
            return (OrderV2Command.CreateOrderV2) toCommand();
        }


        @Override
        public CreateOrderV2ItemCommandCollection getCreateOrderV2ItemCommands() {
            return new CreateOrderV2ItemCommandCollection() {
                @Override
                public void add(OrderV2ItemCommand.CreateOrderV2Item c) {
                    java.util.List<CreateOrMergePatchOrderV2ItemDto> list = new java.util.ArrayList<>(java.util.Arrays.asList(getItems()));
                    list.add((CreateOrMergePatchOrderV2ItemDto) c);
                    setItems(list.toArray(new CreateOrMergePatchOrderV2ItemDto[0]));
                }

                @Override
                public void remove(OrderV2ItemCommand.CreateOrderV2Item c) {
                    java.util.List<CreateOrMergePatchOrderV2ItemDto> list = new java.util.ArrayList<>(java.util.Arrays.asList(getItems()));
                    list.remove((CreateOrMergePatchOrderV2ItemDto) c);
                    setItems(list.toArray(new CreateOrMergePatchOrderV2ItemDto[0]));
                }

                @Override
                public void clear() {
                    setItems(new CreateOrMergePatchOrderV2ItemDto[]{});
                }

                @Override
                public java.util.Iterator<OrderV2ItemCommand.CreateOrderV2Item> iterator() {
                    return java.util.Arrays.stream(getItems())
                            .map(e -> {if (e.getCommandType()==null) e.setCommandType(COMMAND_TYPE_CREATE);return (OrderV2ItemCommand.CreateOrderV2Item) e.toSubclass();}).iterator();
                }
            };
        }

        @Override
        public OrderV2ItemCommand.CreateOrderV2Item newCreateOrderV2Item() {
            return new CreateOrMergePatchOrderV2ItemDto.CreateOrderV2ItemDto();
        }

    }

    public static class MergePatchOrderV2Dto extends CreateOrMergePatchOrderV2Dto implements OrderV2Command.MergePatchOrderV2
    {
        public MergePatchOrderV2Dto() {
            this.commandType = COMMAND_TYPE_MERGE_PATCH;
        }

        @Override
        public String getCommandType() {
            return COMMAND_TYPE_MERGE_PATCH;
        }
        public OrderV2Command.MergePatchOrderV2 toMergePatchOrderV2()
        {
            return (OrderV2Command.MergePatchOrderV2) toCommand();
        }


        @Override
        public OrderV2ItemCommandCollection getOrderV2ItemCommands() {
            return new OrderV2ItemCommandCollection() {
                @Override
                public void add(OrderV2ItemCommand c) {
                    java.util.List<CreateOrMergePatchOrderV2ItemDto> list = new java.util.ArrayList<>(java.util.Arrays.asList(getItems()));
                    list.add((CreateOrMergePatchOrderV2ItemDto) c);
                    setItems(list.toArray(new CreateOrMergePatchOrderV2ItemDto[0]));
                }

                @Override
                public void remove(OrderV2ItemCommand c) {
                    java.util.List<CreateOrMergePatchOrderV2ItemDto> list = new java.util.ArrayList<>(java.util.Arrays.asList(getItems()));
                    list.remove((CreateOrMergePatchOrderV2ItemDto) c);
                    setItems(list.toArray(new CreateOrMergePatchOrderV2ItemDto[0]));
                }

                @Override
                public void clear() {
                    setItems(new CreateOrMergePatchOrderV2ItemDto[]{});
                }

                @Override
                public java.util.Iterator<OrderV2ItemCommand> iterator() {
                    return java.util.Arrays.stream(getItems())
                            .map(e -> (OrderV2ItemCommand) e.toSubclass()).iterator();
                }
            };
        }

        @Override
        public OrderV2ItemCommand.CreateOrderV2Item newCreateOrderV2Item() {
            return new CreateOrMergePatchOrderV2ItemDto.CreateOrderV2ItemDto();
        }

        @Override
        public OrderV2ItemCommand.MergePatchOrderV2Item newMergePatchOrderV2Item() {
            return new CreateOrMergePatchOrderV2ItemDto.MergePatchOrderV2ItemDto();
        }

        @Override
        public OrderV2ItemCommand.RemoveOrderV2Item newRemoveOrderV2Item() {
            return new RemoveOrderV2ItemDto();
        }

    }

}

