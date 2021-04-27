package com.summer.tools.webservice.service.impl;

import com.summer.tools.webservice.service.IHelloService;
import org.apache.cxf.interceptor.InInterceptors;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;


@Service
@WebService(
        targetNamespace = "http://service.webservice.tools.summer.com",
        endpointInterface = "com.summer.tools.webservice.service.IHelloService",
        serviceName = "IHelloService"
)
@BindingType(value = SOAPBinding.SOAP12HTTP_BINDING)
public class HelloServiceImpl implements IHelloService {
    @Override
    public String hello(String message) {
        return "Hello " + message;
    }

    @Override
    public String work(String content) {
        return "Got it, I will do " + content + " soon!";
    }
}