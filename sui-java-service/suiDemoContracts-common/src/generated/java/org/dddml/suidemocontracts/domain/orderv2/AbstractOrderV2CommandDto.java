package org.dddml.suidemocontracts.domain.orderv2;

import java.math.BigInteger;
import org.dddml.suidemocontracts.domain.*;
import java.util.Date;
import org.dddml.suidemocontracts.domain.AbstractCommand;

public abstract class AbstractOrderV2CommandDto extends AbstractCommand
{

    /**
     * Order Id
     */
    private String orderId;

    public String getOrderId()
    {
        return this.orderId;
    }

    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }

    /**
     * Id
     */
    private String surrogateId;

    public String getSurrogateId()
    {
        return this.surrogateId;
    }

    public void setSurrogateId(String id)
    {
        this.surrogateId = id;
    }

    /**
     * Version
     */
    private Long version;

    public Long getVersion()
    {
        return this.version;
    }

    public void setVersion(Long version)
    {
        this.version = version;
    }


    public void copyTo(OrderV2Command command) {
        command.setOrderId(this.getOrderId());
        command.setSurrogateId(this.getSurrogateId());
        command.setVersion(this.getVersion());
        
        command.setRequesterId(this.getRequesterId());
        command.setCommandId(this.getCommandId());
    }

}