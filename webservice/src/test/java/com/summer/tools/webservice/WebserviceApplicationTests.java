package com.summer.tools.webservice;

import com.summer.tools.webservice.interceptors.WebserviceClientInterceptor;
import com.summer.tools.webservice.properties.WebserviceProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest
class WebserviceApplicationTests {

    @Resource
    private WebserviceProperties properties;
    @Resource
    private WebserviceClientInterceptor clientInterceptor;

    @Test
    void webserviceClientTest() {
        try {
            Client client = client();
            // invoke("方法名",参数1,参数2,参数3....);
            Object[] messages = client.invoke("hello", "john");
            log.info("问候接口返回数据:" + messages[0]);

            Object[] replies = client.invoke("work", "meal");
            log.info("回应工作接口返回数据:" + replies[0]);
        } catch (Exception e) {
            log.error("ooPs, 系统在开小差", e);
        }
    }

    private Client client() {
        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(properties.getClient().getUri());
        // 添加客户端拦截器,用于添加授权信息
        client.getOutInterceptors().add(clientInterceptor);

        return client;
    }
}
