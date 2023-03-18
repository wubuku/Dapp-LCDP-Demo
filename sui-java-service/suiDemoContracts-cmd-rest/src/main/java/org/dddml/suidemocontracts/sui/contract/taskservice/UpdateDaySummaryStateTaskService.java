// <autogenerated>
//   This file was generated by T4 code generator .
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.suidemocontracts.sui.contract.taskservice;

import org.dddml.suidemocontracts.sui.contract.repository.*;
import org.dddml.suidemocontracts.sui.contract.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class UpdateDaySummaryStateTaskService {

    @Autowired
    private SuiDaySummaryService suiDaySummaryService;

    @Autowired
    private DaySummaryEventRepository daySummaryEventRepository;

    @Scheduled(fixedDelay = 10000L)
    public void task() {
        //todo filter by event status...
        daySummaryEventRepository.findAll().forEach(e -> {
            String objectId = e.getId_();
            suiDaySummaryService.updateDaySummaryState(objectId);
            //todo update event status...
        });
    }
}