package com.summer.tools.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.summer.tools.common.constants.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;

@Slf4j
public abstract class LocationUtil {

    private static Logger LOGGER = LoggerFactory.getLogger(LocationUtil.class);

    public static String TYPE;
    public static String APP_KEY = "Y8125nlttvjMzqrK9n4mVEnNYnmccfoq";
    public static String SERVICE_URL = "http://api.map.baidu.com/location/ip?ak={0}&coor=bd09ll&ip={1}";


    /**
     * 根据ip地址定位用户所在城市,默认用百度服务
     * @param strIP ip字符串
     * @return 城市名称
     */
    public static String getLocationByIP(String strIP) {
        if ("ali".equals(TYPE)) {
            return "//extend 新建阿里定位服务子类";
        } else {
            return BaiDuLocationService.getLocationByIP(strIP);
        }
    }

    static class BaiDuLocationService {
        /**
         * 获取IP所属城市
         */
        public static String getLocationByIP(String strIP) {
            URL url;
            try {
                url = new URL(MessageFormat.format(SERVICE_URL, APP_KEY, strIP));
            } catch (MalformedURLException ex) {
                LOGGER.error("malformed url occurred");
                return null;
            }

            try(InputStream is = url.openConnection().getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

                String line;
                StringBuilder result = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                LOGGER.info("根据ip定位接口返回数据:\n" + result.toString());
                JSONObject jsonRst = JSONObject.parseObject(result.toString());
                if(CommonConstants.SUCCESS.equals(jsonRst.getString("status"))){
                    JSONObject content = JSONObject.parseObject(jsonRst.getString("content"));
                    JSONObject addressDetail = JSONObject.parseObject(content.getString("address_detail"));
                    return addressDetail.getString("city");
                } else {
                    LOGGER.error("错误码:[{}], 错误内容:[{}]", jsonRst.getString("status"), jsonRst.getString("message"));
                }
            } catch (IOException ex) {
                LOGGER.error("请求百度定位服务接口异常:", ex);
            }
            return null;
        }
    }
}