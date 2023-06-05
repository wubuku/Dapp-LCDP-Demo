package org.dddml.roochdemocontracts.specialization;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AggregateEvent<TA, TS> {

    private TA aggregate;

    private TS state;

    private List<Event> events;

    private Event event;

    public AggregateEvent() {
    }

    public AggregateEvent(TA aggregate, TS state, List<Event> events) {
        this.aggregate = aggregate;
        this.state = state;
        this.events = events;
    }

    public AggregateEvent(TA aggregate, TS state, Event event) {
        this.aggregate = aggregate;
        this.state = state;
        this.event = event;
    }

    public TA getAggregate() {
        return aggregate;
    }

    public void setAggregate(TA aggregate) {
        this.aggregate = aggregate;
    }

    public TS getState() {
        return state;
    }

    public void setState(TS state) {
        this.state = state;
    }

    public List<Event> getEvents() {
        if (events == null && event != null) {
            return Collections.singletonList(event);
        }
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public Event getEvent() {
        if (event == null && events != null && events.size() == 1) {
            return events.get(0);
        }
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }


}
