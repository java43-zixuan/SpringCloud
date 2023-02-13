package com.example.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author ZH
 */
@Slf4j
public class JacksonUtil {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final ObjectMapper MAPPER_EXCLUDE_NULL = new ObjectMapper();

    static {
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER_EXCLUDE_NULL.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER_EXCLUDE_NULL.setSerializationInclusion(Include.NON_NULL);
    }

    public static String toJson(Object obj) throws JsonProcessingException {
        return toJson(obj, MAPPER);
    }

    private static String toJson(Object obj, ObjectMapper objectMapper) throws JsonProcessingException {
        if (obj == null){

            return null;
        }
        return objectMapper.writeValueAsString(obj);
    }

    public static String toJsonIgnoreNull(Object obj) throws JsonProcessingException {
        return toJson(obj, MAPPER_EXCLUDE_NULL);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        if (json == null || "".equals(json)) {
            return null;
        }
        T t = null;
        try {
            t = MAPPER.readValue(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return t;
    }

    public static <T> T fromJson(InputStream is, Class<T> clazz) {
        if (is == null){

            return null;
        }

        T t = null;
        try {
            t = MAPPER.readValue(is, clazz);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return t;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(String json) throws IOException {
        return fromJson(json, Map.class);
    }

    @SuppressWarnings("unchecked")
    public static List<Map<String, Object>> toList(String json) throws IOException {
        return fromJson(json, List.class);
    }

    public static <T> List<T> toList(String jsonArray, Class<T> clazz) throws IOException {
        return MAPPER.readValue(jsonArray, MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
    }

    public static <T> T fromJson(String json, JavaType javaType) throws IOException {
        return MAPPER.readValue(json, javaType);
    }

}
