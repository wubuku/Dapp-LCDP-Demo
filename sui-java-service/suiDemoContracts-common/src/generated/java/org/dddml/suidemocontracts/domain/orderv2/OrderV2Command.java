package org.dddml.suidemocontracts.domain.orderv2;

import java.util.*;
import java.math.BigInteger;
import org.dddml.suidemocontracts.domain.*;
import java.util.Date;
import org.dddml.suidemocontracts.domain.Command;
import org.dddml.suidemocontracts.specialization.DomainError;

public interface OrderV2Command extends Command
{

    String getOrderId();

    void setOrderId(String orderId);

    String getId_();

    void setId_(String id);

    Long getOffChainVersion();

    void setOffChainVersion(Long offChainVersion);

    static void throwOnInvalidStateTransition(OrderV2State state, Command c) {
        if (state.getOffChainVersion() == null) {
            if (isCommandCreate((OrderV2Command)c)) {
                return;
            }
            throw DomainError.named("premature", "Can't do anything to unexistent aggregate");
        }
        if (state.getDeleted() != null && state.getDeleted()) {
            throw DomainError.named("zombie", "Can't do anything to deleted aggregate.");
        }
        if (isCommandCreate((OrderV2Command)c))
            throw DomainError.named("rebirth", "Can't create aggregate that already exists");
    }

    static boolean isCommandCreate(OrderV2Command c) {
        if (c.getOffChainVersion().equals(OrderV2State.VERSION_NULL))
            return true;
        return false;
    }

}

