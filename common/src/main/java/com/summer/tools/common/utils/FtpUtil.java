package com.summer.tools.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.net.TelnetInputStream;
import sun.net.TelnetOutputStream;
import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpProtocolException;

import java.io.*;
import java.net.InetSocketAddress;

public class FtpUtil {

    private static Logger LOGGER = LoggerFactory.getLogger(FtpUtil.class);

    /**
     * 连接ftp服务器
     */
    private static void connect() {
        if (FTP_CLIENT != null) {
            return;
        }
        synchronized (FtpUtil.class) {
            try {
                FTP_CLIENT = FtpClient.create(new InetSocketAddress(HOST, PORT))
                        .login(USER,null, PASSWORD)
                        .setBinaryType();
                LOGGER.info("connect ftp server[{}:{}] success", HOST, PORT);
            } catch (FtpProtocolException | IOException ex) {
                LOGGER.error("connect ftp server failed", ex);
            }
        }
    }

    public static void upload(String serverPath, File clientFile) {

        connect();

        String serverFile = serverPath + clientFile.getName();
        try(TelnetOutputStream os = (TelnetOutputStream) FTP_CLIENT.putFileStream(serverFile, true);
            FileInputStream is = new FileInputStream(clientFile)) {
            byte[] bytes = new byte[1024];
            int c;
            while ((c = is.read(bytes)) != -1) {
                os.write(bytes, 0, c);
            }
            LOGGER.info("file[{}] upload success", clientFile.getName());
        } catch (FtpProtocolException | IOException ex) {
            LOGGER.error("file[{}] upload failed", clientFile.getName(), ex);
        }
    }

    public static void download(String serverFile, File clientPath) {

        connect();

        String fileName = serverFile.substring(serverFile.lastIndexOf(File.pathSeparator));
        File clientFile = new File(clientPath.getAbsolutePath() + File.pathSeparator + fileName);
        try(TelnetInputStream is = (TelnetInputStream) FTP_CLIENT.getFileStream(serverFile);
            FileOutputStream os = new FileOutputStream(clientFile)) {

            byte[] bytes = new byte[1024];
            int c;
            while ((c = is.read(bytes)) != -1) {
                os.write(bytes, 0, c);
            }
            LOGGER.info("file[{}] download success", serverFile);
        } catch (FtpProtocolException | IOException ex) {
            LOGGER.error("file[{}] download failed", serverFile);
        }
    }

    private static FtpClient FTP_CLIENT;
    public static String HOST = "175.24.121.139";
    public static Integer PORT = 22;
    public static String USER = "root";
    public static String PASSWORD = "johnWANG123456";
}
