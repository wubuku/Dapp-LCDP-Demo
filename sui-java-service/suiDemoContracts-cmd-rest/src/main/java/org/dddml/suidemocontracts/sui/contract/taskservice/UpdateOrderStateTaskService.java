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
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateOrderStateTaskService {

    @Autowired
    private SuiOrderService suiOrderService;

    @Autowired
    private OrderEventRepository orderEventRepository;

    @Autowired
    private OrderEventService orderEventService;

    @Scheduled(fixedDelayString = "${sui.contract.update-order-states.fixed-delay:5000}")
    @Transactional
    public void updateOrderStates() {
        orderEventRepository.findByStatusIsNull().forEach(e -> {
            String objectId = e.getId();
            suiOrderService.updateOrderState(objectId);
            orderEventService.updateStatusToProcessed(e);
        });
    }
}
