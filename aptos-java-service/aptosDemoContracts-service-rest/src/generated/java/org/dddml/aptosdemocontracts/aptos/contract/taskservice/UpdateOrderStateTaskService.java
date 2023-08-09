// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.aptosdemocontracts.aptos.contract.taskservice;

import org.dddml.aptosdemocontracts.aptos.contract.repository.*;
import org.dddml.aptosdemocontracts.aptos.contract.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateOrderStateTaskService {

    @Autowired
    private AptosOrderService aptosOrderService;

    @Autowired
    private OrderEventRepository orderEventRepository;

    @Autowired
    private OrderEventService orderEventService;

    @Scheduled(fixedDelayString = "${aptos.contract.update-order-states.fixed-delay:5000}")
    @Transactional
    public void updateOrderStates() {
        orderEventRepository.findByStatusIsNull().forEach(e -> {
            aptosOrderService.updateOrderState(e.getOrderId());
            orderEventService.updateStatusToProcessed(e);
        });
    }
}
