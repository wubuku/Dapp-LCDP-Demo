package org.dddml.suidemocontracts.domain.orderv2;

import java.util.*;
import java.math.BigInteger;
import org.dddml.suidemocontracts.domain.*;
import java.util.Date;

public class OrderV2Commands
{
    private OrderV2Commands() {
    }

    public static class Create extends AbstractOrderV2Command implements OrderV2Command {

        public String getCommandType() {
            return "Create";
        }

        public void setCommandType(String commandType) {
            //do nothing
        }

        /**
         * Order Id
         */
        private String orderId;

        public String getOrderId() {
            return this.orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        /**
         * Product
         */
        private String product;

        public String getProduct() {
            return this.product;
        }

        public void setProduct(String product) {
            this.product = product;
        }

        /**
         * Quantity
         */
        private BigInteger quantity;

        public BigInteger getQuantity() {
            return this.quantity;
        }

        public void setQuantity(BigInteger quantity) {
            this.quantity = quantity;
        }

        /**
         * Off Chain Version
         */
        private Long offChainVersion;

        public Long getOffChainVersion() {
            return this.offChainVersion;
        }

        public void setOffChainVersion(Long offChainVersion) {
            this.offChainVersion = offChainVersion;
        }

    }

    public static class RemoveItem extends AbstractOrderV2Command implements OrderV2Command {

        public String getCommandType() {
            return "RemoveItem";
        }

        public void setCommandType(String commandType) {
            //do nothing
        }

        /**
         * Order Id
         */
        private String orderId;

        public String getOrderId() {
            return this.orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        /**
         * Product Id
         */
        private String productId;

        public String getProductId() {
            return this.productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        /**
         * Off Chain Version
         */
        private Long offChainVersion;

        public Long getOffChainVersion() {
            return this.offChainVersion;
        }

        public void setOffChainVersion(Long offChainVersion) {
            this.offChainVersion = offChainVersion;
        }

    }

    public static class UpdateItemQuantity extends AbstractOrderV2Command implements OrderV2Command {

        public String getCommandType() {
            return "UpdateItemQuantity";
        }

        public void setCommandType(String commandType) {
            //do nothing
        }

        /**
         * Order Id
         */
        private String orderId;

        public String getOrderId() {
            return this.orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        /**
         * Product Id
         */
        private String productId;

        public String getProductId() {
            return this.productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        /**
         * Quantity
         */
        private BigInteger quantity;

        public BigInteger getQuantity() {
            return this.quantity;
        }

        public void setQuantity(BigInteger quantity) {
            this.quantity = quantity;
        }

        /**
         * Off Chain Version
         */
        private Long offChainVersion;

        public Long getOffChainVersion() {
            return this.offChainVersion;
        }

        public void setOffChainVersion(Long offChainVersion) {
            this.offChainVersion = offChainVersion;
        }

    }

    public static class UpdateEstimatedShipDate extends AbstractOrderV2Command implements OrderV2Command {

        public String getCommandType() {
            return "UpdateEstimatedShipDate";
        }

        public void setCommandType(String commandType) {
            //do nothing
        }

        /**
         * Order Id
         */
        private String orderId;

        public String getOrderId() {
            return this.orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        /**
         * Estimated Ship Date
         */
        private Day estimatedShipDate;

        public Day getEstimatedShipDate() {
            return this.estimatedShipDate;
        }

        public void setEstimatedShipDate(Day estimatedShipDate) {
            this.estimatedShipDate = estimatedShipDate;
        }

        /**
         * Off Chain Version
         */
        private Long offChainVersion;

        public Long getOffChainVersion() {
            return this.offChainVersion;
        }

        public void setOffChainVersion(Long offChainVersion) {
            this.offChainVersion = offChainVersion;
        }

    }

    public static class AddOrderShipGroup extends AbstractOrderV2Command implements OrderV2Command {

        public String getCommandType() {
            return "AddOrderShipGroup";
        }

        public void setCommandType(String commandType) {
            //do nothing
        }

        /**
         * Order Id
         */
        private String orderId;

        public String getOrderId() {
            return this.orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        /**
         * Ship Group Seq Id
         */
        private Integer shipGroupSeqId;

        public Integer getShipGroupSeqId() {
            return this.shipGroupSeqId;
        }

        public void setShipGroupSeqId(Integer shipGroupSeqId) {
            this.shipGroupSeqId = shipGroupSeqId;
        }

        /**
         * Shipment Method
         */
        private String shipmentMethod;

        public String getShipmentMethod() {
            return this.shipmentMethod;
        }

        public void setShipmentMethod(String shipmentMethod) {
            this.shipmentMethod = shipmentMethod;
        }

        /**
         * Product Id
         */
        private String productId;

        public String getProductId() {
            return this.productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        /**
         * Quantity
         */
        private BigInteger quantity;

        public BigInteger getQuantity() {
            return this.quantity;
        }

        public void setQuantity(BigInteger quantity) {
            this.quantity = quantity;
        }

        /**
         * Off Chain Version
         */
        private Long offChainVersion;

        public Long getOffChainVersion() {
            return this.offChainVersion;
        }

        public void setOffChainVersion(Long offChainVersion) {
            this.offChainVersion = offChainVersion;
        }

    }

    public static class CancelOrderShipGroupQuantity extends AbstractOrderV2Command implements OrderV2Command {

        public String getCommandType() {
            return "CancelOrderShipGroupQuantity";
        }

        public void setCommandType(String commandType) {
            //do nothing
        }

        /**
         * Order Id
         */
        private String orderId;

        public String getOrderId() {
            return this.orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        /**
         * Ship Group Seq Id
         */
        private Integer shipGroupSeqId;

        public Integer getShipGroupSeqId() {
            return this.shipGroupSeqId;
        }

        public void setShipGroupSeqId(Integer shipGroupSeqId) {
            this.shipGroupSeqId = shipGroupSeqId;
        }

        /**
         * Product Id
         */
        private String productId;

        public String getProductId() {
            return this.productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        /**
         * Cancel Quantity
         */
        private BigInteger cancelQuantity;

        public BigInteger getCancelQuantity() {
            return this.cancelQuantity;
        }

        public void setCancelQuantity(BigInteger cancelQuantity) {
            this.cancelQuantity = cancelQuantity;
        }

        /**
         * Off Chain Version
         */
        private Long offChainVersion;

        public Long getOffChainVersion() {
            return this.offChainVersion;
        }

        public void setOffChainVersion(Long offChainVersion) {
            this.offChainVersion = offChainVersion;
        }

    }

}

