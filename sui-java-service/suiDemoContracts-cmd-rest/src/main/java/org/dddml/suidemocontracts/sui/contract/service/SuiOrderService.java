package org.dddml.suidemocontracts.sui.contract.service;

import com.github.wubuku.sui.utils.SuiJsonRpcClient;
import org.dddml.suidemocontracts.domain.order.AbstractOrderItemStateCollection;
import org.dddml.suidemocontracts.domain.order.OrderItemState;
import org.dddml.suidemocontracts.domain.order.OrderState;
import org.dddml.suidemocontracts.domain.order.OrderStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class SuiOrderService {

    @Autowired
    private OrderStateRepository orderStateRepository;

    private SuiOrderStateRetriever suiOrderStateRetriever;

    @Autowired
    public SuiOrderService(SuiJsonRpcClient suiJsonRpcClient) {
        this.suiOrderStateRetriever = new SuiOrderStateRetriever(suiJsonRpcClient,
                //todo refactor factories???
                id -> (OrderState.MutableOrderState)
                        orderStateRepository.get(id, false),
                (orderState, productId) -> (OrderItemState.MutableOrderItemState)
                        ((AbstractOrderItemStateCollection) orderState.getItems()).getOrAdd(productId)
        );
    }

    @Transactional
    public void updateOrderState(String objectId) {
        OrderState orderState = suiOrderStateRetriever.retrieveOrderState(objectId);
        orderStateRepository.save(orderState);
    }


}
