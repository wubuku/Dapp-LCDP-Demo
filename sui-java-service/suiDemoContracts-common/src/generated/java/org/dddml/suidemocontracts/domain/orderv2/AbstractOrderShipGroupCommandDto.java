package org.dddml.suidemocontracts.domain.orderv2;

import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.domain.AbstractCommand;

public abstract class AbstractOrderShipGroupCommandDto extends AbstractCommand
{

    /**
     * Ship Group Seq Id
     */
    private Integer shipGroupSeqId;

    public Integer getShipGroupSeqId()
    {
        return this.shipGroupSeqId;
    }

    public void setShipGroupSeqId(Integer shipGroupSeqId)
    {
        this.shipGroupSeqId = shipGroupSeqId;
    }


    public void copyTo(OrderShipGroupCommand command) {
        command.setShipGroupSeqId(this.getShipGroupSeqId());
        
        command.setRequesterId(this.getRequesterId());
        command.setCommandId(this.getCommandId());
    }

}
