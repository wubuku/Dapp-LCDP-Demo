// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.domain.order;

import java.util.*;
import java.util.Date;
import org.dddml.roochdemocontracts.domain.*;
import org.dddml.roochdemocontracts.domain.AbstractCommand;

public abstract class AbstractOrderItemShipGroupAssociationCommand extends AbstractCommand implements OrderItemShipGroupAssociationCommand {

    private String productObjId;

    public String getProductObjId()
    {
        return this.productObjId;
    }

    public void setProductObjId(String productObjId)
    {
        this.productObjId = productObjId;
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

    private Integer orderShipGroupShipGroupSeqId;

    public Integer getOrderShipGroupShipGroupSeqId()
    {
        return this.orderShipGroupShipGroupSeqId;
    }

    public void setOrderShipGroupShipGroupSeqId(Integer orderShipGroupShipGroupSeqId)
    {
        this.orderShipGroupShipGroupSeqId = orderShipGroupShipGroupSeqId;
    }


}

