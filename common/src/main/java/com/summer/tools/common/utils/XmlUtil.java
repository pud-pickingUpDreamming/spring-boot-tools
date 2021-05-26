package com.summer.tools.common.utils;

import com.summer.tools.common.enums.ResponseCodeEnum;
import com.summer.tools.common.exceptions.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

public class XmlUtil {

    private static final Logger logger = LoggerFactory.getLogger(XmlUtil.class);

    public static <T> String toXmlStr(T javaBean, Class<T> clazz) {

        try(StringWriter writer = new StringWriter()) {
            Marshaller marshaller = getMarshaller(clazz);
            marshaller.marshal(javaBean, writer);
            return writer.toString();
        } catch (JAXBException | SecurityException | IOException e) {
            logger.error("对象转xml异常:", e);
            throw new BusinessException(ResponseCodeEnum.SYSTEM_ERROR);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T toJavaBean(String xmlStr, Class<T> clazz) {
        try {
            StringReader reader = new StringReader(xmlStr);
            Unmarshaller unmarshaller = getUnMarshaller(clazz);
            return (T)unmarshaller.unmarshal(reader);

        } catch (JAXBException | SecurityException e) {
            logger.error("xml转对象异常:", e);
            throw new BusinessException(ResponseCodeEnum.SYSTEM_ERROR);
        }
    }

    private static <T> Marshaller getMarshaller(Class<T> c) throws JAXBException{

        JAXBContext context = JAXBContext.newInstance(c);
        Marshaller marshaller = context.createMarshaller();
        //编码格式UTF-8
        marshaller.setProperty(Marshaller.JAXB_ENCODING,"GBK");
        //是否格式化生成的xml串
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        //是否省略xml头信息（<?xml version="1.0" encoding="gb2312" standalone="yes"?>）
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);

        return marshaller;
    }

    private static <T> Unmarshaller getUnMarshaller(Class<T> c) throws JAXBException{
        return JAXBContext.newInstance(c).createUnmarshaller();
    }
}
