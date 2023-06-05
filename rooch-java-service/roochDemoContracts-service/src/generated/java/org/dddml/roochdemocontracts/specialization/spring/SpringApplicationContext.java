package org.dddml.roochdemocontracts.specialization.spring;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dddml.roochdemocontracts.specialization.ApplicationContext;
import org.dddml.roochdemocontracts.specialization.ClobConverter;
import org.dddml.roochdemocontracts.specialization.TypeConverter;
import org.dddml.roochdemocontracts.specialization.json.JacksonClobConverter;

/**
 * Created by Yang on 2016/7/28.
 */
public class SpringApplicationContext extends ApplicationContext {

    private org.springframework.context.ApplicationContext innerApplicationContext;

    public SpringApplicationContext(org.springframework.context.ApplicationContext innerApplicationContext) {
        this.innerApplicationContext = innerApplicationContext;
    }

    @Override
    public Object get(String name) {
        int len = 1;
        if (name.startsWith("_")) {
            len = 2;
        }
        String camelName = name.substring(0, len).toLowerCase() + name.substring(len);
        if (innerApplicationContext.containsBean(camelName)) {
            Object obj = innerApplicationContext.getBean(camelName);
            return obj;
        } else {
            if (innerApplicationContext.containsBean(name)) {
                return innerApplicationContext.getBean(name);
            } else {
                return null;
            }
        }
    }

    @Override
    public <T> T get(final Class<T> type) {
        return innerApplicationContext.getBean(type);
    }

    protected static final ClobConverter DEFAULT_CLOB_CONVERTER = new JacksonClobConverter();

    @Override
    public ClobConverter getClobConverter() {
        ClobConverter clobConverter = (ClobConverter) get("clobConverter");
        if (clobConverter == null) {
            return DEFAULT_CLOB_CONVERTER;
        }
        return clobConverter;
    }

    protected static final JacksonTypeConverter DEFAULT_JACKSON_TYPE_CONVERTER = new JacksonTypeConverter();

    @Override
    public TypeConverter getTypeConverter() {
        return DEFAULT_JACKSON_TYPE_CONVERTER;
    }

    public static class JacksonTypeConverter extends DefaultTypeConverter {
        private static final ObjectMapper objectMapper;

        static {
            objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }

        @Override
        public <T> T convertValue(Object fromValue, Class<T> toValueType) {
            return objectMapper.convertValue(fromValue, toValueType);
        }
    }

}
