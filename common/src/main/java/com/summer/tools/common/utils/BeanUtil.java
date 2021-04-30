package com.summer.tools.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.beans.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * bean 操作工具类
 * 基于 spring BeanUtils工具类。
 * @author john.wang
 */
public class BeanUtil {

    private static final Logger logger = LoggerFactory.getLogger(BeanUtil.class);

    /**
     * 默认需要忽略的字段
     */
    private static final List<String> IGNORE_FIELDS =  new ArrayList<>(Arrays.asList("createTime","password"));

    /**
     * 对象拷贝
     * @param source 原对象
     * @param target 目标对象
     * @param ignoreFields 显示声明不需要拷贝的字段
     */
    public static void copyProperties(Object source, Object target, List<String> ignoreFields) {

        ignorePropertiesWrapper(ignoreFields, getEmptyFields(source));
        BeanUtils.copyProperties(source, target, IGNORE_FIELDS.toArray(new String[]{}));
    }

    private static List<String> getEmptyFields(Object source) {
        List<String> emptyFields = new ArrayList<>(10);
        try {
            PropertyDescriptor[] sourcePds = Introspector.getBeanInfo(source.getClass()).getPropertyDescriptors();
            for (PropertyDescriptor sourcePd:sourcePds) {
                Method readMethod = sourcePd.getReadMethod();
                // 忽略没有get方法的字段
                if (readMethod == null) {
                    emptyFields.add(sourcePd.getName());
                    continue;
                }
                // 忽略值为空的字段
                if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                    readMethod.setAccessible(true);
                }
                Object value = readMethod.invoke(source);
                if (value == null) {
                    emptyFields.add(sourcePd.getName());
                }
            }

            return emptyFields;
        } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
            logger.error("获取原数据空值属性出错", e);
            return null;
        }
    }

    private static void ignorePropertiesWrapper(List<String> ignoreFields, List<String> emptyFields) {
        if (ignoreFields != null) {
            IGNORE_FIELDS.addAll(ignoreFields);
        }
        if (emptyFields != null) {
            IGNORE_FIELDS.addAll(emptyFields);
        }
    }
}
