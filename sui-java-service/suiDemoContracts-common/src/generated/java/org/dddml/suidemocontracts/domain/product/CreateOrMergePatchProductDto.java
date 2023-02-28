package org.dddml.suidemocontracts.domain.product;

import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;

public class CreateOrMergePatchProductDto extends AbstractProductCommandDto implements ProductCommand.CreateOrMergePatchProduct
{

    /**
     * Name
     */
    private String name;

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Unit Price
     */
    private BigInteger unitPrice;

    public BigInteger getUnitPrice()
    {
        return this.unitPrice;
    }

    public void setUnitPrice(BigInteger unitPrice)
    {
        this.unitPrice = unitPrice;
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


    private Boolean isPropertyNameRemoved;

    public Boolean getIsPropertyNameRemoved()
    {
        return this.isPropertyNameRemoved;
    }

    public void setIsPropertyNameRemoved(Boolean removed)
    {
        this.isPropertyNameRemoved = removed;
    }

    private Boolean isPropertyUnitPriceRemoved;

    public Boolean getIsPropertyUnitPriceRemoved()
    {
        return this.isPropertyUnitPriceRemoved;
    }

    public void setIsPropertyUnitPriceRemoved(Boolean removed)
    {
        this.isPropertyUnitPriceRemoved = removed;
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

    public void copyTo(CreateOrMergePatchProduct command)
    {
        ((AbstractProductCommandDto) this).copyTo(command);
        command.setName(this.getName());
        command.setUnitPrice(this.getUnitPrice());
        command.setActive(this.getActive());
    }

    public ProductCommand toCommand()
    {
        if (getCommandType() == null) {
            setCommandType(COMMAND_TYPE_MERGE_PATCH);
        }
        if (COMMAND_TYPE_CREATE.equals(getCommandType())) {
            AbstractProductCommand.SimpleCreateProduct command = new AbstractProductCommand.SimpleCreateProduct();
            copyTo((AbstractProductCommand.AbstractCreateProduct) command);
            return command;
        } 
        throw new UnsupportedOperationException("Unknown command type:" + getCommandType());
    }


    public ProductCommand toSubclass() {
        if (getCommandType() == null) {
            setCommandType(COMMAND_TYPE_MERGE_PATCH);
        }
        if (COMMAND_TYPE_CREATE.equals(getCommandType()) || null == getCommandType()) {
            CreateProductDto command = new CreateProductDto();
            copyTo((CreateProduct) command);
            return command;
        } 
        throw new UnsupportedOperationException("Unknown command type:" + getCommandType());
    }

    public void copyTo(CreateProduct command)
    {
        copyTo((CreateOrMergePatchProduct) command);
    }

    public static class CreateProductDto extends CreateOrMergePatchProductDto implements ProductCommand.CreateProduct
    {
        public CreateProductDto() {
            this.commandType = COMMAND_TYPE_CREATE;
        }

        @Override
        public String getCommandType() {
            return COMMAND_TYPE_CREATE;
        }
        public ProductCommand.CreateProduct toCreateProduct()
        {
            return (ProductCommand.CreateProduct) toCommand();
        }

    }

}

