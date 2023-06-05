// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.domain.order;

import java.util.*;
import java.math.BigInteger;
import org.dddml.roochdemocontracts.domain.*;
import java.util.Date;

public class OrderCommands {
    private OrderCommands() {
    }

    public static class Create extends AbstractOrderCommand implements OrderCommand {

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
         * Product Obj Id
         */
        private String productObjId;

        public String getProductObjId() {
            return this.productObjId;
        }

        public void setProductObjId(String productObjId) {
            this.productObjId = productObjId;
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

    public static class RemoveItem extends AbstractOrderCommand implements OrderCommand {

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
         * Product Obj Id
         */
        private String productObjId;

        public String getProductObjId() {
            return this.productObjId;
        }

        public void setProductObjId(String productObjId) {
            this.productObjId = productObjId;
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

        public String getOrderId() {
            return this.getId();
        }

        public void setOrderId(String id) {
            this.setId(id);
        }

    }

    public static class UpdateItemQuantity extends AbstractOrderCommand implements OrderCommand {

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
         * Product Obj Id
         */
        private String productObjId;

        public String getProductObjId() {
            return this.productObjId;
        }

        public void setProductObjId(String productObjId) {
            this.productObjId = productObjId;
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

        public String getOrderId() {
            return this.getId();
        }

        public void setOrderId(String id) {
            this.setId(id);
        }

    }

    public static class UpdateEstimatedShipDate extends AbstractOrderCommand implements OrderCommand {

        public String getCommandType() {
            return "UpdateEstimatedShipDate";
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

        public String getOrderId() {
            return this.getId();
        }

        public void setOrderId(String id) {
            this.setId(id);
        }

    }

    public static class AddOrderShipGroup extends AbstractOrderCommand implements OrderCommand {

        public String getCommandType() {
            return "AddOrderShipGroup";
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
         * Product Obj Id
         */
        private String productObjId;

        public String getProductObjId() {
            return this.productObjId;
        }

        public void setProductObjId(String productObjId) {
            this.productObjId = productObjId;
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

        public String getOrderId() {
            return this.getId();
        }

        public void setOrderId(String id) {
            this.setId(id);
        }

    }

    public static class CancelOrderShipGroupQuantity extends AbstractOrderCommand implements OrderCommand {

        public String getCommandType() {
            return "CancelOrderShipGroupQuantity";
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
         * Product Obj Id
         */
        private String productObjId;

        public String getProductObjId() {
            return this.productObjId;
        }

        public void setProductObjId(String productObjId) {
            this.productObjId = productObjId;
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

        public String getOrderId() {
            return this.getId();
        }

        public void setOrderId(String id) {
            this.setId(id);
        }

    }

    public static class RemoveOrderShipGroupItem extends AbstractOrderCommand implements OrderCommand {

        public String getCommandType() {
            return "RemoveOrderShipGroupItem";
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
         * Product Obj Id
         */
        private String productObjId;

        public String getProductObjId() {
            return this.productObjId;
        }

        public void setProductObjId(String productObjId) {
            this.productObjId = productObjId;
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

        public String getOrderId() {
            return this.getId();
        }

        public void setOrderId(String id) {
            this.setId(id);
        }

    }

}

