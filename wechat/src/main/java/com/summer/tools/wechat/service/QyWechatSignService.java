package com.summer.tools.wechat.service;

import com.alibaba.fastjson.JSONObject;
import com.summer.tools.cache.service.RedisService;
import com.summer.tools.wechat.constant.Constants;
import com.summer.tools.wechat.properties.WechatProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 获取企业微信签名
 */
@Service
@Slf4j
public class QyWechatSignService {
    @Resource
    private WechatProperties properties;
    @Resource
    private RedisService<String> redisService;

    // 本地缓存,避免重复调微信被屏蔽

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

        configMap.put("corpid", properties.getCorpId());
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
        // 取缓存
        String cache = this.redisService.get(Constants.QY_WECHAT_ACCESS_TOKEN_KEY);
        if (StringUtils.isNotBlank(cache)) {
            return cache;
        }

        String accessTokenUrl = MessageFormat.format(ACCESS_TOKEN_URL_FORMAT, properties.getCorpId(), properties.getCorpSecret());

        String backData = template.getForObject(accessTokenUrl, String.class);

        String accessToken = JSONObject.parseObject(backData).getString("access_token");
        long expireTime = JSONObject.parseObject(backData).getLong("expires_in");

        // 缓存时间比有效期短10秒(对取到缓存到调用微信接口这个时间差做容错),防止取到缓存后调用微信接口时token失效
        this.redisService.set(Constants.QY_WECHAT_ACCESS_TOKEN_KEY, accessToken, expireTime - 10, TimeUnit.SECONDS);


        return accessToken;
    }

    public String getJSApiTicket() {
        // 取缓存
        String cache = this.redisService.get(Constants.QY_WECHAT_API_TICKET_KEY);
        if (StringUtils.isNotBlank(cache)) {
            return cache;
        }

        //获取token
        String accessToken = getAccessToken();

        String jsApiTicketUrl = MessageFormat.format(JS_API_TICKET_URL_FORMAT, accessToken);

        String backData = template.getForObject(jsApiTicketUrl, String.class);

        String apiTicket = JSONObject.parseObject(backData).getString("ticket");
        long expireTime = JSONObject.parseObject(backData).getLong("expires_in");

        // 缓存时间比有效期短10秒(对取到缓存到调用微信接口这个时间差做容错),防止取到缓存后调用微信接口时ticket失效
        this.redisService.set(Constants.QY_WECHAT_API_TICKET_KEY, apiTicket, expireTime - 10, TimeUnit.SECONDS);

        return apiTicket;
    }
}