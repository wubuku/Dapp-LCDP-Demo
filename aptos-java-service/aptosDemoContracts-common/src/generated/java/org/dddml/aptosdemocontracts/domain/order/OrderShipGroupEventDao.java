// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.aptosdemocontracts.domain.order;

import java.util.Date;
import org.dddml.aptosdemocontracts.domain.*;

public interface OrderShipGroupEventDao {
    void save(OrderShipGroupEvent e);

    Iterable<OrderShipGroupEvent> findByOrderEventId(OrderEventId orderEventId);

}

