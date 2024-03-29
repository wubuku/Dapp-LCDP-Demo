// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.suidemocontracts.sui.contract.service;

import com.github.wubuku.sui.utils.SuiJsonRpcClient;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.domain.orderv2.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.math.*;

@Service
public class SuiOrderV2Service {

    @Autowired
    private OrderV2StateRepository orderV2StateRepository;

    private SuiOrderV2StateRetriever suiOrderV2StateRetriever;

    @Autowired
    public SuiOrderV2Service(SuiJsonRpcClient suiJsonRpcClient) {
        this.suiOrderV2StateRetriever = new SuiOrderV2StateRetriever(suiJsonRpcClient,
                orderId -> {
                    OrderV2State.MutableOrderV2State s = new AbstractOrderV2State.SimpleOrderV2State();
                    s.setOrderId(orderId);
                    return s;
                },
                (orderV2State, productId) -> (OrderV2ItemState.MutableOrderV2ItemState)
                        ((EntityStateCollection.ModifiableEntityStateCollection<String, OrderV2ItemState>) orderV2State.getItems()).getOrAddDefault(productId),
                (orderV2State, shipGroupSeqId) -> (OrderShipGroupState.MutableOrderShipGroupState)
                        ((EntityStateCollection.ModifiableEntityStateCollection<Integer, OrderShipGroupState>) orderV2State.getOrderShipGroups()).getOrAddDefault(shipGroupSeqId),
                (orderShipGroupState, productId) -> (OrderItemShipGroupAssociationState.MutableOrderItemShipGroupAssociationState)
                        ((EntityStateCollection.ModifiableEntityStateCollection<String, OrderItemShipGroupAssociationState>) orderShipGroupState.getOrderItemShipGroupAssociations()).getOrAddDefault(productId),
                (orderItemShipGroupAssociationState, orderItemShipGroupAssocSubitemDay) -> (OrderItemShipGroupAssocSubitemState.MutableOrderItemShipGroupAssocSubitemState)
                        ((EntityStateCollection.ModifiableEntityStateCollection<Day, OrderItemShipGroupAssocSubitemState>) orderItemShipGroupAssociationState.getSubitems()).getOrAddDefault(orderItemShipGroupAssocSubitemDay)
        );
    }

    @Transactional
    public void updateOrderV2State(String objectId) {
        OrderV2State orderV2State = suiOrderV2StateRetriever.retrieveOrderV2State(objectId);
        if (orderV2State == null) {
            return;
        }
        orderV2StateRepository.merge(orderV2State);
    }

}

