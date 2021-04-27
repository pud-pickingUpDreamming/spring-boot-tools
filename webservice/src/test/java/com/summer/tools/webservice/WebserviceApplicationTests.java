package com.summer.tools.webservice;

import com.summer.tools.webservice.properties.WebserviceClientProperties;
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
    private WebserviceClientProperties clientProperties;

    @Test
    void webserviceClientTest() {
        if (!clientProperties.getEnable()) {
            log.info("webservice服务被禁止调用!");
            return;
        }

        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(clientProperties.getUri());

        // 需要密码的情况需要加上用户名和密码
//         client.getOutInterceptors().add(new CxfInterceptor(USER_NAME,PASS_WORD));

        try {
            // invoke("方法名",参数1,参数2,参数3....);
            Object[] messages = client.invoke("hello", "john");
            Object[] replies = client.invoke("work", "meal");
            log.info("返回数据:" + messages[0]);
            log.info("返回数据:" + replies[0]);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }
}
