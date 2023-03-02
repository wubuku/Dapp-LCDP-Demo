package org.dddml.suidemocontracts.domain.daysummary;

import java.util.*;
import org.dddml.suidemocontracts.domain.*;
import java.util.Date;
import org.dddml.suidemocontracts.domain.AbstractCommand;

public abstract class AbstractDaySummaryCommand extends AbstractCommand implements DaySummaryCommand
{

    private Day day;

    public Day getDay()
    {
        return this.day;
    }

    public void setDay(Day day)
    {
        this.day = day;
    }

    private String surrogateId;

    public String getSurrogateId()
    {
        return this.surrogateId;
    }

    public void setSurrogateId(String id)
    {
        this.surrogateId = id;
    }

    private Long version;

    public Long getVersion()
    {
        return this.version;
    }

    public void setVersion(Long version)
    {
        this.version = version;
    }


}

