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
public class OrderItemShipGroupAssocSubitem {

    private Day orderItemShipGroupAssocSubitemDay;

    private String description;

    public Day getOrderItemShipGroupAssocSubitemDay() {
        return orderItemShipGroupAssocSubitemDay;
    }

    public void setOrderItemShipGroupAssocSubitemDay(Day orderItemShipGroupAssocSubitemDay) {
        this.orderItemShipGroupAssocSubitemDay = orderItemShipGroupAssocSubitemDay;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "OrderItemShipGroupAssocSubitem{" +
                "orderItemShipGroupAssocSubitemDay=" + orderItemShipGroupAssocSubitemDay +
                ", description=" + '\'' + description + '\'' +
                '}';
    }

}
