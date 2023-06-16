package org.dddml.roochdemocontracts.rooch.contract.service;

import com.github.wubuku.rooch.bean.GetAnnotatedStatesResponseMoveStructItem;
import com.github.wubuku.rooch.utils.RoochJsonRpcClient;
import org.dddml.roochdemocontracts.domain.order.OrderItemState;
import org.dddml.roochdemocontracts.domain.order.OrderState;
import org.dddml.roochdemocontracts.rooch.bcs.BcsDomainBeanUtils;
import org.dddml.roochdemocontracts.rooch.contract.Order;
import org.dddml.roochdemocontracts.rooch.contract.OrderItem;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;


public class RoochOrderStateRetriever {
    private RoochJsonRpcClient roochJsonRpcClient;
    private Function<String, OrderState.MutableOrderState> orderStateFactory;
    private BiFunction<OrderState, String, OrderItemState.MutableOrderItemState> orderItemStateFactory;
    private OrderItemProductObjectIdsGetter orderItemProductObjectIdsGetter;

    public RoochOrderStateRetriever(RoochJsonRpcClient roochJsonRpcClient,
                                    Function<String, OrderState.MutableOrderState> orderStateFactory,
                                    BiFunction<OrderState, String, OrderItemState.MutableOrderItemState> orderItemStateFactory,
                                    OrderItemProductObjectIdsGetter orderItemProductObjectIdsGetter
    ) {
        this.roochJsonRpcClient = roochJsonRpcClient;
        this.orderStateFactory = orderStateFactory;
        this.orderItemStateFactory = orderItemStateFactory;
        this.orderItemProductObjectIdsGetter = orderItemProductObjectIdsGetter;
    }

    public OrderState retrieveOrderState(String objectId) {
        List<GetAnnotatedStatesResponseMoveStructItem<Order.MoveObject>> getObjectListResponse = roochJsonRpcClient.getMoveStructAnnotatedStates(
                "/object/" + objectId,
                Order.MoveObject.class
        );
        if (getObjectListResponse.size() == 0) {
            return null;
        }
        Order.MoveObject order = getObjectListResponse.get(0).getMoveValue().getValue();
        return toOrderState(order);
    }

    private OrderState toOrderState(Order.MoveObject orderObj) {
        Order order = orderObj.getValue().getValue();
        OrderState.MutableOrderState orderState = orderStateFactory.apply(order.getOrderId());//note: orderId!
        orderState.setId_(orderObj.getId()); // object ID
        orderState.setVersion(order.getVersion());
        orderState.setTotalAmount(order.getTotalAmount());
        if (order.getItems() != null) {
            String orderItemTableHandle = order.getItems().getValue().getHandle();
            List<OrderItem> items = getOrderItems(orderItemTableHandle, orderItemProductObjectIdsGetter.getOrderItemProductObjectIds(orderState.getOrderId()));
            for (OrderItem i : items) {
                orderState.getItems().add(toOrderItemState(orderState, i));
            }
        }

        return orderState;
    }

    private OrderItemState toOrderItemState(OrderState orderState, OrderItem orderItem) {
        OrderItemState.MutableOrderItemState orderItemState = orderItemStateFactory.apply(orderState, orderItem.getProductObjectId());
        orderItemState.setQuantity(orderItem.getQuantity());
        orderItemState.setItemAmount(orderItem.getItemAmount());
        return orderItemState;
    }

    private List<OrderItem> getOrderItems(String orderItemTableHandle, List<String> productObjectIds) {

        List<OrderItem> orderItems = new ArrayList<>();
        for (String productObjectId : productObjectIds) {
            String key = BcsDomainBeanUtils.formatRoochObjectIdHex(productObjectId);
            List<GetAnnotatedStatesResponseMoveStructItem<OrderItem>> getOrderItemTableItemResponse = roochJsonRpcClient
                    .getMoveStructAnnotatedStates("/table/" + orderItemTableHandle + "/" + key, OrderItem.class);
            if (getOrderItemTableItemResponse.size() == 1 && getOrderItemTableItemResponse.get(0) != null) {
                orderItems.add(getOrderItemTableItemResponse.get(0).getMoveValue().getValue());
            }
        }
        return orderItems;
    }

    public interface OrderItemProductObjectIdsGetter {
        List<String> getOrderItemProductObjectIds(String orderId);
    }


}

