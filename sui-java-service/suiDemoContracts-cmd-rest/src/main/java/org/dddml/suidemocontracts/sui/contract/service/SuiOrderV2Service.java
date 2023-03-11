package org.dddml.suidemocontracts.sui.contract.service;

import com.github.wubuku.sui.utils.SuiJsonRpcClient;
import org.dddml.suidemocontracts.domain.orderv2.AbstractOrderV2ItemStateCollection;
import org.dddml.suidemocontracts.domain.orderv2.OrderV2ItemState;
import org.dddml.suidemocontracts.domain.orderv2.OrderV2State;
import org.dddml.suidemocontracts.domain.orderv2.OrderV2StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class SuiOrderV2Service {

    @Autowired
    private OrderV2StateRepository orderV2StateRepository;

    private SuiOrderV2StateRetriever suiOrderV2StateRetriever;

    @Autowired
    public SuiOrderV2Service(SuiJsonRpcClient suiJsonRpcClient) {
        this.suiOrderV2StateRetriever = new SuiOrderV2StateRetriever(suiJsonRpcClient,
                //todo refactor factories???
                id -> (OrderV2State.MutableOrderV2State)
                        orderV2StateRepository.get(id, false),
                (orderState, productId) -> (OrderV2ItemState.MutableOrderV2ItemState)
                        ((AbstractOrderV2ItemStateCollection) orderState.getItems()).getOrAdd(productId)
        );
    }

    @Transactional
    public void updateOrderState(String objectId) {
        OrderV2State orderState = suiOrderV2StateRetriever.retrieveOrderV2State(objectId);
        orderV2StateRepository.save(orderState);
    }


}
