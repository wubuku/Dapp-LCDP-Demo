package org.dddml.suidemocontracts.specialization;

public interface TypeConverter {
    Object convertFromString(java.lang.Class type, String text);

    String convertToString(java.lang.Class type, Object value);

    String convertToString(Object value);

    String[] convertToStringArray(Object[] values);

    public <T> T convertValue(Object fromValue, Class<T> toValueType);
}