package com.summer.tools.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

public class FileUtil {

    public static String readFileToString(File file, String encoding) {

        try (FileInputStream fileInputStream = new FileInputStream(file); ByteArrayOutputStream outputStream=new ByteArrayOutputStream()) {
            byte[] buffer=new byte[1024];
            int len;

            while((len=fileInputStream.read(buffer))!=-1){
                outputStream.write(buffer, 0, len);
            }

            return new String(buffer, encoding);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
