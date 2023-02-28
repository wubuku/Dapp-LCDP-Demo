package org.dddml.suidemocontracts.domain.domainname;

import java.util.Map;
import java.util.List;
import org.dddml.support.criterion.Criterion;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;

public interface DomainNameStateQueryRepository
{
    DomainNameState get(DomainNameId id);

    Iterable<DomainNameState> getAll(Integer firstResult, Integer maxResults);
    
    Iterable<DomainNameState> get(Iterable<Map.Entry<String, Object>> filter, List<String> orders, Integer firstResult, Integer maxResults);

    Iterable<DomainNameState> get(Criterion filter, List<String> orders, Integer firstResult, Integer maxResults);

    DomainNameState getFirst(Iterable<Map.Entry<String, Object>> filter, List<String> orders);

    DomainNameState getFirst(Map.Entry<String, Object> keyValue, List<String> orders);

    Iterable<DomainNameState> getByProperty(String propertyName, Object propertyValue, List<String> orders, Integer firstResult, Integer maxResults);

    long getCount(Iterable<Map.Entry<String, Object>> filter);

    long getCount(Criterion filter);

}

