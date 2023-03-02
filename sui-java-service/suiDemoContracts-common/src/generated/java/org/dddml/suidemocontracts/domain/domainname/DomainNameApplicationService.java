package org.dddml.suidemocontracts.domain.domainname;

import java.util.Map;
import java.util.List;
import org.dddml.support.criterion.Criterion;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.Event;
import org.dddml.suidemocontracts.domain.Command;

public interface DomainNameApplicationService
{
    void when(DomainNameCommands.Register c);

    void when(DomainNameCommands.Renew c);

    DomainNameState get(DomainNameId id);

    Iterable<DomainNameState> getAll(Integer firstResult, Integer maxResults);

    Iterable<DomainNameState> get(Iterable<Map.Entry<String, Object>> filter, List<String> orders, Integer firstResult, Integer maxResults);

    Iterable<DomainNameState> get(Criterion filter, List<String> orders, Integer firstResult, Integer maxResults);

    Iterable<DomainNameState> getByProperty(String propertyName, Object propertyValue, List<String> orders, Integer firstResult, Integer maxResults);

    long getCount(Iterable<Map.Entry<String, Object>> filter);

    long getCount(Criterion filter);

    DomainNameEvent getEvent(DomainNameId domainNameId, long version);

    DomainNameState getHistoryState(DomainNameId domainNameId, long version);

}

