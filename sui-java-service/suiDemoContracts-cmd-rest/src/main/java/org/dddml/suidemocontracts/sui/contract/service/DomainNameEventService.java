// <autogenerated>
//   This file was generated by T4 code generator .
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.suidemocontracts.sui.contract.service;

import com.github.wubuku.sui.bean.EventId;
import com.github.wubuku.sui.bean.PaginatedMoveEvents;
import com.github.wubuku.sui.bean.SuiMoveEventEnvelope;
import com.github.wubuku.sui.utils.SuiJsonRpcClient;
import org.dddml.suidemocontracts.domain.domainname.AbstractDomainNameEvent;
import org.dddml.suidemocontracts.sui.contract.ContractConstants;
import org.dddml.suidemocontracts.sui.contract.DomainBeanUtils;
import org.dddml.suidemocontracts.sui.contract.SuiPackage;
import org.dddml.suidemocontracts.sui.contract.domainname.Registered;
import org.dddml.suidemocontracts.sui.contract.domainname.Renewed;
import org.dddml.suidemocontracts.sui.contract.repository.DomainNameEventRepository;
import org.dddml.suidemocontracts.sui.contract.repository.SuiPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DomainNameEventService {

    @Autowired
    private SuiPackageRepository suiPackageRepository;

    @Autowired
    private SuiJsonRpcClient suiJsonRpcClient;

    @Autowired
    private DomainNameEventRepository domainNameEventRepository;

    @Transactional
    public void updateStatusToProcessed(AbstractDomainNameEvent event) {
        event.setStatus("D");
        domainNameEventRepository.save(event);
    }

    @Transactional
    public void pullRegisteredEvents() {
        String packageId = getDefaultSuiPackageId();
        if (packageId == null) {
            return;
        }
        int limit = 1;
        EventId cursor = getRegisteredEventNextCursor();
        while (true) {
            PaginatedMoveEvents<Registered> eventPage = suiJsonRpcClient.getMoveEvents(
                    packageId + "::" + ContractConstants.DOMAIN_NAME_MODULE_REGISTERED,
                    cursor, limit, false, Registered.class);

            if (eventPage.getData() != null && !eventPage.getData().isEmpty()) {
                cursor = eventPage.getNextCursor();
                for (SuiMoveEventEnvelope<Registered> eventEnvelope : eventPage.getData()) {
                    saveRegistered(eventEnvelope);
                }
            } else {
                break;
            }
            if (cursor == null) {
                break;
            }
        }
    }

    private EventId getRegisteredEventNextCursor() {
        AbstractDomainNameEvent lastEvent = domainNameEventRepository.findFirstRegisteredByOrderBySuiTimestampDesc();
        return lastEvent != null ? new EventId(lastEvent.getSuiTxDigest(), lastEvent.getSuiEventSeq()) : null;
    }

    private void saveRegistered(SuiMoveEventEnvelope<Registered> eventEnvelope) {
        AbstractDomainNameEvent.Registered registered = DomainBeanUtils.toRegistered(eventEnvelope);
        if (domainNameEventRepository.findById(registered.getDomainNameEventId()).isPresent()) {
            return;
        }
        domainNameEventRepository.save(registered);
    }

    @Transactional
    public void pullRenewedEvents() {
        String packageId = getDefaultSuiPackageId();
        if (packageId == null) {
            return;
        }
        int limit = 1;
        EventId cursor = getRenewedEventNextCursor();
        while (true) {
            PaginatedMoveEvents<Renewed> eventPage = suiJsonRpcClient.getMoveEvents(
                    packageId + "::" + ContractConstants.DOMAIN_NAME_MODULE_RENEWED,
                    cursor, limit, false, Renewed.class);

            if (eventPage.getData() != null && !eventPage.getData().isEmpty()) {
                cursor = eventPage.getNextCursor();
                for (SuiMoveEventEnvelope<Renewed> eventEnvelope : eventPage.getData()) {
                    saveRenewed(eventEnvelope);
                }
            } else {
                break;
            }
            if (cursor == null) {
                break;
            }
        }
    }

    private EventId getRenewedEventNextCursor() {
        AbstractDomainNameEvent lastEvent = domainNameEventRepository.findFirstRenewedByOrderBySuiTimestampDesc();
        return lastEvent != null ? new EventId(lastEvent.getSuiTxDigest(), lastEvent.getSuiEventSeq()) : null;
    }

    private void saveRenewed(SuiMoveEventEnvelope<Renewed> eventEnvelope) {
        AbstractDomainNameEvent.Renewed renewed = DomainBeanUtils.toRenewed(eventEnvelope);
        if (domainNameEventRepository.findById(renewed.getDomainNameEventId()).isPresent()) {
            return;
        }
        domainNameEventRepository.save(renewed);
    }


    private String getDefaultSuiPackageId() {
        return suiPackageRepository.findById(ContractConstants.DEFAULT_SUI_PACKAGE_NAME)
                .map(SuiPackage::getObjectId).orElse(null);
    }
}
