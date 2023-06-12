package org.dddml.roochdemocontracts.rooch.bcs.tests;

import com.github.wubuku.rooch.utils.HexUtils;
import com.novi.serde.DeserializationError;
import com.novi.serde.SerializationError;
import org.dddml.roochdemocontracts.rooch.bcs.Day;
import org.dddml.roochdemocontracts.rooch.bcs.Month;
import org.dddml.roochdemocontracts.rooch.bcs.Year;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BcsTests {

    @Test
    void testSerializeAndDeserialize() throws SerializationError, DeserializationError {
        Year year = new Year();
        year.calendar = "ChineseLunar";
        year.number = 2023;
        Month month = new Month();
        month.year = year;
        month.number = 6;
        month.isLeap = false;
        Day day = new Day();
        day.month = month;
        day.number = 10;
        day.timeZone = "Beijing";

        byte[] bytes = day.bcsSerialize(); //0xe7070c4368696e6573654c756e617206000a074265696a696e67
        System.out.println(HexUtils.byteArrayToHexWithPrefix(bytes));

        Day day2 = Day.bcsDeserialize(bytes);
        System.out.println(day2);
        //day2.number = 1;
        Assertions.assertEquals(day, day2);
    }

    /*
[
  {
    "event_id": {
      "event_handle_id": "0xb6217859d5aa17d0f29b409671328a57944c72927cf6e8f9810bc19797f9c9bd",
      "event_seq": 0
    },
    "type_tag": "0xf8e38d63a5208d499725e7ac4851c4a0836e45e2230041b7e3cf43e4738c47b4::order_ship_group::OrderItemShipGroupAssociationTableItemAdded",
    "event_data": "0x076f72645f303031010c321aff9a2cb8aea76e8e1e5e58d93d9affd044dcc6de7770eb519c2db2209a",
    "event_index": 0
  },
  {
    "event_id": {
      "event_handle_id": "0x842c9722aac394a3074513409d93e6e277107e83edb456cfa8cf98de5098afb8",
      "event_seq": 0
    },
    "type_tag": "0xf8e38d63a5208d499725e7ac4851c4a0836e45e2230041b7e3cf43e4738c47b4::order::OrderShipGroupTableItemAdded",
    "event_data": "0x076f72645f30303101",
    "event_index": 1
  },
  {
    "event_id": {
      "event_handle_id": "0x895e25c84e55dbfe2a8e73034c534a7a3d540acf67d55313689cc6534513435e",
      "event_seq": 0
    },
    "type_tag": "0xf8e38d63a5208d499725e7ac4851c4a0836e45e2230041b7e3cf43e4738c47b4::order::OrderShipGroupAdded",
    "event_data": "0xa29559906755a3e83c1d0c8802fbcfce0c887fd1feccb90053a95f8da5b4f38d076f72645f3030310000000000000000010d736869705f6d6574686f645f310c321aff9a2cb8aea76e8e1e5e58d93d9affd044dcc6de7770eb519c2db2209a0100000000000000",
    "event_index": 2
  }
]
     */


}
