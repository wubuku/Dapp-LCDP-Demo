package org.dddml.suidemocontracts.domain.domainname;


public class DeleteDomainNameDto extends AbstractDomainNameCommandDto implements DomainNameCommand.DeleteDomainName
{

    public DeleteDomainNameDto() {
        this.commandType = COMMAND_TYPE_DELETE;
    }

    @Override
    public String getCommandType() {
        return COMMAND_TYPE_DELETE;
    }

    public DomainNameCommand.DeleteDomainName toDeleteDomainName()
    {
        AbstractDomainNameCommand.SimpleDeleteDomainName command = new AbstractDomainNameCommand.SimpleDeleteDomainName();
        ((AbstractDomainNameCommandDto)this).copyTo(command);
        return command;
    }
}

