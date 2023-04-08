// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.suidemocontracts.domain.product;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;

public class ProductCommands {
    private ProductCommands() {
    }

    public static class Create extends AbstractProductCommand implements ProductCommand {

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

