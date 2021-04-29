package com.summer.tools.common.utils;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import java.beans.*;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Locale.ENGLISH;

/**
 * bean 操作工具类
 * 部分参考 spring BeanUtils工具类。
 * @author john
 */
public class BeanUtil {

    private static final Logger logger = LoggerFactory.getLogger(BeanUtil.class);
    /**
     * 如果有确定不需要copy的, 把Collections.emptyList() 换成 Arrays.asList("a","b") ,并把不需copy的属性塞进去
     */
    private static final List<String> IGNORE_PROPERTIES =  new ArrayList<>(Arrays.asList("createTime","password"));

    private static void ignorePropertiesWrapper(List<String> ignoreProperties, List<String> emptyProperties) {
        if (ignoreProperties != null) {
            IGNORE_PROPERTIES.addAll(ignoreProperties);
        }
        if (emptyProperties != null) {
            IGNORE_PROPERTIES.addAll(emptyProperties);
        }
    }

    public static void copyProperties(Object source, Object target, List<String> ignoreProperties) {

        ignorePropertiesWrapper(ignoreProperties, getEmptyProperties(source));
        BeanUtils.copyProperties(source, target, IGNORE_PROPERTIES.toArray(new String[]{}));
    }

    private static List<String> getEmptyProperties(Object source) {
        List<String> emptyProperties = new ArrayList<>(10);
        try {
            PropertyDescriptor[] sourcePds = Introspector.getBeanInfo(source.getClass()).getPropertyDescriptors();
            for (PropertyDescriptor sourcePd:sourcePds) {
                Method readMethod = sourcePd.getReadMethod();
                if (readMethod == null) {
                    emptyProperties.add(sourcePd.getName());
                    continue;
                }

                if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                    readMethod.setAccessible(true);
                }
                Object value = readMethod.invoke(source);
                if (value == null) {
                    emptyProperties.add(sourcePd.getName());
                }
            }

            return emptyProperties;
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        Source source = new Source();
        PlainBean bean = new PlainBean();
        bean.setPro1("beanPro2").setPro2("beanPro2");
        source.setPro1(null).setPro2("sPro2").setPro3(bean);
        Target target = new Target().setPro1("haveValue");
        BeanUtil.copyProperties(source, target, null, true);
        System.out.println(source);
        System.out.println(target);
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @NoArgsConstructor
    @ToString
    private static class Source {
        private String pro1;

        private String pro2;

        private PlainBean pro3;
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @NoArgsConstructor
    @ToString
    private static class Target {
        private String pro1;

        private String pro2;

        private PlainBean pro3;

        private String pro4;
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @NoArgsConstructor
    @ToString
    private static class PlainBean {
        private String pro1;

        private String pro2;
    }

}
