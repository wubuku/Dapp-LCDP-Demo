package org.dddml.suidemocontracts.domain.domainname;


public class DeleteDomainNameDto extends AbstractDomainNameCommandDto
{

    public DeleteDomainNameDto() {
        this.commandType = COMMAND_TYPE_DELETE;
    }

    @Override
    public String getCommandType() {
        return COMMAND_TYPE_DELETE;
    }

}

