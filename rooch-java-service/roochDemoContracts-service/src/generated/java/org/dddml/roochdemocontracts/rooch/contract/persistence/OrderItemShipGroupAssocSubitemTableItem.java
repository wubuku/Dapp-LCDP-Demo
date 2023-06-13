package org.dddml.roochdemocontracts.rooch.contract.persistence;

import org.dddml.roochdemocontracts.domain.RoochEventId;
import org.dddml.roochdemocontracts.domain.order.OrderItemShipGroupAssocSubitemId;

import java.util.Date;
import java.util.Objects;

public class OrderItemShipGroupAssocSubitemTableItem {
    private OrderItemShipGroupAssocSubitemId orderItemShipGroupAssocSubitemId;

    public OrderItemShipGroupAssocSubitemId getOrderItemShipGroupAssocSubitemId() {
        return orderItemShipGroupAssocSubitemId;
    }

    public void setOrderItemShipGroupAssocSubitemId(OrderItemShipGroupAssocSubitemId orderItemShipGroupAssocSubitemId) {
        this.orderItemShipGroupAssocSubitemId = orderItemShipGroupAssocSubitemId;
    }

    private RoochEventId roochEventId;

    public RoochEventId getRoochEventId() {
        return roochEventId;
    }

    public void setRoochEventId(RoochEventId roochEventId) {
        this.roochEventId = roochEventId;
    }

    private Long roochEventIndex;

    public Long getRoochEventIndex() {
        return roochEventIndex;
    }

    public void setRoochEventIndex(Long roochEventIndex) {
        this.roochEventIndex = roochEventIndex;
    }

    private String createdBy;

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    private Date createdAt;

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    private String updatedBy;

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    private Date updatedAt;

    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    private Boolean deleted;

    public Boolean getDeleted() {
        return this.deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemShipGroupAssocSubitemTableItem that = (OrderItemShipGroupAssocSubitemTableItem) o;
        return Objects.equals(orderItemShipGroupAssocSubitemId, that.orderItemShipGroupAssocSubitemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderItemShipGroupAssocSubitemId);
    }
}
