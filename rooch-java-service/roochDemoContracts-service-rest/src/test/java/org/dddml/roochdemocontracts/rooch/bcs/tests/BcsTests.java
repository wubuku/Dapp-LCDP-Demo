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
}
