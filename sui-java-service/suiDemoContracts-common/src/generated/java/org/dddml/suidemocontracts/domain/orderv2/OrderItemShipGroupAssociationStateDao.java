package org.dddml.suidemocontracts.domain.orderv2;

import java.util.Date;
import org.dddml.suidemocontracts.domain.*;

public interface OrderItemShipGroupAssociationStateDao {

    /**
     * Get entity state.
     * @param id Entity global Id.
     * @param nullAllowed If returning null is a allowed. It is true for query.
     * @param aggregateState Aggregate state.
     * @return Entity state.
     */
    OrderItemShipGroupAssociationState get(OrderV2OrderItemShipGroupAssociationId id, boolean nullAllowed, OrderV2State aggregateState);

    void save(OrderItemShipGroupAssociationState state);

    Iterable<OrderItemShipGroupAssociationState> findByOrderV2OrderIdAndOrderShipGroupShipGroupSeqId(String orderV2OrderId, Integer orderShipGroupShipGroupSeqId, OrderV2State aggregateState);

    void delete(OrderItemShipGroupAssociationState state);
}


