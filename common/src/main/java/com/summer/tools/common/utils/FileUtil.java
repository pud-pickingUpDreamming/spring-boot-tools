package com.summer.tools.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {

    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public static String readFileToString(File file, String encoding) {
        try(InputStream stream = new FileInputStream(file)) {
            return streamToSting(stream, encoding);
        } catch (IOException ex) {
            logger.error("读取文件失败:", ex);
            return null;
        }
    }

    public static String streamToSting(InputStream inputStream, String encoding) {

        try (ByteArrayOutputStream outputStream=new ByteArrayOutputStream()) {
            byte[] buffer=new byte[1024];
            int len;

            while((len=inputStream.read(buffer))!=-1){
                outputStream.write(buffer, 0, len);
            }

            return new String(buffer, encoding);
        } catch (IOException ex) {
            logger.error("数据流转字符串失败:", ex);
            return null;
        }
    }

    public static String getContentType(File file) {

        //利用nio提供的类判断文件ContentType
        Path path = Paths.get(file.getPath());
        String contentType = null;
        try {
            contentType = Files.probeContentType(path);
        } catch (IOException e) {
            logger.error("Read File ContentType Error");
        }
        //若失败则调用另一个方法进行判断
        if (contentType == null) {
            contentType = new MimetypesFileTypeMap().getContentType(file);
        }
        return contentType;
    }
}
