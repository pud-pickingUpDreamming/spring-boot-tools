package com.summer.tools.common.utils;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringReader;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * 基于jackson的 json 工具类
 * @author john
 */
public class JsonUtil {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        // 设置时区
        OBJECT_MAPPER.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        // 只处理非空值
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 忽略处理不了的属性
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 把对象转换成json字符串
     * @param obj 对象
     * @return json字符窜
     */
    public static String stringify(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error(MessageFormat.format("can not stringify {0} to {1}:", obj, String.class.getSimpleName()), e);
        }
        return null;
    }

    /**
     * @param str json字符串
     * @param clazz 指定类型class
     * @param <T> 转换后的类型
     * @return 把json字符串转换成指定类型的对象
     */
    public static <T> T parse(String str, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(new StringReader(str), clazz);
        } catch (IOException e) {
            logger.error(MessageFormat.format("can not parse {0} to {1}:", str, clazz.getSimpleName()), e);
        }
        return null;
    }

    /**
     * 把json字符串转换为指定泛型类型的map
     * @param str json字符串
     * @param clazz map value类型class
     * @param <T> map value类型
     * @return 指定泛型类型的map
     */
    @SuppressWarnings("unused")
    public static <T> Map<String, T> parseMap(String str, Class<T> clazz) {
        try {
            Map<String, T> resultMap = OBJECT_MAPPER.readValue(str, new AutoTypeReference<>());
            // 把LinkedHashMap转换成对象
            resultMap.forEach((k,v) -> {
                if (! v.getClass().getSimpleName().equals(clazz.getSimpleName())) {
                    T t = parse(stringify(v), clazz);
                    resultMap.put(k, t);
                }
            });
            return resultMap;
        }
        catch (IOException e) {
            logger.error(MessageFormat.format("can not parse {0} to Map<String, {1}>:", str, clazz.getSimpleName()), e);
        }
        return null;
    }

    /**
     * 把json字符串转换为指定泛型类型的map
     * @param str json字符串
     * @param clazz map value类型class
     * @param <T> map value类型
     * @return 指定泛型类型的map
     */
    public static <T> List<T> parseList(String str, Class<T> clazz) {
        try {
            List<T> resultList = OBJECT_MAPPER.readValue(str, new AutoTypeReference<>());
            // 把LinkedHashMap转换成对象
            resultList.forEach(e -> {
                if (! e.getClass().getSimpleName().equals(clazz.getSimpleName())) {
                    T t = parse(stringify(e), clazz);
                    resultList.set(resultList.indexOf(e), t);
                }
            });
            return resultList;
        }
        catch (IOException e) {
            logger.error(MessageFormat.format("can not parse {0} to List<{1}>:", str, clazz.getSimpleName()), e);
        }
        return null;
    }

    private static class AutoTypeReference<T> extends TypeReference<T> {}
}
