package org.dddml.suidemocontracts.domain.domainname;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.Event;

public interface DomainNameEvent extends Event, SuiEventEnvelope, SuiMoveEvent {

    interface SqlDomainNameEvent extends DomainNameEvent {
        DomainNameEventId getDomainNameEventId();

        boolean getEventReadOnly();

        void setEventReadOnly(boolean readOnly);
    }

    DomainNameId getDomainNameId();

    //void setDomainNameId(DomainNameId domainNameId);

    BigInteger getVersion();
    
    //void setVersion(BigInteger version);

    String getId_();
    
    //void setId_(String id);

    String getCreatedBy();

    void setCreatedBy(String createdBy);

    Date getCreatedAt();

    void setCreatedAt(Date createdAt);

    String getCommandId();

    void setCommandId(String commandId);


}

