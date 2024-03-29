// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.suidemocontracts.domain.domainname;

import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.domain.AbstractCommand;

public abstract class AbstractDomainNameCommandDto extends AbstractCommand {

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
    private String id_;

    public String getId_()
    {
        return this.id_;
    }

    public void setId_(String id)
    {
        this.id_ = id;
    }

    /**
     * Off Chain Version
     */
    private Long offChainVersion;

    public Long getOffChainVersion()
    {
        return this.offChainVersion;
    }

    public void setOffChainVersion(Long offChainVersion)
    {
        this.offChainVersion = offChainVersion;
    }


    public void copyTo(DomainNameCommand command) {
        command.setDomainNameId(this.getDomainNameId());
        command.setId_(this.getId_());
        command.setOffChainVersion(this.getOffChainVersion());
        
        command.setRequesterId(this.getRequesterId());
        command.setCommandId(this.getCommandId());
    }

}
