package com.summer.tools.common.utils;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.util.Properties;

public class SFtpUtil {

    private static Logger LOGGER = LoggerFactory.getLogger(SFtpUtil.class);

    /**
     * 连接sftp服务器
     */
    private static void connect() {
        if (SFTP_CLIENT != null) {
            return;
        }
        synchronized(SFtpUtil.class) {
            try {
                JSch jsch = new JSch();
                Session sshSession = jsch.getSession(USER, HOST, PORT);
                LOGGER.info("Session created.");
                sshSession.setPassword(PASSWORD);

                Properties sshConfig = new Properties();
                sshConfig.put("StrictHostKeyChecking", "no");
                sshSession.setConfig(sshConfig);
                sshSession.connect();
                LOGGER.info("Session connected");
                Channel channel = sshSession.openChannel("sftp");
                channel.connect();
                LOGGER.info("Channel connected");
                SFTP_CLIENT = (ChannelSftp) channel;

                LOGGER.info("connect sftp server[{}:{}] success", HOST, PORT);
            } catch (JSchException ex) {
                LOGGER.error("connect sftp server[{}:{}] failed", HOST, PORT, ex);
            }
        }
    }

    public static void upload(String serverPath, String clientFile) {
        File file = new File(clientFile);
        upload(serverPath, file);
    }

    public static void upload(String serverPath, File clientFile) {
        connect();
        if (!clientFile.exists()) {
            LOGGER.error("upload file not exists");
        }
        if (serverPathNotExists(serverPath)) {
            LOGGER.error("upload dir not exists");
        }
        String serverFile = serverPath + clientFile.getName();
        try {
            SFTP_CLIENT.put(clientFile.getAbsolutePath(), serverFile);
            LOGGER.info("upload success,serverFile[{}],clientFile[{}] ", serverFile, clientFile.getAbsolutePath());
        } catch (SftpException ex) {
            LOGGER.error("upload failed,serverFile[{}],clientFile[{}] ", serverFile, clientFile.getAbsolutePath(), ex);
        }
    }

    public static void download(String serverFile, String clientPath) {
        File file = new File(clientPath);
        download(serverFile, file);
    }

    public static void download(String serverFile, File clientPath) {
        connect();
        if (serverFileNotExists(serverFile)) {
            LOGGER.error("download file not exists");
        }

        if (!clientPath.exists() && !clientPath.mkdir()) {
            LOGGER.error("download dir not exists");
        }
        String fileName = serverFile.substring(serverFile.lastIndexOf("/") + 1);
        String clientFile = clientPath.getAbsolutePath() + fileName;
        try {
            SFTP_CLIENT.get(serverFile, clientFile);
            LOGGER.info("download success,serverFile[{}],clientFile[{}] ", serverFile, clientFile);
        } catch (SftpException ex) {
            LOGGER.error("download failed,serverFile[{}],clientFile[{}] ", serverFile, clientFile, ex);
        }
    }

    /**
     * 判断sftp服务端路径是否存在,不存在逐个创建,
     * 如果创建成功了也算存在,如果有一个创建失败,那就是真的不存在了
     * @param serverPath sftp服务端路径
     */
    private static boolean serverPathNotExists(String serverPath) {
        String[] paths = serverPath.split("/");
        String dir = null;
        for (String path:paths) {
            dir = dir == null ? path : dir + "/" + path;
            try{
                SFTP_CLIENT.cd(dir);
            }catch(SftpException ex) {
                if(ChannelSftp.SSH_FX_NO_SUCH_FILE == ex.id){
                    try {
                        SFTP_CLIENT.mkdir(dir);
                    } catch (SftpException e) {
                        LOGGER.error("create dir err");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean serverFileNotExists(String serverFile) {
        try {
            SFTP_CLIENT.ls(serverFile);
            return false;
        } catch (SftpException ex) {
            LOGGER.error("download file not exists");
            return true;
        }
    }

    private static ChannelSftp SFTP_CLIENT;
    public static String HOST = "175.24.121.139";
    public static Integer PORT = 22;
    public static String USER = "root";
    public static String PASSWORD = "";
}
