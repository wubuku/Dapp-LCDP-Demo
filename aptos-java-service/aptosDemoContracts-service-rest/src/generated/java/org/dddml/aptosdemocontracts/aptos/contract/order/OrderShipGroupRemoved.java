// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.aptosdemocontracts.aptos.contract.order;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import org.dddml.aptosdemocontracts.aptos.contract.*;

import java.math.*;
import java.util.*;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OrderShipGroupRemoved {

    private String orderId;

    private BigInteger version;

    private Integer shipGroupSeqId;

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

    @Override
    public String toString() {
        return "OrderShipGroupRemoved{" +
                "orderId=" + '\'' + orderId + '\'' +
                ", version=" + version +
                ", shipGroupSeqId=" + shipGroupSeqId +
                '}';
    }

}
