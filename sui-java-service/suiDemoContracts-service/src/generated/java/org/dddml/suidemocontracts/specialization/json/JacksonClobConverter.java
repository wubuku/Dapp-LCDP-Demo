package org.dddml.suidemocontracts.specialization.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dddml.suidemocontracts.specialization.ClobConverter;

import java.io.IOException;
import java.util.Map;

public class JacksonClobConverter implements ClobConverter {

    private static final ObjectMapper OBJECT_MAPPER;

    static {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER = objectMapper;
    }

    @Override
    public String toString(Map<String, Object> lobProperties) {
        try {
            return OBJECT_MAPPER.writeValueAsString(lobProperties);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, Object> parseLobProperties(String text) {
        try {
            return OBJECT_MAPPER.readValue(text, new TypeReference<Map<String, Object>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
