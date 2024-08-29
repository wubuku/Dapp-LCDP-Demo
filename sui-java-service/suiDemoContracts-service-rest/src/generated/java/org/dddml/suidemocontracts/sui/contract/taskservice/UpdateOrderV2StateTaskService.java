// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.suidemocontracts.sui.contract.taskservice;

import org.dddml.suidemocontracts.domain.orderv2.AbstractOrderV2Event;
import org.dddml.suidemocontracts.sui.contract.repository.*;
import org.dddml.suidemocontracts.sui.contract.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateOrderV2StateTaskService {

    @Autowired
    private SuiOrderV2Service suiOrderV2Service;

    @Autowired
    private OrderV2EventRepository orderV2EventRepository;

    @Autowired
    private OrderV2EventService orderV2EventService;

    @Scheduled(fixedDelayString = "${sui.contract.update-order-v2-states.fixed-delay:5000}")
    @Transactional
    public void updateOrderV2States() {
        java.util.List<AbstractOrderV2Event> es = orderV2EventRepository.findByStatusIsNull();
        AbstractOrderV2Event e = es.stream().findFirst().orElse(null);
        if (e != null) {
            String objectId = e.getId_();
            suiOrderV2Service.updateOrderV2State(objectId);
            es.stream().filter(ee -> ee.getId_().equals(objectId))
                    .forEach(orderV2EventService::updateStatusToProcessed);
        }
    }
}
