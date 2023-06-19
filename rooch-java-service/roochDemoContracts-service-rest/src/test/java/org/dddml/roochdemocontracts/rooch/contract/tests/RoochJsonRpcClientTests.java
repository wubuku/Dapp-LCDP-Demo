package org.dddml.roochdemocontracts.rooch.contract.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wubuku.rooch.bean.*;
import com.github.wubuku.rooch.utils.HexUtils;
import com.github.wubuku.rooch.utils.MoveOSStdViewFunctions;
import com.github.wubuku.rooch.utils.RoochJsonRpcClient;
import org.dddml.roochdemocontracts.rooch.contract.*;
import org.dddml.roochdemocontracts.rooch.contract.daysummary.DaySummaryCreated;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.util.List;

public class RoochJsonRpcClientTests {

    static final long DEFAULT_MAX_GAS_BUDGE = 1000000;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testGetStatesResponse_1() throws MalformedURLException, JsonProcessingException {
        String rpcBaseUrl = "http://127.0.0.1:50051/";
        String path = "/object/0xa29559906755a3e83c1d0c8802fbcfce0c887fd1feccb90053a95f8da5b4f38d";
        RoochJsonRpcClient rpcClient = new RoochJsonRpcClient(rpcBaseUrl);
        GetStatesResponse response = rpcClient.getStates(path);
        System.out.println(response);
    }

    @Test
    void testGetAnnotatedStatesResponse_1() throws MalformedURLException, JsonProcessingException {
        String objectId = "0x3ff04ab70c9b45c5b969fd2ee363ab3c576e2ab18e63b2a7d16aa7f676d072e5";

        String rpcBaseUrl = "http://127.0.0.1:50051/";
        String path = "/object/" + objectId;
        RoochJsonRpcClient rpcClient = new RoochJsonRpcClient(rpcBaseUrl);
        GetAnnotatedStatesResponse response = rpcClient.getAnnotatedStates(path);
        System.out.println(response);
        System.out.println(response.get(0).getMoveValue().getClass());

        // ////////////////////
        List<GetAnnotatedStatesResponseMoveStructItem<MoveOSStdObject>> response2 = rpcClient.getMoveStructAnnotatedStates(path,
                MoveOSStdObject.class, DaySummary.class
        );
        System.out.println(response2);
        System.out.println(response2.get(0).getMoveValue().getValue().getValue());
        MoveOSStdObject<DaySummary> moveOSStdObject = (MoveOSStdObject<DaySummary>) response2.get(0).getMoveValue().getValue();
        System.out.println(moveOSStdObject.getValue().getValue().getDay());

        // ////////////////////
        List<GetAnnotatedStatesResponseMoveStructItem<DaySummary.MoveObject>> response3 = rpcClient.getMoveStructAnnotatedStates(path,
                DaySummary.MoveObject.class
        );
        System.out.println(response3);
        System.out.println(response3.get(0).getMoveValue().getValue().getValue());
        System.out.println(response3.get(0).getMoveValue().getValue().getValue().getValue().getDay().getValue().getNumber());
    }

