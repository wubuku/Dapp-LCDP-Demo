// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.rooch.contract.service;

import com.github.wubuku.rooch.bean.GetAnnotatedStatesResponseMoveStructItem;
import com.github.wubuku.rooch.utils.RoochJsonRpcClient;
import org.dddml.roochdemocontracts.domain.daysummary.*;
import org.dddml.roochdemocontracts.domain.*;
import org.dddml.roochdemocontracts.rooch.contract.DomainBeanUtils;
import org.dddml.roochdemocontracts.rooch.bcs.BcsDomainBeanUtils;
import org.dddml.roochdemocontracts.rooch.contract.DaySummary;

import java.util.*;
import java.math.*;
import java.util.function.*;

public class RoochDaySummaryStateRetriever {

    private RoochJsonRpcClient roochJsonRpcClient;

    private Function<Day, DaySummaryState.MutableDaySummaryState> daySummaryStateFactory;


    public RoochDaySummaryStateRetriever(RoochJsonRpcClient roochJsonRpcClient,
                                    Function<Day, DaySummaryState.MutableDaySummaryState> daySummaryStateFactory
    ) {
        this.roochJsonRpcClient = roochJsonRpcClient;
        this.daySummaryStateFactory = daySummaryStateFactory;
    }

    public DaySummaryState retrieveDaySummaryState(String objectId) {
        List<GetAnnotatedStatesResponseMoveStructItem<DaySummary.MoveObject>> getObjectListResponse = roochJsonRpcClient.getMoveStructAnnotatedStates(
                "/object/" + com.github.wubuku.rooch.utils.HexUtils.formatHex(objectId),
                DaySummary.MoveObject.class
        );
        if (getObjectListResponse.size() == 0) {
            return null;
        }
        DaySummary.MoveObject daySummary = getObjectListResponse.get(0).getMoveValue().getValue();
        return toDaySummaryState(daySummary);
    }

    private DaySummaryState toDaySummaryState(DaySummary.MoveObject daySummaryObj) {
        DaySummary daySummary = daySummaryObj.getValue().getValue();
        DaySummaryState.MutableDaySummaryState daySummaryState = daySummaryStateFactory.apply(DomainBeanUtils.toDay(daySummary.getDay()));
        daySummaryState.setId_(daySummaryObj.getId());
        daySummaryState.setVersion(daySummary.getVersion());
        daySummaryState.setDescription(daySummary.getDescription());
        daySummaryState.setMetadata(daySummary.getMetadata());
        daySummaryState.setArrayData(new HashSet<>(Arrays.asList(daySummary.getArrayData())));
        daySummaryState.setOptionalData(daySummary.getOptionalData().getValue().getVec().length == 0 ? null : daySummary.getOptionalData().getValue().getVec()[0]);
        daySummaryState.setU16ArrayData(new HashSet<>(Arrays.asList(daySummary.getU16ArrayData())));
        daySummaryState.setU32ArrayData(new HashSet<>(Arrays.asList(daySummary.getU32ArrayData())));
        daySummaryState.setU64ArrayData(new HashSet<>(Arrays.asList(daySummary.getU64ArrayData())));
        daySummaryState.setU128ArrayData(new HashSet<>(Arrays.asList(daySummary.getU128ArrayData())));
        daySummaryState.setU256ArrayData(new HashSet<>(Arrays.asList(daySummary.getU256ArrayData())));
        return daySummaryState;
    }

}
