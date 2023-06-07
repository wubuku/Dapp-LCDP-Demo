package org.dddml.suidemocontracts.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dddml.suidemocontracts.sui.contract.Day;
import org.dddml.suidemocontracts.sui.contract.Month;
import org.dddml.suidemocontracts.sui.contract.Year;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReflectionTests {
    public static Class<?> getGenericType(Class<?> clazz) {
        Type superClass = clazz.getGenericSuperclass();
        if (superClass instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) superClass;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            if (actualTypeArguments != null && actualTypeArguments.length > 0) {
                return (Class<?>) actualTypeArguments[0];
            }
        }
        return null;
    }

    public static void main(String[] args) throws JsonProcessingException {
        Class<?> dayFieldsClass = ReflectionTests.getGenericType(Day.class);
        System.out.println(dayFieldsClass);

        Day d = new Day();
        d.setFields(new Day.DayFields());
        d.getFields().setMonth(new Month());
        d.getFields().getMonth().setFields(new Month.MonthFields());
        d.getFields().getMonth().getFields().setYear(new Year());
        d.getFields().getMonth().getFields().getYear().setFields(new Year.YearFields());

        d.getFields().getMonth().getFields().getYear().getFields().setNumber(2023);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(d);
        System.out.println(json);
        Day d2 = objectMapper.readValue(json, Day.class);
        System.out.println(d2.getFields().getMonth().getFields().getYear().getFields().getNumber());
    }
}

