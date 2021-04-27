package com.summer.tools.webservice.interceptors;

import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;
import java.util.List;

@Slf4j
@Component
public class WebserviceClientInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

    // 发送前处理
    public WebserviceClientInterceptor() {
        super(Phase.PREPARE_SEND);
    }

    @Override
    public void handleMessage(SoapMessage soap) throws Fault {
        // 在soap消息中添加认证头信息
        List<Header> headers = soap.getHeaders();
        Document doc = DOMUtils.createDocument();
        Element auth = doc.createElement(HEADER_NAME);
        Element token = doc.createElement(HEADER_TOKEN);
        token.setTextContent("this.token");
        auth.appendChild(token);
        Header header = new SoapHeader(new QName(HEADER_NAME), auth);
        headers.add(header);
    }

    private static final String HEADER_NAME = "authority";
    private static final String HEADER_TOKEN = "token";
}

