package org.dddml.roochdemocontracts.specialization;


import java.util.ArrayList;
import java.util.List;

public class EventStream {

    public EventStream() {
    }

    private long steamVersion;

    public final long getSteamVersion() {
        return steamVersion;
    }

    public final void setSteamVersion(long value) {
        steamVersion = value;
    }

    private List<Event> events = new ArrayList<>();

    public final List<Event> getEvents() {
        return events;
    }

    public final void setEvents(List<Event> value) {
        events = value;
    }

}
