package org.dddml.suidemocontracts.taskservice;

import org.dddml.suidemocontracts.sui.contract.service.DaySummaryEventService;
import org.dddml.suidemocontracts.sui.contract.service.SuiOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PullDaySummaryEventTaskService {

    @Autowired
    private DaySummaryEventService daySummaryEventService;

    @Scheduled(fixedDelay = 5000L)
    public void task() {
        daySummaryEventService.pullDaySummaryCreatedEvents();
    }
}
