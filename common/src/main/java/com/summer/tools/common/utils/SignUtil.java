package com.summer.tools.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 签名工具类
 * @author john
 */
public class SignUtil {

    public static Logger LOGGER = LoggerFactory.getLogger(SignUtil.class);

    /**
     * 对内容用sha签名
     * @param content 待签名内容
     * @return sign
     */
    public static String signWithSha(String content) {
        if (content == null || content.length() == 0) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(content.getBytes(StandardCharsets.UTF_8));

            return HexUtil.toString(messageDigest.digest());
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.error("Sha签名失败", ex);
        }
        return null;
    }

    public static String signWithMd5(String content) {
        if (content == null || content.length() == 0) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(content.getBytes(StandardCharsets.UTF_8));

            return HexUtil.toString(messageDigest.digest());
        } catch (Exception ex) {
            LOGGER.error("Md5签名失败", ex);
        }
        return null;
    }
}
