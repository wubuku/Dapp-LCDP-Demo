// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.domain.order;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.roochdemocontracts.domain.*;
import org.dddml.roochdemocontracts.domain.AbstractCommand;

public abstract class AbstractOrderItemCommand extends AbstractCommand implements OrderItemCommand {

    private String productObjectId;

    public String getProductObjectId()
    {
        return this.productObjectId;
    }

    public void setProductObjectId(String productObjectId)
    {
        this.productObjectId = productObjectId;
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


}

