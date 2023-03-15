package org.dddml.suidemocontracts.domain.orderv2;

import java.util.Date;
import org.dddml.suidemocontracts.domain.*;

public interface OrderItemShipGroupAssocSubitemStateDao {

    /**
     * Get entity state.
     * @param id Entity global Id.
     * @param nullAllowed If returning null is a allowed. It is true for query.
     * @param aggregateState Aggregate state.
     * @return Entity state.
     */
    OrderItemShipGroupAssocSubitemState get(OrderV2OrderItemShipGroupAssocSubitemId id, boolean nullAllowed, OrderV2State aggregateState);

    void save(OrderItemShipGroupAssocSubitemState state);

    Iterable<OrderItemShipGroupAssocSubitemState> findByOrderV2OrderIdAndOrderShipGroupShipGroupSeqIdAndOrderItemShipGroupAssociationProductId(String orderV2OrderId, Integer orderShipGroupShipGroupSeqId, String orderItemShipGroupAssociationProductId, OrderV2State aggregateState);

    void delete(OrderItemShipGroupAssocSubitemState state);
}


