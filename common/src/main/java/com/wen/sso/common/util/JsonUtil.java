package com.wen.sso.common.util;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * JSON 工具类
 *
 * @author wenjun
 * @since 2021/6/20
 */
@Slf4j
public class JsonUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static String toString(Object obj) {
        if (Objects.isNull(obj)) {
            return null;
        }
        if (obj instanceof String) {
            return (String) obj;
        }
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("JSON序列化异常：{}", obj, e);
            return null;
        }
    }

    public static <T> T toBean(String json, Class<T> tClass) {
        if (StrUtil.isEmpty(json)) {
            return null;
        }
        try {
            return MAPPER.readValue(json, tClass);
        } catch (JsonProcessingException e) {
            log.error("JSON解析异常：{}", json, e);
            return null;
        }
    }

    public static <T> List<T> toList(String json, Class<T> tClass) {
        if (StrUtil.isEmpty(json)) {
            return null;
        }
        try {
            CollectionType type = MAPPER.getTypeFactory().constructCollectionType(List.class, tClass);
            return MAPPER.readValue(json, type);
        } catch (JsonProcessingException e) {
            log.error("JSON解析异常：{}", json, e);
            return null;
        }
    }

    public static <K, V> Map<K, V> toMap(String json, Class<K> kClass, Class<V> vClass) {
        if (StrUtil.isEmpty(json)) {
            return null;
        }
        try {
            MapType type = MAPPER.getTypeFactory().constructMapType(Map.class, kClass, vClass);
            return MAPPER.readValue(json, type);
        } catch (JsonProcessingException e) {
            log.error("JSON解析异常：{}", json, e);
            return null;
        }
    }

    public static <T> T nativeRead(String json, TypeReference<T> type) {
        if (StrUtil.isEmpty(json)) {
            return null;
        }
        try {
            return MAPPER.readValue(json, type);
        } catch (JsonProcessingException e) {
            log.error("JSON解析异常：{}", json, e);
            return null;
        }
    }
}
