package com.summer.tools.webservice.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(targetNamespace = "http://service.webservice.tools.summer.com", name = "IHelloService")
public interface IHelloService {
    @WebMethod
    @WebResult
    String hello(@WebParam(name = "message") String message);

    @WebMethod
    @WebResult
    String work(@WebParam(name = "content") String content);
}
