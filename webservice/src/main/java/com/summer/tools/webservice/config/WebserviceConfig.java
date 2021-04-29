package com.summer.tools.webservice.config;

import com.summer.tools.webservice.interceptors.WebserviceClientInterceptor;
import com.summer.tools.webservice.interceptors.WebserviceServerAuthInterceptor;
import com.summer.tools.webservice.properties.WebserviceProperties;
import com.summer.tools.webservice.service.IHelloService;
import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.ext.logging.LoggingInInterceptor;
import org.apache.cxf.ext.logging.LoggingOutInterceptor;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.annotation.Resource;
import javax.xml.ws.Endpoint;

/**
 * webservice配置
 * 服务端: 暴露soup接口端点给外部调用
 * 客户端: 创建单例Client给内部用
 */
@Configuration
public class WebserviceConfig {
    // server 端依赖服务
    @Resource
    private Bus bus;
    @Resource
    private IHelloService iHelloService;
    @Resource
    private WebserviceServerAuthInterceptor authInterceptor;
    // client 端依赖服务
    @Resource
    private WebserviceProperties properties;
    @Resource
    private WebserviceClientInterceptor clientInterceptor;

    @Bean
    public Endpoint helloEndpoint(){
        EndpointImpl endpoint = new EndpointImpl(bus, iHelloService);
        // 鉴权
        endpoint.getInInterceptors().add(authInterceptor);
        // 日志 rt/features/logging
        endpoint.getInInterceptors().add(new LoggingInInterceptor());
        endpoint.getOutInterceptors().add(new LoggingOutInterceptor());
        endpoint.publish("/IHelloService");
        return endpoint;
    }

    /**
     * 此处在真实项目中是没问题的,而且这种配置是最优的,但是我这个项目是服务端客户端一体的。
     * 服务端没起来,创建客户端肯定会报错的,最后的结果就是服务起不来
     * @return webservice客户端
     */
//    @Bean
    @ConditionalOnProperty(name = "webservices.enable", havingValue = "true")
    public Client client() {
        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(properties.getClient().getUri());
        // 添加客户端拦截器,用于添加授权信息
        client.getOutInterceptors().add(clientInterceptor);

        return client;
    }
}