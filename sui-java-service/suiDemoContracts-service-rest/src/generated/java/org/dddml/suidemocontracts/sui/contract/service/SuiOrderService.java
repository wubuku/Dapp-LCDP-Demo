// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.suidemocontracts.sui.contract.service;

import com.github.wubuku.sui.utils.SuiJsonRpcClient;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.domain.order.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.math.*;

@Service
public class SuiOrderService {

    @Autowired
    private OrderStateRepository orderStateRepository;

    private SuiOrderStateRetriever suiOrderStateRetriever;

    @Autowired
    public SuiOrderService(SuiJsonRpcClient suiJsonRpcClient) {
        this.suiOrderStateRetriever = new SuiOrderStateRetriever(suiJsonRpcClient,
                id -> {
                    OrderState.MutableOrderState s = new AbstractOrderState.SimpleOrderState();
                    s.setId(id);
                    return s;
                },
                (orderState, productId) -> (OrderItemState.MutableOrderItemState)
                        ((EntityStateCollection.ModifiableEntityStateCollection<String, OrderItemState>) orderState.getItems()).getOrAddDefault(productId)
        );
    }

    @Transactional
    public void updateOrderState(String objectId) {
        OrderState orderState = suiOrderStateRetriever.retrieveOrderState(objectId);
        if (orderState == null) {
            return;
        }
        orderStateRepository.merge(orderState);
    }

    @Transactional
    public void deleteOrder(String objectId) {
        OrderState.MutableOrderState s = (OrderState.MutableOrderState) orderStateRepository.get(objectId, true);
        if (s != null) {
            s.setDeleted(true);
            orderStateRepository.merge(s);
        }
    }

}

