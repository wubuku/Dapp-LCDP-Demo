// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.rooch.contract;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.wubuku.rooch.bean.*;

import java.math.*;
import java.util.*;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OrderItemTableItemAdded {

    private String orderId;

    private String productObjectId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductObjectId() {
        return productObjectId;
    }

    public void setProductObjectId(String productObjectId) {
        this.productObjectId = productObjectId;
    }

    @Override
    public String toString() {
        return "OrderItemTableItemAdded{" +
                "orderId=" + '\'' + orderId + '\'' +
                ", productObjectId=" + '\'' + productObjectId + '\'' +
                '}';
    }
    
}

