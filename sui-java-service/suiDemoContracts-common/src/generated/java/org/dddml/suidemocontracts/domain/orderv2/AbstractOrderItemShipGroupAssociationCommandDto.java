package org.dddml.suidemocontracts.domain.orderv2;

import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.domain.AbstractCommand;

public abstract class AbstractOrderItemShipGroupAssociationCommandDto extends AbstractCommand
{

    /**
     * Product Id
     */
    private String productId;

    public String getProductId()
    {
        return this.productId;
    }

    public void setProductId(String productId)
    {
        this.productId = productId;
    }


    public void copyTo(OrderItemShipGroupAssociationCommand command) {
        command.setProductId(this.getProductId());
        
        command.setRequesterId(this.getRequesterId());
        command.setCommandId(this.getCommandId());
    }

}