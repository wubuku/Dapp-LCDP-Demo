package org.dddml.suidemocontracts.domain.order;

import java.util.Map;
import java.util.List;
import org.dddml.support.criterion.Criterion;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;

public interface OrderStateQueryRepository
{
    OrderState get(String id);

    Iterable<OrderState> getAll(Integer firstResult, Integer maxResults);
    
    Iterable<OrderState> get(Iterable<Map.Entry<String, Object>> filter, List<String> orders, Integer firstResult, Integer maxResults);

    Iterable<OrderState> get(Criterion filter, List<String> orders, Integer firstResult, Integer maxResults);

    OrderState getFirst(Iterable<Map.Entry<String, Object>> filter, List<String> orders);

    OrderState getFirst(Map.Entry<String, Object> keyValue, List<String> orders);

    Iterable<OrderState> getByProperty(String propertyName, Object propertyValue, List<String> orders, Integer firstResult, Integer maxResults);

    long getCount(Iterable<Map.Entry<String, Object>> filter);

    long getCount(Criterion filter);

    OrderItemState getOrderItem(String orderId, String productId);

    Iterable<OrderItemState> getOrderItems(String orderId, Criterion filter, List<String> orders);

}

