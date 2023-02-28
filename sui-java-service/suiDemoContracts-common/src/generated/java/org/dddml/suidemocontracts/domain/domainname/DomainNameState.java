package org.dddml.suidemocontracts.domain.domainname;

import java.util.Set;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.Event;

public interface DomainNameState
{
    Long VERSION_ZERO = 0L;

    Long VERSION_NULL = VERSION_ZERO - 1;

    DomainNameId getDomainNameId();

    BigInteger getExpirationDate();

    Long getVersion();

    String getCreatedBy();

    Date getCreatedAt();

    String getUpdatedBy();

    Date getUpdatedAt();

    Boolean getActive();

    Boolean getDeleted();

    interface MutableDomainNameState extends DomainNameState {
        void setDomainNameId(DomainNameId domainNameId);

        void setExpirationDate(BigInteger expirationDate);

        void setVersion(Long version);

        void setCreatedBy(String createdBy);

        void setCreatedAt(Date createdAt);

        void setUpdatedBy(String updatedBy);

        void setUpdatedAt(Date updatedAt);

        void setActive(Boolean active);

        void setDeleted(Boolean deleted);


        void mutate(Event e);

        //void when(DomainNameEvent.DomainNameStateCreated e);

        //void when(DomainNameEvent.DomainNameStateMergePatched e);

        //void when(DomainNameEvent.DomainNameStateDeleted e);
    }

    interface SqlDomainNameState extends MutableDomainNameState {

        boolean isStateUnsaved();

        boolean getForReapplying();
    }
}

