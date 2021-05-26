package com.summer.tools.common.services.impl;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.*;
import com.summer.tools.common.enums.ResponseCodeEnum;
import com.summer.tools.common.exceptions.BusinessException;
import com.summer.tools.common.properties.CloudStorageProperties;
import com.summer.tools.common.services.ICloudStorageService;
import com.summer.tools.common.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.*;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * @author john
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "common.cloud-storage.type", havingValue = "azure")
public class AzureServiceImpl implements ICloudStorageService {

    @Resource
    private CloudStorageProperties properties;

    private static CloudBlobContainer container = null;

    private static final int DEFAULT_BLOCK_SIZE = 1024*1024;

    @PostConstruct
    public void initAzure() {
        try {
            // 获得StorageAccount对象
            String format = "DefaultEndpointsProtocol={0};AccountName={1};AccountKey={2};EndpointSuffix={3}";
            CloudStorageAccount storageAccount = CloudStorageAccount
                    .parse(MessageFormat.format(format, properties.getProtocol(), properties.getAccountName(),
                            properties.getAccountKey(), properties.getEndPoint()));
            // 由StorageAccount对象创建BlobClient
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
            // 根据传入的containerName, 获得container实例
            container = blobClient.getContainerReference(properties.getContainerName());
        } catch (URISyntaxException | InvalidKeyException | StorageException e) {
            log.error("azure云存储服务初始化失败", e);
            throw new BusinessException(ResponseCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public String uploadFile(File file, String dir) {
        try {
            // 构建目标BlockBlob对象
            CloudBlockBlob blob = container.getBlockBlobReference(dir + "/"+ file.getName());
            blob.getProperties().setContentType(FileUtil.getContentType(file));
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
                log.debug("文件 {} 上传成功", file.getName());
                file.deleteOnExit();
            }
            String baseUrl = properties.getBaseUrl();
            return baseUrl !=null ? blob.getUri().toString().replace(baseUrl,"") : blob.getUri().toString();
        } catch (URISyntaxException | StorageException | IOException e) {
            log.error("azure云存储服务上传文件失败:", e);
            throw new BusinessException(ResponseCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public String uploadFile(InputStream inputStream, String path, long size, String contentType) {
        try {
            // 构建目标BlockBlob对象
            CloudBlockBlob blob = container.getBlockBlobReference(path);
            // 设置mime 类型
            blob.getProperties().setContentType(contentType);
            // 将本地文件上传到Azure Container
            blob.upload(inputStream, size);

            String baseUrl = properties.getBaseUrl();
            return baseUrl !=null ? blob.getUri().toString().replace(baseUrl,"") : blob.getUri().toString();
        } catch (URISyntaxException | StorageException | IOException e) {
            log.error("azure云存储服务上传文件失败:", e);
            throw new BusinessException(ResponseCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public String uploadFile(File file, String dir, int blockSize) {

        blockSize = blockSize > 0 ? blockSize : DEFAULT_BLOCK_SIZE;
        byte[] bufferBytes = new byte[blockSize];
        try {
            String path = StringUtils.isBlank(dir) ? file.getName() : dir + "/" + file.getName();
            FileInputStream inputStream = new FileInputStream(file);

            int blockCount = (int) (file.length() / blockSize) + 1;
            log.info("Total block count：" + blockCount + ", Total size: " + file.length());
            int currentBlockSize;

            // 构建目标BlockBlob对象
            CloudBlockBlob blockBlob = container.getBlockBlobReference(path);
            ArrayList<BlockEntry> blockList = new ArrayList<>();
            for (int i = 0; i < blockCount; i++) {
                String blockId = String.format("%08d", i);
                currentBlockSize = blockSize;
                // 最后一个块处理
                if (i == blockCount - 1) {
                    currentBlockSize = (int) (file.length() - blockSize * i);
                    bufferBytes = new byte[currentBlockSize];
                }
                // 没有数据跳过
                int size = inputStream.read(bufferBytes, 0, currentBlockSize);
                if (size <= 0) {
                    continue;
                }
                // 把数据放到数据块里
                blockBlob.uploadBlock(blockId, getRandomDataStream(blockSize), blockSize, null, null, null);
                blockList.add(new BlockEntry(blockId, BlockSearchMode.LATEST));
                log.debug("Submitted block index：" + i + ", BlockIndex:" + blockId);
            }
            // 批量提交
            blockBlob.commitBlockList(blockList);
            return blockBlob.getUri().toString();
        } catch (URISyntaxException | StorageException | IOException e) {
            log.error("azure云存储服务上传大文件失败:", e);
            throw new BusinessException(ResponseCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public void download(String path, String baseDir) {
        // 检查文件最终目录
        String fullPath = baseDir.concat(path).trim();
        String fullDir = fullPath.substring(0, fullPath.lastIndexOf("/"));
        File file = new File(fullDir);
        if (!file.exists() || !file.isDirectory()) {
            if(!file.mkdirs()) {
                return;
            }
        }

        try {
            // 传入要blob的path
            CloudBlockBlob blob = container.getBlockBlobReference(path);
            // 传入目标path
            blob.downloadToFile(baseDir);
        } catch (URISyntaxException | StorageException | IOException e) {
            log.error("azure云存储服务下载文件失败:", e);
            throw new BusinessException(ResponseCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public void download(String path, OutputStream outStream) {
        try {
            // 传入要blob的path
            CloudBlockBlob blob = container.getBlockBlobReference(path);
            // 传入目标path
            blob.download(outStream);
        } catch (URISyntaxException | StorageException e) {
            log.error("azure云存储服务下载文件失败:", e);
            throw new BusinessException(ResponseCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public List<String> listBlobs(String prefix) {
        List<String> blobs = new ArrayList<>(16);
        Iterable<ListBlobItem> blobItems = container.listBlobs(prefix, true);
        for (ListBlobItem blobItem : blobItems) {
            blobs.add(blobItem.getUri().toString());
        }
        return blobs;
    }

    private static ByteArrayInputStream getRandomDataStream(int length) {
        final Random randGenerator = new Random();
        final byte[] buff = new byte[length];
        randGenerator.nextBytes(buff);
        return new ByteArrayInputStream(buff);
    }
}