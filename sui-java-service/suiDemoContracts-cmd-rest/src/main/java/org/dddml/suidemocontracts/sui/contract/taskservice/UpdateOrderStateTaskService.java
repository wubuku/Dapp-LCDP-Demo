package org.dddml.suidemocontracts.sui.contract.taskservice;

import org.dddml.suidemocontracts.sui.contract.repository.OrderEventRepository;
import org.dddml.suidemocontracts.sui.contract.service.SuiOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class UpdateOrderStateTaskService {

    @Autowired
    private SuiOrderService suiOrderService;

    @Autowired
    private OrderEventRepository orderEventRepository;

    @Scheduled(fixedDelay = 10000L)
    public void task() {
        orderEventRepository.findAll().forEach(orderEvent -> {
            String objectId = orderEvent.getId();
            suiOrderService.updateOrderState(objectId);
            //todo update event status...
        });
    }
}
