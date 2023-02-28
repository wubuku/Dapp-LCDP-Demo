package org.dddml.suidemocontracts.domain.orderv2.hibernate;

import java.io.Serializable;
import java.util.*;
import java.math.BigInteger;
import org.dddml.suidemocontracts.domain.*;
import java.util.Date;
import org.dddml.suidemocontracts.specialization.*;
import org.dddml.suidemocontracts.specialization.hibernate.AbstractHibernateEventStore;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.springframework.transaction.annotation.Transactional;
import org.dddml.suidemocontracts.domain.orderv2.*;

public class HibernateOrderV2EventStore extends AbstractHibernateEventStore
{
    @Override
    protected Serializable getEventId(EventStoreAggregateId eventStoreAggregateId, long version)
    {
        return new OrderV2EventId((String) eventStoreAggregateId.getId(), version);
    }

    @Override
    protected Class getSupportedEventType()
    {
        return AbstractOrderV2Event.class;
    }

    @Transactional(readOnly = true)
    @Override
    public EventStream loadEventStream(Class eventType, EventStoreAggregateId eventStoreAggregateId, long version) {
        Class supportedEventType = AbstractOrderV2Event.class;
        if (!eventType.isAssignableFrom(supportedEventType)) {
            throw new UnsupportedOperationException();
        }
        String idObj = (String) eventStoreAggregateId.getId();
        Criteria criteria = getCurrentSession().createCriteria(AbstractOrderV2Event.class);
        criteria.add(Restrictions.eq("orderV2EventId.orderId", idObj));
        criteria.add(Restrictions.le("orderV2EventId.version", version));
        criteria.addOrder(Order.asc("orderV2EventId.version"));
        List es = criteria.list();
        for (Object e : es) {
            ((AbstractOrderV2Event) e).setEventReadOnly(true);
        }
        EventStream eventStream = new EventStream();
        if (es.size() > 0) {
            eventStream.setSteamVersion(((AbstractOrderV2Event) es.get(es.size() - 1)).getOrderV2EventId().getVersion());
        } else {
            //todo?
        }
        eventStream.setEvents(es);
        return eventStream;
    }

}

