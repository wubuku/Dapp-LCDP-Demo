package org.dddml.suidemocontracts.domain.product;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;

public class ProductCommands
{
    private ProductCommands() {
    }

    public static class Create extends org.dddml.suidemocontracts.domain.product.AbstractProductCommand implements ProductCommand {

        public String getCommandType() {
            return "Create";
        }

        public void setCommandType(String commandType) {
            //do nothing
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
         * Name
         */
        private String name;

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        /**
         * Unit Price
         */
        private BigInteger unitPrice;

        public BigInteger getUnitPrice() {
            return this.unitPrice;
        }

        public void setUnitPrice(BigInteger unitPrice) {
            this.unitPrice = unitPrice;
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

