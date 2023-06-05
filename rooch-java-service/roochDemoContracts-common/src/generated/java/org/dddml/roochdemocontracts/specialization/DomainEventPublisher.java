package org.dddml.roochdemocontracts.specialization;

import java.util.List;

public interface DomainEventPublisher {

    void publish(String aggregateType, Object aggregateId, List<Event> domainEvents);
    //void publish(String aggregateType, Object aggregateId, Map<String, String> headers, List<Event> domainEvents);

    default void publish(Class<?> aggregateType, Object aggregateId, List<Event> domainEvents) {
        publish(aggregateType.getName(), aggregateId, domainEvents);
    }
}
