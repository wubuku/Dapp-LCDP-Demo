package org.dddml.roochdemocontracts.rooch.contract.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wubuku.rooch.bean.*;
import com.github.wubuku.rooch.utils.HexUtils;
import com.github.wubuku.rooch.utils.MoveOSStdViewFunctions;
import com.github.wubuku.rooch.utils.RoochJsonRpcClient;
import org.dddml.roochdemocontracts.rooch.contract.DaySummary;
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
        String path = "/object/0x4349384f4591976d3b5c043d81f7b468bab7ef851f51703923e26cda31133520";
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
        String tableHandle = "0x59e2c8f9d1d07e91fcd979fed7dbb98c3e0672b70633d9b5766054d89c738e61";
        byte[] key = new byte[]{1};
        String path = "/table/" + tableHandle + "/" + HexUtils.byteArrayToHexWithPrefix(key);
        RoochJsonRpcClient rpcClient = new RoochJsonRpcClient(rpcBaseUrl);
        List<TypedGetAnnotatedStatesResponseItem<BigInteger>> response = rpcClient
                .getAnnotatedStates(path, BigInteger.class);
        System.out.println(response);
        System.out.println(response.get(0).getMoveValue());

        TypedGetAnnotatedStatesResponseItem<BigInteger> response2 = rpcClient
                .getAnnotatedTableItem(tableHandle, key, BigInteger.class);
        System.out.println(response2);
        System.out.println(response2.getMoveValue());
    }

    @Test
    void testGetEvents_1() throws MalformedURLException, JsonProcessingException {
        String rpcBaseUrl = "http://127.0.0.1:50051/";
        //Id: "0x53f32af12dc9236eb67f1c064cf55ee8891a90040f71ba17422cfdd91eb7358b";
        String eventHandleType = "0x565d5717526aecec1f9d464867f7d92d6eae2dc8ca73a0dc2613dd185d3d7bc7::day_summary::DaySummaryCreated";
        RoochJsonRpcClient rpcClient = new RoochJsonRpcClient(rpcBaseUrl);
        List<AnnotatedEventView<Object>> getEventsResponse1 = rpcClient.getEventsByEventHandle(eventHandleType,
                null,
                null,
                Object.class);
        System.out.println(getEventsResponse1);
        System.out.println(getEventsResponse1.get(0).getParsedEventData().getValue().getClass());

        System.out.println("------------------------");
        BigInteger cursor = null;
        Long limit = 1L;
        System.out.println("cursor: " + cursor);
        List<AnnotatedEventView<DaySummaryCreated>> getEventsResponse2 = rpcClient.getEventsByEventHandle(eventHandleType,
                cursor, limit, DaySummaryCreated.class);
        System.out.println(getEventsResponse2);
        System.out.println(getEventsResponse2.get(0).getParsedEventData().getValue().getDay().getValue().getNumber());

        System.out.println("------------------------");
        cursor = getEventsResponse2.get(0).getEvent().getEventId().getEventSeq();
        cursor = cursor.add(BigInteger.ONE);
        System.out.println("cursor: " + cursor);

        getEventsResponse2 = rpcClient.getEventsByEventHandle(eventHandleType,
                cursor, limit, DaySummaryCreated.class);
        System.out.println(getEventsResponse2);
        //System.out.println(getEventsResponse2.get(0).getParsedEventData().getValue().i);
        //System.out.println("cursor: " + cursor);
    }

    @Test
    void testGetEvents_2() throws MalformedURLException, JsonProcessingException {
//        String rpcBaseUrl = "http://127.0.0.1:50051/";
//        RoochJsonRpcClient rpcClient = new RoochJsonRpcClient(rpcBaseUrl);
//
//        String eventHandleType2 = "0x565d5717526aecec1f9d464867f7d92d6eae2dc8ca73a0dc2613dd185d3d7bc7::something::BarTableItemAdded";//Id: "0x86a59b599dad6bd0b054eee46578c962f83479c38405555cf7ee421f82b4c7b2";
//        List<AnnotatedEventView<TestBarTableItemAdded>> getEventsResponse3 = rpcClient.getEventsByEventHandle(eventHandleType2,
//                null, null, TestBarTableItemAdded.class);
//        System.out.println(getEventsResponse3);
//        System.out.println(getEventsResponse3.get(0).getParsedEventData().getValue().item.getValue().key.getClass());
//        System.out.println(getEventsResponse3.get(0).getParsedEventData().getValue().item.getValue().value.getClass());
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
