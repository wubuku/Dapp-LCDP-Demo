package org.dddml.suidemocontracts.taskservice;

import org.dddml.suidemocontracts.sui.contract.service.SuiOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class UpdateOrderStateTaskService {

    @Autowired
    private SuiOrderService suiOrderService;

    @Scheduled(fixedDelay = 1000L)
    public void task() {
        suiOrderService.updateOrderState("0x8134656922ebdfdd67a4e6a3da444d53c997c196");
    }
}
