package org.dddml.roochdemocontracts.specialization;

public class EventReference<TId, T> implements Event {
    private Class<T> eventType;
    private TId eventId;
    private String url;

    public Class<T> getEventType() {
        return eventType;
    }

    public void setEventType(Class<T> eventType) {
        this.eventType = eventType;
    }

    public TId getEventId() {
        return eventId;
    }

    public void setEventId(TId eventId) {
        this.eventId = eventId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public EventReference(Class<T> eventType, TId eventId, String url) {
        this.eventType = eventType;
        this.eventId = eventId;
        this.url = url;
    }
}
