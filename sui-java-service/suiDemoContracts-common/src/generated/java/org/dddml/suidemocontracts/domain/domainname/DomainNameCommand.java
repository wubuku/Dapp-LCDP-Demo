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

    String getSurrogateId();

    void setSurrogateId(String id);

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
        if (c.getVersion().equals(DomainNameState.VERSION_NULL))
            return true;
        return false;
    }

}
