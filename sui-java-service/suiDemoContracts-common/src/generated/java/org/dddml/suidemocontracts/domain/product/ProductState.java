package org.dddml.suidemocontracts.domain.product;

import java.util.Set;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.Event;

public interface ProductState
{
    Long VERSION_ZERO = 0L;

    Long VERSION_NULL = VERSION_ZERO - 1;

    String getProductId();

    String getName();

    BigInteger getUnitPrice();

    Long getVersion();

    String getCreatedBy();

    Date getCreatedAt();

    String getUpdatedBy();

    Date getUpdatedAt();

    Boolean getActive();

    String getCommandId();

    interface MutableProductState extends ProductState {
        void setProductId(String productId);

        void setName(String name);

        void setUnitPrice(BigInteger unitPrice);

        void setVersion(Long version);

        void setCreatedBy(String createdBy);

        void setCreatedAt(Date createdAt);

        void setUpdatedBy(String updatedBy);

        void setUpdatedAt(Date updatedAt);

        void setActive(Boolean active);

        void setCommandId(String commandId);


        void mutate(Event e);

        //void when(ProductEvent.ProductStateCreated e);

    }

    interface SqlProductState extends MutableProductState {

        boolean isStateUnsaved();

        boolean getForReapplying();
    }
}

