package org.dddml.suidemocontracts.taskservice;

import org.dddml.suidemocontracts.sui.contract.service.DaySummaryEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PullDaySummaryEventsTaskService {

    @Autowired
    private DaySummaryEventService daySummaryEventService;

    @Scheduled(fixedDelayString = "${sui.contract.pull-day-summary-events.day-summary-created-fixed-delay}")
    public void pullDaySummaryCreatedEvents() {
        daySummaryEventService.pullDaySummaryCreatedEvents();
    }
}
