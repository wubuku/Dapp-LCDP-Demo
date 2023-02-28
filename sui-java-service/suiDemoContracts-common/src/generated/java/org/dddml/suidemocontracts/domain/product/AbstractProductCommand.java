package org.dddml.suidemocontracts.domain.product;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.domain.AbstractCommand;

public abstract class AbstractProductCommand extends AbstractCommand implements ProductCommand
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

    private Long version;

    public Long getVersion()
    {
        return this.version;
    }

    public void setVersion(Long version)
    {
        this.version = version;
    }


    public static abstract class AbstractCreateOrMergePatchProduct extends AbstractProductCommand implements CreateOrMergePatchProduct
    {

        private String name;

        public String getName()
        {
            return this.name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        private BigInteger unitPrice;

        public BigInteger getUnitPrice()
        {
            return this.unitPrice;
        }

        public void setUnitPrice(BigInteger unitPrice)
        {
            this.unitPrice = unitPrice;
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

    public static abstract class AbstractCreateProduct extends AbstractCreateOrMergePatchProduct implements CreateProduct
    {
        @Override
        public String getCommandType() {
            return COMMAND_TYPE_CREATE;
        }

    }

    public static class SimpleCreateProduct extends AbstractCreateProduct
    {
    }

    

}

