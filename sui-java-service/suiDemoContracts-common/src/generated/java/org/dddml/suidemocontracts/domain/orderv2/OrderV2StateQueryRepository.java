package org.dddml.suidemocontracts.domain.orderv2;

import java.util.Map;
import java.util.List;
import org.dddml.support.criterion.Criterion;
import java.math.BigInteger;
import org.dddml.suidemocontracts.domain.*;
import java.util.Date;

public interface OrderV2StateQueryRepository
{
    OrderV2State get(String id);

    Iterable<OrderV2State> getAll(Integer firstResult, Integer maxResults);
    
    Iterable<OrderV2State> get(Iterable<Map.Entry<String, Object>> filter, List<String> orders, Integer firstResult, Integer maxResults);

    Iterable<OrderV2State> get(Criterion filter, List<String> orders, Integer firstResult, Integer maxResults);

    OrderV2State getFirst(Iterable<Map.Entry<String, Object>> filter, List<String> orders);

    OrderV2State getFirst(Map.Entry<String, Object> keyValue, List<String> orders);

    Iterable<OrderV2State> getByProperty(String propertyName, Object propertyValue, List<String> orders, Integer firstResult, Integer maxResults);

    long getCount(Iterable<Map.Entry<String, Object>> filter);

    long getCount(Criterion filter);

    OrderV2ItemState getOrderV2Item(String orderV2OrderId, String productId);

    Iterable<OrderV2ItemState> getOrderV2Items(String orderV2OrderId, Criterion filter, List<String> orders);

    OrderShipGroupState getOrderShipGroup(String orderV2OrderId, Integer shipGroupSeqId);

    Iterable<OrderShipGroupState> getOrderShipGroups(String orderV2OrderId, Criterion filter, List<String> orders);

    OrderItemShipGroupAssociationState getOrderItemShipGroupAssociation(String orderV2OrderId, Integer orderShipGroupShipGroupSeqId, String productId);

    Iterable<OrderItemShipGroupAssociationState> getOrderItemShipGroupAssociations(String orderV2OrderId, Integer orderShipGroupShipGroupSeqId, Criterion filter, List<String> orders);

    OrderItemShipGroupAssocSubitemState getOrderItemShipGroupAssocSubitem(String orderV2OrderId, Integer orderShipGroupShipGroupSeqId, String orderItemShipGroupAssociationProductId, Day orderItemShipGroupAssocSubitemDay);

    Iterable<OrderItemShipGroupAssocSubitemState> getOrderItemShipGroupAssocSubitems(String orderV2OrderId, Integer orderShipGroupShipGroupSeqId, String orderItemShipGroupAssociationProductId, Criterion filter, List<String> orders);

}

