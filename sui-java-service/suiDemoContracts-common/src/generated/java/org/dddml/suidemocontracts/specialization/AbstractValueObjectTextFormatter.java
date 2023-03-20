package org.dddml.suidemocontracts.specialization;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Created by yangjiefeng on 2018/3/6.
 */
public abstract class AbstractValueObjectTextFormatter<T> implements TextFormatter<T> {

    private static final String FOR_EACH_FLATTENED_PROPERTY_METHOD_NAME = "forEachFlattenedProperty";

    private static final String SET_FLATTENED_PROPERTY_VALUES_METHOD_NAME = "setFlattenedPropertyValues";

    private static final String FLATTENED_PROPERTY_TYPES_FIELD_NAME = "FLATTENED_PROPERTY_TYPES";

    private static final String FLATTENED_PROPERTY_TYPE_MAP_FIELD_NAME = "FLATTENED_PROPERTY_TYPE_MAP";

    private String fieldSeparator = ",";

    protected String getFieldSeparator() {
        return fieldSeparator;
    }

    protected void setFieldSeparator(String separator) {
        this.fieldSeparator = separator;
    }

    private Class<T> valueObjectClass;

    public AbstractValueObjectTextFormatter(Class<T> clazz, String fieldSeparator) {
        this(clazz);
        this.fieldSeparator = fieldSeparator;
    }

    public AbstractValueObjectTextFormatter(Class<T> clazz) {
        this.valueObjectClass = clazz;
    }


    @Override
    public T parse(String text) {
        String[] strValues = text.split(getFieldSeparator());
        T obj = null;
        try {
            obj = this.valueObjectClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        String[] fieldTypes = new String[0];
        try {
            Field f = this.valueObjectClass.getDeclaredField(FLATTENED_PROPERTY_TYPES_FIELD_NAME);
            f.setAccessible(true);
            fieldTypes = (String[]) f.get(null);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        if (strValues.length != fieldTypes.length) {
            throw new IllegalArgumentException(String.format("The fields count in argument \"%1$s\" is NOT right.", "text"));
        }
        Object[] filedValues = new Object[fieldTypes.length];
        for (int i = 0; i < fieldTypes.length; i++) {
            String strVal = strValues[i].trim();
            filedValues[i] = convertFromString(getClassByTypeName(fieldTypes[i]), strVal);
        }
        try {
            Method m = this.valueObjectClass
                    .getDeclaredMethod(SET_FLATTENED_PROPERTY_VALUES_METHOD_NAME, Object[].class);
            m.setAccessible(true);
            m.invoke(obj, new Object[]{filedValues});
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    @Override
    public String toString(T value) {
        final Map<String, String> propMap;
        try {
            Field f = this.valueObjectClass.getDeclaredField(FLATTENED_PROPERTY_TYPE_MAP_FIELD_NAME);
            f.setAccessible(true);
            propMap = (Map<String, String>) f.get(null);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        StringBuilder sb = new StringBuilder();
        BiConsumer<String, Object> append = (n, v) -> {
            if (!sb.toString().isEmpty()) {
                sb.append(getFieldSeparator());
            }
            Class<?> t = getClassByTypeName(propMap.get(n));
            sb.append(convertToString(t, v));
        };
        try {
            Method m = this.valueObjectClass
                    .getDeclaredMethod(FOR_EACH_FLATTENED_PROPERTY_METHOD_NAME, BiConsumer.class);
            m.setAccessible(true);
            m.invoke(value, append);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    protected String convertToString(Class type, Object value) {
        return ApplicationContext.current.getTypeConverter().convertToString(type, value);
    }

    protected Object convertFromString(Class type, String text) {
        return ApplicationContext.current.getTypeConverter().convertFromString(type, text);
    }

    protected abstract Class<?> getClassByTypeName(String type);

}
