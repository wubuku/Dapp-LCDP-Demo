// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.domain.order;

import java.util.Date;
import org.dddml.roochdemocontracts.domain.*;

public interface OrderItemShipGroupAssociationEventDao {
    void save(OrderItemShipGroupAssociationEvent e);

    Iterable<OrderItemShipGroupAssociationEvent> findByOrderShipGroupEventId(OrderShipGroupEventId orderShipGroupEventId);

}

