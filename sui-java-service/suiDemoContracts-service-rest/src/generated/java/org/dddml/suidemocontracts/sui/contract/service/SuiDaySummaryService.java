// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.suidemocontracts.sui.contract.service;

import com.github.wubuku.sui.utils.SuiJsonRpcClient;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.domain.daysummary.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
public class SuiDaySummaryService {

    @Autowired
    private DaySummaryStateRepository daySummaryStateRepository;

    private SuiDaySummaryStateRetriever suiDaySummaryStateRetriever;

    @Autowired
    public SuiDaySummaryService(SuiJsonRpcClient suiJsonRpcClient) {
        this.suiDaySummaryStateRetriever = new SuiDaySummaryStateRetriever(suiJsonRpcClient,
                day -> {
                    DaySummaryState.MutableDaySummaryState s = new AbstractDaySummaryState.SimpleDaySummaryState();
                    s.setDay(day);
                    return s;
                }
        );
    }

    @Transactional
    public void updateDaySummaryState(String objectId) {
        DaySummaryState daySummaryState = suiDaySummaryStateRetriever.retrieveDaySummaryState(objectId);
        daySummaryStateRepository.merge(daySummaryState);
    }


}

