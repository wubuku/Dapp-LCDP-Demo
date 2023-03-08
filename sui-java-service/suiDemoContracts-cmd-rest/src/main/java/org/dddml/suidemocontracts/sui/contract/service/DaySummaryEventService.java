package org.dddml.suidemocontracts.sui.contract.service;

import com.github.wubuku.sui.bean.EventId;
import com.github.wubuku.sui.bean.MoveEvent;
import com.github.wubuku.sui.bean.PaginatedMoveEvents;
import com.github.wubuku.sui.bean.SuiMoveEventEnvelope;
import com.github.wubuku.sui.utils.SuiJsonRpcClient;
import org.dddml.suidemocontracts.domain.daysummary.AbstractDaySummaryEvent;
import org.dddml.suidemocontracts.sui.contract.DomainBeanUtils;
import org.dddml.suidemocontracts.sui.contract.daysummary.DaySummaryCreated;
import org.dddml.suidemocontracts.sui.contract.repository.DaySummaryEventRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DaySummaryEventService {

    @Autowired
    private SuiJsonRpcClient suiJsonRpcClient;

    @Autowired
    private DaySummaryEventRepository daySummaryEventRepository;

    public void pullDaySummaryCreatedEvents() {
        String packageId = "0x4c9edf32a36c6369ac859336672642b7c3140252"; //todo get from DB
        int limit = 1; //todo let be configurable
        EventId cursor = null; //todo get from DB
        while (true) {
            PaginatedMoveEvents<DaySummaryCreated> eventPage = suiJsonRpcClient.getMoveEvents(
                    packageId + "::day_summary::DaySummaryCreated",
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

        daySummaryEventRepository.save(daySummaryCreated);
    }

}
