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
public class OrderShipGroup {

    private Integer shipGroupSeqId;

    private String shipmentMethod;

    private AnnotatedMoveTableView orderItemShipGroupAssociations;

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

    public AnnotatedMoveTableView getOrderItemShipGroupAssociations() {
        return orderItemShipGroupAssociations;
    }

    public void setOrderItemShipGroupAssociations(AnnotatedMoveTableView orderItemShipGroupAssociations) {
        this.orderItemShipGroupAssociations = orderItemShipGroupAssociations;
    }

    @Override
    public String toString() {
        return "OrderShipGroup{" +
                "shipGroupSeqId=" + shipGroupSeqId +
                ", shipmentMethod=" + '\'' + shipmentMethod + '\'' +
                ", orderItemShipGroupAssociations=" + orderItemShipGroupAssociations +
                '}';
    }

}
