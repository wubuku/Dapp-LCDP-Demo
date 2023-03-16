package org.dddml.suidemocontracts.domain.orderv2;

import org.dddml.suidemocontracts.domain.*;
import java.util.Date;
import org.dddml.suidemocontracts.domain.AbstractCommand;

public abstract class AbstractOrderItemShipGroupAssocSubitemCommandDto extends AbstractCommand
{

    /**
     * Order Item Ship Group Assoc Subitem Day
     */
    private Day orderItemShipGroupAssocSubitemDay;

    public Day getOrderItemShipGroupAssocSubitemDay()
    {
        return this.orderItemShipGroupAssocSubitemDay;
    }

    public void setOrderItemShipGroupAssocSubitemDay(Day orderItemShipGroupAssocSubitemDay)
    {
        this.orderItemShipGroupAssocSubitemDay = orderItemShipGroupAssocSubitemDay;
    }


    public void copyTo(OrderItemShipGroupAssocSubitemCommand command) {
        command.setOrderItemShipGroupAssocSubitemDay(this.getOrderItemShipGroupAssocSubitemDay());
        
        command.setRequesterId(this.getRequesterId());
        command.setCommandId(this.getCommandId());
    }

}
