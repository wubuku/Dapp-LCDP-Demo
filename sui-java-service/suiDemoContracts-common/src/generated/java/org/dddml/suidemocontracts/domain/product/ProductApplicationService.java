package org.dddml.suidemocontracts.domain.product;

import java.util.Map;
import java.util.List;
import org.dddml.support.criterion.Criterion;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.Event;
import org.dddml.suidemocontracts.domain.Command;

public interface ProductApplicationService
{
    void when(ProductCommand.CreateProduct c);

    ProductState get(String id);

    Iterable<ProductState> getAll(Integer firstResult, Integer maxResults);

    Iterable<ProductState> get(Iterable<Map.Entry<String, Object>> filter, List<String> orders, Integer firstResult, Integer maxResults);

    Iterable<ProductState> get(Criterion filter, List<String> orders, Integer firstResult, Integer maxResults);

    Iterable<ProductState> getByProperty(String propertyName, Object propertyValue, List<String> orders, Integer firstResult, Integer maxResults);

    long getCount(Iterable<Map.Entry<String, Object>> filter);

    long getCount(Criterion filter);

    ProductEvent getEvent(String productId, long version);

    ProductState getHistoryState(String productId, long version);

}

