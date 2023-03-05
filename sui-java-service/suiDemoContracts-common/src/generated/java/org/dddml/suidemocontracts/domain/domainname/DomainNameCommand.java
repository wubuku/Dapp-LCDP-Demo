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

    String getId_();

    void setId_(String id);

    Long getOffChainVersion();

    void setOffChainVersion(Long offChainVersion);

    static void throwOnInvalidStateTransition(DomainNameState state, Command c) {
        if (state.getOffChainVersion() == null) {
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
        if (c.getOffChainVersion().equals(DomainNameState.VERSION_NULL))
            return true;
        return false;
    }

}

