package org.dddml.suidemocontracts.sui.contract.taskservice;

import org.dddml.suidemocontracts.sui.contract.repository.OrderV2EventRepository;
import org.dddml.suidemocontracts.sui.contract.service.SuiOrderV2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class UpdateOrderV2StateTaskService {

    @Autowired
    private SuiOrderV2Service suiOrderV2Service;

    @Autowired
    private OrderV2EventRepository orderV2EventRepository;

    @Scheduled(fixedDelay = 10000L)
    public void task() {
        orderV2EventRepository.findAll().forEach(orderEvent -> {
            String objectId = orderEvent.getId_();
            suiOrderV2Service.updateOrderState(objectId);
            //todo update event status...
        });
    }
}
