// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.suidemocontracts.sui.contract.repository;

import org.dddml.suidemocontracts.domain.domainname.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface DomainNameEventRepository extends JpaRepository<AbstractDomainNameEvent, DomainNameEventId> {

    List<AbstractDomainNameEvent> findByStatusIsNull();

    AbstractDomainNameEvent.Registered findFirstRegisteredByOrderBySuiTimestampDesc();

    AbstractDomainNameEvent.Renewed findFirstRenewedByOrderBySuiTimestampDesc();

}
