package org.dddml.suidemocontracts.domain.orderv2;

import java.util.Date;
import org.dddml.suidemocontracts.domain.*;

public interface OrderShipGroupStateDao {

    /**
     * Get entity state.
     * @param id Entity global Id.
     * @param nullAllowed If returning null is a allowed. It is true for query.
     * @param aggregateState Aggregate state.
     * @return Entity state.
     */
    OrderShipGroupState get(OrderV2OrderShipGroupId id, boolean nullAllowed, OrderV2State aggregateState);

    void save(OrderShipGroupState state);

    Iterable<OrderShipGroupState> findByOrderV2OrderId(String orderV2OrderId, OrderV2State aggregateState);

    void delete(OrderShipGroupState state);
}


