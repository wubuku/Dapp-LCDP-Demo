package org.dddml.suidemocontracts.specialization.hibernate;

import org.dddml.suidemocontracts.specialization.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.function.Consumer;

/**
 * Created by Yang on 2016/7/25.
 */
public abstract class AbstractHibernateEventStore implements EventStore {
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    @Transactional(readOnly = true)
    @Override
    public EventStream loadEventStream(EventStoreAggregateId aggregateId) {
        throw new UnsupportedOperationException();
    }

    @Transactional
    @Override
    public void appendEvents(EventStoreAggregateId aggregateId, long version, Collection<Event> events, Consumer<Collection<Event>> afterEventsAppended) {
        for (Event e : events) {
            getCurrentSession().save(e);
            if (e instanceof Saveable) {
                Saveable saveable = (Saveable) e;
                saveable.save();
            }
        }
        //System.out.println("####################################################");
        afterEventsAppended.accept(events);
        //System.out.println("####################################################");
    }

    @Transactional(readOnly = true)
    @Override
    public Event getEvent(Class eventType, EventStoreAggregateId eventStoreAggregateId, long version) {
        Class supportedEventType = getSupportedEventType();
        if (!eventType.isAssignableFrom(supportedEventType)) {
            throw new UnsupportedOperationException();
        }
        Serializable eventId = getEventId(eventStoreAggregateId, version);
        return (Event) getCurrentSession().get(eventType, eventId);
    }

    @Transactional(readOnly = true)
    @Override
    public Event getEvent(EventStoreAggregateId eventStoreAggregateId, long version) {
        Serializable eventId = getEventId(eventStoreAggregateId, version);
        return (Event) getCurrentSession().get(getSupportedEventType(), eventId);
    }

    public boolean isEventWithCommandIdExisted(Class eventType, EventStoreAggregateId eventStoreAggregateId, String commandId) {
        throw new UnsupportedOperationException();
    }

    protected abstract Class getSupportedEventType();

    protected abstract Serializable getEventId(EventStoreAggregateId eventStoreAggregateId, long version);

}
