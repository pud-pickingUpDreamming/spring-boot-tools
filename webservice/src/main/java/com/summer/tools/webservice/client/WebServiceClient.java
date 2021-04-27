package com.summer.tools.webservice.client;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

/**
 * webservice客户端
 */
public class WebServiceClient {

    public static void main(String[] args){

        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient("http://localhost:80/services/IHelloService?wsdl");


        // 需要密码的情况需要加上用户名和密码
//         client.getOutInterceptors().add(new CxfInterceptor(USER_NAME,PASS_WORD));

        try {
            // invoke("方法名",参数1,参数2,参数3....);
            Object[] objects = client.invoke("hello", "wyj");
            Object[] objects1 = client.invoke("hello1", "wyj");
            System.out.println("返回数据:" + objects[0]);
            System.out.println("返回数据:" + objects1[0]);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }
}
