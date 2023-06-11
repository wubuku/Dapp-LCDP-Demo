package org.dddml.roochdemocontracts.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dddml.roochdemocontracts.rooch.contract.Day;
import org.dddml.roochdemocontracts.rooch.contract.Month;
import org.dddml.roochdemocontracts.rooch.contract.Year;

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
    }
}

