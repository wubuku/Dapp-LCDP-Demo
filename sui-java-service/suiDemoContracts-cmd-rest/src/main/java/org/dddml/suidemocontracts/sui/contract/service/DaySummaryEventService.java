package org.dddml.suidemocontracts.sui.contract.service;

import com.github.wubuku.sui.bean.EventId;
import com.github.wubuku.sui.bean.MoveEvent;
import com.github.wubuku.sui.bean.PaginatedMoveEvents;
import com.github.wubuku.sui.bean.SuiMoveEventEnvelope;
import com.github.wubuku.sui.utils.SuiJsonRpcClient;
import org.dddml.suidemocontracts.domain.daysummary.AbstractDaySummaryEvent;
import org.dddml.suidemocontracts.sui.contract.Day;
import org.dddml.suidemocontracts.sui.contract.daysummary.DaySummaryCreated;
import org.dddml.suidemocontracts.sui.contract.repository.DaySummaryEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DaySummaryEventService {

    @Autowired
    private SuiJsonRpcClient suiJsonRpcClient;

    @Autowired
    private DaySummaryEventRepository daySummaryEventRepository;

    public void pullDaySummaryCreatedEvents()
    {
        String packageId = "0x4c9edf32a36c6369ac859336672642b7c3140252"; //todo get from DB
        int limit = 1; //todo let be configurable
        EventId cursor = null; //todo get from DB
        //int counter = 0;
        while (true) {
            PaginatedMoveEvents<DaySummaryCreated> eventPage = suiJsonRpcClient.getMoveEvents(
                    packageId + "::day_summary::DaySummaryCreated",
                    cursor, limit, false, DaySummaryCreated.class);
            //System.out.println(eventPage);
            if (eventPage.getData() != null && !eventPage.getData().isEmpty()) {
                cursor = eventPage.getNextCursor();
                //System.out.println("Next cursor: " + eventPage.getNextCursor());
                //System.out.println(eventPage.getData().get(0).getEvent().getMoveEvent().getFields().getId());
                for (SuiMoveEventEnvelope<DaySummaryCreated> eventEnvelope : eventPage.getData()) {
                    //System.out.println(event.getEvent().getMoveEvent().getFields().getId());
                    saveDaySummaryCreated(eventEnvelope);
                }
            } else {
                break;
            }
            //counter++;
            //System.out.println("counter: " + counter);
            if (cursor == null) {
                break;
            }
        }
    }

    private void saveDaySummaryCreated(SuiMoveEventEnvelope<DaySummaryCreated> eventEnvelope) {
        MoveEvent<DaySummaryCreated> moveEvent = eventEnvelope.getEvent().getMoveEvent();
        DaySummaryCreated contractEvent = moveEvent.getFields();

        AbstractDaySummaryEvent.DaySummaryCreated daySummaryCreated = new AbstractDaySummaryEvent.DaySummaryCreated();
        Day contractDay = contractEvent.getDay();
        org.dddml.suidemocontracts.domain.Day day = new org.dddml.suidemocontracts.domain.Day();
        day.setNumber(contractDay.getFields().getNumber());
        day.setTimeZone(contractDay.getFields().getTimeZone());
        //day.setMonth(contractDay.getFields().getMonth()); //todo
        daySummaryCreated.setDay(day);

        daySummaryCreated.setDescription(contractEvent.getDescription());
        daySummaryCreated.setMetaData(contractEvent.getMetaData());
        daySummaryCreated.setArrayData(contractEvent.getArrayData());
        daySummaryCreated.setOptionalData(contractEvent.getOptionalData());

        // --------- set event envelop info. ---------
        daySummaryCreated.setSuiTimestamp(eventEnvelope.getTimestamp());
        daySummaryCreated.setSuiTxDigest(eventEnvelope.getTxDigest());
        daySummaryCreated.setSuiEventSeq(eventEnvelope.getId().getEventSeq());

        // --------- set SuiEvent info. ---------
        daySummaryCreated.setSuiPackageId(moveEvent.getPackageId());
        daySummaryCreated.setSuiTransactionModule(moveEvent.getTransactionModule());
        daySummaryCreated.setSuiSender(moveEvent.getSender());
        // --------- set NextCursor info. ---------
        //daySummaryCreated.setNextCursor(new SuiEventId(cursor.getTxDigest(), cursor.getEventSeq()));

        daySummaryEventRepository.save(daySummaryCreated);
    }
}
