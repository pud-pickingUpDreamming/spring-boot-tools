package com.summer.tools.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShellUtil {

    private static final Logger logger = LoggerFactory.getLogger(ShellUtil.class);

    public static void exec(String cmd, boolean isWindows) {

        try {
            // cmd 支持多个命令
            String[] commands = { "/bin/sh", "-c", cmd };
            if (isWindows) {
                commands = new String[]{"cmd.exe ", "/c", cmd};
            }
            Process exec = Runtime.getRuntime().exec(commands);
            String line;
            //要执行的程序可能输出较多，而运行窗口的缓冲区有限，会造成waitFor阻塞，利用这种方式可以在waitFor命令之前读掉输出缓冲区的内容
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
            while ((line = bufferedReader.readLine())!=null) {
                logger.info("result=======" + line);
            }
            bufferedReader.close();
            //等待脚本执行完成
            exec.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
