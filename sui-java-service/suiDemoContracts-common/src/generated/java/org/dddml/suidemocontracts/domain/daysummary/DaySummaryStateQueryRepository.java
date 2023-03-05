package org.dddml.suidemocontracts.domain.daysummary;

import java.util.Map;
import java.util.List;
import org.dddml.support.criterion.Criterion;
import org.dddml.suidemocontracts.domain.*;
import java.math.BigInteger;
import java.util.Date;

public interface DaySummaryStateQueryRepository
{
    DaySummaryState get(Day id);

    Iterable<DaySummaryState> getAll(Integer firstResult, Integer maxResults);
    
    Iterable<DaySummaryState> get(Iterable<Map.Entry<String, Object>> filter, List<String> orders, Integer firstResult, Integer maxResults);

    Iterable<DaySummaryState> get(Criterion filter, List<String> orders, Integer firstResult, Integer maxResults);

    DaySummaryState getFirst(Iterable<Map.Entry<String, Object>> filter, List<String> orders);

    DaySummaryState getFirst(Map.Entry<String, Object> keyValue, List<String> orders);

    Iterable<DaySummaryState> getByProperty(String propertyName, Object propertyValue, List<String> orders, Integer firstResult, Integer maxResults);

    long getCount(Iterable<Map.Entry<String, Object>> filter);

    long getCount(Criterion filter);

}

