package com.summer.tools.common.services;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * @author join
 */
public interface ICloudStorageService {

    /**
     * 上传文件
     * @param file 待上传文件
     * @param dir 上传目录(相对容器目录)
     * @return 云端地址
     */
    String uploadFile(File file, String dir);

    /**
     * 上传文件
     * @param inputStream 文件输入流
     * @param path 云端文件路径(相对容器目录)
     * @param size 文件大小
     * @param contentType 文件类型
     * @return 云端地址
     */
    String uploadFile(InputStream inputStream, String path, long size, String contentType);

    /**
     * 上传大文件 -- 把文件拆分成多个数据块存储
     * @param file 上传文件
     * @param dir 上传目录(相对容器目录)
     * @param blockSize 存储块大小
     * @return 云端地址
     */
    String uploadFile(File file, String dir, int blockSize);

    /**
     * 下载文件
     * @param path 云端文件路径(相对容器目录)
     * @param baseDir 下载目标根目录
     */
    void download(String path, String baseDir);

    /**
     * 下载文件
     * @param path 云端文件路径(相对容器目录)
     * @param outStream 输出流(浏览器下载)
     */
    void download(String path, OutputStream outStream);

    /**
     * 查看云端文件列表
     * @param dir 文件目录(相对容器目录)
     * @return 云端文件列表
     */
    List<String> listBlobs(String dir);
}
