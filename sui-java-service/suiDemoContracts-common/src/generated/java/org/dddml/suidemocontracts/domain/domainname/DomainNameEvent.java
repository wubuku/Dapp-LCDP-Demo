package org.dddml.suidemocontracts.domain.domainname;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.Event;

public interface DomainNameEvent extends Event {

    interface SqlDomainNameEvent extends DomainNameEvent {
        DomainNameEventId getDomainNameEventId();

        boolean getEventReadOnly();

        void setEventReadOnly(boolean readOnly);
    }

    DomainNameId getDomainNameId();

    //void setDomainNameId(DomainNameId domainNameId);

    Long getVersion();
    
    //void setVersion(Long version);

    String getCreatedBy();

    void setCreatedBy(String createdBy);

    Date getCreatedAt();

    void setCreatedAt(Date createdAt);

    String getCommandId();

    void setCommandId(String commandId);

    interface DomainNameStateEvent extends DomainNameEvent {
        BigInteger getExpirationDate();

        void setExpirationDate(BigInteger expirationDate);

        Boolean getActive();

        void setActive(Boolean active);

    }

    interface DomainNameStateCreated extends DomainNameStateEvent
    {
    
    }


    interface DomainNameStateMergePatched extends DomainNameStateEvent
    {
        Boolean getIsPropertyExpirationDateRemoved();

        void setIsPropertyExpirationDateRemoved(Boolean removed);

        Boolean getIsPropertyActiveRemoved();

        void setIsPropertyActiveRemoved(Boolean removed);



    }

    interface DomainNameStateDeleted extends DomainNameStateEvent
    {
    }


}

