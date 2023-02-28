package org.dddml.suidemocontracts.domain.domainname;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.domain.Command;
import org.dddml.suidemocontracts.specialization.DomainError;

public interface DomainNameCommand extends Command
{

    DomainNameId getDomainNameId();

    void setDomainNameId(DomainNameId domainNameId);

    Long getVersion();

    void setVersion(Long version);

    static void throwOnInvalidStateTransition(DomainNameState state, Command c) {
        if (state.getVersion() == null) {
            if (isCommandCreate((DomainNameCommand)c)) {
                return;
            }
            throw DomainError.named("premature", "Can't do anything to unexistent aggregate");
        }
        if (state.getDeleted() != null && state.getDeleted()) {
            throw DomainError.named("zombie", "Can't do anything to deleted aggregate.");
        }
        if (isCommandCreate((DomainNameCommand)c))
            throw DomainError.named("rebirth", "Can't create aggregate that already exists");
    }

    static boolean isCommandCreate(DomainNameCommand c) {
        if ((c instanceof DomainNameCommand.CreateDomainName) 
            && (COMMAND_TYPE_CREATE.equals(c.getCommandType()) || c.getVersion().equals(DomainNameState.VERSION_NULL)))
            return true;
        if ((c instanceof DomainNameCommand.MergePatchDomainName))
            return false;
        if ((c instanceof DomainNameCommand.DeleteDomainName))
            return false;
        if (c.getVersion().equals(DomainNameState.VERSION_NULL))
            return true;
        return false;
    }

    interface CreateOrMergePatchDomainName extends DomainNameCommand
    {

        BigInteger getExpirationDate();

        void setExpirationDate(BigInteger expirationDate);

        Boolean getActive();

        void setActive(Boolean active);

    }

    interface CreateDomainName extends CreateOrMergePatchDomainName
    {
    }

    interface MergePatchDomainName extends CreateOrMergePatchDomainName
    {
        Boolean getIsPropertyExpirationDateRemoved();

        void setIsPropertyExpirationDateRemoved(Boolean removed);

        Boolean getIsPropertyActiveRemoved();

        void setIsPropertyActiveRemoved(Boolean removed);


    }

	interface DeleteDomainName extends DomainNameCommand
	{
	}

}