    @Test
    void testGetTableItem_1() throws MalformedURLException {
        String rpcBaseUrl = "http://127.0.0.1:50051/";
        RoochJsonRpcClient rpcClient = new RoochJsonRpcClient(rpcBaseUrl);
        String productObjId = "0xd1e84ac071bea210a3b3644e4aaa4b04b385953b84293919993361210b8a5f7d";
        String orderObjId = "0x2c59aa01ef0fe5344ca44c987aeedf783d9472742dde74ce4a9c957319e21189";


        System.out.println(productObjId);
        System.out.println(orderObjId);

        System.out.println(HexUtils.formatHex(productObjId));
        System.out.println(HexUtils.formatHex(orderObjId));
        if (true) return;
        List<GetAnnotatedStatesResponseMoveStructItem<Order.MoveObject>> getOrderResponse = rpcClient.getMoveStructAnnotatedStates(
                "/object/" + orderObjId,
                Order.MoveObject.class
        );
        System.out.println(getOrderResponse);
        //if (true) return;

        String shipGroupTableHandle = getOrderResponse.get(0).getMoveValue().getValue().getValue().getValue().getOrderShipGroups().getValue().getHandle();
        System.out.println("Order ship group table handle: " + shipGroupTableHandle);
        byte[] key = new byte[]{1}; // Order Ship Group Seq Id.
        String shipGroupTableItemPath = "/table/" + shipGroupTableHandle + "/" + HexUtils.byteArrayToHexWithPrefix(key);
        System.out.println("Order Ship Group TableItem Path: " + shipGroupTableItemPath);
        List<GetAnnotatedStatesResponseMoveStructItem<OrderShipGroup>> getShipGroupTableItemResponse = rpcClient
                .getMoveStructAnnotatedStates(shipGroupTableItemPath, OrderShipGroup.class);
        System.out.println(getShipGroupTableItemResponse);
        System.out.println(getShipGroupTableItemResponse.get(0).getMoveValue().getValue().getShipGroupSeqId());
        //if (true) return;

        //if (false) {
        String orderItemShipGroupAssociationTableHandle = getShipGroupTableItemResponse.get(0).getMoveValue().getValue().getOrderItemShipGroupAssociations().getValue().getHandle();
        System.out.println("orderItemShipGroupAssociationTableHandle: " + orderItemShipGroupAssociationTableHandle);
        System.out.println("Product ObjectID: " + productObjId);
        String orderItemShipGroupAssociationTableItemPath = "/table/" + orderItemShipGroupAssociationTableHandle + "/" + productObjId;
        System.out.println("OrderItemShipGroupAssociation TableItem Path: " + orderItemShipGroupAssociationTableItemPath);
        List<GetAnnotatedStatesResponseMoveStructItem<OrderItemShipGroupAssociation>> getOrderItemShipGroupAssociationResp = rpcClient
                .getMoveStructAnnotatedStates(orderItemShipGroupAssociationTableItemPath, OrderItemShipGroupAssociation.class);
        System.out.println(getOrderItemShipGroupAssociationResp);
        //if (true) return;
        //}

        //0xc4f1a312d456c28e0bdfefa16e4865fbe8049b256d339283936b3c6b7a107ec1
        String orderItemTableHandle = getOrderResponse.get(0).getMoveValue().getValue().getValue().getValue().getItems().getValue().getHandle();
        System.out.println("Order item table handle: " + orderItemTableHandle);
        String orderItemKey = productObjId; // Product Object Id.
        String orderItemTableItemPath = "/table/" + orderItemTableHandle + "/" + orderItemKey;
        System.out.println("Order Item TableItem Path: " + orderItemTableItemPath);
        List<GetAnnotatedStatesResponseMoveStructItem<OrderItem>> getOrderItemTableItemResponse = rpcClient
                .getMoveStructAnnotatedStates(orderItemTableItemPath, OrderItem.class);
        System.out.println(getOrderItemTableItemResponse);

//        TypedGetAnnotatedStatesResponseItem<BigInteger> getPrimitiveTableItemResponse = rpcClient
//                .getAnnotatedTableItem(tableHandle, key, BigInteger.class);
//        System.out.println(getPrimitiveTableItemResponse);
//        System.out.println(getPrimitiveTableItemResponse.getMoveValue());
    }

    @Test
    void testGetEvents_1() throws MalformedURLException, JsonProcessingException {
        String rpcBaseUrl = "http://127.0.0.1:50051/";
        //Id: "0x53f32af12dc9236eb67f1c064cf55ee8891a90040f71ba17422cfdd91eb7358b";
        String eventHandleType = "0x565d5717526aecec1f9d464867f7d92d6eae2dc8ca73a0dc2613dd185d3d7bc7::day_summary::DaySummaryCreated";
        RoochJsonRpcClient rpcClient = new RoochJsonRpcClient(rpcBaseUrl);
        EventPageView<Object> getEventsResponse1 = rpcClient.getEventsByEventHandle(eventHandleType,
                null,
                null,
                Object.class);
        System.out.println(getEventsResponse1);
        //System.out.println(getEventsResponse1.getData().get(0).getParsedEventData().getValue().getClass());

        System.out.println("------------------------");
        BigInteger cursor = null;
        Long limit = 1L;
        System.out.println("cursor: " + cursor);
        EventPageView<DaySummaryCreated> getEventsResponse2 = rpcClient.getEventsByEventHandle(eventHandleType,
                cursor, limit, DaySummaryCreated.class);
        System.out.println(getEventsResponse2);
        //System.out.println(getEventsResponse2.getData().get(0).getParsedEventData().getValue().getDay().getValue().getNumber());

        if (getEventsResponse2.getHasNextPage()) {
            System.out.println("------------------------");
            //cursor = getEventsResponse2.getData().get(0).getEvent().getEventId().getEventSeq();
            cursor = getEventsResponse2.getNextCursor();
            System.out.println("cursor: " + cursor);

            getEventsResponse2 = rpcClient.getEventsByEventHandle(eventHandleType,
                    cursor, limit, DaySummaryCreated.class);
            System.out.println(getEventsResponse2);
        }

    }

    @Test
    void testGetEvents_2() throws MalformedURLException, JsonProcessingException {
        String rpcBaseUrl = "http://127.0.0.1:50051/";
        RoochJsonRpcClient rpcClient = new RoochJsonRpcClient(rpcBaseUrl);

        String eventHandleType2 = "0xf8e38d63a5208d499725e7ac4851c4a0836e45e2230041b7e3cf43e4738c47b4"
                + "::order_ship_group::OrderItemShipGroupAssociationTableItemAdded";
        EventPageView<OrderItemShipGroupAssociationTableItemAdded> getEventsResponse3 = rpcClient.getEventsByEventHandle(eventHandleType2,
                null, null, OrderItemShipGroupAssociationTableItemAdded.class);
        System.out.println(getEventsResponse3);
        System.out.println(getEventsResponse3.getData().get(0).getParsedEventData().getValue().getOrderId());
        System.out.println(getEventsResponse3.getData().get(0).getParsedEventData().getValue().getOrderShipGroupShipGroupSeqId());
    }

