package org.dddml.suidemocontracts.sui.contract.service;

import com.github.wubuku.sui.bean.*;
import com.github.wubuku.sui.utils.SuiJsonRpcClient;
import org.dddml.suidemocontracts.domain.daysummary.AbstractDaySummaryEvent;
import org.dddml.suidemocontracts.sui.contract.ContractConstants;
import org.dddml.suidemocontracts.sui.contract.DomainBeanUtils;
import org.dddml.suidemocontracts.sui.contract.SuiPackage;
import org.dddml.suidemocontracts.sui.contract.daysummary.DaySummaryCreated;
import org.dddml.suidemocontracts.sui.contract.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DaySummaryEventService {

    @Autowired
    private SuiPackageRepository suiPackageRepository;

    @Autowired
    private SuiJsonRpcClient suiJsonRpcClient;

    @Autowired
    private DaySummaryEventRepository daySummaryEventRepository;

    public void pullDaySummaryCreatedEvents() {
        String packageId = getDefaultSuiPackageId();
        if (packageId == null) {
            return;
        }
        int limit = 1; //todo let be configurable
        EventId cursor = null; //todo get from DB?
        while (true) {
            PaginatedMoveEvents<DaySummaryCreated> eventPage = suiJsonRpcClient.getMoveEvents(
                    packageId + "::" + ContractConstants.DAY_SUMMARY_MODULE_DAY_SUMMARY_CREATED,
                    cursor, limit, false, DaySummaryCreated.class);
            //System.out.println(eventPage);
            if (eventPage.getData() != null && !eventPage.getData().isEmpty()) {
                cursor = eventPage.getNextCursor();
                //System.out.println("Next cursor: " + eventPage.getNextCursor());
                for (SuiMoveEventEnvelope<DaySummaryCreated> eventEnvelope : eventPage.getData()) {
                    //System.out.println(event.getEvent().getMoveEvent().getFields().getId());
                    saveDaySummaryCreated(eventEnvelope);
                }
            } else {
                break;
            }
            if (cursor == null) {
                break;
            }
        }
    }

    private void saveDaySummaryCreated(SuiMoveEventEnvelope<DaySummaryCreated> eventEnvelope) {
        AbstractDaySummaryEvent.DaySummaryCreated daySummaryCreated = DomainBeanUtils.toDaySummaryCreated(eventEnvelope);
        if (daySummaryEventRepository.findById(daySummaryCreated.getDaySummaryEventId()).isPresent()) {
            return;
        }
        daySummaryEventRepository.save(daySummaryCreated);
    }

    private String getDefaultSuiPackageId() {
        return suiPackageRepository.findById(ContractConstants.DEFAULT_SUI_PACKAGE_NAME)
                .map(SuiPackage::getObjectId).orElse(null);
    }
}
