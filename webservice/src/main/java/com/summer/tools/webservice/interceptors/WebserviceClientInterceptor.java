package com.summer.tools.webservice.interceptors;

import com.summer.tools.common.utils.SignUtil;
import com.summer.tools.webservice.properties.WebserviceProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.annotation.Resource;
import javax.xml.namespace.QName;
import java.util.List;

@Slf4j
@Component
public class WebserviceClientInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

    @Resource
    private WebserviceProperties properties;

    // 发送前处理
    public WebserviceClientInterceptor() {
        super(Phase.PREPARE_SEND);
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        // 根据约定的用户名密码生成token
        String token = SignUtil.signWithMd5(properties.getUsername() + properties.getPasswd());
        List<Header> headers = message.getHeaders();
        // 生成授权dom元素
        Document document = DOMUtils.createDocument();
        Element rootEle = document.createElement(WebserviceProperties.HEADER_AUTH);
        Element tokenEle = document.createElement(WebserviceProperties.HEADER_TOKEN);
        // 设置token值
        tokenEle.setTextContent(token);
        rootEle.appendChild(tokenEle);
        // 把生成的dom文档放到SoapMessage header里
        headers.add(new Header(new QName(WebserviceProperties.HEADER_AUTH), rootEle));
    }
}

