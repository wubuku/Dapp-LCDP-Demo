package org.dddml.suidemocontracts.domain.product;

import java.util.Set;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.Event;

public interface ProductState extends VersionedSuiMoveObject
{
    Long VERSION_ZERO = 0L;

    Long VERSION_NULL = VERSION_ZERO - 1;

    String getProductId();

    String getId_();

    String getName();

    BigInteger getUnitPrice();

    Long getOffChainVersion();

    String getCreatedBy();

    Date getCreatedAt();

    String getUpdatedBy();

    Date getUpdatedAt();

    Boolean getActive();

    Boolean getDeleted();

    interface MutableProductState extends ProductState, VersionedSuiMoveObject.MutableVersionedSuiMoveObject {
        void setProductId(String productId);

        void setId_(String id);

        void setName(String name);

        void setUnitPrice(BigInteger unitPrice);

        void setOffChainVersion(Long offChainVersion);

        void setCreatedBy(String createdBy);

        void setCreatedAt(Date createdAt);

        void setUpdatedBy(String updatedBy);

        void setUpdatedAt(Date updatedAt);

        void setActive(Boolean active);

        void setDeleted(Boolean deleted);


        void mutate(Event e);

        //void when(ProductEvent.ProductStateCreated e);

        //void when(ProductEvent.ProductStateMergePatched e);

        //void when(ProductEvent.ProductStateDeleted e);
    }

    interface SqlProductState extends MutableProductState {

        boolean isStateUnsaved();

        boolean getForReapplying();
    }
}

