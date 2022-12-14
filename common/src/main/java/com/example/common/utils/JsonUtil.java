package com.example.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public class JsonUtil {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final ObjectMapper mapperExcludeNull = new ObjectMapper();

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        mapperExcludeNull.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapperExcludeNull.setSerializationInclusion(Include.NON_NULL);
    }

    public static String toJson(Object obj) throws JsonProcessingException {
        return toJson(obj, mapper);
    }

    private static String toJson(Object obj, ObjectMapper objectMapper) throws JsonProcessingException {
        if (obj == null) {return null;}
        return objectMapper.writeValueAsString(obj);
    }

    public static String toJsonIgnoreNull(Object obj) throws JsonProcessingException {
        return toJson(obj, mapperExcludeNull);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        if (json == null || "".equals(json)) {
            return null;
        }
        T t = null;
        try {
            t = mapper.readValue(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return t;
    }

    public static <T> T fromJson(InputStream is, Class<T> clazz) {
        if (is == null)
        {return null;}

        T t = null;
        try {
            t = mapper.readValue(is, clazz);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return t;
    }

    public static <T> T fromJson(byte[] src, Class<T> clazz) {
        if (src == null)
        { return null;}

        T t = null;
        try {
            t = mapper.readValue(src, clazz);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
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
        return mapper.readValue(jsonArray, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
    }

    public static <T> T fromJson(String json, JavaType javaType) throws IOException {
        return mapper.readValue(json, javaType);
    }

    /**
     * ????????? Map ????????????????????? JavaBean
     *
     * @param clazz ??????????????????
     * @param map   ?????????????????? map
     * @return ??????????????? JavaBean ??????
     * @throws IntrospectionException    ???????????????????????????
     * @throws IllegalAccessException    ??????????????? JavaBean ??????
     * @throws InstantiationException    ??????????????? JavaBean ??????
     * @throws InvocationTargetException ????????????????????? setter ????????????
     */
    @SuppressWarnings("rawtypes")
    public static <T> T fromMap(Map map, Class<T> clazz) {
        T obj = null;
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            obj = clazz.newInstance(); // ?????? JavaBean ??????
            // ??? JavaBean ?????????????????????
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (int i = 0; i < propertyDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();
                if (map.containsKey(propertyName)) {
                    // ?????????????????? try ???????????????????????????????????????????????????????????????????????????????????????
                    Object value = map.get(propertyName);
                    if ("".equals(value)) {
                        value = null;
                    }
                    Object[] args = new Object[1];
                    args[0] = value;
                    try {
                        descriptor.getWriteMethod().invoke(obj, args);
                    } catch (InvocationTargetException e) {
                        System.out.println("??????????????????");
                    }
                }
            }
        } catch (IllegalAccessException | InstantiationException e) {
            System.out.println("????????? JavaBean ??????");
        } catch (IntrospectionException e) {
            System.out.println("?????????????????????");
        } catch (IllegalArgumentException e) {
            System.out.println("????????????");
        }
        return (T) obj;
    }

}
