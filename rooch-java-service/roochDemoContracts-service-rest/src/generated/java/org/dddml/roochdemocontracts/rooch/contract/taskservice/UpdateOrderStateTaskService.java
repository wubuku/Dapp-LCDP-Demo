// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.rooch.contract.taskservice;

import org.dddml.roochdemocontracts.rooch.contract.repository.*;
import org.dddml.roochdemocontracts.rooch.contract.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateOrderStateTaskService {

    @Autowired
    private RoochOrderService roochOrderService;

    @Autowired
    private OrderEventRepository orderEventRepository;

    @Autowired
    private OrderEventService orderEventService;

    @Scheduled(fixedDelayString = "${rooch.contract.update-order-states.fixed-delay:5000}")
    @Transactional
    public void updateOrderStates() {
        orderEventRepository.findByStatusIsNull().forEach(e -> {
            String objectId = e.getId_();
            roochOrderService.updateOrderState(objectId);
            orderEventService.updateStatusToProcessed(e);
        });
    }
}
