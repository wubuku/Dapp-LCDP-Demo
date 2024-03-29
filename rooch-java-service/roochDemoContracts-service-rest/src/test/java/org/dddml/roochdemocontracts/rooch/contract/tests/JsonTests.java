package org.dddml.roochdemocontracts.rooch.contract.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dddml.roochdemocontracts.rooch.contract.Day;
import org.dddml.roochdemocontracts.rooch.contract.Month;
import org.dddml.roochdemocontracts.rooch.contract.Year;
import org.junit.jupiter.api.Test;

public class JsonTests {
    @Test
    void testJson_1() throws JsonProcessingException {
        Day d = new Day();
        d.setValue(new Day.DayValue());
        d.getValue().setMonth(new Month());
        d.getValue().getMonth().setValue(new Month.MonthValue());
        d.getValue().getMonth().getValue().setYear(new Year());
        d.getValue().getMonth().getValue().getYear().setValue(new Year.YearValue());

        d.getValue().getMonth().getValue().getYear().getValue().setNumber(2023);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(d);
        System.out.println(json);
        Day d2 = objectMapper.readValue(json, Day.class);
        System.out.println(d2.getValue().getMonth().getValue().getYear().getValue().getNumber());
    }

    @Test
    void testMisc_1() {
        short b = (short) 0xff;
        System.out.println(Short.valueOf(b).byteValue());

        int u16 = 0xffff;
        short s = (short) u16;
        System.out.println(s);
        System.out.println(Integer.valueOf(u16).shortValue());

        Long u32 = 0xffffffffL;
        Integer i = (int) u32.longValue();
        System.out.println(i);
        System.out.println(u32.intValue());

        java.math.BigInteger u64 = new java.math.BigInteger("18446744073709551615");
        Long l = u64.longValue();
        System.out.println(l);

    }
}
