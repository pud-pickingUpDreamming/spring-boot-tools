package com.summer.tools.common.utils;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author john.wang
 */
public class RsaUtil {
    /**
     * 获取公钥
     * @param filename 公钥文件
     * @return 公钥
     * @throws Exception 读文件、解密相关异常
     */
    public PublicKey getPublicKey(String filename) throws Exception {
        DataInputStream dis = dataInputStream(filename);
        if (dis == null) {
            return null;
        }
        byte[] keyBytes = new byte[dis.available()];
        dis.readFully(keyBytes);
        dis.close();
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    /**
     * 获取私钥
     * @param filename 私钥文件
     * @return 私钥
     * @throws Exception 读文件、解密相关异常
     */
    public PrivateKey getPrivateKey(String filename) throws Exception {
        DataInputStream dis = dataInputStream(filename);
        if (dis == null) {
            return null;
        }
        byte[] keyBytes = new byte[dis.available()];
        dis.readFully(keyBytes);
        dis.close();
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    private DataInputStream dataInputStream(String filename) {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(filename);
        if (resourceAsStream == null) {
            return null;
        }
        return new DataInputStream(resourceAsStream);
    }

    /**
     * 生存rsa公钥和密钥
     * @param publicKeyFilename 公钥文件名
     * @param privateKeyFilename 私钥文件名
     * @param password 加密关键字
     * @throws IOException io 异常
     * @throws NoSuchAlgorithmException 算法不存在异常
     */
    public static void generateKey(String publicKeyFilename, String privateKeyFilename, String password) throws IOException, NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom(password.getBytes());
        keyPairGenerator.initialize(1024, secureRandom);
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
        FileOutputStream fos = new FileOutputStream(publicKeyFilename);
        fos.write(publicKeyBytes);
        fos.close();
        byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
        fos = new FileOutputStream(privateKeyFilename);
        fos.write(privateKeyBytes);
        fos.close();
    }

    public static void main(String[] args) {
        String publicKeyFilename = "";
    }
}

