// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.rooch.contract.repository;

import org.dddml.roochdemocontracts.domain.product.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface ProductEventRepository extends JpaRepository<AbstractProductEvent, ProductEventId> {

    List<AbstractProductEvent> findByStatusIsNull();

    AbstractProductEvent.ProductCreated findFirstProductCreatedByOrderByRoochEventId_EventSeqDesc();

}
