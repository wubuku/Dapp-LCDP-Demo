package org.dddml.suidemocontracts.domain.daysummary.hibernate;

import java.io.Serializable;
import java.util.*;
import org.dddml.suidemocontracts.domain.*;
import java.util.Date;
import org.dddml.suidemocontracts.specialization.*;
import org.dddml.suidemocontracts.specialization.hibernate.AbstractHibernateEventStore;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.springframework.transaction.annotation.Transactional;
import org.dddml.suidemocontracts.domain.daysummary.*;

public class HibernateDaySummaryEventStore extends AbstractHibernateEventStore
{
    @Override
    protected Serializable getEventId(EventStoreAggregateId eventStoreAggregateId, long version)
    {
        return new DaySummaryEventId((Day) eventStoreAggregateId.getId(), version);
    }

    @Override
    protected Class getSupportedEventType()
    {
        return AbstractDaySummaryEvent.class;
    }

    @Transactional(readOnly = true)
    @Override
    public EventStream loadEventStream(Class eventType, EventStoreAggregateId eventStoreAggregateId, long version) {
        Class supportedEventType = AbstractDaySummaryEvent.class;
        if (!eventType.isAssignableFrom(supportedEventType)) {
            throw new UnsupportedOperationException();
        }
        Day idObj = (Day) eventStoreAggregateId.getId();
        Criteria criteria = getCurrentSession().createCriteria(AbstractDaySummaryEvent.class);
        criteria.add(Restrictions.eq("daySummaryEventId.dayMonthYearNumber", idObj.getMonth().getYear().getNumber()));
        criteria.add(Restrictions.eq("daySummaryEventId.dayMonthYearCalendar", idObj.getMonth().getYear().getCalendar()));
        criteria.add(Restrictions.eq("daySummaryEventId.dayMonthNumber", idObj.getMonth().getNumber()));
        criteria.add(Restrictions.eq("daySummaryEventId.dayMonthIsLeap", idObj.getMonth().getIsLeap()));
        criteria.add(Restrictions.eq("daySummaryEventId.dayNumber", idObj.getNumber()));
        criteria.add(Restrictions.eq("daySummaryEventId.dayTimeZone", idObj.getTimeZone()));
        criteria.add(Restrictions.le("daySummaryEventId.version", version));
        criteria.addOrder(Order.asc("daySummaryEventId.version"));
        List es = criteria.list();
        for (Object e : es) {
            ((AbstractDaySummaryEvent) e).setEventReadOnly(true);
        }
        EventStream eventStream = new EventStream();
        if (es.size() > 0) {
            eventStream.setSteamVersion(((AbstractDaySummaryEvent) es.get(es.size() - 1)).getDaySummaryEventId().getVersion());
        } else {
            //todo?
        }
        eventStream.setEvents(es);
        return eventStream;
    }

}

