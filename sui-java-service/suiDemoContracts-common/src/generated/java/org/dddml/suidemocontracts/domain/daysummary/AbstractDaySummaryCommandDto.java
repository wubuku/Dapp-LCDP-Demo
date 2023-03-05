package org.dddml.suidemocontracts.domain.daysummary;

import org.dddml.suidemocontracts.domain.*;
import java.math.BigInteger;
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
     * Id
     */
    private String id_;

    public String getId_()
    {
        return this.id_;
    }

    public void setId_(String id)
    {
        this.id_ = id;
    }

    /**
     * Off Chain Version
     */
    private Long offChainVersion;

    public Long getOffChainVersion()
    {
        return this.offChainVersion;
    }

    public void setOffChainVersion(Long offChainVersion)
    {
        this.offChainVersion = offChainVersion;
    }


    public void copyTo(DaySummaryCommand command) {
        command.setDay(this.getDay());
        command.setId_(this.getId_());
        command.setOffChainVersion(this.getOffChainVersion());
        
        command.setRequesterId(this.getRequesterId());
        command.setCommandId(this.getCommandId());
    }

}
