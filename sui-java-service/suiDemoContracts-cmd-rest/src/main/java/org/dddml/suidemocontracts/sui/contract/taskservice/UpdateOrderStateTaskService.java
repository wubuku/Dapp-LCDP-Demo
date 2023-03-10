package org.dddml.suidemocontracts.sui.contract.taskservice;

import org.dddml.suidemocontracts.sui.contract.service.SuiOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class UpdateOrderStateTaskService {

    @Autowired
    private SuiOrderService suiOrderService;

    @Scheduled(fixedDelay = 10000L)
    public void task() {
        //todo get order id need to update from database
        suiOrderService.updateOrderState("0x8134656922ebdfdd67a4e6a3da444d53c997c196");
    }
}
