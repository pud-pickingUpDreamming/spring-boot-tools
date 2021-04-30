package com.summer.tools.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author john.wang
 */
public class RsaUtil {

    private static final Logger logger = LoggerFactory.getLogger(RsaUtil.class);
    /**
     * 获取公钥
     * @param filename 公钥文件
     * @return 公钥
     */
    public static PublicKey getPublicKey(String filename) {
        try(DataInputStream dis = dataInputStream(filename)) {
            if (dis == null) {
                return null;
            }
            byte[] keyBytes = new byte[dis.available()];
            dis.readFully(keyBytes);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(spec);
        } catch (IOException e) {
            logger.error("公钥文件读取失败:", e);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.error("生成公钥对象失败", e);
        }
        return null;
    }

    /**
     * 获取私钥
     * @param filename 私钥文件
     * @return 私钥
     */
    public static PrivateKey getPrivateKey(String filename) {
        try(DataInputStream dis = dataInputStream(filename)) {
            if (dis == null) {
                return null;
            }
            byte[] keyBytes = new byte[dis.available()];
            dis.readFully(keyBytes);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(spec);
        } catch (IOException e) {
            logger.error("私钥文件读取失败:", e);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.error("生成私钥对象失败", e);
        }
        return null;
    }

    private static DataInputStream dataInputStream(String filename) {
        try {
            InputStream in = new FileInputStream(new File(filename));
            return new DataInputStream(in);
        } catch (FileNotFoundException e) {
            logger.error("文件不存在", e);
            return null;
        }
    }

    /**
     * 生存rsa公钥和密钥
     * @param publicKeyFilename 公钥文件名
     * @param privateKeyFilename 私钥文件名
     * @param password 加密关键字
     */
    public static void generateKey(String publicKeyFilename, String privateKeyFilename, String password) {
        try {
            String dirPath = publicKeyFilename.substring(0, publicKeyFilename.lastIndexOf(File.separatorChar));
            File dir = new File(dirPath);
            if (!dir.exists() && !dir.mkdirs()) {
                throw new RuntimeException("系统异常");
            }
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            SecureRandom secureRandom = new SecureRandom(password.getBytes());
            keyPairGenerator.initialize(1024, secureRandom);
            KeyPair keyPair = keyPairGenerator.genKeyPair();

            byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
            byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();

            try(FileOutputStream pubOs = new FileOutputStream(publicKeyFilename);
                FileOutputStream priOs = new FileOutputStream(privateKeyFilename)) {
                pubOs.write(publicKeyBytes);
                priOs.write(privateKeyBytes);
            } catch (IOException e) {
                logger.error("生成公私钥文件出错", e);
            }

        } catch (NoSuchAlgorithmException e) {
            logger.error("生成rsa公私钥出错:", e);
        }
    }
}

