package org.dddml.suidemocontracts.domain.orderv2;

import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.domain.AbstractCommand;

public abstract class AbstractOrderItemShipGroupAssocSubitemCommandDto extends AbstractCommand
{

    /**
     * Order Item Ship Group Assoc Subitem Seq Id
     */
    private Integer orderItemShipGroupAssocSubitemSeqId;

    public Integer getOrderItemShipGroupAssocSubitemSeqId()
    {
        return this.orderItemShipGroupAssocSubitemSeqId;
    }

    public void setOrderItemShipGroupAssocSubitemSeqId(Integer orderItemShipGroupAssocSubitemSeqId)
    {
        this.orderItemShipGroupAssocSubitemSeqId = orderItemShipGroupAssocSubitemSeqId;
    }


    public void copyTo(OrderItemShipGroupAssocSubitemCommand command) {
        command.setOrderItemShipGroupAssocSubitemSeqId(this.getOrderItemShipGroupAssocSubitemSeqId());
        
        command.setRequesterId(this.getRequesterId());
        command.setCommandId(this.getCommandId());
    }

}
