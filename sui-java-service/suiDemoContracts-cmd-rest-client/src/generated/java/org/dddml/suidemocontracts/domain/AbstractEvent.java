package org.dddml.suidemocontracts.domain;

import org.dddml.suidemocontracts.specialization.StateEventType;

public abstract class AbstractEvent
{
    public static final String STATE_EVENT_TYPE_CREATED = StateEventType.CREATED;

    public static final String STATE_EVENT_TYPE_MERGE_PATCHED = StateEventType.MERGE_PATCHED;

    public static final String STATE_EVENT_TYPE_DELETED = StateEventType.DELETED;

    public static final String STATE_EVENT_TYPE_REMOVED = StateEventType.REMOVED;

    private String eventType;

    public String getEventType()
    {
        return this.eventType;
    }

    public void setEventType(String eventType)
    {
        this.eventType = eventType;
    }

    private String commandId;

    public String getCommandId()
    {
        return this.commandId;
    }

    public void setCommandId(String commandId)
    {
        this.commandId = commandId;
    }

}


