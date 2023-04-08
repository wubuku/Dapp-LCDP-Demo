// <autogenerated>
//   This file was generated by dddappp code generator.
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
public class UpdateDomainNameStateTaskService {

    @Autowired
    private SuiDomainNameService suiDomainNameService;

    @Autowired
    private DomainNameEventRepository domainNameEventRepository;

    @Autowired
    private DomainNameEventService domainNameEventService;

    @Scheduled(fixedDelayString = "${sui.contract.update-domain-name-states.fixed-delay:5000}")
    @Transactional
    public void updateDomainNameStates() {
        domainNameEventRepository.findByStatusIsNull().forEach(e -> {
            String objectId = e.getId_();
            suiDomainNameService.updateDomainNameState(objectId);
            domainNameEventService.updateStatusToProcessed(e);
        });
    }
}
