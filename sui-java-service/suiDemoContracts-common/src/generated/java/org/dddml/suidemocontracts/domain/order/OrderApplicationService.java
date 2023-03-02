package org.dddml.suidemocontracts.domain.order;

import java.util.Map;
import java.util.List;
import org.dddml.support.criterion.Criterion;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.Event;
import org.dddml.suidemocontracts.domain.Command;

public interface OrderApplicationService
{
    void when(OrderCommands.Create c);

    void when(OrderCommands.RemoveItem c);

    void when(OrderCommands.UpdateItemQuantity c);

    OrderState get(String id);

    Iterable<OrderState> getAll(Integer firstResult, Integer maxResults);

    Iterable<OrderState> get(Iterable<Map.Entry<String, Object>> filter, List<String> orders, Integer firstResult, Integer maxResults);

    Iterable<OrderState> get(Criterion filter, List<String> orders, Integer firstResult, Integer maxResults);

    Iterable<OrderState> getByProperty(String propertyName, Object propertyValue, List<String> orders, Integer firstResult, Integer maxResults);

    long getCount(Iterable<Map.Entry<String, Object>> filter);

    long getCount(Criterion filter);

    OrderEvent getEvent(String id, long version);

    OrderState getHistoryState(String id, long version);

    OrderItemState getOrderItem(String orderId, String productId);

    Iterable<OrderItemState> getOrderItems(String orderId, Criterion filter, List<String> orders);

}

