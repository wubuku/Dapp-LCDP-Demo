package org.dddml.suidemocontracts.domain.orderv2;

import java.util.*;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.domain.AbstractCommand;

public abstract class AbstractOrderItemShipGroupAssocSubitemCommand extends AbstractCommand implements OrderItemShipGroupAssocSubitemCommand
{

    private Integer orderItemShipGroupAssocSubitemSeqId;

    public Integer getOrderItemShipGroupAssocSubitemSeqId()
    {
        return this.orderItemShipGroupAssocSubitemSeqId;
    }

    public void setOrderItemShipGroupAssocSubitemSeqId(Integer orderItemShipGroupAssocSubitemSeqId)
    {
        this.orderItemShipGroupAssocSubitemSeqId = orderItemShipGroupAssocSubitemSeqId;
    }

    private String orderV2OrderId;

    public String getOrderV2OrderId()
    {
        return this.orderV2OrderId;
    }

    public void setOrderV2OrderId(String orderV2OrderId)
    {
        this.orderV2OrderId = orderV2OrderId;
    }

    private Integer orderShipGroupShipGroupSeqId;

    public Integer getOrderShipGroupShipGroupSeqId()
    {
        return this.orderShipGroupShipGroupSeqId;
    }

    public void setOrderShipGroupShipGroupSeqId(Integer orderShipGroupShipGroupSeqId)
    {
        this.orderShipGroupShipGroupSeqId = orderShipGroupShipGroupSeqId;
    }

    private String orderItemShipGroupAssociationProductId;

    public String getOrderItemShipGroupAssociationProductId()
    {
        return this.orderItemShipGroupAssociationProductId;
    }

    public void setOrderItemShipGroupAssociationProductId(String orderItemShipGroupAssociationProductId)
    {
        this.orderItemShipGroupAssociationProductId = orderItemShipGroupAssociationProductId;
    }


}