    @Test
    void testGetEventsAndGetAnnotatedStates_1() throws MalformedURLException, JsonProcessingException {
//        String rpcBaseUrl = "http://127.0.0.1:50051/";
//        RoochJsonRpcClient rpcClient = new RoochJsonRpcClient(rpcBaseUrl);
//
//        String contractAddr = "0x565d5717526aecec1f9d464867f7d92d6eae2dc8ca73a0dc2613dd185d3d7bc7";
//        String eventType = contractAddr + "::something::SomethingCreated";
//        StringStringBigIntAnnotatedFunReturnValueViewTriple evtHandleResult = MoveOSStdViewFunctions.getEventHandle(rpcClient, eventType);
//        System.out.println(evtHandleResult);
//
//        // ////////// get event handle by event type //////////
//        String eventHandle = evtHandleResult.getItem1().getMoveValue();
//        System.out.println("eventHandle:");
//        System.out.println(eventHandle);//0xd503491955e774d10d2dc373f7507022190bd2202d2a8ddbe33d838f42b61521
//        System.out.println("-----------------");
//        List<AnnotatedEventView<TestSomethingCreated>> getEventsResponse1 = rpcClient.getEventsByEventHandle(
//                eventType, //eventHandle,
//                null, null, TestSomethingCreated.class);
//        System.out.println(getEventsResponse1);
//        System.out.println(getEventsResponse1.get(0).getParsedEventData().getValue().i);
//
//        // /////////// get created object ID in event emitted /////////
//        String objId = getEventsResponse1.get(0).getParsedEventData().getValue().obj_id;
//        System.out.println("Created ObjectID: " + objId);
//        System.out.println("-----------------");
//        String path = "/object/" + objId;
//        List<GetAnnotatedStatesResponseMoveStructItem<TestSomethingObject>> response3 = rpcClient.getMoveStructAnnotatedStates(path,
//                TestSomethingObject.class
//        );
//        System.out.println(response3);
//
//        // /////////// get table info. by table handle in object fields ///////////
//        MoveOSStdTable moveTable = response3.get(0).getMoveValue().getValue().getValue().getValue().barTable.getValue();
//        String tableHandle = moveTable.getHandle();
//        System.out.println("tableHandle: " + tableHandle);
//        String path4 = "/object/" + tableHandle;
//        List<GetAnnotatedStatesResponseMoveStructItem<MoveOSStdObject>> response4 = rpcClient.getMoveStructAnnotatedStates(path4,
//                MoveOSStdObject.class, MoveOSStdRawTableInfo.class
//        );
//        System.out.println(response4);
//        MoveOSStdObject<MoveOSStdRawTableInfo> tableMoveObj = response4.get(0).getMoveValue().getValue();
//        System.out.println(tableMoveObj.getValue().getValue().getStateRoot());
//
//        // //////////// get table item value by table handle and item key //////////
//        byte[] key = new byte[]{0};
//        TypedGetAnnotatedStatesResponseItem<BigInteger> tableItemVal1 = rpcClient.getAnnotatedTableItem(tableHandle, key, BigInteger.class);
//        System.out.println(tableItemVal1);
//
//        // ////////// get event handle by event type //////////
//        String eventType2 = contractAddr + "::something::BarTableItemAdded";
//        StringStringBigIntAnnotatedFunReturnValueViewTriple evtHandleResult2 = MoveOSStdViewFunctions.getEventHandle(rpcClient, eventType2);
//        System.out.println(evtHandleResult2);
//        String eventHandleId2 = evtHandleResult2.getItem1().getMoveValue();
//        //String eventHandle2 = "0x86a59b599dad6bd0b054eee46578c962f83479c38405555cf7ee421f82b4c7b2";
//        System.out.println(eventHandleId2);
//        List<AnnotatedEventView<TestBarTableItemAdded>> getEventsResponse2 = rpcClient.getEventsByEventHandle(
//                eventType2, null, null, TestBarTableItemAdded.class);
//        System.out.println(getEventsResponse2);
//        System.out.println(getEventsResponse2.get(0).getParsedEventData().getValue().item.getValue().key.getClass());
//        System.out.println(getEventsResponse2.get(0).getParsedEventData().getValue().item.getValue().value.getClass());

    }

    @Test
    void testExecuteViewFunction_1() throws MalformedURLException {
        String rpcBaseUrl = "http://127.0.0.1:50051/";
        RoochJsonRpcClient rpcClient = new RoochJsonRpcClient(rpcBaseUrl);
        String eventType = "0x565d5717526aecec1f9d464867f7d92d6eae2dc8ca73a0dc2613dd185d3d7bc7::something::SomethingCreated";
        Triple<?, ?, ?> result = MoveOSStdViewFunctions.getEventHandle(rpcClient, eventType);
        System.out.println(result);
    }

    @Test
    void testGetTransactions_1() throws MalformedURLException, JsonProcessingException {

    }

}
