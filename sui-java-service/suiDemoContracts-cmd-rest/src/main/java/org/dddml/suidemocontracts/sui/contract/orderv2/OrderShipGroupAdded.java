// <autogenerated>
//   This file was generated by T4 code generator .
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.suidemocontracts.sui.contract.orderv2;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import org.dddml.suidemocontracts.sui.contract.*;

import java.math.*;
import java.util.*;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OrderShipGroupAdded {
    private String id;

    private String orderId;

    private BigInteger version;

    private Integer shipGroupSeqId;

    private String shipmentMethod;

    private String productId;

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

    public Integer getShipGroupSeqId() {
        return shipGroupSeqId;
    }

    public void setShipGroupSeqId(Integer shipGroupSeqId) {
        this.shipGroupSeqId = shipGroupSeqId;
    }

    public String getShipmentMethod() {
        return shipmentMethod;
    }

    public void setShipmentMethod(String shipmentMethod) {
        this.shipmentMethod = shipmentMethod;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public BigInteger getQuantity() {
        return quantity;
    }

    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderShipGroupAdded{" +
                "id='" + id + '\'' +
                ", orderId=" + '\'' + orderId + '\'' +
                ", version=" + version +
                ", shipGroupSeqId=" + shipGroupSeqId +
                ", shipmentMethod=" + '\'' + shipmentMethod + '\'' +
                ", productId=" + '\'' + productId + '\'' +
                ", quantity=" + quantity +
                '}';
    }

}
