// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.aptosdemocontracts.aptos.contract.service;

import com.github.wubuku.aptos.bean.Event;
import com.github.wubuku.aptos.utils.NodeApiClient;

import org.dddml.aptosdemocontracts.domain.daysummary.AbstractDaySummaryEvent;
import org.dddml.aptosdemocontracts.aptos.contract.ContractConstants;
import org.dddml.aptosdemocontracts.aptos.contract.DomainBeanUtils;
import org.dddml.aptosdemocontracts.aptos.contract.AptosAccount;

import org.dddml.aptosdemocontracts.aptos.contract.daysummary.DaySummaryCreated;
import org.dddml.aptosdemocontracts.aptos.contract.repository.DaySummaryEventRepository;
import org.dddml.aptosdemocontracts.aptos.contract.repository.AptosAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.*;
import java.util.*;


@Service
public class DaySummaryEventService {

    @Value("${aptos.contract.address}")
    private String aptosContractAddress;

    @Autowired
    private AptosAccountRepository aptosAccountRepository;

    @Autowired
    private NodeApiClient aptosNodeApiClient;

    @Autowired
    private DaySummaryEventRepository daySummaryEventRepository;

    @Transactional
    public void updateStatusToProcessed(AbstractDaySummaryEvent event) {
        event.setStatus("D");
        daySummaryEventRepository.save(event);
    }

    @Transactional
    public void pullDaySummaryCreatedEvents() {
        String resourceAccountAddress = getResourceAccountAddress();
        if (resourceAccountAddress == null) {
            return;
        }
        int limit = 1;
        BigInteger cursor = getDaySummaryCreatedEventNextCursor();
        if (cursor == null) {
            cursor = BigInteger.ZERO;
        }
        while (true) {
            List<Event<DaySummaryCreated>> eventPage;
            try {
                eventPage = aptosNodeApiClient.getEventsByEventHandle(
                        resourceAccountAddress,
                        this.aptosContractAddress + "::" + ContractConstants.DAY_SUMMARY_MODULE_EVENTS,
                        ContractConstants.DAY_SUMMARY_MODULE_DAY_SUMMARY_CREATED_HANDLE_FIELD,
                        DaySummaryCreated.class,
                        cursor.longValue(),
                        limit
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (eventPage != null && eventPage.size() > 0) {
                cursor = cursor.add(BigInteger.ONE);
                for (Event<DaySummaryCreated> eventEnvelope : eventPage) {
                    saveDaySummaryCreated(eventEnvelope);
                }
            } else {
                break;
            }
        }
    }

    private BigInteger getDaySummaryCreatedEventNextCursor() {
        AbstractDaySummaryEvent lastEvent = daySummaryEventRepository.findFirstDaySummaryCreatedByOrderByAptosEventSequenceNumber();
        return lastEvent != null ? lastEvent.getAptosEventSequenceNumber() : null;
    }

    private void saveDaySummaryCreated(Event<DaySummaryCreated> eventEnvelope) {
        AbstractDaySummaryEvent.DaySummaryCreated daySummaryCreated = DomainBeanUtils.toDaySummaryCreated(eventEnvelope);
        if (daySummaryEventRepository.findById(daySummaryCreated.getDaySummaryEventId()).isPresent()) {
            return;
        }
        daySummaryEventRepository.save(daySummaryCreated);
    }


    private String getResourceAccountAddress() {
        return aptosAccountRepository.findById(ContractConstants.RESOURCE_ACCOUNT_ADDRESS)
                .map(AptosAccount::getAddress).orElse(null);
    }
}
