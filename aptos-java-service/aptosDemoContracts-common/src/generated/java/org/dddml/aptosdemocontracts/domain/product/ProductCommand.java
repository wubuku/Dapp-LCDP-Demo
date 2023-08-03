// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.aptosdemocontracts.domain.product;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.aptosdemocontracts.domain.*;
import org.dddml.aptosdemocontracts.domain.Command;
import org.dddml.aptosdemocontracts.specialization.DomainError;

public interface ProductCommand extends Command {

    String getProductId();

    void setProductId(String productId);

    Long getOffChainVersion();

    void setOffChainVersion(Long offChainVersion);

    static void throwOnInvalidStateTransition(ProductState state, Command c) {
        if (state.getOffChainVersion() == null) {
            if (isCommandCreate((ProductCommand)c)) {
                return;
            }
            throw DomainError.named("premature", "Can't do anything to unexistent aggregate");
        }
        if (state.getDeleted() != null && state.getDeleted()) {
            throw DomainError.named("zombie", "Can't do anything to deleted aggregate.");
        }
        if (isCommandCreate((ProductCommand)c))
            throw DomainError.named("rebirth", "Can't create aggregate that already exists");
    }

    static boolean isCommandCreate(ProductCommand c) {
        if (c.getOffChainVersion().equals(ProductState.VERSION_NULL))
            return true;
        return false;
    }

}

