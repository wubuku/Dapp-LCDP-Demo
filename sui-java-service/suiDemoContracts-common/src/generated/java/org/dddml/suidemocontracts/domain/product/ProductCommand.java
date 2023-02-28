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
        if ((c instanceof ProductCommand.CreateProduct) 
            && (COMMAND_TYPE_CREATE.equals(c.getCommandType()) || c.getVersion().equals(ProductState.VERSION_NULL)))
            return true;
        if (c.getVersion().equals(ProductState.VERSION_NULL))
            return true;
        return false;
    }

    interface CreateOrMergePatchProduct extends ProductCommand
    {

        String getName();

        void setName(String name);

        BigInteger getUnitPrice();

        void setUnitPrice(BigInteger unitPrice);

        Boolean getActive();

        void setActive(Boolean active);

    }

    interface CreateProduct extends CreateOrMergePatchProduct
    {
    }

}

