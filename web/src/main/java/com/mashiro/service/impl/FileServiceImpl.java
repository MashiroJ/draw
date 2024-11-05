package com.mashiro.service.impl;

import com.mashiro.service.FileService;
import com.mashiro.utils.minio.MinioProperties;
import io.minio.*;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Resource
    private MinioClient minioClient;

    @Resource
    private MinioProperties minioProperties;

    @Override
    public String upload(MultipartFile file) throws IOException {
        String filename = generateFileName(file.getOriginalFilename());
        putObjectToMinio(file.getInputStream(), filename, file.getContentType());
        return generateFileUrl(filename);
    }

    @Override
    public String uploadFromUrl(String fileUrl) {
        String filename = generateFileName("image_from_url.png");
        try (InputStream inputStream = fetchImageFromUrl(fileUrl)) {
            putObjectToMinio(inputStream, filename, "image/png");
        } catch (Exception e) {
            throw new RuntimeException("无法从URL上传文件", e);
        }
        return generateFileUrl(filename);
    }

    private InputStream fetchImageFromUrl(String fileUrl) throws Exception {
        URL url = new URL(fileUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoInput(true);
        connection.connect();
        return connection.getInputStream();
    }

    private void putObjectToMinio(InputStream stream, String filename, String contentType) {
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(minioProperties.getBucketName())
                    .object(filename)
                    .stream(stream, -1, 10485760)
                    .contentType(contentType)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("上传到Minio时出错", e);
        }
    }

    private String generateFileName(String originalFilename) {
        String dateStr = new SimpleDateFormat("yyyyMMdd").format(new Date());
        return dateStr + "/" + UUID.randomUUID() + "-" + originalFilename;
    }

    private String generateFileUrl(String filename) {
        return minioProperties.getEndpoint() + "/" + minioProperties.getBucketName() + "/" + filename;
    }

    @Override
    public void deleteImage(String objectName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(minioProperties.getBucketName())
                    .object(objectName)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("Error deleting image", e);
        }
    }

    @Override
    public InputStream getFile(String objectName) {
        try {
            return minioClient.getObject(GetObjectArgs.builder()
                    .bucket(minioProperties.getBucketName())
                    .object(objectName)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("Error fetching file", e);
        }
    }
}
