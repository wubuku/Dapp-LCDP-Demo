// <autogenerated>
//   This file was generated by T4 code generator .
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.suidemocontracts.sui.contract.service;

import com.github.wubuku.sui.bean.EventId;
import com.github.wubuku.sui.bean.PaginatedMoveEvents;
import com.github.wubuku.sui.bean.SuiMoveEventEnvelope;
import com.github.wubuku.sui.utils.SuiJsonRpcClient;
import org.dddml.suidemocontracts.domain.daysummary.AbstractDaySummaryEvent;
import org.dddml.suidemocontracts.sui.contract.ContractConstants;
import org.dddml.suidemocontracts.sui.contract.DomainBeanUtils;
import org.dddml.suidemocontracts.sui.contract.SuiPackage;
import org.dddml.suidemocontracts.sui.contract.daysummary.DaySummaryCreated;
import org.dddml.suidemocontracts.sui.contract.repository.DaySummaryEventRepository;
import org.dddml.suidemocontracts.sui.contract.repository.SuiPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DaySummaryEventService {

    @Autowired
    private SuiPackageRepository suiPackageRepository;

    @Autowired
    private SuiJsonRpcClient suiJsonRpcClient;

    @Autowired
    private DaySummaryEventRepository daySummaryEventRepository;

    @Transactional
    public void pullDaySummaryCreatedEvents() {
        String packageId = getDefaultSuiPackageId();
        if (packageId == null) {
            return;
        }
        int limit = 1;
        EventId cursor = getDaySummaryCreatedEventNextCursor();
        while (true) {
            PaginatedMoveEvents<DaySummaryCreated> eventPage = suiJsonRpcClient.getMoveEvents(
                    packageId + "::" + ContractConstants.DAY_SUMMARY_MODULE_DAY_SUMMARY_CREATED,
                    cursor, limit, false, DaySummaryCreated.class);

            if (eventPage.getData() != null && !eventPage.getData().isEmpty()) {
                cursor = eventPage.getNextCursor();
                for (SuiMoveEventEnvelope<DaySummaryCreated> eventEnvelope : eventPage.getData()) {
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

    private EventId getDaySummaryCreatedEventNextCursor() {
        AbstractDaySummaryEvent lastEvent = daySummaryEventRepository.findFirstDaySummaryCreatedByOrderBySuiTimestampDesc();
        return lastEvent != null ? new EventId(lastEvent.getSuiTxDigest(), lastEvent.getSuiEventSeq()) : null;
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
