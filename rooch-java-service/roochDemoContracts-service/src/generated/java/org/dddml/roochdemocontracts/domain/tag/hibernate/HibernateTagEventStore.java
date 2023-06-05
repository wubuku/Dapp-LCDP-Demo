// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.domain.tag.hibernate;

import java.io.Serializable;
import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.roochdemocontracts.domain.*;
import org.dddml.roochdemocontracts.specialization.*;
import org.dddml.roochdemocontracts.specialization.hibernate.AbstractHibernateEventStore;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.springframework.transaction.annotation.Transactional;
import org.dddml.roochdemocontracts.domain.tag.*;

public class HibernateTagEventStore extends AbstractHibernateEventStore {
    @Override
    protected Serializable getEventId(EventStoreAggregateId eventStoreAggregateId, long version)
    {
        return new TagEventId((String) eventStoreAggregateId.getId(), BigInteger.valueOf(version));
    }

    @Override
    protected Class getSupportedEventType()
    {
        return AbstractTagEvent.class;
    }

    @Transactional(readOnly = true)
    @Override
    public EventStream loadEventStream(Class eventType, EventStoreAggregateId eventStoreAggregateId, long version) {
        Class supportedEventType = AbstractTagEvent.class;
        if (!eventType.isAssignableFrom(supportedEventType)) {
            throw new UnsupportedOperationException();
        }
        String idObj = (String) eventStoreAggregateId.getId();
        Criteria criteria = getCurrentSession().createCriteria(AbstractTagEvent.class);
        criteria.add(Restrictions.eq("tagEventId.name", idObj));
        criteria.add(Restrictions.le("tagEventId.offChainVersion", version));
        criteria.addOrder(Order.asc("tagEventId.offChainVersion"));
        List es = criteria.list();
        for (Object e : es) {
            ((AbstractTagEvent) e).setEventReadOnly(true);
        }
        EventStream eventStream = new EventStream();
        if (es.size() > 0) {
            eventStream.setSteamVersion(((AbstractTagEvent) es.get(es.size() - 1)).getTagEventId().getVersion().longValue());
        } else {
            //todo?
        }
        eventStream.setEvents(es);
        return eventStream;
    }

}

