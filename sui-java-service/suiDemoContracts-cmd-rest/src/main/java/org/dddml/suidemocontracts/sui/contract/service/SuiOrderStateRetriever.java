package org.dddml.suidemocontracts.sui.contract.service;

import com.github.wubuku.sui.bean.DynamicFieldInfo;
import com.github.wubuku.sui.bean.DynamicFieldPage;
import com.github.wubuku.sui.bean.GetMoveObjectDataResponse;
import com.github.wubuku.sui.utils.SuiJsonRpcClient;
import org.dddml.suidemocontracts.domain.order.OrderItemState;
import org.dddml.suidemocontracts.domain.order.OrderState;
import org.dddml.suidemocontracts.sui.contract.Order;
import org.dddml.suidemocontracts.sui.contract.OrderItem;
import org.dddml.suidemocontracts.sui.contract.OrderItemDynamicField;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class SuiOrderStateRetriever {

    private SuiJsonRpcClient suiJsonRpcClient;

    private Function<String, OrderState.MutableOrderState> orderStateFactory;
    private BiFunction<OrderState, String, OrderItemState.MutableOrderItemState> orderItemStateFactory;

    public SuiOrderStateRetriever(SuiJsonRpcClient suiJsonRpcClient,
                                  Function<String, OrderState.MutableOrderState> orderStateFactory,
                                  BiFunction<OrderState, String, OrderItemState.MutableOrderItemState> orderItemStateFactory
    ) {
        this.suiJsonRpcClient = suiJsonRpcClient;
        this.orderStateFactory = orderStateFactory;
        this.orderItemStateFactory = orderItemStateFactory;
    }

    public OrderState retrieveOrderState(String objectId) {
        GetMoveObjectDataResponse<Order> getObjectDataResponse = suiJsonRpcClient.getMoveObject(
                objectId, Order.class
        );

        Order order = getObjectDataResponse.getDetails().getData().getFields();
        return toOrderState(order);
    }


    private OrderState toOrderState(Order order) {
        // new aggregate root state instance
        OrderState.MutableOrderState orderState = orderStateFactory.apply(order.getId().getId());
        orderState.setVersion(order.getVersion()); // on-chain version
        orderState.setTotalAmount(order.getTotalAmount());

        // /////////// get inner entity items /////////////
        String itemsTableId = order.getItems().getFields().getId().getId();
        List<OrderItem> items = getItems(itemsTableId);
        for (OrderItem item : items) {
            orderState.getItems().add(toOrderItemState(orderState, item));
        }
        // ///////////////////////////////////////////////

        return orderState;
    }

    private OrderItemState toOrderItemState(OrderState orderState, OrderItem item) {
        // new inner entity state instance
        OrderItemState.MutableOrderItemState orderItemState = orderItemStateFactory.apply(orderState, item.getProductId());
        orderItemState.setProductId(item.getProductId());
        orderItemState.setQuantity(item.getQuantity());
        orderItemState.setItemAmount(item.getItemAmount());

        // ////// no inner entity in the entity //////
        // ///////////////////////////////////////////

        return orderItemState;
    }

    private List<OrderItem> getItems(String orderItemTableId) {
        List<OrderItem> orderItems = new ArrayList<>();
        String cursor = null;
        while (true) {
            DynamicFieldPage orderItemFieldPage = suiJsonRpcClient.getDynamicFields(orderItemTableId, cursor, null);
            for (DynamicFieldInfo orderItemFieldInfo : orderItemFieldPage.getData()) {
                //String fieldName = orderItemFieldInfo.getName();
                //System.out.println("field name: " + orderItemFieldInfo.getName());
                String fieldObjectId = orderItemFieldInfo.getObjectId();
                //System.out.println("field object Id: " + fieldObjectId);
                //System.out.println("== get dynamic field object by parent_id and field_name ==");
//                GetMoveObjectDataResponse<OrderItemDynamicField> getOrderItemFieldObjectDataResponse = client
//                        .getDynamicFieldMoveObject(testOrderItemTableId, fieldName, OrderItemDynamicField.class);
////                System.out.println(getOrderItemFieldObjectDataResponse);
////                System.out.println("== get object by id. ==");
                GetMoveObjectDataResponse<OrderItemDynamicField> getOrderItemFieldResponse
                        = suiJsonRpcClient.getMoveObject(fieldObjectId, OrderItemDynamicField.class);
//                System.out.println(getOrderItemFieldResponse);
//                System.out.println(getOrderItemFieldResponse.getDetails().getData().getFields().getName());
                OrderItem orderItem = getOrderItemFieldResponse
                        .getDetails()   // GetMoveObjectDataResponse.Details
                        .getData()      // MoveObject<OrderItemDynamicField>
                        .getFields()    // OrderItemDynamicField
                        .getValue()     // MoveObject<OrderItem>
                        .getFields()    // OrderItem
                        ;
                //System.out.println(orderItem);
                orderItems.add(orderItem);
            }
            cursor = orderItemFieldPage.getNextCursor();
            if (cursor == null) {
                //System.out.println("end of pages");
                break;
            }
        }
        return orderItems;
    }
}
