package org.dddml.suidemocontracts.domain.order;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.*;


public class OrderItemStateDto
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

    private Long version;

    public Long getVersion()
    {
        return this.version;
    }

    public void setVersion(Long version)
    {
        this.version = version;
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

    private String createdBy;

    public String getCreatedBy()
    {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }

    private Date createdAt;

    public Date getCreatedAt()
    {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt)
    {
        this.createdAt = createdAt;
    }

    private String updatedBy;

    public String getUpdatedBy()
    {
        return this.updatedBy;
    }

    public void setUpdatedBy(String updatedBy)
    {
        this.updatedBy = updatedBy;
    }

    private Date updatedAt;

    public Date getUpdatedAt()
    {
        return this.updatedAt;
    }

    public void setUpdatedAt(Date updatedAt)
    {
        this.updatedAt = updatedAt;
    }

}

