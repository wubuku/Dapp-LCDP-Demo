// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.aptosdemocontracts.aptos.contract.service;


import com.github.wubuku.aptos.bean.AccountResource;
import com.github.wubuku.aptos.utils.*;
import org.dddml.aptosdemocontracts.aptos.contract.AptosAccount;
import org.dddml.aptosdemocontracts.aptos.contract.ContractConstants;
import org.dddml.aptosdemocontracts.aptos.contract.DomainBeanUtils;
import org.dddml.aptosdemocontracts.aptos.contract.repository.AptosAccountRepository;
import org.dddml.aptosdemocontracts.domain.daysummary.*;
import org.dddml.aptosdemocontracts.domain.*;
import org.dddml.aptosdemocontracts.aptos.contract.DaySummary;

import java.io.IOException;
import java.math.*;
import java.util.*;
import java.util.function.*;


public class AptosDaySummaryStateRetriever {

    private NodeApiClient aptosNodeApiClient;

    private String aptosContractAddress;

    private AptosAccountRepository aptosAccountRepository;

    private Function<Day, DaySummaryState.MutableDaySummaryState> daySummaryStateFactory;


    public AptosDaySummaryStateRetriever(NodeApiClient aptosNodeApiClient,
                                    String aptosContractAddress,
                                    AptosAccountRepository aptosAccountRepository,
                                    Function<Day, DaySummaryState.MutableDaySummaryState> daySummaryStateFactory
    ) {
        this.aptosNodeApiClient = aptosNodeApiClient;
        this.aptosContractAddress = aptosContractAddress;
        this.aptosAccountRepository = aptosAccountRepository;
        this.daySummaryStateFactory = daySummaryStateFactory;
    }

    public DaySummaryState retrieveDaySummaryState(Day day) {
        String resourceAccountAddress = getResourceAccountAddress();
        AccountResource<DaySummary.Tables> accountResource;
        try {
            accountResource = aptosNodeApiClient.getAccountResource(resourceAccountAddress,
                    ContractConstants.DAY_SUMMARY_MODULE_TABLES,
                    DaySummary.Tables.class,
                    null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String tableHandle = accountResource.getData().getDaySummaryTable().getHandle();
        DaySummary daySummary;
        try {
            daySummary = aptosNodeApiClient.getTableItem(
                    tableHandle,
                    ContractConstants.toNumericalAddressType(ContractConstants.DAY_SUMMARY_ID_TYPE, this.aptosContractAddress),
                    this.aptosContractAddress + "::" + ContractConstants.DAY_SUMMARY_MODULE_DAY_SUMMARY,
                    day,
                    DaySummary.class,
                    null
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return toDaySummaryState(daySummary);
    }

    private DaySummaryState toDaySummaryState(DaySummary daySummary) {
        DaySummaryState.MutableDaySummaryState daySummaryState = daySummaryStateFactory.apply(DomainBeanUtils.toDay(daySummary.getDay()));
        daySummaryState.setVersion(daySummary.getVersion());
        daySummaryState.setDescription(daySummary.getDescription());
        daySummaryState.setMetadata(daySummary.getMetadata());
        daySummaryState.setArrayData(new HashSet<>(Arrays.asList(daySummary.getArrayData())));
        daySummaryState.setOptionalData(daySummary.getOptionalData().getVec().size() == 0 ? null : daySummary.getOptionalData().getVec().get(0));
        daySummaryState.setU16ArrayData(new HashSet<>(Arrays.asList(daySummary.getU16ArrayData())));
        daySummaryState.setU32ArrayData(new HashSet<>(Arrays.asList(daySummary.getU32ArrayData())));
        daySummaryState.setU64ArrayData(new HashSet<>(Arrays.asList(daySummary.getU64ArrayData())));
        daySummaryState.setU128ArrayData(new HashSet<>(Arrays.asList(daySummary.getU128ArrayData())));
        daySummaryState.setU256ArrayData(new HashSet<>(Arrays.asList(daySummary.getU256ArrayData())));
        return daySummaryState;
    }

    private String getResourceAccountAddress() {
        return aptosAccountRepository.findById(ContractConstants.RESOURCE_ACCOUNT_ADDRESS)
                .map(AptosAccount::getAddress).orElse(null);
    }

}

