// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.aptosdemocontracts.aptos.contract.taskservice;

import org.dddml.aptosdemocontracts.aptos.contract.service.ProductEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PullProductEventsTaskService {

    @Autowired
    private ProductEventService productEventService;

    @Scheduled(fixedDelayString = "${aptos.contract.pull-product-events.product-event.fixed-delay:5000}")
    public void pullProductEvents() {
        productEventService.pullProductEvents();
    }

}
