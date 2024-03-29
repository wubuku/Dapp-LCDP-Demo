// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.rooch.contract.order;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import org.dddml.roochdemocontracts.rooch.contract.*;

import java.math.*;
import java.util.*;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OrderItemQuantityUpdated {
    private String id;

    private String orderId;

    private BigInteger version;

    private String productObjId;

    private BigInteger quantity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public String getProductObjId() {
        return productObjId;
    }

    public void setProductObjId(String productObjId) {
        this.productObjId = productObjId;
    }

    public BigInteger getQuantity() {
        return quantity;
    }

    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderItemQuantityUpdated{" +
                "id=" + '\'' + id + '\'' +
                ", orderId=" + '\'' + orderId + '\'' +
                ", version=" + version +
                ", productObjId=" + '\'' + productObjId + '\'' +
                ", quantity=" + quantity +
                '}';
    }

}
