package com.share2renew.util;

import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @program: Share2Renew_BackEnd
 * @description: FastDFSUtil
 * @author: Junxian Cai
 **/
public class FastDFSUtils {

    private static Logger logger = LoggerFactory.getLogger(FastDFSUtils.class);

    /**
     * 初始化客户端
     * ClientGlobal.init 读取配置文件，并初始化对应的属性
     */
    static {
        try {
            String filePath = new ClassPathResource("fdfs_client.conf").getFile().getAbsolutePath();
            ClientGlobal.init(filePath);
        } catch (Exception e) {
            logger.error("Initialize FastDFS Failed", e.getMessage());
        }
    }

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    public static String[] upload(MultipartFile file) {
        String name = file.getOriginalFilename();
        logger.info("File Name: ", name);
        StorageClient storageClient = null;
        String[] uploadResults = null;
        try {
            //获取storage客户端
            storageClient = getStorageClient();
            //上传
            uploadResults = storageClient.upload_file(file.getBytes(), name.substring(name.lastIndexOf(".") + 1),
                    null);
        } catch (Exception e) {
            logger.error("Upload File Failed", e.getMessage());
        }
        if (null == uploadResults) {
            logger.error("Upload File Failed", storageClient.getErrorCode());
        }
        return uploadResults;
    }

    /**
     * 获取文件信息
     *
     * @param groupName
     * @param remoteFileName
     * @return
     */
    public static FileInfo getFileInfo(String groupName, String remoteFileName) {
        StorageClient storageClient = null;
        try {
            storageClient = getStorageClient();
            return storageClient.get_file_info(groupName, remoteFileName);
        } catch (Exception e) {
            logger.error("Failed to get the file information.", e.getMessage());
        }
        return null;
    }

    /**
     * 下载文件
     *
     * @param groupName
     * @param remoteFileName
     * @return
     */
    public static InputStream downFile(String groupName, String remoteFileName) {
        StorageClient storageClient = null;
        try {
            storageClient = getStorageClient();
            byte[] fileByte = storageClient.download_file(groupName, remoteFileName);
            InputStream inputStream = new ByteArrayInputStream(fileByte);
            return inputStream;
        } catch (Exception e) {
            logger.error("File download failed", e.getMessage());
        }
        return null;
    }

    /**
     * 删除文件
     *
     * @param groupName
     * @param remoteFileName
     */
    public static void deleteFile(String groupName, String remoteFileName) {
        StorageClient storageClient = null;
        try {
            storageClient = getStorageClient();
            storageClient.delete_file(groupName, remoteFileName);
        } catch (Exception e) {
            logger.error("File delete failed", e.getMessage());
        }
    }


    /**
     * 生成storage客户端
     *
     * @return
     * @throws IOException
     */
    private static StorageClient getStorageClient() throws IOException {
        TrackerServer trackerServer = getTrackerServer();
        StorageClient storageClient = new StorageClient(trackerServer, null);
        return storageClient;
    }


    /**
     * 生成tracker服务器
     *
     * @return
     * @throws IOException
     */
    private static TrackerServer getTrackerServer() throws IOException {
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getTrackerServer();
        return trackerServer;
    }


    /**
     * 获取文件路径
     *
     * @return
     */
    public static String getTrackerUrl() {
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = null;
        StorageServer storeStorage = null;
        try {
            trackerServer = trackerClient.getTrackerServer();
            storeStorage = trackerClient.getStoreStorage(trackerServer);
        } catch (Exception e) {
            logger.error("Failed to get file's path", e.getMessage());
        }
        return "http://" + storeStorage.getInetSocketAddress().getHostString() + ":8888/";
    }

}
