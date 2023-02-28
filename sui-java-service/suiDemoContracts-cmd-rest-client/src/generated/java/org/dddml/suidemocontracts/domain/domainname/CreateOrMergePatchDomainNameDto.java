package org.dddml.suidemocontracts.domain.domainname;

import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;

public class CreateOrMergePatchDomainNameDto extends AbstractDomainNameCommandDto
{

    /**
     * Expiration Date
     */
    private BigInteger expirationDate;

    public BigInteger getExpirationDate()
    {
        return this.expirationDate;
    }

    public void setExpirationDate(BigInteger expirationDate)
    {
        this.expirationDate = expirationDate;
    }

    /**
     * Active
     */
    private Boolean active;

    public Boolean getActive()
    {
        return this.active;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
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

    public static class CreateDomainNameDto extends CreateOrMergePatchDomainNameDto
    {
        public CreateDomainNameDto() {
            this.commandType = COMMAND_TYPE_CREATE;
        }

        @Override
        public String getCommandType() {
            return COMMAND_TYPE_CREATE;
        }

    }

    public static class MergePatchDomainNameDto extends CreateOrMergePatchDomainNameDto
    {
        public MergePatchDomainNameDto() {
            this.commandType = COMMAND_TYPE_MERGE_PATCH;
        }

        @Override
        public String getCommandType() {
            return COMMAND_TYPE_MERGE_PATCH;
        }

    }

}

