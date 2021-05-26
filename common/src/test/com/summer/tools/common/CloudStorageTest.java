package com.summer.tools.common;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.azure.storage.blob.ListBlobItem;
import com.summer.tools.common.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.text.MessageFormat;
import java.util.Objects;

@Slf4j
public class CloudStorageTest {

    @Test
    public void azureTest() {
        File dir = new File("D:\\data\\flowable");
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            String prefix = dir.getName();
            test(file,prefix);
        }
    }

    private void test(File file,String prefix){

        try {
            System.out.println(FileUtil.getContentType(file));
            // 获得StorageAccount对象
            String format = "DefaultEndpointsProtocol={0};AccountName={1};AccountKey={2};EndpointSuffix={3}";
            CloudStorageAccount storageAccount =
                    CloudStorageAccount.parse(MessageFormat.format(format, "https", "johnwang", "WNY2QHkO402bYT119D6JFyfGNdtRfmgVpfafPkYX7oAn+0pz8cfMIzoEpDio/LBYxJgAnnXY7N0fXJ4x6fEGsw==","core.windows.net"));
            // 由StorageAccount对象创建BlobClient
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
            // 根据传入的containerName, 获得container实例
            CloudBlobContainer container = blobClient.getContainerReference("test");
            // 构建目标BlockBlob对象
            CloudBlockBlob blob = container.getBlockBlobReference(prefix + "/" + file.getName());
            String contentType = new MimetypesFileTypeMap().getContentType(file);
            blob.getProperties().setContentType(contentType);
            // 将本地文件上传到Azure Container
            blob.uploadFromFile(file.getPath());
            // 获得获得属性
            blob.downloadAttributes();
            // 获得上传后的文件大小
            long blobSize = blob.getProperties().getLength();
            // 获得本地文件大小
            long localSize = file.length();
            // 校验
            if (blobSize != localSize) {
                log.warn("文件 {} 上传失败", file.getName());
                // 删除blob
                blob.deleteIfExists();
            } else {
                log.info("文件 {} 上传成功", file.getName());
            }
            System.out.println(blob.getUri().toString());
            Iterable<ListBlobItem> blobItems = container.listBlobs("", true);
            for (ListBlobItem blobItem : blobItems) {
                log.info(blobItem.getUri().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
