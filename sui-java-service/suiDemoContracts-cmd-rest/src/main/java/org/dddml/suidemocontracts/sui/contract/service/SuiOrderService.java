// <autogenerated>
//   This file was generated by T4 code generator .
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.suidemocontracts.sui.contract.service;

import com.github.wubuku.sui.utils.SuiJsonRpcClient;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.domain.order.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

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
                        ((EntityStateCollection.ModifiableEntityStateCollection<String, OrderItemState>) orderState.getItems()).getOrAdd(productId)
        );
    }

    @Transactional
    public void updateOrderState(String objectId) {
        OrderState orderState = suiOrderStateRetriever.retrieveOrderState(objectId);
        orderStateRepository.merge(orderState);
    }


}

