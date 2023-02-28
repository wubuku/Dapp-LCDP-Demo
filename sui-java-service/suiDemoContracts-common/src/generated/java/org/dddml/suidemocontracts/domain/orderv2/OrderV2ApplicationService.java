package org.dddml.suidemocontracts.domain.orderv2;

import java.util.Map;
import java.util.List;
import org.dddml.support.criterion.Criterion;
import java.math.BigInteger;
import org.dddml.suidemocontracts.domain.*;
import java.util.Date;
import org.dddml.suidemocontracts.specialization.Event;
import org.dddml.suidemocontracts.domain.Command;

public interface OrderV2ApplicationService
{
    void when(OrderV2Command.CreateOrderV2 c);

    void when(OrderV2Command.MergePatchOrderV2 c);

    void when(OrderV2Command.DeleteOrderV2 c);

    void when(OrderV2Commands.RemoveItem c);

    void when(OrderV2Commands.UpdateItemQuantity c);

    void when(OrderV2Commands.UpdateEstimatedShipDate c);

    OrderV2State get(String id);

    Iterable<OrderV2State> getAll(Integer firstResult, Integer maxResults);

    Iterable<OrderV2State> get(Iterable<Map.Entry<String, Object>> filter, List<String> orders, Integer firstResult, Integer maxResults);

    Iterable<OrderV2State> get(Criterion filter, List<String> orders, Integer firstResult, Integer maxResults);

    Iterable<OrderV2State> getByProperty(String propertyName, Object propertyValue, List<String> orders, Integer firstResult, Integer maxResults);

    long getCount(Iterable<Map.Entry<String, Object>> filter);

    long getCount(Criterion filter);

    OrderV2Event getEvent(String orderId, long version);

    OrderV2State getHistoryState(String orderId, long version);

    OrderV2ItemState getOrderV2Item(String orderV2OrderId, String productId);

    Iterable<OrderV2ItemState> getOrderV2Items(String orderV2OrderId, Criterion filter, List<String> orders);

}

