package br.gov.mg.uberlandia.decserver.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.TimeZone;

@Component
public class JacksonUtils {

    public static final ObjectMapper objectMapper = defaultObjectMapper();

    public static ObjectMapper defaultObjectMapper() {
        return applyDefaultConfig(new ObjectMapper());
    }

    private static ObjectMapper applyDefaultConfig(ObjectMapper objectMapper) {
        return objectMapper.registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .setTimeZone(TimeZone.getDefault());
    }

    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new JacksonException(e.getMessage(), e);
        }
    }

    public static <T> String toRawString(T object) {
        return toJson(object).replace("\"", "");
    }

    public static <T> T parseJson(String json, Class<T> tClass) {
        try {
            return objectMapper.readValue(json, tClass);
        } catch (IOException e) {
            throw new JacksonException(e.getMessage(), e);
        }
    }

    public static <T> T fromJsonNode(JsonNode jsonNode, Class<T> tClass) {
        try {
            return objectMapper.treeToValue(jsonNode, tClass);
        } catch (IOException e) {
            throw new JacksonException(e.getMessage(), e);
        }
    }

    public static JsonNode toJsonNode(Object object) {
        return objectMapper.valueToTree(object);
    }

    public static <T extends JsonNode> T toJsonNode(String json) {
        try {
            return (T) objectMapper.readTree(json);
        } catch (IOException e) {
            throw new JacksonException(e.getMessage(), e);
        }
    }

    public static class JacksonException extends RuntimeException {
        public JacksonException() {
            super();
        }

        public JacksonException(String message) {
            super(message);
        }

        public JacksonException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
