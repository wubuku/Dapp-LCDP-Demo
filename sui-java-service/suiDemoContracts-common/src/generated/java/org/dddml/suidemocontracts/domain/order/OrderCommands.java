package org.dddml.suidemocontracts.domain.order;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;

public class OrderCommands
{
    private OrderCommands() {
    }

    public static class Create extends org.dddml.suidemocontracts.domain.order.AbstractOrderCommand implements OrderCommand {

        public String getCommandType() {
            return "Create";
        }

        public void setCommandType(String commandType) {
            //do nothing
        }

        /**
         * Id
         */
        private String id;

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
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

    public static class RemoveItem extends org.dddml.suidemocontracts.domain.order.AbstractOrderCommand implements OrderCommand {

        public String getCommandType() {
            return "RemoveItem";
        }

        public void setCommandType(String commandType) {
            //do nothing
        }

        /**
         * Id
         */
        private String id;

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
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

    public static class UpdateItemQuantity extends org.dddml.suidemocontracts.domain.order.AbstractOrderCommand implements OrderCommand {

        public String getCommandType() {
            return "UpdateItemQuantity";
        }

        public void setCommandType(String commandType) {
            //do nothing
        }

        /**
         * Id
         */
        private String id;

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
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

}

