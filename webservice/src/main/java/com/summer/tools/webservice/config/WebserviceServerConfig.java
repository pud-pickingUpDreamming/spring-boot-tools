package com.summer.tools.webservice.config;

import com.summer.tools.webservice.interceptors.WebserviceServerInterceptor;
import com.summer.tools.webservice.service.IHelloService;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.annotation.Resource;
import javax.xml.ws.Endpoint;

/**
 * webservice服务端配置
 * 暴露soup接口端点给外部调用
 */
@Configuration
public class WebserviceServerConfig {
    @Resource
    private Bus bus;
    @Resource
    private IHelloService iHelloService;
    @Resource
    private WebserviceServerInterceptor serverInterceptor;

    @Bean
    public Endpoint helloEndpoint(){
        EndpointImpl endpoint = new EndpointImpl(bus, iHelloService);
        endpoint.getInInterceptors().add(serverInterceptor);
        endpoint.publish("/IHelloService");
        return endpoint;
    }
}