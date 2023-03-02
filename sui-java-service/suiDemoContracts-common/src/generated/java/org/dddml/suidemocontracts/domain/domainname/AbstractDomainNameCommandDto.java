package org.dddml.suidemocontracts.domain.domainname;

import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.domain.AbstractCommand;

public abstract class AbstractDomainNameCommandDto extends AbstractCommand
{

    /**
     * Domain Name Id
     */
    private DomainNameId domainNameId;

    public DomainNameId getDomainNameId()
    {
        return this.domainNameId;
    }

    public void setDomainNameId(DomainNameId domainNameId)
    {
        this.domainNameId = domainNameId;
    }

    /**
     * Id
     */
    private String surrogateId;

    public String getSurrogateId()
    {
        return this.surrogateId;
    }

    public void setSurrogateId(String id)
    {
        this.surrogateId = id;
    }

    /**
     * Version
     */
    private Long version;

    public Long getVersion()
    {
        return this.version;
    }

    public void setVersion(Long version)
    {
        this.version = version;
    }


    public void copyTo(DomainNameCommand command) {
        command.setDomainNameId(this.getDomainNameId());
        command.setSurrogateId(this.getSurrogateId());
        command.setVersion(this.getVersion());
        
        command.setRequesterId(this.getRequesterId());
        command.setCommandId(this.getCommandId());
    }

}
