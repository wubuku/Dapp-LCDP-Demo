package org.dddml.suidemocontracts.domain.orderv2;

import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;

public interface OrderV2ItemStateDao {

    /**
     * Get entity state.
     * @param id Entity global Id.
     * @param nullAllowed If returning null is a allowed. It is true for query.
     * @param aggregateState Aggregate state.
     * @return Entity state.
     */
    OrderV2ItemState get(OrderV2ItemId id, boolean nullAllowed, OrderV2State aggregateState);

    void save(OrderV2ItemState state);

    Iterable<OrderV2ItemState> findByOrderV2OrderId(String orderV2OrderId, OrderV2State aggregateState);

    void delete(OrderV2ItemState state);
}


