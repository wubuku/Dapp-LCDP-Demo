// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.suidemocontracts.domain.domainname;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.Event;

public interface DomainNameEvent extends Event, SuiEventEnvelope, SuiMoveEvent, HasStatus {

    interface SqlDomainNameEvent extends DomainNameEvent {
        DomainNameEventId getDomainNameEventId();

        boolean getEventReadOnly();

        void setEventReadOnly(boolean readOnly);
    }

    interface Registered extends DomainNameEvent {
        BigInteger getRegistrationPeriod();

        void setRegistrationPeriod(BigInteger value);

        String getOwner();

        void setOwner(String value);

    }

    interface Renewed extends DomainNameEvent {
        BigInteger getRenewPeriod();

        void setRenewPeriod(BigInteger value);

        String getAccount();

        void setAccount(String value);

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

