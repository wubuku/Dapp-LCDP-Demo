// <autogenerated>
//   This file was generated by T4 code generator .
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.suidemocontracts.sui.contract.taskservice;

import org.dddml.suidemocontracts.sui.contract.service.ProductEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PullProductEventsTaskService {

    @Autowired
    private ProductEventService productEventService;

    @Scheduled(fixedDelayString = "${sui.contract.pull-product-events.product-created-fixed-delay:5000}")
    public void pullProductCreatedEvents() {
        productEventService.pullProductCreatedEvents();
    }

}
