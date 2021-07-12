package com.summer.tools.cache.service;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.UUID;
import java.util.Map;
import java.util.HashMap;
import java.util.Formatter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 获取企业微信签名
 */
@Service
@Slf4j
public class Sign {
    // 本地缓存,避免重复调微信被屏蔽
    private static long EXPIRE_POINT = 0;
    private static String ACCESS_TOKEN = null;
    private static String API_TICKET = null;

    private static final String CORP_ID = "";
    private static final String CORP_SECRET = "";
    private static final String SIGN_STR_FORMAT = "jsapi_ticket={0}&noncestr={1}&timestamp={2}&url={3}";
    private static final String ACCESS_TOKEN_URL_FORMAT = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid={0}&corpsecret={1}"; // Get
    private static final String JS_API_TICKET_URL_FORMAT = "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token={0}"; // Get

    @Resource
    private RestTemplate template;

    public Map<String, String> sign (String url) {

        String jsApiTicket = getJSApiTicket();

        Map<String, String> configMap = sign(jsApiTicket, url);

        log.info("签名结果:[{}]",  configMap);

        return configMap;
    }

    public Map<String, String> sign(String jsApiTicket, String url) {
        Map<String, String> configMap = new HashMap<>();

        String nonceStr = UUID.randomUUID().toString().replace("-", "");
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);

        String signStr = MessageFormat.format(SIGN_STR_FORMAT, jsApiTicket, nonceStr, timestamp, url);

        log.info("签名字符串:[{}]", signStr);

        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(signStr.getBytes(StandardCharsets.UTF_8));
            String signature = byteToHex(crypt.digest());
            configMap.put("signature", signature);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        configMap.put("corpid", CORP_ID);
        configMap.put("url", url);
        configMap.put("jsapi_ticket", jsApiTicket);
        configMap.put("noncestr", nonceStr);
        configMap.put("timestamp", timestamp);
        return configMap;

    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;

    }

    public String getAccessToken() {
        // 本地缓存AccessToken 避免受到频率拦截, 过期前10s 更新token
        if (ACCESS_TOKEN != null && System.currentTimeMillis() < EXPIRE_POINT - 10000) {
            return ACCESS_TOKEN;
        }

        String accessTokenUrl = MessageFormat.format(ACCESS_TOKEN_URL_FORMAT, CORP_ID, CORP_SECRET);

        String backData = template.getForObject(accessTokenUrl, String.class);

        ACCESS_TOKEN = JSONObject.parseObject(backData).getString("access_token");
        long expireTime = JSONObject.parseObject(backData).getLong("expires_in");
        EXPIRE_POINT = System.currentTimeMillis() + 1000*expireTime;

        return ACCESS_TOKEN;
    }

    public String getJSApiTicket() {
        // 本地缓存AccessToken 避免受到频率拦截, 过期前20s 更新token
        if (API_TICKET != null && System.currentTimeMillis() < EXPIRE_POINT - 20000) {
            return API_TICKET;
        }

        //获取token
        String accessToken = getAccessToken();

        String jsApiTicketUrl = MessageFormat.format(JS_API_TICKET_URL_FORMAT, accessToken);

        String backData = template.getForObject(jsApiTicketUrl, String.class);

        API_TICKET = JSONObject.parseObject(backData).getString("ticket");
        return API_TICKET;
    }
}