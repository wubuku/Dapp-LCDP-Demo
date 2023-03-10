// <autogenerated>
//   This file was generated by T4 code generator .
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.suidemocontracts.sui.contract.taskservice;

import org.dddml.suidemocontracts.sui.contract.service.OrderEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PullOrderEventsTaskService {

    @Autowired
    private OrderEventService orderEventService;

    @Scheduled(fixedDelayString = "${sui.contract.pull-order-events.order-created-fixed-delay:5000}")
    public void pullOrderCreatedEvents() {
        orderEventService.pullOrderCreatedEvents();
    }

    @Scheduled(fixedDelayString = "${sui.contract.pull-order-events.order-item-removed-fixed-delay:5000}")
    public void pullOrderItemRemovedEvents() {
        orderEventService.pullOrderItemRemovedEvents();
    }

    @Scheduled(fixedDelayString = "${sui.contract.pull-order-events.order-item-quantity-updated-fixed-delay:5000}")
    public void pullOrderItemQuantityUpdatedEvents() {
        orderEventService.pullOrderItemQuantityUpdatedEvents();
    }

}
