package org.dddml.suidemocontracts.domain.daysummary;

import org.dddml.suidemocontracts.domain.*;
import java.util.Date;
import org.dddml.suidemocontracts.domain.AbstractCommand;

public abstract class AbstractDaySummaryCommandDto extends AbstractCommand
{

    /**
     * Day
     */
    private Day day;

    public Day getDay()
    {
        return this.day;
    }

    public void setDay(Day day)
    {
        this.day = day;
    }

    /**
     * Version
     */
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
