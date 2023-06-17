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
public class UpdateProductStateTaskService {

    @Autowired
    private RoochProductService roochProductService;

    @Autowired
    private ProductEventRepository productEventRepository;

    @Autowired
    private ProductEventService productEventService;

    @Scheduled(fixedDelayString = "${rooch.contract.update-product-states.fixed-delay:5000}")
    @Transactional
    public void updateProductStates() {
        productEventRepository.findByStatusIsNull().forEach(e -> {
            String objectId = e.getId_();
            roochProductService.updateProductState(objectId);
            productEventService.updateStatusToProcessed(e);
        });
    }
}
