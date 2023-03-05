package org.dddml.suidemocontracts.domain.daysummary;

import java.util.*;
import org.dddml.support.criterion.Criterion;
import org.dddml.suidemocontracts.domain.*;
import java.math.BigInteger;
import java.util.Date;

public interface DaySummaryStateRepository
{
    DaySummaryState get(Day id, boolean nullAllowed);

    void save(DaySummaryState state);

}

