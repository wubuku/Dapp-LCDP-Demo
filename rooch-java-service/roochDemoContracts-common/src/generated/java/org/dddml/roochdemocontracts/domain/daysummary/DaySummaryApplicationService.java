// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.domain.daysummary;

import java.util.Map;
import java.util.List;
import org.dddml.support.criterion.Criterion;
import org.dddml.roochdemocontracts.domain.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.roochdemocontracts.specialization.Event;
import org.dddml.roochdemocontracts.domain.Command;

public interface DaySummaryApplicationService {
    void when(DaySummaryCommands.Create c);

    void when(DaySummaryCommands.Delete c);

    DaySummaryState get(Day id);

    Iterable<DaySummaryState> getAll(Integer firstResult, Integer maxResults);

    Iterable<DaySummaryState> get(Iterable<Map.Entry<String, Object>> filter, List<String> orders, Integer firstResult, Integer maxResults);

    Iterable<DaySummaryState> get(Criterion filter, List<String> orders, Integer firstResult, Integer maxResults);

    Iterable<DaySummaryState> getByProperty(String propertyName, Object propertyValue, List<String> orders, Integer firstResult, Integer maxResults);

    long getCount(Iterable<Map.Entry<String, Object>> filter);

    long getCount(Criterion filter);

    DaySummaryEvent getEvent(Day day, long version);

    DaySummaryState getHistoryState(Day day, long version);

}

