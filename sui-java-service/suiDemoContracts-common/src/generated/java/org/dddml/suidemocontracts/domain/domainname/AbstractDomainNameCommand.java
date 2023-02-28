package org.dddml.suidemocontracts.domain.domainname;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.domain.AbstractCommand;

public abstract class AbstractDomainNameCommand extends AbstractCommand implements DomainNameCommand
{

    private DomainNameId domainNameId;

    public DomainNameId getDomainNameId()
    {
        return this.domainNameId;
    }

    public void setDomainNameId(DomainNameId domainNameId)
    {
        this.domainNameId = domainNameId;
    }

    private Long version;

    public Long getVersion()
    {
        return this.version;
    }

    public void setVersion(Long version)
    {
        this.version = version;
    }


    public static abstract class AbstractCreateOrMergePatchDomainName extends AbstractDomainNameCommand implements CreateOrMergePatchDomainName
    {

        private BigInteger expirationDate;

        public BigInteger getExpirationDate()
        {
            return this.expirationDate;
        }

        public void setExpirationDate(BigInteger expirationDate)
        {
            this.expirationDate = expirationDate;
        }

        private Boolean active;

        public Boolean getActive()
        {
            return this.active;
        }

        public void setActive(Boolean active)
        {
            this.active = active;
        }

    }

    public static abstract class AbstractCreateDomainName extends AbstractCreateOrMergePatchDomainName implements CreateDomainName
    {
        @Override
        public String getCommandType() {
            return COMMAND_TYPE_CREATE;
        }

    }

    public static abstract class AbstractMergePatchDomainName extends AbstractCreateOrMergePatchDomainName implements MergePatchDomainName
    {
        @Override
        public String getCommandType() {
            return COMMAND_TYPE_MERGE_PATCH;
        }

        private Boolean isPropertyExpirationDateRemoved;

        public Boolean getIsPropertyExpirationDateRemoved()
        {
            return this.isPropertyExpirationDateRemoved;
        }

        public void setIsPropertyExpirationDateRemoved(Boolean removed)
        {
            this.isPropertyExpirationDateRemoved = removed;
        }

        private Boolean isPropertyActiveRemoved;

        public Boolean getIsPropertyActiveRemoved()
        {
            return this.isPropertyActiveRemoved;
        }

        public void setIsPropertyActiveRemoved(Boolean removed)
        {
            this.isPropertyActiveRemoved = removed;
        }


    }

    public static class SimpleCreateDomainName extends AbstractCreateDomainName
    {
    }

    
    public static class SimpleMergePatchDomainName extends AbstractMergePatchDomainName
    {
    }

    
	public static class SimpleDeleteDomainName extends AbstractDomainNameCommand implements DeleteDomainName
	{
        @Override
        public String getCommandType() {
            return COMMAND_TYPE_DELETE;
        }
	}

    

}

