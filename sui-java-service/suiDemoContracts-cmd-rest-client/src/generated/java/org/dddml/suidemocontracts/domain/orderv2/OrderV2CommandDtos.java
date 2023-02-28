package org.dddml.suidemocontracts.domain.orderv2;

import java.util.*;
import java.math.BigInteger;
import org.dddml.suidemocontracts.domain.*;
import java.util.Date;

public class OrderV2CommandDtos
{
    private OrderV2CommandDtos() {
    }

    public static class RemoveItemDto extends org.dddml.suidemocontracts.domain.AbstractCommand {

        public String getCommandType() {
            return "RemoveItem";
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
         * Version
         */
        private Long version;

        public Long getVersion() {
            return this.version;
        }

        public void setVersion(Long version) {
            this.version = version;
        }

    }

    public static class UpdateItemQuantityDto extends org.dddml.suidemocontracts.domain.AbstractCommand {

        public String getCommandType() {
            return "UpdateItemQuantity";
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
         * Version
         */
        private Long version;

        public Long getVersion() {
            return this.version;
        }

        public void setVersion(Long version) {
            this.version = version;
        }

    }

    public static class UpdateEstimatedShipDateDto extends org.dddml.suidemocontracts.domain.AbstractCommand {

        public String getCommandType() {
            return "UpdateEstimatedShipDate";
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
         * Version
         */
        private Long version;

        public Long getVersion() {
            return this.version;
        }

        public void setVersion(Long version) {
            this.version = version;
        }

    }

}

