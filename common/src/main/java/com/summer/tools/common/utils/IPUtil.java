package com.summer.tools.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;

public class IPUtil {

    private static Logger LOGGER = LoggerFactory.getLogger(IPUtil.class);

    /**
     * 百度应用访问key,申请并激活百度开发者账号,创建个应用就可以获得
     */
    private static final String AK = "Y8125nlttvjMzqrK9n4mVEnNYnmccfoq";
    /**
     * 百度定位服务接口,开发文档 https://lbsyun.baidu.com/index.php?title=webapi/ip-api
     */
    private static final String LOCATION_SERVICE_URL = "http://api.map.baidu.com/location/ip?ak={0}&coor=bd09ll&ip={1}";

    /**
     * 获取ip地址
     */
    public static String getIpAddr(HttpServletRequest request){

        String ip = request.getHeader("x-forwarded-for");

        if(ip == null || ip.length() ==0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() ==0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() ==0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取IP所属地址
     */
    public static String getLocationByIP(String strIP) {
        try {
            URL url = new URL(MessageFormat.format(LOCATION_SERVICE_URL, AK, strIP));
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(), StandardCharsets.UTF_8));
            String line;
            StringBuilder result = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
            String ipAddr = result.toString();
            JSONObject obj1= JSONObject.parseObject(ipAddr);
            if("0".equals(obj1.get("status").toString())){
                JSONObject obj2= JSONObject.parseObject(obj1.get("content").toString());
                JSONObject obj3= JSONObject.parseObject(obj2.get("address_detail").toString());
                return obj3.get("city").toString();
            }else{
                return "读取失败";
            }
        } catch (IOException e) {
            return "读取失败";
        }
    }

    public static void main(String[] args) {
        System.out.println(getLocationByIP("1.15.74.240"));
    }
}