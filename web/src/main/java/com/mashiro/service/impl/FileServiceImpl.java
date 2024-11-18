package com.mashiro.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.mashiro.service.FileService;
import com.mashiro.utils.minio.MinioProperties;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Resource
    private MinioClient minioClient;

    @Resource
    private MinioProperties minioProperties;

    // 通用接口使用的固定目录
    private static final String COMMON_DIRECTORY = "common/";

    @Override
    public String upload(MultipartFile file) throws IOException {
        String filename = generateFileName(file.getOriginalFilename());
        String objectName = COMMON_DIRECTORY + filename;
        putObjectToMinio(file.getInputStream(), objectName, file.getContentType());
        return generateFileUrl(objectName);
    }

    @Override
    public String uploadFromUrl(String fileUrl) {
        int userId = StpUtil.getLoginIdAsInt();
        String filename = generateUrlFileName();
        // 用户接口使用用户ID作为目录前缀
        String objectName = "user/" + userId + "/" + filename;

        try (InputStream inputStream = fetchImageFromUrl(fileUrl)) {
            putObjectToMinio(inputStream, objectName, "image/png");
        } catch (Exception e) {
            throw new RuntimeException("无法从URL上传文件", e);
        }

        return generateFileUrl(objectName);
    }

    @Override
    public void deleteImage(String objectName) {
        try {
            minioClient.removeObject(
                RemoveObjectArgs.builder()
                    .bucket(minioProperties.getBucketName())
                    .object(objectName)
                    .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("删除图片时出错", e);
        }
    }

    @Override
    public InputStream getFile(String objectName) {
        try {
            return minioClient.getObject(
                GetObjectArgs.builder()
                    .bucket(minioProperties.getBucketName())
                    .object(objectName)
                    .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("获取文件时出错", e);
        }
    }

    /**
     * 从指定的URL获取图片的输入流
     *
     * @param fileUrl 图片的URL
     * @return 图片的输入流
     * @throws IOException 如果无法读取URL内容
     */
    private InputStream fetchImageFromUrl(String fileUrl) throws IOException {
        URL url = new URL(fileUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoInput(true);
        connection.connect();
        return connection.getInputStream();
    }

    /**
     * 将输入流中的数据上传到MinIO
     *
     * @param stream      文件的输入流
     * @param objectName  MinIO中的对象名
     * @param contentType 文件的内容类型
     */
    private void putObjectToMinio(InputStream stream, String objectName, String contentType) {
        try {
            minioClient.putObject(
                PutObjectArgs.builder()
                    .bucket(minioProperties.getBucketName())
                    .object(objectName)
                    .stream(stream, -1, 10485760) // 10 MB 的最大单个对象大小
                    .contentType(contentType)
                    .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("上传到Minio时出错", e);
        }
    }

    /**
     * 生成唯一的文件名，确保文件名不重复
     *
     * @param originalFilename 原始文件名
     * @return 生成的唯一文件名
     */
    private String generateFileName(String originalFilename) {
        return UUID.randomUUID() + "-" + originalFilename;
    }

    /**
     * 生成从URL上传的文件名，附加特定后缀以区分来源
     *
     * @return 生成的文件名
     */
    private String generateUrlFileName() {
        return UUID.randomUUID() + "-image_from_url.png";
    }

    /**
     * 生成文件的访问URL
     *
     * @param objectName MinIO中的对象名
     * @return 文件的完整访问URL
     */
    private String generateFileUrl(String objectName) {
        return String.format("%s/%s/%s", minioProperties.getEndpoint(), minioProperties.getBucketName(), objectName);
    }
}