// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.aptosdemocontracts.domain.daysummary;

import java.util.*;
import org.dddml.aptosdemocontracts.domain.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.aptosdemocontracts.domain.AbstractCommand;

public abstract class AbstractDaySummaryCommand extends AbstractCommand implements DaySummaryCommand {

    private Day day;

    public Day getDay()
    {
        return this.day;
    }

    public void setDay(Day day)
    {
        this.day = day;
    }

    private Long offChainVersion;

    public Long getOffChainVersion()
    {
        return this.offChainVersion;
    }

    public void setOffChainVersion(Long offChainVersion)
    {
        this.offChainVersion = offChainVersion;
    }


}

