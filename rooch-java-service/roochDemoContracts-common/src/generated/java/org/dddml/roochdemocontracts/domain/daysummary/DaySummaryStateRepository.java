// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.domain.daysummary;

import java.util.*;
import org.dddml.support.criterion.Criterion;
import org.dddml.roochdemocontracts.domain.*;
import java.math.BigInteger;
import java.util.Date;

public interface DaySummaryStateRepository {
    DaySummaryState get(Day id, boolean nullAllowed);

    void save(DaySummaryState state);

    void merge(DaySummaryState detached);
}

