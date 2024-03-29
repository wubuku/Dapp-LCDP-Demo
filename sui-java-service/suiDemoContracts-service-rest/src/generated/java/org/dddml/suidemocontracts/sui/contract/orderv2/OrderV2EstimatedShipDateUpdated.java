// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.suidemocontracts.sui.contract.orderv2;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import org.dddml.suidemocontracts.sui.contract.*;

import java.math.*;
import java.util.*;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OrderV2EstimatedShipDateUpdated {
    private String id;

    private String orderId;

    private BigInteger version;

    private DayForEvent estimatedShipDate;

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

    public DayForEvent getEstimatedShipDate() {
        return estimatedShipDate;
    }

    public void setEstimatedShipDate(DayForEvent estimatedShipDate) {
        this.estimatedShipDate = estimatedShipDate;
    }

    @Override
    public String toString() {
        return "OrderV2EstimatedShipDateUpdated{" +
                "id=" + '\'' + id + '\'' +
                ", orderId=" + '\'' + orderId + '\'' +
                ", version=" + version +
                ", estimatedShipDate=" + estimatedShipDate +
                '}';
    }

}
