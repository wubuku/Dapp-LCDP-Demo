// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.aptosdemocontracts.aptos.contract;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.wubuku.aptos.bean.*;

import java.math.*;
import java.util.*;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OrderItemShipGroupAssociationTableItemAdded {

    private String orderId;

    private Integer orderShipGroupShipGroupSeqId;

    private String productId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderShipGroupShipGroupSeqId() {
        return orderShipGroupShipGroupSeqId;
    }

    public void setOrderShipGroupShipGroupSeqId(Integer orderShipGroupShipGroupSeqId) {
        this.orderShipGroupShipGroupSeqId = orderShipGroupShipGroupSeqId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "OrderItemShipGroupAssociationTableItemAdded{" +
                "orderId=" + '\'' + orderId + '\'' +
                ", orderShipGroupShipGroupSeqId=" + orderShipGroupShipGroupSeqId +
                ", productId=" + '\'' + productId + '\'' +
                '}';
    }
    
}
