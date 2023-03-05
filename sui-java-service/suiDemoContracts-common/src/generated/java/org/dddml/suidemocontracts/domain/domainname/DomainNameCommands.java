package org.dddml.suidemocontracts.domain.domainname;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;

public class DomainNameCommands
{
    private DomainNameCommands() {
    }

    public static class Register extends AbstractDomainNameCommand implements DomainNameCommand {

        public String getCommandType() {
            return "Register";
        }

        public void setCommandType(String commandType) {
            //do nothing
        }

        /**
         * Domain Name Id
         */
        private DomainNameId domainNameId;

        public DomainNameId getDomainNameId() {
            return this.domainNameId;
        }

        public void setDomainNameId(DomainNameId domainNameId) {
            this.domainNameId = domainNameId;
        }

        /**
         * Registration Period
         */
        private BigInteger registrationPeriod;

        public BigInteger getRegistrationPeriod() {
            return this.registrationPeriod;
        }

        public void setRegistrationPeriod(BigInteger registrationPeriod) {
            this.registrationPeriod = registrationPeriod;
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

    public static class Renew extends AbstractDomainNameCommand implements DomainNameCommand {

        public String getCommandType() {
            return "Renew";
        }

        public void setCommandType(String commandType) {
            //do nothing
        }

        /**
         * Domain Name Id
         */
        private DomainNameId domainNameId;

        public DomainNameId getDomainNameId() {
            return this.domainNameId;
        }

        public void setDomainNameId(DomainNameId domainNameId) {
            this.domainNameId = domainNameId;
        }

        /**
         * Renew Period
         */
        private BigInteger renewPeriod;

        public BigInteger getRenewPeriod() {
            return this.renewPeriod;
        }

        public void setRenewPeriod(BigInteger renewPeriod) {
            this.renewPeriod = renewPeriod;
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

