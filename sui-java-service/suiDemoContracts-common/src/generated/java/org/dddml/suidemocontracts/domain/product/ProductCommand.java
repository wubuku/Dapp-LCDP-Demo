package org.dddml.suidemocontracts.domain.product;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.domain.Command;
import org.dddml.suidemocontracts.specialization.DomainError;

public interface ProductCommand extends Command
{

    String getProductId();

    void setProductId(String productId);

    String getId_();

    void setId_(String id);

    Long getVersion();

    void setVersion(Long version);

    static void throwOnInvalidStateTransition(ProductState state, Command c) {
        if (state.getVersion() == null) {
            if (isCommandCreate((ProductCommand)c)) {
                return;
            }
            throw DomainError.named("premature", "Can't do anything to unexistent aggregate");
        }
        if (isCommandCreate((ProductCommand)c))
            throw DomainError.named("rebirth", "Can't create aggregate that already exists");
    }

    static boolean isCommandCreate(ProductCommand c) {
        if (c.getVersion().equals(ProductState.VERSION_NULL))
            return true;
        return false;
    }

}

