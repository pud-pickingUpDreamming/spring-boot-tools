package com.summer.tools.common.utils;


import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class IPUtil {


    private static Logger LOGGER = LoggerFactory.getLogger(IPUtil.class);

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
     * 
     * 获取IP+IP所属地址
     */
    public static String getIpBelongAddress(HttpServletRequest request){

        String ip = getIpAddr(request);
        String belongIp = getIPbelongAddress(ip);

        return ip + belongIp;
    }
    /**
     * 
     * 获取IP所属地址
     */
    public static String getIPbelongAddress(String ip){

        String ipAddress = "[]";
        try{
            //淘宝提供的服务地址
//            String context = call("http://ip.taobao.com/service/getIpInfo.php?ip="+ip);
//            JSONObject fromObject = JSONObject.fromObject(context);
//            String code = fromObject.getString("code");
//            if(code.equals("0")){
//                JSONObject jsonObject = fromObject.getJSONObject("data");
//                ipAddress =  "["+jsonObject.get("country")+"/" +jsonObject.get("city")+"]";
//            }
        }catch(Exception e){
        LOGGER.error("获取IP所属地址出错",e);
            e.printStackTrace();
        }
        return ipAddress;
    }
    /**
     * 获取Ip所属地址
     */
    public static String call( String urlStr ){

        try {

            URL url = new URL(urlStr);
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();

            httpCon.setConnectTimeout(3000);
            httpCon.setDoInput(true);
            httpCon.setRequestMethod("GET");

            int code = httpCon.getResponseCode();

            if(code == 200){
                return FileUtil.streamToSting(httpCon.getInputStream(), null);
            }
        } catch (Exception e) {
            LOGGER.error("获取IP所属地址出错",e);
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {


        String context = call("https://blog.csdn.net/yo_yolv/article/details/51646482");

        Map<String, Object> fromObject = JsonUtil.parseMap(context, Object.class);
        Map<String, Object> data = JsonUtil.parseMap(JsonUtil.stringify(fromObject.get("data")), Object.class);;
        System.out.println(fromObject);
        System.err.println(data.get("city"));
    }
}