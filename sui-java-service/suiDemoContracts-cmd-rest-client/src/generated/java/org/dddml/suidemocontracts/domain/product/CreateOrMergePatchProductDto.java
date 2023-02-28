package org.dddml.suidemocontracts.domain.product;

import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;

public class CreateOrMergePatchProductDto extends AbstractProductCommandDto
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

    public static class CreateProductDto extends CreateOrMergePatchProductDto
    {
        public CreateProductDto() {
            this.commandType = COMMAND_TYPE_CREATE;
        }

        @Override
        public String getCommandType() {
            return COMMAND_TYPE_CREATE;
        }

    }

}

