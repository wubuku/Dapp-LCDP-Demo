package org.dddml.suidemocontracts.domain.order.hibernate;

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
import org.dddml.suidemocontracts.domain.order.*;

public class HibernateOrderEventStore extends AbstractHibernateEventStore
{
    @Override
    protected Serializable getEventId(EventStoreAggregateId eventStoreAggregateId, long version)
    {
        return new OrderEventId((String) eventStoreAggregateId.getId(), version);
    }

    @Override
    protected Class getSupportedEventType()
    {
        return AbstractOrderEvent.class;
    }

    @Transactional(readOnly = true)
    @Override
    public EventStream loadEventStream(Class eventType, EventStoreAggregateId eventStoreAggregateId, long version) {
        Class supportedEventType = AbstractOrderEvent.class;
        if (!eventType.isAssignableFrom(supportedEventType)) {
            throw new UnsupportedOperationException();
        }
        String idObj = (String) eventStoreAggregateId.getId();
        Criteria criteria = getCurrentSession().createCriteria(AbstractOrderEvent.class);
        criteria.add(Restrictions.eq("orderEventId.id", idObj));
        criteria.add(Restrictions.le("orderEventId.version", version));
        criteria.addOrder(Order.asc("orderEventId.version"));
        List es = criteria.list();
        for (Object e : es) {
            ((AbstractOrderEvent) e).setEventReadOnly(true);
        }
        EventStream eventStream = new EventStream();
        if (es.size() > 0) {
            eventStream.setSteamVersion(((AbstractOrderEvent) es.get(es.size() - 1)).getOrderEventId().getVersion());
        } else {
            //todo?
        }
        eventStream.setEvents(es);
        return eventStream;
    }

}

