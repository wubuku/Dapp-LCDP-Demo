// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.suidemocontracts.sui.contract;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.wubuku.sui.bean.*;

import java.math.*;
import java.util.*;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OrderV2 {

    private UID id;

    private String orderId;

    private Long offChainVersion;

    private BigInteger totalAmount;

    private Day estimatedShipDate;

    private BigInteger version;

    private Table items;

    private Table orderShipGroups;

    public UID getId() {
        return id;
    }

    public void setId(UID id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getOffChainVersion() {
        return offChainVersion;
    }

    public void setOffChainVersion(Long offChainVersion) {
        this.offChainVersion = offChainVersion;
    }

    public BigInteger getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigInteger totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Day getEstimatedShipDate() {
        return estimatedShipDate;
    }

    public void setEstimatedShipDate(Day estimatedShipDate) {
        this.estimatedShipDate = estimatedShipDate;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public Table getItems() {
        return items;
    }

    public void setItems(Table items) {
        this.items = items;
    }

    public Table getOrderShipGroups() {
        return orderShipGroups;
    }

    public void setOrderShipGroups(Table orderShipGroups) {
        this.orderShipGroups = orderShipGroups;
    }

    @Override
    public String toString() {
        return "OrderV2{" +
                "id=" + id +
                ", orderId=" + '\'' + orderId + '\'' +
                ", offChainVersion=" + offChainVersion +
                ", totalAmount=" + totalAmount +
                ", estimatedShipDate=" + estimatedShipDate +
                ", version=" + version +
                ", items=" + items +
                ", orderShipGroups=" + orderShipGroups +
                '}';
    }
}
