package org.dddml.suidemocontracts.domain.order;

import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;

public interface OrderItemStateDao {

    /**
     * Get entity state.
     * @param id Entity global Id.
     * @param nullAllowed If returning null is a allowed. It is true for query.
     * @param aggregateState Aggregate state.
     * @return Entity state.
     */
    OrderItemState get(OrderItemId id, boolean nullAllowed, OrderState aggregateState);

    void save(OrderItemState state);

    Iterable<OrderItemState> findByOrderId(String orderId, OrderState aggregateState);

    void delete(OrderItemState state);
}


