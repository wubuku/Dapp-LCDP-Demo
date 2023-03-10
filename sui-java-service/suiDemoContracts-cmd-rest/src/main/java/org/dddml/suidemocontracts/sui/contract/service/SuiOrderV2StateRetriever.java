// <autogenerated>
//   This file was generated by T4 code generator .
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.suidemocontracts.sui.contract.service;

import com.github.wubuku.sui.bean.*;
import com.github.wubuku.sui.utils.*;
import org.dddml.suidemocontracts.domain.orderv2.*;
import org.dddml.suidemocontracts.sui.contract.*;

import java.util.*;
import java.math.*;
import java.util.function.*;

public class SuiOrderV2StateRetriever {

    private SuiJsonRpcClient suiJsonRpcClient;

    private Function<String, OrderV2State.MutableOrderV2State> orderV2StateFactory;
    private BiFunction<OrderV2State, String, OrderV2ItemState.MutableOrderV2ItemState> orderV2ItemStateFactory;

    public SuiOrderV2StateRetriever(SuiJsonRpcClient suiJsonRpcClient,
                                  Function<String, OrderV2State.MutableOrderV2State> orderV2StateFactory,
                                  BiFunction<OrderV2State, String, OrderV2ItemState.MutableOrderV2ItemState> orderV2ItemStateFactory
    ) {
        this.suiJsonRpcClient = suiJsonRpcClient;
        this.orderV2StateFactory = orderV2StateFactory;
        this.orderV2ItemStateFactory = orderV2ItemStateFactory;
    }

    public OrderV2State retrieveOrderV2State(String objectId) {
        GetMoveObjectDataResponse<OrderV2> getObjectDataResponse = suiJsonRpcClient.getMoveObject(
                objectId, OrderV2.class
        );

        OrderV2 orderV2 = getObjectDataResponse.getDetails().getData().getFields();
        return toOrderV2State(orderV2);
    }

    private OrderV2State toOrderV2State(OrderV2 orderV2) {
        OrderV2State.MutableOrderV2State orderV2State = orderV2StateFactory.apply(orderV2.getOrderId());
        orderV2State.setId_(orderV2.getId().getId());
        orderV2State.setVersion(orderV2.getVersion());
        orderV2State.setTotalAmount(orderV2.getTotalAmount());

        orderV2State.setEstimatedShipDate(DomainBeanUtils.toDay(orderV2.getEstimatedShipDate()));

        String orderV2ItemTableId = orderV2.getItems().getFields().getId().getId();
        List<OrderV2Item> items = getOrderV2Items(orderV2ItemTableId);
        for (OrderV2Item i : items) {
            orderV2State.getItems().add(toOrderV2ItemState(orderV2State, i));
        }

        return orderV2State;
    }

    private OrderV2ItemState toOrderV2ItemState(OrderV2State orderV2State, OrderV2Item orderV2Item) {
        OrderV2ItemState.MutableOrderV2ItemState orderV2ItemState = orderV2ItemStateFactory.apply(orderV2State, orderV2Item.getProductId());
        orderV2ItemState.setQuantity(orderV2Item.getQuantity());

        orderV2ItemState.setItemAmount(orderV2Item.getItemAmount());

        return orderV2ItemState;
    }

    private List<OrderV2Item> getOrderV2Items(String orderV2ItemTableId) {
        List<OrderV2Item> orderV2Items = new ArrayList<>();
        String cursor = null;
        while (true) {
            DynamicFieldPage orderV2ItemFieldPage = suiJsonRpcClient.getDynamicFields(orderV2ItemTableId, cursor, null);
            for (DynamicFieldInfo orderV2ItemFieldInfo : orderV2ItemFieldPage.getData()) {
            
                String fieldObjectId = orderV2ItemFieldInfo.getObjectId();
                GetMoveObjectDataResponse<OrderV2ItemDynamicField> getOrderV2ItemFieldResponse
                        = suiJsonRpcClient.getMoveObject(fieldObjectId, OrderV2ItemDynamicField.class);
                OrderV2Item orderV2Item = getOrderV2ItemFieldResponse
                        .getDetails().getData().getFields().getValue().getFields();
                orderV2Items.add(orderV2Item);
            }
            cursor =orderV2ItemFieldPage.getNextCursor();
            if (cursor == null) {
                break;
            }
        }
        return orderV2Items;
    }

    
}

