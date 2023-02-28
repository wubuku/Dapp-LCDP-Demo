package org.dddml.suidemocontracts.domain.domainname;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;

public class DomainNameCommandDtos
{
    private DomainNameCommandDtos() {
    }

    public static class RegisterDto extends org.dddml.suidemocontracts.domain.AbstractCommand {

        public String getCommandType() {
            return "Register";
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

    public static class RenewDto extends org.dddml.suidemocontracts.domain.AbstractCommand {

        public String getCommandType() {
            return "Renew";
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

