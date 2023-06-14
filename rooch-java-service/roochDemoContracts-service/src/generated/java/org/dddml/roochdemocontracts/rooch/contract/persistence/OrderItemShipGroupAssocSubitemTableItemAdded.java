package org.dddml.roochdemocontracts.rooch.contract.persistence;

import org.dddml.roochdemocontracts.domain.RoochEvent;
import org.dddml.roochdemocontracts.domain.RoochEventId;
import org.dddml.roochdemocontracts.domain.order.OrderItemShipGroupAssocSubitemId;

import java.math.BigInteger;
import java.util.Date;
import java.util.Objects;

public class OrderItemShipGroupAssocSubitemTableItemAdded implements RoochEvent, RoochEvent.MutableRoochEvent {
    private OrderItemShipGroupAssocSubitemId orderItemShipGroupAssocSubitemId;
    private RoochEventId roochEventId;
    private Long roochEventIndex;
    private String createdBy;
    private Date createdAt;
    private String updatedBy;
    private Date updatedAt;
    private Boolean deleted;
    private String roochSender;
    private String roochTxHash;
    private String roochTypeTag;
    private Long roochTimestampMs;
    private BigInteger roochBlockHeight;

    public OrderItemShipGroupAssocSubitemId getOrderItemShipGroupAssocSubitemId() {
        return orderItemShipGroupAssocSubitemId;
    }

    public void setOrderItemShipGroupAssocSubitemId(OrderItemShipGroupAssocSubitemId orderItemShipGroupAssocSubitemId) {
        this.orderItemShipGroupAssocSubitemId = orderItemShipGroupAssocSubitemId;
    }

    public RoochEventId getRoochEventId() {
        return roochEventId;
    }

    public void setRoochEventId(RoochEventId roochEventId) {
        this.roochEventId = roochEventId;
    }

    @Override
    public String getRoochSender() {
        return roochSender;
    }

    @Override
    public void setRoochSender(String p) {
        roochSender = p;
    }

    @Override
    public String getRoochTxHash() {
        return roochTxHash;
    }

    @Override
    public void setRoochTxHash(String p) {
        roochTxHash = p;
    }

    @Override
    public String getRoochTypeTag() {
        return roochTypeTag;
    }

    @Override
    public void setRoochTypeTag(String p) {
        roochTypeTag = p;
    }

    @Override
    public Long getRoochTimestampMs() {
        return roochTimestampMs;
    }

    @Override
    public void setRoochTimestampMs(Long p) {
        roochTimestampMs = p;
    }

    @Override
    public BigInteger getRoochBlockHeight() {
        return roochBlockHeight;
    }

    @Override
    public void setRoochBlockHeight(BigInteger p) {
        this.roochBlockHeight = p;
    }

    public Long getRoochEventIndex() {
        return roochEventIndex;
    }

    public void setRoochEventIndex(Long roochEventIndex) {
        this.roochEventIndex = roochEventIndex;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

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
        OrderItemShipGroupAssocSubitemTableItemAdded that = (OrderItemShipGroupAssocSubitemTableItemAdded) o;
        return Objects.equals(orderItemShipGroupAssocSubitemId, that.orderItemShipGroupAssocSubitemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderItemShipGroupAssocSubitemId);
    }

    @Override
    public String toString() {
        return "OrderItemShipGroupAssocSubitemTableItemAdded{" +
                "orderItemShipGroupAssocSubitemId=" + orderItemShipGroupAssocSubitemId +
                ", roochEventId=" + roochEventId +
                ", roochEventIndex=" + roochEventIndex +
                ", createdBy='" + createdBy + '\'' +
                ", createdAt=" + createdAt +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedAt=" + updatedAt +
                ", deleted=" + deleted +
                ", roochSender='" + roochSender + '\'' +
                ", roochTxHash='" + roochTxHash + '\'' +
                ", roochTypeTag='" + roochTypeTag + '\'' +
                ", roochTimestampMs=" + roochTimestampMs +
                ", roochBlockHeight=" + roochBlockHeight +
                '}';
    }
}
