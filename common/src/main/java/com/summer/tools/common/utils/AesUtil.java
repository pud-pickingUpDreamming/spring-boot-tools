package com.summer.tools.common.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.Security;

/**
 * AES加解密，默认256位加密,支持key是16位和32位
 * @author john.wang
 *
 */
public class AesUtil {
    public static Logger LOGGER = LoggerFactory.getLogger(AesUtil.class);

    public static final String IV = "0000000000000000";

    public static String KEY = "boss567890encode";

    public static Charset ENCODING = StandardCharsets.UTF_8;

    public static boolean ENABLE = true;

    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    public static String encrypt(String content) {
        return encrypt(content, KEY);
    }

    public static String decrypt(String content) {
        return decrypt(content, KEY);
    }

    /**
     * 输出加密字符串
     * @param content 原始内容
     * @param key 加密key
     * @return 加密后的字符串
     */
    public static String encrypt(String content, String key) {
        if (!ENABLE) {return content;}

        key = StringUtils.isBlank(key) ? KEY : key;
        byte[] encodeData = encode(content, key);
        return Base64.encodeBase64String(encodeData);
    }

    /**
     * 输出解密字符串
     * @param content 加密内容
     * @param key 加密key
     * @return 解密后字符串
     */
    public static String decrypt(String content, String key) {
        if (!ENABLE) {return content;}

        key = StringUtils.isBlank(key) ? KEY : key;
        byte[] contentByteArr = Base64.decodeBase64(content);
        byte[] decodeData = decode(contentByteArr, key);
        return decodeData == null ? null : new String(decodeData, ENCODING);
    }

    /**
     * 数据加密
     * @param content 原内容
     * @param key 加密key
     * @return 加密后数据
     */
    private static byte[] encode(String content, String key) {
        try {
            Key sKey = getSecretKey(key);
            IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes(ENCODING));
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            cipher.init(Cipher.ENCRYPT_MODE, sKey, ivSpec);
            return cipher.doFinal(content.getBytes(ENCODING));
        } catch (Exception ex) {
            LOGGER.error("数据加密:", ex);
        }
        return null;
    }

    /**
     * 数据解密
     * @param content base64解码后的内容
     * @param key 加密key
     * @return 解密后数据
     */
    private static byte[] decode(byte[] content, String key) {
        try {
            Key sKey = getSecretKey(key);
            IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes(ENCODING));
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            cipher.init(Cipher.DECRYPT_MODE, sKey, ivSpec);
            return cipher.doFinal(content);
        } catch (Exception ex) {
            LOGGER.error("数据解密失败:", ex);
        }
        return null;
    }

    private static Key getSecretKey(String key) {
        if ( key.length() == 32) {
            return new SecretKeySpec(HexUtil.toByteArr(key), "AES");
        } else {
            return new SecretKeySpec(key.getBytes(), "AES");
        }
    }
}
 

