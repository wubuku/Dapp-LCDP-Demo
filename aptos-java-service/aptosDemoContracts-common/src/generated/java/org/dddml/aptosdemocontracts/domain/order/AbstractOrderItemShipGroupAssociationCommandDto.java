// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.aptosdemocontracts.domain.order;

import java.util.Date;
import org.dddml.aptosdemocontracts.domain.*;
import org.dddml.aptosdemocontracts.domain.AbstractCommand;

public abstract class AbstractOrderItemShipGroupAssociationCommandDto extends AbstractCommand {

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
