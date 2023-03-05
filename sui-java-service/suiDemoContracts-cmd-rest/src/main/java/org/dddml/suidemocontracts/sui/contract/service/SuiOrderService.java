package org.dddml.suidemocontracts.sui.contract.service;

import com.github.wubuku.sui.bean.DynamicFieldInfo;
import com.github.wubuku.sui.bean.DynamicFieldPage;
import com.github.wubuku.sui.bean.GetMoveObjectDataResponse;
import com.github.wubuku.sui.utils.SuiJsonRpcClient;
import org.dddml.suidemocontracts.domain.order.*;
import org.dddml.suidemocontracts.sui.contract.Order;
import org.dddml.suidemocontracts.sui.contract.OrderItem;
import org.dddml.suidemocontracts.sui.contract.OrderItemDynamicField;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class SuiOrderService {

    @Autowired
    private SuiJsonRpcClient suiJsonRpcClient;

    @Autowired
    private OrderStateRepository orderStateRepository;

    @Transactional
    public void updateOrderState(String objectId) {
        GetMoveObjectDataResponse<Order> getObjectDataResponse = suiJsonRpcClient.getMoveObject(
                objectId, Order.class
        );

        Order order = getObjectDataResponse.getDetails().getData().getFields();
        OrderState orderState = toOrderState(order);
        orderStateRepository.save(orderState);
    }

    @NotNull
    private OrderState toOrderState(Order order) {
        // new aggregate root state instance
        OrderState.MutableOrderState orderState = (OrderState.MutableOrderState)
                orderStateRepository.get(order.getId().getId(), false);
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
        OrderItemState.MutableOrderItemState orderItemState = (OrderItemState.MutableOrderItemState)
                ((AbstractOrderItemStateCollection) orderState.getItems()).getOrAdd(item.getProductId());
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
