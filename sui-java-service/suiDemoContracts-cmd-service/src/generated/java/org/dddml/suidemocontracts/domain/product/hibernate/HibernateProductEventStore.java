package org.dddml.suidemocontracts.domain.product.hibernate;

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
import org.dddml.suidemocontracts.domain.product.*;
import java.util.function.Consumer;

public class HibernateProductEventStore implements EventStore {
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() { return this.sessionFactory; }

    public void setSessionFactory(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

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
            if (e instanceof AbstractProductEvent.AbstractProductStateCreated) {
                ProductState s = ((AbstractProductEvent.AbstractProductStateCreated)e).getProductState();
                getCurrentSession().save(s);
            } else {
                getCurrentSession().save(e);
            }
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
        Class supportedEventType = AbstractProductEvent.SimpleProductStateCreated.class;
        if (!eventType.isAssignableFrom(supportedEventType)) {
            throw new UnsupportedOperationException();
        }
        return getEvent(eventStoreAggregateId, version);
    }

    @Transactional(readOnly = true)
    @Override
    public Event getEvent(EventStoreAggregateId eventStoreAggregateId, long version) {
        String idObj = (String) eventStoreAggregateId.getId();
        ProductState state = getCurrentSession().get(AbstractProductState.SimpleProductState.class, idObj);
        return new AbstractProductEvent.SimpleProductStateCreated(state);
    }

    @Transactional(readOnly = true)
    @Override
    public EventStream loadEventStream(Class eventType, EventStoreAggregateId eventStoreAggregateId, long version) {
        Class supportedEventType = AbstractProductEvent.SimpleProductStateCreated.class;
        if (!eventType.isAssignableFrom(supportedEventType)) {
            throw new UnsupportedOperationException();
        }
        Event e = getEvent(eventStoreAggregateId, version);
        EventStream es = new EventStream();
        es.setEvents(e != null ? Collections.singletonList(e) : Collections.EMPTY_LIST);
        return es;
    }

    public boolean isEventWithCommandIdExisted(Class eventType, EventStoreAggregateId eventStoreAggregateId, String commandId) {
        throw new UnsupportedOperationException();
    }

}

