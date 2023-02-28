package org.dddml.suidemocontracts.specialization;

import java.util.List;
import java.util.Map;

public interface DomainEventPublisher {

    void publish(String aggregateType, Object aggregateId, List<Event> domainEvents);
    //void publish(String aggregateType, Object aggregateId, Map<String, String> headers, List<Event> domainEvents);

    default void publish(Class<?> aggregateType, Object aggregateId, List<Event> domainEvents) {
        publish(aggregateType.getName(), aggregateId, domainEvents);
    }
}
