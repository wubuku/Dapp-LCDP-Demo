package org.dddml.suidemocontracts.domain.domainname.hibernate;

import java.io.Serializable;
import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.*;
import org.dddml.suidemocontracts.specialization.hibernate.AbstractHibernateEventStore;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.springframework.transaction.annotation.Transactional;
import org.dddml.suidemocontracts.domain.domainname.*;

public class HibernateDomainNameEventStore extends AbstractHibernateEventStore
{
    @Override
    protected Serializable getEventId(EventStoreAggregateId eventStoreAggregateId, long version)
    {
        return new DomainNameEventId((DomainNameId) eventStoreAggregateId.getId(), version);
    }

    @Override
    protected Class getSupportedEventType()
    {
        return AbstractDomainNameEvent.class;
    }

    @Transactional(readOnly = true)
    @Override
    public EventStream loadEventStream(Class eventType, EventStoreAggregateId eventStoreAggregateId, long version) {
        Class supportedEventType = AbstractDomainNameEvent.class;
        if (!eventType.isAssignableFrom(supportedEventType)) {
            throw new UnsupportedOperationException();
        }
        DomainNameId idObj = (DomainNameId) eventStoreAggregateId.getId();
        Criteria criteria = getCurrentSession().createCriteria(AbstractDomainNameEvent.class);
        criteria.add(Restrictions.eq("domainNameEventId.domainNameIdTopLevelDomain", idObj.getTopLevelDomain()));
        criteria.add(Restrictions.eq("domainNameEventId.domainNameIdSecondLevelDomain", idObj.getSecondLevelDomain()));
        criteria.add(Restrictions.le("domainNameEventId.offChainVersion", version));
        criteria.addOrder(Order.asc("domainNameEventId.offChainVersion"));
        List es = criteria.list();
        for (Object e : es) {
            ((AbstractDomainNameEvent) e).setEventReadOnly(true);
        }
        EventStream eventStream = new EventStream();
        if (es.size() > 0) {
            eventStream.setSteamVersion(((AbstractDomainNameEvent) es.get(es.size() - 1)).getDomainNameEventId().getOffChainVersion());
        } else {
            //todo?
        }
        eventStream.setEvents(es);
        return eventStream;
    }

}

