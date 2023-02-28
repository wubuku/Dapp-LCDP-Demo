package org.dddml.suidemocontracts.domain.domainname;

import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;

public class CreateOrMergePatchDomainNameDto extends AbstractDomainNameCommandDto implements DomainNameCommand.CreateOrMergePatchDomainName
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

    public void copyTo(CreateOrMergePatchDomainName command)
    {
        ((AbstractDomainNameCommandDto) this).copyTo(command);
        command.setExpirationDate(this.getExpirationDate());
        command.setActive(this.getActive());
    }

    public DomainNameCommand toCommand()
    {
        if (getCommandType() == null) {
            setCommandType(COMMAND_TYPE_MERGE_PATCH);
        }
        if (COMMAND_TYPE_CREATE.equals(getCommandType())) {
            AbstractDomainNameCommand.SimpleCreateDomainName command = new AbstractDomainNameCommand.SimpleCreateDomainName();
            copyTo((AbstractDomainNameCommand.AbstractCreateDomainName) command);
            return command;
        } else if (COMMAND_TYPE_MERGE_PATCH.equals(getCommandType())) {
            AbstractDomainNameCommand.SimpleMergePatchDomainName command = new AbstractDomainNameCommand.SimpleMergePatchDomainName();
            copyTo((AbstractDomainNameCommand.SimpleMergePatchDomainName) command);
            return command;
        } 
        throw new UnsupportedOperationException("Unknown command type:" + getCommandType());
    }


    public DomainNameCommand toSubclass() {
        if (getCommandType() == null) {
            setCommandType(COMMAND_TYPE_MERGE_PATCH);
        }
        if (COMMAND_TYPE_CREATE.equals(getCommandType()) || null == getCommandType()) {
            CreateDomainNameDto command = new CreateDomainNameDto();
            copyTo((CreateDomainName) command);
            return command;
        } else if (COMMAND_TYPE_MERGE_PATCH.equals(getCommandType())) {
            MergePatchDomainNameDto command = new MergePatchDomainNameDto();
            copyTo((MergePatchDomainName) command);
            return command;
        } 
        throw new UnsupportedOperationException("Unknown command type:" + getCommandType());
    }

    public void copyTo(CreateDomainName command)
    {
        copyTo((CreateOrMergePatchDomainName) command);
    }

    public void copyTo(MergePatchDomainName command)
    {
        copyTo((CreateOrMergePatchDomainName) command);
        command.setIsPropertyExpirationDateRemoved(this.getIsPropertyExpirationDateRemoved());
        command.setIsPropertyActiveRemoved(this.getIsPropertyActiveRemoved());
    }

    public static class CreateDomainNameDto extends CreateOrMergePatchDomainNameDto implements DomainNameCommand.CreateDomainName
    {
        public CreateDomainNameDto() {
            this.commandType = COMMAND_TYPE_CREATE;
        }

        @Override
        public String getCommandType() {
            return COMMAND_TYPE_CREATE;
        }
        public DomainNameCommand.CreateDomainName toCreateDomainName()
        {
            return (DomainNameCommand.CreateDomainName) toCommand();
        }

    }

    public static class MergePatchDomainNameDto extends CreateOrMergePatchDomainNameDto implements DomainNameCommand.MergePatchDomainName
    {
        public MergePatchDomainNameDto() {
            this.commandType = COMMAND_TYPE_MERGE_PATCH;
        }

        @Override
        public String getCommandType() {
            return COMMAND_TYPE_MERGE_PATCH;
        }
        public DomainNameCommand.MergePatchDomainName toMergePatchDomainName()
        {
            return (DomainNameCommand.MergePatchDomainName) toCommand();
        }

    }

}

